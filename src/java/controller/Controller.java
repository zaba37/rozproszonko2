/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Ad;
import entity.Adminmesanges;
import entity.Admins;
import entity.Users;
import facade.AdFacade;
import facade.AdminmesangesFacade;
import facade.AdminsFacade;
import facade.UsersFacade;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author zaba3
 */
@WebServlet(name = "Controller", loadOnStartup = 1, urlPatterns = {"/editProfil", "/profil", "/detailsAd", "/register", "/login", "/logout", "/createValidation", "/createAdminMsg", "/allUsers", "/addNewAd", "/deleteUser", "/editAdminMsg"})
public class Controller extends HttpServlet {

    @EJB
    private UsersFacade usersFacade;

    @EJB
    private AdFacade adFacade;

    @EJB
    AdminmesangesFacade adminMsg;

    @EJB
    AdminsFacade adminFacade;

    @Override
    public void init() throws ServletException {

        // store category list in servlet context
        //getServletContext().setAttribute("categories", categoryFacade.findAll());
        getServletContext().setAttribute("adminMsg", adminMsg.findAll());
        getServletContext().setAttribute("add", adFacade.findAll());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String userPath = request.getServletPath();

        switch (userPath) {
            case "/profil":
                HttpSession session3 = request.getSession();
                Users user2 = usersFacade.FindByLogin((String) session3.getAttribute("login"));
                request.setAttribute("usr", user2);
                userPath = "/profil";
                break;
            case "/register":
                userPath = "/createAccount";
                break;
            case "/logout":
                HttpSession session = request.getSession();
                session.invalidate();
                userPath = "/successLogout";
                break;
            case "/addNewAd":
                userPath = "/addNewAd";
                break;
            case "/createAdminMsg":
                userPath = "/addAdminMsg";
                break;
            case "/editAdminMsg":
                String msgId = request.getQueryString();
                Adminmesanges msg = adminMsg.find(Integer.parseInt(msgId));
                request.setAttribute("msg", msg);
                break;
            case "/allUsers":
                request.setAttribute("users", usersFacade.findAll());
                userPath = "/showAllUsers";
                break;
            case "/deleteUser":
                String userId = request.getQueryString();
                Users user = usersFacade.find(Integer.parseInt(userId));

                for (Ad ad : user.getAdList()) {
                    adFacade.remove(ad);
                }

                usersFacade.remove(user);
                request.setAttribute("users", usersFacade.findAll());
                userPath = "/showAllUsers";
                break;
            case "/detailsAd":
                String adId = request.getQueryString();
                Ad ad = adFacade.find(Integer.parseInt(adId));
                ad.setVisit(ad.getVisit() + 1);
                adFacade.edit(ad);
                request.setAttribute("ad", ad);

                if (ad.getModerator() != null) {
                    if (ad.getModerator().equals("m")) {
                        request.setAttribute("m", -1000);
                    }
                }

                String extension = "";
                if (ad.getPathtofile() != null) {
                    int a = ad.getPathtofile().lastIndexOf(".");

                    if (a >= 0) {
                        extension = ad.getPathtofile().substring(a + 1);
                    }

                    request.setAttribute("type", extension);
                }
                userPath = "/detailsAD";
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
            case "/editProfil":
                HttpSession session3 = request.getSession();
                Users user2 = usersFacade.FindByLogin((String) session3.getAttribute("login"));
                user2.setEmail((String) request.getParameter("email"));
                user2.setLogin((String) request.getParameter("login"));
                user2.setNumberadforonepage(Integer.valueOf((String) request.getParameter("numberForOnePage")));
                user2.setNumberofstyle(Integer.valueOf((String) request.getParameter("style")));
                user2.setPassword((String) request.getParameter("password"));
                session3.setAttribute("style", user2.getNumberofstyle());
                session3.setAttribute("pageSize", user2.getNumberadforonepage());
                usersFacade.edit(user2);
                userPath = "/successEditAccountData";
                break;
            case "/createValidation":
                boolean checkLoginAvilable = false;
                boolean checkPasswordCorrect = false;
                boolean checkEmailAvilable = false;
                boolean checkEmpty = false;

                ArrayList<Users> users = new ArrayList();
                ArrayList<Admins> admins = new ArrayList();
                admins.addAll(adminFacade.findAll());
                users.addAll(usersFacade.findAll());

                String username = request.getParameter("username");
                String email = request.getParameter("email");
                String password1 = request.getParameter("password1");
                String password2 = request.getParameter("password2");

                if (!username.equals("") && !email.equals("") && !password1.equals("") && !password2.equals("")) {
                    for (Users u : users) {
                        if (u.getLogin().equals(request.getParameter("username"))) {
                            checkLoginAvilable = true;
                            break;
                        }

                        if (u.getEmail().equals(request.getParameter("email"))) {
                            checkEmailAvilable = true;
                            break;
                        }
                    }

                    if (!password1.equals(password2)) {
                        checkPasswordCorrect = true;
                    }

                    for (Admins a : admins) {
                        if (a.getLogin().equals(request.getParameter("username"))) {
                            checkLoginAvilable = true;
                            break;
                        }
                    }

                } else {
                    if (username.equals("")) {
                        request.setAttribute("wrongUserNameEmpty", "-2");
                    }

                    if (email.equals("")) {
                        request.setAttribute("wrongEmailEmpty", "-2");
                    }

                    if (password1.equals("") || password2.equals("")) {
                        request.setAttribute("wrongPasswordEmpty", "-2");
                    }

                    checkEmpty = true;
                }

                if (!checkEmpty) {
                    if (checkLoginAvilable) {
                        userPath = "/createAccount";
                        request.setAttribute("wrongUserName", "-1");
                    } else if (checkPasswordCorrect) {
                        userPath = "/createAccount";
                        request.setAttribute("wrongPassword", "-1");
                    } else if (checkEmailAvilable) {
                        userPath = "/createAccount";
                        request.setAttribute("wrongEmail", "-1");
                    } else {
                        userPath = "/sucessCreateAccount";
                        Users user = new Users();
                        user.setEmail(email);
                        user.setLogin(username);
                        user.setPassword(password2);
                        user.setNumberadforonepage(3);
                        usersFacade.create(user);
                    }
                } else {
                    userPath = "/createAccount";
                }

                break;

            case "/login":
                Users user = usersFacade.findByLoginAndPassword(request.getParameter("login"), request.getParameter("password"));
                Admins admin = adminFacade.findByLoginAndPassword(request.getParameter("login"), request.getParameter("password"));
                getServletContext().setAttribute("add", adFacade.findAll());

                if (admin != null) {
                    user = null;
                    HttpSession session = request.getSession();
                    session.setAttribute("login", admin.getLogin());
                    userPath = "/successAdminLogin";
                } else if (user != null) {
                    HttpSession session = request.getSession();
                    session.setAttribute("login", user.getLogin());
                    userPath = "/successLogin";

                    Admins a = adminFacade.findAll().get(0);
                    session.setAttribute("numberOfFiles", a.getNumberoffiles());
                    session.setAttribute("style", user.getNumberofstyle());
                    session.setAttribute("sizeOfFile", a.getSizeoffile());
                    session.setAttribute("pageSize", user.getNumberadforonepage());
                } else {
                    userPath = "/failLogin";
                }
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
