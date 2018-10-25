<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/jsp/fragment/forms/unapprove lot/unapprove_lot locale.jsp" %>
	<form method="POST" action="controller">
		<input type="hidden" name="command" value="unapprove_lot" required/>
    	<input type="hidden" name="nonce" value="${nonce}" />
		<input type="hidden" name="lot_id" value="${lot.id}" required/>
		<input type="submit" class="btn btn-danger" value="${unapprove_lot}" required>
	</form>