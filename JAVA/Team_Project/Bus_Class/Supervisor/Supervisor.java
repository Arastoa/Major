package Supervisor;

import java.io.IOException;  // IOException를 선언하기 위해 선언

import jxl.read.biff.BiffException;  // BiffException를 선언하기 위해 선언
import jxl.write.WriteException; // WriteException를 선언하기 위해 선언

import java.sql.SQLException; // SQLException를 선언하기 위해 선언
import java.util.Scanner; // Scanner클래스를 사용하기 위해 util패키지 선언

import Supervisor_menu.Supervisor_Menu;
import File.*; // File패키지에 있는 Database,Excel클래스를 사용하기 위해 File패키지 선언

public class Supervisor { // 관리자 모드를 지원하는 Owner 클래스

	public void owner() throws BiffException, IOException, 
			ClassNotFoundException, SQLException, WriteException {
		
		// BiffException:엑셀파일 호환안될때 발생(XLSX 파일 읽어오기 불가) , IOException:입출력 동작실패 또는 인터럽트시 발생
		// WriteException: 엑셀파일 출력 할때 발생
		// ClassNotFoundException: 다른 패키지에 있는 클래스를 불러올시 발생 
		// SQLException : SQL접속 에러시 발생.
		
		int menu; // 메뉴선택 변수
		int submenu; // 서브메뉴선택 변수
		Scanner scan = new Scanner(System.in); // 입력을 위해 Scanner클래스를 사용하기 위한 객체 생성
		Supervisor_Menu me = new Supervisor_Menu();  // Menu 클래스를 사용하는 객체 me 선언
		Excel excel = new Excel(); // Excel 클래스를 사용하는 객체 excel 선언
		DataBase db = new DataBase(); //DataBase 클래스를 사용하는 객체 db 선언
        ReStore res = new ReStore(); // ReStore 클래스를 사용하는 객체 res 선언
		
		me.Start_Menu(); // 프로그램 첫 메뉴 호출!

		for (;;) // 반복적으로 수행하기위해 무한루프반복
		{
			System.out.println("보실 업무를 입력하세요:(1~5 아닌 수 입력시 다시입력)"); 
			menu = scan.nextInt(); // 메뉴 선택 위해 숫자 입력받음
			if (menu == 1) {    // 메뉴가 1일시, 버스의 전체시간을 확인하는 Bus.Check 메소드 호출 
				me.BusTime_Check(); // 조건 성립시 시간확인메뉴 호출
				submenu = scan.nextInt(); // 각 터미널을 선택하기 위해 서브메뉴의 값을 입력받음
				if (submenu == 1)  // 서브메뉴의 값이 1~4로 바뀜에 따라 각 조건에 맞는 터미널이 선택됨
					excel.Read(1);  // 1=센트럴 , 2=남부 , 3=동서울, 4=상봉 터미널
				else if (submenu == 2)
					excel.Read(2);  // 각 터미널에 맞는 엑셀 파일을 불러오기 위해 Excel 클래스의 Read 메소드 사용
				else if (submenu == 3)
					excel.Read(3);
				else if (submenu == 4)
					excel.Read(4);
			}

			else if (menu == 2) {   // 메뉴가 2일시, 현재 데이터베이스 상에 있는 유저 정보를 호출함
				me.Bus_State(); // 조건성립시 회원별 버스이용현황 메뉴 호출
				db.Database();  // 데이터 베이스에 있는 값을 가져와서, 엑셀 파일로 저장하는 DataBase 클래스의 Database 메소드 호출
				excel.Read(5);  // 위에서 저장한 엑셀 파일을 불러오기 위해 Excel 클래스의 Read 메소드 사용
			} else if (menu == 3) {  // 메뉴가 3일시, 현재 데이터베이스 상에 있는 회원 정보를 삭제하는 메뉴 호출
				me.Bus_Delete(); // 조건성립시 회원정보삭제메뉴 호출
				db.SelectDelete(); // 회원정보를 삭제하는 기능을 가진 DataBase 클래스에 SelectDelete 메소드 호출
			} else if (menu == 4){  //메뉴가 3일시, 데이터베이스에 이상이 있어서 포맷한다고 가정, 
			    me.Bus_Restore();	// 조건성립시 데이터베이스 정보복구메뉴 호출
			    res.Restore1();  // 데이터베이스 정보복구 기능을 가진 ReStore 클래스에 Restore 메소드 호출
			    res.Restore2();  // 데이터베이스 정보복구 기능을 가진 Restore 클래스에 Restore 메소드 호출
                    // Restore1은 삭제, Restore2는 복구
			}
			else if (menu == 5) {
				me.End(); // 조건성립시 예약종료메뉴 호출
				break;    // 반복문 종료
			} else
				// (1~5) 아닌 다른 숫자 입력시 다시 입력받음
				continue;

		}
	}
}
