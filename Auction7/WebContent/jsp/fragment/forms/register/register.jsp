<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/jsp/fragment/forms/register/register locale.jsp" %>
<link rel="stylesheet" href="css/input-area.css">
<script src="js/checkRepeatPasswordMatch.js"></script>

<form method="POST" class="input-area" action="controller" onsubmit="return checkRepeatPasswordMatch(this)">
        <input type="hidden" name="command" value="register" required/>
        <%@include file="/jsp/fragment/input_field/username/username.jsp"%>
        <%@include file="/jsp/fragment/input_field/email/email.jsp"%>
        <%@include file="/jsp/fragment/input_field/password/password.jsp"%>
        <%@include file="/jsp/fragment/input_field/repeat password/repeat_password.jsp"%>
        <input type="submit" 
            class="submit bg-info text-light" 
            value="${register}" 
            name="register"
            required/>
    </form>