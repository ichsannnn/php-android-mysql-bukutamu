<?php
  $response = array();

  require_once __DIR__ . '/db_connect.php';
  $db = new DB_CONNECT();
  if (isset($_GET['pid'])) {
    $pid = $_GET['pid'];
    $result = mysql_query('SELECT * FROM pendaftaran WHERE pid = $pid');

    if (!empty($result)) {
      if (mysql_num_rows($result) > 0) {
        $result = mysql_fetch_array($result);
        $pendaftaran = array();
        $pendaftaran['pid'] = $result['pid'];
        $pendaftaran['name'] = $result['name'];
        $pendaftaran['email'] = $result['email'];
        $pendaftaran['description'] = $result['description'];
        $pendaftaran['create_at'] = $result['create_at'];
        $pendaftaran['update_at'] = $result['update_at'];

        $response['success'] = 1;
        $response['pendaftaran'] = array();

        array_push($response['pendaftaran'], $pendaftaran);
        echo json_encode($response);
      } else {
        $response['success'] = 0;
        $response['message'] = 'Tidak ada data yang ditemukan';
        echo json_encode($response);
      }
    } else {
      $response['success'] = 0;
      $response['message'] = 'Required field(s) is missing';
      echo json_encode($response);
    }
  }
?>
