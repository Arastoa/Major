<?
//비밀번호 대조 과정 crypt -> md5 -> sha1 -> hash함수 사용(sha2 -> sha256)
//http://kr.php.net/manual/kr/function.hash.php 참고

include "../db_info.php";

function nomal_login($_user,$conn){
	//먼저 틀린 횟수부터 불러와서 로그인 해도 되는지 확인
	$query="select fault from userinfo where id='{$_user['userid']}'";
	$result=mysql_query($query,$conn) or die("login 쿼리 전송 실패".mysql_error());
	$qarray=mysql_fetch_array($result);

	if($qarray[fault]>=4){
?>
		<script>
			alert("로그인 실패 횟수가 5회 이상입니다. 관리자에게 문의해주세요");
		</script>
<?
		exit;
	}
	else{
		$query="select pwd from userinfo where id='{$_user['userid']}'";
		$result=mysql_query($query,$conn) or die("login 쿼리 전송 실패".mysql_error());
		$qarray=mysql_fetch_array($result);

		//아이디를 찾았고 비밀번호가 유저가 입력한 비밀번호와 같을때 세션 시작시킴
		if(!empty($qarray) && ($qarray['pwd']==hash('sha256',$_user['pwd']))) {
			session_start();
			$_SESSION['id'] = $_user['userid'];
			$query="update userinfo set fault=0 where id='$_user[userid]'";
			mysql_query($query,$conn);
?>
			<script>
				alert("로그인되었습니다.");
			</script>
<?
		}
		else if (hash('sha256',$_user['pwd'])!=$qarray['pwd'])  {
			$query="update userinfo set fault=fault+(1) where id='$_user[userid]'";
			mysql_query($query,$conn);
?>
			<script>
				alert("비밀번호가 다릅니다.");
			</script>

<?		}
		else {
?>
			<script>
				alert("등록되지 않은 사용자입니다.");
			</script>
<?		exit;
		}
	}
}

function auto_login($_user,$conn) {
	$query="select fault from userinfo where id='{$_user['userid']}'";
	$result=mysql_query($query,$conn) or die("login 쿼리 전송 실패".mysql_error());
	$qarray=mysql_fetch_array($result);

	echo print_r($qarray);
	echo $qarray[fault];

	if($qarray[fault]>=4){
?>
		<script>
			alert("로그인 실패 횟수가 5회 이상입니다. 관리자에게 문의해주세요");
		</script>
<?
		exit;
	}
	else{
		$query="select pwd from userinfo where id='{$_user['userid']}'";
		$result=mysql_query($query,$conn) or die("login 쿼리 전송 실패".mysql_error());
		$qarray=mysql_fetch_array($result);


		if(!empty($qarray) && ($qarray['pwd']==hash('sha256',$_user['pwd']))) {
			session_start();
			
			//rand() 함수보다 고급 알고리즘이고 rand()함수는 0~32767까지 생성하나 mt_rand()는 2147483647까지 생성.
			//그 값을 해쉬화 시켜서 db와 쿠키에 저장.
			$auto_key=hash('sha256',mt_rand());	
			
			$_SESSION['id'] = $_user['userid'];
			$query="update userinfo set fault=0, auto_login= '$auto_key' where id='$_user[userid]'";
			mysql_query($query,$conn);

			setcookie('userinfo[id]',$_user[userid],time()+60*60*24*365);	//쿠키 유효시간 1년으로 잡음
			setcookie('userinfo[pwd]',$auto_key,time()+60*60*24*365);
?>
			<script>
				alert("자동 로그인되었습니다.");
			</script>
<?
		}
		else if (hash('sha256',$_user['pwd'])!=$qarray['pwd'])  {
			$query="update userinfo set fault=fault+(1) where id='$_user[userid]'";
			mysql_query($query,$conn);
?>
			<script>
				alert("비밀번호가 다릅니다.");
			</script>
<?		}
		else {
?>
			<script>
				alert("등록되지 않은 사용자입니다.");
			</script>
<?		exit;
		}
	}
}

function login_chk($_user){
	if($_user['userid']==""){
?>
		<script>
			alert("아이디를입력해주세요");
			history.back();	//뒤로가기는 최대한 배운걸 많이 써먹어보려고 밑에선 go(-1)로 씀
		</script>
<?		return FALSE;
	}

	if(empty($_user['pwd'])) {
?>
		<script>
			alert("비밀번호를 입력해주세요");
			history.go(-1);
		</script>
<?		return FALSE;
	}

	return TRUE;

/*  이렇게도 공백체크 가능
<?
	if(strlen($_user['pwd'])==0) {
?>
		<script>
			alert("비밀번호를 입력해주세요");
			location.href('http://localhost/Login/login.html');
		</script>
<?}*/
}
?>

<?

if(login_chk($_POST)){
	if($_POST['auto_login']=="true"){
		auto_login($_POST,$conn);
	}
	else{
		nomal_login($_POST,$conn);
	}
//로그인 되었을때 페이지 새로고침
?>
<script>
	location.href('http://localhost/main_marquee.html');
	parent.login_icon.location.href="login_icon.php";
	parent.side.location.href="../side_menu.php";
</script>
<?
}?>
