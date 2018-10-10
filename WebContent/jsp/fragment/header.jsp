<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- Header -->
<header>
    <nav class="navbar navbar-expand-sm bg-dark navbar-dark fixed-top">
        <!-- Main page link -->
        <form method="POST" action="controller" name="auction_set_page">
            <input type="hidden" 
            	name="command" 
            	value="auction_set_page" 
            	required/>
            <input type="submit" 
            	class="submit" 
            	value="Английский аукцион" 
            	name="submit"
            required/>
        </form>
        <!-- /Main page link -->
        <!-- Mobile devices toggle -->
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#collapsibleNavbar">
            <span class="navbar-toggler-icon"></span>
        </button>
        <!-- Элементы меню -->
        <div class="collapse navbar-collapse" id="collapsibleNavbar">
            <ul class="navbar-nav">
                <!-- Линк на страницу предложения лота -->
                <li class="nav-item">
                    <a class="nav-link" href="#">Предложить лот</a>
                </li>
            </ul>
            	<!-- Right-sided group -->
            		<!-- Logout button -->
            		<form method="POST" action="controller" name="log_out_form" class="navbar-nav ml-auto">
                		<input type="hidden" name="command" value="log_out" />
                		<input type="submit" class="btn btn-outline-primary" value="Выйти" />
            		</form>
            		<!-- /Logout button -->
            	<!-- Right-sided group -->
        </div>
    </nav>
</header>
<!-- /Header -->