package Interface_Inherit;

import java.util.Calendar;

//버스와 공통되는 변수들을 한데 묶어줘서 상속함. 인터페이스로 쓰고 싶었지만 변수라 final로 써야하기에 클래스로 상속시킴
public class bookInfo {
	public int terminal;
	public String destination;
	public Calendar booking_time;
	public Calendar arrived_time;
	public String seat;
}
