package icar.event;

/**
 * Cette interface doit �tre implement� pour pouvoir envoyer des �v�nements 
 * de type IcarRapportTraceEvent
 *
 * @author SALUT Jerome
 */
public interface IcarRapportTraceSender {

    /**
     * Ajouter un listener
     *
     * @param listener a value of type 'IcarRapportTraceListener'
     */
    public void addIcarRapportTraceListener(IcarRapportTraceListener listener);
 
    /**
     * Supprime un listener
     *
     * @param listener a value of type 'IcarRapportTraceListener'
     */
    public void eraseIcarRapportTraceListener(IcarRapportTraceListener listener);

    /**
     * Previent les listeners de l'�venement
     *
     * @param motif la classe concern�e par le changement de la liste d'exemple
     */
    public void alertIcarRapportTraceListeners(String motif);

}
