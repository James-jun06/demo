package com.cmcc.hy.learnDemo.Utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

public class RandomUtil {

	// public static void main(String[] args) {
	//
	// Map<String, Object> msg = new HashMap<String, Object>();
	// Map<String, Object> msgs = new HashMap<String, Object>();
	// msg.put("msgtype", "text");
	// msgs.put("content", "qwerqwer");
	// msg.put("text",msgs);
	// System.out.println(JSONObject.toJSONString(msg));
	//
	//// System.out.println(RandomUtil.CreateDigest("yqx_open_zq"));
	//
	// }

	public String createToken(String host, String apName, String secret) {
		String authenticator = "JTYQXToken:";
		try {
			Date now = new Date();

			String digest = CreateDigest(apName);

			String orgToken = "Host=" + host + ";" + "CurrentTime=" + now.getTime() + "$" + digest;
			String transformation = "DESede/CBC/PKCS5Padding";
			Cipher cipher = Cipher.getInstance(transformation);

			byte[] iv = new byte[] { 93, 81, (byte) 122, (byte) 233, 47, 50, 17, (byte) 103 };

			IvParameterSpec ivparam = new IvParameterSpec(iv);

			SecretKey secretKey = new SecretKeySpec(getKeyBytes(secret), "DESede");

			cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivparam);

			byte[] encriptText = cipher.doFinal(orgToken.getBytes());

			authenticator = MD5(openFileToString(encriptText)).toUpperCase() + makeToken();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return authenticator;
	}

	public String openFileToString(byte[] _bytes) {
		String file_string = "";

		for (int i = 0; i < _bytes.length; i++) {
			file_string += (char) _bytes[i];
		}

		return file_string;
	}

	public static byte[] getKeyBytes(String keyString) {

		byte[] b = new byte[24];

		byte[] key = keyString.getBytes();

		int count = keyString.getBytes().length;

		for (int i = 0; i < count; i++) {
			b[i] = key[i];
		}
		for (int i = count; i < 24; i++) {
			b[i] = 0x20;
		}
		return b;
	}

	private static String CreateDigest(String src) {
		// TODO Auto-generated method stub
		String ret = "";
		try {
			// Hash算法
			MessageDigest sha = MessageDigest.getInstance("SHA-1");
			sha.update(src.getBytes());
			// Base64加密
			ret = Base64.encodeToString(sha.digest(), 0);
		} catch (Exception e) {
		}
		return ret;
	}

	/**
	 * 
	 * @return
	 */
	public static String makeToken() {
		// checkException
		String token = (System.currentTimeMillis() + new Random().nextInt(999999999)) + "";
		// 数据指纹 128位长 16个字节 md5
		try {
			MessageDigest md = MessageDigest.getInstance("md5");
			byte [] md5 = md.digest(token.getBytes());

			return Base64.encodeToString(md5, 0);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	// 根据指定长度生成字母和数字的随机数
	// 0~9的ASCII为48~57
	// A~Z的ASCII为65~90
	// a~z的ASCII为97~122
	public static String createRandomCharData(int length) {
		StringBuilder sb = new StringBuilder();
		Random rand = new Random();// 随机用以下三个随机生成器
		Random randdata = new Random();
		Random randnub = new Random();

		int data = 0;
		for (int i = 0; i < length; i++) {
			int index = rand.nextInt(3);
			// 目的是随机选择生成数字，大小写字母
			switch (index) {
			case 0:
				data = randdata.nextInt(10);// 仅仅会生成0~9
				sb.append(data);
				break;
			case 1:
				data = randdata.nextInt(26) + 65;// 保证只会产生65~90之间的整数
				if (randnub.nextInt(101) % 19 == 0) {
					sb.append((char) data + "-");
				} else {
					sb.append((char) data);
				}
				break;
			case 2:
				data = randdata.nextInt(26) + 97;// 保证只会产生97~122之间的整数
				if (randnub.nextInt(101) % 17 == 0) {
					sb.append((char) data + "_");
				} else {
					sb.append((char) data);
				}
				break;
			default:
				break;
			}
		}
		String result = sb.toString();
		return result.toLowerCase();
	}

	// 根据指定长度生成纯数字的随机数
	public String createnumdata(int length) {
		StringBuilder sb = new StringBuilder();
		Random rand = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(rand.nextInt(10));
		}
		String data = sb.toString();

		return data;
	}

	public static String md5Digest32(String input) {
		String str = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(input.getBytes());
			byte[] b = md.digest();

			int i;

			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0) {
					i += 256;
				}
				if (i < 16) {
					buf.append("0");
				}
				buf.append(Integer.toHexString(i));
			}
			str = buf.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();

		}

		return str;
	}

	public static String MD5(String sourceStr) {
		String result = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			try {
				md.update(sourceStr.getBytes("UTF-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			byte[] b = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0) {
					i += 256;
				}
				if (i < 16) {
					buf.append("0");
				}
				buf.append(Integer.toHexString(i));
			}
			result = buf.toString();
		} catch (NoSuchAlgorithmException e) {
			System.out.println(e);
		}
		return result;
	}

	public static String authentication(Map<String, Object> srcData) {
		// 排序，根据keyde 字典序排序
		if (null == srcData) {

		}
		List<Entry<String, Object>> list = new ArrayList<Entry<String, Object>>(srcData.entrySet());
		Collections.sort(list, new Comparator<Entry<String, Object>>() {
			// 升序排序
			public int compare(Entry<String, Object> o1, Entry<String, Object> o2) {
				return o1.getKey().compareTo(o2.getKey());
			}
		});
		StringBuffer srcSb = new StringBuffer();
		for (Entry<String, Object> srcAtom : list) {
			srcSb.append(String.valueOf(srcAtom.getValue()));
		}
		System.out.println("身份验证加密前字符串：" + srcSb.toString());
		// 计算token
		String token = MD5(srcSb.toString());
		// System.out.println(cToken);//for test
		return token;
	}

	/**
	 * 生活问卷的加密
	 * 
	 * @param user
	 * @param email
	 * @param mobile
	 * @return
	 */
	public static String md5Code(String user, String email, String mobile) {

		String key = "f105e94ef73d413ada9562984e86c2b6";
		@SuppressWarnings("rawtypes")
		Map<String, Comparable> map = new HashMap<String, Comparable>();
		map.put("user", user);
		map.put("site", "358");
		long time = new Date().getTime();

		map.put("ctime", time);
		map.put("email", email);
		map.put("mobile", mobile);
		List<String> keyList = new ArrayList<String>();

		keyList.add("user");
		keyList.add("site");
		keyList.add("ctime");
		keyList.add("email");
		keyList.add("mobile");
		String md5_param = sortParam(keyList, map) + key;
		md5_param = MD5(md5_param);
		// md5 equals md5_param + key
		md5_param = "/?site=358&user=" + user + "&ctime=" + time + "&email=" + email + "&mobile=" + mobile + "&md5="
				+ md5_param;
		return md5_param;
	}

	private static String sortParam(List<String> keyList, @SuppressWarnings("rawtypes") Map<String, Comparable> map) {
		String md5_param = "";
		// sort before
		System.out.println(keyList);
		Collections.sort(keyList);
		// sort after
		System.out.println(keyList);
		for (int i = 0; i < keyList.size(); i++) {
			md5_param += map.get(keyList.get(i));
		}
		return md5_param;
	}

	public String createAppkey(String appId) {
		RandomUtil randomutil = new RandomUtil();
		int leg = appId.length();
		String num = randomutil.createnumdata(5 - leg);
		String charm = randomutil.createRandomData(2);

		// TODO Auto-generated method stub
		return "1" + appId + num + charm;
	}

	public String createRandomData(int length) {
		StringBuilder sb = new StringBuilder();
		Random rand = new Random();// 随机用以下三个随机生成器
		Random randdata = new Random();
		Random randnub = new Random();

		int data = 0;
		for (int i = 0; i < length; i++) {
			int index = rand.nextInt(1);
			// 目的是随机选择生成数字，大小写字母
			switch (index) {
			case 0:
				data = randdata.nextInt(26) + 97;// 保证只会产生97~122之间的整数
				if (randnub.nextInt(101) % 17 == 0) {
					sb.append((char) data);
				} else {
					sb.append((char) data);
				}
				break;
			default:
				break;
			}
		}
		String result = sb.toString();
		return result.toLowerCase();
	}
}
