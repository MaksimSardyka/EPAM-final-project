<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>	  
    <fmt:setLocale value="${sessionScope.locale}" />
    <fmt:setBundle basename="resources.localization" var="loc" />
    <fmt:message bundle="${loc}" key="localization.user_data"
	  var="user_data" />
	<fmt:message bundle="${loc}" key="localization.update_user_data"
	  var="update_user_data" />
	<fmt:message bundle="${loc}" key="localization.manage_funds"
	  var="manage_funds" />