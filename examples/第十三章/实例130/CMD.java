package 第十三章.实例130;

import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;

public class CMD extends MIDlet implements CommandListener {
	private Display display;
	private Form props;
	// MIDP总共八个命令都包含在此
	private Command backCommand = new Command("BACK", Command.BACK, 2);
	private Command cancelCommand = new Command("CANCEL", Command.CANCEL, 1);
	private Command exitCommand = new Command("EXIT", Command.EXIT, 1);
	private Command helpCommand = new Command("HELP", Command.HELP, 1);
	private Command itemCommand = new Command("ITEM", Command.ITEM, 1);
	private Command okCommand = new Command("OK", Command.OK, 1);
	private Command screenCommand = new Command("SCREEN", Command.SCREEN, 1);
	private Command stopCommand = new Command("STOP", Command.STOP, 1);

	public CMD() {
		display = Display.getDisplay(this);
	}

	public void startApp() {
		props = new Form("Hello World");
		props.append("Hello World!\n");
		props.addCommand(backCommand);
		props.addCommand(cancelCommand);
		props.addCommand(exitCommand);
		props.addCommand(helpCommand);
		props.addCommand(itemCommand);
		props.addCommand(okCommand);
		props.addCommand(screenCommand);
		props.addCommand(stopCommand);
		props.setCommandListener(this);
		display.setCurrent(props);
	}

	public void showScreen(String cmd) {
		Form form = new Form("show cmd");
		form.append(cmd);
		form.addCommand(exitCommand);
		form.setCommandListener(this);
		display.setCurrent(form);
	}
    
	// 对不同的命令进行事件处理
	public void commandAction(Command c, Displayable s) {
		if (c == exitCommand) {
			destroyApp(false);
			notifyDestroyed();
		} else if (c == helpCommand) {
			showScreen("help");
		} else if (c == backCommand) {
			showScreen("back");
		} else if (c == cancelCommand) {
			showScreen("cancel");
		} else if (c == itemCommand) {
			showScreen("item");
		} else if (c == okCommand) {
			showScreen("ok");
		} else if (c == screenCommand) {
			showScreen("screen");
		}
		if (c == stopCommand) {
			showScreen("stop");
		}
	}

	public void destroyApp(boolean unconditional) {
	}

	public void pauseApp() {
		display.setCurrent(null);
		props = null;
	}

}
