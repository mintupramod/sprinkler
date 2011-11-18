package sprinkler.utilities;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * @author Claudio Tanci
 *
 */
public class Bundle {
	private static final String BUNDLE_NAME = "hunt.bundle"; //$NON-NLS-1$

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle(BUNDLE_NAME);

	private Bundle() {
	}

	/**
	 * @param key
	 * @return
	 */
	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
