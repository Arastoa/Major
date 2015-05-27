<?
	$conn=mysql_connect("localhost","root","autoset") or die("connection err      check me!".mysql_error());
	mysql_select_db("testdb",$conn) or die("select_db err      check me!".mysql_error());
	//서버 인코딩 설정 euckr로 안되어있을때 주석 풀고 사용
	//$query="set names 'euckr'";
	//mysql_query($query,$conn);
?>
