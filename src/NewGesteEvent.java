package icar;

import java.util.Vector;

/**
 * Cette classe repr�sente l'�venement "nouveau geste".
 * Elle transporte le vecteur du geste nouvellement cr�e.
 *
 * @author SALUT Jerome
 */
class NewGesteEvent {

    // ATTRIBUTS

    // Le trac�
    private Vector trace;

    // La source de l'�venement
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

    // Renvoie la source de l'�venement
    public Object getSource() {
	return source;
    }

    // Renvoie le vecteur des points du trac�
    public Vector getTrace() {
	return trace;
    }

    // Renvoie l'echantillonage du trace
    public int getEchantillonage() {
	return echantillonage;
    }
   
}
