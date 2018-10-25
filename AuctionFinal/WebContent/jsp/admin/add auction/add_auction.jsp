<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/jsp/admin/add auction/add_auction locale.jsp" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>${add_auction_page_title}</title>
<%@include file="/jsp/fragment/head_info.jsp"%>
<%-- Custom styles --%>
<link rel="stylesheet" href="css/style.css">
</head>
<body>
	<%@include file="/jsp/fragment/header/header.jsp"%>
	<div class="container-fluid h-100 bg-light content">
		<div class="alert alert-secondary">${breadcrumbs_add_auction}</div>
		<%@include file="/jsp/fragment/error_message.jsp"%>
		<%@include file="/jsp/fragment/success_message.jsp"%>
			<div class="row mx-0 justify-content-center align-items-center text-light">
				<%@include file="/jsp/fragment/forms/create auction/create_auction.jsp"%>
			</div>
	</div>
	<%@include file="/jsp/fragment/footer/footer.jsp"%>
</body>
</html>