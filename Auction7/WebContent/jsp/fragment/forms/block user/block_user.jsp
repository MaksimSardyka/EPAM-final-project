<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/jsp/fragment/forms/block user/block_user locale.jsp" %>
<form method="POST" action="controller">
    	<input type="hidden" name="command" value="block_user"/>
    	<input type="hidden" name="user_id" value="${user.id}"/>
        <input type="submit" 
            class="btn btn-success" 
            value="${block_user}" 
            name="block"
            required/>
</form>