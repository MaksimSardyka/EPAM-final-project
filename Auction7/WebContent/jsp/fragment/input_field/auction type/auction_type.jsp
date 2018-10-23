<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/jsp/fragment/input_field/auction type/auction_type locale.jsp" %>
${auction_type}:
<select name="type" required>
    <option value="direct" selected>
    	${direct}
   	</option>
	<option value="reverse">
		${reverse}
	</option>
</select>