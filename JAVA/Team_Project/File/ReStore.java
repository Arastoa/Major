package File;

import java.io.File; // File을 불러오기 위해 선언
import java.io.IOException; // IOException를 선언하기 위해 선언
import java.sql.*; // 데이터베이스 연동을 위해 sql패키지의 모든 클래스를 선언

import jxl.*; // 외부라이브러리인 엑셀 라이브러리의 사용위해 jxl패키지 선언
import jxl.read.biff.BiffException; // BiffException를 선언하기 위해 선언

public class ReStore {
	// SQLException: SQL접속 에러시 발생 , ClassNotFoundException: 같은패키지에 있는 다른 클래스를
	// 불러오지 못할때 발생
	// BiffException:엑셀파일 호환안될때 발생(XLSX 파일 읽어오기 불가) , IOException:입출력 동작실패
	public void Restore1() throws ClassNotFoundException, SQLException, // 데이이터베이스에 내용삭제 (날라갔다고가정)
			BiffException, IOException {
		// Connection con = null; // 디비연동을 위한 변수 con 선언

		Class.forName("com.mysql.jdbc.Driver"); // // JDBC 드라이버 로드
		Connection con = DriverManager.getConnection( "jdbc:mysql://localhost:3306/project_bus", "root", "autoset"); 
		// 데이터베이스  연결(database/id/password)																	
		// DriverManager메소드를 통하여 연결을 받아옴
	
		java.sql.Statement st = null; // Statement 클래스를 사용하는 st 객체
		ResultSet rs = null; // ResultSet클래스를 사용하는 rs 객체
		st = con.createStatement(); // 변수 st에 새 상태를 만들어줌
		rs = st.executeQuery("use project_bus"); // project_bus 데이터베이스를 사용하는 쿼리문을 rs에 저장								
		st.executeUpdate("delete from userinfo"); // 테이블에 있는 모든 내용 삭제 (데이터베이스 정보손실�榮鳴� 가정)											
		rs = st.executeQuery("select * from userinfo"); // userinfo 테이블에 있는 내용을 출력하는 쿼리문을 rs에 저장
										
		if (rs.next()) // 테이블에 내용이 없음을 확인 ( 있을시 while문 돌고 없을시 '내용없음' 문장 출력
		{
			while (rs.next()) {
				String id = rs.getString("id");
				String pwd = rs.getString("pwd");
				String email = rs.getString("email");
				String seat = rs.getString("seat");
				String pho_num = rs.getString("pho_no");
				String destination = rs.getString("destination");
				String terminal = rs.getString("terminal");
				String booking_time = rs.getString("booking_time");
				String price = rs.getString("price");
				System.out.println(id + pwd + email + seat + pho_num
						+ destination + terminal + booking_time + price);
			}
		} else
			System.out.println("데이터베이스에 등록된 회원정보가 없습니다.");
	}

	public void Restore2() throws ClassNotFoundException, SQLException, //데이터베이스 내용복구
			BiffException, IOException {

		Class.forName("com.mysql.jdbc.Driver"); // // JDBC 드라이버 로드
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_bus", "root", "autoset"); 
		 // 데이터베이스 연결(id/pw)
		 // DriverManager메소드를 통하여 연결을 받아옴																	
		java.sql.Statement st = null; // Statement 클래스를 사용하는 st 객체
		ResultSet rs = null; // ResultSet클래스를 사용하는 rs 객체
		st = con.createStatement(); // 변수 st에 새 상태를 만들어줌
		rs = st.executeQuery("use project_bus"); // project_bus 데이터베이스를 사용하는 쿼리문을 rs에 저장
													
		Workbook workbook = null; // 엑셀 패키지 내에 있는 WorkBook클래스와 Sheet클래스의 사용을 위해 객체 생성	
		Sheet sheet = null;
		workbook = Workbook.getWorkbook(new File("회원현황.xls")); // 현황.xls라는 엑셀파일은 workbook에 저장											
		sheet = workbook.getSheet("회원현황"); // 현황.xls 안에 현황 sheet를 sheet에 저장

		// 시트내에서 데이터가 시작되는 Row지정
		int nRowStartIndex = 1;
		// 시트내에서 데이터가 끝나는 Row지정
		int nRowEndIndex = sheet.getColumn(0).length - 1;
		// getColumn 메소드를 사용하여 0번열의 행의 갯수를 반환한다. (최대 출력을결정) -> 행이 작은 열로선정시, 더 긴행을가진 값들은 안나옴을방지!
		// 엑셀파일에서 0번열의 행의갯수를 제일 길게 저장

		// 시트내에서 데이터가 시작되는 Column지정
		int nColumnStartIndex = 0;
		// 시트내에서 데이터가 끝나는 Column지정
		int nColumnEndIndex = sheet.getRow(1).length - 1;
		// getRow 메소드를 사용하여 1번행의 열의 갯수를 반환한다. (최대 출력을결정) -> 열이 더 작은 행으로 선정시, 더 긴 열을가진 값들은 안나옴을방지!
		// 엑셀파일에서 1번행의 열의 갯수를 제일 길게 저장

		String id = "", pwd = "1234", email = "1234", seat = "", ph_no = "", destination = "", booking_time = ""
		,arrived_time="";
		int price = 0,terminal=0;
        // id , pwd , email은 not null로 설정되어 있어서 기본 값을 넣어줌
		
		for (int nRow = nRowStartIndex; nRow <= nRowEndIndex; nRow++) {
			for (int nColumn = nColumnStartIndex; nColumn <= nColumnEndIndex; nColumn++) {
				// 이중 for문으로 각 행과 열을 순서대로 설정
				switch (nColumn) {
				case 0:
					id = sheet.getCell(nColumn, nRow).getContents();
		st.executeUpdate("insert into userinfo (id,pwd,email) values ('"+ id + "','" + pwd + "','" + email + "')");
					// 출력변수에 getCell메소드를 사용해 각 셀의 위치를 선정 후 getcontents 메소드를 사용해 값을 불러옴!
					
					break;
					// id 같은경우 컬럼,로우에 해당하는 셀을 읽어와서 getContents 메소드를 사용해 내용을 읽어와 변수에 저장후 
					// sql클래스의 executeupdate 메소드를 통해 값들을 데이터베이스에 넣어준다.
		       case 1:
					pwd = sheet.getCell(nColumn, nRow).getContents();
					st.executeUpdate("update userinfo set pwd ='" + pwd+ "' where id='" + id + "'");
					break;
				case 2:
					email = sheet.getCell(nColumn, nRow).getContents();
					st.executeUpdate("update userinfo set email ='" + email+ "' where id='" + id + "'");
					break;
				case 3:
					seat = sheet.getCell(nColumn, nRow).getContents();
					st.executeUpdate("update userinfo set seat ='" + seat+ "' where id='" + id + "'");
					break;
				case 4:
					ph_no = sheet.getCell(nColumn, nRow).getContents();
					st.executeUpdate("update userinfo set ph_no ='" + ph_no+ "' where id='" + id + "'");
					break;
				case 5:
					destination = sheet.getCell(nColumn, nRow).getContents();
					st.executeUpdate("update userinfo set destination ='"+ destination + "' where id='" + id + "'");
					break;
				case 6:
					if (sheet.getCell(nColumn, nRow).getContents().equals(""))
					{
						terminal = 0;
				    	st.executeUpdate("update userinfo set terminal ="+ terminal + " where id='" + id + "'");
					}
					else
						terminal = Integer.parseInt(sheet.getCell(nColumn, nRow).getContents());
					st.executeUpdate("update userinfo set terminal ='"+ terminal + "' where id='" + id + "'");
					break;
					// terminal이나 booking_time이나 price의 경우는 데이터베이스 테이블에서
					// Date time이나 int형으로 선언되어 있어서 String으로 받지를 못한다.
					// 그래서 equals 메소드를 사용하여 "" 널값일 경우 NULL을 직접 대치하여 넣어주고, 아닐경우 같이 GETCONTENT로 받는다
				case 7:
					if (sheet.getCell(nColumn, nRow).getContents().equals("")) {
						booking_time = "NULL";
						st.executeUpdate("update userinfo set booking_time ="+ booking_time + " where id='" + id + "'");
					} else {
						booking_time = sheet.getCell(nColumn, nRow).getContents();
						st.executeUpdate("update userinfo set booking_time ='"+ booking_time + "' where id='" + id + "'");
					}

					break;
				case 8:    
					if (sheet.getCell(nColumn, nRow).getContents().equals(""))
						price = 0;
					else
						price = Integer.parseInt(sheet.getCell(nColumn, nRow).getContents());
					st.executeUpdate("update userinfo set price ='" + price+ "' where id='" + id + "'");
					break;
					
				case 9:
					if (sheet.getCell(nColumn, nRow).getContents().equals("")) {
						arrived_time = "NULL";
						st.executeUpdate("update userinfo set arrived_time ="+ arrived_time + " where id='" + id + "'");
					} else {
						arrived_time = sheet.getCell(nColumn, nRow).getContents();
						st.executeUpdate("update userinfo set arrived_time ='"+ arrived_time + "' where id='" + id + "'");
					}

					break;
				}
			}

		}
		rs = st.executeQuery("select * from userinfo");
		if (rs.isBeforeFirst())
			System.out.println("데이터베이스 복구완료!");
	}
}
