package User;

import Bus_Class.*;
import Confirm_Encrypt.*;
import java.sql.*;
import java.util.*;

public class UserMode {
	User user;
	
	public void userMode() {
		int control;
		Scanner scan=new Scanner(System.in);
		
		//인트로 회원가입 로그인 종료 3개중에 하나를 받음
		while(true){
			User_Menu.print_intro();
			control=scan.nextInt();
			
			switch(control){
			case 1:
				Register();
				break;
			case 2:
				while(!user_login()) {}
				while(login_mode()) {}
				break;
			case 3:
				User_Menu.exit();
				System.exit(1);
			default :
				System.out.println("잘못입력하셨습니다");
				break;
			}
		}
	}
	public boolean login_mode(){
		Bus_Terminal bus_terminal=new Bus_Terminal();
		int control;
		Scanner scan=new Scanner(System.in);
		
		while(true){
			User_Menu.print_mainMenu(user);
			control=scan.nextInt();
			chk_user();
			switch(control){
			case 1:	//예약
				bus_terminal.book_Bus(user);
				break;
			case 2:	//예약 취소
				cancel(scan);
				break;
			case 3:	//좌석확인
				confirm_seat();
				break;
			case 4:	//회원정보
				User_Menu.userInfo();
				control=scan.nextInt();
				switch(control){
				case 1:	//회원정보보기
					System.out.println(user);
					break;
				case 2:	//탈퇴
					withdrawal();
					user=new User();
					return false;	//로그인 상태 해제하고 원래 userMode로 빠져나감
				case 3:
					break;
				default :
					System.out.println("잘못된 값을 입력했습니다.");
					break;
					
				}
				break;
			case 5 :
				user=new User();
				return false;
			default:
				System.out.println("다시 입력해주세요.");
			}
		}
	}
	void cancel(Scanner scan){
		user.print_ticket();
		System.out.println("예약을 취소하시겠습니까? (0: no 1: yes)");
		int control = scan.nextInt();
		
		switch(control){
		case 0:
			System.out.println("취소합니다.");
			break;
		case 1:
			user.reset_bookInfo();
			Connection con = null;
			try {
				Class.forName("com.mysql.jdbc.Driver");
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_bus?zeroDateTimeBehavior=convertToNull",
						"root", "autoset");
				java.sql.Statement st = null;
				ResultSet rs = null;
				st = con.createStatement();
				rs = st.executeQuery("use project_bus");
				
				st.execute("update userinfo set terminal=NULL, destination=NULL, booking_time=NULL, price=NULL, arrived_time=NULL, seat=NULL where id='"+user.id+"'");
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		default :
			System.out.println("잘못된 값을 입력하셨습니다.");
			break;
		}
		
	}
	//메뉴를 불러올때마다 현재시각과 도착시간을 체크해서 현재시간이 발차시각을 지나면 예매내역 초기화
	void chk_user() {
		Calendar now=Calendar.getInstance();
		if(this.user.arrived_time!=null && this.user.arrived_time.getTimeInMillis() < now.getTimeInMillis()){
			user.reset_bookInfo();	
			Connection con = null;
			try {
				Class.forName("com.mysql.jdbc.Driver");
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_bus?zeroDateTimeBehavior=convertToNull",
						"root", "autoset");
				java.sql.Statement st = null;
				ResultSet rs = null;
				st = con.createStatement();
				rs = st.executeQuery("use project_bus");
				
				st.execute("update userinfo set terminal=NULL, destination=NULL, booking_time=NULL, price=NULL, arrived_time=NULL, seat=NULL where id='"+user.id+"'");
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	//좌석 확인
	public void confirm_seat() {
		user.print_ticket();
	}
	
	//회원 탈퇴
	public boolean withdrawal() {
		Scanner scan=new Scanner(System.in);
		String id, pwd;
		Connection con = null;

		try {
			//db접속과정
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_bus?zeroDateTimeBehavior=convertToNull",
					"root", "autoset");

			java.sql.Statement st = null;
			ResultSet rs = null;
			st = con.createStatement();
			rs = st.executeQuery("use project_bus");
			
			System.out.print("아이디를 입력해주세요 : ");
			id=scan.nextLine(); 
			
			System.out.print("비밀번호를 입력해주세요 : ");
			pwd=scan.nextLine();
			
			//id pwd 맞는 조건 찾음
			rs=st.executeQuery("select count(*) from userinfo where id='"+id+"' and pwd='"+Encrypt.SHA256(pwd)+"'");
			rs.next();
			
			//찾았을때
			if(rs.getInt("count(*)")==1){
				System.out.println("정말로 탈퇴하시겠습니까? (0:no  1:yes)");
				int control=scan.nextInt();
				switch(control){
				case 0:
					return false;
				case 1:
					if(st.execute("delete from userinfo where id='"+user.id+"'")){
						System.out.println("탈퇴되었습니다. 지금까지 이용해 주셔서 감사합니다.");
						return true;
					}
				default:
					return false;
				}
			}

			else {
				System.out.println("입력이 잘못되었습니다. 메인메뉴로 갑니다");
				return false;
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch(Exception e){		
			e.printStackTrace();
		}
		
		return true;
	}
	public boolean user_login(){
		Scanner scan=new Scanner(System.in);
		String id, pwd;
		Connection con = null;

		try {
			
			//db접속과정
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_bus?zeroDateTimeBehavior=convertToNull",
					"root", "autoset");

			java.sql.Statement st = null;
			ResultSet rs = null;
			st = con.createStatement();
			rs = st.executeQuery("use project_bus");
			
			System.out.print("아이디를 입력해주세요 : ");
			id=scan.nextLine(); 
			
			System.out.print("비밀번호를 입력해주세요 : ");
			pwd=scan.nextLine();
			
			//id pwd 맞는 조건 찾음
			rs=st.executeQuery("select count(*) from userinfo where id='"+id+"' and pwd='"+Encrypt.SHA256(pwd)+"'");
			rs.next();
			
			//찾았을때
			if(rs.getInt("count(*)")==1){
				System.out.println("로그인 성공");
				st.executeQuery("select * from userinfo where id='"+id+"'");
				rs=st.getResultSet();
				
				rs.next();
				
				//userMode에서 사용할 user 초기화
				String user_id=rs.getString("id");
				String email=rs.getString("email");
				String ph_no=rs.getString("ph_no");
				String seat = rs.getString("seat");
				int terminal=rs.getInt("terminal");
				String destination = rs.getString("destination");
				
				String atime=(rs.getString("arrived_time")==null) ? "" : rs.getString("arrived_time").replace('.', '0');
				String btime=(rs.getString("booking_time")==null) ? "" : rs.getString("booking_time").replace('.', '0');
				
				if(!btime.equals("") && !atime.equals("")){	//예약이 되어있을 경우
					//시간 초기화를 다양하게 해보기 위해 이번엔 토큰 사용
					int[] sepTime=new int[3];
					StringTokenizer stok=new StringTokenizer(btime.substring(11,16),":");
					for(int i=0 ; stok.hasMoreTokens() ; i++){
						sepTime[i]=Integer.parseInt(stok.nextToken());
					}
					atime=atime.substring(11,16);
					this.user=new User(user_id,email,ph_no, terminal,destination,""+sepTime[0]+":"+sepTime[1],atime,seat);
				}
				
				else{	//예약하지 않았을 경우
					
					this.user=new User(user_id,email,ph_no);
				}
			}
			else {
				System.out.println("일치하는 데이터가 존재하지 않습니다. 다시 한번 확인해주세요");
				return false;
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch(Exception e){		
			e.printStackTrace();
		}
		
		return true;
	}
	
	public void Register(){
		Scanner scan=new Scanner(System.in);
		try {
			String id, pwd="", email,ph_no;
			Connection con = null;

			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_bus",
					"root", "autoset");

			java.sql.Statement st = null;
			ResultSet rs = null;
			st = con.createStatement();
			rs = st.executeQuery("use project_bus");
			
			
			while(true){
				System.out.println("아이디를 입력해주세요.");
				id=scan.nextLine();
				if(confirm.isContain_korean(id) || id.length()>20){
					System.out.println("잘못된 아이디입니다.");
					continue;
				}
				
				rs=st.executeQuery("select count(*) from userinfo where id='"+id+"'");
				
				rs.next();
				if(rs.getInt("count(*)")==0){
					break;
				}
				System.out.println("중복되는 아이디가 있습니다. 다시 입력해주세요");
			}
			
			while(true){
				System.out.println("비밀번호를 입력해주세요.");
				pwd=scan.nextLine();
				if(pwd.length()>10 ||confirm.isContain_korean(pwd)){
					System.out.println("잘못된 비밀번호입니다.");
					continue;
				}
				break;
			}
			
			while(true){
				System.out.println("이메일을 입력해주세요.");
				email=scan.nextLine();
				if(!confirm.isContain_at(email)){
					System.out.println("잘못된 이메일 주소입니다.");
					continue;
				}
				break;
			}
				
			while(true){
				System.out.println("휴대전화번호를 입력해주세요.");
				ph_no=scan.nextLine();
				if(!confirm.isContain_2Hypen(ph_no)){
					System.out.println("잘못된 휴대전화번호입니다.");
					continue;
				}
				break;
			}
			
			if(st.executeUpdate("insert into userInfo(id,pwd,email,ph_no) values('"+id+"','"+Encrypt.SHA256(pwd)+"','"+email+"','"+ph_no+"');")==1)
				System.out.println("회원가입이 완료되었습니다.");
			else
				System.out.println("회원가입에 실패했습니다. 관리자에게 문의해주세요");
			
			//con.close();
		} catch (SQLException sqex) {
			System.out.println("SQLException 에러 ! 회원가입 실패");
			sqex.getMessage();
		} catch (Exception e) {
			System.out.println("Exception 에러! 관리자에게 문의해주세요");
		}
	}
}
