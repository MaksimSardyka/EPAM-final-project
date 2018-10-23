<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/jsp/fragment/forms/place bid/place_bid locale.jsp" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<link rel="stylesheet" href="css/input-area.css">
	<form method="POST" class="input-area" action="controller">
		<input type="hidden" name="command" value="bid" />
			<input type="hidden" name="lot_id" value="${lot.id}" />
			<c:choose>
				<c:when test="${lot.auctionType == 'DIRECT' }">
           			<input type="number" step="0.01" name="amount" min="${lot.bidPrice + 0.01}" class="bg-light" value="${lot.bidPrice}" /> 
				</c:when>
				<c:when test="${lot.auctionType == 'REVERSE' }">
            		<input type="number" step="0.01" name="amount" max="${lot.bidPrice - 0.01}" class="bg-light" value="${lot.bidPrice}" />  
				</c:when>
			</c:choose>
			<input type="submit" class="btn btn-success" value="${place_bid}" />
	</form>