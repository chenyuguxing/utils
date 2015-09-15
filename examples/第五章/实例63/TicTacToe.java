package 第五章.实例63;
// : c14:TicTacToe.java

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TicTacToe extends JApplet {
	private JTextField rows = new JTextField("3"),
	                   cols = new JTextField("3");
	private static final int BLANK = 0, XX = 1, OO = 2;

	class ToeDialog extends JDialog {
		private int turn = XX; // 从X开始

		ToeDialog(int cellsWide, int cellsHigh) {
			setTitle("The game itself");
			Container cp = getContentPane();
			cp.setLayout(new GridLayout(cellsWide, cellsHigh));
			for (int i = 0; i < cellsWide * cellsHigh; i++)
				cp.add(new ToeButton());
			setSize(cellsWide * 50, cellsHigh * 50);
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		}

		class ToeButton extends JPanel {
			private int state = BLANK;

			public ToeButton() {
				addMouseListener(new ML());
			}

			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				int x1 = 0, y1 = 0;
				int x2 = getSize().width - 1; 
				int y2 = getSize().height - 1;
				g.drawRect(x1, y1, x2, y2);
				x1 = x2 / 4;
				y1 = y2 / 4;
				int wide = x2 / 2, high = y2 / 2;
				if (state == XX) {
					g.drawLine(x1, y1, x1 + wide, y1 + high);
					g.drawLine(x1, y1 + high, x1 + wide, y1);
				}
				if (state == OO)
					g.drawOval(x1, y1, x1 + wide / 2, y1 + high / 2);
			}

			class ML extends MouseAdapter {
				public void mousePressed(MouseEvent e) {
					if (state == BLANK) {
						state = turn;
						turn = (turn == XX ? OO : XX);
					} else
						state = (state == XX ? OO : XX);
					repaint();
				}
			}
		}
	}

	class BL implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JDialog d = new ToeDialog(Integer.parseInt(rows.getText()), Integer
					.parseInt(cols.getText()));
			d.setVisible(true);
		}
	}

	public void init() {
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(2, 2));
		p.add(new JLabel("Rows", JLabel.CENTER));
		p.add(rows);
		p.add(new JLabel("Columns", JLabel.CENTER));
		p.add(cols);
		Container cp = getContentPane();
		cp.add(p, BorderLayout.NORTH);
		JButton b = new JButton("go");
		b.addActionListener(new BL());
		cp.add(b, BorderLayout.SOUTH);
	}

	public static void main(String[] args) {
		TicTacToe test = new TicTacToe();
		JFrame frame = new JFrame("TicTacToe");
		frame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
		frame.getContentPane().add(test);
		frame.setSize(100,100);
	    test.init();
	    test.start();
	    frame.setVisible(true);
	}
}
