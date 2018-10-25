<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/jsp/fragment/auction card/auction_card locale.jsp" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://epam.by/auction/functions" prefix="f" %>
<%-- Card --%>
<c:forEach items="${requestScope.auction_set}" var="auction">
	<div class="col-sm-6 col-md-4 col-xl-3">
		<div class="card mb-4 shadow-sm">
			<img class="card-img-top" src="http://placehold.it/500x500"
				alt="Card image cap">
			<div class="card-body">
				<table>
					<tr>
						<td>${since}:</td>
						<td>${f:formatLocalDateTime(auction.startTime, 'HH:mm dd.MM.yyyy')}</td>
					</tr>
					<tr>
						<td>${untill}:</td>
						<td>${f:formatLocalDateTime(auction.finishTime, 'HH:mm dd.MM.yyyy')}</td>
					</tr>
				</table>
				<form method="POST" action="controller">
					<input type="hidden" name="command" value="view_lot_set">
    				<input type="hidden" name="nonce" value="${nonce}" />
					<input type="hidden" name="auction_id" value="${auction.id}">
					<input type="hidden" name="page_number" value="0">
					<input type="submit" class="btn btn-outline-primary btn-block" value="${show_auction}">
				</form>
			</div>
		</div>
	</div>
</c:forEach>
<%-- /Card --%>