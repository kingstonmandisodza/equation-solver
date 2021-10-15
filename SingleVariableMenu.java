import java.util.Scanner;
public class SingleVariableMenu
{
	/**
	 * @author Kingston
	 * This is the single variable menu class, the class that the user directly interacts with...
	 * ...if they choose the single variable option.
	 * The user then enters the equation.
	 * Validation is performed on all user input in order to meet Objective 12
	 */
	public Scanner sc = new Scanner(System.in);

	private void print(Object x)
	{
		System.out.println(x);
	}

	//prints the equation menu
	final private String equationMenu = "Please enter your equation."
			+ "\n- Inlcude x^1 and x^0 terms in your polynomials."
			+ "\n- write 'x' as '1x'."
			+ "\n- Make sure there is no ambiguity."
			+ "\n- Do not include brackets if they are not needed.";


	public SingleVariableMenu()
	{
	}

	//this method runs the menu associated with this class
	public void runProgram()
	{
		String input = getUserInput();
		boolean check = true;


		do
		{
			if(checkUserInput(input))
			{
				try 
				{
					(new Equation (input)).f(0);

				}
				catch(Exception e) 
				{
					print("You have either entered an invalid equation or an equation which is"
							+ " not in the correct form.");
					check = false;
				}

				if(check) 
				{
					if(determineIfPolynomial(input))
					{
						try 
						{
							solvePolynomial(input);
						}
						catch(Exception e) 
						{
							print("You have either entered an invalid equation or an equation which is"
									+ " not in the correct form.");
						}

					}
					else
					{
						solveNonPolynomialEquation(input);
					}
				}
			}
			else
			{
				print("This is not an equation");
			}

		}while(!checkUserInput(input));
	}

	//receives the equation from the user
	private String getUserInput()
	{
		print(equationMenu);
		return sc.nextLine();
	}

	//checks the user input to ensure it is in the correct format.
	private boolean checkUserInput(String eq)
	{
		if(eq.isEmpty()) 
		{
			return false;
		}



		else 
		{
			return true;
		}
	}

	private void solvePolynomial(String eq)
	{

		//runs for quadratics only
		if(determinePolynomialOrder(eq) == 2)
		{
			//quadratic object
			Quadratic quad = new Quadratic(eq);

			//solves using the quadratic formula
			print(quad.solveUsingQuadraticFormulaWithSteps());

			print("Alternative method.");

			//solves by completing the square
			print(quad.solveUsingCompletingTheSquareWithSteps());
		}

		//runs for cubics only
		else if(determinePolynomialOrder(eq) == 3)
		{
			Cubic cub = new Cubic(eq);
			String option = "";
			String option2 = "";
			String strSolution, strNumerator, strDenominator;
			Fraction solution;


			//runs if you know a solution.
			do
			{
				//confirms whether the user knows a rational solution, once it is identified as a cubic
				print("Do you know a rational solution? Y or N.");
				option = sc.nextLine().toUpperCase();

				switch(option)
				{
				//runs if the user knows a solution.
				//shows the standard a level methods of solving cubics
				case"Y":

					//checks if the solution is an integer or a non-integer rational
					do
					{
						print("Is your solution an integer? Y or N.");
						option2 = sc.nextLine().toUpperCase();

						switch(option2)
						{

						case"Y":

							//receives the user's solution
							print("Enter solution.");

							do
							{
								//input is taken as a string so validation can be performed on it
								strSolution = sc.nextLine();
						
								//will only run if the string does not contain non-numerical characters
								if(!strSolution.contains("^[0-9]")) 
								{
									//stores the solution as a Fraction
									solution = new Fraction(Integer.parseInt(strSolution),1);

									//solves the equation by comparing coefficients and returns it to the user
									print(cub.solveUsingComparingCoefficientsWithSteps(solution));
									
									//if the solution entered is a solution, and the solution is not 0, algebraic division is also used
									if(!cub.solveUsingComparingCoefficientsWithSteps(solution).equals("The solution entered is not a solution")) 
									{
										if(!strSolution.equals("0")) 
										{
											print("Alternative method.");
											print(cub.solveUsingAlgebraicDivisionWithSteps(solution));
										}
									}
									
									//runs if the solution entered is wrong
									else 
									{
										print("As the solution entered is wrong, the Newton Raphson method will be "
												+ "used to attempt to provide a correct solution");
										print(cub.solveUsingNewtonRaphsonWithSteps(3, 20));
									}
								}
								
								//Informs the user the number entered is invalid
								else 
								{
									print("Invalid number entered.");
									print("Enter solution.");
								}
						
							}while(strSolution.contains("^[0-9]"));

							break;

						//same logic as above, only it receives input for the numerator and the denominator
						case"N":

							do
							{
								print("Enter numerator.");

								strNumerator = sc.nextLine();
								if(!strNumerator.contains("^[0-9]")) 
								{

									do
									{
										print("Enter denominator.");
										strDenominator = sc.nextLine();

										if(!strDenominator.contains("^[0-9]")) 
										{
											solution = new Fraction(Integer.parseInt(strNumerator), Integer.parseInt(strDenominator));

											if(!cub.solveUsingComparingCoefficientsWithSteps(solution).equals("The solution entered is not a solution")) 
											{
												if(!strNumerator.equals("0")) 
												{
													print("Alternative method.");
													print(cub.solveUsingAlgebraicDivisionWithSteps(solution));
												}
											}
											else 
											{
												print("As the solution entered is wrong, the Newton Raphson method will be "
														+ "used to attempt to provide a correct solution");
												print(cub.solveUsingNewtonRaphsonWithSteps(3, 20));
											}

										}
										else 
										{
											print("Invalid option entered.");
										}


									}while (strDenominator.contains("^[0-9]"));

								}
								else 
								{
									print("Invalid option entered.");
								}

							}while(strNumerator.contains("^[0-9]"));

							break;

						default:
							print("Invalid option entered.");

							break;
						}

					}while(!option2.equals("Y") && !option2.equals( "N"));

					break;

				//solves the cubic using the Newton Raphson method is a solution is not known
				case "N":

					print(cub.solveUsingNewtonRaphsonWithSteps(3, 20));

					break;

				//informs the user that the option entered is invalid
				default:
					print("Invalid option entered.");
					break;
				}
				
				
			//runs until a valid option is selected
			}while(!option.toUpperCase().equals("Y") && !option.toUpperCase().equals("N"));

		}
		else
		{
			Polynomial poly = new Polynomial(eq);

			//tells the user the method
			print("This equation will be solved with the Newton-Raphson method.");

			//receives the start number and the number of iterations from the user
			print("Enter start number");
			double startNum = Double.parseDouble(sc.nextLine());
			print("Enter number of iterations");
			double iterations = Integer.parseInt(sc.nextLine());

			//prints the result of solving using the newton raphson method
			print(poly.solveUsingNewtonRaphsonWithSteps(startNum, iterations));
		}


	}

	private void solveNonPolynomialEquation(String eq)
	{
		Equation equation = new Equation(eq);
		String option;
		double startNum;
		int iterations;
		double lowerLimit;
		double upperLimit;

		//gives the user an option for how they want the equation to be solved.
		//the bisection method provides a solution marginally faster but the newton raphson method is more accurate with a large number of iterations.
		print("Do you want to solve using the Newton-Raphson method (N) or bisection method (B)?");
		option = sc.nextLine().toUpperCase();

		switch(option)
		{
		case"N":
			print("Enter start number");
			startNum = Double.parseDouble(sc.nextLine());

			print("Enter number of iterations");
			iterations = Integer.parseInt(sc.nextLine());
			print(equation.solveUsingNewtonRaphson(startNum, iterations, 0));
			break;
		case"B":
			print("Enter the lower limit");
			lowerLimit = Double.parseDouble(sc.nextLine());
			print("Enter the upper limit");
			upperLimit = Double.parseDouble(sc.nextLine());
			print(equation.solveUsingBisectionMethod(lowerLimit, upperLimit, 0));

		}
	}

	//determines if the equation is a polynomial
	//looks for non-polynomial features
	private boolean determineIfPolynomial(String eq)
	{
		//needs fixing

		for(int i = 1; i < eq.length(); i++)
		{
			if((""+eq.charAt(i)).equals("^"))
			{
				if((""+eq.charAt(i-1)).matches("[0-9]{1}"))
				{
					return false;
				}
			}

		}

		eq = eq.replaceAll("[0-9]", "")
				.replace("^", "")
				.replace("+", "")
				.replace("-", "")
				.replace(".", "")
				.replace("x", "")
				.replace("=", "");



		if(eq.isEmpty())
		{
			return true;
		}

		return false;

	}

	private int determinePolynomialOrder(String eq)
	{
		return (new Polynomial(eq)).findLargestPower();
	}

	public static void main(String args[])
	{
		SingleVariableMenu menu = new SingleVariableMenu();
		menu.runProgram();
	}




}

