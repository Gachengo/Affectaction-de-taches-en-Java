package org;

import java.util.ArrayList;

public class Noeud{
	
	private int idNoeud;
	private int nbVoisins;
	private ArrayList<Noeud>  successeurs;
	
	public Noeud(int id) {
 
		this.idNoeud = id;
	}
	public int getId() {
		
		return this.idNoeud;
	}
	// renvoie le nombre de voisins
	public int getNbrVoisins() {
   
		return this.nbVoisins;
	}
	// Ajuster le nombre de voisins
	public void setNbrVoissins(int nbrV) {
		
		this.nbVoisins = nbrV;
	}
	// retourne le nombre de successeurs
	public int getNbrSuccesseur() {
		
		return successeurs.size();
	}
	public void setSuccesseur(Noeud noeudS) {
		
		this.successeurs.add(noeudS);
	}
}
