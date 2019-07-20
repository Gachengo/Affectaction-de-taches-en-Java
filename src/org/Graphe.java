package org;

import java.util.ArrayDeque;  
import java.util.ArrayList;  
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javas.systems.Systems;
/*
 * @Steven Cib.
*/
public class Graphe {
	
	private int nbNoeuds;
	private int nbrVertex;
	protected Noeud[][] noeuds;
	
	public Graphe(Noeud[][] nds, int nbrVertex) {
		
		this.noeuds = nds;
		this.nbrVertex = nbrVertex;
		this.nbNoeuds = noeuds.length;
	}
	//On verifie si la personne a choisie toutes les taches ou non
	public boolean isNoeud(int i, int j) {
		
		if(noeuds[i][j].getId() >= 0) {
			
			return true;
		}
		return false;
	}
	public boolean isExist(int line, Noeud noeud) {
		
		for(int i = 0; i < nbrVertex; i++) {
			
			if(noeuds[line][i].getId() == noeud.getId()) {
				
				return true;
			}
		}
		return false;
	}
	public List<Noeud> neihghtbours(int index){
		
		List<Noeud> edge = new ArrayList<Noeud>();
		
		for(int i = 0; i < nbrVertex; i++)
			if(isNoeud(index, i))
				edge.add(new Noeud(noeuds[index][i].getId()));
		return edge;
	}
	public List<Noeud> index(Noeud noeud){
		
		List<Noeud> edge = new ArrayList<Noeud>();
		
		for(int i = 0; i < noeuds.length; i++)
			if(isExist(i, noeud))
				edge.add(new Noeud(i));
		
		return edge;
	}
	/*
	 *@Steven Cib.
	*/
	//Cette methode retourne le parcours en largeur de la source au puits
	public LinkedList<Noeud> cheminBFS(Noeud source, Noeud puits){
		
		boolean vue[] = new boolean[noeuds.length];
		
		int[] niveau = new int[noeuds.length];
		
		for(int i = 0; i < vue.length; i++) {
			
			vue[i] = false;
		}
		vue[source.getId()] = true;
		niveau[source.getId()] = 0;
		
		Queue<Noeud> queu = new ArrayDeque<Noeud>();
		
		LinkedList<Noeud> courtChemin = new LinkedList<Noeud>();
		
		queu.add(source);
		courtChemin.add(source);
		
		//On parcourt tous les autres noeuds 
		while(!queu.isEmpty()) {
				
			source = queu.poll();		
			
			//On verifie les successeurs du noeud actuel
			for(Noeud index : neihghtbours(source.getId())) {
						
			//S'il n'est pas encore visite on le visite même chose pour ses voisins
			
				if(vue[index.getId()] == false){
						
					vue[index.getId()] = true;
					
					niveau[index.getId()] = niveau[source.getId()] + 1;
					
					queu.add(index);
					
					courtChemin.add(index);		
				}
			}
		}
		return courtChemin;
	}
	/*
	 *@ Steven Cib.
	*/
	//Cette methode verifier si le parcours en largeur peut être inverser donce du puit à la source
	
	public boolean inverseChemin(LinkedList<Noeud> chemin) {
		
		boolean isExist = false;
		
		Noeud puit  = chemin.getLast();
		
		boolean seen[] = new boolean[chemin.size()];
		
		int niveau[] = new int[chemin.size()];
		
		for(int i = 0; i < seen.length; i++) {
			
			seen[i] = false;
		}
		seen[puit.getId()] = true;
		
		niveau[puit.getId()] = 0;
		
		Queue<Noeud> queu = new ArrayDeque<Noeud>();
		
		LinkedList<Noeud> cheminRetour = new LinkedList<Noeud>();
		
		queu.add(puit);
		
		cheminRetour.add(puit);
		
		//On parcourt tous les autres noeuds
		while(!queu.isEmpty()) {
				
			puit = queu.poll();		
			
			//On verifie les successeurs du noeud actuel
			for(Noeud i : index(puit)) {
						
				//Si c'est un noeud et il n'est pas encore visite on le visite
				if(seen[i.getId()] == false){
						
					seen[i.getId()] = true;
					
					niveau[i.getId()] = niveau[puit.getId()] + 1;
					
					queu.add(i);
					
					cheminRetour.add(i);
				}
				else if(niveau[puit.getId()] == niveau[i.getId()]) {
					
					return false;
				}
			}
		}
		for(int i = 0; i < cheminRetour.size(); i++)
			if(i == cheminRetour.size() - 1)
				System.out.print(cheminRetour.get(i).getId());
			else
				System.out.print(cheminRetour.get(i).getId() + "->");
		
		return isExist;
	}
	//Cette methode renvoie le nombre de flot max depuit t jusqu'a s
	public int ff(Noeud s, Noeud t) {
		
		int flot_max = 0;
		LinkedList<Noeud> cheminBfs = new LinkedList<Noeud>();
		cheminBfs = cheminBFS(s, t);
		
		List<Noeud> tache = index(cheminBfs.getLast());
		List<Noeud> personne  = neihghtbours(cheminBfs.getFirst().getId());
		
		//le nombre de successeurs du noeud puit
		int nbrSucceur = tache.size();		
		
		int [] nbrS = new int[nbrSucceur];
		Noeud[] couplage = new Noeud[nbrSucceur];
		for(int i = 0; i < couplage.length; i++) {
			
			couplage[i] = new Noeud(-1);
		}
		for(int i = 0; i < nbrSucceur; i++) {
			
			nbrS[i] = index(tache.get(i)).size();
		}
		for(int i = 0; i < nbrSucceur; i++) {
				
			int min =  min_valTable(nbrS);
				
			Noeud noeudM = tache.get(min);
				
			for(Noeud pred : index(noeudM)) {
					
				//Procedure de marquage et d'augmentation de flot en verifiant toujours la loi de Kirchoff est respectée
				if((!in_array(couplage, pred)) && (couplage[min].getId() < 0)) {
						
					couplage[min] = pred;
					flot_max = flot_max + 1;
				}
			}
			nbrS[min] = nbrSucceur;
		}		
		System.out.println("---------------------------------------------------------");
		System.out.println("| Flot Max : " + flot_max + " \t\t\t\t\t\t|");
		System.out.println("---------------------------------------------------------");
		
		for(int i = 0; i < couplage.length; i++) {
			
			int num = i + 1;
			System.out.println("|-> La tâche numéro : " + num + " sera effectué par la personne " + getPositionNoeud(personne, couplage[i]) + " |");
		}
		System.out.println("---------------------------------------------------------");
		boolean sign = Systems.system();
		if(sign) {
			/*
			 * 
			 */
		}
		return nbrSucceur;
	}
	/* Etant donné que une personne a droit d'effectuer juste une tache;
	 * Cette methode verifie si une tache est deja affecter à une personne 			@Steven Cib.
	 * Cette methode verifie si un element se trouve deja dans un tableau
	*/
	public boolean in_array(Noeud[] tab, Noeud n) {
		
		boolean exist = false;
				
		for(int i = 0; i < tab.length; i ++) {
			
			if(tab[i].getId() ==  n.getId())
				exist = true;
		}
		return exist;
	}
	/*
	 * @ Steven Cib.
	*/
	//Cette methode retoune l'index de la plus petite valeur du tableau donné en parametre
	public int min_valTable(int[] tab) {
		
		int index = 0;
		int min  = tab[0];
		
		for(int i = 0; i < tab.length; i++) {
			
			if(tab[i] < min) {
				
				min = tab[i];
				index = i;
			}
		}
		return index;
	}
	/* @ Steven Cib.
	 * 
	 * Etant donné que le vecteur representant la liste de personne n'est pas forcement noté de 1 à n noeud
	 * Nous nous proposons de faire une methode qui prend en parametre un List contenant ce vecteur et un noeud
	 * Et qui renvoi l'index du noeud qui sera un noeud representant une personne
	 * Cette methode retourne la position d'un noeud dans un List<>
	 * 
	 * @ Steve Cib.
	 */
	public int getPositionNoeud(List<Noeud> list, Noeud noeud) {
		
		int position  = -1;
		
		for(int i = 0; i < list.size(); i++) {
			if(list.get(i).getId() == noeud.getId()) {
				
				position = i +1;
			}
		}
		return position;
	}
	/*
	 * @Steven Cib
	*/
}
