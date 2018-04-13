package icar;

/**
 * Cette classe doit etre implémentée pour recevoir des évenements
 * de type NewGesteEvent.
 *
 * @author SALUT Jerome
 */
interface NewGesteListener {

    // Cette methode est appelé lorsqu'un évenement de produit
    public void processNewGesteEvent(NewGesteEvent event);

}
