<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/jsp/user data/user_data locale.jsp" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>${user.login}</title>
<%@include file="/jsp/fragment/head_info.jsp"%>
<link rel="stylesheet" href="css/alert.css">
</head>

<body>
	<%@include file="/jsp/fragment/header/header.jsp"%>
	<div class="content">
		<%@include file="/jsp/fragment/error_message.jsp"%>
		<%@include file="/jsp/fragment/success_message.jsp"%>
		<div id="accordion">
			<div class="card">
				<button class="btn btn-link text-dark card-header"
					data-toggle="collapse" data-target="#collapseOne"
					aria-expanded="true" aria-controls="collapseOne">
					${user_data}</button>
				<div id="collapseOne" class="collapse show" data-parent="#accordion">
					<div class="card-body text-center">
						<%@include file="/jsp/fragment/user data card/user_data_card.jsp"%>
					</div>
				</div>
			</div>
			<div class="card">
				<button class="btn btn-link text-dark collapsed card-header"
					data-toggle="collapse" data-target="#collapseTwo"
					aria-expanded="false" aria-controls="collapseTwo">
					${update_user_data}</button>
				<div id="collapseTwo" class="collapse" data-parent="#accordion">
					<div
						class="row card-body justify-content-center align-items-center mx-0">
						<%@include file="/jsp/fragment/forms/update user/update_user.jsp"%>
					</div>
				</div>
			</div>
			<div class="card">
				<button class="btn btn-link text-dark collapsed card-header"
					data-toggle="collapse" data-target="#collapseThree"
					aria-expanded="false" aria-controls="collapseThree">
					${manage_funds}</button>
				<div id="collapseThree" class="collapse"
					aria-labelledby="headingThree" data-parent="#accordion">
					<div class="card-body row justify-content-around mx-0">
						<div class="col-sm-4 mb-3">
							<%@include file="/jsp/fragment/forms/add funds/add_funds.jsp"%>
						</div>
						<div class="col-sm-4 mb-3">
							<%@include file="/jsp/fragment/forms/withdraw funds/withdraw_funds.jsp"%>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@include file="/jsp/fragment/footer/footer.jsp"%>
</body>
</html>