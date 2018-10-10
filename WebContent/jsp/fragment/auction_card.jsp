<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- Card -->
<c:forEach items="${requestScope.auction_set}" var="auction">
	<div class="col-sm-6 col-md-4 col-xl-3">
		<div class="card mb-4 shadow-sm">
			<!-- mb-4 = отступ снизу  -->
			<img class="card-img-top" src="http://placehold.it/500x500"
				alt="Card image cap">
			<div class="card-body">
				<p class="card-text">${auction.startTime}-${auction.finishTime}</p>
				<form method="POST" action="controller">
					<input type="hidden" name="command" value="lot_set_page"> <input
						type="hidden" name="auction_id" value="${auction.id}"> <input
						type="submit" class="btn btn-outline-primary btn-block"
						value="Show auction">
				</form>
			</div>
		</div>
	</div>
</c:forEach>
<!-- /Card -->