package icar;

import java.util.Vector;
import java.util.Iterator;

/**
 * Cette classe représente l'ensemble des exemples de trace pour un geste
 * et permet d'obtenir le vecteur moyen et la matrice de covariance
 * representant ces traces.
 *
 * @author SALUT Jerome
 */
class Entraineur implements java.io.Serializable {

    // CONSTANTE

    // Fixe l'uid de la classe (!attention)
    static final long serialVersionUID = -6507827164451128011L;

    // ATTRIBUTS

    // L'ensemble des traces d'apprentissage
    private Vector listeTrace;

    // Le vecteur moyen des caracteristiques des tracés
    private double[] vecteurMoyen;

    // La matrice de covariance associé au tracé et au vecteur moyen
    private double[][] matCov;


    // CONSTRUCTEUR
    public Entraineur() {
	// Liste vide
	listeTrace = new Vector();


	// Alloue l'espace memoire pour le vecteur moyen
	vecteurMoyen = new double[Trace.nombreCarac];

	// Alloue l'espace memoire pour la matrice de covariance
	matCov = new double[Trace.nombreCarac][Trace.nombreCarac];

    }

    // METHODES

    // Permet d'ajoute un trace a l'echantillon des traces d'apprentissage
    public void ajouterTrace(Trace trace) {
	// Ajoute un trace 
	listeTrace.add(trace);
	
	// Recalcule le vecteur moyen et la matrice de covariance
	calcVecteurMoyen();
	calcMatriceCovariance();

    }

    // Permet de supprimer un trace (un Vector de Point)
    public void supprimerTrace(Vector trace) {
	// Cherche l'objet trace concerné
	for (int k=0; k<listeTrace.size(); k++) {

	    // Teste si on a trouvé l'objet Trace contenant le trace
	    // recherche
	    if (((Trace)listeTrace.elementAt(k)).getTrace() == trace) {
		listeTrace.remove(k);
		break;
	    }
	}

	// Recalcule le vecteur moyen et la matrice de covariance
	calcVecteurMoyen();
	calcMatriceCovariance();	
    }

    // PRIVATE

    // Recalcule le vecteur moyen
    private void calcVecteurMoyen() {
	// Parcour les nombreCarac caracteristiques
	for (int i=0; i < vecteurMoyen.length; i++) {

	    // Mise a 0 de la valeur moyenne pour la composante i
	    vecteurMoyen[i] = 0;

	    // Calcul de la somme des composantes i pour chaque exemple
	    // de cette classe d'entrainement
	    for (int e=0; e < listeTrace.size(); e++) {
		Trace trace = (Trace) listeTrace.elementAt(e);
		vecteurMoyen[i] += trace.getCaracteristiques()[i];
	    }

	    // Calcul de la moyenne
	    vecteurMoyen[i] /= listeTrace.size();

	}
	
    }

    // Recalcule le matrice de covariance
    public void calcMatriceCovariance() {
	// Parcour la matrice de covariance
	for (int i=0; i < matCov.length; i++) {	
	    for (int j=0; j < matCov.length; j++) {

		// Mise a 0 de la somme
		matCov[j][i] = 0;

		// Calcul de l'élement en i,j
		for (int e=0; e < listeTrace.size(); e++) {
		    Trace trace = (Trace) listeTrace.elementAt(e);
		    double [] tab = trace.getCaracteristiques();
		    matCov[j][i] += 
			((tab[i] - vecteurMoyen[i]) * (tab[j] - vecteurMoyen[j]));
			            
		}

	    }	
	}
    }


    // PUBLIC

    // Renvoie un Vector de Vector de Point contenant la liste de tout les
    // traces de la classe
    public Vector getTraces() {
	// Vecteur resultat
	Vector resultat = new Vector();
	
	// Parcour chaque objet Trace de la classe pour
	// en extraire son trace
	for (int k=0; k <listeTrace.size(); k++) {
	    // L'ajoute au resultat
	    resultat.add(((Trace)listeTrace.elementAt(k)).getTrace());
	}

	return resultat;
    }
    
    // Renvoie le nombre d'exemple appris par l'entraineur
    public double getNombreExempleAppris() {
	return listeTrace.size();
    }

    // Renvoie la matrice de covariance de la classe
    public double[][] getMatCov() {
	return matCov;
    }

    // Renvoie le vecteur moyen de la classe
    public double[] getVecteurMoyen() {
	return vecteurMoyen;
    }

    // toString (pour DEBUG)
    public String toString() {
	String resultat = "";
	
	// Le vecteur moyen
	resultat += "Vecteur moyen:\n";
	for (int k=0; k < vecteurMoyen.length; k++) {
	    resultat+= k + ":" +  vecteurMoyen[k] + "\n";
	}



	return resultat;
    }

}
