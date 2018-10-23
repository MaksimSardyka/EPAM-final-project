<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>	  
    <fmt:setLocale value="${sessionScope.locale}" />
    <fmt:setBundle basename="resources.localization" var="loc" />
    <fmt:message bundle="${loc}" key="localization.language"
	  var="language" />
	<fmt:message bundle="${loc}" key="localization.english_auction"
	  var="english_auction" />