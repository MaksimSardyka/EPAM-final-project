<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/jsp/fragment/input_field/auction description/auction_description locale.jsp" %>
<input type="text" 
	name="description"
    placeholder="${auction_description}" 
    maxlength="256" 
    autocomplete="off"
    required/>