<link rel="stylesheet" href="font_sty.css">
<?
	include "../db_info.php";
	$query="select auto_login from userinfo where id='{$_COOKIE['userinfo']['id']}'";
	$result=mysql_query($query,$conn) or die("login icon 쿼리 전송 실패".mysql_error());
	$qarray=mysql_fetch_array($result);

	//자동 로그인으로 접속했을 경우
	if( isset($_COOKIE[userinfo][id]) && $_COOKIE[userinfo][pwd]==$qarray[0] ){
			$_SESSION['id'] = $_COOKIE[userinfo][id];
			echo "<font class=sty3>".$_COOKIE[userinfo][id]."님 환영합니다.</font>";
?>
			<html>
			<link rel="stylesheet" href="../font_sty.css">
			<body>
				<a href="logout.php" target="content"><img src="../Image/logout.png" height=29 border=0><a>
			</body>
			</html>
<?	}

	else {
		if(empty($_SESSION[id])) {	//세션이 살아있는지 체크
?>
			<html>
			<body>
				<a href="login.html" target="content"><img src="../Image/login.png"  height=29 border=0><a>&nbsp;&nbsp;&nbsp;
				<a href="../Regist/Register.html" target="content"><img src="../Image/Register.png" height=29 border=0><a>
			</body>
			</html>

<?
			} else {
				echo "<font class=sty3>".$_SESSION['id']."님 환영합니다.</font>";
?>
			<html>
			<link rel="stylesheet" href="../font_sty.css">
			<body>
				<a href="logout.php" target="content"><img src="../Image/logout.png" height=29 border=0><a>
			</body>
			</html>
<?		}

	}
?>
