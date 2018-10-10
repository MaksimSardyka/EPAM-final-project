<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Auctions</title>
<%@include file="/jsp/fragment/head_info.jsp"%>
<!-- Custom styles -->
<link rel="stylesheet" href="css/main/main.css">
</head>
<body class="bg-light">
	<%@include file="/jsp/fragment/header.jsp"%>
	<%@include file="/jsp/fragment/breadcrumbs.jsp"%>
	<div class="container-fluid content row">
			<%@include file="/jsp/fragment/auction_card.jsp"%>
	</div>
	<%@include file="/jsp/fragment/pagination.jsp"%>
	<%@include file="/jsp/fragment/footer.jsp"%>
</body>
</html>