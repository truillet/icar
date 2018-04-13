package icar;

/**
 * Cette classe doit etre implementee pour lancer des évenements
 * de type NewGesteEvent.
 *
 * @author SALUT Jerome
 */
interface NewGesteSender {

    // Ajouter un listener
    public void addNewGesteListener(NewGesteListener listener);

    // Supprime un listener
    public void eraseNewGesteListener(NewGesteListener listener);

    // Previent les listeners de l'évenement
    public void alertNewGesteListeners();

}
