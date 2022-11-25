<?php 

    error_reporting(E_ALL); 
    ini_set('display_errors',1); 

    include('dbcon.php');


    $android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");

    // 어플리케이션에서 전달한 값 웹서버에서 받기
    if( (($_SERVER['REQUEST_METHOD'] == 'POST') && isset($_POST['submit'])) || $android )
    {
        $TAG_ID=$_POST['TAG_ID'];


        if(empty($TAG_ID)){
            $errMSG = "TAG_ID를 입력하세요.";
        }
        else{
        }


        // member 테이블의 tag_id랑 비교해 데이터 삭제
        if(!isset($errMSG))
        {
            try{
                $stmt = $con->prepare("DELETE FROM member WHERE tag_id = {$TAG_ID}");


                if($stmt->execute())
                {
                    $successMSG = "사용자 삭제되었습니다.";
                }
                else
                {
                    $errMSG = "사용자 삭제 에러";
                }

            } catch(PDOException $e) {
                die("Database error: " . $e->getMessage()); 
            }
        }

    }

?>


<?php 
    if (isset($errMSG)) echo $errMSG;
    if (isset($successMSG)) echo $successMSG;

	$android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");
   
    if( !$android )
    {
?>
    <html>
       <body>

            <form action="<?php $_PHP_SELF ?>" method="POST">
                TAG_ID: <input type = "text" name = "TAG_ID" />
                <input type = "submit" name = "submit" />
            </form>
       
       </body>
    </html>

<?php 
    }
?>