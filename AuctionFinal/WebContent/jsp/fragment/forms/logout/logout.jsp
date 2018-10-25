<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/jsp/fragment/forms/logout/logout locale.jsp" %>
<!-- Logout button -->
<form method="POST" action="controller" id="logout_form">
	<input type="hidden" name="command" value="log_out" required/>
    	<input type="hidden" name="nonce" value="${nonce}" />
</form>
<button form="logout_form" class="btn btn-primary btn-block" name="submit">
	${log_out}
</button>
<!-- /Logout button -->