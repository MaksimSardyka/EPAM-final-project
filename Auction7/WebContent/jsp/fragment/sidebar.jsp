<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<style>
.sidebar form {
margin-bottom:15px;
margin-left:15px;
margin-right:15px;
}
</style>
<%-- Sidebar --%>
<nav class="sidebar categories bg-dark h-100" class="text-light">
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