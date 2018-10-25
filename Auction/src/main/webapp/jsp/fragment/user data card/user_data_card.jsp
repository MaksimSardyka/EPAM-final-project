<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/jsp/fragment/user data card/user_data_card locale.jsp" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<h3>${label_your_account}: 
<c:choose>
	<c:when test="${user.blocked}">
		<span class="badge badge-danger">${blocked}</span>
	</c:when>
	<c:otherwise>
		<span class="badge badge-success">${active}</span>
	</c:otherwise>
</c:choose>
</h3>
<table class="table-hover text-left mx-auto">
	<tr>
		<td>${label_username}:</td>
		<td>${user.login}</td>
	</tr>
	<tr>
		<td>${label_email}:</td>
		<td>${user.email}</td>
	</tr>
	<tr>
		<td>${label_role}:</td>
		<td>${user.role}</td>
	</tr>
	<tr>
		<td>${label_money}:</td>
		<td>${user.money}</td>
	</tr>
	<tr>
		<td>${label_frozen_money}:</td>
		<td>${user.frozenMoney}</td>
	</tr>
</table>