<?
include "../db_info.php";

	//아이디 확인 과정
	$query="select count(*) from userinfo where id='$_POST[userid]' ";
	$result=mysql_query($query,$conn);
	$row=mysql_fetch_row($result);

	if($row[0]>=1){
?>
	<script>
		alert("중복되는 아이디가 있습니다.");
		history.back();	//뒤로 감으로서 다시 아이디, 비밀번호 입력받음
	</script>
<?
	}
	//이메일 주소 셋팅
	if(empty($_POST['email_addr'])){
		$email=$_POST['email'].'@'.$_POST['email_dns'];
	}
	else {
		$email=$_POST['email'].'@'.$_POST['email_addr'];
	}
	//
/*	다른 인코딩 환경에서 작업할경우 어떤 캐릭터셋으로 설정되는지 알고싶을때 이부분 출력	
	$username=$_POST['username'];
	echo $username."<br>";

	$charchk=mb_detect_encoding($username);
	echo $charchk."<br>";
*/
	//패스워드 해쉬화
	$pwd=hash('sha256',$_POST['userpwd']);

	$query="insert into userinfo(id, pwd, name,pwdhint, answer, email,job,sns,fault) values('$_POST[userid]', '$pwd', '$_POST[username]','$_POST[pwhint]','$_POST[answer]','$email','$_POST[job]','$_POST[sns]','0')";
	$result=mysql_query($query,$conn) or die("Register 쿼리 전송 실패 ".mysql_error());
	

	/*
//여기 해결못함 =------------------------------------------------------------------
	foreach($_POST['interest'] as $value){
		$query="insert into userinfo (id,interest) values ('$_POST[userid]','$value')";
		echo $value."<br>";
		mysql_query($query,$conn) or die("interest 등록 실패 ".mysql_err());
	}
	*/
?>
	<script>
		alert("축하합니다!\n회원가입이 완료되었습니다.");
		location.href("../main_marquee.html");	//iframe을 초기화면으로 다시 잡아줌
	</script>
