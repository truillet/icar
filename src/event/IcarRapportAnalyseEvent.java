package icar.event;

/**
 * Cette classe contient le rapport d'analyse produit par le syst�me de
 * classification pour un trac� donn�.
 * <br>
 * Plus pr�cisement, elle contient:
 * <br>- l'identifiant de la classe reconnue
 * <br>- une chaine de caract�re explicitant les d�tails de reconnaissance
 *
 * @author SALUT Jerome
 */
public class IcarRapportAnalyseEvent {

    // ATTRIBUTS
    
    // La source de l'�venement
    private Object source;

    // Le message � transmettre
    private String message;

    // Motif de la classe reconnue
    private String motif;

    /**
     * Construit l'�venement du rapport d'analyse
     *
     * @param source la source ayant d�clench� l'�venement
     * @param motif l'identifiant de la classe reconnue
     * @param message le d�tail de la reconnaissance de la classe
     */
    public IcarRapportAnalyseEvent(Object source, String motif, String message) {

	this.source = source;
	this.message = message;
	this.motif = motif;

    }

    // ACCESSEURS
 
    /**
     * Renvoie la source de l'�venement
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
     * Renvoie le message � transmettre
     *
     * @return a value of type 'String'
     */
    public String getMessage() {
	return message;
    }
}
