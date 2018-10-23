<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/jsp/fragment/input_field/password/password locale.jsp" %>
<input type="password" 
	name="password" 
    placeholder="${password}" 
    pattern="(?=^.{6,40}$)((?=.*\d)|(?=.*\W+))(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*$" 
    maxlength="40"
    title="Password(6-40 symbols) should contain UpperCase, LowerCase, Number/SpecialChar"
    autocomplete="off"
    required/>