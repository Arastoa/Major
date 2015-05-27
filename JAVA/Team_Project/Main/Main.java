package Main;

import Supervisor.Supervisor;
import User.*;
import java.io.IOException; // IOException을 선언하기 위해 선언
import jxl.read.biff.BiffException; // BiffException를 선언하기 위해 선언
import jxl.write.WriteException;   // WriteException를 선언하기 위해 선언
import java.sql.SQLException; //SQLException를 선언하기 위해 선언
import java.util.Scanner; // Scanner클래스 사용을 위해 자바의 Util 패키지 선언

public class Main { // 프로그램 첫 시작(Main)

	public static void main(String args[]) throws BiffException, IOException,
			SQLException, ClassNotFoundException, WriteException { // 예외처리 선언

		// BiffException: 엑셀파일 중 호환안되는 XLSX 파일 읽어올때 발생 , IOException: 입출력 동작실패 또는 인터럽트시 발생
		// WriteException: 엑셀파일 출력 예외처리
		// SQLException: SQL접속 에러시 발생 , ClassNotFoundException: 같은패키지에 있는 다른 클래스를 불러오지 못할때 발생
		
		Scanner scan = new Scanner(System.in); // Scanner 클래스 사용 객체 scan 선언
		int control;
		
		while(true){
			User_Menu.print_intro();
			
			control=scan.nextInt();
			
			UserMode userMode=new UserMode();
			switch(control){
			case 1:
				userMode.Register();
				break;
			case 2:
				while(!userMode.user_login()) {}
				while(userMode.login_mode()) {}
				break;
			case 3:
				User_Menu.exit();
				System.exit(1);
			case 445343:
				Supervisor supervisor = new Supervisor(); // supervisor 클래스 사용해 관리자 모드 진입
				supervisor.owner();
			default :
				System.out.println("잘못입력하셨습니다");
				break;
			}
		}
	}

}
