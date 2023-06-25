import java.awt.*;
import java.io.*;
import java.util.*;

//First Programmer  ->  Name: Hasan  	     Surname: Özeren    No: 150121036
//Second Programmer ->  Name: Niyazi Ozan    Surname: Ateþ      No: 150121991
//Third Programmer  ->  Name: Ahmet Arda     Surname: Nalbant   No: 150121004
//Fourth Programmer ->  Name: Umut  	     Surname: Bayar     No: 150120043

public class BinaryDataConverter {

	public static void main(String[] args) throws Exception {
		
		Scanner tempScan = new Scanner(System.in);
        System.out.print("What is the input file labeled as: ");
        String fileName = tempScan.nextLine();
        File file = new File(fileName);
        
        File outputFile = new File("output.txt");
        PrintWriter output = new PrintWriter(outputFile);
        
        if (file.exists()) {
        	 Scanner input = new Scanner(new File(fileName));
        	 Scanner consoleReader = new Scanner(System.in);
        	 
        	 System.out.print("Byte ordering (type \"b\" for Big endian or \"l\" Little endian): ");
        	 String byteOrdering = consoleReader.next();
        	 System.out.print("Data type (type \"float\" for float or \"int\" for int or \"unsigned\" for unsigned): ");
        	 String dataType = consoleReader.next();
        	 System.out.print("Data type size (type 1, 2, 3 or 4): ");
        	 String dataTypeSize = consoleReader.next();
        	 
        	 boolean test = false;
        	 if (!(byteOrdering.equals("b") || byteOrdering.equals("l"))) {
        		 System.out.println("Wrong byte ordering entered!");
        		 output.write("Wrong byte ordering entered!\n");
        		 test = true;
        	 }
        	 if (!(dataType.equals("float") || dataType.equals("int") || dataType.equals("unsigned"))) {
        		 System.out.println("Wrong data type entered!");
        		 output.write("Wrong data type entered!\n");
        		 test = true;
        	 }
        	 if (!(dataTypeSize.equals("1") || dataTypeSize.equals("2") || dataTypeSize.equals("3") || dataTypeSize.equals("4"))) {
        		 System.out.println("Wrong data type size entered!");
        		 output.write("Wrong data type size entered!\n");
        		 test = true;
        	 }
        	 if (!test) {
        		 ArrayList<String> numbers = new ArrayList<>();
            	 
            	 while (input.hasNext()) {
            		 String temp = input.nextLine().replaceAll(" ", "");
            		 for (int i = 0 ; i < 12/Integer.parseInt(dataTypeSize) ; i++) {
            			 numbers.add(temp.substring(i*Integer.parseInt(dataTypeSize)*2, (i+1)*Integer.parseInt(dataTypeSize)*2));
            		 }
            	 }
            	 
            	 if (byteOrdering.equals("l")) {
            		 littleToBig(numbers, Integer.parseInt(dataTypeSize)*2);
            	 }
            	 
            	 HextoBin(numbers, Integer.parseInt(dataTypeSize)*2);
            	 
            	 if (dataType.equals("unsigned")) {
            		 binToUnInt(numbers, Integer.parseInt(dataTypeSize)*8);
            	 }
            	 else if (dataType.equals("int")) {
            		 binToInt(numbers, Integer.parseInt(dataTypeSize)*8);
            	 }
            	 else if (dataType.equals("float")) {
            		 binToFloat(numbers, Integer.parseInt(dataTypeSize)*8);
            	 }
            	 
            	 printNumbers(numbers, 12/Integer.parseInt(dataTypeSize), output);
            	 
            	 input.close();
            	 consoleReader.close();
            }
        }
        else {
            System.out.println("File not found: " + fileName);
        }
        Desktop.getDesktop().open(outputFile);
        output.close();
        tempScan.close();
	}
	
	static void littleToBig(ArrayList<String> numbers, int size) {
		
		int totalNumbers = numbers.size();
		for (int i = 0 ; i < totalNumbers ; i++) {
			String temp = "";
			for (int j = 0 ; j < size ; j += 2) {
				temp = numbers.get(i).substring(j, j+2) + temp;
			}
			numbers.set(i, temp);
		}
	}
	
	static void HextoBin(ArrayList<String> numbers, int size) {
		
		for (int i = 0 ; i < numbers.size() ; i++) {
			String binary = "";
			for (int j = 0 ; j < size ; j++) {
				switch(numbers.get(i).charAt(j)) {
		            case '0':
		            	binary = binary + "0000";
		                break;
		            case '1':
		            	binary = binary + "0001";
		                break;
		            case '2':
		            	binary = binary + "0010";
		                break;
		            case '3':
		            	binary = binary + "0011";
		                break;
		            case '4':
		            	binary = binary + "0100";
		                break;
		            case '5':
		                binary = binary + "0101";
		                break;
		            case '6':
		            	binary = binary + "0110";
		                break;
		            case '7':
		            	binary = binary + "0111";
		                break;
		            case '8':
		            	binary = binary + "1000";
		                break;
		            case '9':
		            	binary = binary + "1001";
		                break;
		            case 'a':
		            case 'A':
		            	binary = binary + "1010";
		                break;
		            case 'b':
		            case 'B':
		            	binary = binary + "1011";
		                break;
		            case 'c':
		            case 'C':
		            	binary = binary + "1100";
		                break;
		            case 'd':
		            case 'D':
		            	binary = binary + "1101";
		                break;
		            case 'e':
		            case 'E':
		            	binary = binary + "1110";
		                break;
		            case 'f':
		            case 'F':
		            	binary = binary + "1111";
		                break;
		            default:
		                System.out.println("Invalid Hexadecimal Digit!");
		         }
			}
			numbers.set(i, binary);
		}
	}
	
	static void binToUnInt(ArrayList<String> numbers, int size) {
		
		long tempNumber = 0;
		for (int i = 0 ; i < numbers.size() ; i++) {
			tempNumber = 0;
			for (int j = 0 ; j < size ; j++) {
				tempNumber = tempNumber*2 + (numbers.get(i).charAt(j) - '0');
			}
			String temp = "" + tempNumber;
			numbers.set(i, temp);
		}
	}
	
	static void binToInt(ArrayList<String> numbers, int size) {
		
		long tempNumberNegative = 0;
		long tempNumber = 0;
		for (int i = 0 ; i < numbers.size() ; i++) {
			tempNumber = 0;
			for (int j = 1 ; j < size ; j++) {
				tempNumber = tempNumber*2 + (numbers.get(i).charAt(j) - '0');
			}
			if (numbers.get(i).charAt(0) == '1') {
				tempNumberNegative = (long)(-1*Math.pow(2, (size-1)));
				tempNumber = tempNumber + tempNumberNegative;
			}
			String temp = "" + tempNumber;
			numbers.set(i, temp);
		}
	}
	
	static void binToFloat(ArrayList<String> numbers, int size) {
		for (int i = 0 ; i < numbers.size() ; i++) {
			switch (size) {
			case 8:
				
				int sign = 1;
				if (numbers.get(i).charAt(0) == '1') {
					sign = -1;
				}
				
				int bias = 7;
				String temp = numbers.get(i).substring(1, 5);
				int exp = 0;
				for (int j = 0 ; j < temp.length() ; j++) {
					exp = exp*2 + (temp.charAt(j) - '0');
				}
				
				String frac = numbers.get(i).substring(5, 8);
				double fraction = 0;
				for (int k = 0 ; k < frac.length() ; k++) {
					if (numbers.get(i).charAt(5 + k) == '1') {
						fraction = fraction + Math.pow(2, -(k+1));
					}
				}
				
				if (temp.equals("0000")) {
					if (frac.equals("000")) {
						if (sign == 1) {
							frac = "0";
						}
						else {
							frac = "-0";
						}
						numbers.set(i, frac);
					}
					else {
						if (sign == 1) {
							frac = "" + (Math.pow(2, -6)*fraction);
						}
						else {
							frac = "-" + (Math.pow(2, -6)*fraction);
						}
						int index = frac.indexOf(".");
						int lastIndex = frac.indexOf("E");
						String tempNumber = frac.substring(0, (index+1));
						int loop = 0;
						while(index+1+loop < frac.length() && (frac.charAt(index+1+loop) != 'E')) {
							tempNumber = tempNumber + frac.charAt(index+1+loop);
							loop++;
						}
						double roundNumber = Double.parseDouble(tempNumber);
						roundNumber = Math.round(roundNumber*100000)/100000.0;
						tempNumber = "" + roundNumber;
						if (roundNumber < 0) {
							while (tempNumber.length() < 8) {
								tempNumber = tempNumber + "0";
							}
						}
						else {
							while (tempNumber.length() < 7) {
								tempNumber = tempNumber + "0";
							}
						}
						if (lastIndex != -1) {
							frac = tempNumber + frac.substring(lastIndex);
						}
						else {
							frac = tempNumber;
						}
						numbers.set(i, frac);
					}
				}
				else if (temp.equals("1111")) {
					if (frac.equals("000")) {
						if (sign == 1) {
							numbers.set(i, "infinity");
						}
						else {
							numbers.set(i, "-infinity");
						}
					}
					else {
						numbers.set(i, "NaN");
					}
				}
				else {
					fraction = fraction + 1;
					frac = "" + (sign*Math.pow(2, (exp-bias))*fraction);
					int index = frac.indexOf(".");
					int lastIndex = frac.indexOf("E");
					String tempNumber = frac.substring(0, (index+1));
					int loop = 0;
					while(index+1+loop < frac.length() && (frac.charAt(index+1+loop) != 'E')) {
						tempNumber = tempNumber + frac.charAt(index+1+loop);
						loop++;
					}
					double roundNumber = Double.parseDouble(tempNumber);
					roundNumber = Math.round(roundNumber*100000)/100000.0;
					tempNumber = "" + roundNumber;
					if (roundNumber < 0) {
						while (tempNumber.length() < 8) {
							tempNumber = tempNumber + "0";
						}
					}
					else {
						while (tempNumber.length() < 7) {
							tempNumber = tempNumber + "0";
						}
					}
					if (lastIndex != -1) {
						frac = tempNumber + frac.substring(lastIndex);
					}
					else {
						frac = tempNumber;
					}
					numbers.set(i, frac);
				}
				
				break;
			case 16:

				sign = 1;
				if (numbers.get(i).charAt(0) == '1') {
					sign = -1;
				}
				
				bias = 31;
				temp = numbers.get(i).substring(1, 7);
				exp = 0;
				for (int j = 0 ; j < temp.length() ; j++) {
					exp = exp*2 + (temp.charAt(j) - '0');
				}
				
				frac = numbers.get(i).substring(7, 16);
				fraction = 0;
				for (int k = 0 ; k < frac.length() ; k++) {
					if (numbers.get(i).charAt(7 + k) == '1') {
						fraction = fraction + Math.pow(2, -(k+1));
					}
				}
				
				if (temp.equals("000000")) {
					if (frac.equals("000000000")) {
						if (sign == 1) {
							frac = "0";
						}
						else {
							frac = "-0";
						}
						numbers.set(i, frac);
					}
					else {
						if (sign == 1) {
							frac = "" + (Math.pow(2, -30)*fraction);
						}
						else {
							frac = "-" + (Math.pow(2, -30)*fraction);
						}
						int index = frac.indexOf(".");
						int lastIndex = frac.indexOf("E");
						String tempNumber = frac.substring(0, (index+1));
						int loop = 0;
						while(index+1+loop < frac.length() && (frac.charAt(index+1+loop) != 'E')) {
							tempNumber = tempNumber + frac.charAt(index+1+loop);
							loop++;
						}
						double roundNumber = Double.parseDouble(tempNumber);
						roundNumber = Math.round(roundNumber*100000)/100000.0;
						tempNumber = "" + roundNumber;
						if (roundNumber < 0) {
							while (tempNumber.length() < 8) {
								tempNumber = tempNumber + "0";
							}
						}
						else {
							while (tempNumber.length() < 7) {
								tempNumber = tempNumber + "0";
							}
						}
						if (lastIndex != -1) {
							frac = tempNumber + frac.substring(lastIndex);
						}
						else {
							frac = tempNumber;
						}
						numbers.set(i, frac);
					}
				}
				else if (temp.equals("111111")) {
					if (frac.equals("000000000")) {
						if (sign == 1) {
							numbers.set(i, "infinity");
						}
						else {
							numbers.set(i, "-infinity");
						}
					}
					else {
						numbers.set(i, "NaN");
					}
				}
				else {
					fraction = fraction + 1;
					frac = "" + (sign*Math.pow(2, (exp-bias))*fraction);
					int index = frac.indexOf(".");
					int lastIndex = frac.indexOf("E");
					String tempNumber = frac.substring(0, (index+1));
					int loop = 0;
					while(index+1+loop < frac.length() && (frac.charAt(index+1+loop) != 'E')) {
						tempNumber = tempNumber + frac.charAt(index+1+loop);
						loop++;
					}
					double roundNumber = Double.parseDouble(tempNumber);
					roundNumber = Math.round(roundNumber*100000)/100000.0;
					tempNumber = "" + roundNumber;
					if (roundNumber < 0) {
						while (tempNumber.length() < 8) {
							tempNumber = tempNumber + "0";
						}
					}
					else {
						while (tempNumber.length() < 7) {
							tempNumber = tempNumber + "0";
						}
					}
					if (lastIndex != -1) {
						frac = tempNumber + frac.substring(lastIndex);
					}
					else {
						frac = tempNumber;
					}
					numbers.set(i, frac);
				}
				break;
			case 24:

				sign = 1;
				if (numbers.get(i).charAt(0) == '1') {
					sign = -1;
				}
				
				bias = 127;
				temp = numbers.get(i).substring(1, 9);
				exp = 0;
				for (int j = 0 ; j < temp.length() ; j++) {
					exp = exp*2 + (temp.charAt(j) - '0');
				}
				
				frac = numbers.get(i).substring(9, 24);
				fraction = 0;
				for (int k = 0 ; k < frac.length() - 2 ; k++) {
					if (numbers.get(i).charAt(9 + k) == '1') {
						fraction = fraction + Math.pow(2, -(k+1));
					}
				}
				if (numbers.get(i).charAt(22) == '1') {
					if (numbers.get(i).charAt(23) == '1') {
						fraction = fraction + Math.pow(2, -13);
					}
					else {
						if (numbers.get(i).charAt(21) == '1') {
							fraction = fraction + Math.pow(2, -13);
						}
					}
				}
				
				if (temp.equals("00000000")) {
					if (frac.equals("0000000000000000")) {
						if (sign == 1) {
							frac = "0";
						}
						else {
							frac = "-0";
						}
						numbers.set(i, frac);
					}
					else {
						if (sign == 1) {
							frac = "" + (Math.pow(2, -126)*fraction);
						}
						else {
							frac = "-" + (Math.pow(2, -126)*fraction);
						}
						int index = frac.indexOf(".");
						int lastIndex = frac.indexOf("E");
						String tempNumber = frac.substring(0, (index+1));
						int loop = 0;
						while(index+1+loop < frac.length() && (frac.charAt(index+1+loop) != 'E')) {
							tempNumber = tempNumber + frac.charAt(index+1+loop);
							loop++;
						}
						double roundNumber = Double.parseDouble(tempNumber);
						roundNumber = Math.round(roundNumber*100000)/100000.0;
						tempNumber = "" + roundNumber;
						if (roundNumber < 0) {
							while (tempNumber.length() < 8) {
								tempNumber = tempNumber + "0";
							}
						}
						else {
							while (tempNumber.length() < 7) {
								tempNumber = tempNumber + "0";
							}
						}
						if (lastIndex != -1) {
							frac = tempNumber + frac.substring(lastIndex);
						}
						else {
							frac = tempNumber;
						}
						numbers.set(i, frac);
					}
				}
				else if (temp.equals("11111111")) {
					if (frac.equals("0000000000000000")) {
						if (sign == 1) {
							numbers.set(i, "infinity");
						}
						else {
							numbers.set(i, "-infinity");
						}
					}
					else {
						numbers.set(i, "NaN");
					}
				}
				else {
					fraction = fraction + 1;
					frac = "" + (sign*Math.pow(2, (exp-bias))*fraction);
					int index = frac.indexOf(".");
					int lastIndex = frac.indexOf("E");
					String tempNumber = frac.substring(0, (index+1));
					int loop = 0;
					while(index+1+loop < frac.length() && (frac.charAt(index+1+loop) != 'E')) {
						tempNumber = tempNumber + frac.charAt(index+1+loop);
						loop++;
					}
					double roundNumber = Double.parseDouble(tempNumber);
					roundNumber = Math.round(roundNumber*100000)/100000.0;
					tempNumber = "" + roundNumber;
					if (roundNumber < 0) {
						while (tempNumber.length() < 8) {
							tempNumber = tempNumber + "0";
						}
					}
					else {
						while (tempNumber.length() < 7) {
							tempNumber = tempNumber + "0";
						}
					}
					if (lastIndex != -1) {
						frac = tempNumber + frac.substring(lastIndex);
					}
					else {
						frac = tempNumber;
					}
					numbers.set(i, frac);
				}
				break;
			case 32:

				sign = 1;
				if (numbers.get(i).charAt(0) == '1') {
					sign = -1;
				}
				
				bias = 511;
				temp = numbers.get(i).substring(1, 11);
				exp = 0;
				for (int j = 0 ; j < temp.length() ; j++) {
					exp = exp*2 + (temp.charAt(j) - '0');
				}
				
				frac = numbers.get(i).substring(11, 32);
				fraction = 0;
				for (int k = 0 ; k < frac.length() - 8 ; k++) {
					if (numbers.get(i).charAt(11 + k) == '1') {
						fraction = fraction + Math.pow(2, -(k+1));
					}
				}
				if (numbers.get(i).charAt(24) == '1') {
					int k = 25;
					boolean rounding = false;
					while (k < 32) {
						if (numbers.get(i).charAt(k) == '1') {
							rounding = true;
						}
						k++;
					}
					if (!rounding && numbers.get(i).charAt(23) == '1') {
						rounding = true;
					}
					if (rounding) {
						fraction = fraction + Math.pow(2, -13);
					}
				}
				
				if (temp.equals("0000000000")) {
					if (frac.equals("000000000000000000000")) {
						if (sign == 1) {
							frac = "0";
						}
						else {
							frac = "-0";
						}
						numbers.set(i, frac);
					}
					else {
						if (sign == 1) {
							frac = "" + (Math.pow(2, -510)*fraction);
						}
						else {
							frac = "-" + (Math.pow(2, -510)*fraction);
						}
						int index = frac.indexOf(".");
						int lastIndex = frac.indexOf("E");
						String tempNumber = frac.substring(0, (index+1));
						int loop = 0;
						while(index+1+loop < frac.length() && (frac.charAt(index+1+loop) != 'E')) {
							tempNumber = tempNumber + frac.charAt(index+1+loop);
							loop++;
						}
						double roundNumber = Double.parseDouble(tempNumber);
						roundNumber = Math.round(roundNumber*100000)/100000.0;
						tempNumber = "" + roundNumber;
						if (roundNumber < 0) {
							while (tempNumber.length() < 8) {
								tempNumber = tempNumber + "0";
							}
						}
						else {
							while (tempNumber.length() < 7) {
								tempNumber = tempNumber + "0";
							}
						}
						if (lastIndex != -1) {
							frac = tempNumber + frac.substring(lastIndex);
						}
						else {
							frac = tempNumber;
						}
						numbers.set(i, frac);
					}
				}
				else if (temp.equals("1111111111")) {
					if (frac.equals("000000000000000000000")) {
						if (sign == 1) {
							numbers.set(i, "infinity");
						}
						else {
							numbers.set(i, "-infinity");
						}
					}
					else {
						numbers.set(i, "NaN");
					}
				}
				else {
					fraction = fraction + 1;
					frac = "" + (sign*Math.pow(2, (exp-bias))*fraction);
					int index = frac.indexOf(".");
					int lastIndex = frac.indexOf("E");
					String tempNumber = frac.substring(0, (index+1));
					int loop = 0;
					while(index+1+loop < frac.length() && (frac.charAt(index+1+loop) != 'E')) {
						tempNumber = tempNumber + frac.charAt(index+1+loop);
						loop++;
					}
					double roundNumber = Double.parseDouble(tempNumber);
					roundNumber = Math.round(roundNumber*100000)/100000.0;
					tempNumber = "" + roundNumber;
					if (roundNumber < 0) {
						while (tempNumber.length() < 8) {
							tempNumber = tempNumber + "0";
						}
					}
					else {
						while (tempNumber.length() < 7) {
							tempNumber = tempNumber + "0";
						}
					}
					if (lastIndex != -1) {
						frac = tempNumber + frac.substring(lastIndex);
					}
					else {
						frac = tempNumber;
					}
					numbers.set(i, frac);
				}
				break;
			default:
			}
		}
	}
	
	static void printNumbers(ArrayList<String> numbers, int numberPerLine, PrintWriter output) {
		int lines = numbers.size()/numberPerLine;
		
		for (int i = 0 ; i < lines ; i++) {
			for (int j = 0 ; j < numberPerLine ; j++) {
				System.out.print(numbers.get(i*numberPerLine + j) + " ");
				output.write(numbers.get(i*numberPerLine + j) + " ");
			}
			System.out.println("");
			output.write("\n");
		}
		
	}
	
}
