
/**
 * Modification pour ajout -b
 * @author Adrien Dax
 * @author Philippe Truillet
 * @date 05.10.2016
 */
public class IcarClientIvy {

    // La methode main
    public static void main(String[] args) {
	String adresse = "127.255.255.255:2010";
        String dicopath = "dictionnaire.dat";
		
        for(int i = 0; i < args.length; ++i)
            if(args[i].equalsIgnoreCase("-b") && (args.length > i + 1))
                adresse = args[i+1];
        for(int i = 0; i < args.length; ++i)
            if(args[i].endsWith(".dat"))
                dicopath = args[i];
        new InterfaceClientIvy(dicopath, adresse);
    }
}