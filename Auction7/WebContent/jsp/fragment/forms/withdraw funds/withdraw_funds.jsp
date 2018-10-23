<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/jsp/fragment/forms/withdraw funds/withdraw_funds locale.jsp" %>
<script type="text/javascript">
$(function() {
	  $('#withdraw_funds input[name=amount]').on('input', function() {
	    this.value = this.value
	      .replace(/[^\d.]/g, '')             // numbers and decimals only
	      .replace(/(^[\d]{10})[\d]/g, '$1')  // not more than 10 digits at the beginning
	      .replace(/(\..*)\./g, '$1')         // decimal can't exist more than once
	      .replace(/(\.[\d]{2})./g, '$1');    // not more than 2 digits after decimal
	  });
	});
</script>

<form method="POST" action="controller" id="withdraw_funds">
    	<input type="hidden" name="command" value="withdraw_funds"/>
    	<div class="input-group">
    	    <div class="input-group-prepend">
                <span class="input-group-text">&#36;</span>
            </div>
    		<input type="number"
    			name="amount" 
    			class="form-control" 
    			autocomplete="off" 
    			placeholder="0.00" 
    			step="0.01" 
    			min="0.01"
    			max="${user.money}"
    			class="bg-light"/>
    		<div class="input-group-append">
        		<input type="submit" 
            		class="btn btn-warning" 
            		value="${withdraw_funds}" 
            		name="withdraw_funds_btn"
            		required/>
            </div>
        </div>
</form>