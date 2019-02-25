package com.kking.common.utils;

import org.apache.commons.lang3.time.DateUtils;

import java.security.MessageDigest;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

public class MD5Util {

	public MD5Util() {
	}

	public static String MD5(String s) {
		return MD5(s.getBytes());
	}

	public static String MD5(byte[] btInput) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'A', 'B', 'C', 'D', 'E', 'F' };
		try {
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			mdInst.update(btInput);
			byte md[] = mdInst.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 15];
				str[k++] = hexDigits[byte0 & 15];
			}

			return new String(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String args1[]) {
		try {
			Date date = DateUtils.parseDate("1988/07/15 01", Locale.CHINESE,"yyyy/MM/dd HH");
			System.out.println(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
