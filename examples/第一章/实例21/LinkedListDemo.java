package 第一章.实例21;
import java.util.LinkedList;

//很容易使用LinkedList实现一个堆栈
class LinkedListStack {
	// 堆栈内部使用一个LinkedList容器存放元素
	private LinkedList data = new LinkedList();
	public boolean isEmpty() {
		return data.isEmpty();
	}
	public Object top() {
		// 取栈顶元素相当于取LinkedList容器的头位置的元素
		return data.getFirst();
	}
	public void push(Object element) {
		// 压入元素相当于在LinkedList容器的头位置插入元素
		data.addFirst(element);
	}
	public void pop() {
		// 弹出元素相当于删除LinkedList容器的头位置的元素
		data.removeFirst();
	}
}
public class LinkedListDemo {
	public static void main(String[] args) {
		LinkedListStack stack = new LinkedListStack();
		// 下面的语句使用类LinkedList实现的堆栈的用法
		stack.push("C++");
		stack.push("C#");
		stack.push("Java");
		while (! stack.isEmpty()) {
			System.out.println(stack.top());
			stack.pop();
		}
	}
}
