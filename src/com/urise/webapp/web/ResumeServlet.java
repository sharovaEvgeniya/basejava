package com.urise.webapp.web;

import com.urise.webapp.model.*;
import com.urise.webapp.storage.Storage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class ResumeServlet extends HttpServlet {
    private Storage storage;

    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);
        addDriver();
        storage = com.urise.webapp.Config.get().getStorage();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp")
                    .forward(request, response);
            return;
        }
        Resume r;
        switch (action) {
            case "delete" -> {
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            }
            case "add" -> {
                request.getRequestDispatcher("/WEB-INF/jsp/add.jsp").forward(request, response);
                response.sendRedirect("resume");
                return;
            }
            case "view", "edit" -> {
                r = storage.get(uuid);
            }
            default -> throw new IllegalArgumentException("Action " + action + "  is illegal");
        }
        request.setAttribute("resume", r);
        request.getRequestDispatcher(("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp"))
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        Resume resume;
        if (uuid == null) {
            resume = new Resume(UUID.randomUUID().toString(), fullName.trim());
            storage.save(resume);
        } else {
            resume = storage.get(uuid);
            resume.setFullName(fullName.trim());
        }
        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && value.trim().length() != 0) {
                resume.addContact(type, value.trim());
            } else {
                resume.getContacts().remove(type);
            }
        }
        for (SectionType type : SectionType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && value.trim().length() != 0) {
                switch (type) {
                    case PERSONAL, OBJECTIVE ->
                            resume.addSection(type, new TextSection(value.trim().replaceAll("\\s+", " ")));
                    case ACHIEVEMENT, QUALIFICATION -> {
                        List<String> stringList = Arrays.stream(value.trim().replaceAll("\\s+", " ").split("\\.")).toList();
                        resume.addSection(type, new ListSection(stringList));
                    }
                    case EDUCATION, EXPERIENCE -> {
                        OrganizationSection organizationSection = setOrganization(request, type);
                        resume.addSection(type, organizationSection);
                    }
                }
            } else {
                resume.getSections().remove(type);
            }
        }
        storage.update(resume);
        response.sendRedirect("resume");
    }

    private void addDriver() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private OrganizationSection setOrganization(HttpServletRequest request, SectionType type) {
        List<Organization> organizationList = new ArrayList<>();
        String[] nameOrganization = request.getParameterValues(type.name() + "orgName");
        String[] linkOrganization = request.getParameterValues(type.name() + "website");
        for (int i = 0; i < nameOrganization.length; i++) {
            if (isPresent(nameOrganization[i])) {
                List<Organization.Period> periodList = setPeriod(
                        request.getParameterValues(type.name() + i + "startDate"),
                        request.getParameterValues(type.name() + i + "endDate"),
                        request.getParameterValues(type.name() + i + "title"),
                        request.getParameterValues(type.name() + i + "description"));
                organizationList.add(new Organization(nameOrganization[i], linkOrganization[i], periodList));
            }
        }
        return organizationList.size() == 0 ? null : new OrganizationSection(organizationList);
    }

    private static List<Organization.Period> setPeriod(String[] startDate, String[] endDate, String[] title, String[] description) {
        List<Organization.Period> periodList = new ArrayList<>();
        for (int i = 0; i < title.length; i++) {
            if (isPresent(startDate[i]) && isPresent(title[i])) {
                periodList.add(new Organization.Period(checkDate(startDate[i]), checkDate(endDate[i]), title[i], description[i]));
            }
        }
        return periodList.isEmpty() ? null : periodList;
    }

    private static LocalDate checkDate(String line) {
        return line.isEmpty() ? null : LocalDate.parse(line);
    }

    private static boolean isPresent(String line) {
        return line != null && !line.isEmpty();
    }
}
