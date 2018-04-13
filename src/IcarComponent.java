package icar;

import java.util.Vector;
import java.util.Iterator;
import java.util.Set;
import java.awt.Panel;
import java.awt.Component;
import java.awt.Insets;
import java.awt.Graphics;
import java.awt.Canvas;
import java.awt.AWTEvent;
import java.awt.BorderLayout;
import icar.util.TimePrecision;
import icar.event.*;

/**
 * Cette classe est un composant AWT à implanter tel quel dans une application
 * AWT et permet la reconnaissance graphique de geste basée sur des
 * dictionnaires.
 * <br>Ce composant possède un dictionnaire associé que l'on peut
 * modifier à volonté.
 * <br>La classe permet le chargement/sauvegarde de dictionnaire, mais
 * aussi la création de dictionnaire en mode apprentissage. 
 * <br>Cette classe sert de controleur entre ZoneDessin et Classifieur,
 * c'est à lire entre le zone de trace et le systeme de classification.
 *
 * @author SALUT Jerome
 */
public class IcarComponent 
    extends Panel
    implements NewGesteListener,      
	       IcarRapportAnalyseSender,
	       IcarRapportTraceSender,
	       IcarRapportClasseSender
{

    // ATTRIBUTS

    // Le systeme de classification
    private Classifieur classifieur;
    
    // La zone de trace
    private ZoneDessin zoneDessin;


    // La liste des listeners de IcarRapportAnalyse
    private Vector listeIcarRapportAnalyseListeners;
    // La liste des listeners de IcarRapportTrace
    private Vector listeIcarRapportTraceListeners;
    // La liste des listeners de IcarRapportClasse
    private Vector listeIcarRapportClasseListeners;

    // Indique l'état du classifieur: apprentissage ou non
    private boolean isTraining;

    // Indique le motif actuel
    private String motif;


    // CONSTRUCTEUR
    /**
     * Cree un nouveau composant à partir d'un fichier dictionnaire
     *
     * @param nomFichierDictionnaire le chemin vers le fichier dictionnaire
     * @param tailleX la taille du composant en X
     * @param tailleY la taille du composant en Y
     */
    public IcarComponent(String nomFichierDictionnaire, int tailleX, int tailleY) {
	super();

	setSize(tailleX, tailleY);

	// Aucun listeners
	listeIcarRapportAnalyseListeners = new Vector();
	listeIcarRapportTraceListeners = new Vector();
	listeIcarRapportClasseListeners = new Vector();

	// Valeurs par defaut
	isTraining = false;
	motif = null;

	// Cree un systeme de classification
	if (nomFichierDictionnaire.equals("")) {
	    //classifieur = Classifieur.chargerDepuisFichier("dictionnaire.dat");
	    classifieur = new Classifieur();
	} else {

	    // Chargement du dictionnaire
	    // Si le fichier n'existe pas ou est endommagé
	    // on fait une erreur	    
	    if (!loadFromFile(nomFichierDictionnaire)) {

		classifieur = new Classifieur();
	    }
	}


	// La zone de dessin (ajoute au composent IcarComponent, soit this)
	zoneDessin = new ZoneDessin(tailleX, tailleY);
	zoneDessin.addNewGesteListener(this);
	super.add(zoneDessin);

	// Fixe l'echantillonage apprioprie
	zoneDessin.setEchantillonage(1);


	// Ajoute les listeners

	//interfaceProgramme.addNewTraceListener(this);
	//interfaceProgramme.addDictionnaryActionListener(this);
	//interfaceAdmin.addNewClasseSelectedListener(this);

	// Informe l'interface des classes existantes
	//interfaceAdmin.majListeClasse( classifieur.getListeClasse() );
	alertIcarRapportClasseListeners();
	//alertIcarRapportTraceListeners(null);

	
	
    }

    // Surcharge pour éviter l'ajout de composant
    public Component add(Component comp) { return null; }
    public Component add(Component comp, int index){return null;}
    public void add(Component comp, Object constraints) {}
    public void add(Component comp, Object constraints, int index) {}
    public Component add(String name, Component comp) {return null;}

    // METHODES PUBLIQUES

    /**
     * Efface le contenu du composant
     *
     */
    public void clear() {
	zoneDessin.vider();
    }

    /**
     * Cree un nouveau dictionnaire associé au composant
     *
     */
    public void newDictionnary() {
	// Abandon du dictionnaire courant et creation d'un nouveau
	classifieur = new Classifieur();

	// Signale le changement des classe et des traces
	alertIcarRapportClasseListeners();

	alertIcarRapportTraceListeners(null);


    }

    /**
     * Charge un dictionnaire depuis une fichier
     *
     * @param nomFichier le chemin vers le fichier dictionnaire
     * @return FAUX si le chargement a été abandonné
     * <br> VRAI si le chargement a été un succes
     */
    public boolean loadFromFile(String nomFichier) {
	// Chargement du dictionnaire
	Classifieur temp = Classifieur.chargerDepuisFichier(nomFichier);
	      
	// Test de l'echantillonage
	if (temp != null) {
	    // Si l'echantillonage du dictionnaire est inférieur (a peu pres)
	    // a la precision du systeme, on refuse le chargement
	    if (temp.getEchantillonage()+2  < TimePrecision.getMinPrecision()) {
		alertIcarRapportAnalyseListeners("",
						 "Echantillonage du dictionnaire non supporte par votre systeme"
						 + "("
						 + temp.getEchantillonage()
						 + "ms)");
		temp = null;
	    } else {
		alertIcarRapportAnalyseListeners("",
						 "Dictionnaire echantillone a " 
						 + temp.getEchantillonage() 
						 + "ms");
		
	    }
	    
	}
	      
	// Si le chargement a reussi
	if (temp != null) {
	    classifieur = temp;
	    
	    // Met a jour l'interface
	    //interfaceAdmin.majListeClasse(classifieur.getListeClasse());
	    //interfaceAdmin.majListeTrace(null);
	    alertIcarRapportClasseListeners();
	    alertIcarRapportTraceListeners(null);
	    
	    // Fixe l'echantillonage
	    setEchantillonage(temp.getEchantillonage());
	    
	    // Indique que le chargement s'est bien passé
	    return true;

	} else {
	    // Indique l'échec du chargement

	    return false;
	}

    }

    /**
     * Sauvegarde le dictionnaire courant dans un fichier
     *
     * @param nomfichier le chemin vers le fichier dans lequel sauvegarder
     * le dictionnaire
     */
    public void saveToFile(String nomfichier) {
	classifieur.sauvegarderVersFichier(nomfichier);

    }

    /**
     * Efface une classe du dictionnaire courant
     *
     * @param nomClasse l'identifiant de la classe
     */
    public void deleteClass(String nomClasse) {

	// Efface la classe
	classifieur.effacerClasse(nomClasse);

	// Met a jour l'interface
	//interfaceAdmin.majListeClasse(classifieur.getListeClasse());
	//interfaceAdmin.majListeTrace(null);
	alertIcarRapportClasseListeners();
	alertIcarRapportTraceListeners(null);
	  
    }

    /**
     * Efface un exemple d'une classe
     *
     * @param nomClasse l'identifiant de la classe concernée
     * @param trace l'exemple à supprimer
     */
    public void deleteClassExemple(String nomClasse, Vector trace) {

	// Efface l'exemple
	classifieur.effacerTrace(nomClasse, trace);

	// Met a jour l'interface
	alertIcarRapportClasseListeners();
	alertIcarRapportTraceListeners(nomClasse);
    }
    
    /**
     * Affiche un trace dans le composant.
     * <br> Ce tracé est en général obtenu par un événement de rapport
     * provenant du sytème de classification
     *
     * @param trace a value of type 'Vector'
     */
    public void afficher(Vector trace) {
	zoneDessin.afficher(trace);
    }

    // ACCESSEURS PUBLICS

    /**
     * Fixe le détails d'affichage des tracés.
     * <br> A chaque appel, le composant commute entre un affichage
     * détaille ou non.
     *
     */
    public void switchShowDetailsState() {
	zoneDessin.afficherDetails();
    }

    /**
     * Fixe le mode d'apprentissage ou de reconnaissance
     *
     * @param isTraining <br>TRUE indique le mode d'apprentissage, c'est à dire
     * que tout nouveau tracé sera enregistré dans le dictionnaire courante
     * dans la classe fixée par la fonction <b>setMotif(..)</b>
     * <br> FALSE indique le mode de reconnaissance (par defaut)
     */
    public void setTrainingMode(boolean isTraining) {
	if (isTraining) {
	    this.isTraining = true;
	} else {
	    this.isTraining = false;
	}
    }

    /**
     * Renvoie la valeur d'échantillonage courant
     * <br> L'échantillonage est la distance temporelle minimum entre 2 points
     * du tracé sur la zone de tracé.
     *
     * @return a value of type 'int'
     */
    public int getEchantillonage() {
	return zoneDessin.getEchantillonage();
    }

    /**
     * Fixe l'echantillonage courant.
     * <br> L'échantillonage est la distance temporelle minimum entre 2 points
     * du tracé sur la zone de tracé.
     *
     * @param echantillonage a value of type 'int'
     */
    public void setEchantillonage(int echantillonage) {
	zoneDessin.setEchantillonage(echantillonage);
    }

    /**
     * Renvoie l'identifiant de la classe courante (en cours d'apprentissage
     * ou reconnue)
     *
     * @return l'identifiant de la classe
     */
    public String getMotif() {
	return motif;
    }

    /**
     * Fixe l'identifiant de la classe courante (en vue d'un apprentissage)
     *
     * @param motif l'identifiant de la classe
     */
    public void setMotif(String motif) {
	this.motif = motif;
    }

    // 
    /**
     * Renvoie la liste des classe contenues par le dictionnaire courant
     *
     * @return a value of type 'Set'
     */
    public Set getListeClasse() {
	return classifieur.getListeClasse();
    }

    // 
    /**
     * Renvoie la liste des exemples d'une classe spécifique
     *
     * @param motif la classe concernée
     * @return la liste des exemples de la classe (un vecteur de tracé)
     */
    public Vector getListeTrace(String motif) {
	return classifieur.getListeTrace(motif);
    }

    /**
     * Renvoie la taille des bordures (utilisé par AWT)
     *
     * @return a value of type 'Insets'
     */
    public Insets getInsets() {
	return new Insets(-5, -5, -5, -5);
    }

    // EVENEMENTS

    // --NewGesteEvent : la zone de dessin envoie un nouveau tracé

    /**
     * Cette methode est appelé lorsqu'un évenement NewTraceEvent se produit.
     * Utilisé en interne, ne pas appeler directement.
     *
     * @param event a value of type 'NewGesteEvent'
     */
    public void processNewGesteEvent(NewGesteEvent event) {
	
	// Entrainement ?
	if (isTraining && motif != "") {

	    // Apprend le nouveau tracé d'exemple
	    classifieur.ajouterTrace(motif, 
				     event.getTrace(), 
				     event.getEchantillonage());


	    alertIcarRapportAnalyseListeners(motif,
				    "Apprentissage de la classe " 
				    + motif);

	    // Met à jour la liste des classe au niveau de l'interface
	    //interfaceAdmin.majListeClasse( classifieur.getListeClasse() );
	    alertIcarRapportClasseListeners();
	    alertIcarRapportTraceListeners(motif);

	} 
	// Sinon classification du trace
	else {
	    // Identifie la trace courant
	    TraceRapportAnalyse rapport = 
		classifieur.identifier(event.getTrace());
	    String motif = rapport.getMotif();

	    // Si aucun resultat n'a ete trouve
	    /*
	    if (motif == null) {
		motif = "aucune correspondance";
	    }
	    */
	    // Log
	    alertIcarRapportAnalyseListeners(motif,
					"Reconnaissance de: " + motif 
					+"\n" + rapport.getRejet()
					+"\n" +(rapport.getTrace().toString())
					+ rapport.getScores()
					);
	    
	}

	
    }


    // --IcarRapportAnalyseEvent
    
    // Ajouter un listener
    public void addIcarRapportAnalyseListener(IcarRapportAnalyseListener listener) {
	listeIcarRapportAnalyseListeners.add(listener);
    }

    // Supprime un listener
    public void eraseIcarRapportAnalyseListener(IcarRapportAnalyseListener listener) {
	listeIcarRapportAnalyseListeners.remove(listener);
    }

    // Previent les listeners de l'évenement
    public void alertIcarRapportAnalyseListeners(String motif, String message) {
	// Un iterateur pour parcourir la liste des listeners
	Iterator iter = listeIcarRapportAnalyseListeners.iterator();

	// L'evenement a transmettre
	IcarRapportAnalyseEvent event = 
	    new IcarRapportAnalyseEvent(this, motif,  message);

	// Parcour la liste des listeners et leur envoi l'evenement event
	while(iter.hasNext()) {
	    IcarRapportAnalyseListener listener = 
		(IcarRapportAnalyseListener)iter.next();
	    listener.processIcarRapportAnalyseEvent(event);
	}
    } 


    // --IcarRapportClasseEvent
    
    // Ajouter un listener
    public void addIcarRapportClasseListener(IcarRapportClasseListener listener)
    {
	listeIcarRapportClasseListeners.add(listener);
    }

    // Supprime un listener
    public void eraseIcarRapportClasseListener(IcarRapportClasseListener listener) {
	listeIcarRapportClasseListeners.remove(listener);
    }

    // Previent les listeners de l'évenement
    public void alertIcarRapportClasseListeners() {
	// Un iterateur pour parcourir la liste des listeners
	Iterator iter = listeIcarRapportClasseListeners.iterator();

	// L'evenement a transmettre
	IcarRapportClasseEvent event = 
	    new IcarRapportClasseEvent(this, classifieur.getListeClasse());

	// Parcour la liste des listeners et leur envoi l'evenement event
	while(iter.hasNext()) {
	    IcarRapportClasseListener listener = 
		(IcarRapportClasseListener)iter.next();
	    listener.processIcarRapportClasseEvent(event);
	}
    } 


    // --IcarRapportTraceEvent
    
    // Ajouter un listener
    public void addIcarRapportTraceListener(IcarRapportTraceListener listener)
    {
	listeIcarRapportTraceListeners.add(listener);
    }

    // Supprime un listener
    public void eraseIcarRapportTraceListener(IcarRapportTraceListener listener) {
	listeIcarRapportTraceListeners.remove(listener);
    }

    // Previent les listeners de l'évenement
    public void alertIcarRapportTraceListeners(String motif) {
	// Un iterateur pour parcourir la liste des listeners
	Iterator iter = listeIcarRapportTraceListeners.iterator();

	// L'evenement a transmettre
	IcarRapportTraceEvent event = 
	    new IcarRapportTraceEvent(this, motif, 
				      classifieur.getListeTrace(motif));

	// Parcour la liste des listeners et leur envoi l'evenement event
	while(iter.hasNext()) {
	    IcarRapportTraceListener listener = 
		(IcarRapportTraceListener)iter.next();
	    listener.processIcarRapportTraceEvent(event);
	}
    } 



}
