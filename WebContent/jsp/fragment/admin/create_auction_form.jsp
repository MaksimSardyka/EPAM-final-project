<%@page contentType="text/html" pageEncoding="UTF-8"%>
    <link rel="stylesheet" href="css/input-area.css">
    <form method="POST" class="input-area bg-dark" action="controller">
        <input type="hidden" name="command" value="create_auction"/>
		<input type="datetime-local" 
				name="start_time"
				placeholder="Start time"
				required/>
		<input type="datetime-local" 
				name="finish_time"
				placeholder="Finish time"
				required/>
        <input type="text" 
            name="description"
            placeholder="auction description" 
            maxlength="256" 
            required/>
        <select name="type" 
        		required>
                <option value="direct" selected>Прямой</option>
  				<option value="reverse">Обратный</option>
		</select>
        <input type="submit" 
            class="submit bg-info text-light" 
            value="Create auction" 
            name="create"
            required/>
    </form>