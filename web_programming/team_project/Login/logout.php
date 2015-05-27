<?
	include "../db_info.php";
	$query="update userinfo set auto_login= NULL where id='$_SESSION[id]'";	//자동 로그인일 경우 자동 로그인 해제
	mysql_query($query,$conn);

	session_unset();
	session_destroy();
	setcookie('userinfo[id]',"",time()-1);	//자동로그인도 풀리게 만들어줌
	setcookie('userinfo[pwd]',"",time()-1);

?>

<script>
	alert("정상적으로 로그아웃 되었습니다.");
	location.href('http://localhost/main_marquee.html');
	parent.login_icon.location.href="login_icon.php";
	parent.side.location.href="../side_menu.php";
</script>  
