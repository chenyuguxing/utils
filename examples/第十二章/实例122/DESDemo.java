package 第十二章.实例122;
package security;
import java.security.*;
import javax.crypto.*;
public class DESDemo {
	public static void main(String[] args) throws Exception {
		// 明文
		byte[] plainText = "Hello World!".getBytes();
		// 得到DES私钥
		System.out.println("\nStart generating DES key");
		KeyGenerator keyGen = KeyGenerator.getInstance("DES");
		keyGen.init(56);
		Key key = keyGen.generateKey();
		System.out.println("Finish generating DES key");
		// 得到DES cipher 对象，并打印出算法的提供者
		Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
		System.out.println("\n" + cipher.getProvider().getInfo());
		// 使用密钥对明文进行加密
		System.out.println("\nStart encryption");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] cipherText = cipher.doFinal(plainText);
		System.out.println("Finish encryption: ");
		System.out.println(new String(cipherText, "UTF8"));
		// 使用同一把密钥对密文进行解密
		System.out.println("\nStart decryption");
		cipher.init(Cipher.DECRYPT_MODE, key);
		byte[] newPlainText = cipher.doFinal(cipherText);
		System.out.println("Finish decryption: ");
		System.out.println(new String(newPlainText, "UTF8"));
	}
}
