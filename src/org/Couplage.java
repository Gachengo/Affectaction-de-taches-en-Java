package org;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class Couplage {
	
	/*
	 * @ Steven Cib.
	*/
	public static void main(String[] args) {

		// TODO Auto-generated method stub
		
		@SuppressWarnings("unused")
	
		Graphe graphe = creetReseau("data.txt");
		
		//Affichage du graphe
		
		System.out.println("Graphe :");
		System.out.println("--------------");
		
		graphe.afficheGraphe();
		
		System.out.println();
		
		//Affichage du chemin BFS
		System.out.println("Chemin BFS :");
		System.out.println("--------------");
		
		LinkedList<Noeud> cheminBfs = new LinkedList<Noeud>();
		
		cheminBfs = graphe.cheminBFS(new Noeud(0), new Noeud(11));
		
		for(int i = 0; i < cheminBfs.size(); i++)
			if(i == cheminBfs.size() -1)
				System.out.print(cheminBfs.get(i).getId());
			else
				System.out.print(cheminBfs.get(i).getId() + "->");
		
		//Affiche chemin inverse
		System.out.println("\n\nChemin BFS inverse :");
		
		boolean exist = graphe.inverseChemin(cheminBfs);
		
		//flot max
		
		System.out.println("\n\nFord Fulkerson :");
		
		int flot_max = graphe.ff(new Noeud(0), new Noeud(11));
		
		
	}
	public static Graphe creetReseau(String fileName) {
			
		ArrayList<String> arrayList = new ArrayList<String>();
		Noeud[][] noeuds = null;
		BufferedReader reader;
		int sizeMatrix = 0;
			
		try {
			reader = new BufferedReader(new FileReader(fileName));
				
			String currentLine;
				
			while((currentLine = reader.readLine()) != null) {
					
				arrayList.add(currentLine);
			}
			sizeMatrix = Integer.parseInt(arrayList.get(0));
			
			String personne = "";
			
			for(int i = sizeMatrix + 1; i <= sizeMatrix * 2; i++) {
				
				if(i < sizeMatrix * 2)
					personne  += i + " ";
				else
					personne  += i;
			}
			arrayList.remove(0);
			
			arrayList.add(0, personne);
			
			int width  = sizeMatrix;
			int lenght = (sizeMatrix * 2) + 2;
			
			noeuds = new Noeud[lenght][width];
				
			for(int i = 0; i < lenght; i++){
				
				for(int j = 0; j < width; j++) {
						
					noeuds[i][j] = new Noeud(-1);
				}
			}	
			String[] s = arrayList.get(0).split(" ");
			
			for(int i = 0; i < s.length; i++) {
				
				noeuds[0][i] = new Noeud(Integer.parseInt(s[i]));
			}
			for(int i = 1; i <= sizeMatrix; i++) {
					
				String[] currentL = arrayList.get(i).split(" ");
				
				int ii = sizeMatrix + i;
				
				for(int j = 0; j < currentL.length; j++) {
						
					noeuds[ii][j] = new Noeud(Integer.parseInt(currentL[j]));					
				}
			}
			for(int i = 1; i < sizeMatrix + 1; i++) {
				
				noeuds[i][0] = new Noeud(noeuds.length - 1);	
			}
			
		}catch(FileNotFoundException e) {
				
			System.out.println("Source Date Not Found :" + e);
				
		}catch(IOException e) {
				
			System.out.println("Error " + e);
		}
		return new Graphe(noeuds, sizeMatrix);
	}
}
