<?php
   $db = mysqli_connect("mysqlsvr42.world4you.com", "sql9918093", "a90jm92", "4543316db5");
    if(mysqli_connect_errno()){
        die('Unable to connect to database');
    }

    $hash = $_GET['hash']; 

    $secretKey="rLdZyTAJeynUh6JDR8Sut8Yj1sLXIPWO"; 
    $real_hash = strtoupper(hash('sha1', $secretKey)); 

    if($real_hash == $hash) { 
        $sql = "TRUNCATE TABLE news;"; 
        $db = mysqli_query($db, $sql); 

    } else{
        echo "Hash error";
    }
    mysqli_close($conn);
?>