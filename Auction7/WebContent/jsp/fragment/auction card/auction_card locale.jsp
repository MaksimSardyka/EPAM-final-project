<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>	  
    <fmt:setLocale value="${sessionScope.locale}" />
    <fmt:setBundle basename="resources.localization" var="loc" />
    <fmt:message bundle="${loc}" key="localization.since" var="since" />
    <fmt:message bundle="${loc}" key="localization.untill" var="untill" />
    <fmt:message bundle="${loc}" key="localization.show_auction" var="show_auction" />