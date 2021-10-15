public class Quadratic extends Polynomial
{
	/**
	 * @author Kingston
	 * This is the Quadratic class.
	 * Is capable of solving quadratic equations using completing the square or the...
	 * ...quadratic formula.
	 * Inherits from Polynomial.
	 * Uses the Polynomial method FindPolynomialTerms to get the coefficients.
	 */
	
	//gets the turning point using turning point = -b/2a
	public double getTurningX()
	{
		double[] coefficients = findPolynomialTerms();

		return (-1 * coefficients[1])/(2 * coefficients[2]);

	}

	//gets the discriminant by finding b^2-4ac
	public double getDiscriminant()
	{
		double[] coefficients = findPolynomialTerms();

		return (coefficients[1] * coefficients[1]) - 4 * coefficients[0] * coefficients[2];
	}


	//This method applies the quadratic formula to an equation, and shows the steps.
	public String solveUsingQuadraticFormulaWithSteps()
	{
		String solutions[] = {"",""};
		double turningPoint = getTurningX();
		double disc = getDiscriminant();
		String output;
		double a = findPolynomialTerms()[2];
		
		//solutions stores both instances of the quadratic formula, which are then combined for the string output.
		solutions[0] = "Using the quadratic formula:\n\n-b+sqrt(b^2-4ac)/2a\n";
		solutions[1] = "Using the quadratic formula:\n\n-b-sqrt(b^2-4ac)/2a\n";

		//runs for real numbers
		if(disc >= 0)
		{
			//uses the quadratic formula as normal.
			solutions[0] = solutions[0]  + "\n= "  +("" + (turningPoint + (Math.sqrt(disc)/(2*a) ) ) );
			solutions[1] = solutions[1]  + "\n= " +("" + (turningPoint - (Math.sqrt(disc)/(2*a) ) ) );

		}
		
		//runs for complex numbers
		else
		{
			//uses the quadratic formula for the absolute value of the discriminant.
			//the b^2-4ac/2a has an i placed after it.
			solutions[0] = solutions[0]  + "\n= " +("" + turningPoint + "+" +(Math.sqrt(Math.abs(disc))/(2*a) ) )  + "i" ;
			solutions[1] = solutions[1]  +"\n= " +("" + turningPoint   + "-" + (Math.sqrt(Math.abs(disc))/(2*a) ) )  + "i ";
		}


		//sanitises the output
		for(int i = 0; i < solutions.length; i++)
		{
			solutions[i] = solutions[i].replace("--", "+")
					.replace("+-", "-")
					.replace("-+", "-")
					.replace("++", "+") + "\n";
		}

		//output collects the two solutions and how they are achieved
		output = solutions[0]+"\n"+solutions[1];
		return output;
	}
	
	//finds both the real and imaginary square roots
	private String findSqrt(double num)
	{
		if (num>=0) return ""+ Math.sqrt(num);
		else return ""+ Math.sqrt(Math.abs(num))+ "i";
	}

	//Uses the completing the square approach to solve a quadratic equation
	//Shows the steps
	//completing the square rearranges a quadratic into square form so it can be written in terms of x.
	public String solveUsingCompletingTheSquareWithSteps()
	{
		double a = findPolynomialTerms()[2];
		double b =  findPolynomialTerms()[1];
		double c =  findPolynomialTerms()[0];
		double turningX = getTurningX();
		double RHS = -c + (turningX * turningX);
		String output;

		//the square completed in general terms.
		output = (a+"x^2+"+b+"x+"+c+"=0\n").replace("+-", "-").replace("-+","-").replace("++","+").replace("--", "+");
		
		output = output	+"iff (x-"+turningX +")^2-"+(turningX * turningX)+((c>=0)?"+"+c:c)+"=0\n"
				+"iff (x-"+turningX+")^2=" +RHS+"\n"
				+"iff x-"+turningX +"=+/-"+findSqrt(RHS)+"\n"
				+"iff x=" +turningX +"+/-"+findSqrt(RHS);

		//replaces repeated additive operators with their non-repeated equivalent 
		return output.replace("+-", "-")
				.replace("-+","-")
				.replace("++","+")
				.replace("--", "+");
	}

	//adds missing terms so that equations entered without x^1 or x^0 terms can still be solved.
	private String addMissingTerms(String equation) 
	{
		boolean x1 = false, x0 = false;

		for(int i = 0; i < equation.length()-1; i++) 
		{


			if(equation.charAt(i) == '^' && equation.charAt(i+1) == '1') 
			{
				x1 = true;
			}

			else if(equation.charAt(i) == '^' && equation.charAt(i+1) == '0') 
			{
				x0 = true;
			}
		}

		//if a term is not spotted, it is added in the form 0x^n
		if(!x1) equation = equation + "+0x^1";
		if(!x0) equation = equation + "+0x^0";

		return equation;
	}

	public void resetQuadraticParameters()
	{
		resetEquationParameters();
	}

	public Quadratic(String userInputEquation)
	{
		super(userInputEquation);
		resetQuadraticParameters();
		setEquation(addMissingTerms(userInputEquation));
	}


}