<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>	  
    <fmt:setLocale value="${sessionScope.locale}" />
    <fmt:setBundle basename="resources.localization" var="loc" />
    <fmt:message bundle="${loc}" key="localization.label_your_account"
	  var="label_your_account" />
	<fmt:message bundle="${loc}" key="localization.active"
	  var="active" />
	<fmt:message bundle="${loc}" key="localization.blocked"
	  var="blocked" />
    <fmt:message bundle="${loc}" key="localization.label_username"
	  var="label_username" />
	<fmt:message bundle="${loc}" key="localization.label_email"
	  var="label_email" />
	<fmt:message bundle="${loc}" key="localization.label_role"
	  var="label_role" />
	<fmt:message bundle="${loc}" key="localization.label_money"
	  var="label_money" />
	<fmt:message bundle="${loc}" key="localization.label_frozen_money"
	  var="label_frozen_money" />