/*********************************************************************
사용법

생성자 생성할 때 매개 변수로 charSet 설정
예를 들어 "utf-8" 이런식으로
encode 메소드는 인코딩
decode 메소드는 디코딩

 둘다 반환값은 String 형


*********************************************************************/

package dync.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.net.URLDecoder;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
public class ConvertChar {
	
	private String enc;
	
	public ConvertChar(String enc){
		this.enc = enc;
	}
	
	final public String encode(String str) {
		String resultUrlEncode = null;
		String resultStr = null;
		
		try {
			resultUrlEncode = URLEncoder.encode(str,enc);
		} catch (UnsupportedEncodingException e) {
			System.out.println("인코딩 오류");
			e.printStackTrace();
		}
		resultStr = Base64.encode(resultUrlEncode.getBytes());
		
		return resultStr;
	}
	
	final public String decode(String str){
		String resultBase64Decode = null;
		String resultStr = null;
		
		resultBase64Decode = new String(Base64.decode(str));
		try {
			resultStr = URLDecoder.decode(resultBase64Decode, enc);
		} catch (UnsupportedEncodingException e) {
			System.out.println("디코딩 오류");
			e.printStackTrace();
		}
		
		return resultStr;
	}
}
