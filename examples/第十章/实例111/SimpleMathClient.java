package 第十章.实例111;
package test;
import java.io.*;
import java.net.*;
import java.util.*;
import org.apache.soap.util.xml.*;
import org.apache.soap.*;
import org.apache.soap.rpc.*;

public class SimpleMathClient {
	
	public static void main(String[] args) throws Exception {
		System.err.println("SOAP call testing");
		double value = Math.random();
		SimpleMathClient smc = new SimpleMathClient();
		//调用远程的SOAP服务
		double returnValue = smc.doRequest(value);
		System.err.println("the sin value of " + value + "is: " + returnValue);
	}
	
	public double doRequest(double value) throws Exception {
		// Build the call.
		Call call = new Call();
		//设置远程对象的URI
		call.setTargetObjectURI("urn:test.math.sin");
		//设置调用的方法名
		call.setMethodName("getSinValue");
		//设置编码风格
		call.setEncodingStyleURI(Constants.NS_URI_SOAP_ENC);
		//设置方法调用的参数
		Vector params = new Vector();
		params.addElement(
			new Parameter("input", double.class, new Double(value), null));
		call.setParams(params);
		//发送RPC请求
		Response resp =
			call.invoke(
				new URL("http://127.0.0.1:8080/soap/servlet/rpcrouter"), "");
		if (resp.generatedFault()) { //远程调用出错处理
			Fault fault = resp.getFault();
			System.out.println("the call failed: ");
			System.out.println("  Fault Code   = " + fault.getFaultCode());
			System.out.println("  Fault String = " + fault.getFaultString());
			return 0.0d;
		} 
		else { //调用成功，获取返回值
			Parameter result = resp.getReturnValue();
			return ((Double) result.getValue()).doubleValue();
		}
	}
}
