<?php
session_start();
include('config.php');

if($_SERVER['REQUEST_METHOD'] === "POST") {
    if(!(isset($_POST['csrf']) and (string)$_POST['csrf'] === $_SESSION['csrf'])) {
        die("CSRF token error!");
    }

    $username = (isset($_POST['username']) === true && $_POST['username'] !== '') ? (string)$_POST['username'] : die('Missing username');
    $password = (isset($_POST['password']) === true && $_POST['password'] !== '') ? (string)$_POST['password'] : die('Missing password');

    if (strlen($username) > 32 || strlen($password) > 32) {
        die('Invalid input');
    }

    $sth = $pdo->prepare('SELECT password FROM users WHERE username = :username');
    $sth->execute([':username' => $username]);

    if ($sth->fetch()[0] !== $password) {
        die('wrong password');
    }

    $sth = $pdo->prepare('SELECT `identity` FROM users WHERE username = :username');
    $sth->execute([':username' => $username]);

    if ($sth->fetch()[0] === "admin") {
        $_SESSION['is_admin'] = true;
    } else {
        $_SESSION['is_admin'] = false;
    }

    #echo $username;
    header("Location: index.php");
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

        <title>Signin</title>

        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="css/signin.css" rel="stylesheet">

    </head>

    <body>

    <div class="container">

        <form class="form-signin" action="login.php" method="post">
            <h2 class="form-signin-heading">Please Sign in</h2>
            <input type="hidden" name="csrf" id="csrf" value="<?php $_SESSION['csrf'] = (string)rand();echo $_SESSION['csrf']; ?>" required>
            <label for="inputUsername" class="sr-only">Username</label>
            <input type="text" name="username" id="inputUsername" class="form-control" placeholder="Username" required autofocus>
            <label for="inputPassword" class="sr-only">Password</label>
            <input type="password" name="password" id="inputPassword" class="form-control" placeholder="Password" required>
            <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
            <a href="register.php" class="btn btn-primary btn-lg btn-block" role="button">Sign up</a>
        </form>
    </div>

    </body>
    </html>


    <?php
}
?>
