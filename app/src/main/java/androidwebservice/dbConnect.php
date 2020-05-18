<?php
$hostname = "localhost";
$username = "root";
$password = "1234";
$dbname = "mypham";
$con = mysqli_connect($hostname,$username,$password,$dbname); //Kết nối database.
	mysqli_set_charset($con,'utf8');


?>