<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:if test="${not empty successMessage}">
	<div class="alert alert-success">
		<p>${successMessage}</p>
	</div>
</c:if>
