package com.lf.http;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by lfeng on 17/6/1.
 *
 * @author lfeng
 * @date 2017/06/01
 */
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
