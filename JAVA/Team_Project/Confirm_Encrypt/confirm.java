package Confirm_Encrypt;

public class confirm {
	//주로 회원가입할때 많이 사용
	//한글 있는지 확인 있으면 false 반환
	public static boolean isContain_korean(String str) {
		
		for(int i=0 ; i<str.length() ; i++){
			char ch=str.charAt(i);
			
			if(ch >=0xAC00   && ch<=0xD7A3) //한글범위안에 스트링이 있으면
				return true;
		}	
		return false;
	}
	
	//@있는지 확인 있으면 true반환
	public static boolean isContain_at(String str) {
		
		for(int i=0 ; i<str.length() ; i++){
			char ch=str.charAt(i);
			
			if(ch=='@')	//이메일
				return true;
		}
		return false;
	}
	//-- 하이픈 두개 있는지 확인 및 핸드폰 첫자리 번호 확인
	public static boolean isContain_2Hypen(String str) {
		int count=0;
		if(!(str.contains("010") || str.contains("011") || str.contains("017") || str.contains("019") ||str.contains("016")
				|| str.contains("018")))
			return false;
		for(int i=0 ; i<str.length() ; i++){
			char ch=str.charAt(i);
			
			if(ch=='-') //-을 포함하고 있으면 카운터 증가
				count++;
		}
		
		if(count==2)
			return true;
		else
			return false;
	}
}
