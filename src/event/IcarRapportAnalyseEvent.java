package icar.event;

/**
 * Cette classe contient le rapport d'analyse produit par le système de
 * classification pour un tracé donné.
 * <br>
 * Plus précisement, elle contient:
 * <br>- l'identifiant de la classe reconnue
 * <br>- une chaine de caractère explicitant les détails de reconnaissance
 *
 * @author SALUT Jerome
 */
public class IcarRapportAnalyseEvent {

    // ATTRIBUTS
    
    // La source de l'évenement
    private Object source;

    // Le message à transmettre
    private String message;

    // Motif de la classe reconnue
    private String motif;

    /**
     * Construit l'évenement du rapport d'analyse
     *
     * @param source la source ayant déclenché l'évenement
     * @param motif l'identifiant de la classe reconnue
     * @param message le détail de la reconnaissance de la classe
     */
    public IcarRapportAnalyseEvent(Object source, String motif, String message) {

	this.source = source;
	this.message = message;
	this.motif = motif;

    }

    // ACCESSEURS
 
    /**
     * Renvoie la source de l'évenement
     *
     * @return a value of type 'Object'
     */
    public Object getSource() {
	return source;
    }

    /**
     * Renvoie le motif de la classe reconnu
     *
     * @return a value of type 'String'
     */
    public String getMotif() {
	return motif;
    }

    /**
     * Renvoie le message à transmettre
     *
     * @return a value of type 'String'
     */
    public String getMessage() {
	return message;
    }
}
