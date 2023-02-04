package com.sof8.frame;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MaskingUtil {
	
	/* 사용자 아이디 마스킹 처리 */
	public static String getMaskedId(String id) {

		return id.replaceAll("(?<=.{3})." , "*");
	}
	// 요청 : user_id = 사용자아이디
	// 결과 : 사용****
	
	public static String getMaskedEmail(String email) {
	      /*
	      * 요구되는 메일 포맷
	      * {userId}@domain.com
	      * */
	      String regex = "\\b(\\S+)+@(\\S+.\\S+)";
	      Matcher matcher = Pattern.compile(regex).matcher(email);
	      if (matcher.find()) {
	         String id = matcher.group(1); // 마스킹 처리할 부분인 userId
	         /*
	         * userId의 길이를 기준으로 세글자 초과인 경우 뒤 세자리를 마스킹 처리하고,
	         * 세글자인 경우 뒤 두글자만 마스킹,
	         * 세글자 미만인 경우 모두 마스킹 처리
	         */
	         int length = id.length();
	         if (length < 3) {
	            char[] c = new char[length];
	            Arrays.fill(c, '*');
	            return email.replace(id, String.valueOf(c));
	         } else if (length == 3) {
	            return email.replaceAll("\\b(\\S+)[^@][^@]+@(\\S+)", "$1**@$2");
	         } else {
	            return email.replaceAll("\\b(\\S+)[^@][^@][^@]+@(\\S+)", "$1***@$2");
	         }
	      }
	      return email;
	   }

}
