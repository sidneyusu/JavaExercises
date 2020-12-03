/* Created by: Sidney Shane Dizon; UCID: 10149278;
 *  CPSC 331 Assignment 2 
 *  
 *  This java program is designed to address the problem of
 *  multiplication between large natural numbers constructed
 *  from thousands of digits.
 *  
 *  
 *  */

import java.util.Scanner;
import java.util.ArrayList;

public class productOfNumbers {
	public static void main(String[] args){
	//This is the instance of the scanner
	Scanner input = new Scanner(System.in);
	
	/*Section I - Asks from user to insert two numbers 
	//Pre-Condition: Input of two Strings representing a natural number that follows the ff. criteria:
	// i. natural number greater than 1
	// Only characters allowed to be used are:
	//  i. The natural numbers {0, 1, 2, 3, 4, 5, 6, 7, 8, 9} 
	//Post-Condition: Stores the input into 2 String variables, namely:
	// i. num1
	// ii. num2*/
	System.out.println("Type-in your 1st number to multiply");
	String num1 = input.nextLine();
	System.out.println("Type-in your 2nd number to multiply");
	String num2 = input.nextLine();
	/*
	long start = System.currentTimeMillis();
	*/
	/*Section II - Calculates the product of two natural numbers
	 * Pre-Condition: 
	 * i. Two stored string values from the Section I, that is, the input of the user
	 * Post-Condition: 
	 * i. The input values are not changed 
	 * ii. Calculated the product of two natural numbers > 1 
	 */
	//We need the list of products
	ArrayList<String> listOfProducts;
	//Make the number with more digits the argument for the first parameter
	if (num1.length()<num2.length()){
		listOfProducts = calcProduct(num2, num1);

	}
	else{ // else they have the same number of digits or num1 has more digits then 
		listOfProducts = calcProduct(num1, num2);

	}
	
	//Now we have the list of products, we have to add all the products to get the final product
	String product = addNumbers(listOfProducts);
	
	/*
	 * Section III - Prints on Screen the Result from Section II
	 * Pre-Condition:
	 * i. String representation of the product to be printed
	 * Post-Condition:
	 * i. Prints on the screen the product of multiplying two natural numbers
	 */
	System.out.println("Results:\nProduct:\n" + product);
	
	/*
	 * Section IV - Prints on Screen the intermediate calculations of the multiplication 
	 * 	(partial products) from Section II
	 * Pre-Condition:
	 * i. ArrayList of Strings that represents the partial products
	 * Post-Condition:
	 * i. Prints on the screen the partial products
	 */
	System.out.println("\nIntermediate Calculations:\n");
	for (Short p=1; p<(listOfProducts.size()+1); p++){
		System.out.println(p + ") " + listOfProducts.get(p-1));
	}
	/*
	long elapse = System.currentTimeMillis() - start;
	System.out.println("Total Time: " + elapse);
	*/
	input.close();
	}
	
	/**
	 * Multiplies two numbers and then returns a list of products of for each digits of the numbers
	 * @param num1 number to multiply to
	 * @param num2 number to multiply with
	 * @return returns a list of products for each digits of the numbers
	 */
	public static ArrayList<String> calcProduct(String num1, String num2){
		ArrayList<String> productList = new ArrayList<String>();
		Short k = 0; // represents the index of the products in the list of products from the 2 numbers
		Short z = 0; // represents how many zeroes we need to add to the end of the product
		Short i = (short) (num2.length()); // represents the index of the digit from the 2nd number
		// iterate on each digit of the 2nd number
		while (i>0){
			String product2Str = "";
			Short carryOver = 0;
			Short j = (short) (num1.length()); // represents the index of the digit from the 1st number
			//Multiply the digit of the 2nd number starting from 
			// the right to all of the digits of the 1st number
			while (j>0){
				// get the digits to be multiplied
				String frstNumStr = num1.substring(j-1, j);
				String scndNumStr = num2.substring(i-1, i);
				// then multiply and add carryOvers
				Short product1 = (short) ((Short.parseShort(frstNumStr)*Short.parseShort(scndNumStr))+carryOver);
				String product1Str = product1.toString();
				
				//System.out.println(product1Str);
				
				if (j>1){ //if not the last number to be multiplied with
					if (product1>9){   //there is carryOver
						product2Str = product1Str.substring(1) + product2Str;
						carryOver = Short.parseShort(product1Str.substring(0, 1));
					}
					else{	//there is no carryOver 
				carryOver = 0;
				product2Str = product1Str + product2Str;
					}
				}
				else //else it is the last number to be multiplied with
					product2Str = product1Str + product2Str;
				j--;
				}
			
			Short z2 = z;// represents how many zeroes we need to add to the end of the product
			String productStr = product2Str;
			//We need to compensate for the zeroes in the end of the numbers for each time we multiply
			while (z2>0){
				productStr = productStr + "0";
				z2--;
			}
			
			
			productList.add(productStr);
			k++;
			i--;
			z++;
			}		
		
		return productList;
	}
	
	/**
	 * Method that Adds up a list of numbers 
	 * @param list1	ArrayList of numbers stored as String to be sumed up
	 * @return	returns a String representing the total sum of all the numbers in the ArrayList
	 */
	public static String addNumbers (ArrayList<String> list1){
		
		String frstNum = list1.get(0); // this is the 1st number in the array
		Short i = 1; // since we already got the first element in the list, we start at the 2nd index
		
		while (i<list1.size()){
			String scndNum = list1.get(i); // this will be the second number to add with
			String num1Str; // this will be the numbers with same number of digits
			String num2Str; // to make adding easier
			
			// If first number has more digits than the second 
			// number then make them of same number of digits by adding "0" to the left
			if (firstHasMoreDigits(frstNum, scndNum)){
				num2Str = getFinalNumber(frstNum, scndNum);
				num1Str = frstNum;
			}
			else if (frstNum.length() == scndNum.length()){ // if equal digits, do nothing
				num1Str = frstNum;
				num2Str = scndNum;
			}
			else{
				num1Str = getFinalNumber(scndNum, frstNum);
				num2Str = scndNum;	
			}
			//Once we have equal number of digits and elements in the list
			frstNum = getSum(num1Str, num2Str);	
			i = (short) (i+1);
		}	
		return frstNum;
	}
	
	/**
	 * Calculates the sum of two numbers
	 * @param num1 first number
	 * @param num2 second number
	 * @return returns a String representation of the sum
	 */
	public static String getSum(String num1, String num2){
		Short i = (short) num1.length();
		String sumStr = "";
		boolean carryOver = false;
		
		while (i>0){
			//Parse to Short to get each digit in the string
			Short first = Short.parseShort(num1.substring(i-1, i));
			Short second = Short.parseShort(num2.substring(i-1, i));
			//Add the numbers
			Short sum = (short) (first + second);
			
			if (!carryOver){
				//Check for carry overs
				if (sum > 9){
					
					sumStr = Short.toString(sum).substring(1) + sumStr;
					carryOver = true;
					i = (short) (i-1);
				}
				//If no carry Overs
				else{
					sumStr = Short.toString(sum) + sumStr;
					carryOver = false;
					i = (short) (i-1);
				}
			}
			else{
				sum = (short) (sum+1);
				if (sum > 9){
					sumStr = Short.toString(sum).substring(1) + sumStr;
					carryOver = true;
					i = (short) (i-1);
				}
				//If no carry Overs
				else{
					sumStr = Short.toString(sum) + sumStr;
					carryOver = false;
					i = (short) (i-1);
				}
			}
		}
		
		if (carryOver)
			sumStr = 1 + sumStr;
		//return the reverse of the sumStr
		return sumStr;
		
	}
	
	/**
	 * Get method to acquire number with zeros to have equal length size for numbers after decimal
	 * @param num1 number with larger number of digits
	 * @param num2 number with smaller number of digits
	 * @return returns the final number with zeros so that num1 and num2 have the same number of digits
	 */
	public static String getFinalNumber (String num1, String num2){
		Short num1StrLng = (short) num1.length();
		Short num2StrLng = (short) num2.length();
		String numFinal = num2;
		Short i=0;
				
		//Add zeros to have equal length of numbers
		while (i<num1StrLng-num2StrLng){
			numFinal = "0" + numFinal;
			i = (short) (i+1);
		}
		return numFinal;
	}
	
	/**
	 * Check to see if the numbers has same number of digits
	 * @param num1 first number to compare
	 * @param num2 second number to compare
	 * @return returns true if the first number as more digits than the second, otherwise false
	 */
	public static Boolean firstHasMoreDigits (String num1, String num2){
		Short num1StrLng = (short) num1.length();
		Short num2StrLng = (short) num2.length();
		//If first number has more digits return true
		if (num1StrLng > num2StrLng)
			return true;
		return false;
	}
}


//Example numbers
//44035170633074083307104399050237617076107780526301
//250303963611040636701620931487
//
//10 characters
// 1000000000
// 100 characters
//1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111
// 1000 characters
//1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111
