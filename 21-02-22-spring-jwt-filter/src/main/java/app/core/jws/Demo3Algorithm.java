package app.core.jws;

import io.jsonwebtoken.SignatureAlgorithm;

public class Demo3Algorithm {

	public static void main(String[] args) {

		System.out.println(SignatureAlgorithm.HS256); 
		System.out.println(SignatureAlgorithm.HS256.getDescription()); 
		
		//HMAC
		// hash based Message Authentication code
	}

}
