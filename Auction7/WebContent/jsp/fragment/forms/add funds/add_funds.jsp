<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/jsp/fragment/forms/add funds/add_funds locale.jsp" %>
<script type="text/javascript">
$(function() {
	  $('#add_funds input[name=amount]').on('input', function() {
	    this.value = this.value
	      .replace(/[^\d.]/g, '')             // numbers and decimals only
	      .replace(/(^[\d]{10})[\d]/g, '$1')  // not more than 2 digits at the beginning
	      .replace(/(\..*)\./g, '$1')         // decimal can't exist more than once
	      .replace(/(\.[\d]{2})./g, '$1');    // not more than 4 digits after decimal
	  });
	});
</script>
<form method="POST" action="controller" id="add_funds">
    	<input type="hidden" name="command" value="add_funds"/>
    	<div class="input-group">
    	    <div class="input-group-prepend">
                <span class="input-group-text">&#36;</span>
            </div>
    		<input type="number" 
    			class="form-control" 
    			name="amount"
    			autocomplete="off"
    			placeholder="0.00" 
    			min="0.01"
    			step="0.01" 
    			max="9999999999.99"/>
    		<div class="input-group-append">
        		<input type="submit" 
            		class="btn btn-success" 
            		value="${add_funds}" 
            		name="add_funds_btn"
            		required/>
            </div>
        </div>
</form>