package icar;

import java.util.Vector;
import java.lang.Math;
import java.io.Serializable;
import icar.util.Point;

/**
 * Cette classe effectue la première analyse d'un trace en vue d'en dégager
 * les caractéristiques (selon l'algorithme de RUBINE)
 *
 * @author SALUT Jerome
 */
class Trace implements Serializable{

    // CONSTANTE

    // Fixe l'uid de la classe (!attention)
    static final long serialVersionUID = 2174692975053027546L;

    // ATTRIBUTS

    // Le trace en lui meme, c'est a dire l'ensemble de point qui le
    // definit
    private Vector trace;


    // Le vecteur des caracteristiques du trace
    private double caracteristiques[];
    public static final int nombreCarac = 13;

    // Les index de chaque carac dans le vecteur
    private static final int f1 = 0;
    private static final int f2 = 1;
    private static final int f3 = 2;
    private static final int f4 = 3;
    private static final int f5 = 4;
    private static final int f6 = 5;
    private static final int f7 = 6;
    private static final int f8 = 7;
    private static final int f9 = 8;
    private static final int f10 = 9;
    private static final int f11 = 10;
    private static final int f12 = 11;
    private static final int f13 = 12;

    // CONSTRUCTEUR
    public Trace(Vector trace) {
	// Sauvegarde le trace
	this.trace = new Vector(trace);

	// Initialise le tableau
	caracteristiques = new double[nombreCarac];

	// Calcule les caracteristiques
	calculerCarac();
    }

    // ACCESSEURS
    
    // Renvoi une point indexe par sa position sur le trace
    private Point getPoint(int position) {
	if (position < trace.size()) {
	    return ((Point)trace.elementAt(position));
	} else {
	    return null;
	}
    }

    // Renvoie la carré d'un nombre
    private double carre(double nombre) {
	return Math.pow(nombre, 2);
    }

    private long carre(long nombre) {
	return nombre * nombre;
    }

    // METHODES

    // PRIVATE

    //-----------
    // F1 et F2: le cosinus/sinus de l'angle entre le premier et troisieme
    // point du trace (on considere qu'il doit y avoir au moins
    // 3 points)
    private void calcF1F2() {

	Point p0 = getPoint(0), p2 = getPoint(2);
	double temp = Math.sqrt( carre(p2.getX() - p0.getX()) 
			  + carre(p2.getY() - p0.getY())) ;
	caracteristiques[f1] = (p2.getX()- p0.getX()) / temp;	   
	caracteristiques[f2] = (p2.getY()- p0.getY()) / temp;

    }

    //-----------
    // F3 et F4: determiner la plus petite boite englobant le trace
    // f3 est la distance de la diagonale et f4 l'angle formé
    // par cette diagonale avec l'horizontale
    private void calcF3F4() {

	// DETECTION DE LA BOITE ENGLOBANTE
	double 
	    maxX = java.lang.Double.NEGATIVE_INFINITY, 
	    minX = java.lang.Double.POSITIVE_INFINITY, 
	    maxY = java.lang.Double.NEGATIVE_INFINITY, 
	    minY = java.lang.Double.POSITIVE_INFINITY;

	// Cherche le max et min des X dans le trace
	// ainsi que le max et min des Y 
	for (int k=0; k < trace.size(); k++) {
	    Point pk = getPoint(k);
	    if (pk.getX() > maxX)
		maxX = pk.getX();
	    if (pk.getX() < minX)
		minX = pk.getX();
	    if (pk.getY() > maxY)
		maxY = pk.getY();
	    if (pk.getY() < minY)
		minY = pk.getY();
	}

	/*
	System.out.println("minX " + minX
			   + "\nmaxX " + maxX
			   + "\nminY " + minY
			   + "\nmaxY " + maxY);
	*/

	// La distance entre Point(xmin, ymin) et Point(xmax,ymax)
	caracteristiques[f3] = Math.sqrt( carre(maxX - minX) + carre (maxY - minY) );

	// L'angle formé par la diagonale avec l'horizontale
	caracteristiques[f4] = Math.atan ( (maxY - minY) / (maxX - minX) );
    }

    //-----------
    // F5: la distance entre le premier et le derniere point du tracé
    private void calcF5() {
	Point pFirst = (Point)trace.elementAt(0),
	    pLast = (Point)trace.elementAt(trace.size()-1);

	caracteristiques[f5] = 
	    Math.sqrt( carre(pLast.getX() - pFirst.getX() ) 
		       + carre(pLast.getY() - pFirst.getY() ) );

    }

    //-----------
    // F6 et F7: la cosinus et le sinus de l'angle entre le premier et le
    // dernier point (attention, utilise F5)
    private void calcF6F7 () {
	Point pFirst = (Point)trace.elementAt(0),
	    pLast = (Point)trace.elementAt(trace.size()-1);

	// Teste si le début et la fin du tracé de sont pas confondus
	if (caracteristiques[f5] == 0) {
	    // On dit que beta = 0 (arbitraire), 
	    // donc cos(beta) = 1 et sin(beta) = 0
	    caracteristiques[f6] = 1;
	    caracteristiques[f7] = 0;
	} else {
	    caracteristiques[f6] = 
		(pLast.getX() - pFirst.getX()) / caracteristiques[f5];
	    caracteristiques[f7] = 
		(pLast.getY() - pFirst.getY()) / caracteristiques[f5];
	}
    }

    //-----------
    // F8 : la longueur totale du tracé
    // F9 : l'angle total formé par le tracé
    // F10 : la somme des valeur absolue de chaque angle du tracé
    // F11: la somme des carres des memes angles
    // F12: la vitesse maximale atteinte lors du tracé
    private void calcF8_F12() {
	// f8
	double longueurTotale = 0;
	// f9, f10, f11
	double angleTotal = 0, angleAbsTotal = 0, angleCarreTotal = 0;

	Point p0 = (Point)trace.elementAt(0);
	Point p1 = (Point)trace.elementAt(1);
	double deltaX0 = p1.getX() - p0.getX();
	double deltaY0 = p1.getY() - p0.getY();
	long deltaT0 = p1.getDate() - p0.getDate();
	longueurTotale += Math.sqrt( carre(deltaX0) + carre(deltaY0) );
	
	// F12
	double vitesseMax =  (carre(deltaX0) + carre(deltaY0)) / carre(deltaT0);
   
	for (int k=1; k<= trace.size()-2; k++) {
	    Point pk = (Point)trace.elementAt(k);
	    Point pkp1 = (Point)trace.elementAt(k+1);
	    Point pkm1 = (Point)trace.elementAt(k-1);
	    double deltaXp = pkp1.getX() - pk.getX();
	    double deltaYp = pkp1.getY() - pk.getY();
	    double deltaXpm1 = pk.getX() - pkm1.getX();
	    double deltaYpm1 = pk.getY() - pkm1.getY();
	    
	    if (((deltaXp == 0) && (deltaYp ==0))
		|| ((deltaXpm1 == 0) && (deltaYpm1 ==0))) continue;

	    double tetaP = Math.atan( (deltaXp*deltaYpm1 - deltaXpm1*deltaYp)
				     /(deltaXp*deltaXpm1 + deltaYp*deltaYpm1));
	    long deltaTp = pkp1.getDate() - pk.getDate();
	    //System.out.println(tetaP + " " 
	    //		       +(deltaXp) + (deltaXpm1) +( deltaYp)+(deltaYpm1));
	    longueurTotale += Math.sqrt( carre(deltaXp) + carre(deltaYp) );

	    angleTotal += tetaP;
	    angleAbsTotal += Math.abs(tetaP);
	    angleCarreTotal += carre(tetaP);
	    
	    double vitesseMaxTemp = (carre(deltaXp) + carre(deltaYp)) / carre(deltaTp);
	    
	    if (vitesseMaxTemp > vitesseMax)
		vitesseMax = vitesseMaxTemp;

	}

	// Renvoie les résultats
	caracteristiques[f8] = longueurTotale;
	caracteristiques[f9] = angleTotal;
	caracteristiques[f10] = angleAbsTotal;
	caracteristiques[f11] = angleCarreTotal;
	caracteristiques[f12] = vitesseMax;

    }

    //-----------
    // F13: la duree du trace
    private void calcF13() {

	Point pFirst = (Point)trace.elementAt(0),
	    pLast = (Point)trace.elementAt(trace.size()-1);

	caracteristiques[f13] = pLast.getDate() - pFirst.getDate();
    }

    // Calcule le vecteur des caracteristique du trace
    private boolean calculerCarac() {
	
	// Au moins 3 points dans la tracé
	if (trace.size() <= 2) return false;
	
	calcF1F2();
	calcF3F4();
	calcF5();
	calcF6F7();
	calcF8_F12();
	calcF13();

	// On a extrait tout ce qu'il fallait du trace !!
	return true;
    }


    // PUBLIC

    // Renvoie le trace initial sous forme de Vector de Point
    public Vector getTrace() {
	return trace;
    }

    // Renvoie l'ensemble des caracteristiques
    public double[] getCaracteristiques() {
	return caracteristiques;
    }

    // toString
    public String toString() {
	String resultat = "";
	
	// Concatene toutes les caracteristiques
	for (int k=0; k<nombreCarac; k++) {
	    resultat+= "f" + (k+1) + ": " + caracteristiques[k] +"\n";
	}


	return resultat;
    }
}
