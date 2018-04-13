package icar.event;

/**
 * Cette interface doit �tre implement� pour recevoir des �v�nements de
 * type IcarRapportAnalyseEvent
 *
 * @author SALUT Jerome
 */
public interface IcarRapportAnalyseListener {

    /**
     * Cette methode est appel� lorsqu'un �venement se produit
     *
     * @param event a value of type 'IcarRapportAnalyseEvent'
     */
    public void processIcarRapportAnalyseEvent(IcarRapportAnalyseEvent event);

}
