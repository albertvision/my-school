<!DOCTYPE html>
<html lang="bg" >
<head>
  <meta charset="UTF-8">
  <title>CodePen - Bootstrap responsive hover navbar</title>
  <link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css'>
  <link rel="stylesheet" href="static/css/menu.css">

</head>
<body>
<header class="navbar navbar-inverse navbar-fixed-top bs-docs-nav" role="banner">
  <div class="container">
    <div class="navbar-header">
      <button class="navbar-toggle" type="button" data-toggle="collapse" data-target=".bs-navbar-collapse">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <!-- <a href="./" class="navbar-brand">Bootstrap Menu</a> -->
    </div>
    <nav class="collapse navbar-collapse bs-navbar-collapse" role="navigation">
      <ul class="nav navbar-nav navbar-right">
        <li>
          <a href="#">Home</a>
        </li>
        
        <li class="dropdown">
          <a class="dropbtn">Каталози <b class="caret"></b></a>
          <ul class="dropdown-menu">
            <li><a href="#">Училище</a></li>
            <li><a href="#">Структура</a></li>
			<li><a href="#">Преподавани предмети</a></li>
            <li><a href="#">Преподаватели</a></li>
			<li><a href="#">Ученици</a></li>
          </ul>
        </li>
        <li>
          <a href="#">Програма</a>
        </li>
        <li>
          <a href="#">Бележник</a>
        </li>
        <li>
          <a href="#">Отсъствия</a>
        </li>
		<li>
          <a href="#">Справки</a>
        </li>
      </ul>
    </nav>
  </div>
</header>
  <script src='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js'></script>
</body>
</html>
