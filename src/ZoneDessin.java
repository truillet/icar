package icar;

import java.awt.*;
import java.awt.event.*;
import java.util.Vector;
import java.util.Iterator;
import java.util.Date;
import icar.util.Point;

/**
 * Cette classe est un canvas AWT et permet le tracé de formes par drag&drop.
 * Un evenement est envoyé pour chaque nouveau tracé.
 *
 * @author SALUT Jerome
 */
class ZoneDessin extends Canvas implements  NewGesteSender {

    // ATTRIBUTS
	
    // La taille de la zone de dessin
    private int tailleX;
    private int tailleY;

    // Le tracé
    private Vector trace;

    // La valeur d'echantillonage du trace, c'est a dire le temp
    // entre chaque point enregistre
    private int echantillonage;

    // Indication de trace fini
    private boolean traceFini;

    // Indicateur temporel de début de tracé
    private long timeDebutTrace;

    // Indicateur temporel du dernier point tracé
    private long timeLastPoint;

    // Buffer d'image pour éviter le clignotement
    private Image offScreenImage;


    // Indique s'il faut tracer ou non les details du trace
    private boolean afficherDetailTrace;

    // La liste des listeners
    private Vector listeGesteListeners;

    
    // CONSTRUCTEUR
    public ZoneDessin(int tailleX, int tailleY) {

	// Appelle le constructeur de la super class
	super();

	// Cree un tracé vide
	trace = new Vector();

	// Aucun listeners
	listeGesteListeners = new Vector();

	// Fixe la taille de la zone
	this.tailleX = tailleX;
	this.tailleY = tailleY;

	// Valeur par defaut
	traceFini = true;
	afficherDetailTrace = true;
	echantillonage = 1;

	// Active la prise en charge des événements
	enableEvents( AWTEvent.MOUSE_MOTION_EVENT_MASK | 
		      AWTEvent.MOUSE_EVENT_MASK |
		      AWTEvent.MOUSE_WHEEL_EVENT_MASK);  
	//addMouseListener(this);
	}

    // Dessine la zone d'affichage
    public void paint(Graphics g) {

	// Fixe la couleur du fond
	Dimension d = this.getSize();
	g.setColor(Color.gray);
	g.fillRect(0, 0, d.width, d.height);

	// Redessine le tracé


	// Trace le premier point
	if ((trace.size()>0)) {
	    Point p = (Point)trace.elementAt(0);
	    g.setColor(Color.black);
	    g.fillOval(p.getX()-3, p.getY()-3, 6, 6);
	}
	// Parcour le tracé et le redessine
	for (int k =0; k<= trace.size()-2; k++) {
	    Point p_sav =(Point)trace.elementAt(k) ;
	    Point p = (Point)trace.elementAt(k+1);
	    g.setColor(Color.black);
	    g.drawLine(p_sav.getX(), p_sav.getY(), p.getX(), p.getY());
	    
	    // Trace un point rouge si besoin
	    if (afficherDetailTrace) {
		g.setColor(Color.red);
		g.drawOval(p.getX()-2, p.getY()-2, 4, 4);
	    }
	}

    }

    // Fixe la valeur de l'echantillonage du trace
    public void setEchantillonage(int valeur) {
	if (valeur >= 0)
	    echantillonage = valeur;

    }

    // Renvoie la valeur de l'echantillonage courant
    public int getEchantillonage() {
	return echantillonage;
    }

    // Affiche ou cache le detail des trace
    public void afficherDetails() {

	// Change l'etat
	afficherDetailTrace ^= true;
	    
	// Demande un rafraichissement
	repaint();

    }
	
    // Vide la zone d'affichage
    public void vider() {
	// Efface le vecteur du trace
	trace.clear();

	// Rafraichit l'affichage
	repaint();
    }
    
    // Affiche l'ensemble de Point passe en parametre
    public void afficher(Vector trace) {
	this.trace = new Vector(trace);
	repaint();
    }

    // Retrace l'objet
    public void update(java.awt.Graphics g) {
	//super.update(g);

	// Creer le backbuffer si inexistant
	if (offScreenImage == null) {
	    offScreenImage = 
		createImage(getSize().width, getSize().height);
	}

	// Recupere un contexte graphique sur le backbuffer
	Graphics offGr = offScreenImage.getGraphics();
	// Dessine l'objet dessus
	paint(offGr);
	g.drawImage(offScreenImage, 0, 0, this);

    }

    // Renvoie la taille de l'objet
    public Dimension getPreferredSize() {
	return new Dimension(tailleX, tailleY);  
    }

    // Gere les évenements de la souris
    public void processMouseMotionEvent(MouseEvent e) {


	super.processMouseEvent(e);

	
	// Si l'utilisateur deplace la souris en gardant le bouton appuyé
	if ( e.getID() == MouseEvent.MOUSE_DRAGGED) {

	    // Effacer le trace precedent si besoin et débute un nouveau tracé
	    if (traceFini) {
		// Efface la zone de tracé
		vider();
		// Indique la début du tracé
		traceFini = false;
		// Indique la date de début du tracé
		timeDebutTrace = System.currentTimeMillis();
		timeLastPoint = timeDebutTrace -1;

	    }

	    // Enregistre le point courant dans le trace
	    // et verifie que le point courant n'a pas la meme date que le dernier
	    // point inséré (ceci est du a un manque de précision de l'horloge
	    // sous certain Os, comme Windows)
	    // Ici on tient compte de la valeur de l'echantillonage donnee
	    if (System.currentTimeMillis() > timeLastPoint + (echantillonage-1)) {
		//System.out.println(new Point(e.getX(), e.getY(),0));
		// Rajoute le point dans le vecteur du trace
		trace.add(new Point(e.getX(),
				    e.getY(),
				    (System.currentTimeMillis() - timeDebutTrace )));
		// Enregistre la date d'enregistrement de ce point
		timeLastPoint = System.currentTimeMillis();
		
	    }
	    
	    repaint();

	} 
	// Arrete le trace
	else if (traceFini!=true) {
	    // Indique la fin du tracé
	    traceFini = true;

	    // Informe les listeners qu'un nouveau trace est disponible
	    alertNewGesteListeners();

	}
	

    }

    // EVENEMENTS


    // Ajouter un listener
    public void addNewGesteListener(NewGesteListener listener) {
	listeGesteListeners.add(listener);
    }

    // Supprime un listener
    public void eraseNewGesteListener(NewGesteListener listener) {
	listeGesteListeners.remove(listener);
    }


    // Previent les listeners de l'évenement
    public void alertNewGesteListeners() {
	// Un iterateur pour parcourir la liste des listeners
	Iterator iter = listeGesteListeners.iterator();

	// L'evenement a transmettre
	NewGesteEvent event = 
	    new NewGesteEvent(this, new Vector(trace), echantillonage);

	// Parcour la liste des listeners et leur envoi l'evenement event
	while(iter.hasNext()) {
	    NewGesteListener listener = (NewGesteListener )iter.next();
	    listener.processNewGesteEvent(event);
	}
    }
}
