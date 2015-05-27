 <link rel="stylesheet" href="../font_sty.css">
 <?
	//팝업으로 띄워지는 창 팝업 하나에서 해결하기 위해 따로 다른 php파일과 연동하지 않고
	//mode를 설정해줘서 이 파일 안에서 전부 해결함
	if($_GET[mode]!="delete"){
 ?>
 <center>
	<table>
			<tr>
				<td colspan=2> <font class="sty2">커피를 구입한 날짜를 입력해 주세요. (yyyy-mm-dd)<br> </font></td>
			</tr>
			<tr>
				<td>
				<form method="POST" action ="<?$_SERVER[PHP_SELF]?>?mode=delete">
					   <input type="text" name="day"></td>
				<td><input type="submit" value="커피등록" style="height:30px; width:90px; font-size:20px; font-family:SMCasiopeaL; background-color:#FFFFFF; border-style: double;"></td>
			</tr>
	</table>
</center>
<?
}

else{
	//strtotime은 시간을 초로 바꿔주는 함수. 다양한 포맷 지원
	//http://docs.php.net/manual/en/function.strtotime.php 참고
	if(strtotime("now") < strtotime("$_POST[day]")) {
?>
	<script>
				alert("잘못된 값입니다 다시 입력해주세요.");
				history.back();
	</script>
<? exit;
	}
	
	//db와 연동해서 ctime을 잡아주는 과정 잡아주고 난뒤엔 관련 페이지 새로고침
	include "../db_info.php";
	$query="update userinfo set ctime='$_POST[day]'";
	$result=mysql_query($query,$conn) or die("update ctime 에러".mysql_error());
	
	if(isset($result)) {
?>
	<script>
				alert("등록이 완료되었습니다.");
				window.opener.location.reload();
				window.close();
	</script>
<?}
	else{
?>
	<script>
				alert("등록실패.");
	</script>
<?
	}
}
?>
