/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Admindictionary;
import facade.AdmindictionaryFacade;
import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author zaba3
 */
@WebServlet(name = "ControlerDictionary", urlPatterns = {"/ControlerDictionary", "/dictionary", "/newWord", "/deleteWord"})
public class ControlerDictionary extends HttpServlet {

    @EJB
    AdmindictionaryFacade dictionary;

    @Override
    public void init() throws ServletException {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userPath = request.getServletPath();

        switch (userPath) {
            case "/dictionary":
                request.setAttribute("dictionary", dictionary.findAll());
                break;
            case "/deleteWord":
                String deleteWordId = request.getQueryString();
                Admindictionary word = dictionary.find(Integer.valueOf(deleteWordId));
                dictionary.remove(word);
                request.setAttribute("dictionary", dictionary.findAll());
                userPath = "/dictionary";
                break;
        }

        String url = "/WEB-INF/view" + userPath + ".jsp";

        try {
            request.getRequestDispatcher(url).forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userPath = request.getServletPath();

        switch (userPath) {
            case "/newWord":
                Admindictionary word = new Admindictionary();

                if (request.getParameter("word") != null) {
                    if (((String) request.getParameter("word")).length() >= 1) {
                        word.setWord((String) request.getParameter("word"));
                        dictionary.create(word);
                    } else {
                        request.setAttribute("tooShort", -1);
                    }
                } else {
                    request.setAttribute("empty", -1);
                }

                request.setAttribute("dictionary", dictionary.findAll());
                userPath = "/dictionary";
                break;
        }

        String url = "/WEB-INF/view" + userPath + ".jsp";

        try {
            request.getRequestDispatcher(url).forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
