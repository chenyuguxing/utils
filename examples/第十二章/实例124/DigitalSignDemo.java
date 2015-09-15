package 第十二章.实例124;
// DigitalSignDemo.java
package security;
import java.io.*;
import java.security.*;
import java.security.interfaces.*;

public class DigitalSignDemo{
	
	public static void generateKey() {
		try {
			KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
			kpg.initialize(1024);
			KeyPair kp = kpg.genKeyPair();
			PublicKey pbkey = kp.getPublic();
			PrivateKey prkey = kp.getPrivate();
			
			// 保存公钥
			FileOutputStream f1 = new FileOutputStream("pubkey.dat");
			ObjectOutputStream b1 = new ObjectOutputStream(f1);
			b1.writeObject(pbkey);

			// 保存私钥
			FileOutputStream f2 = new FileOutputStream("privatekey.dat");
			ObjectOutputStream b2 = new ObjectOutputStream(f2);
			b2.writeObject(prkey);
		} catch (Exception e) {
		}
	}
	
	public static void sign() throws Exception{
		//获取要签名的数据
		FileInputStream f = new FileInputStream("msg.dat");
		int num = f.available();
		byte[] data = new byte[num];
		f.read(data);
		
		// 获取私钥
		FileInputStream f2 = new FileInputStream("privatekey.dat");
		ObjectInputStream b = new ObjectInputStream(f2);
		RSAPrivateKey prk = (RSAPrivateKey)b.readObject();
		Signature s = Signature.getInstance("MD5WithRSA");
		s.initSign(prk);
		s.update(data);
		System.out.println("");
		byte[] signeddata = s.sign();
		
		// 打印签名
		for(int i=0; i<data.length; i++){
			System.out.print(signeddata[i] + ",");
		}
		
		// 保存签名
		FileOutputStream f3 = new FileOutputStream("sign.dat");
		f3.write(signeddata);
	}
	
	public static void checkSign() throws Exception{
		FileInputStream f = new FileInputStream("msg.dat");
		int num = f.available();
		byte[] data = new byte[num];
		f.read(data);
		
		// 读签名
		FileInputStream f2 = new FileInputStream("sign.dat");
		int num2 = f2.available();
		byte[] signeddata = new byte[num2];
		f2.read(signeddata);
		
		// 读公钥
		FileInputStream f3 = new FileInputStream("pubkey.dat");
		ObjectInputStream b = new ObjectInputStream(f3);
		RSAPublicKey pbk = (RSAPublicKey)b.readObject();
		
		// 获取对象
		Signature s = Signature.getInstance("MD5WithRSA");
		
		//初始化
		s.initVerify(pbk);
		
		// 传入原始数据
		s.update(data);
		boolean ok = false;
		try{
			//用签名验证原始数据
			ok = s.verify(signeddata);
			System.out.println(ok);
		}
		catch(SignatureException e){
			System.out.println(e);
		}
		System.out.println("Check Over!");
	}
	
	public static void main(String args[]) throws Exception{
		sign();
		checkSign();
	}
}
