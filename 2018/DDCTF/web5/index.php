<?php
session_start();
include "config.php";

if (!isset($_SESSION['is_admin'])) {
    die('<br> Please <a href="login.php">login</a>!');
}

if (!$_SESSION['is_admin']) {
    die('You are not admin. <br> Please <a href="login.php">login</a>!');
}

if(isset($_GET['id'])){
    $id = addslashes($_GET['id']);
    if(isset($_GET['title'])){
        $title = addslashes($_GET['title']);
        $title = sprintf("AND title='%s'", $title);
    }else{
        $title = '';
    }
    $sql = sprintf("SELECT * FROM article WHERE id='%s' $title", $id);

    foreach ($pdo->query($sql) as $row) {
        echo "<h1>".$row['title']."</h1><br>".$row['content'];
        die();
    }
    

}


?>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Admin</title>

    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/sticky-footer.css" rel="stylesheet">

  </head>

  <body>

    <div class="container">
      <div class="page-header">
        <h1>文章列表</h1>
      </div>
      <div class="lead">
          <li><a href='?id=1'>Welcome!</a><br></li>
          <li><a href='?id=2'>Hello,world!</a><br></li>
      </div>
    </div>

    <footer class="footer">
      <div class="container">
        <p class="text-muted">©2017 Admin</p>
      </div>
    </footer>
  </body>
</html>
