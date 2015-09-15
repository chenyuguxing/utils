package 第十一章.实例116;
package cal;

import javax.ejb.SessionBean;

/**
 * @ejb.bean name="Calculate"
 *	jndi-name="CalculateBean"
 *	type="Stateless" 
 * 
 *--
 * This is needed for JOnAS.
 * If you are not using JOnAS you can safely remove the tags below.
 * @jonas.bean ejb-name="Calculate"
 *	jndi-name="CalculateBean"
 * 
 *--
 **/

public abstract class CalculateBean implements SessionBean {

	/**
	 * @ejb.interface-method
	 *	view-type="remote" 
	**/
	public int add(int a, int b) {
		return a + b;
	}
}
