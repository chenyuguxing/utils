package 第十一章.实例119;
/*
 * 创建日期 2004-10-31
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package message;

import javax.ejb.MessageDrivenBean;
import javax.jms.MessageListener;
import java.io.Serializable;
import javax.naming.*;
import javax.jms.*;
import javax.mail.*;
import javax.activation.*;
import javax.mail.internet.*;
import java.util.*;
import java.text.*;
import javax.ejb.*;

/**
 * @ejb.bean name="Message" 
 *     acknowledge-mode="Auto-acknowledge" 
 *     destination-type="javax.jms.Queue" 
 *     subscription-durability="NonDurable" 
 *     transaction-type="Bean" 
 * 
 *--
 * This is needed for JOnAS.
 * If you are not using JOnAS you can safely remove the tags below.
 * @jonas.bean ejb-name="Message"
 *	jndi-name="MessageBean"
 * 
 *--
 **/

public class MessageBean implements MessageDrivenBean, MessageListener {

	/** Required method for container to set context. */
	public void setMessageDrivenContext(
		javax.ejb.MessageDrivenContext messageContext)
		throws javax.ejb.EJBException {
		this.messageContext = messageContext;
	}
	/** 
	* Required creation method for message-driven beans. 
	* 
	* @ejb.create-method 
	*/
	public void ejbCreate() {
		// no specific action required for message-driven beans 
	}

	/** Required removal method for message-driven beans. */
	public void ejbRemove() {
		messageContext = null;
	}

	/** 
	* This method implements the business logic for the EJB. 
	* 
	* <p>Make sure that the business logic accounts for asynchronous message processing. 
	* For example, it cannot be assumed that the EJB receives messages in the order they were 
	* sent by the client. Instance pooling within the container means that messages are not 
	* received or processed in a sequential order, although individual onMessage() calls to 
	* a given message-driven bean instance are serialized. 
	* 
	* <p>The <code>onMessage()</code> method is required, and must take a single parameter 
	* of type javax.jms.Message. The throws clause (if used) must not include an application 
	* exception. Must not be declared as final or static. 
	*/
	public void onMessage(javax.jms.Message msg) {
		System.out.println("Message Driven Bean got message " + msg);
		// do business logic here 
		try {
			if (msg instanceof MapMessage) {
				MapMessage map = (MapMessage) msg;
				System.out.println("Order received: ");
				System.out.println(
					"Order ID: "
						+ map.getString("OrderID")
						+ " Item ID: "
						+ map.getInt("ItemID")
						+ " Quanity: "
						+ map.getInt("Quantity")
						+ " Unit Price: "
						+ map.getDouble("UnitPrice"));
				sendNote(map.getString("emailID"));
			} else {
				System.out.println("wrong message type");
			}
		} catch (Throwable te) {
			te.printStackTrace();
		}
	}

	/**
	 * @ejb.interface-method
	 *	view-type="remote" 
	**/
	//发送一个e-mail通知给由recipient参数确定的e-mail账号
	private void sendNote(String recipient) {
		try {
			//得到名字上下文
			Context initial = new InitialContext();
			// 查询mail 服务器的会话
			javax.mail.Session session =
				(javax.mail.Session) initial.lookup(
					"java:comp/env/MailSession");
			//创建一个新的mail对象
			javax.mail.Message msg = new MimeMessage(session);
			//设置mail的属性 
			msg.setFrom();
			msg.setRecipients(
				javax.mail.Message.RecipientType.TO,
				InternetAddress.parse(recipient, false));
			msg.setSubject("Order Confirmation");
			DateFormat dateFormatter =
				DateFormat.getDateTimeInstance(
					DateFormat.LONG,
					DateFormat.SHORT);
			Date timeStamp = new Date();
			String messageText =
				"Thank you for your order."
					+ '\n'
					+ "We received your order on "
					+ dateFormatter.format(timeStamp)
					+ ".";
			msg.setText(messageText);
			msg.setSentDate(timeStamp);
			//发送mail消息
			Transport.send(msg);
		} catch (Exception e) {
			throw new EJBException(e.getMessage());
		}
	}
	
	/** The context for the message-driven bean, set by the EJB container. */
	private javax.ejb.MessageDrivenContext messageContext = null;
}
