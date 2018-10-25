<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/jsp/fragment/forms/view propose lot/view_propose_lot locale.jsp" %>
<form method="POST" action="controller">
	<input type="hidden" 
    	name="command" 
        value="view_propose_lot" 
        required/>
    	<input type="hidden" name="nonce" value="${nonce}" />
    <input type="hidden" 
    	name="auction_id" 
        value="${auction_id}" 
        required/>
	<input type="submit" 
       	class="submit btn btn-outline-primary btn-block" 
       	value="${view_propose_lot}" 
       	name="submit"
        required/>
</form>