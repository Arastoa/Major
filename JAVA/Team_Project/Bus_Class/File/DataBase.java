package File;

import java.io.IOException; // IOException를 선언하기 위해 선언
import java.io.File; // File을 불러오기 위해 선언

import jxl.read.biff.BiffException; // BiffException를 선언하기 위해 선언
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException; // 엑셀 라벨,시트,워크북,파일출력 읽어오기 위해 사용하는 패키지

import java.sql.*; // 데이터베이스 연동을 위해 sql패키지의 모든 클래스를 선언
import java.util.Scanner; // Scanner 클래스 사용하기 위해 util 패키지의 Scanner 클래스 생성

import Confirm_Encrypt.Encrypt;

public class DataBase { // 데이터베이스에 있는 내용을 엑셀로 출력하기 위한 클래스
	public void Database() throws IOException, BiffException, WriteException,
			ClassNotFoundException, SQLException {
		
		// BiffException: 엑셀파일 호환안될때 발생(XLSX 파일 읽어오기 불가) , IOException: 입출력 동작실패
		// 또는 인터럽트시 발생
		// WriteException: 엑셀파일 출력 할때 발생
		// SQLException: SQL접속 에러시 발생 , ClassNotFoundException: 같은패키지에 있는 다른
		// 클래스를 불러오지 못할때 발생

		java.sql.Connection con = null; // 디비연동을 위한 변수 con 선언

		Class.forName("com.mysql.jdbc.Driver"); // JDBC 드라이버 로드
		con = DriverManager.getConnection("jdbc:mysql://localhost/project_bus?zeroDateTimeBehavior=convertToNull",
				"root","autoset"); // 데이터베이스 연결(database/id/pw) DriverManager메소드를 통하여 연결을 받아옴

		java.sql.Statement st = null; // Statement 클래스를 사용하는 st 객체
		ResultSet rs = null; // ResultSet클래스를 사용하는 rs 객체
		st = con.createStatement(); // 변수 st에 새 상태를 만들어줌
		rs = st.executeQuery("use project_bus"); // project_bus 데이터베이스를 사용하는 쿼리문을 rs에 저장											
		if (st.execute("select * from userInfo")) // userinfo 테이블에 출력할 내용이 있는 	경우( 즉, 테이블에 값이존재할경우)										
		{
			rs = st.getResultSet(); // 변수 rs에 저장
		}

		while (rs.next()) { // next()메소드를 사용하여 다음 줄에 내용이 있을때 무한반복
			WritableWorkbook workbook = Workbook.createWorkbook(new File("회원현황.xls"));
			// file형으로 입력하여 , Jxl의 클래스.File을 만드는 클래스인 WritableWorkbook 클래스를 사용하여 	엑셀파일 만듬
		
			WritableSheet s1 = workbook.createSheet("회원현황", 0); // Sheet를 만든다.
			// WritableSheet 클래스의 객체 s1을 선언하여 회원현황이라는 sheet를 만든다. 0번위치에

			Label label1 = new Label(0, 0, "회원ID");
			Label label2 = new Label(1, 0, "비밀번호");
			Label label3 = new Label(2, 0, "\tE-메일");
			Label label4 = new Label(3, 0, "\t\t전화번호");
			Label label5 = new Label(4, 0, "\t좌석");
			Label label6 = new Label(5, 0, "행선지");
			Label label7 = new Label(6, 0, "출발터미널");
			Label label8 = new Label(7, 0, "출발시간");
			Label label9 = new Label(8, 0, "요금");
			Label label10 = new Label(9, 0,"도착시간"); // Label 클래스를활용해 각 라벨에 데이터베이스 테이블에 있는 기본 값들을 저장
			
			s1.addCell(label1);
			s1.addCell(label2);
			s1.addCell(label3);
			s1.addCell(label4);
			s1.addCell(label5);
			s1.addCell(label6);
			s1.addCell(label7);
			s1.addCell(label8);
			s1.addCell(label9);
			s1.addCell(label10);
			// 라벨에 있는 값을 addCell 메소드를 사용하여 엑셀로 출력

			for (int i = 1;; i++) { // 데이터베이스에 실제로 존재하는 회원정보를 출력하는 for문
				if (rs.isAfterLast()) // isAfterLast() 존재하는 값들중 마지막 값이 넘을시 for문 중단					
					break;

				Label label11 = new Label(0, i, rs.getString("id"));
				Label label12 = new Label(1, i, rs.getString("pwd"));
				Label label13 = new Label(2, i, rs.getString("email"));
				Label label14 = new Label(3, i, rs.getString("ph_no"));
				Label label15 = new Label(4, i, rs.getString("seat"));
				Label label16 = new Label(5, i, rs.getString("destination"));
				Label label17 = new Label(6, i, rs.getString("terminal"));
				Label label18 = new Label(7, i, rs.getString("booking_time"));
				Label label19 = new Label(8, i, rs.getString("price"));
                Label label20 = new Label(9, i, rs.getString("arrived_time"));
				
				// Label 클래스를활용해 각 라벨에 데이터베이스 테이블에 있는 기본 값들을 저장
				// 1행부터시작하여 증가하는 열의 값들을 라벨에 저장
				s1.addCell(label11);
				s1.addCell(label12);
				s1.addCell(label13);
				s1.addCell(label14);
				s1.addCell(label15);
				s1.addCell(label16);
				s1.addCell(label17);
				s1.addCell(label18);
				s1.addCell(label19);
				s1.addCell(label20);
				// 라벨에 있는 값들을 addCell 메소드를 사용해 엑셀로 출력
				rs.next(); // 다음 라인으로 넘어감
			}

			workbook.write(); // 실제 파일에다가 출력
			workbook.close(); // 종료
		}
	}

	public void SelectDelete() throws ClassNotFoundException, SQLException {
		// SQLException: SQL접속 에러시 발생 , ClassNotFoundException: 같은패키지에 있는 다른
		// 클래스를 불러오지 못할때 발생

		String CheckId, CheckPwd; // ID와 비밀번호를 체크할 변수 CheckId, CheckPwd 선언
		Scanner scan = new Scanner(System.in); // Scanner 클래스를 사용하는 객체 scan 선언
		java.sql.Connection con = null; // 디비연동을 위한 변수 con 선언

		Class.forName("com.mysql.jdbc.Driver"); // jdbc driver를 통하여 mysql로 연결
		con = DriverManager.getConnection( // DriverManager메소드를 통하여 연결을 받아옴
				"jdbc:mysql://localhost/project_bus", "root", "autoset"); // mysql:3306
																			// database/아이디/비밀번호을 사용함
																			
		java.sql.Statement st = null; // Statement 클래스를 사용하는 st 객체
		ResultSet rs = null; // ResultSet클래스를 사용하는 rs 객체
		st = con.createStatement(); // 변수 st에 새 상태를 만들어줌
		rs = st.executeQuery("use project_bus"); // project_bus 데이터베이스를 사용하는 쿼리문을 rs에 저장
													
		CheckId = scan.nextLine(); // 아이디 확인 변수에 회원정보를 지울 ID를 입력받음
		CheckPwd = scan.nextLine(); // 비번 확인 변수에 회원정보를 지울 비밀번호를 입력받음
		int check_delete = st.executeUpdate("delete from userinfo where id='"+ CheckId + "' and pwd='" + Encrypt.SHA256(CheckPwd) + "'");
		// 입력으로 들어온 ID와 비밀번호가 다 맞는지를 확인하는 chk_del 변수 선언
		// delete문은 executeUpdate 메소드를 사용하여, 디비에 id와 입력받은 id가 같고 디비에 pwd와 입력받은 pwd가 같을시
		// 그 행을 나타내는 정수 1를 리턴시켜주고, 입력한 id와 pwd에 맞는 회원정보를 삭제!

		if (check_delete == 1) // 데이터베이스에서 위의 조건이 성립할시 조건이 성립하는 ROW의 수 즉,1을 return 해줌					
			System.out.println("입력된 회원정보 삭제완료!");
		else
			System.out.println("입력된 회원정보는 테이블에 없습니다!"); // 위의 조건 성립 안할시 (비밀번호,ID 둘중 하나라도 틀릴시 삭제안됨)
														 											
	}

}
