package 第十一章.实例118;
package account;
public class AccountException extends Exception {
	public AccountException() {
		super();
	}
	public AccountException(Exception e) {
		super(e.toString());
	}
	public AccountException(String s) {
		super(s);
	}
}
