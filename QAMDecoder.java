/*
* Author: Alexander Khouri
*/

import java.util.*;
import java.io.*;

public class QAMDecoder {
	public static void main(String[] args)  {
		HashMap<String, String> QAMVals = new HashMap<String, String>();
		populateQAMVals(QAMVals);
		BufferedReader input;
		try {
			input = new BufferedReader(new FileReader("input_file.txt"));
			boolean evenLine = false;
			String line;    // EXPECTED FORMAT: `Nibble 0: (-0.78, 3.20)`
			String tuple = "";
			String binary = "";
			String prevBinary = "";
			while ((line = input.readLine()) != null) {
				tuple = line.substring(line.indexOf('(')); // e.g. (-0.78, 3.20)
				tuple = roundValues(tuple);
				binary = QAMVals.get(tuple);
				System.out.print(line + " decodes as " + binary);
				if (evenLine) System.out.printf(" - together, %s%s gives \"%c\"\n", prevBinary, binary, Integer.parseInt(prevBinary+binary, 2));
				System.out.println();
				evenLine = !evenLine;
				prevBinary = binary;
      		}
      		input.close();
		} catch (Exception e) {
			System.out.println("ERROR!");
      		e.printStackTrace();
		}
	}

  	public static String roundValues(String tuple) {
		String output = "";
		tuple = tuple.replaceAll(" ", "");    // Remove spaces
		double[] xy = new double[2];
		xy[0] = Double.parseDouble(tuple.substring(tuple.indexOf('(')+1, tuple.indexOf(',')));
		xy[1] = Double.parseDouble(tuple.substring(tuple.indexOf(',')+1, tuple.indexOf(')')));
		for (int i = 0; i < xy.length; i++) {
			if (xy[i] <= -2) output += "-3";
			else if (xy[i] < 0) output += "-1";
			else if (xy[i] < 2) output += "1";
			else output += "3";
			if (i < xy.length - 1) output += ",";
		}
		return output;
  	}

	public static void populateQAMVals(HashMap<String, String> QAMVals) {
		QAMVals.put("-3,-3", "0010");
		QAMVals.put("-3,-1", "0011");
		QAMVals.put("-3,1", "0001");
		QAMVals.put("-3,3", "0000");
		QAMVals.put("-1,-3", "0110");
		QAMVals.put("-1,-1", "0111");
		QAMVals.put("-1,1", "0101");
		QAMVals.put("-1,3", "0100");
		QAMVals.put("1,-3", "1110");
		QAMVals.put("1,-1", "1111");
		QAMVals.put("1,1", "1101");
		QAMVals.put("1,3", "1100");
		QAMVals.put("3,-3", "1010");
		QAMVals.put("3,-1", "1011");
		QAMVals.put("3,1", "1001");
		QAMVals.put("3,3", "1000");
	}
}