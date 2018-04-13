package icar;

import java.util.Vector;

/**
 * Cette classe transporte les informations apres classification d'un trace
 * avec en autre la classe reconnue, mais aussi des informations fines sur
 * le processus de reconnaissance.
 */
class TraceRapportAnalyse {

    // Le motif reconnu
    private String motif;

    // Flag de rejet par probabalité et le score
    private boolean rejetProba;
    private double proba;

    // Flag de rejet par calcul des distances de mahalanobis
    private boolean rejetDistance;

    // La liste des score pour chaque classe
    private Vector listeScore;

    // Le trace initial
    private Trace trace;

    // CONSTRUCTEUR
    public TraceRapportAnalyse(Trace trace, String motif,  
			       boolean rejetProba, double proba,
			       boolean rejetDistance,
			       Vector listeScore) 

    {
	this.trace = trace;
	this.motif = motif;

	this.rejetProba = rejetProba;
	this.proba = proba;
	this.rejetDistance = rejetDistance;
	this.listeScore = listeScore;

    }

    // ACCESSEURS

    // Renvoie le motif de la classe trouve
    public String getMotif() {
	return motif;
    }

    // Renvoie le trace initial
    public Trace getTrace() {
	return trace;
    }

    // Renvoie l'etat du rejet et sa nature
    public String getRejet() {
	return rejetProba?"Rejet par test de probabilité ("+proba+")":
	      rejetDistance?"Rejet par calcul des distances de mahalanobis":"";
    }

    // Renvoie la liste des scores
    public String getScores() {
	String resultat = "Liste des scores obtenus pour chaque classe:\n";

	// Parcours de la liste des scores
	for (int k=0; k<listeScore.size(); k++) {
	    resultat += listeScore.elementAt(k) + "\n";
	}

	return resultat;
    }


}
