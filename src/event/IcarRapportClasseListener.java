package icar.event;

/**
 * Cette interface doit être implementé pour recevoir des événements de
 * type IcarRapportClasseEvent
 *
 * @author SALUT Jerome
 */
public interface IcarRapportClasseListener {

    /**
     * Cette methode est appelé lorsqu'un évenement se produit
     *
     * @param event l'évenement transmis
     */
    public void processIcarRapportClasseEvent(IcarRapportClasseEvent event);

}
