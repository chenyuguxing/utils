package 第十三章.实例129;
import javax.microedition.midlet.MIDlet;
import javax.microedition.lcdui.*;

public class HelloWorld extends MIDlet
{
	private Display display;
	public HelloWorld() {
	}

	public void startApp() {
		display = Display.getDisplay(this);
		TextBox t = new TextBox("Demo App", "Hello World", 256, 0);
		display.setCurrent(t);
	}

	public void pauseApp() {
	}

	public void destroyApp(boolean unconditional) {
	}
}
