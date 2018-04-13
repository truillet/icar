package icar;

import java.util.Hashtable;
import java.util.Vector;
import java.util.Enumeration;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Set;
import icar.util.MatriceCarre;

/**
 * Cette classe est le système de classification et regroupe
 * l'ensemble des entraineurs de chaque classe.
 *
 * @author SALUT Jerome
 */
class Classifieur implements Serializable{
    // CONSTANTES

    // Attention ici, on fixe l'identifiant de la classe
    // pour permettre la deserialisation meme si cette classe
    // a legerement change (ajout de methode par exemple)
    // Il est évident qu'en cas d'ajout d'attribut, il faudra
    // surcharger les methodes de serialization pour remédier 
    // a cela, ou creer l'objet
    static final long serialVersionUID = 4710069656297218510L;

    // ATTRIBUTS
    
    // La liste des entraineurs indexé par leur motif
    // (l'appelation de leur geste)
    private Hashtable listeEntraineurs;

    // La matrice de covariance totale (sur l'ensemble des exemples
    // toute classe confondue)
    private double [][] matCov; 

    // La valeur d'echantillonage de l'ensemble des traces du dictionnaire
    private int echantillonage;

    
    // Class containeur representant une classe au sens de la classification
    class ClasseGeste implements Serializable{

	static final long serialVersionUID = -6663114876230563911L;
	// L'entraineur de cette classe de geste
	Entraineur entraineur;

	// Les poids wc de la fonction d'evaluation pour chaque classe
	double[] wc;

	// Le poids constant w0 de la fonction d'evaluation pour chaque classe
	double w0;

	// Le motif de la classe
	String motif;

	// Constructeur
	public ClasseGeste(String motif) {
	    this.entraineur = new Entraineur();
	    this.wc = new double[Trace.nombreCarac];
	    this.motif = motif;
	}

    }
    
    // CONSTRUCTEUR
    public Classifieur() {
	
	// Table de hashage vide
	listeEntraineurs = new Hashtable();


	// Matrice de covariance vide
	matCov = new double[Trace.nombreCarac][Trace.nombreCarac];

	// Echantillonage par defaut
	echantillonage = 1;
    }

    // PRIVEES

    // Calcule la matrice de covariance totale servant au calcul
    // des poids de la fonction d'evaluation
    private void calcMatCov() {
	
	// Calcule la somme totale du nombre d'exemple toute classe confondue
	int nombreExemple = 0;
	
	for (Enumeration e = listeEntraineurs.elements(); e.hasMoreElements();) {
	    nombreExemple += 
		((ClasseGeste)e.nextElement()).entraineur.getNombreExempleAppris() -1;
	}

	// Si le nombre d'exemple est egal au nombre de classe,
	// soit un exemple par classe, on ne peut rien faire
	if (nombreExemple ==0) return;

	// Nombre de classes
	//int nombreClasse = listeEntraineurs.size();

	// Calcule la matrice finale
	for (int i=0; i < matCov.length; i++) {
	    for (int j=0; j < matCov.length; j++) {


		// Somme des élément [i][j] des matrice de covariance de 
		//chaque classe
		matCov[j][i] = 0;
		for (Enumeration e = listeEntraineurs.elements(); 
		     e.hasMoreElements();) 
		{
		    matCov[j][i] += 
			((ClasseGeste)e.nextElement()).entraineur.getMatCov()[j][i];
		}
		
		// Divise par le nombre d'exemple
		//matCov[j][i] /= nombreExemple - nombreClasse;
		matCov[j][i] /= nombreExemple;
	    }
	}
    }

    // Calcule les poids wc et w0 pour chaque classe présente
    private void calcWcW0() {
	
	// Pour chaque classe
	for (Enumeration e = listeEntraineurs.elements(); e.hasMoreElements();) {
	    // --
	    // Calcul de wc

	    ClasseGeste classeGeste = ((ClasseGeste)e.nextElement());
	    double [] vecteurMoyen = classeGeste.entraineur.getVecteurMoyen();

	    // Calcul de l'inverse de la matrice de covariance globale
	    MatriceCarre m;
	    try {
		m = (new MatriceCarre(matCov)).inverse();
	
	    } catch (Exception ex) {
		//System.out.println("Matrice non inversible");
		return;
	    }
	    // Pour chaque composante du vecteur wc
	    for (int j=0; j < classeGeste.wc.length; j++) {
		// Mise a 0 de l'élément
		classeGeste.wc[j] = 0;
		
		// Somme
		for (int i=0; i < vecteurMoyen.length; i++) {
		    try {
		    classeGeste.wc[j] += m.get(i,j) * vecteurMoyen[i];
		    } catch (Exception ex) {
		    }
		}
		
	    }

	    // --
	    // Calcul de w0

	    // Mise a 0 de l'élément
	    classeGeste.w0 = 0;
		
	    // Calcul de la formule
	    for (int i=0; i < vecteurMoyen.length; i++) {
		classeGeste.w0 += classeGeste.wc[i] * vecteurMoyen[i];
	    }
	    classeGeste.w0 *= -0.5; 
	   
	}

    }

    // ACCESSEURS

    // Renvoie la liste des traces d'une classe sous forme d'un Vector
    // de Vector de Point
    public Vector getListeTrace(String motif) {
	if (motif == null) return new Vector();

	ClasseGeste classe = (ClasseGeste)listeEntraineurs.get(motif);
	if (classe != null) {
	    return classe.entraineur.getTraces();
	}
	else {
	    return new Vector();
	}
    }

    // Renvoie la liste des classes connues par le systeme de classification
    public Set getListeClasse() {
	return listeEntraineurs.keySet();
    }

    // Renvoie l'echantillonage du dictionnaire
    public int getEchantillonage() {
	return echantillonage;
    }

    // METHODES PUBLIQUES

    // Ajoute un tracé associé un motif
    public void ajouterTrace(String motif, Vector trace, int echantillonage) {
	// Teste l'existence ou non du motif et de l'entraineur associé
	if (!listeEntraineurs.containsKey(motif)) {
	    // Cree l'entraineur et l'inserer dans la table
	    listeEntraineurs.put(motif, new ClasseGeste(motif));
	}

        // Finalement, ajoute le nouveau tracé dans l'entraineur associé
	((ClasseGeste)listeEntraineurs.get(motif))
	    .entraineur.ajouterTrace(new Trace(trace));

	// REDEMANDER LE CALCUL DES COEFFS
	calcMatCov();
	calcWcW0();


	// S'il s'agit du premier trace appris, on fixe alors l'echantillonage 
	// une fois pour toute
	if ( (getListeClasse().size() == 1)
	     && (getListeTrace(motif).size() == 1) ) {
	    this.echantillonage = echantillonage;
	}
    }

    // Efface un exemple d'une classe
    public void effacerTrace(String motif, Vector trace) {

	// Teste au moins si la classe existe
	if (!listeEntraineurs.containsKey(motif)) {
	    return;
	}

	// Effacer l'exemple
	Entraineur entraineur = 
	    ((ClasseGeste)listeEntraineurs.get(motif)).entraineur;
	entraineur.supprimerTrace(trace);

	// Si l'entraineur n'a plus d'exemple on le supprime
	if (entraineur.getNombreExempleAppris() == 0) {
	    // On efface la classe (le recalcul des poids sera effectue)
	    effacerClasse(motif);
	} else {
	    // Recalcul de poids
	    calcMatCov();
	    calcWcW0();
	}
    }

    // Efface une classe
    public void effacerClasse(String motif) {
	// Efface la classe de la liste
	listeEntraineurs.remove(motif);

	// Recalcul des poids
	calcMatCov();
	calcWcW0();
    }

    // Classifie un tracé
    public TraceRapportAnalyse identifier(Vector tracePoint) {

	// Analyse de trace
	Trace trace = new Trace(tracePoint);

	// Sauvegarde de tous les scores
	Vector scoreSauv = new Vector();
	Vector listeScores = new Vector();

	// Variable temporaire pour le score de la classe
	double maxScore = java.lang.Double.NEGATIVE_INFINITY;
	// Nom de la classe sélectionnée temporairement
	String motif = null;



	// Pour chaque classe, calcul de vc
	for (Enumeration e = listeEntraineurs.elements(); e.hasMoreElements();) {
	    ClasseGeste classeGeste = ((ClasseGeste)e.nextElement());

	    // Le score de la classe en cours
	    double scoreCourant = classeGeste.w0;

	    // Calcul du score
	    for (int i=0; i < classeGeste.wc.length; i++) {
		scoreCourant += classeGeste.wc[i] * trace.getCaracteristiques()[i];
	    }
	    // Sauvegarde en vue de log
	    listeScores.add(classeGeste.motif + " " + scoreCourant);

	    // Mis a jour du maximum
	    if (scoreCourant >= maxScore) {
		maxScore = scoreCourant;
		motif = classeGeste.motif;

	    }

	    // Sauvegarde du score trouve
	    scoreSauv.add(new Double(scoreCourant));
	}

	// ---
	// Test de rejet par calcul de probabilité
	double pig = 0;
	for (int j=0; j < listeEntraineurs.size(); j++) {
	    double valeurCourante = 
		((Double)scoreSauv.elementAt(j)).doubleValue();
	    
	    pig += Math.exp( valeurCourante - maxScore );

	}	
	pig = 1 / pig;

	// On rejette tout les tracé de proba inférieure a 0.95
	boolean rejetProba = pig < 0.95;
	boolean rejetDistance = false;

	if (rejetProba) {
	    //System.out.println("Rejet de " + motif);
	    motif = null;
	} else {

	    // ----
	    // Deuxieme test de rejet par les distances de mahalanobis

	    // Le vecteur des caracteristiques du trace courant
	    double[] carac = trace.getCaracteristiques();
	    // Le resultat
	    double deltac = 0;
	    // Calcul de l'inverse de la matrice globale de covariance
	    MatriceCarre m;
	    try {
		m = (new MatriceCarre(matCov)).inverse();
	        	
		// Calcul de deltac
		for (int j=0; j<carac.length; j++) {
		    for (int k=0; k<carac.length; k++) {

			// Recupere le vecteur moyen de la classe identifiee
			double [] vecteurMoyen =
			    ((ClasseGeste)listeEntraineurs.get(motif))
                                     .entraineur.getVecteurMoyen();

			// Application de la formule
			deltac += m.get(j,k) * (carac[j] - vecteurMoyen[j]) 
			                     * (carac[k] - vecteurMoyen[k]);

		    }
		}
	    
	    } catch (Exception ex) {}

	    // Test de rejet selon la valeur de deltac
	    if (deltac > 0.5 * carac.length * carac.length) {
		motif = null;
		rejetDistance = true;

	    }

	}



	return (new TraceRapportAnalyse(trace,
					motif,
					rejetProba,
					pig,
					rejetDistance,
					listeScores));

    }



    // Sauvegarder l'ensemble du systeme de classification sur fichier
    public void sauvegarderVersFichier(String nomFichier) {

	try {
	    ObjectOutputStream fichierWrite = 
		new ObjectOutputStream(new FileOutputStream(nomFichier));

	    // Ecriture
	    fichierWrite.writeObject(this);

	    // Fermeture
	    fichierWrite.close();

	} catch (IOException e) {
	    System.out.println("Sauvegarde impossible du systeme de classification");
	    //System.out.println(e);
	}
    }

    // Charger l'ensemble du systeme de classification sur fichier
    static public Classifieur chargerDepuisFichier(String nomFichier) {

	try {
	    ObjectInputStream fichierRead = 
		new ObjectInputStream(new FileInputStream(nomFichier));

	    // Lecture
	    Classifieur objet = (Classifieur)fichierRead.readObject();

	    // Fermeture
	    fichierRead.close();

	    //System.out.println(
	    //   ((ClasseGeste)objet.listeEntraineurs.get("a")).serialVersionUID
	    //	       );

	    return objet;
	} catch (java.lang.ClassNotFoundException e) {
	    System.out.println("Fichier introuvable ou endommage: " + nomFichier);
	    return null;
	} catch( java.io.InvalidClassException e) {
	    System.out.println(e + "\n" + e.classname);
	    return null;

	} catch (IOException e) {
	    //System.out.println("Lecture impossible du systeme de classification");
	    return null;
	} catch (Exception e) {
	    System.out.println(e);
	    return null;
	}


    }

}
