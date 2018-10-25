<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/jsp/fragment/forms/view add auction/view_add_auction locale.jsp" %>
<form method="POST" action="controller">
	<input type="hidden" 
    	name="command" 
        value="view_add_auction" 
        required/>
    	<input type="hidden" name="nonce" value="${nonce}" />
	<input type="submit" 
       	class="submit btn btn-outline-primary btn-block" 
       	value="${view_add_auction}" 
       	name="submit"
        required/>
</form>