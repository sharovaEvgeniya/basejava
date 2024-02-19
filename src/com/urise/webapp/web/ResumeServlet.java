package com.urise.webapp.web;

import com.urise.webapp.model.*;
import com.urise.webapp.storage.Storage;
import com.urise.webapp.util.DateUtil;
import com.urise.webapp.util.HtmlUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
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
            case "add" -> r = Resume.EMPTY;
            case "view" -> r = storage.get(uuid);
            case "edit" -> {
                r = storage.get(uuid);
                for (SectionType type : SectionType.values()) {
                    Section section = r.getSection(type);
                    switch (type) {
                        case OBJECTIVE, PERSONAL -> {
                            if (section == null) {
                                section = TextSection.EMPTY;
                            }
                        }
                        case ACHIEVEMENT, QUALIFICATION -> {
                            if (section == null) {
                                section = ListSection.EMPTY;
                            }
                        }
                        case EXPERIENCE, EDUCATION -> {
                            OrganizationSection orgSection = (OrganizationSection) r.getSection(type);
                            List<Organization> emptyFirstOrganizations = new ArrayList<>();
                            emptyFirstOrganizations.add(new Organization("", "", new Organization.Period()));
                            if (orgSection != null) {
                                for (Organization organization : orgSection.getOrganizations()) {
                                    List<Organization.Period> emptyFirstPeriods = new ArrayList<>(organization.getPeriods());
                                    emptyFirstOrganizations.add(new Organization(organization.getTitle(), organization.getUrl(), emptyFirstPeriods));
                                }
                            }
                            section = new OrganizationSection(emptyFirstOrganizations);
                            System.out.println(section);
                        }
                    }
                    r.setSection(type, section);
                    System.out.println(r);
                }
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
        if (uuid == null || uuid.trim().length() == 0) {
            resume = new Resume(UUID.randomUUID().toString(), fullName.trim());
            storage.save(resume);
        } else {
            resume = storage.get(uuid);
            resume.setFullName(fullName.trim());
        }
        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && value.trim().length() != 0) {
                resume.setContact(type, value.trim());
            } else {
                resume.getContacts().remove(type);
            }
        }
        for (SectionType type : SectionType.values()) {
            String value = request.getParameter(type.name()) == null ?
                    request.getParameter(type.name() + "orgName") : request.getParameter(type.name());
            String[] values = request.getParameterValues(type.name()) == null ?
                    request.getParameterValues(type.name() + "orgName") : request.getParameterValues(type.name());
            if (HtmlUtil.isEmpty(value) && values.length < 2) {
                resume.getSections().remove(type);
            } else {
                switch (type) {
                    case OBJECTIVE, PERSONAL -> resume.setSection(type, new TextSection(value));
                    case ACHIEVEMENT, QUALIFICATION ->
                            resume.setSection(type, new ListSection(value.trim().split("\\n")));
                    case EDUCATION, EXPERIENCE -> {
                        OrganizationSection organizationSection = setOrganization(request, type);
                        resume.setSection(type, organizationSection);
                    }
                }
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
        String[] linkOrganization = request.getParameterValues(type.name() + "link");
        for (int i = 0; i < nameOrganization.length; i++) {
            if (isPresent(nameOrganization[i])) {
                List<Organization.Period> periodList = setPeriod(
                        request.getParameterValues(type.name() + i + "startDate"),
                        request.getParameterValues(type.name() + i + "endDate"),
                        request.getParameterValues(type.name() + i + "position"),
                        request.getParameterValues(type.name() + i + "description"));
                organizationList.add(new Organization(nameOrganization[i], linkOrganization[i], periodList));
            }
        }
        return organizationList.size() == 0 ? null : new OrganizationSection(organizationList);
    }

    private static List<Organization.Period> setPeriod(String[] startDate, String[] endDate, String[] position, String[] description) {
        List<Organization.Period> periodList = new ArrayList<>();
        for (int i = 0; i < position.length; i++) {
            if (isPresent(startDate[i]) && isPresent(position[i])) {
                periodList.add(new Organization.Period(checkDate(startDate[i]), checkDate(endDate[i]), position[i], description[i]));
            }
        }
        return periodList.isEmpty() ? null : periodList;
    }

    private static LocalDate checkDate(String line) {
        return line.isEmpty() ? null : DateUtil.parse(line);
    }

    private static boolean isPresent(String line) {
        return line != null && !line.isEmpty();
    }
}
