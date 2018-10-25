<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%-- User --%>
<c:forEach items="${requestScope.user_set}" var="user">
	<div class="alert alert-info">
		<div class="row">
			<div class="col-sm"><c:out value="${user.login}"></c:out></div>
			<div class="col-sm"><c:out value="${user.email}"></c:out></div>
			<div class="col-sm"><c:out value="${user.money}"></c:out></div>
			<div class="col-sm"><c:out value="${user.frozenMoney}"></c:out></div>
			<div class="col-sm">
				<c:choose>
					<c:when test="${not user.blocked}">
						<%@include file="/jsp/fragment/forms/block user/block_user.jsp"%>
					</c:when>
					<c:otherwise>
						<%@include file="/jsp/fragment/forms/unblock user/unblock_user.jsp"%>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</div>
</c:forEach>
<%-- /User --%>