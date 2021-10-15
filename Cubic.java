/**
 * @author Kingston
 * This is the cubic class, for solving cubic equations with cubic methods.
 * Two cubic methods are in use here, comparing coefficients and algebraic divison.
 * Comparing coefficients is a method where, given a rational solution...
 * ...a quadratic which is formed of the other two solutions can be deduced by...
 * ...choosing values for that quadratic which multiply by the factor to form the cubic polynomial.
 * Algebraic long division is a method where a cubic is divided by a factor, to give a remainder of 0.
 * This results in a quadratic which can be solved to find the other two solutions.
 * Both methods are valid at A Level and students are commonly asked to use them.
 */
public class Cubic extends Polynomial
{


	//This method is the comparing coefficients method.
	//The method, given the coefficients of each term of the cubic, attempts to...
	//...find the quadratic containing two solutions, given a correct solution, by...
	//re-constructing the original equation from a general form quadratic, which is...
	//mulitplied by the factor which results from the first solution.
	//This method is part of the A Level Mathematics specificiation.
	public String solveUsingComparingCoefficientsWithSteps(Fraction firstSolution)
	{
	
		//this is overridden by the instruction inside try
		double y = 1;
		
		//checks that the solution entered is, in fact, a solution.
		try 
		{
			y = f(firstSolution.toDecimal());
		}
		catch(Exception e)
		{
		}
		
		//runs if the first solution is, in fact, a solution.
		if(y == 0) 
		{

			//calls findPolynomialTerms() in order to find the x^3 coefficient (n0) the x^2 coefficient (n1) and the constant (n3)
			double n0 = findPolynomialTerms()[3];
			double n1 = findPolynomialTerms()[2];
			double n2 = findPolynomialTerms()[1];
			double n3 = findPolynomialTerms()[0];


			if(firstSolution.getNumerator() != 0) {

				//the coefficients of the quadratic formed as a result of comparing coefficients
				double A, B, C;

				//quadraticSolutions stores the string with the process + solutions to display to the user
				String quadraticSolutions;

				//stores the factor
				Fraction factor;

				//the quadratic object to solve Ax^2+Bx+C
				Quadratic quad;

				//initialises factor as a fraction. The factor is not actually a fraction, but it can be thought of as a fraction directly linked to the first solution.
				//if p/q is a solution, (qx-p) is a factor, by the factor theorem. Therefore -q/p can be thought of as the "factor fraction" of pq.
				//this fraction is simplified to make the factorisation less messy.
				factor = firstSolution.findNegativeReciprocal().simplify();

				//the numerator of the factor fraction is represented by a in (ax-b)
				//the denominator is represented by b.
				int a = factor.getNumerator();
				int b = factor.getDenominator();

				//Finds the general form of each coefficient of the quadratic.
				A = n0/a;
				quadraticSolutions = "A = "+n0+"/"+a + " = " +A+"\n";
				C = n3/b;
				quadraticSolutions = quadraticSolutions + "C = " +n3+"/"+b+" = "+C+"\n";
				B = (n1 - (b * A))/a;
				quadraticSolutions = quadraticSolutions + "B = (" +n1+"-("+b+"*"+A+"))/"+a+" = "+B+"\n"
						+ "Therefore";
				
				//gets rid of any decimals in the quadratic by multiplying by a common multiple
				A = A * Math.abs(a * b);
				B = B * Math.abs(a * b);
				C = C * Math.abs(a * b);

				quadraticSolutions = quadraticSolutions + "("+a+"x-"+b+")("+A+"x^2+"+B+"x+"+C+") = 0\n";



				//forms a quadratic, in the correct format, based on the result of A, C and B
				quad = new Quadratic((A+"x^2+"+B+"x^1+"+C+"x^0").replace("++", "+").replace("--", "+").replace("+-", "-").replace("-+", "-"));

				//solves the quadratic using the quadratic formula and returns it
				quadraticSolutions = quadraticSolutions + 
						quad.solveUsingQuadraticFormulaWithSteps();

				//tidies up the output.
				quadraticSolutions = quadraticSolutions.replace("+-", "-").replace("-+", "-").replace("++", "+").replace("--", "-");

				return quadraticSolutions;
			}

			//runs if the solution entered is 0.
			else 
			{
				//The quadratic resulting from factorising x from the cubic
				Quadratic quad = new Quadratic(n0+"x^2+"+n1+"x^1+"+n2+"x^0");
				
				//stores the solution with steps
				String solutionMessage;

				//Explains how the quadratic has been formed.
				solutionMessage = "As one of the solutions is 0, a quadratic can be formed"
						+ " by factorising x, which leads to the quadratic:\n"
						+n0+"x^2+"+n1+"x+"+n2+"\n";

				//solves the quadratic and displays the steps to the user.
				solutionMessage = solutionMessage + quad.solveUsingQuadraticFormulaWithSteps();

				//returns a tidied up output of the solution message.
				return solutionMessage.replace("+-", "-")
						.replace("--", "+")
						.replace("+-", "-")
						.replace("++", "+");

			}

		}
		else 
		{
			return "The solution entered is not a solution";
		}

	}

	
	//This method takes a known solution, finds the factor implied by that solution, and divides the equation by the factor.
	//This results in a quadratic that can be solved to find the other solutions.
	public String solveUsingAlgebraicDivisionWithSteps(Fraction firstSolution)
	{
		//calls findPolynomialTerms() in order to find the x^3 coefficient (n0), the x^2 coefficient (n1), the x coefficient (n2) and the constant (n3)
		double n0 = findPolynomialTerms()[3];
		double n1 = findPolynomialTerms()[2];
		double n2 = findPolynomialTerms()[1];
		double n3 = findPolynomialTerms()[0];

		//quadraticSolutions stores the string with the process + solutions to display to the user
		String quadraticSolutions;

		//the quadratic object to solve Ax^2+Bx+C
		Quadratic quad;

		//initialises factor as a fraction. The factor is not actually a fraction, but it can be thought of as a fraction directly linked to the first solution.
		//if p/q is a solution, (qx-p) is a factor, by the factor theorem. Therefore -q/p can be thought of as the "factor fraction" of pq.
		//this fraction is simplified to make the factorisation less messy.
		Fraction factor = firstSolution.findNegativeReciprocal().simplify();

		//the numerator of the factor fraction is represented by a in (ax-b)
		//the denominator is represented by b.
		int a = factor.getNumerator();
		int b = factor.getDenominator();

		//performs the division in general terms. Produces quadratic t0x^2+t1x+t2.
		double t0 = n0/a;
		double t1 = (n1 - (b * t0))/a;
		double t2 = (n2 - (b * t1))/a;

		//shows the division steps to the user
		quadraticSolutions = "(1)"+n0+"x^2/"+a +"="+t0+"x^2\n"
				+"(2)"+ n1 +"-" +(b * t0) +"="+(t1*a)+"\n"
				+"(3)"+  (t1*a) + "x/"+a +"="+t1+"x\n"
				+"(4)"+  n2 +"-" +(b * t1) +"="+(t2*a)+"\n"
				+"(5)"+  (t2*a) + "/"+a +"="+t2+"\n"
				+"(6)"+  n3 +"-" +(b * t2)+"="+(n3-(b*t2))+"\n";


		//validation check. If remainder =/= 0 the program will know something has gone wrong
		double remainder = n3 - (b * t2);

		//will attempt to solve the quadratic formed if the remainder is 0
		if(remainder == 0)
		{
			t0 = t0 * a;
			t1 = t1 * a;
			t2 = t2 * a;

			quadraticSolutions = quadraticSolutions + "Therefore: " +t0+"x^2+"+t1+"x+"+t2+"=0\n";
			quad = new Quadratic((t0+"x^2"+"+"+t1+"x^1"+"+"+t2+"x^0").replace("++", "+").replace("--", "+").replace("+-", "-").replace("-+", "+"));
			quadraticSolutions = quadraticSolutions + quad.solveUsingQuadraticFormulaWithSteps();
			quadraticSolutions = quadraticSolutions.replace("++", "+").replace("+-", "-").replace("--", "+").replace("-+", "-");

		}


		//if there is a remainder, then the solution
		else return "error: the value entered as a solution is not a solution.";
		

		return quadraticSolutions;
	}

	//replaces any missing terms.
	//this avoids errors caused by unexpected input due to missing terms.
	private String addMissingTerms(String equation) 
	{
		boolean x2 = false, x1 = false, x0 = false;

		for(int i = 0; i < equation.length()-1; i++) 
		{
	
			if(equation.charAt(i) == '^' && equation.charAt(i+1) == '2') 
			{
				x2 = true;
			}

			else if(equation.charAt(i) == '^' && equation.charAt(i+1) == '1') 
			{
				x1 = true;
			}

			else if(equation.charAt(i) == '^' && equation.charAt(i+1) == '0') 
			{
				x0 = true;
			}
		}

		//if a term is not spotted, it is added in the form 0x^n
		if(!x2) equation = equation + "+0x^2";
		if(!x1) equation = equation + "+0x^1";
		if(!x0) equation = equation + "+0x^0";

		return equation;
	}


	public void resetCubicParameters()
	{
		resetPolynomialParameters();
	}

	public Cubic(String userInputEquation)
	{
		super(userInputEquation);
		resetCubicParameters();
		setEquation(addMissingTerms(userInputEquation));
		;

	}
}

