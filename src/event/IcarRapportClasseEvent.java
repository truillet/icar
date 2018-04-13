package icar.event;

import java.util.Set;

/**
 * Cette classe indique que le syst�me de classification poss�de de nouvelles
 * classes et repr�cise alors toutes les classe connues par le syst�me.
 *
 * @author SALUT Jerome
 */
public class IcarRapportClasseEvent {

    // ATTRIBUTS
    
    // La source de l'�venement
    private Object source;

    // La liste des classe � transmettre
    private Set listeClasse;

    // CONSTRUCTEUR
    /**
     *  Cr�e un rapport de modification de la liste des classes
     *
     * @param source la source ayant d�clench� l'�venement
     * @param listeClasse la liste des classes connues
     */
    public IcarRapportClasseEvent(Object source, Set listeClasse) {

	this.source = source;
	this.listeClasse = listeClasse;

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
     * Renvoie la liste des classes connues
     *
     * @return a value of type 'Set'
     */
    public Set getListeClasse() {
	return listeClasse;
    }
}
