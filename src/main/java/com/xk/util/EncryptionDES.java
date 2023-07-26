package com.xk.util;



import java.security.Key;
import java.security.Security;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.crypto.Cipher;

public class EncryptionDES {
	
	 private static Cipher encryptCipher = null;
	 private static Cipher decryptCipher = null;
	 private String strKey = new SimpleDateFormat("yyyyMMdd").format(new Date());
   
	/**
	 * 
	 *********************************************************.<br>
	 * [方法] byteArr2HexStr <br>
	 * [描述] byte转String <br>
	 * [参数] TODO(对参数的描述) <br>
	 * [返回] String <br>
	 * [时间] 2016-12-19 上午10:14:03 <br>
	 *********************************************************.<br>
	 */
   public static String byteArr2HexStr(byte[] arrB) throws Exception {
	   int iLen = arrB.length;
	   StringBuffer sb = new StringBuffer(iLen * 2);
      for (int i = 0; i < iLen; i++) {
          int intTmp = arrB[i];
           // 把负数转换为正数
           while (intTmp < 0) {
              intTmp = intTmp + 256;
           }
           //前面补0
           if (intTmp < 16) {
              sb.append("0");
           }
           sb.append(Integer.toString(intTmp, 16));
      }
	   return sb.toString();
    }
  
	/**
	 * 
	 *********************************************************.<br>
	 * [方法] hexStr2ByteArr <br>
	 * [描述] 将字符串转成byte数组 <br>
	 * [参数] TODO(对参数的描述) <br>
	 * [返回] byte[] <br>
	 * [时间] 2016-12-19 上午10:13:07 <br>
	 *********************************************************.<br>
	 */
    public static byte[] hexStr2ByteArr(String strIn) throws Exception {
        byte[] arrB = strIn.getBytes();
        int iLen = arrB.length;
        byte[] arrOut = new byte[iLen / 2];
        for (int i = 0; i < iLen; i = i + 2) {
            String strTmp = new String(arrB, i, 2);
            arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
        }
        return arrOut;
    }

    /**
     * 
     *********************************************************.<br>
     * [描述] 加密构造方法  <br>
     *********************************************************.<br>
     */
    public EncryptionDES() throws Exception {
        Security.addProvider(new com.sun.crypto.provider.SunJCE());
        Key key = getKey(strKey.getBytes());

        encryptCipher = Cipher.getInstance("DES");
        encryptCipher.init(Cipher.ENCRYPT_MODE, key);
 
        decryptCipher = Cipher.getInstance("DES");
        decryptCipher.init(Cipher.DECRYPT_MODE, key);
    }
    
   /**
    * 
    *********************************************************.<br>
    * [方法] encrypt <br>
    * [描述] 加密字节数组 <br>
    * [参数] TODO(对参数的描述) <br>
    * [返回] byte[] <br>
    * [时间] 2016-12-19 上午10:11:28 <br>
    *********************************************************.<br>
    */
    public static byte[] encrypt(byte[] arrB) throws Exception {
        return encryptCipher.doFinal(arrB);
    }
    
    /**
     * 
     *********************************************************.<br>
     * [方法] encrypt <br>
     * [描述] 加密字符串 <br>
     * [参数] TODO(对参数的描述) <br>
     * [返回] String <br>
     * [时间] 2016-12-19 上午10:11:12 <br>
     *********************************************************.<br>
     */
    public static String encrypt(String strIn) throws Exception {
        return byteArr2HexStr(encrypt(strIn.getBytes("UTF-8")));
    }
    
	/**
	 * 
	 *********************************************************.<br>
	 * [方法] decrypt <br>
	 * [描述] 解密字节数组 <br>
	 * [参数] TODO(对参数的描述) <br>
	 * [返回] byte[] <br>
	 * [时间] 2016-12-19 上午10:10:53 <br>
	 *********************************************************.<br>
	 */
    public static byte[] decrypt(byte[] arrB) throws Exception {
        return decryptCipher.doFinal(arrB);
    }

	/**
	 * 
	 *********************************************************.<br>
	 * [方法] decrypt <br>
	 * [描述] 解密字符串 <br>
	 * [参数] TODO(对参数的描述) <br>
	 * [返回] String <br>
	 * [时间] 2016-12-19 上午10:10:28 <br>
	 *********************************************************.<br>
	 */
    public static String decrypt(String strIn) throws Exception {
        return new String(decrypt(hexStr2ByteArr(strIn)));
    }
	 /**
	  * 
	  *********************************************************.<br>
	  * [方法] getKey <br>
	  * [描述] 从指定字符串生成密钥  <br>
	  * [参数] TODO(对参数的描述) <br>
	  * [返回] Key <br>
	  * [时间] 2016-12-19 上午10:10:15 <br>
	  *********************************************************.<br>
	  */
    private Key getKey(byte[] arrBTmp) throws Exception {
	    byte[] arrB = new byte[8];
	    for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
	    	arrB[i] = arrBTmp[i];
	    }
	    // 生成密钥
	    Key key = new javax.crypto.spec.SecretKeySpec(arrB, "DES");
	   	return key;
    }
    public static void main(String[] args) throws Exception {
		System.out.println(new EncryptionDES().encrypt("冠儒"));
	}
    
}