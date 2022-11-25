<?php 

    error_reporting(E_ALL); 
    ini_set('display_errors',1); 

    include('dbcon.php');


    $android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");

    // 어플리케이션에서 전달한 값 웹서버에서 받기
    if( (($_SERVER['REQUEST_METHOD'] == 'POST') && isset($_POST['submit'])) || $android )
    {
        $TAG_ID=$_POST['TAG_ID'];
        $CAR_NAME=$_POST['CAR_NAME'];
        $CARD_CO=$_POST['CARD_CO'];
        $CARD_NUM=$_POST['CARD_NUM'];
        $BALANCE=$_POST['BALANCE'];

        if(empty($TAG_ID)){
            $errMSG = "TAG_ID를 입력하세요.";
        }
        else if(empty($CAR_NAME)){
            $errMSG = "CAR_NAME를 입력하세요.";
        }
        else if(empty($CARD_CO)){
            $errMSG = "CARD_CO를 입력하세요.";
        }
        else if(empty($CARD_NUM)){
            $errMSG = "CARD_NUM를 입력하세요.";
        }
        else if(empty($BALANCE)){
            $errMSG = "BALANCE를 입력하세요.";
        }

        // 데이터를 member 테이블에 저장
        if(!isset($errMSG))
        {
            try{
                $stmt = $con->prepare('INSERT INTO member(TAG_ID, CAR_NAME, CARD_CO, CARD_NUM, BALANCE) VALUES(:TAG_ID, :CAR_NAME, :CARD_CO, :CARD_NUM, :BALANCE)');
                $stmt->bindParam(':TAG_ID', $TAG_ID);
                $stmt->bindParam(':CAR_NAME', $CAR_NAME);
                $stmt->bindParam(':CARD_CO', $CARD_CO);
                $stmt->bindParam(':CARD_NUM', $CARD_NUM);
                $stmt->bindParam(':BALANCE', $BALANCE);


                if($stmt->execute())
                {
                    $successMSG = "새로운 사용자를 추가했습니다.";
                }
                else
                {
                    $errMSG = "사용자 추가 에러";
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
                CAR_NAME: <input type = "text" name = "CAR_NAME" />
	   CARD_CO: <input type = "text" name = "CARD_CO" />
	   CARD_NUM: <input type = "text" name = "CARD_NUM" />
	   BALANCE: <input type = "text" name = "BALANCE" />
                <input type = "submit" name = "submit" />
            </form>
       
       </body>
    </html>

<?php 
    }
?>