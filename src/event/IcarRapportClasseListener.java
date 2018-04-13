package icar.event;

/**
 * Cette interface doit �tre implement� pour recevoir des �v�nements de
 * type IcarRapportClasseEvent
 *
 * @author SALUT Jerome
 */
public interface IcarRapportClasseListener {

    /**
     * Cette methode est appel� lorsqu'un �venement se produit
     *
     * @param event l'�venement transmis
     */
    public void processIcarRapportClasseEvent(IcarRapportClasseEvent event);

}
