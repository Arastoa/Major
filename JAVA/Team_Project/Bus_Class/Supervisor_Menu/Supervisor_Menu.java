package Supervisor_menu; // 메뉴 패키지

public class Supervisor_Menu { // 메뉴클래스 ( 관리자모드에서 각 기능에 관한 메뉴의 출력을 제공 )

	public void Start_Menu() { // 처음시작했을 때 출력 메시지
		System.out.println("======== 관리자 메뉴에 접속하신걸 환영합니다.  ========");
		System.out.println("1.전체배차시간 확인 2.회원별 이용현황 확인 3.회원정보 삭제 4.데이터베이스 복구 5.종료");
	}

	public void BusTime_Check() { // 전체 배차시간 확인기능의 출력 메시지
		System.out.println("======== 전체배차시간 확인메뉴에 오신걸 환영합니다. ========");
		System.out.println("\t배차시간을 확인할 터미널을 선택해주세요  ");
		System.out.println("( 1.센트럴시티 2.남부터미널 3.동서울터미널 4.상봉터미널 )");
	}

	public void Bus_State() { // 각 회원의 버스이용 현황 및 정보 출력 메시지
		System.out.println("========각 회원별 버스이용현황 메뉴에 오신걸 환영합니다. ========");
	}

	public void Bus_Delete() { // 회원정보를 삭제기능 출력 메시지
		System.out.println("======== 회원정보 삭제 메뉴에 오신걸 환영합니다.  ========");
		System.out.println("삭제를 원하는 회원의 ID와 비밀번호를 입력하세요  ");
	}

	public void Bus_Restore(){ // 데이터베이스 정보 유실시 데이터베이스 정보 복구 출력 메시지
	   System.out.println("======== 데이터 베이스 복구 메뉴에 오신걸 환영합니다. ========");	
	}
	
	public void End() { // 관리자메뉴 종료 출력 메시지
		System.out.println("======== 관리자 모드를 종료합니다, 감사합니다. ========");

	}
}
