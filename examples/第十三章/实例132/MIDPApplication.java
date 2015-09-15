package 第十三章.实例132;
import java.io.*;
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;

public class MIDPApplication extends MIDlet implements CommandListener {
	Ticker newsTicker; //设置头条新闻用
	private List menuList; //主菜单
	private List luckyList; //一周运势菜单
	private List commList; //通讯簿菜单
	private Command exitCommand; //离开
	private Command execCommand; //执行
	private Command menuCommand; //主目录
	private Command commCommand; //通讯簿
	private Command luckyCommand; //一周运势
	private Command saveCommand; //存储
	String[] menuItem = { "通讯簿", "一周运势" };
	String[] commItem = { "新增", "查询", "修改", "删除" };
	String[] luckyItem = { "金牛座", "处女座", "摩羯座", "射手座", "牡羊座", "狮子座", "双鱼座",
			"巨蟹座", "天蝎座", "双子座", "天秤座", "水瓶座" };
	Image[] menuImage = { createImage("/Sokoban.png"),
			createImage("/Tiles.png") };
	private Display display;
	private String currentScreen = "";
	
	public MIDPApplication() {
		display = Display.getDisplay(this);
		newsTicker = new Ticker("动感地带国庆充值大奉送，充300送300，热烈庆祝北京申奥成功！");
		exitCommand = new Command("离开", Command.EXIT, 1);
		execCommand = new Command("执行", Command.OK, 1);
		menuCommand = new Command("主目录", Command.SCREEN, 1);
		commCommand = new Command("通讯簿", Command.SCREEN, 1);
		luckyCommand = new Command("运势", Command.SCREEN, 1);

	}

	public void startApp() {
		menuList = new List(null, List.IMPLICIT, menuItem, menuImage);
		menuList.setTicker(newsTicker);
		menuList.addCommand(exitCommand);
		menuList.setCommandListener(this);
		display.setCurrent(menuList);
		currentScreen = "主目录";
	}

	public void pauseApp() {
	}

	public void destroyApp(boolean unconditional) {
	}

	// “命令”事件处理
	public void commandAction(Command c, Displayable s) {
		if (c == exitCommand) {
			destroyApp(false);
			notifyDestroyed();
		}

		if (c == List.SELECT_COMMAND) {
			if (currentScreen == "主目录") {
				int itemIndex = menuList.getSelectedIndex();
				switch (itemIndex) {
				case 0: {
					doCommunication();
					break;
				}
				case 1:
					doLucky();
				}
			} else if (currentScreen == "通讯簿") {
				int itemIndex = commList.getSelectedIndex();
				switch (itemIndex) {
				case 0: {
					doAppend();
					break;
				}
				case 1: {
					doQuery();
					break;
				}
				case 2: {
					doModification();
					break;
				}
				case 3: {
					doDelete();
					break;
				}
				}

			} else {

			}
		}

		if (c == menuCommand) {
			display.setCurrent(menuList);
			currentScreen = "主目录";
		}

		if (c == commCommand) {
			doCommunication();

		}

		if (c == luckyCommand) {
			doLucky();
		}

		if (c == menuCommand) {
			doMenu();

		}

		if (c == execCommand) {
			if (currentScreen == "一周运势") {
				showLucky();
			}
		}
	}

	private Image createImage(String name) {
		Image aImage = null;
		try {
			aImage = Image.createImage(name);
		} catch (IOException e) {
		}
		return aImage;
	}

	// 显示主目录
	private void doMenu() {
		currentScreen = "主目录";
		display.setCurrent(menuList);
	}

	// 显示通讯录目录
	private void doCommunication() {
		Image[] commIcon = { createImage("/StarCruiser.png"),
				createImage("/Stock.png"), createImage("/ManyBalls.png"),
				createImage("/Tiles.png") };

		commList = new List("通讯簿", List.IMPLICIT, commItem, commIcon);

		commList.addCommand(luckyCommand);
		commList.addCommand(menuCommand);
		commList.setCommandListener(this);
		currentScreen = "通讯簿";
		display.setCurrent(commList);
	}

	// 显示所有星座
	private void doLucky() {
		luckyList = new List("一周运势", List.EXCLUSIVE, 
				             luckyItem, null);
		luckyList.addCommand(commCommand);
		luckyList.addCommand(menuCommand);
		luckyList.addCommand(execCommand);
		luckyList.setCommandListener(this);
		currentScreen = "一周运势";
		display.setCurrent(luckyList);
	}

	// 显示对应的星座的运势
	private void showLucky() {
		int selectedItem = luckyList.getSelectedIndex();
		Alert alert;
		String information = "";
		switch (selectedItem) {
		case 0: {
			information = "金牛座";
			break;
		}
		case 1: {
			information = "处女座";
			break;
		}
		case 2: {
			information = "摩羯座";
			break;
		}
		case 3: {
			information = "射手座";
			break;
		}
		case 4: {
			information = "牡羊座";
			break;
		}
		case 5: {
			information = "狮子座";
			break;
		}
		case 6: {
			information = "双鱼座";
			break;
		}
		case 7: {
			information = "巨蟹座";
			break;
		}
		case 8: {
			information = "天蝎座";
			break;
		}
		case 9: {
			information = "双子座";
			break;
		}
		case 10: {
			information = "天秤座";
			break;
		}
		case 11: {
			information = "水瓶座";
			break;
		}
		}

		alert = new Alert("信息", information, null, AlertType.INFO);
		alert.setTimeout(Alert.FOREVER);
		display.setCurrent(alert);
	}

	// 处理通讯录添加
	private void doAppend() {
		Form appendForm = new Form("新增");
		saveCommand = new Command("存储", Command.SCREEN, 1);
		TextField nameField = new TextField("姓名", null, 10, TextField.ANY);
		TextField EMailField = new TextField("E Mail", null, 10,
				TextField.EMAILADDR);
		TextField ageField = new TextField("年龄", null, 10, TextField.NUMERIC);
		appendForm.append(nameField);
		appendForm.append(EMailField);
		appendForm.append(ageField);
		appendForm.addCommand(saveCommand);
		appendForm.addCommand(commCommand);
		appendForm.addCommand(luckyCommand);
		appendForm.setCommandListener(this);
		currentScreen = "通讯簿新增";
		display.setCurrent(appendForm);
	}

	// 处理通讯录修改
	private void doModification() {
		Form modificationForm = new Form("修改");
		TextField nameField = new TextField("姓名", null, 10, TextField.ANY);
		modificationForm.append(nameField);
		modificationForm.addCommand(execCommand);
		modificationForm.addCommand(commCommand);
		modificationForm.addCommand(luckyCommand);
		modificationForm.setCommandListener(this);
		currentScreen = "通讯簿修改";
		display.setCurrent(modificationForm);
	}

	// 处理通讯录查询
	private void doQuery() {
		Form queryForm = new Form("查询");
		TextField nameField = new TextField("姓名", null, 10, TextField.ANY);
		queryForm.append(nameField);
		queryForm.addCommand(execCommand);
		queryForm.addCommand(commCommand);
		queryForm.addCommand(luckyCommand);
		queryForm.setCommandListener(this);
		currentScreen = "通讯簿查询";
		display.setCurrent(queryForm);
	}

	// 处理通讯录删除
	private void doDelete() {
		Form deleteForm = new Form("删除");
		TextField nameField = new TextField("姓名", null, 10, TextField.ANY);
		deleteForm.append(nameField);
		deleteForm.addCommand(execCommand);
		deleteForm.addCommand(commCommand);
		deleteForm.addCommand(luckyCommand);
		deleteForm.setCommandListener(this);
		currentScreen = "通讯簿删除";
		display.setCurrent(deleteForm);
	}
}
