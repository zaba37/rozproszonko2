<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%-- 
    Document   : index
    Created on : 2015-11-11, 14:51:38
    Author     : zaba3
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<style type="text/css">
    .tg  {border-collapse:collapse;border-spacing:0; width: 100%}
    .tg td{font-family:Arial, sans-serif;font-size:14px;padding:14px 13px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;}
    .tg th{font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:14px 13px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;}
    .tg .tg-qsvf{font-size:18px;vertical-align:top}
    .tg .tg-2ktp{font-size:16px;vertical-align:top}
    .tg .tg-3iwn{font-weight:bold;font-style:italic;font-size:24px;background-color:#528ccc;color:#ffffff;vertical-align:top}
</style> 

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
                        <h2> </h2>
                    </div><!--close banner_slogan-->	
                    <div id="menubar">
                        <ul id="menu">
                            <li class="current"><a href="index.jsp"><fmt:message key='home'/></a></li>
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
                        <c:if test="${sessionScope.login == 'admin'}">                        
                            <div class="sidebar">
                                <a href="dictionary"><font color="white"><h2><fmt:message key='dictionary'/></h2></font></a>
                                <div class="sidebar_item">

                                </div><!--close sidebar_item--> 
                            </div><!--close sidebar-->  
                        </c:if>
                        <c:if test="${sessionScope.login == 'admin'}">                        
                            <div class="sidebar">
                                <h2><fmt:message key='moderacjaHeader'/></h2>
                                <div class="sidebar_item">
                                    <c:forEach var="ad" items="${add}">
                                        <c:if test="${ad.moderator eq 'm'}">
                                            <p> <a href="mod?${ad.id}"><font color="red">${ad.title}</font></a></p>
                                                </c:if>
                                            </c:forEach>
                                </div><!--close sidebar_item--> 
                            </div><!--close sidebar-->  
                        </c:if>

                        <div class="sidebar">
                            <h2>Latest Blog</h2>
                            <div class="sidebar_item">
                                <h4>March 2012</h4>
                                <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque cursus tempor enim.</p>
                                <div class="button_small">
                                    <a href="#">Read more</a>
                                </div><!--close button_small-->
                            </div><!--close sidebar_item--> 
                        </div><!--close sidebar-->  
                    </div><!--close sidebar_container-->

                    <div id="content">
                        <div class="content_item">
                            <c:forEach var="msg" items="${adminMsg}">
                                <table class="tg">
                                    <tr>
                                        <th class="tg-3iwn"> 
                                            <c:if test="${sessionScope.login == 'admin'}">
                                                <a href="editAdminMsg?${msg.id}"> 
                                                </c:if>
                                                <font color="white">${msg.title}</font> </a> </th>
                                    </tr>
                                    <tr>
                                        <td class="tg-qsvf">${msg.desctyption}</td>
                                    </tr>
                                </table>
                                <h1></h1>
                            </c:forEach>			
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