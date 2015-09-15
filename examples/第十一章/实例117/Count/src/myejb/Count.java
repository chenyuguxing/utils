package 第十一章.实例117;
package myejb;
import javax.ejb.*;
import java.rmi.RemoteException;
// 定义CountBean接口
public interface Count extends EJBObject 
{
  public int count()throws RemoteException;
}
