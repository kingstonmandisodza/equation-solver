import java.util.Scanner;

/**
 * @author Kingston
 * This is the menu class, the class that the user directly interacts with.
 * The user specifies if they are solving a single or multi variable equation
 * The user then enters the equation(s)
 * Validation is performed on all user input in order to meet Objective 12
 */
public class CombinedMenu
{
	//this method replaces System.out.println with print
	//this makes the code more nice to read.
	public static void print(Object x)
	{
		System.out.println(x);
	}
	
	//the main method
	//receives user input for the initial menu and makes sure it is within the valid range.
	//the appropriate menu is then run, depending on the choice th user makes.
	public static void Main() 
	{
		Scanner sc = new Scanner(System.in);
		String option = "";
		
		//the single and multi variable menus are each accessed by an object.
		SingleVariableMenu svm = new SingleVariableMenu();
		MultiVariableMenu mvm = new MultiVariableMenu();
		
		//print out to collect single or multi variable from the user
		//their input determines the next course of action
		print("Are you solving a single variable (1) or multi-variable (2) equation?");


		//the do while runs until the user has selected an allowed option.
		do 
		{
			option = sc.nextLine();
	
		
			switch(option) 
			{
			
			//if the user enters 1, the single variable menu is loaded.
			case"1":
				svm.runProgram();
				break;
				
			//if the user enters 2, the multi-variable menu is loaded.
			case"2":
				mvm.runProgram();
				break;
			
				//this runs if the user input does not match 1 or 2.
			default:
				print("Invalid option entered");
				print("Are you solving a single variable or multi-variable equation?");
				
			}
		}while(!option.matches("[12]{1}"));
		
		sc.close();
	}


	public static void main(String args[])
	{
		Main();
	}
}