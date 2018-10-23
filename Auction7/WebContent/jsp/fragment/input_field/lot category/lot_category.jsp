<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/jsp/fragment/input_field/lot category/lot_category locale.jsp" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
${lot_category}:
<select name="category_id" required>
	<c:forEach items="${requestScope.proposed_lot_set}" var="category">
		<option value="${category.key}">${category.value}</option>
	</c:forEach>
</select>