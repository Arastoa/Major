<script>
<?
	include "../db_info.php";

	$query="select id from userinfo where no='$no'";
	$result=mysql_query($query, $conn);

	if($result[id]==$_SESSION[id]){
		$query="insert into board (id, title, content) values ('$_SESSION[id]','$_POST[title]','$_POST[content]')";
		mysql_query($query,$conn);
?>
		alert("수정 완료!");
		window.document.href("read.php?no=<?=$no?>");
<?
		exit;
	}
	else{
?>
	alert("수정 실패");
	history.back();
<?
	exit;
	}
?>

</script>
