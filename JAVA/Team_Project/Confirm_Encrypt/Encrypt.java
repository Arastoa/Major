package Confirm_Encrypt;

import java.security.*;

public class Encrypt {
	//비밀번호 암호화해서 db에 저장할 SHA256 암호화. 
		public static String SHA256(String pwd) {
			String str_sha="";	//결과 저장할 스트링
			try {
				MessageDigest sha=MessageDigest.getInstance("SHA-256");
				sha.update((pwd+"DSC").getBytes());	//DSC를 키값으로 주고 pwd의 바이트값을 읽어들임
				
				byte[] digest = sha.digest();	//넘겨받은 바이트값을 sha256으로 해쉬시켜 byte배열로 만들어 digest에 반환
				
				//음수값을 만들지 않기 위해 0xFF와 &연산 (unsigned 효과) ==> 따로 설명하기
		        for (int i=0; i<digest.length; i++)	
		        	str_sha += Integer.toHexString(digest[i] & 0xFF).toUpperCase();
				
			} catch (NoSuchAlgorithmException e) {		
				e.printStackTrace();
			}
			return str_sha;
		}
}
