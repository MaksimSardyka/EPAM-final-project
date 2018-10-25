<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/jsp/fragment/forms/create auction/create_auction locale.jsp" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <link rel="stylesheet" href="css/input-area.css">
    <form method="POST" class="input-area bg-dark" action="controller">
        <input type="hidden" name="command" value="create_auction"/>
    	<input type="hidden" name="nonce" value="${nonce}" />
		<%@include file="/jsp/fragment/input_field/start time/start_time.jsp"%>
		<%@include file="/jsp/fragment/input_field/finish time/finish_time.jsp"%>
        <%@include file="/jsp/fragment/input_field/auction description/auction_description.jsp"%>
        <%@include file="/jsp/fragment/input_field/auction type/auction_type.jsp"%>
        <input type="submit" 
            class="submit bg-info text-light" 
            value="${create_auction}" 
            name="create"
            required/>
    </form>
    <c:if test="${not empty errorMessage}">
    	<div class="alert alert-danger"><p>${errorMessage}</p></div>
    </c:if> 