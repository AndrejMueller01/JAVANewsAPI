<?php
   $db = mysqli_connect("mysqlsvr42.world4you.com", "sql9918093", "a90jm92", "4543316db5");
    if(mysqli_connect_errno()){
        die('Unable to connect to database');
    }

    $source = $_GET['source']; 
    $title = $_GET['title']; 
    $sql = "SELECT * FROM news WHERE source='$source' AND title='$title'";

    $result = mysqli_query($db, $sql); 

    if (mysqli_num_rows($result) > 0) {
    } else{
        echo "ok";
    }
    mysqli_close($conn);
?>