<?php 
   		$db = mysqli_connect("mysqlsvr42.world4you.com", "sql9918093", "a90jm92", "4543316db5");
        if(mysqli_connect_errno()){
            die('Unable to connect to database');
        }

        $source = $_GET['source']; 
        $title = $_GET['title']; 
        $published = $_GET['published']; 
        $hash = $_GET['hash']; 
        $url = $_GET['url']; 


        $secretKey="rLdZyTAJeynUh6JDR8Sut8Yj1sLXIPWO"; 
        $real_hash = strtoupper(hash('sha1', $secretKey)); 

        if($real_hash == $hash) { 
            $sql = "insert into news values (NULL, '$source', '$title', '$published', '$url');"; 
            echo $sql;
            $db = mysqli_query($db, $sql); 
        } else{
            echo "Hash error";
        }

        mysqli_close($db);
?>