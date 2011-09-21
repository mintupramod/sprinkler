package hunt.data;

import hunt.utilities.Bundle;
import hunt.vivin.GenericTree;
import hunt.vivin.GenericTreeNode;
import hunt.vivin.GenericTreeTraversalOrderEnum;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.List;

/**
 * @author Claudio Tanci
 * This class implements a decision tree 
 *
 */
public class Tree extends GenericTree<Node> implements java.io.Serializable {

	// root node
	private Node root;

	/**
	 * @deprecated
	 * print the tree
	 * @return string
	 */
	public String print() {
		// We're going to assume a pre-order traversal by default

		String stringRepresentation = "";

		// if(root != null) {
		// stringRepresentation =
		// build(GenericTreeTraversalOrderEnum.PRE_ORDER).toString();
		//
		// }

		return stringRepresentation;
	}
	
	/**
	 * save tree to disk
	 * @param file name
	 */
	public void save(String filename) throws IOException {
		// Write to disk with FileOutputStream
		FileOutputStream f_out = new FileOutputStream(filename);

		// Write object with ObjectOutputStream
		ObjectOutputStream obj_out = new ObjectOutputStream(f_out);

		// Write object out to disk
		obj_out.writeObject(this);
	
	}

	/**
	 * clean tree nodes records
	 */
	public void clean() {

		// for all nodes
		for (GenericTreeNode<Node> node : this.build(GenericTreeTraversalOrderEnum.PRE_ORDER)) {
			((Node) node).setRecords(null);
		}

	}
	
	/**
	 * toDot export a representation of the tree as a dot file
	 * @param file name
	 */
	public void toDot(String filename) {

		BufferedWriter bufferedWriter = null;

		try {

			// Construct the BufferedWriter object
			bufferedWriter = new BufferedWriter(new FileWriter(filename));

			// header
			bufferedWriter.write("// Dot file");
			bufferedWriter.newLine();
			bufferedWriter.write(" digraph graphname {");
			bufferedWriter.newLine();
			
			// nodes and links  
			for (GenericTreeNode<Node> node : this.build(GenericTreeTraversalOrderEnum.PRE_ORDER)) {
				if (node.hasChildren()) {
					// children
					for (GenericTreeNode<Node> child : node.getChildren()) {
						bufferedWriter.write("    \""+((Node) node).getName()+"\" -> \""+((Node) child).getName()+"\" [label=\""+((Node) child).getTestAttribute()+"="+Arrays.toString(((Node) child).getTestCondition().getValues())+"\"];");
						bufferedWriter.newLine();
					}
				}
			}
			 
			// making leafs green 
			for (GenericTreeNode<Node> node : this.build(GenericTreeTraversalOrderEnum.PRE_ORDER)) {
				if (((Node) node).isLeaf()) {
					bufferedWriter.write("    "+((Node) node).getName()+" [color=green]");
					bufferedWriter.newLine();
					bufferedWriter.write("    "+((Node) node).getName()+" [label=\""+((Node) node).getName()+" "+((Node) node).getLabel()+"\"]");
					bufferedWriter.newLine();

				}
			}

			// footer
			bufferedWriter.newLine();
			bufferedWriter.write(" }");

		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			
			// Close the BufferedWriter
			try {
				if (bufferedWriter != null) {
					bufferedWriter.flush();
					bufferedWriter.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * validate the decision tree using a record set
	 * @param recordset
	 * @return error ratio
	 */
	public double validate(RecordSet recordSet) {
		
		int positive = 0;
		
		for (TicTacToeRecord record : recordSet.getRecords()) {
			
			if (this.classify(record).equals(record.getLabel().toString())) {
				positive++;
			}
			
		}

		// return error ratio
		return 1.0 - (double) positive / (double) recordSet.getRecords().size();
	}
	
	/**
	 * classify a record
	 * @param record
	 * @return label
	 */
	public String classify(TicTacToeRecord record) {
		
		Node root = (Node) this.getRoot();
		return classify(root, record);
	}
	
	/**
	 * classify a record
	 * @param node
	 * @param record
	 * @return label
	 */
	public String classify(Node node, TicTacToeRecord record) {
		
		String label = null;
		
		if (node.hasChildren()) {
			for (GenericTreeNode<Node> child : node.getChildren()) {
				int attr = ((Node) child).getTestCondition().getIdAttribute();
				String[] values = ((Node) child).getTestCondition().getValues();
				
				for (int i = 0; i < values.length; i++) {
					String value = values[i];
					
					if (record.getAttribute(attr).value.equals(value)) {
						label = classify((Node) child, record);
					} 
					
				}
				
			} if (label == null) {
				// with n-way decision tree we may not find an appropriate child node for the record,
				// in this case we classify the record with the label of the father node.
				label = node.getLabel();
			}
			
		} else {label = node.getLabel();}
		
		return label;
	}
	
}
