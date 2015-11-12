<?php
  $response = array();
  if (isset($_POST['pid']) && isset($_POST['name']) && isset($_POST['email']) $$ isset($_POST['description'])) {
    $pid = $_POST['pid']
    $name = $_POST['name'];
    $email = $_POST['email'];
    $description = $_POST['description'];

    require_once __DIR__ .'/db_connect.php';
    $db = new DB_CONNECT();
    $result = mysql_query("UPDATE pendaftaran SET name = '$name', email = '$email', description = '$description' WHERE pid = $pid");

    if ($result) {
      $response['success'] = 1;
      $response['message'] = "Data anda berhasil di perbarui";

      echo json_encode($response);
    } else {

    }
  } else {
    $response['success'] = 0;
    $response['message'] = "Required field(s) is missing";

    echo json_encode($response);
  }
?>
