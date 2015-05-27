<html>
<head>
 <link rel="stylesheet" href="../font_sty.css">
<script language="javascript">
		function coffee_register_popup(){
				window.open("Regist/coffee_register.php", "커피 등록", "width=600, height=300");
		}
		
		function delete_coffee(){
				window.open("Regist/delete_coffee.php", "커피 제거", "width=1, height=1");
		}
		function bookmark(){
				window.external.AddFavorite('http://localhost/Main.html', 'Coffe Time');
		}
</script>
</head>
	<body>
	<table   height=100%  align=left cellspacing=0 cellpadding=0 border=0  valign=top>
					<tr>
						<td cellspacing=0 cellpadding=0 valign=top>
						
						<a href="notice.html" target="content"><img src="Image\Notice.png" border=0 height=40></a><br><br>
						<a href="Image/coffee_word.png" target="content"><img src="Image\word.png" border=0 height=25></a><br><br>
						<a href="recommand.html" target="content"><img src="Image\site.png" border=0 height=22></a><br><br>
						<a href="javascript:bookmark()"><img src="Image/Add_Favorites.gif" onmouseover="this.src='Image/hover_add.png'" onmouseout="this.src='Image/Add_Favorites.gif'" alt="즐겨찾기 추가^^"  width=190 border=0></a><br>
<?
	if($_SESSION[id]){	//로그인 되었을 때
		include "db_info.php";
		
		$query="select ctime from userinfo where id='$_SESSION[id]'";
		$result=mysql_query($query,$conn) or die("side 쿼리 전송 실패".mysql_error());
		$qarray=mysql_fetch_array($result);

		if(!empty($qarray[0])) {	//등록한 커피가 있을 경우
				//초를 구해서 시간을 계산하기 때문에 달이 넘어가도 상관이 없음
				//strtotime은 문자열 형태의 날짜를 입력받아 UNIX timestamp 값을 돌려주는 함수다.
				$store_time=(int)((strtotime("now") - strtotime($qarray[ctime]))/86400)+1;
				
				//구입한지 몇일이나 지났고 맛의 상태를 출력시켜줌
?>	
				<center><font class="sty2">커피를 구입하신지 <? echo "<font class='sty2'>$store_time</font>" ?> 일 지났습니다. <br><br>
				커피의 상태 <br><br>
<?
				if($store_time>=0 && $store_time <=1 ){
?>				매우 신선한 상태입니다.
<?			}
				else if($store_time>=2 && $store_time <=5 ){
?>				숙성이 잘 되어 매우 맛있는 상태입니다.
<?			}
				else if($store_time>=6 && $store_time <=7){
?>				맛이 나쁘지는 않은 상태입니다.
<?			}
				else if($store_time >=8 && $store_time<=11){
?>				그럭저럭 먹을만한 상태입니다.
<?			}
				else{
?>				본연의 맛과 향을 많이 잃었습니다.
<?			}
			//커피 삭제 버튼
?>
			<br>
			<input type="button" onclick="delete_coffee()" value="커피삭제" style="height:30px; width:90px; font-size:20px; font-family:SMCasiopeaL; background-color:#FFFFFF; border-style: double;">
<?
		}else{
?>	</font></center>
<?			//커피 등록 안시켰을 때 등록할 수 있는 버튼
?>	      <input type="button" onclick="coffee_register_popup()" value="커피등록" style="height:30px; width:90px; font-size:20px; font-family:SMCasiopeaL; background-color:#FFFFFF; border-style: double;">
<?	}
	}
?>
					</td></tr>
				</table>
	</body>
</html>
