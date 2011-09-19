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


public class Tree extends GenericTree<Node> implements java.io.Serializable {

	private Node root;

	public String print() {
		/*
		 * We're going to assume a pre-order traversal by default
		 */

		String stringRepresentation = "";

		// if(root != null) {
		// stringRepresentation =
		// build(GenericTreeTraversalOrderEnum.PRE_ORDER).toString();
		//
		// }

		return stringRepresentation;
	}
	
	/*
	 * Salva l'albero su disco
	 */
	public void save(String filename) throws IOException {
		// Write to disk with FileOutputStream
		FileOutputStream f_out = new FileOutputStream(filename);

		// Write object with ObjectOutputStream
		ObjectOutputStream obj_out = new ObjectOutputStream(f_out);

		// Write object out to disk
		obj_out.writeObject(this);
	
	}

	/*
	 * Cancella i record presenti nell'albero
	 */
	public void clean() {

		// for all nodes
		for (GenericTreeNode<Node> node : this.build(GenericTreeTraversalOrderEnum.PRE_ORDER)) {
			((Node) node).setRecords(null);
		}

	}
	
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

		// OutputStream stream = null;
		// BufferedWriter outputStream = null;
		//
		// try {
		// outputStream = new BufferedWriter(outputStream);
		// outputStream.newLine();
		// outputStream.write("hi");
		//
		//
		// } catch (Exception e) {
		// // TODO: handle exception
		// } finally {
		// // Close the BufferedWriter
		// try {
		// if (outputStream != null) {
		// outputStream.flush();
		// outputStream.close();
		// }
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }
		//
		// return outputStream;

	}

	public double validate(RecordSet recordSet) {
		
		int positive = 0;
		
		for (TicTacToeRecord record : recordSet.getRecords()) {
			
//			System.out.println(this.classify(record)+" "+record.getLabel().toString());
			
			if (this.classify(record).equals(record.getLabel().toString())) {
				positive++;
			}
			
		}

		// return error ratio
		return 1.0 - (double) positive / (double) recordSet.getRecords().size();
	}
	
	
	public String classify(TicTacToeRecord record) {
		
		Node root = (Node) this.getRoot();
		return classify(root, record);
	}
	
	
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
				
			}
			
		} else {label = node.getLabel();}
		
		return label;
	}

}
