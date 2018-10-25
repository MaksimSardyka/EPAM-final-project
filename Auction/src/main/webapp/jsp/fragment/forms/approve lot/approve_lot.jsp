<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/jsp/fragment/forms/approve lot/approve_lot locale.jsp" %>
	<form method="POST" action="controller">
		<input type="hidden" name="command" value="approve_lot" required/>
    	<input type="hidden" name="nonce" value="${nonce}" />
		<input type="hidden" name="lot_id" value="${lot.id}" required/>
		<input type="submit" class="btn btn-success" value="${approve_lot}" required/>
	</form>