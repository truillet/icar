package icar.event;

/**
 * Cette interface doit être implementé pour recevoir des événements de
 * type IcarRapportTraceEvent
 *
 * @author SALUT Jerome
 */
public interface IcarRapportTraceListener {
 
    /**
     * Cette methode est appelé lorsqu'un évenement se produit
     *
     * @param event l'évenement transmis
     */
    public void processIcarRapportTraceEvent(IcarRapportTraceEvent event);

}
