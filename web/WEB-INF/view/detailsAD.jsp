<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%-- 
    Document   : allAD
    Created on : 2015-11-15, 11:48:18
    Author     : zaba3
--%>

<style type="text/css">
    .tg  {border-collapse:collapse;border-spacing:0; width: 100%}
    .tg td{font-family:Arial, sans-serif;font-size:14px;padding:14px 13px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;}
    .tg th{font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:14px 13px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;}
    .tg .tg-qsvf{font-size:18px;vertical-align:top}
    .tg .tg-2ktp{font-size:16px;vertical-align:top}
    .tg .tg-3iwn{font-weight:bold;font-style:italic;font-size:24px;background-color:#528ccc;color:#ffffff;vertical-align:top}
</style>  

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title><fmt:message key='tytul'/></title>
        <meta name="description" content="free website template" />
        <meta name="keywords" content="enter your keywords here" />
        <meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
        <c:if test="${empty sessionScope.style}"><link rel="stylesheet" type="text/css" href="css/style.css" /></c:if>
        <c:if test="${sessionScope.style == 0}"><link rel="stylesheet" type="text/css" href="css/style.css" /></c:if>
        <c:if test="${sessionScope.style == 1}"><link rel="stylesheet" type="text/css" href="css/style_1.css" /></c:if>
            <script type="text/javascript" src="js/jquery.min.js"></script>
            <script type="text/javascript" src="js/image_slide.js"></script>
        </head>

        <body>
            <div id="header_container">  
                <div id="header">
                    <div id="banner">
                        <h1><span><fmt:message key='tytulBanerPart1'/></span> <fmt:message key='tytulBanerPart2'/></h1>
                </div><!--close banner-->	
                <div id="banner_slogan">
                    <h2></h2>
                </div><!--close banner_slogan-->	
                <div id="menubar">
                    <ul id="menu">
                        <li><a href="index.jsp"><fmt:message key='home'/></a></li>
                        <li class="current"><a href="showAllAd"><fmt:message key='showAllAd'/></a></li>

                        <c:if test="${empty sessionScope.login}">
                            <li><a href="register"><fmt:message key='register'/></a></li>
                            </c:if>

                        <c:if test="${not empty sessionScope.login}">
                            <c:if test="${sessionScope.login != 'admin'}">
                                <li><a href="showYourAd"><fmt:message key='showYourAd'/></a></li>
                                <li><a href="addNewAd"><fmt:message key='addNewAd'/></a></li>
                                <li><a href="profil"><fmt:message key='profil'/></a></li>
                                </c:if>

                            <c:if test="${sessionScope.login == 'admin'}">
                                <li><a href="allUsers"><fmt:message key='allUsers'/></a></li>
                                <li><a href="createAdminMsg"><fmt:message key='createAdminMsg'/></a></li>
                                </c:if>

                            <li><a href="logout"><fmt:message key='logout'/></a></li>
                            </c:if>
                    </ul>
                </div><!--close menubar-->  
            </div><!--close header-->  
        </div><!--close header_container-->  
        <div id="main">
            <div id="site_content">	
                <div class="sidebar_container">       
                    <div class="sidebar">
                        <div class="sidebar_item">
                            <c:if test="${empty sessionScope.login}">
                                <h2><fmt:message key='titleLogin'/></h2>
                                <div class="sidebar_item">
                                    <form action="login" method="post">
                                        <p>
                                            <input type="text" name="login" id="login" value="username" />
                                        </p>
                                        <p>
                                            <input type="password" name="password" id="password" value="password"/>
                                        </p>
                                        <input type="submit" name="loginbt" value="<fmt:message key='login'/>" />
                                        <!--                        <div class="button_small">
                                                                    <a href="/contact.html">Zaloguj</a>
                                                                </div>close button_small-->
                                    </form>
                                </c:if>

                                <c:if test="${not empty sessionScope.login}">
                                    <h2><fmt:message key='titleLogin2'/></h2>
                                    <div class="sidebar_item">
                                        <p>
                                            <fmt:message key='titleLogin2desc'/> ${sessionScope.login}
                                        </p>
                                    </c:if>
                                </div><!--close sidebar_item--> 
                            </div><!--close sidebar-->     
                        </div><!--close sidebar_container-->
                    </div>
                    <div id="content">
                        <div class="content_item">
                            <c:if test="${not empty m}">
                                <h1><font color="red"><fmt:message key='sendtomod'/></font></h1>
                                <br></br>
                            </c:if>
                            <table class="tg">
                                <tr>
                                    <th class="tg-3iwn"><font color="white">${ad.title}</font></th>
                                </tr>
                                <tr>
                                    <td class="tg-qsvf">${ad.descryption}</td>
                                </tr>
                                <tr>
                                    <%

                                        String typ = null;

                                        if (request.getAttribute("type") != null) {
                                            typ = request.getAttribute("type").toString();

                                            if (typ.equals("jpg") || typ.equals("png")) {
                                    %>
                                <img class="advertisementImage" src="${ad.id}/${ad.pathtofile}" />

                                <%
                                    }
                                    if (typ.equals("mp3")) {
                                %>
                                <audio controls>
                                    <source src="${ad.id}/${ad.pathtofile}" type="audio/mpeg">
                                    Your browser does not support the audio element.
                                </audio>
                                <%
                                        }
                                    }

                                %>
                                </tr>
                                <tr>
                                    <td class="tg-2ktp"><fmt:message key='kontakt'/> ${ad.owner.email}</td>
                                </tr>
                                <tr>
                                    <c:if test="${not empty sessionScope.login}">
                                        <c:if test="${sessionScope.login != 'admin'}">
                                            <td><a href="toModeration?${ad.id}">Zgłoś do moderacji</a></td>
                                        </c:if>
                                    </c:if>
                                </tr>
                            </table>
                            <h1></h1>
                        </div><!--close content_item-->
                    </div><!--close content-->
                </div><!--close site_content-->    

            </div><!--close main--> 
            <div id="footer_container">     
                <div id="footer">
                    <a href="http://validator.w3.org/check?uri=referer">Valid XHTML</a> | <a href="http://jigsaw.w3.org/css-validator/check/referer">Valid CSS</a> | <a href="http://fotogrph.com/">Images</a> | website template by <a href="http://www.araynordesign.co.uk">ARaynorDesign</a>
                </div><!--close footer-->
            </div><!--close footer_container-->  
    </body>
</html>
