package Bus_Class;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import Interface_Inherit.Printable;
import Interface_Inherit.bookInfo;

public class Bus extends bookInfo implements Printable{
	public boolean[][] seats=new boolean[3][8];
	public int empty_seat=seats.length*seats[0].length;
	boolean full_C;
	public int benefit;
	public int price;
/*	상속받은 부분
	public int terminal;
	public String destination;
	public Calendar booking_time;
	public Calendar arrived_time;
	public String seat;
*/
	public String toString() {
		return ""+terminal+destination+booking_time.get(Calendar.HOUR)+" "+booking_time.get(Calendar.MINUTE)+" "+arrived_time.get(Calendar.HOUR)+" "+arrived_time.get(Calendar.MINUTE);
	}
	
	//생성자부
	public Bus() {}
	
	public Bus(int price, int terminal, String destination, String bTime, int driving_time){
		this.price=price;
		this.terminal=terminal;
		this.destination=destination;
		this.booking_time=Calendar.getInstance();
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		 try{
			 booking_time.setTime(dateFormat.parse(""+booking_time.get(Calendar.YEAR)+"-"+(booking_time.get(Calendar.MONTH)+1)+"-"+booking_time.get(Calendar.DATE)+" "+bTime));
		 }catch(ParseException e){
			 e.printStackTrace();
		 }
		 //도착시간 설정
		this.arrived_time=Calendar.getInstance();
		this.arrived_time.setTime(this.booking_time.getTime());
		this.arrived_time.add(Calendar.MINUTE, driving_time);
	}
	//버스 좌석 모양으로 출력
	public void print() {
		for(int i=0 ; i<this.seats[0].length ; i++)
			System.out.printf(" %5s   ", i);
		System.out.println("\n-------------------------------------------------------------------------");
		for(int i=0; i<this.seats.length ; i++){
			for(int j=0 ; j<this.seats[i].length ; j++)
				System.out.printf("| %5s  ",this.seats[i][j]);
			
			if(i==0){
				System.out.println("|");
				System.out.println("| \t\t\t\t\t\t\t\t\t|");
				continue;
			}
			
			System.out.println("|");
		}
		System.out.println("-------------------------------------------------------------------------");
		System.out.println();
	}
}
