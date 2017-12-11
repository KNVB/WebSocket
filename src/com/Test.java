package com;

import java.io.UnsupportedEncodingException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class Test {

	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException, UnsupportedEncodingException 
	{
		String stored="uU6k7LTtRabXYzv+7K9YQwbNd+4h4hODAYH1h81CMv5ZiDHjtIRE9OS2o7NmYmifSxmQGWDeeM//PjDs8za/ZWyFKQ1NxjemiQJ7VlByQ5rdwpQL1xn5Yw6oIB5x52tspzJLjXYJXwZD+CWLPHPqdlDdqMc0yrGXIHNEiHkZmrU=";
		byte[] data = Base64.getDecoder().decode(stored);
	
		org.bouncycastle.asn1.pkcs.RSAPublicKey pkcs1PublicKey = org.bouncycastle.asn1.pkcs.RSAPublicKey.getInstance(data);
		
		/*X509EncodedKeySpec spec = new X509EncodedKeySpec(data);
		KeyFactory fact = KeyFactory.getInstance("RSA");
		PublicKey pk=fact.generatePublic(spec);*/
	}

}
