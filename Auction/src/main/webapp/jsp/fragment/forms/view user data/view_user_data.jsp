<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/jsp/fragment/forms/view user data/view_user_data locale.jsp" %>
<form method="POST" action="controller" id="view_user_data_form">
	<input type="hidden" name="command" value="view_user_data" required />
    	<input type="hidden" name="nonce" value="${nonce}" />
</form>
<button form="view_user_data_form" class="btn btn-primary btn-block" name="submit">
	${view_user_data}
</button>