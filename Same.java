import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Same extends JPanel {
	JFrame frame;
	JPanel dessin;
	JLabel text;
	Grille grid;
	
	public static void main(String[] args) {
		Same ig = new Same();
		ig.go();
	}
	public Same() {
		grid = new Grille();
	}
	public void go() {
		frame = new JFrame("SAME");
		dessin = this;
		JToolBar toolBar = new JToolBar(); 
		JButton buttonBegin = new JButton("new game !");
		ActionHandler aHandler = new ActionHandler();
		MouseHandler mHandler = new MouseHandler();
		text = new JLabel();

		//toolbar
		frame.getContentPane().add(BorderLayout.WEST, toolBar);
		toolBar.addSeparator();

		//graphical grid
		dessin.setPreferredSize(new Dimension(grid.nbCol()*50, grid.hauteur*50 + 40));
		frame.getContentPane().add(BorderLayout.CENTER, dessin);

		//"newgame" button
		toolBar.add(buttonBegin);

		//main window
		frame.getContentPane().add(BorderLayout.NORTH, text);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setSize(dessin.getWidth() + 125, dessin.getHeight());
		frame.setVisible(true);

		//listeners
		buttonBegin.addActionListener(aHandler);
		dessin.addMouseListener(mHandler);
		dessin.addMouseMotionListener(mHandler);
	}
	public void paintComponent(Graphics g) {
		g.fillRect(0, 0, grid.largeur*60, grid.hauteur*100);
		grid.dessine(g);
	}
	public class ActionHandler implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			grid.restart();
			text.setText("");
			dessin.repaint();
		}
	}
	public class MouseHandler implements MouseListener, MouseMotionListener {
		@Override
		public void mouseMoved(MouseEvent event) {
			int x = event.getX() / 50;
			int y = grid.hauteur - (event.getY() / 50) - 1;
				grid.deselectionne();
			if (grid.selectionnePions(x, y)) {
				System.out.println("(" + x + "," + y + ")");
			}
			dessin.repaint();
		}
		@Override
		public void mouseClicked(MouseEvent event) {
			int x = event.getX() / 50;
			int y = grid.hauteur - (event.getY() / 50) - 1;
			if (x < grid.nbCol() && y < grid.sizeCol(x)
					&& grid.existSelection()) {
				grid.supprimerSelection();
			}
			if (!grid.existGroupe()) {
				if (grid.nbCol() == 0) {
					text.setText("win !");
				} else {
					text.setText("game over !");
				}
			}
			dessin.repaint();
		}
		@Override
		public void mousePressed(MouseEvent event) {}
		@Override
		public void mouseReleased(MouseEvent event) {}
		@Override
		public void mouseEntered(MouseEvent event) {}
		@Override
		public void mouseExited(MouseEvent event) {}
		@Override
		public void mouseDragged(MouseEvent event) {}
	}
}
