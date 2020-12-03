import java.util.Scanner;

public class characterCreator {
	public static void main(String[] args){
	System.out.println("enter how many chracters\n");
	Scanner input = new Scanner(System.in);
	int i = 0;
	String j = input.nextLine();
	String s = "";
	while (i<Integer.parseInt(j)){
		s = s + 1;
		i++;
	}
	System.out.println("Result:\n" + s);
	input.close();
	
}
}
	
