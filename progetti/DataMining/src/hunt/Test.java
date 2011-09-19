package hunt;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.text.AttributedCharacterIterator.Attribute;
import java.util.ArrayList;
import java.util.Collection;

import hunt.data.Label;
import hunt.data.NominalAttribute;
import hunt.data.RecordSet;
import hunt.data.TicTacToeRecord;
import hunt.data.Tree;
import hunt.utilities.Bundle;

/*
 * A classification test
 */
public class Test {

	public static void main(String[] args) {

		// build the decision tree
		GenericHunt.main(args);
		
		// test error ratio
		// load the decision tree and classify the record
		System.out.println("loading decision tree in "+Bundle.getString("Resources.SaveFileName"));
		
		FileInputStream f_in;
		try {
			f_in = new FileInputStream(Bundle.getString("Resources.SaveFileName"));

			// Read object using ObjectInputStream
			ObjectInputStream obj_in = new ObjectInputStream(f_in);

			// Read the object
			Object obj = obj_in.readObject();

			if (obj instanceof Tree) {
				// Cast object to Tree
				Tree tree = (Tree) obj;
				
				// load validation set in a record set
				RecordSet recordSet = new RecordSet(Bundle.getString("Resources.ValidationSet"));
				
				// validate the decision tree with the validation record set
				System.out.println("error rate = "+tree.validate(recordSet));
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

//		
//		// create a record to test
//		ArrayList<NominalAttribute> attributes = new ArrayList<NominalAttribute>();
//
//		String r = "x,o,o,x,x,o,b,x,o";
//		for (String attr : r.split(",")) {
//			NominalAttribute e = new NominalAttribute();
//			e.setValue(attr);
//			attributes.add(e);
//		}
//
//		Label label = new Label("positive");
//
//		TicTacToeRecord record = new TicTacToeRecord(attributes, label );
//		
//		
//		System.out.println("examinating record "+r);
//
//		// load the decision tree and classify the record
//		System.out.println("loading decision tree in "+Bundle.getString("Resources.SaveFileName"));
//		
//		FileInputStream f_in;
//		try {
//			f_in = new FileInputStream(Bundle.getString("Resources.SaveFileName"));
//
//			// Read object using ObjectInputStream
//			ObjectInputStream obj_in = new ObjectInputStream(f_in);
//
//			// Read the object
//			Object obj = obj_in.readObject();
//
//			if (obj instanceof Tree) {
//				// Cast object to Tree
//				Tree tree = (Tree) obj;
//				
//				// classify the record
//				System.out.println("classification = "+tree.classify(record));
//			}
//
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}







	}

}
