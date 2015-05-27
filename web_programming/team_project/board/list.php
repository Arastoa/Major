<?
	if(empty($_SESSION[id])){
?>
	<script>
		alert("회원만 이용할 수 있습니다.");
		history.back();
	</script>
<?	exit;
	}

	include "../db_info.php";

	$page_size=10;

	$page_list_size = 10;
	
	$num= $_GET['num'];
	if (!$num || $num < 0) $num=0;

	$query = "SELECT * FROM board ORDER BY no DESC LIMIT $num,$page_size";	//내림차순 정렬
	$result = mysql_query($query, $conn);

	$result_count=mysql_query("SELECT count(*) FROM board",$conn);
	$result_row=mysql_fetch_row($result_count);
	$total_row = $result_row[0];

	if ($total_row <= 0) $total_row = 0;
	$total_page = ceil($total_row / $page_size);

	$current_page = ceil(($num+1)/$page_size);
	?>
<html>
	<head>
				<link rel="stylesheet" href="../font_sty.css">

	</head>
	<body>
			<font class="sty3"><center>
			<table width=800 border=0 cellpadding=2 cellspacing=1 style="border-collapse:collapse;" rules="rows" frame="hsides">
	
				<tr height=20>
					<td width=30 align=center>
						번호
					</td>
					<td width=370 align=center>
						제 목
					</td>
					<td width=50 align=center>
						글쓴이
					</td>
					<td width=60 align=center>
						날 짜
					</td>
					<td width=40 align=center>
						조회수
					</td>
				</tr>
	<?
				while($row=mysql_fetch_array($result))
				{
				?>
				<tr>
					<td height=20 align=center>
						<a href="read.php?no=<?=$row[no]?>&num=<?=$num?>">
						<?echo $row[no];?></a>
					</td>
					<td height=20>&nbsp;
						<a href="read.php?no=<?=$row[no]?>&num=<?=$num?>">
						<?=strip_tags($row[title], '<b><i><p><a>');?></a>	<!--태그 제거 함수-->
					</td>
					<td align=center height=20>
						<a href="mailto:<?=$row[email]?>"><?=$row[id]?></a>
					</td>
					<td align=center height=20>
						<?=$row[wdate]?>
					</td>
					<td align=center height=20>
						<?=$row[view]?>
					</td>
				</tr>
	<?
				}
				?>
				</table>
				<table border=0>
				<tr>
					<td width=600 height=20 align=center rowspan=4>
					&nbsp;
				<?
				$start_page = floor(($current_page - 1) / $page_list_size) * $page_list_size + 1;

				$end_page = $start_page + $page_list_size - 1;

				if ($total_page < $end_page) $end_page = $total_page;


				for ($i=$start_page;$i <= $end_page;$i++) {
					$page= ($i-1) * $page_size;
					if ($num!=$page){
						echo "<a href=\"$_SERVER[PHP_SELF]?num=$page\">";
					}
					
					echo " $i ";
					
					if ($num!=$page){
						echo "</a>";
					}
				}

				?>
					</td>
				</tr>
				</table>
				<a href=write.php>글쓰기</a>
			</font></center>
		</body>
</html>
