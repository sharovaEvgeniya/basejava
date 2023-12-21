package com.urise.webapp.web;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class ResumeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        String name = request.getParameter("name");
        response.getWriter().write(name == null ? "Hello resumes!" : "Hello " + name + "!");
        response.getWriter().write("<table>\n" +
                "  <tr>\n" +
                "    <th>Company</th>\n" +
                "    <th>Contact</th>\n" +
                "    <th>Country</th>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <td>Alfreds Futterkiste</td>\n" +
                "    <td>Maria Anders</td>\n" +
                "    <td>Germany</td>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <td>Centro comercial Moctezuma</td>\n" +
                "    <td>Francisco Chang</td>\n" +
                "    <td>Mexico</td>\n" +
                "  </tr>\n" +
                "</table>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
