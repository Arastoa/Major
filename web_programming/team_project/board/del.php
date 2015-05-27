<?
	include "../db_info.php";

	$query="select id from board where no='$_GET[no]'";
	$result=mysql_query($query, $conn);
	$qarray=mysql_fetch_array($result);

	if($qarray[0]==$_SESSION[id])
	{
			$query="delete from board where no='$_GET[no]'";
			$result=mysql_query($query, $conn);
			if($result){
?><script>
				alert('삭제 완료');
				history.go(-2);
</script>
<?
			exit;
			}
	}
	else{
?>
<script>		alert("자신의 글만 삭제 가능합니다"); 
				history.back();
</script>
<?		exit;
	}
?>	
