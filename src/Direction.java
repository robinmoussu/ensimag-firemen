/**
 * Type énuméré indiquant les directions de déplacement possibles.
 * Les déplacements en diagonale ont été ajoutés pour l'implémentation de l'Astar.
 * @author Thibaud Backenstrass
 * @date 2014-11-17
 */
public enum Direction {
    NORD,
    SUD,
    EST,
    OUEST,
    NORD_OUEST,
    NORD_EST,
    SUD_OUEST,
    SUD_EST,
    NONE,
}
