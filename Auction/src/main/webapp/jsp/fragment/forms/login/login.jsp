<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/jsp/fragment/forms/login/login locale.jsp" %>
    <link rel="stylesheet" href="css/input-area.css">
    <form method="POST" action="controller" class="input-area" name="login_form">
        <input type="hidden" 
            name="command" 
            value="log_in" 
            required/>
    	<input type="hidden" name="nonce" value="${nonce}" />
        <%@include file="/jsp/fragment/input_field/username/username.jsp"%>
        <%@include file="/jsp/fragment/input_field/password/password.jsp"%>
        <input type="submit" 
            class="submit bg-info text-light" 
            value="${login}" 
            name="login"
            required/>
    </form>