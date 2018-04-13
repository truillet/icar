package icar;

/**
 * Cette classe doit etre impl�ment�e pour recevoir des �venements
 * de type NewGesteEvent.
 *
 * @author SALUT Jerome
 */
interface NewGesteListener {

    // Cette methode est appel� lorsqu'un �venement de produit
    public void processNewGesteEvent(NewGesteEvent event);

}
