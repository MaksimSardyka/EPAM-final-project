<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/jsp/user/propose_lot locale.jsp" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>${propose_lot_page_title}</title>
<%@include file="/jsp/fragment/head_info.jsp"%>
<%-- Custom styles --%>
<link rel="stylesheet" href="css/style.css">
</head>
<body class="bg-light">
<%@include file="/jsp/fragment/header/header.jsp"%>
<section class="container-fluid content">
		<%@include file="/jsp/fragment/error_message.jsp"%>
		<%@include file="/jsp/fragment/success_message.jsp"%>
            <div class="row text-light">
                <div class="col-sm-10 offset-sm-1 text-center">
                    <div class="info-form">
						<div class="row justify-content-center py-3">
							<%@include file="/jsp/fragment/forms/propose lot/propose_lot.jsp"%>
						</div>
					</div>
				</div>
			</div>
</section>
<%@include file="/jsp/fragment/footer/footer.jsp"%>
</body>
</html>