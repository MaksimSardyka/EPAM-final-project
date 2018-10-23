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
	<div class="container-fluid h-100 bg-light content">
			<div class="row h-100 mx-0 justify-content-center align-items-center">
			<%@include file="/jsp/fragment/forms/propose lot/propose_lot.jsp"%>
		</div>
	</div>
	<%@include file="/jsp/fragment/footer/footer.jsp"%>
</body>
</html>