/* Created by: Sidney Shane Dizon; UCID: 10149278;
 *  CPSC 331 Assignment 3 
 *  
 *  This java program is designed to address the problem of
 *  creating the truth table of a given logical expression
 *  
 *  */

import java.util.Scanner;
import java.util.Stack;
import java.util.ArrayList;
import java.util.HashMap;



public class truthTable {
	public static void main(String[] args){
		//This is the instance of the scanner
		Scanner input = new Scanner(System.in);
		
		/*Section I - Asks from user to enter a string 
		//Pre-Condition: Input of s String representing a logical expression that follows the ff. criteria:
		// i. English alphabet letters from a to z or from A to Z (case insensitive)
		// ii. Left and right parenthesis: ( )
		// iii. Symbols: * + -
		//Post-Condition: Stores the input into a String variable, namely:
		// i. expression
		*/
		System.out.println("Type-in your logical expression.");
		String expression = input.nextLine();
		
		
		/*Section II - Searches for the independent variables contained in the logical expression
		 * Pre-Condition: 
		 * i. The String value stored from the Section I, that is, the input of the user
		 * Post-Condition: 
		 * i. The input values are not changed 
		 * ii. Searches and Prints the independent variables contained in the logical expression 
		 */
		System.out.println("Set of Independent Variables: ");
		ArrayList<String> variables = searchVariables(expression);
		int i = 0;
		while (i<variables.size()){
			System.out.println(variables.get(i));
			i++;
		}
		
		/*Section III - Searches for the subexpressions and logical expression
		 * Pre-Condition: 
		 * i. The String value stored from the Section I, that is, the input of the user
		 * Post-Condition: 
		 * i. The input values are not changed 
		 * ii. Searches and Prints the subexpressions and logical expression
		 */
		System.out.println("Set of Logical Subexpressions and logical expression: ");
		HashMap<String, String> subExpressions = searchExpression(expression);
		int j = 1;
		while (j<subExpressions.size()+1){
			String key = "LE" + j;
			System.out.println(key + ": " + subExpressions.get(key));
			j++;
		}
		
		/*Section IV - Generates the truth value table for the independent variables
		 * Pre-Condition: 
		 * i. The list of independent variables
		 * ii. The Map/Dictionary of the subexpressions and logical expression
		 * Post-Condition: 
		 * i. The input values are not changed 
		 * ii. Generates and Prints the truth values for the subexpressions and logical expression
		 */
		table(variables, subExpressions);
		
		input.close();
	}

	/**
	 * This method searches for the variables in the logical expression entered
	 * by the user
	 * @param expression String representation of the logical expression
	 * @return the variables in the logical expression stored as an Array List
	 */
	public static ArrayList<String> searchVariables(String expression){
		ArrayList variablesList = new ArrayList<String>();
		int i = 0;
		while (i<expression.length()){
			char ch = expression.charAt(i);
			if (Character.isLetter(ch)){//if it is an Alphabet character
				if (variablesList.size()==0)
					variablesList.add(Character.toString(ch));
				else{
					//Search algorithm to avoid duplicates in the sets
					Boolean newVariable = true;
					int j=0;
					while (j<variablesList.size()){
						//if the variable is already in the list then do not add again
						if (variablesList.get(j).equals(Character.toString(ch))){
							newVariable = false;
							break;
						}
						j++;
					}
					//if new variable then add to the list of variables
					if(newVariable)
						variablesList.add(Character.toString(ch));
				}	
			}
			i++;
		}
		return variablesList;
	}
	
	/**
	 * This method searches for subexpression and stores them in a list
	 * @param expression String representation of the logical expression
	 * @return the subexpressions and logical expression stored as an Array List
	 */
	public static HashMap<String, String> searchExpression(String expression){
		HashMap<String, String> expressionMap = new HashMap<String, String>();
		Stack<Character> stack = new Stack<Character>();
		
		String sub = "";
		int l = 1;
		int i = 0;
		while (i<expression.length()){
			char ch = expression.charAt(i);
			String sub1 = "";
			if (ch != ')')
				stack.push(ch);
			else{ //ch == ')'
				if (sub.length() != 0){ //if there is already a subexpression enclose in parenthesis
					sub = "(" + sub + ")";
				}
				while (stack.peek() != '('){
					sub1 = stack.pop() + sub1;
				}
				stack.pop();
				//check if the negation of the previous expression 
				if (sub1.equals("-"))
					sub = sub1 + sub;
				else
					sub = sub + sub1;
				
				String key = "LE" + l;
				expressionMap.put(key, sub);
				l +=1;
			}
			i++;
		}
		return expressionMap;
	}
	
	/**
	 * This method generates the truth values and returns a 2 dimensional array representing the truth table
	 * @param list list of Independent Variables
	 * @param map map of subExpressions and logical expression
	 * @return 2 dimensional ArrayList that represents the truth table
	 */
	public static ArrayList<ArrayList<String>> table(ArrayList<String> list, HashMap<String, String> map){
		ArrayList<ArrayList<String>> table = new ArrayList<ArrayList<String>>();
		ArrayList<String> row1 = new ArrayList<String>();
		
		//first row will be the names of all independent variables, the codenames of
		//the logical expressions and the codename of the logical expression
		int i = 0;
		while (i<list.size()){
			row1.add(list.get(i));
			i++;
		}
		int j = 1;
		while (j<map.size()+1){
			String key = "LE" + j;
			row1.add(key);
			j++;
		}
		table.add(row1);
		//System.out.println(table);
		
		//An array containing the generated values for the independent variables
		ArrayList<HashMap<String, String>> variableValues = variableValuesGenerator (list);		
		//System.out.println(variableValues);
		
		//number of Columns
		int w = 0;
		while (w<row1.size()){
			System.out.print(row1.get(w) + "     ");
			w++;
		}
		int x = 0;
		while (x<variableValues.size()){
			HashMap<String, String> row = variableValues.get(x);
			System.out.println(" ");
			int y = 0;
			while (y< row.size()){
			String key = row1.get(y);
			System.out.print(row.get(key) + "     ");
			y++;
			}
			x++;
		}
		
		
		//expressionValuesGenerator(variableValues, map);
		
		
		return table;
	}
	
	/**
	 * This method generated the truth values for the Independent Variables
	 * @param list list of independent variables
	 * @return a 2 dimensional array containing the possible combinations of 
	 * truth values for the independent variables
	 */
	public static ArrayList<HashMap<String, String>> variableValuesGenerator (ArrayList<String> list){
		//Array containing all the possible truth value combinations
		ArrayList<HashMap<String, String>> rowList = new ArrayList<HashMap<String, String>>();
		int n = list.size();
		int rows = (int) Math.pow(2,n);//number of possible combinations for the truth values
	    int i = 0;
	    while(i<rows){
	    	//truth values combinations
	    	HashMap<String, String> map = new HashMap<String, String>();
	    	int k = 0;
	    	int j=n-1;
	    	while(j > -1){
	    		String value;
	            int num = (i/(int) Math.pow(2, j))%2;
	            if(num == 0)// 0 represents True
	            	value = "T";
	            else// 1 represents False
	            	value = "F";
	            //Map the boolean expression to the corresponding independent variables 
	            String variable = list.get(k);
	            map.put(variable, value);
		        j--;
	            k++;
	        }
	    	rowList.add(map);
	        i++;
        }
	    
	    return rowList;
    }
	
	
	
	public static void expressionValuesGenerator (ArrayList<ArrayList<HashMap<String, Boolean>>> listOfVar,HashMap<String, String> mapOfSubExp){
		ArrayList<ArrayList> rowList = new ArrayList<ArrayList>();
		Stack<Character> stack = new Stack<Character>();
		
		int i = 0;
		while (i<listOfVar.size()){
			//get the subexpressions
			String key = "LE" + (i+1);
			String subExpression = "";
			subExpression = subExpression + mapOfSubExp.get(key);
			int length = subExpression.length();
			//push in a stack 
			String postFix = "";
			int j = 0;
			while (j<length){
				char ch = subExpression.charAt(j);
				stack.push(ch);
				j++;
			}
			//then rearrange then store in a string
			while (!(stack.isEmpty())){
				char ch = stack.pop();
					if(ch == '+' || ch == '-' || ch== '*'){
						char op = stack.pop();
						postFix = postFix + op + ch;
					}
					else{
						postFix = postFix + ch;
					}
			}
			i++;
			
			//push the string into a stack
			Stack<Character> stack1 = new Stack<Character>();
			int l = 0;
			while (l<postFix.length()){
				char ch = postFix.charAt(l);
				stack1.push(ch);
				l++;
			}
			
			int p = 0;
			while (!(stack1.isEmpty())){
				Boolean bool = false;
				if(stack1.pop() == '+'){
					ArrayList<HashMap<String, Boolean>> array = listOfVar.get(i);
					HashMap<String, Boolean> mapping = array.get(0);
					String key1 = Character.toString(stack1.pop());
					String key2 = Character.toString(stack1.pop());
					
					Boolean a = mapping.get(key1); 
					Boolean b = mapping.get(key2);
					
					bool = Boolean.logicalOr(a, b);
					
				}
				else if(stack1.pop() == '*'){
					ArrayList<HashMap<String, Boolean>> array = listOfVar.get(i);
					HashMap<String, Boolean> mapping = array.get(0);
					String key1 = Character.toString(stack1.pop());
					String key2 = Character.toString(stack1.pop());
					
					Boolean a = mapping.get(key1); 
					Boolean b = mapping.get(key2);
					
					bool = Boolean.logicalAnd(a, b);
					
				}
				if (stack1.pop() == '-'){
					if(bool)
						bool = false;
				}
			}
		}


	}

}

//Examples
// ((A+B)*A)
// (-((A+B)*C))
