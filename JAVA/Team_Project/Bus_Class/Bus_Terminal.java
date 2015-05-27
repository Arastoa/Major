package Bus_Class;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import Interface_Inherit.Printable;
import User.*;
import ENUM.enum_terminal;
import IO_Exel.Print_Exel;

public class Bus_Terminal implements Printable{
	public enum SEAT{C,B,A};	//좌석 예약할 때 쓸 공용체
	public Bus bus=new Bus();
	
	//버스 좌석정보 리셋
	void busSeat_reset(Bus bus, int select, String str_dest){
		
		//내용이 변경될때마다 호출하는데 변경 되기전 내용이 남아 있어서 모두 false로 바꿔주고 재설정
		for(int i=0 ; i<bus.seats.length ; i++)
			for(int j=0 ; j<bus.seats[0].length ; j++)
				bus.seats[i][j]=false;
		
		Connection con = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			//db접속과정
			Class.forName("com.mysql.jdbc.Driver");
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_bus?useUnicode=true&characterEncoding=euckr",
					"root", "autoset");
	
			java.sql.Statement st = null;
			ResultSet rs = null;
			st = con.createStatement();
			rs = st.executeQuery("use project_bus");
			
			//같은 터미널, 목적지, 시간에 출발하는 사람들의 seat정보를 불러와서 bus.seat 배열을 재설정
			rs=st.executeQuery("select seat from userinfo where terminal='"+
			select+"' and destination='"+str_dest+"' and booking_time='"+dateFormat.format(this.bus.booking_time.getTime())+"'");
			
			while(rs.next()){
				if(rs.getString("seat")==null)
					break;
				book_seat(this.bus,rs.getString("seat"));
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch(Exception e){		
			e.printStackTrace();
		}
	}
	
	public void book_Bus(User user) {
		
		//버스 셋팅 이후 빈자리 있을 때
		Scanner scan=new Scanner(System.in);
		String str_dest="";	//목적지
		String str_time="";	//시간
		
        int select=select_terminal();	//터미널 시트 선택
		
		if(select==-1)	//선택에러일경우 바로 함수종료
			return;
		
		User_Menu.request_insert();     //입력 요구문
		//행선지 선택
		str_dest=set_destinaion("전체터미널.xls",select,scan);
		if(str_dest.equals("x"))
			return;
			
		//시간 입력
		str_time=set_bookingTime("전체터미널.xls",select,str_dest,scan);
		if(str_time.equals("x"))
			return;
		
		//버스 초기화
		
		bus=new Bus(Print_Exel.get_price(select, str_dest),select,str_dest,str_time,Print_Exel.get_drivingTime(select, str_dest));
		Connection con = null;
		
		try {
			//버스 좌석정보 리셋
			busSeat_reset(this.bus,select,str_dest);
			
			int per;	//사람 인원수 받을 변수
			
			do{
				System.out.println("탑승 인원을 입력해주세요 : (1~2)");
				per=scan.nextInt();
			}while(per>2 || per<1);
			
			String seat=book_seat(this.bus,per);
			if(seat.equals("")){
				System.out.println("남은 좌석이 없습니다.");
				return;
			}
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String bTime=dateFormat.format(this.bus.booking_time.getTime());
			String aTime=dateFormat.format(this.bus.arrived_time.getTime());
			
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_bus?useUnicode=true&characterEncoding=euckr",
					"root", "autoset");

			java.sql.Statement st = null;
			ResultSet rs = null;
			st = con.createStatement();
			rs = st.executeQuery("use project_bus");
			
			//db에 저장된 유저 정보를 갱신하고 
			if(st.executeUpdate("update userinfo set terminal='"+select+"', destination='"+str_dest+"', "+"arrived_time='"+aTime+"', "+
			"booking_time='"+bTime+"', price='"+this.bus.price+"', seat='"+seat+"' where id='"+user.id+"'")==1){
				bTime=bTime.substring(11,16);
				aTime=aTime.substring(10,16);
				//자바 프로그램안에서 돌아가는 user를 설정
				user.set_bookInfo(seat, select,  bTime,  Print_Exel.get_drivingTime(select, str_dest));
				
				System.out.println("예매가 완료되었습니다.");
				System.out.println("seat : "+user.seat);
				busSeat_reset(this.bus,select,str_dest);	//변경된 내용 반영 후 출력 ( 그냥 출력하면 전의 true가 남아있음)
				this.bus.print();
			}
				
			else
				System.out.println("예매 실패! 관리자에게 문의해주세요");
			
			//테스트 출력문
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch(Exception e){		
			e.printStackTrace();
		}
	}
	
	//예약할때 시간 확인용
	public String set_bookingTime(String file_name,int select, String str_dest, Scanner scan){
		Calendar now=Calendar.getInstance();
		Calendar booking_time=Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        Date date = null;
        String str_time="";
        
		while(true){
			System.out.print("시간을 입력하세요 (HH:mm   x : 종료)    ");
			str_time=scan.nextLine();
			String timeTable=Print_Exel.get_timeTable(select, str_dest);
			
			if("x".equals(str_time))
				return "x";
			
			//불러온 시간 테이블 중에서 입력받은 시간이 있는지 확인
			if(!timeTable.contains(str_time) ){
				System.out.println("시간을 잘못입력하셨습니다.");
				continue;
			}
			//calendar 초기화
			try {
				date=dateFormat.parse(""+now.get(Calendar.YEAR)+"-"+(now.get(Calendar.MONTH)+1)+"-"+now.get(Calendar.DATE)+" "+str_time);
			} catch (ParseException e) {	//구문에러 발생시
				System.out.println("시간을 잘못입력하셨습니다.");
				e.printStackTrace();
			}
			
			booking_time.setTime(date);
			
			//밀리초단위로 현재시각과 예약시간을 비교
			if(booking_time.getTimeInMillis() > now.getTimeInMillis())
				break;
			else
				System.out.println("현재시각 이후의 시간을 입력해주세요");
		}
		return str_time;
	}
	
	//목적지 설정
	public String set_destinaion(String file_name,int select,Scanner scan){
		String str_dest="";
		Print_Exel.print_timeTable(file_name,select);
		String destination=Print_Exel.destination_list(select);	//엑셀에서 목적지를 줄로 불러옴
		
		while(true){
			System.out.print("행선지를 입력하세요 : (x : 종료)    ");
			str_dest=scan.nextLine();
			//입력받은 문장이 목적지에 있는지 확인
			if(str_dest.equals("x"))
				return "x";
			else if(destination.contains(str_dest))
				break;
			else
				System.out.println("행선지를 다시 입력해주세요");
		}
		return str_dest;
	}
	
	//enum에 대응하는 값 리턴 시켜서 시트에서 불러와 출력할 수 있게끔함
	public int select_terminal() {
		Scanner scan=new Scanner(System.in);
		int select,e_terminal;
		
		do{
			User_Menu.select_terminal();
			select=scan.nextInt();
		}while(select>5 && select <1);
		
		switch(select){	
		//센트럴 시티 시트의 시작값으로 초기화
		case 1 :
			e_terminal=enum_terminal.TERMINAL.CENTR_CB.ordinal();
			do{
				User_Menu.sub_central();
				select=scan.nextInt();
				select--;
			//select <0 || select >=4 일때까지 이렇게 설정한 이유는 나중에 excel에 노선이 추가되었을때 enum에서 그 노선만 순서에 맞게 넣어주면
			//유지보수가 편해질거라 생각해서 이런식으로 조건값을 줌
			}while(select<enum_terminal.TERMINAL.CENTR_CB.ordinal()	
					|| select >= enum_terminal.TERMINAL.SB_GO.ordinal());
			break;
			
		case 2 :	//상봉터미널 시트 시작값
			e_terminal=enum_terminal.TERMINAL.SB_GO.ordinal();
			do{
				User_Menu.sub_sangbong();
				select=scan.nextInt();
				select--;
			//select <0 || select >1
			}while(select<enum_terminal.TERMINAL.SB_GO.ordinal() - enum_terminal.TERMINAL.SB_GO.ordinal()
					|| select >=enum_terminal.TERMINAL.DS_HN.ordinal()- enum_terminal.TERMINAL.SB_GO.ordinal());
			break;
		case 3 :	//동서울
			e_terminal=enum_terminal.TERMINAL.DS_HN.ordinal();
			do{
				User_Menu.sub_eastSeoul();
				select=scan.nextInt();
				select--;
			//select <0 || select >2
			}while(select<enum_terminal.TERMINAL.DS_HN.ordinal() - enum_terminal.TERMINAL.DS_HN.ordinal()
					|| select >=enum_terminal.TERMINAL.NB_CC.ordinal()- enum_terminal.TERMINAL.DS_HN.ordinal());
			break;
			
		case 4 :	//남부
			e_terminal=enum_terminal.TERMINAL.NB_CC.ordinal();
			do{
				User_Menu.sub_eastSeoul();
				select=scan.nextInt();
				select--;
			//select <0 || select >3
			}while(select<enum_terminal.TERMINAL.NB_CC.ordinal() - enum_terminal.TERMINAL.NB_CC.ordinal()
					|| select >=enum_terminal.TERMINAL.NB_KK.ordinal()- enum_terminal.TERMINAL.NB_CC.ordinal());
			break;
		default :
			return -1;
		}
		return e_terminal+select;
	}
	
	//db에서 받아온 자료로 미리 셋팅할 메소드
	public void book_seat(Bus bus,String seat){
		
		if(seat.contains("&")){		//두자리 들어왔을때
			StringTokenizer stok=new StringTokenizer(seat,"&");
			String[] tmp_seat=new String[2];
			
			for(int i=0 ; stok.hasMoreTokens() ; i++)
				tmp_seat[i]=stok.nextToken();
			
			for(int i=0 ; i<tmp_seat.length ; i++){
				book_seat(bus,tmp_seat[i]);		//재귀호출으로 해결
			}
		}
		else {
			int row=SEAT.valueOf(""+seat.toUpperCase().charAt(0)).ordinal();
			int column=Integer.parseInt(""+seat.toUpperCase().charAt(1));

			if(row==0 && column==7)
				bus.full_C=true;
			
			bus.seats[row][column]=true;
			bus.empty_seat--;
			bus.benefit+=bus.price;
		}
	}
	
	//사용자에게 입력받은 데이터를 통해 버스예매할 메소드
	public String book_seat(Bus bus,int num_per){
		String seat=new String("");
		if(num_per <= bus.empty_seat){	//좌석수가 더 많거나 같은 경우
			//한좌석 입력받고 C열이 비었을 경우
			if(!bus.full_C && num_per==1){
				int start=0;
				while(bus.seats[0][start])
					start++;
				
				bus.seats[0][start]=true;
				
				if(start==7)
					bus.full_C=true;
				
				seat=new String("C").concat(""+start);
			}
			//C열이 꽉차고 한좌석 요구할 경우
			else if(bus.full_C && num_per==1){
				int last=bus.seats[1].length-1;
				int row=1;
				while(bus.seats[row][last]){	
					
					//row를 올리고 줄이고 함으로서 A행인지 B행인지 판단
					if(row==1)
						row++;
					else{
						row--;
						last--;
					}
				}
				bus.seats[row][last]=true;
				
				seat=new String((row==1) ? "B":"A").concat(""+last);
			}
			//두좌석 요구할 경우
			else if(num_per==2){
				int start=0;
				
				while(bus.seats[1][start] || bus.seats[2][start])
					start++;
				
				bus.seats[1][start]=true;
				bus.seats[2][start]=true;
				
				seat=new String("A"+start+"&B"+start);	//두명 예약시 &를 토큰으로 사용 
			}
			
			bus.empty_seat-=num_per;
			bus.benefit+=bus.price;
			return seat.toString();
		}
		else
			return "";
	}
	public void print() {
		bus.print();
	}
}
