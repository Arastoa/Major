<?
include "../db_info.php";

$query="select * from userinfo where id='$_SESSION[id]'";
$result=mysql_query($query,$conn) or die("게시판 유저정보 에러".mysql_error());
$qarray=mysql_fetch_array($result);
?>

<html>
<head>
	<link rel="stylesheet" href="../font_sty.css">
	<title>Document</title>
</head>
<body>
	<font class="sty2"><center><br>
		<form action="insert.php" method = post>
		<table width=800 border=0 cellpadding=2 cellspacing=1 style="border-collapse:collapse;" rules="rows" frame="hsides">
			<tr>
				<td colspan=2 align=center>
					<b>글 쓰 기</b>
				</td>
			</tr>
			<tr>
				<td width = 200 >아이디</td>
				<td ><?echo $qarray[id];?> </td>
			</tr>
			<tr>
				<td width = 200 align=left>이메일</td>
				<td align=left><?echo $qarray[email];?></td>
			</tr>
			<tr>
				<td width = 200 align=left>제목</td>
				<td align=left>
					<input type="text" name="title" size="80" maxlength="35">
				</td>
			</tr>
			<tr>
				<td width = 60 align=left>내용</td>
				<td align=left>
					<textarea name="content" cols="80" rows="35" row="80"></textarea>
				</td>
			</tr>
			<tr>
				<td colspan=10 align=center>
					<input type="submit" value="글 쓰기">&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="reset" value="다시 쓰기">&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="button" value="되돌아가기" onclick="history.back(-1)">
				</td>
			</tr>
	</form></center>
</body>
</html>
