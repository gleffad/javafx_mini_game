import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.*;
import java.awt.event.*;

public class Grille {
	private List<List<Pion>> ensPions;
	private List<Point> selection;
	public static int largeur = 5;
	public static int hauteur = 5;

	public Grille() {
		int roll;
		ensPions = new ArrayList<List<Pion>>();
		selection = new ArrayList<Point>();
		for (int i = 0; i < largeur; i++) {
			ensPions.add(new ArrayList<Pion>());
			for (int j = 0; j < hauteur; j++) {
				roll = (int)(Math.random() * 3);
				switch (roll) {
					case 0: ensPions.get(i).add(j, new Pion(Color.RED));
							break;
					case 1: ensPions.get(i).add(j, new Pion (Color.GREEN));
							break;
					case 2: ensPions.get(i).add(j, new Pion (Color.BLUE));
							break;
					default: System.out.println("err : grid init problem.");
							break;
				}
			}
		}
	}
	public boolean selectionnePions(int i, int j) {
		//out of bound handling
		if (i < 0 || j < 0 || i >= nbCol() || j >= sizeCol(i)) {
			System.out.println("\nerreur : out of bounds Pion selection !\n");
			return false;
		}
		//select current pion
		getPion(i, j).setEtatSelection(true);
		selectPoint(new Point(i, j));
		//upper call
		if (j + 1 < sizeCol(i) && !getPion(i, j + 1).getEtatSelection() && 
				getPion(i, j).memeCoul(getPion(i, j + 1))) {
			selectionnePions(i, j + 1);
		}
		//lower call
		if (j - 1 >= 0 && !getPion(i, j - 1).getEtatSelection() && 
				getPion(i, j).memeCoul(getPion(i, j - 1))) {
			selectionnePions(i, j - 1);
		}
		//left call
		if (i - 1 >= 0 && !getPion(i - 1, j).getEtatSelection() && 
				getPion(i, j).memeCoul(getPion(i - 1, j))) {
			selectionnePions(i - 1, j);
		}
		//right call
		if (i + 1 < nbCol() && !getPion(i + 1, j).getEtatSelection() && 
				getPion(i, j).memeCoul(getPion(i + 1, j))) {
			selectionnePions(i + 1, j);
		}
		if (selection.size() > 1) {
			return true;
		}
		deselectionne();
		return false;
	}
	public void supprimerSelection() {
		//supp pion from grille
		//trier ArrayList selection en j decroissant (bubble sort)
		boolean swapped = false;
		Point tmp;
		do {
			swapped = false;
			for (int i = 0; i < selection.size() - 1; i++) {
				if (selection.get(i).getY() < selection.get(i + 1).getY()) {
					tmp = selection.get(i);
					selection.set(i, selection.get(i + 1));
					selection.set(i + 1, tmp);
					swapped = true;
				}
			}
		} while (swapped);
		//supprimer les j dans l'ordre de ArrayList selection
		for (int i = 0; i < selection.size(); i++) {
			ensPions.get((int)selection.get(i).getX()).remove((int)selection.get(i).getY());
		}
		//supprimer les colonnes i vides
		for (int i = 0; i < ensPions.size(); i++) {
			if (ensPions.get(i).isEmpty()) {
				ensPions.remove(i);
			}
		}
		//supp List selection
		selection.clear();
	}
	public void deselectionne() {
		//deselectPions
		for (int i = 0; i < selection.size(); i++) {
			ensPions.get((int)selection.get(i).getX()).get((int)selection.get(i).getY()).setEtatSelection(false);
		}
		//supp selection
		selection.clear();
	}
	public boolean existGroupe() {
		
		for (int i = 0; i < nbCol(); i++) {
			for (int j = 0; j < sizeCol(i); j++) {
				if ((getPion(i, j).memeCoul(getPion(i, j + 1)))||(getPion(i, j).memeCoul(getPion(i + 1, j)))) {
					return true;
				}
			}
		}
		return false;
	}
	public void restart() {
		ensPions.clear();
		selection.clear();
		Grille g = new Grille();
		ensPions = g.ensPions;
	}
	public void dessine(Graphics g) {
		for (int i = 0; i < nbCol(); i++) {
			for (int j = 0; j < sizeCol(i); j++) {
				getPion(i, j).dessine(g, i * 50, (hauteur - j) * 50 - 50);
			}
		}
	}
	public Pion getPion(int i, int j) {
		return ensPions.get(i).get(j);
	}
	public int sizeCol(int i) {
		return ensPions.get(i).size();
	}
	public int nbCol() {
		return ensPions.size();
	}
	public List getCol(int j) {
		return ensPions.get(j);
	}
	public void selectPoint(Point p) {
		selection.add(p);
	}
	public boolean existSelection(){
		return !selection.isEmpty();
	}
	public String toString() {
		StringBuffer str = new StringBuffer();
		str.append("grille: \n");
		for (int i = 0; i < nbCol(); i++) {
				str.append("col"+i+":"+getCol(i)+"\n");
		}
		str.append("selection:\n"+selection + "\n");
		return str.toString();
	}
	public static void main(String[] args) {
		Grille test = new Grille();
		System.out.println(test);
		System.out.println("\nExist group ?!\n");
		System.out.println(test.existGroupe());
		System.out.println("\nPion(3,3) selected !\n");
		test.selectionnePions(3, 3);
		System.out.println(test);
		System.out.println("\nDeselection !\n");
		test.deselectionne();
		System.out.println(test);
		System.out.println("\nReselection Pion(3,3)!\n");
		test.selectionnePions(3, 3);
		System.out.println(test);
		System.out.println("\nSuppression de la selection !\n");
		test.supprimerSelection();
		System.out.println(test);
		System.out.println("\nRestart !\n");
		test.restart();
		System.out.println(test);
		System.out.println("\nYou win\n");
	}
}
