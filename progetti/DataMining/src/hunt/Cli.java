/**
 * 
 */
package hunt;

/**
 * @author Claudio Tanci
 *
 */
public class Cli {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int firstArg;
		if (args.length > 0) {
		    try {
		        firstArg = Integer.parseInt(args[0]);
		    } catch (NumberFormatException e) {
		        System.err.println("Argument must be an integer");
		        System.exit(1);
		    }
		} else {
			System.out.println("A command line interface for classification trees generation, classify records and general validation.");
			System.out.println("\n");
			System.out.println("Examples:");
			System.out.println("To generate a classification tree");
			System.out.println("  -");
		}

	}

}
