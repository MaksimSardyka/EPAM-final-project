<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Propose lot</title>
<!-- To adapt for device's screen width: -->
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- B4 links: -->
<link rel="stylesheet"
    href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
<link rel="stylesheet" href="css/bootstrap-grid.css">
<script
    src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
    src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
<script
    src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>
<!-- Custom styles -->
<link rel="stylesheet" href="css/container.css">
        <link rel="stylesheet" href="css/form.css">
</head>

<body>
    <!-- Header -->
    <nav class="navbar navbar-expand-sm bg-dark navbar-dark fixed-top">
        <!-- Лого/Линк на главную страницу -->
        <a class="navbar-brand" href="#">Английский аукцион</a>
        <!-- Кнопка просмотра меню на мобильных устройствах -->
        <button class="navbar-toggler" type="button" data-toggle="collapse"
            data-target="#collapsibleNavbar">
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
            <!-- Группа расположенная справо -->
            <!-- Кнопка Logout -->
            <form method="POST" action="controller" name="log_out_form"
                class="navbar-nav ml-auto">
                <input type="hidden" name="command" value="log_out" /> <input
                    type="submit" class="btn btn-outline-primary" value="Выйти" />
            </form>
        </div>
    </nav>
    <!-- /Header -->
    <!-- Lot -->
    <div class="container">
        <form method="POST" action="controller" name="propose_form">
            <input>
            <input type="text" 
                   name="name" 
                   placeholder="Name for your lot" 
                   pattern="[A-Za-z0-9\\s]{1,40}$" 
                   maxlength="40"
                   title="Username(6-40 symbols) may contain upper/lower case  letters, digits, spaces"
                   required/>
            <input type="text" 
                   name="description" 
                   placeholder="Description for your lot" 
                   pattern="[A-Za-z0-9\\s]{1,200}$" 
                   maxlength="200"
                   title="Description(6-40 symbols) may contain upper/lower case  letters, digits, spaces"
                   required/>
            <input type="submit" 
                   class="submit bg-info text-light" 
                   value="propose" 
                   name="propose"
                   required/>
        </form>
         
    </div>
    <!-- /Lot -->
    <!-- Footer -->
    <footer class="py-3 page-footer bg-dark fixed-bottom">
        <!-- Copyright -->
        <div class="footer-copyright text-center text-light">Copyright
            EPAM© 2018</div>
        <!-- /Copyright -->
    </footer>
    <!-- /Footer -->
</body>
</html>