/**
 * Interface permettant de s'assurer que des classes peuvent valider le parcours d'une case.
 */
public interface ValideCase {
    
    /**
     * Prédicat permettant de valider le parcours d'une case.
     * @param c Case à tester
     * @return Entier négatif ou nul si le parcours est impossible, positif et d'autant plus grand que le parcours est meilleur
     */
    int estValide(Case c);
    
}
