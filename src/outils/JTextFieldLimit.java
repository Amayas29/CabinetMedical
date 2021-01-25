package outils;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/***
 * Une limitation de caractere pour les champs der saisie
 * 
 */
public class JTextFieldLimit extends PlainDocument {

	private static final long serialVersionUID = 7668791411023891401L;

	private int limit;

	public JTextFieldLimit(int limit) {
		super();
		this.limit = limit;
	}

	public JTextFieldLimit(int limit, boolean upper) {
		super();
		this.limit = limit;
	}

	public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
		if (str == null) 
			return;

		if ((getLength() + str.length()) <= limit) {
			super.insertString(offset, str, attr);
		}
	}
}