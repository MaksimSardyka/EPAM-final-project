<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>	  
    <fmt:setLocale value="${sessionScope.locale}" />
    <fmt:setBundle basename="resources.localization" var="loc" />
	<fmt:message bundle="${loc}" key="localization.lot_set_page_title"
	  var="lot_set_page_title" />
    <fmt:message bundle="${loc}" key="localization.breadcrumbs_lot_set"
	  var="breadcrumbs_lot_set" />