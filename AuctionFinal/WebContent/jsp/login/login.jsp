<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/jsp/login/login locale.jsp" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
    <head>
        <title>${login_page_title}</title>
        <%@include file="/jsp/fragment/head_info.jsp"%>
        <!-- Custom styles -->
        <link rel="stylesheet" href="css/loginPage/tabs.css">
        <link rel="stylesheet" href="css/loginPage/tab_content.css">
        <link rel="stylesheet" href="css/loginPage/tab_wrapper.css">
        <link rel="stylesheet" href="css/alert.css">
        <script src="js/login.js"></script>
    </head>
    <body>
    <%@include file="/jsp/fragment/header/header.jsp"%>
        <!-- Login-Register form -->
        <div class="container-fluid content h-100 bg-light row h-100 justify-content-center align-items-center">
                <section class="tab_wrapper">
                    <!-- Tabs links -->
                    <ul class="tabs">
                        <li class="active">${login}</li>
                        <li>${register}</li>
                    </ul>
                    <!-- /Tabs links -->
                    <!-- Tabs content -->
                    <ul class="tab_content">
                        <li class="active content_wrapper bg-dark">            
                            <%@include file="/jsp/fragment/forms/login/login.jsp"%>
                        </li>
                        <li class="content_wrapper bg-dark">
                            <%@include file="/jsp/fragment/forms/register/register.jsp"%>
                        </li>
                    </ul>
                    <!-- /Tabs content -->
					<%@include file="/jsp/fragment/error_message.jsp"%>
					<%@include file="/jsp/fragment/success_message.jsp"%>
                </section>
        </div>
        <%@include file="/jsp/fragment/footer/footer.jsp"%>
    </body>
</html>