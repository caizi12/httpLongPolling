package lf.java;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TestServlet extends HttpServlet {
    private static final long serialVersionUID = 6395351891073437973L;



    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(req.getParameter("test"));
    }

    @Override
    public void init() throws ServletException {
        System.out.println("init testServldet");
    }
}
