
public interface ValideCase {
    
    /** prédicat permettant de valider le parcourt d'une case
     *
     * @param case_ Case à tester
     * @return true si case_ peut être parcouru
     */
    boolean estValide(Case case_);
    
}
