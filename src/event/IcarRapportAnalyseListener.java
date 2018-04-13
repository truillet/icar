package icar.event;

/**
 * Cette interface doit être implementé pour recevoir des événements de
 * type IcarRapportAnalyseEvent
 *
 * @author SALUT Jerome
 */
public interface IcarRapportAnalyseListener {

    /**
     * Cette methode est appelé lorsqu'un évenement se produit
     *
     * @param event a value of type 'IcarRapportAnalyseEvent'
     */
    public void processIcarRapportAnalyseEvent(IcarRapportAnalyseEvent event);

}
