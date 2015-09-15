package 第十三章.实例133;
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import javax.microedition.rms.*;
import java.io.*;

public class MultiFieldsMIDlet extends MIDlet implements CommandListener {
	private Command exitCommand;
	private Display display;
	private String[] names = { "张三", "李四", "王五" };

	public MultiFieldsMIDlet() {
		display = Display.getDisplay(this);
		exitCommand = new Command("离开", Command.EXIT, 1);
	}

	public void startApp() {
		TextBox aTextBox = new TextBox("主画面", null, 256, TextField.ANY);
		RecordStore rs = null;
		byte[] nameEmail = null;
		boolean existingOrNot = false;
		boolean OK = true;
        // 如果存在名为myRS的RecordStore
		existingOrNot = existing("myRS");
		if (existingOrNot) {
			try {
				rs = RecordStore.openRecordStore("myRS", false);
			} catch (Exception e) {
				OK = false;
			} finally {
				if (OK) {
					aTextBox.setString("myRS已存在并且已打开");
				} else {
					aTextBox.setString("myRS已存在但无法打开");
				}
			}
		} else {
			try {
				rs = RecordStore.openRecordStore("myRS", true);
			} catch (Exception e) {
				OK = false;
			} finally {
				if (OK) {
					aTextBox.setString("myRS虽未存在，但已创建并完成打开的操作！");
				} else {
					aTextBox.setString("myRS虽未存在，但无法创建，打开文件失败！");
				}
			}
		}

		Person2 aPerson = null;
		if (OK) // 如果myRS已经存在
			try {
				for (int i = 0; i < names.length; i++) {
					aPerson = new Person2();
					aPerson.write(names[i], "name@163.com", 21);
					byte[] data = aPerson.changeToByteArray();
					int recordID = aPerson.getRecordID();
					if (recordID != -1) {
                        // 在recordID的位置设置新信息
						rs.setRecord(recordID, data, 0, data.length);
					} else {
                        //  插入新信息,获得recordID后更新Person2中的信息
						recordID = rs.addRecord(data, 0, data.length);
						aPerson.setRecordID(recordID);
					}
					aPerson = null;
				}
				aTextBox.setString("已完成新增记录的操作！");
			} catch (Exception e) {
				aTextBox.setString("新增记录的操作失败！");
			}

		String result = "";
		aPerson = new Person2();

		if (OK)
			try {
                // 获取RecordStore中的记录数
				int number = rs.getNumRecords();
				byte[] data;
                // 输出每个RecordStroe的记录的信息
				for (int i = 1; i < rs.getNextRecordID(); i++) {
					data = rs.getRecord(i);
					aPerson.changeFromByteArray(data);
					result += "第" + i + "笔\n" + "姓名：" + aPerson.getName()
							+ "\n" + "E-mail：" + aPerson.getEMail() + "\n"
							+ "年龄：" + aPerson.getAge() + "\n";
				}

				result += "共有" + number + "笔记录：\n";
				aTextBox.setString(result);
			} catch (Exception e) {
				aTextBox.setString("获取记录的操作失败！");
				try {
					rs.closeRecordStore();
					System.out.println("1.Closed.");
					RecordStore.deleteRecordStore("myRS");
					System.out.println("delete OK");
				} catch (Exception x) {
				}
			} finally {
				try {
					if (rs != null)
						rs.closeRecordStore();
					rs.deleteRecordStore("myRS");
				} catch (Exception e) {
				}
			}

		aTextBox.setString(result);
		aTextBox.addCommand(exitCommand);
		aTextBox.setCommandListener(this);
		display.setCurrent(aTextBox);
	}

	public void pauseApp() {
	}

	public void destroyApp(boolean unconditional) {
	}

    //	检查指定名字的RecordStore是否存在
	public boolean existing(String recordStoreName) {
		boolean existingOrNot = false;
		RecordStore rs = null;
		if (recordStoreName.length() > 32)
			return false;
		try {
			rs = RecordStore.openRecordStore(recordStoreName, false);
		} catch (RecordStoreNotFoundException e) {
			existingOrNot = false;
		} catch (Exception e) {
		} finally {
			try {
				rs.closeRecordStore();
			} catch (Exception e) {
			}
		}
		return existingOrNot;
	}

	public void commandAction(Command c, Displayable s) {
		destroyApp(false);
		notifyDestroyed();
	}
}

class Person2 {
	private int ID = -1;

	private String name;

	private String EMail;

	private int age;

	public void write(String name, String EMail, int age) {
		this.name = name;
		this.EMail = EMail;
		this.age = age;
	}

	public void setRecordID(int ID) {
		this.ID = ID;
	}

	public int getRecordID() {
		return ID;
	}

    //	 将Person2的信息转化为字节数组存储
	public byte[] changeToByteArray() {
		byte[] data = null;
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			DataOutputStream dos = new DataOutputStream(baos);
			dos.writeUTF(name);
			dos.writeUTF(EMail);
			dos.writeInt(age);
			data = baos.toByteArray();

			baos.close();
			dos.close();
		} catch (Exception e) {
		}
		return data;
	}

    //	 从字节数组中读取数据
	public void changeFromByteArray(byte[] data) {
		try {
			ByteArrayInputStream bais = new ByteArrayInputStream(data);
			DataInputStream dis = new DataInputStream(bais);

			name = dis.readUTF();
			EMail = dis.readUTF();
			age = dis.readInt();

			bais.close();
			dis.close();
		} catch (Exception e) {
		}
	}

	public String getName() {
		return name;
	}

	public String getEMail() {
		return EMail;
	}

	public int getAge() {
		return age;
	}
}

