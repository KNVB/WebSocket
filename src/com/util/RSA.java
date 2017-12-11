package com.util;
import javax.crypto.*;

import java.security.*;
import java.math.BigInteger;
import java.security.interfaces.*;
public class RSA {
	private KeyPair pair = null;
	private RSAPrivateKey privateKey=null;
	private RSAPublicKey publicKey=null;
	/**
	 * object constructor
	 * @param modulusSize (i.e key size,e.g. 1024 bit)
	 * @throws Exception
	 */
	public RSA(int modulusSize)throws Exception
	{
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA",new org.bouncycastle.jce.provider.BouncyCastleProvider());
        keyGen.initialize(modulusSize, new SecureRandom());		
	 	pair = keyGen.generateKeyPair();
	 	privateKey=(RSAPrivateKey)pair.getPrivate();
		publicKey=(RSAPublicKey)pair.getPublic();
	}
	/**
	 * get Public exponent
	 * @return get public exponent
	 */
	public String getPublicExponent()
	{
		return this.publicKey.getPublicExponent().toString(16);
	}
	/**
	 * get Public Modulus
	 * @return get Public Modulus
	 */
	public String getPublicModulus()
	{
		return this.publicKey.getModulus().toString(16);
	}
	
	/**
	 * get Private Exponent
	 * @return get Private Exponent
	 */
	public String getPrivateExponent()
	{
		return this.privateKey.getPrivateExponent().toString(16);
	}
	/**
	 * get Private Modulus
	 * @return  get Private Modulus
	 */
	public BigInteger getPrivateModulus()
	{
		return this.privateKey.getModulus();
	}
	/**
	 * decode data array
	 * @param cleartext encoded byte array
	 * @return decoded byte array
	 * @throws Exception
	 */
	public byte[] decode(byte[] cleartext)throws Exception
	{
		Cipher rsaCipher;
 		// Create the cipher
		rsaCipher = Cipher.getInstance("RSA",new org.bouncycastle.jce.provider.BouncyCastleProvider());
		rsaCipher.init(Cipher.DECRYPT_MODE, this.privateKey);
		byte[] cleartext1 =rsaCipher.doFinal(cleartext);
		return cleartext1;		
	}	
	/**
	 * encode raw data array
	 * @param data raw data array
	 * @return encoded data array
	 * @throws Exception
	 */
	public byte[] encode(byte[] data) throws Exception
	{
		Cipher rsaCipher;
		rsaCipher = Cipher.getInstance("RSA",new org.bouncycastle.jce.provider.BouncyCastleProvider());
		rsaCipher.init(Cipher.ENCRYPT_MODE, this.publicKey);
		byte[] ciphertext = rsaCipher.doFinal(data);
		return ciphertext;
	}
	/**
     * Convert hex decimal string to byte array
     * @param data hex decimal string
     * @return byte array
     */
    public byte[] hexStringToByteArray(String data) {
		int k = 0;
		byte[] results = new byte[data.length() / 2];
		for (int i = 0; i < data.length();) {
			results[k] = (byte) (Character.digit(data.charAt(i++), 16) << 4);
			results[k] += (byte) (Character.digit(data.charAt(i++), 16));
			k++;
		}
 
		return results;
	}
  //Converting a bytes array to string of hex character
    public String byteArrayToHexString(byte[] b) {
     int len = b.length;
     String data = new String();

     for (int i = 0; i < len; i++){
     data += Integer.toHexString((b[i] >> 4) & 0xf);
     data += Integer.toHexString(b[i] & 0xf);
     }
     return data;
    }
	public static void main(String[] args) throws Exception 
	{
		String message = "abcdefghijklmnopqrstuvwxyz1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		message =message +message;
		message =message +message;
		RSA myRSA=new RSA(1024);
		myRSA.encode(message.getBytes());
		System.out.println(message.length());
	}

}
