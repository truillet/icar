package icar;

import java.awt.*;
import java.util.Vector;
import icar.util.Point;

/**
 * Cette classe est un containeur graphique pour un tracé extrait du
 * dictionnaire d'un composant IcarComponent.
 * <br> Celà permet donc seulement une visualisation des tracés d'une classe
 * par exemple.
 *
 * @author SALUT Jerome
 */
public class IcarPreview extends Canvas {

    // CONSTANTES
    /**
     * La marge non dessinable autour du composant
     */
    static public final int marge = 10;
    /**
     * La couleur du fond du composant
     */
    static public final Color couleurFond = Color.lightGray ;

    // ATTRIBUTS
	
    // La taille de la zone de dessin
    private int tailleX;
    private int tailleY;

    // Le tracé
    private Vector trace;

    // CONSTRUCTEUR

    /**
     *
     *
     * @param tailleX taille en X
     * @param tailleY taille en Y
     */
    public IcarPreview(int tailleX, int tailleY) {

	// Appelle le constructeur de la super class
	super();

	// Cree un tracé vide
	trace = null;

	// Fixe la taille de la zone
	this.tailleX = tailleX;
	this.tailleY = tailleY;

    }

    /**
     * Crée la zone de visualisation à partir d'un tracfé
     *
     * @param tailleX taille en X
     * @param tailleY taille en Y
     * @param trace le tracé à afficher
     */
    public IcarPreview(int tailleX, int tailleY, Vector trace) {

	this(tailleX, tailleY);
	afficher(trace);
    }

    // ACCESSEURS

    /**
     * Renvoie le trace actuellement en apercu
     *
     * @return a value of type 'Vector'
     */
    public Vector getTrace() {
	return trace;
    }

    // METHODES PUBLIQUES

    /**
     * Affiche un tracé dans le composant
     *
     * @param trace le tracé à afficher
     */
    public void afficher(Vector trace) {
	// Change le trace
	this.trace = trace;

	// Demande un rafraichissement
	repaint();
    }

    /**
     * Dessine la zone d'affichage (utilisé par AWT)
     *
     * @param g a value of type 'Graphics'
     */
    public void paint(Graphics g) {

	// Fixe la couleur du fond
	Dimension d = this.getSize();
	g.setColor(couleurFond);
	g.fillRect(0, 0, d.width, d.height);

	// Si aucun trace, rien a dessiner
	if (trace == null) return;

	// DETECTION DE LA BOITE ENGLOBANTE
	double 
	    maxX = java.lang.Double.NEGATIVE_INFINITY, 
	    minX = java.lang.Double.POSITIVE_INFINITY, 
	    maxY = java.lang.Double.NEGATIVE_INFINITY, 
	    minY = java.lang.Double.POSITIVE_INFINITY;

	// Cherche le max et min des X dans le trace
	// ainsi que le max et min des Y 
	for (int k=0; k < trace.size(); k++) {
	    Point pk = (Point)(trace.elementAt(k));
	    if (pk.getX() > maxX)
		maxX = pk.getX();
	    if (pk.getX() < minX)
		minX = pk.getX();
	    if (pk.getY() > maxY)
		maxY = pk.getY();
	    if (pk.getY() < minY)
		minY = pk.getY();
	}


	// Calcul des rapport de diminution
	double rapportX =  (tailleX - marge) / (maxX-minX + marge);
	double rapportY =  (tailleY - marge) / (maxY-minY + marge);

	// Redessine le tracé
	// Parcour le tracé et le redessine
	g.setColor(Color.black);
	for (int k =0; k<= trace.size()-2; k++) {
	    Point p_sav =(Point)trace.elementAt(k) ;
	    Point p = (Point)trace.elementAt(k+1);
	    g.drawLine((int)((p_sav.getX() -minX + marge) * rapportX), 
		       (int)((p_sav.getY() -minY + marge) * rapportY), 
		       (int)((p.getX() - minX + marge) * rapportX), 
		       (int)((p.getY() - minY + marge) * rapportY));
	    
	}
	
    }
 
    /**
     * Vide la zone d'affichage
     *
     */
    public void vider() {
	// Efface le vecteur du trace
	trace.clear();

	// Rafraichit l'affichage
	repaint();
    }
    
 
    /**
     * Renvoie la taille de l'objet
     *
     * @return a value of type 'Dimension'
     */
    public Dimension getPreferredSize() {
	return new Dimension(tailleX, tailleY);  
    }

    /**
     *  Renvoie la taille maximum de l'objet
     *
     * @return a value of type 'Dimension'
     */
    public Dimension getMaximumSize() {
	return getPreferredSize();
    }
 
}
