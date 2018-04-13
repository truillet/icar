
package icar.util;

import java.lang.Math;

/**
 * Cette classe permet la manipulation de matrices carr�es, notemment,
 * pour obtenir des matrices invers�es.
 * @author SALUT Jerome
 */
public class MatriceCarre {

    // ATTRIBUTS

    // Les donnees de la matrice
    private double[][] donnees;

    // CONSTRUCTEURS

    /**
     * Creation d'une matrice vide. 
     * Tout les �l�ments de la matrice sont initialis� � 0 par d�faut.
     *
     * @param taille la taille de la matrice carre
     */
    public MatriceCarre(int taille) {
	donnees = new double[taille][taille];

	// Initialisation
	for (int x=0; x <taille; x++) {
	    // Comme la matrice est carre, on part de X
	    for (int y=x; y<taille; y++) {
		donnees[x][y] = 0;
		donnees[y][x] = 0;

	    }
	}

    }

    /**
     * Creation d'une matrice � partir d'un tableau de donn�es.
     *
     * @param tableau un tableau � 2 dimensions contenant les donn�es 
     * � charger dans la matrice
     * @exception Exception si le param�tre n'est pas un tableau carr�
     */
    public MatriceCarre(double [][] tableau) throws Exception{
	// Verifie que la matrice est carr�
	if (tableau.length != tableau[0].length) 
	    throw new Exception("Matrice non carre");

	// Allocation de l'espace m�moire
	donnees = new double[tableau.length][tableau.length];

	// Copie des donn�es
	for (int y=0; y <tableau.length; y++) {
	    for (int x=0; x<tableau.length; x++) {
		donnees[y][x] = tableau[y][x];
	    }
	}
	
    }

    /**
     * Creation d'une matrice � partir d'une autre matrice
     *
     * @param m la matrice � copier
     */
    public MatriceCarre(MatriceCarre m){
	// Allocation de l'espace m�moire
	donnees = new double[m.donnees.length][m.donnees.length];

	try {
	    // Copie des donn�es
	    for (int y=0; y <m.donnees.length; y++) {
		for (int x=0; x<m.donnees.length; x++) {
		    donnees[y][x] = m.get(x,y);
		}
	    }
	} catch (Exception e) {
	    // Normalement il n'y a pas d'exception
	    System.out.println(e);
	}
    }

    /**
     * Creation d'une matrice identit�
     *
     * @param taille la taille de la matrice
     * @return la matrice identit� cr�e
     */
    public static MatriceCarre getIdentite(int taille) {
	// Creation d'une matrice vide de 0
	MatriceCarre resultat = new MatriceCarre(taille);

	try {
	    // Initialisation de la diagonale
	    for (int k=0; k<taille; k++) {
		resultat.set(k,k,1);
	    }
	} catch (Exception e) {
	    return null;
	}

	return resultat;
    }

    // ACCESSEURS

    /**
     * Affecte une valeur � une case de la matrice
     *
     * @param x (0, taille-1)
     * @param y (0, taille-1)
     * @param valeur la nouvelle valeur
     * @exception Exception si les indices sont hors bornes
     */
    public void set(int x, int y, double valeur) throws Exception{
	if ( (x<0) || (y<0) || (x>=donnees.length) || (y>=donnees.length)) {
	    throw new Exception("set: Indice hors limite");
	}
	donnees[y][x] = valeur;
    }

    /**
     * Renvoie la valeur d'une case
     *
     * @param x (0, taille-1)
     * @param y (0, taille-1)
     * @return la valeur courante de la case
     * @exception Exception si les indices sont hors bornes
     */
    public double get(int x, int y) throws Exception{
	if ( (x<0) || (y<0) || (x>=donnees.length) || (y>=donnees.length)) {
	    throw new Exception("set: Indice hors limite");
	}
	return donnees[y][x];
    }  


    // METHODES
  
    /**
     * Renvoie l'inverse de la matrice par la methode du pivot de gauss 
     *
     * @return la matrice invers�e
     * @exception Exception si la matrice n'est pas inversible
     */
    public MatriceCarre inverse() throws Exception{
	// Copie de la matrice courante
	MatriceCarre courant = new MatriceCarre(this);

	// Creation de la matrice identite
	MatriceCarre resultat = MatriceCarre.getIdentite(donnees.length);

	// Transforme la matrice sous forme triangulaire sup�rieure
	// (valeur nulle dans la partie inf�rieure)
	for (int x=0; x<donnees.length; x++) {
	    for (int y=x+1; y<donnees.length; y++) {
		// Ici on prend comme pivot la valeur dans la diagonale 

		// Optimisation du calcul du pivot (pivot max non nul)
		double max = java.lang.Double.NEGATIVE_INFINITY;
		int numeroLigne = 0;
		for (int y2=x; y2<donnees.length; y2++) {
		    if (Math.abs(courant.donnees[y2][x]) > max) {
			max = Math.abs(courant.donnees[y2][x]);
			numeroLigne = y2;
		    }
		}

		// Inversion des lignes "x" et "numeroLigne" si necessaire
		if (numeroLigne != x) {

		    // Inversion des lignes
		    double temp;
		    for (int k=0; k<donnees.length; k++) {
			// Inversion dans la matrice courante
			temp = courant.donnees[numeroLigne][k];
			courant.donnees[numeroLigne][k] = 
			    courant.donnees[x][k];
			courant.donnees[x][k] = temp;

			// Inversion dans la matrice identite
			temp = resultat.donnees[numeroLigne][k];
			resultat.donnees[numeroLigne][k] = 
			    resultat.donnees[x][k];
			resultat.donnees[x][k] = temp;
		    }
		}

		// Test d'inversibilit�
		if (courant.donnees[x][x] == 0) {
		    //System.out.println(courant);
		    throw new Exception("inverse: Matrice non inversible");

		}
		// Calcul du pivot
		double pivot = courant.donnees[y][x] / courant.donnees[x][x];


		for (int k=0; k<donnees.length; k++) {
		    // Operation elementaire: composition de ligne
		    // de maniere a ce que le premiere element des autres
		    // lignes soit 0
		    // Ly = Ly - pivot * LPivot
		    courant.donnees[y][k] -=  pivot * courant.donnees[x][k];
		    resultat.donnees[y][k] -=  pivot * resultat.donnees[x][k];

		}
	    }
	}

	/*
	// Transforme la matrice sous forme diagonale
	// (valeur nulle dans la partie superieure)
	for (int x=donnees.length-1; x>=0; x--) {
	    for (int y=x-1; y>=0; y--) {		
		// Ici on prend comme pivot la valeur dans la diagonale 
		// Calcul du pivot
		double pivot = courant.donnees[y][x] / courant.donnees[x][x];

		for (int k=0; k<donnees.length; k++) {
		    // Operation elementaire: composition de ligne
		    // de maniere a ce que le premiere element des autres
		    // lignes soit 0
		    // Ly = Ly - pivot * LPivot
		    courant.donnees[y][k] = 
			courant.donnees[y][k] - pivot * courant.donnees[x][k];
		    resultat.donnees[y][k] = 
			resultat.donnees[y][k] - pivot * resultat.donnees[x][k];

		}
	    }
	}

	// Mise a 1 de la diagonale
	for (int x=0; x<donnees.length; x++) {
	    for (int k=0; k<donnees.length; k++) {
		double pivot = courant.donnees[x][x];
		resultat.donnees[x][k] = resultat.donnees[x][k] / pivot;
		// courant.donnees[x][k] = courant.donnees[x][k] / pivot;
		// => on obtiendrai des 1 sur la diagonale de courant, soit
		// la matrice identit�
	    }
	}
	*/


	// Triangularisation de la partie sup�rieure
	for ( int x = donnees.length-1; x >= 0; x-- ) {
	    for ( int i = 0; i < x; i++ ) {
		// Calcul du pivot
		double pivot = courant.donnees[i][x] / courant.donnees[x][x];
		for ( int j = 0; j < donnees.length; j++ ) {
		    resultat.donnees[i][j] -= pivot * resultat.donnees[x][j];
		}
	    }
	     // Mise � 1 de la diagonale
	    for ( int j = 0; j < donnees.length; j++ ) {
		resultat.donnees[x][j] /= courant.donnees[x][x];
	    }
	}

	//System.out.println(courant);
	// Renvoie le r�sultat
	return resultat;
    }

    
    /**
     * Renvoie une repr�sentation visuelle de la matrice
     *
     * @return chaine de caract�re repr�sentant la matrice
     */
    public String toString() {
	String resultat = "";

	for (int y=0; y <donnees.length; y++) {
	    for (int x=0; x<donnees.length; x++) {
		// Approximation du r�sultat pour affichage
		resultat += ((double)(Math.round(donnees[y][x] * 100)))/100 + "  ";
	    }
	    resultat += "\n";
	}

	return resultat;
    }

}
