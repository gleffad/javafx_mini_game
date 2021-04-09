import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Pion {
	private boolean etatSelection;
	private Color couleur;

	public Pion(Color c) {
		couleur = c;
		etatSelection = false;
	}
	public void setEtatSelection(boolean b) {
		etatSelection = b;
	}
	public boolean getEtatSelection() {
		return etatSelection;
	}
	public Color getColor() {
		return couleur;
	}
	public String toString() {
		String col;
		String bin;
		//etat logique
		if (etatSelection) {
			bin = "t";
		} else {
			bin = "f";
		}
		//couleur
		if (couleur.equals(Color.RED)) {
			col = "R";
		} else if (couleur.equals(Color.GREEN)) {
			col = "B";
		} else if (couleur.equals(Color.BLUE)) {
			col = "G";
		} else {
			col = "_";
		}
		return "[" + col + "," + bin + "]";
	}
	public boolean memeCoul(Pion p) {
		return couleur.equals(p.couleur);
	}

	public void dessine(Graphics g, int x, int y) {
		g.setColor(couleur);
		if (etatSelection) {
			g.fillRect(x, y, 45, 45);
		} else {
			g.fillOval(x, y, 50, 50);
		}
	}
	public static void main(String[] args) {
		Pion a = new Pion(Color.RED);
		Pion b = new Pion(Color.BLUE);
		Pion c = new Pion(Color.GREEN);
		b.setEtatSelection(true);
		System.out.println("-----a-----\n");
		System.out.println(a);
		System.out.println("-----b-----\n");
		System.out.println(b);
		System.out.println("-----c-----\n");
		System.out.println(c);
	}
}
