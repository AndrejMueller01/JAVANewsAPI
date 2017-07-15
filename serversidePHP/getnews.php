<?php
   $db = mysqli_connect("mysqlsvr42.world4you.com", "sql9918093", "a90jm92", "4543316db5");
        if(mysqli_connect_errno()){
            die('Unable to connect to database');
        }

    $id = $_GET['id']; 

    $query = "SELECT * FROM `news` WHERE id='$id'";
    $result = mysqli_query($db, $query);
 
    if (mysqli_num_rows($result) > 0) {
        while($row = mysqli_fetch_assoc($result)) {
            echo "{ \"id\":" . $row["ID"]. ", \"source\":\"" . $row["source"]. "\", \"title\":\"" . $row["title"]. "\", \"published\":\"". $row["published"]. "\", \"url\":\"" . $row["url"]. "\" }";
        }
    } else {
        echo "false";
    }

    mysqli_close($conn);
?>