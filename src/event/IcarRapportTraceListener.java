package icar.event;

/**
 * Cette interface doit �tre implement� pour recevoir des �v�nements de
 * type IcarRapportTraceEvent
 *
 * @author SALUT Jerome
 */
public interface IcarRapportTraceListener {
 
    /**
     * Cette methode est appel� lorsqu'un �venement se produit
     *
     * @param event l'�venement transmis
     */
    public void processIcarRapportTraceEvent(IcarRapportTraceEvent event);

}
