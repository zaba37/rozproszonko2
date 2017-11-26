/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Ad;
import entity.Admindictionary;
import entity.Adminmesanges;
import entity.Users;
import facade.AdFacade;
import facade.AdmindictionaryFacade;
import facade.AdminmesangesFacade;
import facade.UsersFacade;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 *
 * @author zaba3

 */
 @MultipartConfig(location = "/tmp", 
         fileSizeThreshold = 1024 * 1024 * 20,
         maxFileSize = 1024 * 1024 * 20, 
         maxRequestSize = 1024 * 1024 * 20)
@WebServlet(name = "ControllerAD", urlPatterns = {"/nextPage", "/search", "/modPress", "/mod", "/toModeration", "/editOwnAdPress", "/editOwnAd", "/deleteOwnAd", "/showYourAd", "/index", "/ControllerAD", "/createNewAd", "/showAllAd", "/createNewAdminMessenge", "/editAdminMsgPress"})
public class ControllerAD extends HttpServlet {

    @EJB
    UsersFacade users;

    @EJB
    AdFacade ad;

    @EJB
    AdminmesangesFacade adminMsg;

    @EJB
    AdmindictionaryFacade dictionary;

    ArrayList<Ad> allAd = new ArrayList();
    ArrayList<Ad> searchAdList = new ArrayList();

    @Override
    public void init() throws ServletException {
        // store category list in servlet context
        getServletContext().setAttribute("adminMsg", adminMsg.findAll());
        getServletContext().setAttribute("add", ad.findAll());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userPath = request.getServletPath();

        switch (userPath) {
            case "nextPage":
                break;
            case "/showAllAd":
                ArrayList<Ad> adList2 = new ArrayList();
                ArrayList<Ad> result = new ArrayList();
                String pageNumber = request.getQueryString();
                int page;
                int pageSize;
                int indexFrom;
                int indexTo;
                pageSize = 6;
                HttpSession session = request.getSession();

                adList2.addAll(ad.findAll());

                if (session.getAttribute("login") == null || ((String) session.getAttribute("login")).equals("admin")) {
                    session.setAttribute("pageSize", pageSize);
                } else {
                    String login5 = (String) session.getAttribute("login");
                    Users owner5 = users.FindByLogin(login5);
                    pageSize = owner5.getNumberadforonepage();
                    session.setAttribute("pageSize", pageSize);
                }

                double a = Math.ceil((double) adList2.size() / pageSize);

                if (a == 0) {
                    a = 1;
                }

                session.setAttribute("howManyPages", a);

                if (pageNumber == null) {
                    page = 1;
                } else {
                    page = Integer.parseInt(pageNumber);
                }

                indexTo = page * pageSize;
                indexFrom = indexTo - pageSize;

                for (int i = indexFrom; i < indexTo; i++) {
                    if (i <= adList2.size() - 1) {
                        result.add(adList2.get(i));
                    } else {
                        break;
                    }
                }

                request.setAttribute("ad", result);

                userPath = "/showAllAd";
                break;
            case "/showYourAd":
                HttpSession session2 = request.getSession();
                String login = (String) session2.getAttribute("login");
                Users owner = users.FindByLogin(login);

                ArrayList<Ad> adList1 = ad.FindByOwnerId(owner.getId());

                owner.setAdList(adList1);

                users.edit(owner);
                request.setAttribute("owner", owner);
                userPath = "/showOwnAd";
                break;
            case "/deleteOwnAd":
                String deleteAdId = request.getQueryString();
                Ad add13 = ad.find(Integer.parseInt(deleteAdId));

                HttpSession session3 = request.getSession();
                String login1 = (String) session3.getAttribute("login");
                Users owner1 = users.FindByLogin(login1);

                ArrayList<Ad> adList = ad.FindByOwnerId(owner1.getId());
                adList.remove(add13);
                owner1.setAdList(adList);

                users.edit(owner1);
                ad.remove(add13);

                request.setAttribute("owner", owner1);
                userPath = "/showOwnAd";
                break;
            case "/editOwnAd":
                String editAdId = request.getQueryString();
                Ad add2 = ad.find(Integer.parseInt(editAdId));
                request.setAttribute("ad", add2);
                break;
            case "/toModeration":
                String modAdId = request.getQueryString();
                Ad add3 = ad.find(Integer.parseInt(modAdId));
                add3.setModerator("m");
                ad.edit(add3);
                userPath = "/successAddToModeration";
                break;
            case "/mod":
                String editAdId2 = request.getQueryString();
                Ad add22 = ad.find(Integer.parseInt(editAdId2));
                request.setAttribute("ad", add22);
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
            case "/createNewAd":
                boolean badWord = false;
                Ad add = new Ad();
                Users user = new Users();
                HttpSession session12 = request.getSession();

                user = users.FindByLogin((String) session12.getAttribute("login"));

                ArrayList<Admindictionary> wordList = new ArrayList();
                wordList.addAll(dictionary.findAll());

                String[] wordFromDescryption = request.getParameter("message2").split(" ");
                String[] wordFromTitle = request.getParameter("title2").split(" ");
                ArrayList<String> banedWord = new ArrayList();

                for (Admindictionary word : wordList) {
                    for (int i = 0; i < wordFromDescryption.length; i++) {
                        if ((word.getWord().toLowerCase()).equals(wordFromDescryption[i].toLowerCase())) {
                            banedWord.add(wordFromDescryption[i].toLowerCase());
                        }
                    }

                    for (int j = 0; j < wordFromTitle.length; j++) {
                        if ((word.getWord().toLowerCase()).equals(wordFromTitle[j].toLowerCase())) {
                            banedWord.add(wordFromTitle[j].toLowerCase());
                        }
                    }
                }

                if (banedWord.isEmpty()) {
                    add.setOwner(user);
                    add.setTitle(request.getParameter("title2"));
                    add.setDescryption(request.getParameter("message2"));
                    add.setVisit(0);

                    ad.create(add);

                    //Users user10 = users.FindByLogin(user.getLogin());
                    String applicationPath = request.getServletContext().getRealPath("");

                    File fileSaveDir = new File(applicationPath + "/" + add.getId());
                    if (!fileSaveDir.exists()) {
                        fileSaveDir.mkdir();
                    }

                    int i = 1;
                    for (Part part : request.getParts()) {

                        if (part.getName().equalsIgnoreCase("imageUri")) {
                            String fileName = extractFileName(part);
                            if (fileName != null && !fileName.equals("")) {
                                InputStream ip;
                                ip = part.getInputStream();

                                FileOutputStream fos = new FileOutputStream(applicationPath + "/" + add.getId() + "/" + fileName);
                                int len;
                                int size = 1024;
                                byte[] buf;
                                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                                buf = new byte[size];
                                while ((len = ip.read(buf, 0, size)) != -1) {
                                    bos.write(buf, 0, len);
                                }
                                buf = bos.toByteArray();
                                int maxSize = (int) session12.getAttribute("sizeOfFile");
                                if (buf.length > maxSize * 1000000) {
                                    File f = new File(applicationPath + "/" + add.getId() + "/" + fileName);
                                    f.delete();

                                    fos.close();
                                    continue;
                                }
                                fos.write(buf);
                                add.setPathtofile(fileName);
                                fos.close();
                            }
                        }
                    }

                    ad.edit(add);
                    userPath = "/successCreateNewAd";
                } else {
                    userPath = "/banedWordDetected";
                    request.setAttribute("banedWords", banedWord);
                }

                break;
//            case "/showAllAd":
//                userPath = "/showAllAd";
//                break;
            case "/createNewAdminMessenge":
                Adminmesanges msg = new Adminmesanges();
                msg.setTitle(request.getParameter("title"));
                msg.setDesctyption(request.getParameter("message"));

                adminMsg.create(msg);

                userPath = "/successCreateNewAdminMsg";
                getServletContext().setAttribute("adminMsg", adminMsg.findAll());
                break;

            case "/editAdminMsgPress":
                Adminmesanges msg2 = new Adminmesanges();
                msg2.setId(Integer.parseInt(request.getParameter("id")));
                msg2.setTitle(request.getParameter("title"));
                msg2.setDesctyption(request.getParameter("message"));

                if (((String) request.getParameter("edit")).equals("Usun")) {
                    adminMsg.remove(msg2);
                    userPath = "/successRemoveAdminMsg";
                } else if (((String) request.getParameter("edit")).equals("Zatwierdz")) {
                    adminMsg.edit(msg2);
                    userPath = "/successEditAdminMsg";
                }
                getServletContext().setAttribute("adminMsg", adminMsg.findAll());
                break;
            case "/editOwnAdPress":
                Ad add2 = ad.find(Integer.parseInt(request.getParameter("id")));
                add2.setTitle(request.getParameter("title"));
                add2.setDescryption(request.getParameter("message"));
                ad.edit(add2);
                userPath = "/successEditOwnAd";
                break;
            case "/modPress":

                if (((String) request.getParameter("edit")).equals("Zatwierdz")) {
                    Ad add22 = ad.find(Integer.parseInt(request.getParameter("id")));
                    add22.setTitle(request.getParameter("title"));
                    add22.setDescryption(request.getParameter("message"));
                    add22.setModerator("b");
                    ad.edit(add22);
                    userPath = "/successModerationMsg";
                } else if (((String) request.getParameter("edit")).equals("Anuluj")) {
                    userPath = "/cancelModerationMsg";
                }
                getServletContext().setAttribute("add", ad.findAll());
                break;
            case "/search":
                String[] wordFromSearch = request.getParameter("searchWords").split(" ");
                String option = request.getParameter("typ");
                ArrayList<Ad> adlistsearch = new ArrayList();
                adlistsearch.addAll(ad.findAll());
                ArrayList<Ad> result = new ArrayList();

                for (Ad ad : adlistsearch) {
                    String[] mad = ad.getDescryption().split(" ");
                    String[] tad = ad.getTitle().split(" ");
                    ArrayList<String> madList = new ArrayList();
                    ArrayList<String> tadList = new ArrayList();
                    boolean andFlag = false;
                    boolean orFlag = false;
                    boolean notFlag = false;

                    for (String m : mad) {
                        madList.add(m);
                    }

                    for (String t : tad) {
                        tadList.add(t);
                    }

                    if (option.equals("and")) {
                        if (wordFromSearch.length == (madList.size() + tadList.size())) {
                            for (String w : wordFromSearch) {
                                madList.remove(w);
                                tadList.remove(w);
                            }

                            if (madList.isEmpty() && tadList.isEmpty()) {
                                andFlag = true;
                            }
                        }
                    }

                    if (option.equals("not")) {
                        for (String m : mad) {
                            for (String s : wordFromSearch) {
                                if (m.toLowerCase().equals(s.toLowerCase())) {
                                    notFlag = true;
                                }
                            }
                        }

                        for (String t : tad) {
                            for (String s : wordFromSearch) {
                                if (t.toLowerCase().equals(s.toLowerCase())) {
                                    notFlag = true;
                                }
                            }
                        }
                    }

                    if (option.equals("or")) {
                        for (String m : mad) {
                            for (String s : wordFromSearch) {
                                if (m.toLowerCase().equals(s.toLowerCase())) {
                                    orFlag = true;
                                }
                            }
                        }

                        for (String t : tad) {
                            for (String s : wordFromSearch) {
                                if (t.toLowerCase().equals(s.toLowerCase())) {
                                    orFlag = true;
                                }
                            }
                        }
                    }

                    if (option.equals("and") && andFlag) {
                        result.add(ad);
                    }

                    if (option.equals("or") && orFlag) {
                        result.add(ad);
                    }

                    if (option.equals("not") && !notFlag) {
                        result.add(ad);
                    }
                }

                userPath = "/showAllAd";

                request.setAttribute("ads", -1);
                request.setAttribute("adsearch", result);
                searchAdList.addAll(result);
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

    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }
        return "";
    }
}
