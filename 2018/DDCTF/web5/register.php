<?php

session_start();
include('config.php');

if($_SERVER['REQUEST_METHOD'] === "POST") {
    if(!(isset($_POST['csrf']) and (string)$_POST['csrf'] === $_SESSION['csrf'])) {
        die("CSRF token error!");
    }

    $admin = "admin###" . substr(str_shuffle('0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ'), 0, 32);
    $username = (isset($_POST['username']) === true && $_POST['username'] !== '') ? (string)$_POST['username'] : die('Missing username');
    $password = (isset($_POST['password']) === true && $_POST['password'] !== '') ? (string)$_POST['password'] : die('Missing password');
    $code = (isset($_POST['code']) === true) ? (string)$_POST['code'] : '';
    if (strlen($username) > 32 || strlen($password) > 32) {
        die('Invalid input');
    }

    $sth = $pdo->prepare('SELECT username FROM users WHERE username = :username');
    $sth->execute([':username' => $username]);

    if ($sth->fetch() !== false) {
        die('username has been registered');
    }

    if($code === $admin) {
        $identity = "admin";
    } else {
        $identity = "guest";
    }

    $sth = $pdo->prepare('INSERT INTO users (username, password, `identity`) VALUES (:username, :password, :identity)');
    $sth->execute([':username' => $username, ':password' => $password, ':identity' => $identity]);

    echo '<script>alert("register success");location.href="./login.php"</script>';
} else {
    ?>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>Sign up</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/signin.css" rel="stylesheet">
  </head>

  <body>
    <div class="container">

      <form class="form-signin" action="register.php" method="post">
        <h2 class="form-signin-heading">Please Sign up</h2>
        <input type="hidden" name="csrf" id="csrf" value="<?php $_SESSION['csrf'] = (string)rand();echo $_SESSION['csrf']; ?>" required>
        <label for="inputUsername" class="sr-only">Username</label>
        <input type="text" name="username" id="inputUsername" class="form-control" placeholder="Username" required autofocus>
        <label for="inputPassword" class="sr-only">Password</label>
        <input type="password" name="password" id="inputPassword" class="form-control" placeholder="Password" required>
        <label for="inputCode" class="sr-only">Code</label>
        <input type="text" name="code" id="inputCode" class="form-control" placeholder="Code" required>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign up</button>
      </form>
    </div>

  </body>
</html>

<?php } ?>