<?php
  $response = array();
  require_once __DIR__ .'/db_connect.php';
  $db = new DB_CONNECT();
  $result = mysql_query('SELECT * FROM pendaftaran') or die(mysql_error());

  if (mysql_num_rows($result) > 0) {
    $response['pendaftaran'] = array();
    while($row = mysql_fetch_array($result)) {
      $pendaftaran = array();
      $pendaftaran['pid'] = $row['pid'];
      $pendaftaran['name'] = $row['name'];
      $pendaftaran['email'] = $row['email'];
      $pendaftaran['description'] = $row['description'];
      $pendaftaran['create_at'] = $row['create_at'];
      $pendaftaran['update_at'] = $row['update_at'];

      array_push($response['pendaftaran'], $pendaftaran);
    }
    $response['success'] = 1;
    echo json_encode($response);
  } else {
    $response['success'] = 0;
    $response['message'] = "Tidak ada data yang ditemukan";

    echo json_encode($response);
  }
?>
