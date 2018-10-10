<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
        <!-- Custom styles -->
        <!--<link rel="stylesheet" href="css/loginPage/tab_content.css"> CONTAINS display:none -->
        <link rel="stylesheet" href="css/loginPage/tab_wrapper.css">
        <link rel="stylesheet" href="css/alert.css">
        <!-- Login-Register form -->
        <div class="container-fluid h-100 bg-light">
            <div class="row h-100 justify-content-center align-items-center">
                <section class="tab_wrapper">
                    <!-- Tabs content -->
                    <ul class="tab_content">
                        <li class="content_wrapper bg-dark">            
                            <%@include file="/jsp/fragment/admin/create_auction_form.jsp"%>
                        </li>
                    </ul>
                    <!-- /Tabs content -->
                    <c:if test="${not empty errorMessage}">
                        <div class="alert alert-danger"><p>${errorMessage}</p></div>
                    </c:if>  
                </section>
            </div>
        </div>