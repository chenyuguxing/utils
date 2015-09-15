package 第十一章.实例116;
package cal;

import java.rmi.RemoteException;
import java.util.Hashtable;
import javax.ejb.CreateException;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class Client {

	private cal.CalculateHome getHome() throws NamingException {
		return (cal.CalculateHome) getContext().lookup(
			cal.CalculateHome.JNDI_NAME);
	}
	private InitialContext getContext() throws NamingException {
		Hashtable props = new Hashtable();

		props.put(
			InitialContext.INITIAL_CONTEXT_FACTORY,
			"org.jnp.interfaces.NamingContextFactory");
		props.put(InitialContext.PROVIDER_URL, "jnp://127.0.0.1:1099");

		// This establishes the security for authorization/authentication
		// props.put(InitialContext.SECURITY_PRINCIPAL,"username");
		// props.put(InitialContext.SECURITY_CREDENTIALS,"password");

		InitialContext initialContext = new InitialContext(props);
		return initialContext;
	}
	public void testBean() {

		try {
			cal.Calculate myBean = getHome().create();

			//--------------------------------------
			//This is the place you make your calls.
			//System.out.println(myBean.callYourMethod());
			System.out.println("5+8 = ");
			System.out.println(myBean.add(5, 8));

		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (CreateException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Client test = new Client();
		test.testBean();
	}
}
