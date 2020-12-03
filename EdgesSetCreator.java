/* Created by: Sidney Shane Dizon; UCID: 10149278;
 *  CPSC 331 Assignment 4 
 *  
 *  This java program is designed to address the problem of
 *  detecting the most and least popular vertices in an undirected graph G
 *  and saves the adjacent Matrix and adjacent List in the disk
 *  
 *  */

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;


public class EdgesSetCreator {

	public static void main(String[] args){
		//This is the instance of Scanner
		Scanner input = new Scanner(System.in);
		
		/*Section I - Asks from user to enter a positive integer which defines how many vertices |V| an
		 * undirected graph G has and the full path including full name of a CSV text file that holds the
		 * information of the Edges set of the undirected graph G
		//Pre-Condition: Input of the user follows the ff. criteria:
		// i. The numbering of the vertices starts with number zero 0
		// ii. All vertices numbered from 0 to |𝑉| − 1 exist in the undirected graph 𝐺
		// iii. You can assume that |𝑉| ≤ 32768
		// iv. Edges are described in the CSV text files using the Vertices numbering and the format Vertex, Vertex
		// for example: 0,1
		//Post-Condition: Stores the input into a String variable, namely:
		// i. vertices
		// ii. pathFile
		*/
		System.out.println("Type-in the number of Vertices. ");
		String vertices = input.nextLine();
		System.out.println("Type-in the path of the CSV text file. ");
		String pathFile = input.nextLine();
		
		/*Section II - Reads the set of edges in the CSV text file
		 * Pre-Condition: 
		 * i. The String value stored from the Section I, that is, the input of the user for the 
		 *     variable pathFile
		 * Post-Condition: 
		 * i. The input values are not changed 
		 * ii. Reads the edges from the CSV text files and stores the edges in an Array
		 *  
		 */
		ArrayList<String> edges = new ArrayList<String>();
		File file = new File(pathFile);
		String dirName = file.getParent();
		try{
			Scanner inputStream = new Scanner(file);
			// hasNext() loops line-by-line
			while (inputStream.hasNextLine()){
				String data = inputStream.nextLine();
				edges.add(data);
				}
			//after loop, close scanner
			inputStream.close();
			}
		catch (FileNotFoundException e){
			e.printStackTrace();
			}
		ArrayList<ArrayList<Integer>> listOfEdges = getEdges(edges);
		
		/*Section III - Creates the Adjacent Matrix and List  
		 * Pre-Condition: 
		 * i. The ArrayList of edges stored from the Section II, that is, the set of edges 
		 *     from the CSV text file
		 * ii. The String vertices that represents the range of vertices in |V|
		 * Post-Condition: 
		 * i. The ArrayList of edges are not changed 
		 * ii. Creates a 2D ArrayList representing the adjacentMatrix of G
		 * iii. Creates an Array of Linked List representing the adjacentList of G
		 *  
		 */
		//this is the dimension of the graph of G
		int dimension = Integer.parseInt(vertices);
		//the byte that represent an edge in the graph of G
		byte one = 1;
		//Initialize an array to enable modification later representing the adjacent Matrix
		ArrayList<ArrayList<Byte>> adjacentMatrix = getMatrix(dimension);
		//Initialize an array that will hold the linked list representing the adjacent list
		ArrayList<LinkedList<Integer>> adjacentList = getList(dimension);
		
		//Iterate on each pair of edges
		for (int i=0; i<listOfEdges.size(); i++){
			//the edge we are comparing with
			ArrayList<Integer> pair = listOfEdges.get(i);
			int column = pair.get(0);
			int row = pair.get(1);
			//check if it is in range of the specified vertex
			if ((column < dimension) && (row < dimension)) {
				//Modify the Adjacent Matrix
				adjacentMatrix.get(column).set(row, one);
				adjacentMatrix.get(row).set(column, one);
				//Modify the Adjacent List
				adjacentList.get(column).add(row);
				adjacentList.get(row).add(column);
			}
		}
		
		/*Section IV - Searches the most and least popular vertices with their neighbors
		 * Pre-Condition: 
		 * i. The Adjacent List from Section III
		 * Post-Condition: 
		 * i. The Adjacent List are not changed 
		 * ii. Stores the most popular vertex in an array of linked lists
		 * iii. Stores the most popular vertex in an array of linked lists
		 */
		ArrayList<LinkedList<Integer>> mostPopular = getMostPopular(adjacentList);
		ArrayList<LinkedList<Integer>> leastPopular = getLeastPopular(adjacentList);
		
		/*Section V - Prints the most and least popular vertices with their neighbors in the console
		 * Pre-Condition: 
		 * i. The most popular vertices with their neighbors
		 * ii. The least popular vertices with their neighbors
		 * Post-Condition: 
		 * i. The most and least popular vertices are not changed 
		 * ii. The neighbors are not changed
		 * iii. Prints on the console the results for the user to analyze
		 */
		
		System.out.println("Number of Neighbors for MPV: " + (mostPopular.get(0).size()-1) + "\nMPV, Neighbors");
		for(int a=0; a<mostPopular.size(); a++){
			System.out.print(mostPopular.get(a).get(0));
			for(int e=1; e<mostPopular.get(a).size(); e++){
				System.out.print("," + mostPopular.get(a).get(e));
			}
			System.out.print("\n");
		}
		
		System.out.println("Number of Neighbors for LPV: " + (leastPopular.get(0).size()-1) + "\nLPV, Neighbors");
		for(int a=0; a<leastPopular.size(); a++){
			System.out.print(leastPopular.get(a).get(0));
			for(int e=1; e<leastPopular.get(a).size(); e++){
				System.out.print("," + leastPopular.get(a).get(e));
			}
			System.out.print("\n");
		}
		
		/*Section VI - Saves the Adjacent Matrix and Adjacent List of G on the disk
		 * Pre-Condition: 
		 * i. The Adjacent Matrix
		 * ii. The Adjacent List
		 * iii. the directory to where the files are to be saved
		 * Post-Condition: 
		 * i. The Adjacent matrix and list are not changed 
		 * ii. The adjacent matrix and list are saved as CSV files in the disk
		 * 
		 */
		saveMatrix(dirName, adjacentMatrix);
		saveList(dirName, adjacentList);
		
		input.close();
		
	}
	
	
	/**
	 * This method converts an array of strings representing the set in V to an array of integers
	 * @param list Array of String to be converted to integer type 
	 * @return an array of integers representing the edges from the CSV file
	 */
	public static ArrayList<ArrayList<Integer>> getEdges(ArrayList<String> list){
		ArrayList<ArrayList<Integer>> listOfEdges = new ArrayList<ArrayList<Integer>>();
		
		//Iterates for each pairs of vertices
		for (int i=0;i<list.size();i++){
			int j=0;
			//Stores each pair in an array
			ArrayList<Integer> edge = new ArrayList<Integer>();
			String num = "";
			while (j<list.get(i).length()){
				//checks if it is a ','
				if (list.get(i).charAt(j) == ','){
					//convert the string to integer
					int num1 = Integer.parseInt(num);
					edge.add(num1);
					num = "";
				}
				//else if the character is the last element
				else if (j == (list.get(i).length()-1)){
					num = num + list.get(i).charAt(j);
					edge.add(Integer.parseInt(num));
				}
				else{
					num = num + list.get(i).charAt(j);
				}
				j++;
			}
			listOfEdges.add(edge);
		}
		
		//for (int k=0;k<3;k++){
		//	System.out.println(listOfEdges.get(k));
		//	}
		return listOfEdges;
	}
	
	/**
	 * This method creates a matrix with the specified dimension with 0 values
	 * @param dimension dimension of the square matrix to be created
	 * @return the initialized matrix with the specified dimensions
	 */
	public static ArrayList<ArrayList<Byte>> getMatrix(int dimension){
		ArrayList<ArrayList<Byte>> adjacentMatrix = new ArrayList<ArrayList<Byte>>();
		//creation of each column
		for(int i=0; i<dimension; i++){
			ArrayList<Byte> row = new ArrayList<Byte>();
			//creation of each row values
			for(int j=0; j<dimension; j++){
				byte zero = 0;
				row.add(zero);
			}
			adjacentMatrix.add(row);
		}
		return adjacentMatrix;		
	}
	
	/**
	 * This method create and initializes a linked list that will represent the adjacent list
	 * @param dimension the number of linked lists to be created
	 * @return the initialized linked list that temporarily represents the adjacent list
	 */
	public static ArrayList<LinkedList<Integer>> getList(int dimension){
		ArrayList<LinkedList<Integer>> adjacentList = new ArrayList<LinkedList<Integer>>();
		//creation of each head in the list
		for (int i=0; i<dimension; i++){
			LinkedList<Integer> empty = new LinkedList<Integer>();
			empty.add(i);
			adjacentList.add(empty);
		}
		return adjacentList;
	}
	
	/**
	 * This method searches for the most popular vertices with their neighbors
	 * @param list the adjacent list of G
	 * @return the most popular vertices with their neighbors  
	 */
	public static ArrayList<LinkedList<Integer>> getMostPopular(ArrayList<LinkedList<Integer>> list){
		ArrayList<LinkedList<Integer>> popular = new ArrayList<LinkedList<Integer>>();
		//the 1st element in the list will be the compared to find out the maximum size the list has
		int maxSize = list.get(0).size();
		//find out the maximum number of neighbor of the most popular vertex
		for(int i=1; i<list.size(); i++){
			if (list.get(i).size() > maxSize)
				maxSize = list.get(i).size();
		}
		//System.out.println(maxSize);
		
		//store all the vertices with most neighbors
		for(int j=0; j<list.size(); j++){
			if (list.get(j).size() == maxSize)
				popular.add(list.get(j));
		}
		
		return popular;
	}
	/**
	 This method searches for the least popular vertices with their neighbors
	 * @param list the adjacent list of |V|
	 * @return the least popular vertices with their neighbors  
	 */
	public static ArrayList<LinkedList<Integer>> getLeastPopular (ArrayList<LinkedList<Integer>> list){
		ArrayList<LinkedList<Integer>> unpopular = new ArrayList<LinkedList<Integer>>();
		
		//the 1st element in the list be compared to find the minimum size the list has
		int minSize = list.get(0).size();
		//find out the minimum number of neighbor of the most popular vertex
		for(int i=1; i<list.size(); i++){
			if (list.get(i).size() < minSize)
				minSize = list.get(i).size();
		}
	//	System.out.println(minSize);
		
		//store all the vertices with the least neighbors
		for(int j=0; j<list.size(); j++){
			if (list.get(j).size() == minSize)
				unpopular.add(list.get(j));
		}
		
		return unpopular;
	}

	
	/**
	 * This method saves the adjacency Matrix of the undirected graph G on the disk 
	 * @param path directory to where the CSV file is to be save
	 * @param adjacentMatrix the adjacency MAtric of the undirected graph G
	 */
	public static void saveMatrix(String path, ArrayList<ArrayList<Byte>> adjacentMatrix){
			
		String matrix = "Adjacent Matrix.csv";
		File dir = new File (path);
		File adjMatrix = new File (dir, matrix);
		try {
			PrintWriter pen = new PrintWriter(adjMatrix);

		StringBuilder sb = new StringBuilder();
		sb.append("X");
		//create the 1st row of the matrix 
		for(int q=0; q<adjacentMatrix.size(); q++){
			sb.append(',');
			sb.append(q);
		}
		sb.append('\n');
		//Create the following rows of the matrix
		for(int n=0; n<adjacentMatrix.size(); n++){
			sb.append(n);
			//columns of the matrix
			for(int s=0; s<adjacentMatrix.get(n).size(); s++){
				sb.append(',');
				sb.append(adjacentMatrix.get(n).get(s));
			}
			sb.append('\n');
		}
		//write the compiled results
		pen.write(sb.toString());
		pen.close();
		//System.out.println("Writing done");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * This method saves the adjacency List of the undirected graph G on the disk 
	 * @param path directory to where the CSV file is to be save
	 * @param adjacentList the adjacency List of the undirected graph G
	 */
	public static void saveList (String path, ArrayList<LinkedList<Integer>> adjacentList){
		
		String list = "Adjacent List.csv";
		File dir = new File (path);
		File adjList = new File (dir, list);
		try {
			PrintWriter pen = new PrintWriter(adjList);
		StringBuilder sb = new StringBuilder();
		
		//Create the each rows of the list
		for(int n=0; n<adjacentList.size(); n++){
			//columns of the list
			sb.append(adjacentList.get(n).get(0));
			for(int s=1; s<adjacentList.get(n).size(); s++){
				sb.append(',');
				sb.append(adjacentList.get(n).get(s));
			}
			sb.append('\n');
		}
		//write the compiled results
		pen.write(sb.toString());
		pen.close();
		//System.out.println("Writing done");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}	
	}
}
	
//Sample path: 
// C:\Users\Sidney\workspace\Assignment4\src\GraphEdges.csv

