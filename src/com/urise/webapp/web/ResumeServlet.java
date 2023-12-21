package com.urise.webapp.web;

import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.Storage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ResumeServlet extends HttpServlet {
    Storage storage;

    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);
        addDriver();
        storage = com.urise.webapp.Config.get().getStorage();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Resume> resumeList = storage.getAllSorted();
        int count = 1;
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter printWriter = response.getWriter();
        printWriter.write("<style>\n" +
                "    table {\n" +
                "        width: 30%;\n" +
                "    }\n" +
                "    table, th, td {\n" +
                "        border:2px solid #2E6E9E;\n" +
                "    }\n" +
                "</style>");
        for (Resume resume : resumeList) {
            printWriter.write("<table>\n" +
                    "    <tr>\n" +
                    "        <th>№</th>    \n" +
                    "        <th>Uuid</th>\n" +
                    "        <th>Full Name</th>\n" +
                    "    </tr>\n" +
                    "\n" +
                    "    <tr>\n" +
                    "        <td>" + count++ + "</td>\n" +
                    "        <td>" + resume.getUuid() + "</td>\n" +
                    "        <td>" + resume.getFullName() + "</td>\n" +
                    "    </tr>\n" +
                    "</table>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    private void addDriver() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
