<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/jsp/fragment/lot data/lot_data locale.jsp" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://epam.by/auction/functions" prefix="f" %>
<%@taglib uri="http://epam.by/auction/tag" prefix="ctg" %>
<link rel="stylesheet" href="css/carousel.css">
<script type="text/javascript">
$(function() {
	  $('.carousel-inner .carousel-item:first').addClass('active');
	});
</script>

<div class="row align-items-center mx-0">
<%-- Image carousel --%>
	<div id="lot_image" class="carousel slide col-sm" data-ride="carousel">
	<%-- The slideshow --%>
  		<div class="carousel-inner h-100">
  			<c:forEach items="${lot.pictureSet}" var="picture">
  			    <div class="carousel-item">
      				<img src="data/${picture}" alt="Lot image" width="1000" height="500">
    			</div>
			</c:forEach>
  		</div>
	<%-- /The slideshow --%>
  	<%-- Left and right controls --%>
  		<a class="carousel-control-prev" href="#lot_image" data-slide="prev">
    		<span class="carousel-control-prev-icon"></span>
  		</a>
  		<a class="carousel-control-next" href="#lot_image" data-slide="next">
    		<span class="carousel-control-next-icon"></span>
  		</a>
	<%-- /Left and right controls --%>
</div>
<%-- /Image carousel --%>

<div class="col-sm">
	<h1 class="mx-auto"><c:out value="${lot.name}"></c:out></h1>
	<p>Lot proposed by: <c:out value="${lot.ownerLogin}"></c:out></p>
	${lot_description}:
	<p><c:out value="${lot.description}"></c:out></p>

	${auction_type}:
	<c:choose>
		<c:when test="${lot.auctionType == 'DIRECT'}">
        	<p>${direct}</p> 
		</c:when>
		<c:when test="${lot.auctionType == 'REVERSE'}">
            <p>${reverse}</p> 
		</c:when>
	</c:choose>
	
		<c:if test="${lot.approved}">
			<script>
				// Set the date we're counting down to
				distance = <ctg:finishes start="${lot.startTime}" finish="${lot.finishTime}" />;

				// Update the count down every 1 second
				var countdownfunction = setInterval(function() {
    				// count down on 1 second
    				distance = distance - 1000;
    
    				// Time calculations for days, hours, minutes and seconds
    				var days = Math.floor(distance / (1000 * 60 * 60 * 24));
    				var hours = Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
    				var minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
    				var seconds = Math.floor((distance % (1000 * 60)) / 1000);
    
    				// Output the result in an element with id="countdown"
    				document.getElementById("countdown").innerHTML = days + "d " + hours + "h " + minutes + "m " + seconds + "s ";
    
    				// If the count down is over, write some text 
    				if (distance < 0) {
        				clearInterval(countdownfunction);
        				document.getElementById("countdown").innerHTML = "EXPIRED";
    				}
				}, 1000);
			</script>
		
			<p>${start_time}: ${f:formatLocalDateTime(lot.startTime, 'HH:mm dd.MM.yyyy')}</p>
			<p>${finish_time}: ${f:formatLocalDateTime(lot.finishTime, 'HH:mm dd.MM.yyyy')}</p>
			<p id="countdown" style="font-size:20px"></p>
		<c:choose>
			<c:when test="${user.role == 'USER'}">
				<%@include file="/jsp/fragment/forms/place bid/place_bid.jsp"%>
			</c:when>
			<c:otherwise>
				<h3>Current price is: <span class="badge badge-secondary">${lot.bidPrice}</span></h3>
			</c:otherwise>
		</c:choose>
			
		</c:if>
	<%-- Approve-UnApprove button --%>
	<c:if test="${user.role == 'ADMINISTRATOR'}">
		<c:choose>
			<c:when test="${lot.approved}">
				<%@include file="/jsp/fragment/forms/unapprove lot/unapprove_lot.jsp"%>
			</c:when>
			<c:otherwise>
				<%@include file="/jsp/fragment/forms/approve lot/approve_lot.jsp"%>
			</c:otherwise>
		</c:choose>
	</c:if>
	<%-- /Approve-UnApprove button --%>
</div>
</div>