<?php
  $response = array();

  if (isset($_POST['pid'])) {
    $pid = $_POST['pid'];
    require_once __DIR__ .'/db_connect.php';
    $db = new DB_CONNECT();
    $result = mysql_query("DELETE * FROM pendaftaran WHERE pid = $pid");
    if (mysql_affected_rows() > 0) {
      $response['success'] = 1;
      $response['message'] = "Data berhasil dihapus";

      echo json_encode($response);
    } else {
      $response['success'] = 0;
      $response['message'] = "Tidak ada data yang tersedia";

      echo json_encode($response);
    }
  } else {
    $response['success'] = 0;
    $response['message'] = "Required field(s) is missing";

    echo json_encode($response);
  }
?>
