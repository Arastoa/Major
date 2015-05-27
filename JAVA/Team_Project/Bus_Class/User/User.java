package User;

import java.util.Calendar;
import java.util.Date;

import ENUM.enum_terminal;

import java.text.*;

import Interface_Inherit.bookInfo;

public class User extends bookInfo{
	public String id;
	public String email;
	public String ph_no;
	
	/*	상속 받은 것
	public int terminal;
	public String destination;
	public Calendar booking_time;
	public Calendar arrived_time;
	public String seat;
	 */
	//예약관련 정보 재설정
	public void reset_bookInfo() {
		this.terminal=-1;
		this.destination="";
		this.booking_time=null;
		this.arrived_time=null;
		this.seat="";
	}
	//좌석 재설정
	public void set_seat(String seat){
		this.seat=seat;
	}
	
	//터미널 재설정
	public void set_terminal(int terminal){
		this.terminal=terminal;
	}
	
	//booking_time과 arrived_time 재설정
	public void set_time(String bTime,int driving_time){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date = new Date();
		Calendar now=Calendar.getInstance();
		
		try{
			//this.booking_time.setTime(dateFormat.parse("09:40"));
			date=(dateFormat.parse(""+now.get(Calendar.YEAR)+"-"+(now.get(Calendar.MONTH)+1)+"-"+now.get(Calendar.DATE)+" "+bTime));
		}catch(ParseException e){
			e.printStackTrace();
		}
		
		this.booking_time=this.booking_time.getInstance();
		this.booking_time.setTime(date);
		
		this.arrived_time=Calendar.getInstance();
		this.arrived_time.setTime(this.booking_time.getTime());
		this.arrived_time.add(Calendar.MINUTE, driving_time);
	}
	
	//예약 관련 정보만 재설정
	public void set_bookInfo(String seat,int terminal,String bTime,int driving_time){
		set_time(bTime,driving_time);
		set_terminal(terminal);
		set_seat(seat);
	}
	
	public User() {}
	
	public User(String id,String email, String ph_no){
		this.id=id;
		this.email=email;
		this.ph_no=ph_no;
	}
	
	public User(String id,String email, String ph_no, int terminal, String destination, String bTime, int driving_time, String seat){
		this.id=id;
		this.email=email;
		this.ph_no=ph_no;
		this.terminal=terminal;
		this.destination=destination;
		this.booking_time=Calendar.getInstance();
		this.seat=seat;
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date = new Date();
		Calendar now=Calendar.getInstance();
		try{
			date=(dateFormat.parse(""+now.get(Calendar.YEAR)+"-"+(now.get(Calendar.MONTH)+1)+"-"+now.get(Calendar.DATE)+" "+bTime));
		}catch(ParseException e){
			e.printStackTrace();
		}
		this.booking_time.setTime(date);
		
		this.arrived_time=Calendar.getInstance();
		this.arrived_time.setTime(this.booking_time.getTime());
		this.arrived_time.add(Calendar.MINUTE, driving_time);
	}
	
	public User(String id,String email, String ph_no, int terminal, String destination, String bTime, String aTime, String seat){
		this.id=id;
		this.email=email;
		this.ph_no=ph_no;
		this.terminal=terminal;
		this.destination=destination;
		this.booking_time=Calendar.getInstance();
		this.arrived_time=Calendar.getInstance();
		this.seat=seat;
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date = new Date();
		Calendar now=Calendar.getInstance();
		try{
			date=(dateFormat.parse(""+now.get(Calendar.YEAR)+"-"+(now.get(Calendar.MONTH)+1)+"-"+now.get(Calendar.DATE)+" "+bTime));
		}catch(ParseException e){
			e.printStackTrace();
		}
		this.booking_time.setTime(date);

		try{
			date=(dateFormat.parse(""+now.get(Calendar.YEAR)+"-"+(now.get(Calendar.MONTH)+1)+"-"+now.get(Calendar.DATE)+" "+aTime));
		}catch(ParseException e){
			e.printStackTrace();
		}
		this.arrived_time.setTime(date);
	}
	//예약 정보 출력
	public void print_ticket() {
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy년 MM월 dd일 hh시 mm분");
		String tmp_ter=this.terminal==-1 ? "" : enum_terminal.TERMINAL.values()[terminal].toString();
		
		System.out.println("============ticket============");
		System.out.print("id : "+id+
			   "\nterminal : "+tmp_ter+
			   "\ndestination : "+this.destination+
			   "\nbooking_time : "+ ((this.booking_time==null)  ?  " " : dateFormat.format(this.booking_time.getTime()))+
			   "arrived time : "+((this.arrived_time==null)  ?  " " : dateFormat.format(this.arrived_time.getTime())));
		System.out.println("\n==============================");
	}
	
	public String toString() {
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy년 MM월 dd일 hh시 mm분");
		String tmp_ter=this.terminal==-1 ? "" : enum_terminal.TERMINAL.values()[terminal].toString();
		return "id : "+id+
			   "\nemail : "+email+
			   "\nph_no : "+ph_no+
			   "\nterminal : "+tmp_ter+
			   "\ndestination : "+this.destination+
			   "\nbooking_time : "+ ((this.booking_time==null)  ?  " " : dateFormat.format(this.booking_time.getTime()))+
			   "\narrived time : "+((this.arrived_time==null)  ?  " " : dateFormat.format(this.arrived_time.getTime()))+
			   "\nseat : "+seat;
	}
}
