package 第十一章.实例117;
package myejb;
import javax.naming.*;
import java.util.Properties;
import javax.rmi.PortableRemoteObject;
// 这个代码触发调用一个状态Bean中的方法
public class CountClient {
	public static void main(String[] args) {
		/*try {
			//获取系统属性，初始化JNDI
			Properties props = System.getProperties();
			// 取得Home对象的引用
			Context ctx = new InitialContext(props);
			CountHome Home =
				(CountHome) javax.rmi.PortableRemoteObject.narrow(
					ctx.lookup("CountHome"),
					CountHome.class);
			//具有3个Count EJB对象的数组
			Count count[] = new Count[3];
			int countVal = 0;
			for (int i = 0; i < 3; i++) { //创建EJB对象，并将当前的计数器初始化
				count[i] = Home.create(countVal);
				countVal = count[i].count();
				System.out.println(countVal);
			}
			for (int i = 0;
				i < 3;
				i++) { // 调用每一个EJB对象的count()方法，保证Bean正常被激活和钝化
				countVal = count[i].count();
				System.out.println(countVal);
			}
			for (int i = 0; i < 3; i++) {
				//EJB对象是用完毕，从内存中清除
				count[i].remove();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		try {

			//jndi配置,硬编码到java中，应实现为外部属性文件

			Properties p = new Properties();

			p.setProperty(
				"java.naming.factory.initial",
				"org.jnp.interfaces.NamingContextFactory");

			p.setProperty("java.naming.provider.url", "localhost:1099");

			//out print jndi配置

			p.list(System.out);

			// Get a naming context

			InitialContext jndiContext = new InitialContext(p);

			System.out.println("Got context");

			// Get a reference to the Interest Bean

			//jboss默认jndi名为ejb-jar.xml中的:ejb-name

			Object ref = jndiContext.lookup("Count");

			System.out.println("Got reference");

			// Get a reference from this to the Bean"s Home interface

			CountHome home =
				(CountHome) PortableRemoteObject.narrow(ref, CountHome.class);

			//具有3个Count EJB对象的数组
			Count count[] = new Count[3];
			int countVal = 0;		
			for (int i = 0; i < 3; i++) { //创建EJB对象，并将当前的计数器初始化
				count[i] = home.create(countVal);
				countVal = count[i].count();
				System.out.println(countVal);
			}
			for (int i = 0;
				i < 3;
				i++) { // 调用每一个EJB对象的count()方法，保证Bean正常被激活和钝化
				countVal = count[i].count();
				System.out.println(countVal);
			}
			for (int i = 0; i < 3; i++) {
				//EJB对象是用完毕，从内存中清除
				count[i].remove();
			}

		} catch (Exception e) {

			System.out.println(e.toString());

		}

	}
}
