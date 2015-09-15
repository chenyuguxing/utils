package 第十三章.实例134;
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import java.util.*;

public class SimpleTimerMIDlet extends MIDlet {
	Display display;
	StarField field = new StarField();
	FieldMover mover = new FieldMover();
	Timer timer = new Timer();

	public SimpleTimerMIDlet() {
		display = Display.getDisplay(this);
	}

	protected void destroyApp(boolean unconditional) {
	}

	protected void startApp() {
		display.setCurrent(field);
		// 在执行前等待100毫秒，然后每100毫秒执行一次
		timer.schedule(mover, 100, 100);
	}

	protected void pauseApp() {
	}

	public void exit() {
		timer.cancel(); // 停止滚动
		destroyApp(true);
		notifyDestroyed();
	}

	class FieldMover extends TimerTask {
		public void run() {
			field.scroll();
		}
	}

	class StarField extends Canvas {
		int height;
		int width;
		int[] stars;
		Random generator = new Random();
		boolean painting = false;

		public StarField() {
			height = getHeight();
			width = getWidth();
			stars = new int[height];
			for (int i = 0; i < height; ++i) {
				stars[i] = -1;
			}
		}

		public void scroll() {
			if (painting)
				return;
			for (int i = height - 1; i > 0; --i) {
				stars[i] = stars[i - 1];
			}
			stars[0] = (generator.nextInt() % (3 * width)) / 2;
			if (stars[0] >= width) {
				stars[0] = -1;
			}
			repaint();
		}

		protected void paint(Graphics g) {
			painting = true;
			g.setColor(0, 0, 0);
			g.fillRect(0, 0, width, height);
			g.setColor(255, 255, 255);
			for (int y = 0; y < height; ++y) {
				int x = stars[y];
				if (x == -1)
					continue;
				g.drawLine(x, y, x, y);
			}
			painting = false;
		}

		protected void keypressed(int keycode) {
			exit();
		}
	}
}

