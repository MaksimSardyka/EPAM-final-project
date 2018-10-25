<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/jsp/fragment/header/header locale.jsp" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- Header -->
<header>
	<nav class="navbar navbar-expand-sm bg-dark navbar-dark fixed-top" style="height:56px">
	
	<span class="navbar-brand">
		<c:choose>
			<c:when test="${not empty user && empty requestScope.auction_set}">
					<%@include file="/jsp/fragment/forms/view auction set/view_auction_set.jsp"%>
			</c:when>
			<c:otherwise>
				${english_auction}
			</c:otherwise>
	</c:choose>
			</span>
	<ul class="navbar-nav">	
	<li class="nav-item dropdown">
      <a class="nav-link dropdown-toggle" href="#" id="navbardrop" data-toggle="dropdown">
        ${language}
      </a>
      <div class="dropdown-menu">
        <%@include file="/jsp/fragment/forms/pick language/pick_language.jsp"%>
      </div>
    </li>
    </ul>
		
		<c:if test="${not empty user}">
			<!-- Mobile devices toggle -->
			<button class="navbar-toggler" type="button" data-toggle="collapse"data-target="#collapsibleNavbar">
				<span class="navbar-toggler-icon"></span>
			</button>
			<!-- /Mobile devices toggle -->
			<div class="collapse navbar-collapse" id="collapsibleNavbar">
				<div class="navbar-nav ml-auto btn-group bg-dark">
					<%@include file="/jsp/fragment/forms/view user data/view_user_data.jsp"%>
					<%@include file="/jsp/fragment/forms/logout/logout.jsp"%>
				</div>
			</div>
			<!-- /Mobile devices toggle -->
		</c:if>
	</nav>
</header>
<!-- /Header -->