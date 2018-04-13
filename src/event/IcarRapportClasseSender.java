package icar.event;

/**
 * Cette interface doit �tre implement� pour pouvoir envoyer des �v�nements 
 * de type IcarRapportClasseEvent
 *
 * @author SALUT Jerome
 */
public interface IcarRapportClasseSender {
 
    /**
     * Ajouter un listener
     *
     * @param listener a value of type 'IcarRapportClasseListener'
     */
    public void addIcarRapportClasseListener(IcarRapportClasseListener listener);

    /**
     * Supprime un listener
     *
     * @param listener a value of type 'IcarRapportClasseListener'
     */
    public void eraseIcarRapportClasseListener(IcarRapportClasseListener listener);
 
    /**
     * Previent les listeners de l'�venement
     *
     */
    public void alertIcarRapportClasseListeners();

}
