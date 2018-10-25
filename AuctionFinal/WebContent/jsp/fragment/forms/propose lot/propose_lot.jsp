<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/jsp/fragment/forms/propose lot/propose_lot locale.jsp" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="stylesheet" href="css/input-area.css">
    <script type="text/javascript">
    $(function() {
        // Multiple images preview in browser
        var imagesPreview = function(input, placeToInsertImagePreview) {
            if (input.files) {
                var filesAmount = input.files.length;
                for (i = 0; i < filesAmount; i++) {
                    var reader = new FileReader();
                    reader.onload = function(event) {
                        $($.parseHTML('<img>')).attr('src', event.target.result).appendTo(placeToInsertImagePreview);
                    }
                    reader.readAsDataURL(input.files[i]);
                }
            }
        };
        $('#propose_lot input[type=file]').on('change', function() {
            imagesPreview(this, 'div.gallery');
        });
    });
    </script>
    <script type="text/javascript">
$(function() {
	  $('#propose_lot input[name=amount]').on('input', function() {
	    this.value = this.value
	      .replace(/[^\d.]/g, '')             // numbers and decimals only
	      .replace(/(^[\d]{10})[\d]/g, '$1')  // not more than 10 digits at the beginning
	      .replace(/(\..*)\./g, '$1')         // decimal can't exist more than once
	      .replace(/(\.[\d]{2})./g, '$1');    // not more than 2 digits after decimal
	  });
});
</script>

<form method="POST" class="input-area bg-dark" enctype="multipart/form-data" action="controller" id="propose_lot">
    	<input type="hidden" name="command" value="propose_lot"/>
    	<input type="hidden" name="nonce" value="${nonce}" />
    	<input type="hidden" name="auction_id" value="${auction_id}"/>
    	<%@include file="/jsp/fragment/input_field/lot name/lot_name.jsp"%>
    	<%@include file="/jsp/fragment/input_field/lot description/lot_description.jsp"%>
    	<%@include file="/jsp/fragment/input_field/lot category/lot_category.jsp"%>
    	<%@include file="/jsp/fragment/input_field/start price/start_price.jsp"%>
		<input type='file' accept="image/*" name='file1' id='gallery-photo-add'/>
		<input type='file' accept="image/*" name='file2' id='gallery-photo-add'/>
		<input type='file' accept="image/*" name='file3' id='gallery-photo-add'/>
        <input type="submit" 
          			class="btn btn-success" 
            		value="${propose_lot}" 
            		name="propose_lot_btn"
            	required/>
</form>
<c:if test="${not empty errorMessage}">
	<div class="alert alert-danger"><p>${errorMessage}</p></div>
</c:if>
<div class="gallery"></div> 