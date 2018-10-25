<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/jsp/auction set/auction_set locale.jsp"%>
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
		<div class="row">
			<c:if test="${user.role == 'ADMINISTRATOR'}">
				<div class="col-md-2">
					<div class="collapse navbar-collapse show"
						id="collapsibleNavbar">
						<%@include file="/jsp/fragment/sidebar.jsp"%>
					</div>
				</div>
			</c:if>
			<div class="col-md px-0">
			<div class="alert alert-secondary">${breadcrumbs_auction_set}</div>
				<%@include file="/jsp/fragment/error_message.jsp"%>
				<%@include file="/jsp/fragment/success_message.jsp"%>
				<div class="container-fluid">
					<div class="row">
						<%@include file="/jsp/fragment/auction card/auction_card.jsp"%>
					</div>
					<%@include file="/jsp/fragment/pagination.jsp"%>
				</div>
			</div>
		</div>
	</div>
	<%@include file="/jsp/fragment/footer/footer.jsp"%>
</body>
</html>