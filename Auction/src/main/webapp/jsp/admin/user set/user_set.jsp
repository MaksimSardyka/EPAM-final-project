<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/jsp/admin/user set/user_set locale.jsp" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>${user_set_page_title}</title>
<%@include file="/jsp/fragment/head_info.jsp"%>
<%-- Custom styles --%>
<link rel="stylesheet" href="css/style.css">
</head>
<body class="bg-light">
	<%@include file="/jsp/fragment/header/header.jsp"%>
	<div class="container-fluid content">
		<div class="alert alert-secondary">${breadcrumbs_user_set}</div>
		<%@include file="/jsp/fragment/error_message.jsp"%>
		<%@include file="/jsp/fragment/success_message.jsp"%>
		<%@include file="/jsp/fragment/users.jsp"%>
	</div>
	<%@include file="/jsp/fragment/pagination.jsp"%>
	<%@include file="/jsp/fragment/footer/footer.jsp"%>
</body>
</html>