<?php
    require_once('dbConnect.php');
    $response = array();
    if($_SERVER['REQUEST_METHOD']=='POST'){

        $username = $_POST['user'];
        $password = $_POST['password'];
        

        $sql = "select * from dangnhap where User = '$username' and Password = '$password'";
        $check = mysqli_fetch_array(mysqli_query($con,$sql));
        if(isset($check)){
            $response["success"] = 1;
			$response["message"] = "Login success";
			$response["user_name"] = $check['User'];
            $response["email"] = $check['email'];
            $response['SDT'] = $check['sodienthoai'];
            $response['fullname'] = $check['hovaten'];
        }
        else{
            $response['success'] = 0;
            $response['message'] = "Login failed";
        }

    }
    else{
        $response['message'] = "connect failed";
    }
    echo json_encode($response);

      
      
    
?>