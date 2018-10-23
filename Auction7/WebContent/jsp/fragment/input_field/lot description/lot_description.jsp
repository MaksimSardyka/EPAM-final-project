<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/jsp/fragment/input_field/lot description/lot_description locale.jsp" %>
<textarea name="lot_description"
	rows="5"
	maxlength="200" 
    placeholder="${lot_description}" 
    autocomplete="off"
    required>
</textarea>