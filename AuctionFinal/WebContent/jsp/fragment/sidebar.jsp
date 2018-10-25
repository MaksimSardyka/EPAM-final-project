<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%-- Sidebar --%>
<nav class="sidebar categories h-100 py-5 px-4">
	<c:if test="${not empty user}">
		<c:if test="${user.role == 'ADMINISTRATOR'}">
			<%@include file="/jsp/fragment/forms/view lot await approval/view_lot_await_approval.jsp"%>
			<c:if test="${empty requestScope.user_set}">
				<%@include file="/jsp/fragment/forms/view user set/view_user_set.jsp"%>
			</c:if>
			<%@include file="/jsp/fragment/forms/view add auction/view_add_auction.jsp"%>
		</c:if>
		<c:if test="${user.role == 'USER'}">
			<%@include file="/jsp/fragment/forms/view propose lot/view_propose_lot.jsp"%>
		</c:if>
	</c:if>
</nav>
<%-- /Sidebar --%>