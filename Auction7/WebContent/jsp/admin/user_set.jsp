<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Auctions</title>
<%@include file="/jsp/fragment/head_info.jsp"%>
<!-- Custom styles -->
<link rel="stylesheet" href="css/style.css">
</head>
<body class="bg-light">
	<%@include file="/jsp/fragment/header/header.jsp"%>
	<div class="container-fluid content">
		<div class="alert alert-secondary">Home / Users</div>
		<%@include file="/jsp/fragment/users.jsp"%>
	</div>
	<%@include file="/jsp/fragment/pagination.jsp"%>
	<%@include file="/jsp/fragment/footer/footer.jsp"%>
</body>
</html>