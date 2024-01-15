package com.urise.webapp.web;

import com.urise.webapp.model.*;
import com.urise.webapp.storage.Storage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
            case "view", "edit" -> r = storage.get(uuid);
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
                    case PERSONAL, OBJECTIVE -> resume.addSection(type, new TextSection(value.trim()));
                    case ACHIEVEMENT, QUALIFICATION -> {
                        List<String> stringList = Arrays.stream(value.trim().split("\\.")).toList();
                        resume.addSection(type, new ListSection(stringList));
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
}
