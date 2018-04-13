package icar.event;

/**
 * Cette interface doit être implementé pour pouvoir envoyer des événements 
 * de type IcarRapportAnalyseEvent
 *
 * @author SALUT Jerome
 */
public interface IcarRapportAnalyseSender {

    /**
     * Ajouter un listener
     *
     * @param listener a value of type 'IcarRapportAnalyseListener'
     */
    public void addIcarRapportAnalyseListener(IcarRapportAnalyseListener listener);

    /**
     * Supprime un listener
     *
     * @param listener a value of type 'IcarRapportAnalyseListener'
     */
    public void eraseIcarRapportAnalyseListener(IcarRapportAnalyseListener listener);

    /**
     * Previent les listeners de l'évenement
     *
     * @param motif l'identifiant de la classe reconnue
     * @param message le détail de la reconnaissance de la classe
     */
    public void alertIcarRapportAnalyseListeners(String motif, String message);

}
