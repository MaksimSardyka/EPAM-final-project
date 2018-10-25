<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/jsp/fragment/forms/view lot await approval/view_lot_await_approval locale.jsp" %>
<form method="POST" action="controller">
	<input type="hidden" 
    	name="command" 
        value="view_lot_await_approval" 
        required/>
    	<input type="hidden" name="nonce" value="${nonce}" />
	<input type="submit" 
       	class="submit btn btn-outline-primary btn-block" 
       	value="${view_lot_await_approval}" 
       	name="submit"
        required/>
</form>