<?php
  $response = array();
  if(isset($_POST['name']) $$ isset($_POST['email']) && isset($_POST['description'])) {
    $name   = $_POST['name'];
    $email  = $_POST['emial'];
    $description  = $_POST['description'];

    require_once __DIR__ . 'db_connect.php';
    $db = new DB_CONNECT();
    $result = mysql_query("INSERT INTO pendaftaran(name, email, description) VALUES ('$name', '$email', '$description')");
    if ($result) {
      $response["success"] = 1;
      $response["message"] = "Pendaftaran anda berhasil";

      echo json_encode($response);
    } else {
      $response["success"] = 0;
      $response["message"] = "Gagal, silahkan coba lagi";

      echo json_encode($response);
    }
  } else {
    $response['success'] = 0;
    $response['message'] = 'Required field(s) is missing';

    echo json_encode($response);
  }
?>
