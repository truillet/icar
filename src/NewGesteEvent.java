package icar;

import java.util.Vector;

/**
 * Cette classe représente l'évenement "nouveau geste".
 * Elle transporte le vecteur du geste nouvellement crée.
 *
 * @author SALUT Jerome
 */
class NewGesteEvent {

    // ATTRIBUTS

    // Le tracé
    private Vector trace;

    // La source de l'évenement
    private Object source;

    // La valeur de l'echantillonage du trace
    private int echantillonage;

    // CONSTRUCTEUR
    public NewGesteEvent(Object source, Vector trace, int echantillonage) {
	this.source = source;
	this.trace = trace;
	this.echantillonage = echantillonage;
    }

    // ACCESSEURS

    // Renvoie la source de l'évenement
    public Object getSource() {
	return source;
    }

    // Renvoie le vecteur des points du tracé
    public Vector getTrace() {
	return trace;
    }

    // Renvoie l'echantillonage du trace
    public int getEchantillonage() {
	return echantillonage;
    }
   
}
