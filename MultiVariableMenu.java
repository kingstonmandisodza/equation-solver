import java.util.Scanner;
/**
 * @author Kingston
 * This is the multivariable menu class, the class that the user directly interacts with...
 * ...if they choose the multivariable option.
 * The user then enters the equations in the system.
 * Validation is performed on all user input in order to meet Objective 12
 */
public class MultiVariableMenu
{
	private Scanner sc = new Scanner(System.in);

	//allows one to write print instead of System.out.println
	//makes for easier reading.
	private void print(Object x)
	{
		System.out.println(x);
	}

	public MultiVariableMenu()
	{
	}

	//Is the method used to load the menu.
	//calls on other private methods to deal with user input as required
	public void runProgram()
	{

		String input;

		do
		{
			input = getUserInput();

			//checkUserInput makes sure the input is valid
			if(checkUserInput(input))
			{
				//attempts to receive the equations from the user
				//if the user gives bad input, this input is identified as invalid.
				if(input.equals("2"))
				{
					try 
					{
						carryOutTwoVariable();
					}
					catch(Exception e) 
					{
						print("One or more of your equations is not valid.");
					}
				}

				//attempts to receive the equations from the user
				//if the user gives bad input, this input is identified as invalid.
				else if(input.equals("3"))
				{
					try 
					{
						carryOutThreeVariable();
					}
					catch(Exception e) 
					{
						print("One or more of your equations is not valid.");
					}
				}
			}
			else
			{
				print("Invalid option entered");
			}
		}
		while(!checkUserInput(input));

	}

	//Asks the user if they are solving two or three variable equations
	private String getUserInput()
	{
		print("Enter 2 to solve a two variable equation."
				+ "\nEnter 3 to solve a 3 variable equation.");

		return sc.nextLine();
	}

	//Receives the input required for two-variable equations
	//validates the input to prevent most erroneous input
	//any input that slips through is accounted for by exception handling
	private void carryOutTwoVariable()
	{
		String equation;
		boolean stop;
		do 
		{
			print("Write 1x instead of just x.");
			print("Enter the first two-variable equation.");
			equation = sc.nextLine();
			stop = true;

			//checks every char to see if it is on the whitelist or not.
			for(int i = 0; i < equation.length(); i++) 
			{
				if(equation.charAt(i) != '+') 
				{
					if(equation.charAt(i) != '-') 
					{
						if(!(""+equation.charAt(i)).matches("[0-9xyz=]{1}")) 
						{
							stop = false;
						}
					}
				}
			}
			
			//if a non-whitelisted char is found, the equation is declared invalid.
			if(!stop) 
			{
				print("Invalid equation.");
			}

		}while (!stop);
		
		//stores the valid equation as a TwoVariable
		TwoVariable tv1 = new TwoVariable(equation);



		//same logic as above.
		do 
		{

			print("Enter the second two-variable equation.");
			equation = sc.nextLine();
			stop = true;

			for(int i = 0; i < equation.length(); i++) 
			{
				if(equation.charAt(i) != '+') 
				{
					if(equation.charAt(i) != '-') 
					{
						if(!(""+equation.charAt(i)).matches("[0-9xy=]{1}")) 
						{
							stop = false;
						}
					}
				}	
			}
			if(!stop) 
			{
				print("Invalid equation.");
			}
		}while (!stop);

		TwoVariable tv2 = new TwoVariable(equation);
		print((new TwoVariableSystem(tv1,tv2)).solve());
	}

	//same logic as CarryOutTwoVariable but with z included in validation
	private void carryOutThreeVariable()
	{
		String equation;
		boolean stop;
		do 
		{
			print("Write 1x instead of just x.");
			print("Enter the first three-variable equation.");
			equation = sc.nextLine();
			stop = true;

			for(int i = 0; i < equation.length(); i++) 
			{
				if(equation.charAt(i) != '+') 
				{
					if(equation.charAt(i) != '-') 
					{
						if(!(""+equation.charAt(i)).matches("[0-9xyz=]{1}")) 
						{
							stop = false;
						}
					}
				}
				if(!stop) 
				{
					print("Invalid equation.");
				}
			}
		}while (!stop);

		ThreeVariable tv1 = new ThreeVariable(equation);


		do 
		{
			print("Enter the second three-variable equation.");
			equation = sc.nextLine();
			stop = true;

			for(int i = 0; i < equation.length(); i++) 
			{
				if(equation.charAt(i) != '+') 
				{
					if(equation.charAt(i) != '-') 
					{
						if(!(""+equation.charAt(i)).matches("[0-9xyz=]{1}")) 
						{
							stop = false;
						}
					}
				}
			}
			if(!stop) 
			{
				print("Invalid equation.");
			}
		}while (!stop);
		ThreeVariable tv2 = new ThreeVariable(equation);

		do 
		{
			print("Enter the third three-variable equation.");
			equation = sc.nextLine();
			stop = true;

			for(int i = 0; i < equation.length(); i++) 
			{
				if(equation.charAt(i) != '+') 
				{
					if(equation.charAt(i) != '-') 
					{
						if(!(""+equation.charAt(i)).matches("[0-9xyz=]{1}")) 
						{

							stop = false;
						}
					}
				}
			}
			if(!stop) 
			{
				print("Invalid equation.");
			}
		}while (!stop);

		ThreeVariable tv3 = new ThreeVariable(equation);
		print((new ThreeVariableSystem(tv1,tv2,tv3)).solve());

	}

	//checks the initial user input and returns true if it is valid
	private boolean checkUserInput(String userInput)
	{
		return (userInput.matches("[23]{1}")) ? true : false;
	}

	public static void main(String args[])
	{
		MultiVariableMenu menu = new MultiVariableMenu();
		menu.runProgram();
	}
}