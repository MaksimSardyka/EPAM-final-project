<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Administrator panel</title>
<%@include file="/jsp/fragment/head_info.jsp"%>
<!-- Custom styles -->
<link rel="stylesheet" href="css/style.css">
</head>
<body>
	<%@include file="/jsp/fragment/header.jsp"%>
	<div class="container-fluid content">
		<!-- Nav pills -->
		<ul class="nav nav-pills nav-justified">
			<li class="nav-item">
			     <a class="nav-link active" data-toggle="pill" href="#home">Create auction</a>
			</li>
			<li class="nav-item">
			     <a class="nav-link" data-toggle="pill" href="#menu1">Auctions</a>
		   </li>
		   <li class="nav-item">
			     <a class="nav-link" data-toggle="pill" href="#menu2">Users</a>
		   </li>
		</ul>
		<!-- /Nav pills -->
		<!-- Tab panes -->
			<div class="tab-pane active" id="home">
				<%@include file="/jsp/fragment/admin/create_auction.jsp"%>
			</div>
			<div class="tab-pane container fade" id="menu1">2</div>
			<div class="tab-pane container fade" id="menu2">3</div>
		<!-- /Tab panes -->

		Welcome ${user.role} ${user.login}
	</div>
	<%@include file="/jsp/fragment/footer.jsp"%>
</body>
</html>