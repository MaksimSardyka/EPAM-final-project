<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/jsp/fragment/input_field/start price/start_price locale.jsp" %>
	${start_price}:
    <input type="number"
    	autocomplete="off" 
    	placeholder="0.00" 
    	step="0.01" 
    	min="0.01"
    	name="amount"
    	required/>