/**
 * Exception levée lors d'un mauvais formatage des fichiers de cartes.
 * @author Enseignants Ensimag
 * @date -
 */
public class ExceptionFormatDonnees extends Exception {
	private static final long serialVersionUID = 1L;

	public ExceptionFormatDonnees(String msg) {
		super(msg);
	}
}
