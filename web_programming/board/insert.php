<?
	include "../db_info.php";

	$query="insert into board (id, title, content, wdate, view) values ('$_SESSION[id]','$_POST[title]','$_POST[content]',now(),0)";
	$result=mysql_query($query, $conn) or die("등록 실패".mysql_error());
?>

<script>
	alert("게시완료");
	window.location.href("list.php");
</script>
