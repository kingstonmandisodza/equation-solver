public class Polynomial extends Equation
{
	/**
	 * @author Kingston
	 * This is the Polynomial class.
	 * The polynomial class is used to perform the Newton Rapshon method with steps.
	 * The polynomial class is also capable of finding the coefficient and corresponding...
	 * ...power of each term of the polynomial equation entered.
	 * differentiate is capable of algebraically differentiating any polynomial equation.
	 * Quadratic and Cubic both inherit from this class.
	 * This class inherits from Equation.
	 */
	
	//This method is used in various functions where the coefficients and powers are needed.
	//it works by identifying where an x is, and checking the values before and after the x to...
	//...to find the coefficient and power.
	public String[][] getCoefficientsAndPowers()
	{

		//variable declarations
		String equation = getEquation();
		String[][] coefficients;
		int count = 0;
		int count2 = 0;
		int k = 0;
		boolean cont = true;

		boolean minuscheck = false;

		//checks how many x terms there are.
		for(int i = 0; i < equation.length(); i++)
		{
			if(equation.charAt(i) == 'x')
			{
				count++;
			}
		}

		//creates an array which stores each coefficient and power
		coefficients = new String [count][2];

		//so the array isn't empty
		for(int i = 0; i < count; i++)
		{
			coefficients[i][0] ="";
			coefficients[i][1] ="";
		}

		//finds every instance of an x then works backwards to find the coefficient and forwards to find the power.
		for(int i = 1; i < equation.length(); i++)
		{
			if(equation.charAt(i) == 'x')
			{
				k = i - 1;

				while( k >= 0 && cont)
				{

					//runs every time a number or decimal point is found
					if((""+equation.charAt(k)).matches("[.0-9]"))
					{
						//appends that number to the front of the coefficient
						coefficients[count2][0] = equation.charAt(k) + coefficients[count2][0];
						k--;
					}

					//runs when a minus is encountered on the front of a number and this is the first -
					else if (equation.charAt(k) == '-' && !minuscheck)
					{

						//appends the - to the front of the coefficient
						coefficients[count2][0] = equation.charAt(k) + coefficients[count2][0];

						//tells the program to no longer run.
						minuscheck = true;
						cont = false;
						k--;
					}
					else cont = false;
				}

				cont = true;
				k = i + 2;

				//the same logic as above, but only works forwards instead of backwards to find the power
				while( k < equation.length() && cont)
				{
					if((""+equation.charAt(k)).matches("[.0-9]") )
					{
						coefficients[count2][1] = coefficients[count2][1] + equation.charAt(k);
						k++;
					}

					else if (equation.charAt(k) == '-' && k == i + 2)
					{
						coefficients[count2][0] = equation.charAt(k) + coefficients[count2][0];
						k++;
					}

					else cont = false;
				}

				cont = true;
				count2++;
				minuscheck = false;
			}

		}

		return coefficients;
	}

	//Uses getCoefficientsAndPowers to form a double with all the terms of the polynomial
	//Each polynomial coefficient is then stored in a 1d array, with the index of the array specifying the power.
	//This is used in the Quadratic and Cubic classes for quadratic and cubic methods.
	public double [] findPolynomialTerms()
	{
		String[][] coefficients = getCoefficientsAndPowers();
		int largestPower = 0;


		//finds the largest power.
		largestPower = findLargestPower();

		//the largest power determines the size of the array
		double [] terms = new double[largestPower + 1];


		//this adds up all terms which have the same power.
		for (int j = 0; j < terms.length; j++)
		{
			for (int i = 0; i < coefficients.length; i++)
			{
				if(Integer.parseInt(coefficients[i][1]) == j)
				{
					terms[j] = terms[j] + Double.parseDouble(coefficients[i][0]);
				}

			}
		}

		//returns the terms.
		return terms;
	}

	//gets the coefficients of the polynomial and looks for the largest power
	//then returns the largest power
	//this allows the order of the polynomial to be determined
	public int findLargestPower() 
	{
		String[][] coefficients = getCoefficientsAndPowers();
		int largestPower = 0;

		//Checks each coefficient's power, and if it is larger than the current largest power...
		//...the largest power is updated.
		for (int i = 0; i < coefficients.length; i++)
		{
			largestPower = (Integer.parseInt(coefficients[i][1]) > largestPower) ? 
					Integer.parseInt(coefficients[i][1]) : largestPower;
		}

		return largestPower;
	}


	//performs algebraic differentiation on the polynomial
	//it does this using f'(x) = anx^(n-1)
	//getCoefficientsAndPowers is used to get the original terms.
	//the result of the differentiation is printed to the user.
	public Polynomial differentiate()
	{
		//uses the getCoefficientsAndPowers method to find the coefficients and powers
		String[][] coefficients = getCoefficientsAndPowers();

		String differential = "";
		double newCoefficient;
		int newPower;

		//finds the new coefficient and power for each coefficient collected using the rules of differentiation
		for(int i = 0; i < coefficients.length; i++)
		{
			newCoefficient = Double.parseDouble(coefficients[i][0]) * Double.parseDouble(coefficients[i][1]);
			newPower = Integer.parseInt(coefficients[i][1])-1;
			differential = differential + ""+newCoefficient + "x^" +newPower
					+ ( (i == coefficients.length-1) ? "":"+");
		}

		//sanitises the output
		differential = differential.replace("++", "+")
				.replace("+-", "-")
				.replace("-+", "-")
				.replace("--", "+")
				.replace("+0.0x^-1","");

		//returns as type polynomial so that polynomial methods can be done on the differential.
		return new Polynomial(differential);
	}

	//Uses the newton raphson method, but also shows the steps using the differentiate method.
	public String solveUsingNewtonRaphsonWithSteps(double x, double numIterations) 
	{
		Polynomial differential = (new Polynomial (getEquation())).differentiate();
		String output = "";

		try 
		{
			//runs for the amount of iterations specified by the user.
			for(int i = 0; i < numIterations; i++) 
			{
				//outputs the iterative formula generated by f(x) and f'(x)
				output = output + "x_"+(i+1)+"=x_"+(i)+"-("+getEquation()+")/("+differential.getEquation()+")";
				//applies the iterative formula
				x = x - f(x)/findDerivativeAtPoint(x);
				//prints the result of applying the iterative formula
				output = output + "\nx_"+(i+1)+"="+x+"\n";
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return output;
	}


	public void resetPolynomialParameters()
	{
		resetEquationParameters();
	}

	public Polynomial(String userInputEquation)
	{
		super(userInputEquation);
		resetPolynomialParameters();
		setEquation(userInputEquation);
	}
}