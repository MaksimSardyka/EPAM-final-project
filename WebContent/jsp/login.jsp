<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Login page</title>
        <%@include file="/jsp/fragment/head_info.jsp"%>
        <!-- Custom styles -->
        <link rel="stylesheet" href="css/loginPage/tabs.css">
        <link rel="stylesheet" href="css/loginPage/tab_content.css">
        <link rel="stylesheet" href="css/loginPage/tab_wrapper.css">
        <link rel="stylesheet" href="css/alert.css">
        <link rel="stylesheet" href="css/style.css">
        <script src="js/login.js"></script>
        <!-- Validation
        <script src="js/validateLogin.js"></script>
        <link rel="stylesheet" href="css/nonvalid.css">
        -->
    </head>
    <body>
    <%@include file="/jsp/fragment/login_header.jsp"%>
        <!-- Login-Register form -->
        <div class="container-fluid content h-100 bg-light row h-100 justify-content-center align-items-center">
                <section class="tab_wrapper">
                    <!-- Tabs links -->
                    <ul class="tabs">
                        <li class="active">Login</li>
                        <li>Register</li>
                    </ul>
                    <!-- /Tabs links -->
                    <!-- Tabs content -->
                    <ul class="tab_content">
                        <li class="active content_wrapper bg-dark">            
                            <%@include file="/jsp/fragment/login_form.jsp"%>
                        </li>
                        <li class="content_wrapper bg-dark">
                            <%@include file="/jsp/fragment/register_form.jsp"%>
                        </li>
                    </ul>
                    <!-- /Tabs content -->
                    <c:if test="${not empty errorMessage}">
                        <div class="alert alert-danger"><p>${errorMessage}</p></div>
                    </c:if>  
                </section>
        </div>
        <%@include file="/jsp/fragment/footer.jsp"%>
    </body>
</html>