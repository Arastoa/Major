<script>
		//등록시킨 커피 지우는 php파일 confirm으로 if문 조작. 
		if(confirm("정말로 삭제하시겠습니까?")){
<?
			include "../db_info.php";
		
			$query="update userinfo set ctime=NULL where id='$_SESSION[id]'";
			$result=mysql_query($query,$conn);
?>
			alert("삭제가 완료되었습니다.");
			window.opener.location.reload();
			window.close();
		}
else {
	window.close();
}
</script>
