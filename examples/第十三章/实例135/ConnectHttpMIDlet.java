package 第十三章.实例135;
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import javax.microedition.io.*;
import java.io.*;

public class ConnectHttpMIDlet extends MIDlet implements CommandListener {
	private Command exitCommand;
	private Command sendCommand;
	private Command backCommand;
	private Display display;
	private String defaultURL = "http://www.java.sun.com";
	private Form mainForm, resultForm;
	private TextField URL;
	private StringItem resultItem;
	
	public ConnectHttpMIDlet() {
		display = Display.getDisplay(this);
		exitCommand = new Command("离开", Command.EXIT, 1);
		sendCommand = new Command("送出", Command.OK, 1);
		backCommand = new Command("上页", Command.OK, 1);
	}

	public void startApp() {
		URL = new TextField(null, defaultURL, 250, TextField.URL);
		mainForm = new Form("联机数据");
		mainForm.append(URL);
		mainForm.addCommand(sendCommand);
		mainForm.addCommand(exitCommand);
		mainForm.setCommandListener(this);
		display.setCurrent(mainForm);
	}

	public void pauseApp() {
	}

	public void destroyApp(boolean unconditional) {
	}

	public void commandAction(Command c, Displayable s) {
		if (c == sendCommand) {
			String result = "";
			resultItem = new StringItem(null, result);
			resultForm = new Form("结果");
			String URLString = URL.getString();
			try { // 连接URL，返回字符串信息
				result = requestUsingGET(URLString);
			} catch (IOException e) {
				result = "联机失败";
			}
			resultItem.setText(result);
			resultForm.append(resultItem);
			resultForm.addCommand(backCommand);
			resultForm.setCommandListener(this);
			display.setCurrent(resultForm);
		} else if (c == backCommand) {
			URL.setString(defaultURL);
			display.setCurrent(mainForm);
		} else {
			destroyApp(false);
			notifyDestroyed();
		}
	}

	// 建立Http连接
	private String requestUsingGET(String URLString) throws IOException {
		HttpConnection hpc = null;
		String content = "";
		try {
			hpc = (HttpConnection) Connector.open(URLString);
			content += "URL: " + hpc.getURL() + "\n";
			content += "Protocol: " + hpc.getProtocol() + "\n";
			content += "Host: " + hpc.getHost() + "\n";
			content += "Port: " + hpc.getPort() + "\n";
			content += "File: " + hpc.getFile() + "\n";
			content += "Query: " + hpc.getQuery() + "\n";
			content += "reference: " + hpc.getRef() + "\n";
			content += "request method: " + hpc.getRequestMethod();
		} catch (IOException e) {
			System.out.println("error");
		}
		try {
			if (hpc != null)
				hpc.close();
		} catch (IOException e2) {
		}
		return content;
	}
}
