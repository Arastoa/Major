<?
	include "../db_info.php";

	$query="select * from board where no='$_GET[no]'";
	$result=mysql_query($query, $conn) or die("select board 에러");
	$qarray=mysql_fetch_array($result);
	
	$equery="select email from userinfo where id='$qarray[id]'";
	$result=mysql_query($equery, $conn) or die("select userinfo 에러");
	$eqarray=mysql_fetch_array($result);
?>

<html>
<head>
	<link rel="stylesheet" href="../font_sty.css">
	<title>Document</title>
</head>
<body>
	<font class="sty2"><center><br>
		<form action="insert.php" method = post>
		<table width=800 글쓴이 border=0 cellpadding=2 cellspacing=1 style="border-collapse:collapse;" rules="rows" frame="hsides">
			<tr>
				<td height=20 colspan=4 align="center">
					<b><font style="font-size:20px;"><?echo $qarray[title];?></font></b>
				</td>
			</tr>
			<tr>
				<td width = 50 height=20 align=left>글쓴이</td>
				<td width=50 align=left><? echo $qarray[id];?> </td>
				<td width = 70 height=20 align=left>이메일</td>
				<td width=50><?echo $eqarray[email];?> </td>
			</tr>
			<tr>
				<td width = 50 height=20 align=left>날&nbsp;&nbsp;&nbsp;짜</td>
				<td width=300><?echo $qarray[wdate];?> </td>
				<td width = 60 height=20 align=left>조회수</td>
				<td width=50><?echo $qarray[view];?> </td>
			</tr>
			<tr>
				<td colspan=4>
					<pre>
 <?echo $qarray[content];?>
					</pre>
				</td>
			</tr>
			<tr>
				<td width=400 align=lleft height=20>
					<a href="list.php?num=<?=$num?>">[목록보기] </a>
					<a href="write.php">[글쓰기] </a>
					<a href="del.php?no=<?=$_GET[no]?>">[삭제] </a>
				</td>

			</tr>
	</form></center>
</body>
</html>
<?
//조회수 업데이트

	$query="update board set view = view+1 where no='$_GET[no]'";
	mysql_query($query, $conn);
?>
