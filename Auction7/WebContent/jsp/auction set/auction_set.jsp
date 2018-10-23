<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/jsp/auction set/auction_set locale.jsp" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>${auction_set_page_title}</title>
<%@include file="/jsp/fragment/head_info.jsp"%>
</head>
<body class="bg-light">
	<%@include file="/jsp/fragment/header/header.jsp"%>
	<div class="container-fluid content">
		<div class="row mr-0">
			<div class="col-md-2 pr-0">
				<div class="collapse navbar-collapse show h-100" id="collapsibleNavbar">
					<%@include file="/jsp/fragment/sidebar.jsp"%>
				</div>
			</div>
			<div class="col-md-10 px-0">
				<div class="alert alert-secondary">${breadcrumbs_auction_set}</div>
				<div class="row mx-0">
					<%@include file="/jsp/fragment/auction card/auction_card.jsp"%>
				</div>
				<%@include file="/jsp/fragment/pagination.jsp"%>
			</div>
		</div>
	</div>
	<%@include file="/jsp/fragment/footer/footer.jsp"%>
</body>
</html>