<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%-- 
    Document   : addAdminMsg
    Created on : 2015-11-17, 08:34:07
    Author     : zaba3
--%>

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
        <c:if test="${sessionScope.login == 'admin'}">
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
                            <li><a href="showAllAd"><fmt:message key='showAllAd'/></a></li>

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
                                    <li  class="current"><a href="createAdminMsg"><fmt:message key='createAdminMsg'/></a></li>
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
                                <c:if test="${not empty sessionScope.login}">
                                    <h1>Utwórz nowe ogłoszenie</h1> 
                                    <form action="createNewAdminMessenge" method="post">
                                        <table cellspacing="20">
                                            <tr>
                                                <td>
                                                    <fmt:message key='tytile'/>
                                                </td> 
                                            </tr>
                                            <tr>
                                                <td>
                                                    <input type="text" name="title" id="title"/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <fmt:message key='desc'/>
                                                </td>    
                                            </tr>
                                            <tr>
                                                <td>
                                                    <textarea name="message" rows="20" cols="70"></textarea>                                  
                                                </td>
                                            </tr>
                                        </table>
                                        <p><input type="submit" value="Utwórz" /> </p>
                                    </form>
                                </c:if>	
                            </div><!--close content_item-->
                        </div><!--close content-->
                    </div><!--close site_content-->    
                </div><!--close main--> 
                <div id="footer_container">     
                    <div id="footer">
                        <a href="http://validator.w3.org/check?uri=referer">Valid XHTML</a> | <a href="http://jigsaw.w3.org/css-validator/check/referer">Valid CSS</a> | <a href="http://fotogrph.com/">Images</a> | website template by <a href="http://www.araynordesign.co.uk">ARaynorDesign</a>
                    </div><!--close footer-->
                </div><!--close footer_container-->  
            </c:if>
    </body>
</html>

