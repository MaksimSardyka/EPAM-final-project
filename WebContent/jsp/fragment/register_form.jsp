<%@page contentType="text/html" pageEncoding="UTF-8"%>
    <link rel="stylesheet" href="css/input-area.css">
    <form method="POST" class="input-area" action="controller" name="register_form">
        <input type="hidden" name="command" value="register"/>
        <input type="text" 
            name="username" 
            placeholder="Username" 
            pattern="^[A-Za-z0-9_]{1,40}$" 
            maxlength="40"
            title="Username(6-40 symbols) should contain UpperCase, LowerCase, Number/SpecialChar"
            required/>
        <input type="email" 
            name="email"
            placeholder="email" 
            maxlength="256" 
            required/>
        <input type="password" 
            name="password" 
            placeholder="Password" 
            pattern="(?=^.{6,40}$)((?=.*\d)|(?=.*\W+))(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*$" 
            maxlength="40"
            title="Password(6-40 symbols) should contain UpperCase, LowerCase, Number/SpecialChar"
            required/>
        <input type="password" 
            name="repeat_password" 
            placeholder="Repeat-Password" 
            pattern="(?=^.{6,40}$)((?=.*\d)|(?=.*\W+))(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*$" 
            maxlength="40"
            title="Password(6-40 symbols) should contain UpperCase, LowerCase, Number/SpecialChar"
            required/>
        <input type="submit" 
            class="submit bg-info text-light" 
            value="Register" 
            name="register"
            required/>
    </form>