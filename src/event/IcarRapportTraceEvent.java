package icar.event;

import java.util.Vector;

/**
 * Cette classe indique qu'une classe ne possède plus le meme nombre d'exemple.
 * Elle reprécise alors la liste des exemples connus.
 *
 * @author SALUT Jerome
 */
public class IcarRapportTraceEvent {

    // ATTRIBUTS
    
    // La source de l'évenement
    private Object source;

    // Le motif de la classe
    private String motif;

    // La liste des traces de la classe
    private Vector listeTrace;

    // CONSTRUCTEUR
    /**
     * Crée un rapport de modification d'une classe
     *
     * @param source la source ayant déclenché l'évenement
     * @param motif l'identifiant de la classe concernée
     * @param listeTrace la liste des exemples de la classe
     */
    public IcarRapportTraceEvent(Object source, String motif, 
				  Vector listeTrace) {

	this.source = source;
	this.motif = motif;
	this.listeTrace = listeTrace;
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

    // 
    /**
     * Renvoie l'identifiant de la classe concernée
     *
     * @return a value of type 'String'
     */
    public String getMotif() {
	return motif;
    }

    /**
     * Renvoie la liste des traces de la classe
     *
     * @return a value of type 'Vector'
     */
    public Vector getListeTrace() {
	return listeTrace;
    }
}
