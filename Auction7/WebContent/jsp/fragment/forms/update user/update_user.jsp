<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/jsp/fragment/forms/update user/update_user locale.jsp" %>
<link rel="stylesheet" href="css/input-area.css">
<script src="js/checkRepeatPasswordMatch.js"></script>
<form method="POST" class="input-area bg-dark" action="controller" onsubmit="return checkRepeatPasswordMatch(this)">
    	<input type="hidden" name="command" value="update_user"/>
        <%@include file="/jsp/fragment/input_field/username/username.jsp"%>
		<%@include file="/jsp/fragment/input_field/email/email.jsp"%>
		<%@include file="/jsp/fragment/input_field/password/password.jsp"%>
		<%@include file="/jsp/fragment/input_field/repeat password/repeat_password.jsp"%>
        <input type="submit" 
            class="submit bg-info text-light" 
            value="${update_user}" 
            name="update"
            required/>
</form>