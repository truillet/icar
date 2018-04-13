package icar.event;

import java.util.Set;

/**
 * Cette classe indique que le système de classification possède de nouvelles
 * classes et reprécise alors toutes les classe connues par le système.
 *
 * @author SALUT Jerome
 */
public class IcarRapportClasseEvent {

    // ATTRIBUTS
    
    // La source de l'évenement
    private Object source;

    // La liste des classe à transmettre
    private Set listeClasse;

    // CONSTRUCTEUR
    /**
     *  Crée un rapport de modification de la liste des classes
     *
     * @param source la source ayant déclenché l'évenement
     * @param listeClasse la liste des classes connues
     */
    public IcarRapportClasseEvent(Object source, Set listeClasse) {

	this.source = source;
	this.listeClasse = listeClasse;

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
     * Renvoie la liste des classes connues
     *
     * @return a value of type 'Set'
     */
    public Set getListeClasse() {
	return listeClasse;
    }
}
