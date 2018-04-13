package icar.util;

import java.io.Serializable;

/**
 * Cette classe représente un point à 3 composantes: 
 * <br> - coordonnée X
 * <br> - coordonnée Y
 * <br> - coordonnée temporelle
 *
 * @author SALUT Jerome
 */
public class Point implements Serializable{

    // CONSTANTE
    static final long serialVersionUID = 8557804818606144345L;

    // ATTRIBUTS
    private int x;
    private int y;
    private long date;

    // CONSTRUCTEURS

    /**
     * Cree un point à partir des 3 coordonnées
     *
     * @param x a value of type 'int'
     * @param y a value of type 'int'
     * @param date a value of type 'long'
     */
    public Point(int x, int y, long date) {
	this.x = x;
	this.y = y;
	this.date = date;
    }

    /**
     * Cree un point à partir d'un autre point
     *
     * @param p a value of type 'Point'
     */
    public Point(Point p) {
	this.x = p.getX();
	this.y = p.getY();
	this.date = p.getDate();
    }

    // ACCESSEURS

    /**
     * Renvoie la coordonnée X du point
     *
     * @return a value of type 'int'
     */
    public int getX() {
	return x;
    }

    /**
     * Renvoie la coordonnée Y du point
     *
     * @return a value of type 'int'
     */
    public int getY() {
	return y;
    }

    /**
     * Describe Renvoie la coordonnée temporelle du point
     *
     * @return a value of type 'long'
     */
    public long getDate() {
	return date;
    }

    // METHODES

    /**
     * Permet de calculer la distance séparant 2 points
     *
     * @param p le point dont on cherche la distance depuis le point courant
     * @return a value of type 'double'
     */
    public double distance(Point p) {
	return java.lang.Math.sqrt( (getX() - p.getX()) * (getX() - p.getX()) 
				  + (getY() - p.getY()) * (getY() - p.getY()) );
    }

    /**
     * Renvoie une représentation textuelle du point
     *
     * @return une chaine de caractère de la forme (X,Y,T)
     */
    public String toString() {
	return "(" + x + "," + y + "," + date + ")";
    }

}
