<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/jsp/lot set/lot_set locale.jsp" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>${lot_set_page_title}</title>
<%@include file="/jsp/fragment/head_info.jsp"%>
<%-- Custom JS --%>
<script src="js/sidebar.js"></script>
<%-- Custom styles --%>
<link rel="stylesheet" href="css/style.css">
</head>
<body>
    <%@include file="/jsp/fragment/header/header.jsp"%>
	<div class="container-fluid content bg-light">
		<div class="alert alert-secondary">${breadcrumbs_lot_set}</div>
		<div class="container-fluid">
			<div class="row">
				<%@include file="/jsp/fragment/lot card/lot_card.jsp"%>
			</div>
		<%@include file="/jsp/fragment/pagination.jsp"%>
		</div>
	</div>
	<%@include file="/jsp/fragment/footer/footer.jsp"%>
</body>
</html>