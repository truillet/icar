package icar.util;

/**
 * Cette classe permet de mesurer la pr�cision d'horloge du syst�me 
 * d'exploitation sur lequel la JVM tourne. 
 * En effet, il a �t� constat� que la pr�cision
 * minimum que l'on peut pour une mesure temporelle de date peut varier
 * de plusieur dizaine de millisecondes entre certain syst�mes.
 *
 * @author SALUT Jerome
 */
public class TimePrecision {

    // CONSTANTE

    // Precision de la mesure par defaut
    static final int precision = 5;

    // METHODE

    /**
     * Renvoie la valeur d'�chantillonage la plus petite sur ce systeme
     *
     * @return la valeur d'�chantillonage
     */
    static public int getMinPrecision() {

	// Le minimum d'�chantillonage
	long min = 0;
	
	// Stabilisation
	for (int r = 0; r < 100; ++ r) System.currentTimeMillis ();
	long time = System.currentTimeMillis (), 
	    time_prev = time;

	// On boucle pour faire une moyenne selon la pr�cision
	for (int i = 0; i < precision; ++ i) {

	    // Attendre que la date change
	    while (time == time_prev)
		time = System.currentTimeMillis ();
	    
	    // On fait la somme des difference constatees
	    min += (time - time_prev);
	    
	    time_prev = time;
	 }

	// On renvoit la moyenne des differences pour une meilleure precision
	return (int)(min/precision);

    }
}
