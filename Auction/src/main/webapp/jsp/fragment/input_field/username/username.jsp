<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/jsp/fragment/input_field/username/username_locale.jsp" %>
<input type="text" 
	name="username" 
    placeholder="${username}" 
    pattern="^[A-Za-z0-9_]{6,40}$"
    maxlength="40"
    title="Username(6-40 symbols) may contain A-Z a-z 0-9 _"
    autocomplete="off"
    required/>