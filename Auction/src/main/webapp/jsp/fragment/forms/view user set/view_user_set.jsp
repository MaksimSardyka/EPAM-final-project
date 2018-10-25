<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/jsp/fragment/forms/view user set/view_user_set locale.jsp" %>
	<form method="POST" action="controller">
		<input type="hidden" 
        	name="command" 
           	value="view_user_set" 
            required/>
    	<input type="hidden" name="nonce" value="${nonce}" />
            <input type="hidden" name="page_number" value="0">
        <input type="submit" 
           	class="submit btn btn-outline-primary btn-block" 
           	value="${view_user_set}"
            name="submit"
            required/>
        </form>