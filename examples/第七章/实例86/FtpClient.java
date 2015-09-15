package 第七章.实例86;
import java.net.*;
import java.io.*;
import java.util.*;

class FtpClient {
    static final boolean debug = false;
    public static final int FTP_PORT = 21;  // ftp的端口
    static int FTP_SUCCESS = 1;
    static int FTP_TRY_AGAIN = 2;
    static int FTP_ERROR = 3;
    // 数据传输套接字
    private Socket  dataSocket = null;
    private boolean replyPending = false;
    private boolean binaryMode = false;
    private boolean passiveMode = false;
    String user = null;  // 登录用的用户名
    String password = null;  // 登录用的密码
    String command;  // 最近一次命令
    int lastReplyCode;  // 最近的响应信息
    public String welcomeMsg; // 服务器的欢迎信息
    // 从服务器返回的响应字符串对象
    protected Vector serverResponse = new Vector(1);
    protected Socket serverSocket = null; // 与服务器通信的套接字
    public PrintWriter  serverOutput;  
    public InputStream  serverInput;  // 从服务器读取响应的缓冲流

    /** 返回服务器连接的状态 */
    public boolean serverIsOpen() {
        return serverSocket != null;
    }

	/** 设置为被动模式 */
	public void setPassive(boolean mode) {
		passiveMode = mode;
	}
	
	/** 读取服务器的响应信息 */
    public int readServerResponse() throws IOException {
        StringBuffer replyBuf = new StringBuffer(32);
        int c;
        int continuingCode = -1;
        int code = -1;
        String response;
        if (debug) System.out.println("readServerResponse start");
        try{
        while (true) {
            if (debug) System.out.println("readServerResponse outer while loop: "+ serverInput.available());    
            while ((c = serverInput.read()) != -1) {
               if (c == '\r') {
                    if ((c = serverInput.read()) != '\n')
                        replyBuf.append('\r');
                }
                replyBuf.append((char)c);               
                if (c == '\n')
                    break;                
            }
            if (debug) System.out.println("Now past inner while loop");
            response = replyBuf.toString();
            replyBuf.setLength(0);
            if (debug) {
                System.out.print(response);
            }
            try {
                code = Integer.parseInt(response.substring(0, 3));
            } catch (NumberFormatException e) {
                code = -1;
            } catch (StringIndexOutOfBoundsException e) {
                // 此行不存在响应码，循环跳到下一次
                continue;
            }
            serverResponse.addElement(response);
            if (continuingCode != -1) {
                /* we've seen a XXX- sequence */
                if (code != continuingCode ||
                    (response.length() >= 4 && response.charAt(3) == '-')) {
                    continue;
                } else {
                    continuingCode = -1; // 到达程序的结尾
                    break;
                }
            } else if (response.length() >= 4 && response.charAt(3) == '-') {
                continuingCode = code;
                continue;
            } else {
                break;
            }
        }
        }catch(Exception e){e.printStackTrace();}
        if (debug) System.out.println("readServerResponse done");
        return lastReplyCode = code;
    }

    /** 发送命令 <i>cmd</i> 给服务器 */
    public void sendServer(String cmd) {
        if (debug) System.out.println("sendServer start");
        serverOutput.println(cmd);
        if (debug) System.out.println("sendServer done");

    }
   
    /** 返回服务器的所有响应字符串 */
    public String getResponseString() {
        String s = new String();
        for(int i = 0;i < serverResponse.size();i++) {
           s+=serverResponse.elementAt(i);
        }
        serverResponse = new Vector(1);
        return s;
    }

    /** 获取响应字符串 */
   public String getResponseStringNoReset() {
        String s = new String();
        for(int i = 0;i < serverResponse.size();i++) {
           s+=serverResponse.elementAt(i);
        }
        return s;
    }
   
    /** 发送 QUIT 命令给服务器并关闭连接 */
    public void closeServer() throws IOException {
        if (serverIsOpen()) {
            issueCommand("QUIT");
        if (! serverIsOpen()) {
              return;
            }
            serverSocket.close();
            serverSocket = null;
            serverInput = null;
            serverOutput = null;
        }
    }

    protected int issueCommand(String cmd) throws IOException {
        command = cmd;
        int reply;
        if (debug) System.out.println(cmd);
        if (replyPending) {
            if (debug) System.out.println("replyPending");
            if (readReply() == FTP_ERROR)
                System.out.print("Error reading pending reply\n");
        }
        replyPending = false;
        do {
            sendServer(cmd);
            reply = readReply();
            if (debug) System.out.println("in while loop of issueCommand method");
        } while (reply == FTP_TRY_AGAIN);
        return reply;
    }

    // 检测命令
    protected void issueCommandCheck(String cmd) throws IOException {
        if (debug) System.out.println("issueCommandCheck");
        if (issueCommand(cmd) != FTP_SUCCESS) {            
            throw new FtpProtocolException(cmd);
            }
    }

    /** 读取返回数据 */
    protected int readReply() throws IOException {
		lastReplyCode = readServerResponse();
		switch (lastReplyCode / 100) {
		case 1:
			replyPending = true;

		case 2:// 这个case用来以后扩展功能

		case 3:
			return FTP_SUCCESS;

		case 5:
			if (lastReplyCode == 530) {
				if (user == null) {
					throw new FtpLoginException("Not logged in");
				}
				return FTP_ERROR;
			}
			if (lastReplyCode == 550) {
				if (!command.startsWith("PASS"))
					throw new FileNotFoundException(command);
				else
					throw new FtpLoginException("Error: Wrong Password!");
			}
		}
		return FTP_ERROR;
	}

	//　打开数据连接
	protected Socket openDataConnection(String cmd) throws IOException {
		ServerSocket portSocket = null;
		String portCmd;
		InetAddress myAddress = InetAddress.getLocalHost();
		byte addr[] = myAddress.getAddress();
		int shift;
		String ipaddress;
		int port = 0;
		IOException e;
		if (this.passiveMode) {
			// 首先尝试被动模式传输
			try { 
				getResponseString();
				if (issueCommand("PASV") == FTP_ERROR) {
					e = new FtpProtocolException("PASV");
					throw e;
				}
				String reply = getResponseStringNoReset();
				reply = reply.substring(reply.lastIndexOf("(") + 1, reply
						.lastIndexOf(")"));
				StringTokenizer st = new StringTokenizer(reply, ",");
				String[] nums = new String[6];
				int i = 0;
				while (st.hasMoreElements()) {
					try {
						nums[i] = st.nextToken();
						i++;
					} catch (Exception a) {
						a.printStackTrace();
					}
				}
				ipaddress = nums[0] + "." + nums[1] + "." + nums[2] + "."
						+ nums[3];
				try {
					int firstbits = Integer.parseInt(nums[4]) << 8;
					int lastbits = Integer.parseInt(nums[5]);
					port = firstbits + lastbits;
				} catch (Exception b) {
					b.printStackTrace();
				}
				if ((ipaddress != null) && (port != 0)) {
					dataSocket = new Socket(ipaddress, port);
				} else {
					e = new FtpProtocolException("PASV");
					throw e;
				}
				if (issueCommand(cmd) == FTP_ERROR) {
					e = new FtpProtocolException(cmd);
					throw e;
				}
			} catch (FtpProtocolException fpe) { 
				portCmd = "PORT ";
				// 附加host地址
				for (int i = 0; i < addr.length; i++) {
					portCmd = portCmd + (addr[i] & 0xFF) + ",";
				}
				try {
					portSocket = new ServerSocket(20000);
					// 附加端口
					portCmd = portCmd
							+ ((portSocket.getLocalPort() >>> 8) & 0xff) + ","
							+ (portSocket.getLocalPort() & 0xff);
					if (issueCommand(portCmd) == FTP_ERROR) {
						e = new FtpProtocolException("PORT");
						throw e;
					}
					if (issueCommand(cmd) == FTP_ERROR) {
						e = new FtpProtocolException(cmd);
						throw e;
					}
					dataSocket = portSocket.accept();
				} finally {
					if (portSocket != null)
						portSocket.close();
				}
				dataSocket = portSocket.accept();
				portSocket.close();
			}
		}
		else { // 端口传送
			portCmd = "PORT ";
			// 附加host地址
			for (int i = 0; i < addr.length; i++) {
				portCmd = portCmd + (addr[i] & 0xFF) + ",";
			}
			try {
				portSocket = new ServerSocket(20000);
				// 附加端口号
				portCmd = portCmd + ((portSocket.getLocalPort() >>> 8) & 0xff)
						+ "," + (portSocket.getLocalPort() & 0xff);
				if (issueCommand(portCmd) == FTP_ERROR) {
					e = new FtpProtocolException("PORT");
					throw e;
				}
				if (issueCommand(cmd) == FTP_ERROR) {
					e = new FtpProtocolException(cmd);
					throw e;
				}
				dataSocket = portSocket.accept();
			} finally {
				if (portSocket != null)
					portSocket.close();
			}
			dataSocket = portSocket.accept();
			portSocket.close();
		}
		return dataSocket; 
	}

    /** 打开一个到 <i>host </i>的FTP连接 */
    public void openServer(String host) throws IOException, 
	                                           UnknownHostException {
        int port = FTP_PORT;
        if (serverSocket != null)
            closeServer(); 
        serverSocket = new Socket(host, FTP_PORT);    
        serverOutput = new PrintWriter(new BufferedOutputStream(serverSocket.getOutputStream()),true);
        serverInput = new BufferedInputStream(serverSocket.getInputStream());
    }

    /** 打开到 host <i>host </i> 端口为 <i>port </i>的FTP连接 */
    public void openServer(String host, int port) throws IOException, UnknownHostException {
        if (serverSocket != null)
            closeServer();
        serverSocket = new Socket(host, port);
        serverOutput = new PrintWriter(new BufferedOutputStream(serverSocket.getOutputStream()),
                                       true);
        serverInput = new BufferedInputStream(serverSocket.getInputStream());

        if (readReply() == FTP_ERROR)
            throw new FtpConnectException("Welcome message");
    }
 
     /** 使用用户名 <i>user</i> 和密码 <i>password</i> 登录*/
    public void login(String user, String password) throws IOException {
        if (!serverIsOpen())
            throw new FtpLoginException("Error: not connected to host.\n");
        this.user = user;
        this.password = password;
        if (issueCommand("USER " + user) == FTP_ERROR)
            throw new FtpLoginException("Error: User not found.\n");
        if (password != null && issueCommand("PASS " + password) == FTP_ERROR)
            throw new FtpLoginException("Error: Wrong Password.\n");       
    }

    /** 只用用户名 <i>user</i> 不用密码登录 */
    public void login(String user) throws IOException {

        if (!serverIsOpen())
            throw new FtpLoginException("not connected to host");
        this.user = user;        
        if (issueCommand("USER " + user) == FTP_ERROR)
            throw new FtpLoginException("Error: Invalid Username.\n");                 
    }

    /** 以Ascii 模式从FTP server获取一个文件 */
    public BufferedReader getAscii(String filename) throws IOException {     
        Socket  s = null;
        try {
            s = openDataConnection("RETR " + filename);
        } catch (FileNotFoundException fileException) {fileException.printStackTrace();}
        return new BufferedReader( new InputStreamReader(s.getInputStream()));          
    }

    /** 以Binary 模式从FTP server获取一个文件 */
    public BufferedInputStream getBinary(String filename) throws IOException {     
        Socket  s = null;
        try {
            s = openDataConnection("RETR " + filename);
        } catch (FileNotFoundException fileException) {fileException.printStackTrace();}
        return new BufferedInputStream(s.getInputStream());          
    }


    /** 以Ascii 模式发送一个文件 */
    public BufferedWriter putAscii(String filename) throws IOException {
        Socket s = openDataConnection("STOR " + filename);
        return new BufferedWriter(new OutputStreamWriter(s.getOutputStream()),4096);
    }
     
    /** 以Binary 模式向server发送一个文件 */
    public BufferedOutputStream putBinary(String filename) throws IOException {
        Socket s = openDataConnection("STOR " + filename);
        return new BufferedOutputStream(s.getOutputStream());
    }

    /** 以Ascii 模式在server上创建或添加一个文件 */
    public BufferedWriter appendAscii(String filename) throws IOException {
        Socket s = openDataConnection("APPE " + filename);
        return new BufferedWriter(new OutputStreamWriter(s.getOutputStream()),4096);
    }

    /** 以Binary 模式在server上创建或添加一个文件 */
    public BufferedOutputStream appendBinary(String filename) throws IOException {
        Socket s = openDataConnection("APPE " + filename);
        return new BufferedOutputStream(s.getOutputStream());
    }

   /** NLIST 文件在远端 FTP server */
    public BufferedReader nlist() throws IOException {
        Socket s = openDataConnection("NLST");        
        return new BufferedReader( new InputStreamReader(s.getInputStream()));
    }

    /** LIST files 在远端 FTP server */
    public BufferedReader list() throws IOException {
        Socket s = openDataConnection("LIST");        
        return new BufferedReader( new InputStreamReader(s.getInputStream()));
    }

    /** 在FTP server上改变文件路径 */
    public void cd(String remoteDirectory) throws IOException {
        issueCommandCheck("CWD " + remoteDirectory);
    }

    /** 在server上改变文件名 */
    public void rename(String oldFile, String newFile) throws IOException {
         issueCommandCheck("RNFR " + oldFile);
         issueCommandCheck("RNTO " + newFile);
    }
      
    /** Site 命令 */ 
    public void site(String params) throws IOException {
         issueCommandCheck("SITE "+ params);
    }            
	
    /** 设置为'I'传输模式 */
    public void binary() throws IOException {
        issueCommandCheck("TYPE I");
        binaryMode = true;
    }

    /** 设置为'A'传输模式 */
    public void ascii() throws IOException {
        issueCommandCheck("TYPE A");
        binaryMode = false;
    }

    /** 发送Abort 命令 */
    public void abort() throws IOException {
        issueCommandCheck("ABOR");
    }

    /** 在远端系统浏览上一级目录 */
    public void cdup() throws IOException {
        issueCommandCheck("CDUP");
    }

    /** 在远端系统中创建一个目录 */
    public void mkdir(String s) throws IOException {
        issueCommandCheck("MKD " + s);
    }

    /** 在远端系统中删除某个路径 */
    public void rmdir(String s) throws IOException {
        issueCommandCheck("RMD " + s);
    }

    /** 删除文件 */
    public void delete(String s) throws IOException {
        issueCommandCheck("DELE " + s);
    }

    /** 获取当前目录名 */
    public void pwd() throws IOException {
        issueCommandCheck("PWD");
    }
    
    /** 获取远端系统的信息 */
    public void syst() throws IOException {
        issueCommandCheck("SYST");
    }


    /** 新的FTP客户端连接到host <i>host</i>. */
    public FtpClient(String host) throws IOException {      
        openServer(host, FTP_PORT);      
    }

    /** 新的FTP客户端连接到host <i>host</i>, port <i>port</i>. */
    public FtpClient(String host, int port) throws IOException {
        openServer(host, port);
    }
    
    /** 演示 */
    public static void main (String args []) throws IOException{
      System.out.println("Demo of FtpClient class.\n");
      // 标准登陆过程 
      FtpClient f = new FtpClient("ftp.zsu.edu.cn");
      System.out.print(f.getResponseString());
      f.login("anonymous");
      System.out.print(f.getResponseString());                       
      f.pwd(); 
      System.out.println(f.command);                  
      System.out.print(f.getResponseString());
      f.setPassive(true);
      
      // 使用列表
      System.out.println("\nDemo of nlist() function");
      f.ascii();  // 把客户端设为ASCII模式以获取文本列表   
      System.out.println(f.command);              
      System.out.print(f.getResponseString());     
      BufferedReader t = f.nlist();     // f.list提供了更多的一些细节信息
      System.out.println(f.command);
      System.out.print(f.getResponseString());  
      while( true ) {
         String stringBuffer = t.readLine();
         if( stringBuffer == null ) break;
         else System.out.println(stringBuffer);               
      }
      t.close();
      System.out.print(f.getResponseString());  
      // 下面使用 getAscii() 函数获取文件.  函数 getBinary() 也类似 
      System.out.println("\nDemo of getAscii() function");
      f.ascii();       //  设置传输模式为ASCII
      System.out.println(f.command);
      System.out.print(f.getResponseString());  
      BufferedReader bufGet = f.getAscii("welcome.msg");
      System.out.println(f.command);      
      System.out.print(f.getResponseString());      
      PrintWriter pOut = new PrintWriter(new BufferedWriter(new FileWriter("welcome.msg")));
      int i;                                
      char c[] = new char[4096];
      while ((i = bufGet.read(c)) != -1) 
        pOut.write(c,0,i);                                                   
      bufGet.close();
      pOut.close();            
      System.out.print(f.getResponseString());         
      // 下面使用appendAscii()函数添加一个文件，appendBinary()函数的使用类似
      System.out.println("\nDemo of appendAscii() function");
      BufferedWriter bufAppe;
      String localFile = "file name goes here"; 
      f.ascii();
      System.out.println(f.command);
      try {
      bufAppe = f.appendAscii(localFile);
      System.out.println(f.command);
      System.out.print(f.getResponseString());          
	  FileReader fIn = new FileReader(localFile);            
      BufferedReader bufIn = new BufferedReader(fIn);
      int k;
      char b[] = new char[1024];
      while ((k = bufIn.read(b)) != -1) 
        bufAppe.write(b,0,k);                                  
      bufIn.close();
      bufAppe.flush();
      bufAppe.close();                 
      }catch(Exception appendErr) {
         System.out.println(appendErr.toString());//printStackTrace();
         
      }
      System.out.print(f.getResponseString()); 
      // 下面使用putBianary()函数发送一个二进制文件，函数putAscii()类似
      System.out.println("\nDemo of putBinary() function");
      String localFile2 = "file name goes here"; 
      f.binary();
      System.out.println(f.command);
      BufferedOutputStream bufPut = f.putBinary(localFile2);
      System.out.println(f.command);
      System.out.print(f.getResponseString());     
      BufferedInputStream bufIn = new BufferedInputStream(new FileInputStream(localFile2));
      int j;
      byte b[] = new byte[1024];
      while ((j = bufIn.read(b)) != -1) 
        bufPut.write(b,0,j);                                  
      bufIn.close();
      bufPut.flush();
      bufPut.close();                 
      System.out.print(f.getResponseString()); 
      // 关闭连接
      f.closeServer();
      System.out.println(f.command);
      System.out.print(f.getResponseString());
    }
}

class FtpLoginException extends FtpProtocolException {
    FtpLoginException(String s) {
        super(s);
    }
}
class FtpConnectException extends FtpProtocolException {
    FtpConnectException(String s) {
        super(s);
    }
}

class FtpProtocolException extends IOException {
    FtpProtocolException(String s) {
        super(s);     
    }
}
