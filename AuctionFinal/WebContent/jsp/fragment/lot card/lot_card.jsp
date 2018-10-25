<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/jsp/fragment/lot card/lot_card locale.jsp" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- Card -->
<c:forEach items="${requestScope.lot_set}" var="lot">
	<div class="col-sm-6 col-md-4 col-xl-3">
		<div class="card mb-4 shadow-sm">
			<img class="card-img-top" src="http://placehold.it/500x500"
				alt="Card image cap">
			<div class="card-body">
				<p class="card-text">${lot.name}</p>
				<form method="POST" action="controller">
					<input type="hidden" name="command" value="view_lot">
    				<input type="hidden" name="nonce" value="${nonce}" />
					<input type="hidden" name="lot_id" value="${lot.id}"> 
					<input type="submit" class="btn btn-outline-primary btn-block" value="${show_lot}">
				</form>
			</div>
		</div>
	</div>
</c:forEach>
<!-- /Card -->