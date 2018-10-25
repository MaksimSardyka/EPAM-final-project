<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>	  
    <fmt:setLocale value="${sessionScope.locale}" />
    <fmt:setBundle basename="resources.localization" var="loc" />
    <fmt:message bundle="${loc}" key="localization.auction_type"
	  var="auction_type" />
    <fmt:message bundle="${loc}" key="localization.direct"
	  var="direct" />
	<fmt:message bundle="${loc}" key="localization.reverse"
	  var="reverse" />