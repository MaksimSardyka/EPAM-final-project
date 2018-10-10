<%@page contentType="text/html" pageEncoding="UTF-8"%>
    <link rel="stylesheet" href="css/input-area.css">
    <form method="POST" action="controller" class="input-area" name="login_form">
        <input type="hidden" 
            name="command" 
            value="log_in" 
            required/>
        <input type="text" 
            name="username" 
            placeholder="Username" 
            pattern="^[A-Za-z0-9_]{1,40}$" 
            maxlength="40"
            title="Username(6-40 symbols) should contain UpperCase, LowerCase, Number/SpecialChar"
            required/>
        <input type="password"
            name="password" 
            placeholder="Password" 
            pattern="(?=^.{6,40}$)((?=.*\d)|(?=.*\W+))(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*$" 
            maxlength="40"
            title="Password(6-40 symbols) should contain UpperCase, LowerCase, Number/SpecialChar"
            required/>
        <input type="submit" 
            class="submit bg-info text-light" 
            value="Login" 
            name="login"
            required/>
    </form>