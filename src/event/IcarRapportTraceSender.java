package icar.event;

/**
 * Cette interface doit être implementé pour pouvoir envoyer des événements 
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
     * Previent les listeners de l'évenement
     *
     * @param motif la classe concernée par le changement de la liste d'exemple
     */
    public void alertIcarRapportTraceListeners(String motif);

}
