<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/jsp/fragment/forms/unblock user/unblock_user locale.jsp" %>
<form method="POST" action="controller">
    	<input type="hidden" name="command" value="unblock_user"/>
    	<input type="hidden" name="user_id" value="${user.id}"/>
        <input type="submit"  
            class="btn btn-danger"
            value="${unblock_user}" 
            name="unblock"
            required/>
</form>