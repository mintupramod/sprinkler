package classification;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.List;

import vivin.GenericTree;
import vivin.GenericTreeNode;
import vivin.GenericTreeTraversalOrderEnum;

public class Tree extends GenericTree<Node> {

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

}
