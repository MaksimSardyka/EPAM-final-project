<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="col-sm-2"> 
	<div class="col">
		<img src="http://placehold.it/360x600" class="img-fluid" />
	</div>
	<div class="col">
		<img src="http://placehold.it/360x600" class="img-fluid" />
	</div>
</div>

<div class="col">
	<img src="http://placehold.it/360x600" class="img-fluid" />  
</div>
 
<div class="col-sm-6">	 
	<h6 class="text-uppercase text-black-40">  /${lot.category}  </h6>
	<h2>${lot.name}</h2>
	<p>${lot.description}</p>
	
	<c:choose>
		<c:when test="${lot.auction.type == 'DIRECT' }">
                        <p>Прямой аукцион</p> 
		</c:when>
		<c:otherwise>
                        <p>Обратный аукцион </p>
		</c:otherwise>
	</c:choose>
	<p>Auction starts at: ${lot.auction.startTime}</p>
	<p>Auction finishes at: ${lot.auction.finishTime}</p>
	<form method="POST" class="input-area" action="controller">
		<input type="hidden" name="command" value="bid" />
		<input type="hidden" name="lot_id" value="${lot.id}" /> 
		<input type="number" step="0.01" name="amount" min="${lot.price}" class="bg-light" value="${lot.price}" /> 
		<input type="submit" value="Сделать ставку" />
	</form>
</div>