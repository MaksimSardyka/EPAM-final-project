<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>${lot.name}</title>
<%@include file="/jsp/fragment/head_info.jsp"%>
<%-- Custom styles --%>
<link rel="stylesheet" href="css/form.css">
<link rel="stylesheet" href="css/style.css">
</head>
<body>
	<%@include file="/jsp/fragment/header/header.jsp"%>
	<div class="content">
		<div class="alert alert-secondary">${lot.category}</div>
		<%@include file="/jsp/fragment/error_message.jsp"%>
		<%@include file="/jsp/fragment/success_message.jsp"%>
		 <%@include file="/jsp/fragment/lot data/lot_data.jsp"%>
	</div>
	<%@include file="/jsp/fragment/footer/footer.jsp"%>
</body>
</html>