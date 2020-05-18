<?php
    require_once('dbConnect.php');
    $response = array();
	if($_SERVER['REQUEST_METHOD'] == 'POST'){
		$username = $_POST['user'];
		$password = $_POST['password'];
        $email = $_POST['Email'];
        $hovaten = $_POST['FullName'];
        $SDT = $_POST['SDT'];
        // $username = "quangtinh123456";
        // $password = "12512";
        // $hovaten = "jake pham";
        // $SDT = "098783578";
        // $email = "efe@gmail.com";
		$sqlCheck = "SELECT User FROM dangnhap WHERE User = '$username'";
		$result = @mysqli_query($con,$sqlCheck);
		if (mysqli_num_rows($result) != 0) {	
				$response["success"] = 0;
                $response["message"] = "Exit user";
                
			} else {	
				$sqlInsert = "INSERT INTO dangnhap VALUES (null,'$username','$password',' $hovaten','$SDT','$email')";
				$resultInsert = @mysqli_query($con,$sqlInsert);
				if ($resultInsert) {
						$sqlGetInfo = "SELECT User, email FROM dangnhap WHERE user = '$username' AND Password = '$password'";
						$result = mysqli_query($con,$sqlGetInfo);
						$row = mysqli_fetch_array($result);
						
						$response["success"] = 1;
						$response["message"] = "Registration success";
						$response["user_name"] = $username;
                        $response["email"] = $email;
                        $response['SDT'] = $SDT;
                        $response['fullname'] = $hovaten;
                        
				} else {
					$response["success"] = 0;
                    $response["message"] = "Registration failed";
                    
				}
			}	
			/**Return json*/
		echo json_encode($response);
    }
    


?>