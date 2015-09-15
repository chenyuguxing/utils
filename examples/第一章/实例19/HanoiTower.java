package 第一章.实例19;
//求解河内塔游戏
public class HanoiTower {
	// 将n个盘从from柱移到to柱，以aux柱为辅助柱
	public static void move(int n, char from, char to, char aux) {
		if (n == 1) {
			// 仅有一个盘时，直接从from柱移到to柱
			System.out.println("将#1盘从 " + from + " 移到 " + to);
		} else {
			// 将n - 1个盘从from柱移到aux柱，以to柱为辅助柱
			move(n - 1, from, aux, to);
			// 将最下的圆盘从from柱移到to柱
			System.out.println("将#" + n + "盘从 " + from + " 移到 " + to);
			// 将n - 1个盘从aux柱移到to柱，以from柱为辅助柱
			move(n - 1, aux, to, from);
		}
	}

	public static void main(String[] args) {
		// 将4个圆盘从A柱移到C柱，移动时利用B柱为辅助柱
		move(3, 'A', 'C', 'B');
	}
}
