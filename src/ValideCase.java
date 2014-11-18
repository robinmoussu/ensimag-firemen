/**
 * Interface permettant de s'assurer que des classes peuvent valider le parcours d'une case.
 * @author Robin Moussu
 * @date 2014-11-17
 */
public interface ValideCase {
    
    /**
     * Prédicat permettant de valider le parcours d'une case.
     * @param c Case à tester
     * @return Booléen indiquant si la case peut être parcourue
     */
    boolean estValide(Case c);
    
}
