/**
 * 
 */
package sprinkler;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.StringTokenizer;

import sprinkler.data.Label;
import sprinkler.data.Node;
import sprinkler.data.NominalAttribute;
import sprinkler.data.NominalRecord;
import sprinkler.data.RecordSet;
import sprinkler.data.Tree;
import sprinkler.utilities.Bundle;
import sprinkler.utilities.CSVLoader;

/**
 * @author Claudio Tanci
 * 
 */
public class Cli {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		if (args.length > 0) {

			String firstArg = args[0];
			
			if (firstArg.equals("generate")) {
				String training = args[1];
				float purity = Float.valueOf(args[2]);
				String output = args[3];
				String dotfile = args[4];
				
				generate(training, purity, output, dotfile);
				
			} else if (firstArg.equals("classify")) {
				String tree = args[1];
				String record = args[2];

				classify(tree, record);

			} else if (firstArg.equals("validate")) {
				String training = args[1];
				int samples = Integer.parseInt(args[2]);
				float pstart = Float.parseFloat(args[3]);
				float pstop = Float.parseFloat(args[4]);
				int steps = Integer.parseInt(args[5]);
				float confidence = Float.parseFloat(args[6]);

				validate(training, samples, pstart, pstop, steps, confidence);
				
				
			} else {
				error();
			}

		} else {
			error();
		}

	}

	private static void error() {
		System.out.println("A command line interface for classification trees generation, classify records and general validation.");
		System.out.println("\n");
		System.out.println("Examples:");
		System.out.println("To generate a classification tree");
		System.out.println("  generate training_set_file purity output_path_tree_file output_dot_file");
		System.out.println("To classify a record");
		System.out.println("  classify tree_file record");
		System.out.println("To perform full iterative validation for Accuracy Vs Purity in a data set using .632 validation");
		System.out.println("  validate training_set_file samples pstart pstop steps confidence");
		System.out.println("  where samples        number of samples to be taken for .632 bootstrap validation");
		System.out.println("        pstart, pstop  min, max purity values to test in [0-1] (gini index)");
		System.out.println("        steps          number of steps requested for purity");
		System.out.println("        confidence     confidence value requested (one of one of .99 .98 .95 .9 .8 .7 .5)");
	}
	
	private static void generate(String training, float purity, String output, String dotfile){
		
		Node root = new Node();

		System.out.println("Reading file data " + training);
		try {
			root.setRecords(CSVLoader.loadRecordSet(training));
		} catch (IOException e1) {
			System.out.println("error reading file...");
		}

		System.out.println("Generating decision tree...");
		Tree tree = new Tree();
		tree.setRoot(root);

		System.out.println(((Node) tree.getRoot()).size() + " records to be analyzed");
		
		tree = (Tree) GenericHunt.treeGrowth(tree, purity);

		System.out.println(tree.getNumberOfNodes() + " nodes in the tree");
		
		System.out.println("Cleaning data set records...");
		tree.clean();
		
		
		// saving the tree
		try {
			tree.save(output);
			System.out.println("Decision tree saved as "+output);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// exporting tree dot file
		System.out.println("Decision tree dot file saved as "+dotfile);
		tree.toDot(dotfile);	
	}
	
	private static void classify(String treeFile, String recordStr){
		
//		load the decision tree and classify the record
		System.out.println("loading decision tree in "+treeFile);
		
		FileInputStream f_in;
		try {
			f_in = new FileInputStream(treeFile);

			// Read object using ObjectInputStream
			ObjectInputStream obj_in = new ObjectInputStream(f_in);

			// Read the object
			Object obj = obj_in.readObject();

			if (obj instanceof Tree) {
				// Cast object to Tree
				Tree tree = (Tree) obj;
				
				// record String to Record
				// break comma separated line using ","
				StringTokenizer st = new StringTokenizer(recordStr, ",");
				ArrayList<NominalAttribute> attributes = new ArrayList<NominalAttribute>();
				// attributes
				while (st.hasMoreTokens()) {
					NominalAttribute attribute = new NominalAttribute(null, st.nextToken());
					attributes.add(attribute);
				}
				Label label = new Label(null, null);
				NominalRecord record = new NominalRecord(attributes, label);
				
				// print classification
				System.out.println("Label: "+tree.classify(record));
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private static void validate(String strFile, int samples, float pstart, float pstop, int steps, float confidence){
		Validate.validate(strFile, samples, pstart, pstop, steps, confidence);
	}

}
