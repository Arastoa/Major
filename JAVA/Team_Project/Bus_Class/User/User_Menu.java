package User;

public class User_Menu {
	 
	public static void print_intro()
	{
		System.out.println("============ 서울터미널 예약 프로그램 ===========");
		System.out.println("\t1.회원가입         2.로그인         3.종료\n");
		System.out.println("========================================");
		
	}
 
	public static void print_mainMenu(User user)
	{
		System.out.printf("============ %12s님 환영합니다       =================\n",user.id);
		System.out.println("\t1.좌석예약    2.예약취소    3.내역확인   4.회원정보   5.로그아웃");
		System.out.println("=======================================================");
	}
	public static void print_Cancel(User user)
	{
		System.out.println("============= 예약취소 =============");
		System.out.println("\t"+user.seat+" 좌석 예약을 취소합니다. ");
	}
 
	public static void Check()
	{
		System.out.println("=========== 예약 좌석 확인 ===========");
		System.out.println("\t고객님의 접수내역입니다");
	}
	
	public static void userInfo() {
		System.out.println("=========== 회원정보관리 ===========");
		System.out.println("\t1.회원정보 보기    2.회원탈퇴    3.돌아가기 ");
	}
	
	public static void select_terminal() {
		System.out.println("============= 터미널을 선택해주세요 =============");
		System.out.println("1.센트럴시티 2.상봉 터미널 3.동서울 터미널 4.남부 터미널 5.뒤로가기");
	}
	
	public static void sub_central() {
		System.out.println("=============== 1.충북 2.전북 3.전남 4.경기 ===============");
	}
	public static void sub_sangbong() {
		System.out.println("=============== 1.고속 2.시외 ===============");
	}
	public static void sub_eastSeoul() {
		System.out.println("=============== 1.호남 2.영동 3.경부 ===============");
	}
	public static void sub_southSeoul() {
		System.out.println("=============== 1.충청 2.전라 3.경상 4.경기 ===============");
	}
	public static void request_insert() {
		System.out.println("=============== 행선지와 시간을 입력해주세요 :  ===============");
	}
	public static void exit() {
		System.out.println("\n프로그램을 종료합니다.");
	}
}
