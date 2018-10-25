<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/jsp/fragment/forms/pick language/pick_language locale.jsp" %>
<script type="text/javascript">
	$(function() {
		$('#change_language_form select[name=language]').change(function() {
			this.form.submit();
		});
	});
</script>
<form method="POST" action="controller" id="change_language_form">
	<input type="hidden" name="command" value="change_language">
    	<input type="hidden" name="nonce" value="${nonce}" />
	<select class="btn w-100" name="language" required> 
		<option disabled selected value="">${pick_language}:</option>
		<option value="en_US">English</option>
		<option value="ru_RU">Русский</option>
		<option value="de_DE">Deutsch</option>
	</select>
</form>