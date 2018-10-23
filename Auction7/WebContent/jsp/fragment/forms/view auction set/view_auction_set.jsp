<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/jsp/fragment/forms/view auction set/view_auction_set locale.jsp" %>
<!-- View auction set button -->
<form method="POST" action="controller" id="view_auction_set_form">
	<input type="hidden" name="command" value="view_auction_set" required/>
</form>
<button form="view_auction_set_form" class="btn btn-primary btn-block" name="submit">
	${view_auction_set}
</button>
<!-- /View auction set button -->