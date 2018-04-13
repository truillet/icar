import java.util.Calendar;
import icar.event.*;
import fr.dgac.ivy.*;

public class InterfaceClientIvy extends Interface {

    // ATTRIBUTS
    private static final String nomIvy  = "ICAR";
    private String adresse  = "127.255.255.255:2010";
    private Ivy busIvy;

    // CONSTRUCTEUR
    public InterfaceClientIvy(String nomFichier, String adresse) {

	super(nomFichier);
	this.adresse = adresse;
	
	// Creation du bus ivy
	busIvy = new Ivy(nomIvy, "ICAR Agent is alive", null);
	try {
	    busIvy.start(adresse);
	} catch(IvyException e) {
	    System.out.println("BusIvy error: " + e);
	}

	// Retaille la fenetre au mieux
	pack();

	// Centre la frame sur l'ecran
	this.centrer();
      
	// Affiche la fenetre
	show();
    }

    // METHODES PUBLIQUES
    // Ajouter un message dans la fenetre de log
    public void ecrireFenetreLog(String texte) {

    }

    // EVENEMENTS

    // --IcarRapportAnalyse

    // Cette methode est appel�e lorsqu'un �venement se produit 
    // (nouveau trac� analys�)
    public void processIcarRapportAnalyseEvent(IcarRapportAnalyseEvent event) {
	// On effectue le traitement de la super classe
	super.processIcarRapportAnalyseEvent(event);


	// Transmission de la classe
	if (event.getMotif() != null & busIvy !=null) {
	    try {
		busIvy.sendMsg(nomIvy + " Gesture=" + event.getMotif());
	    } catch (IvyException ie) {
		System.out.println("BusIvy: can't send my message !");
	    }	
	    
	}
    }

}
