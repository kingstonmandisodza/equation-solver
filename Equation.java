import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import javax.script.*;

/**
 * @author Kingston
 * This class is for the general type "Equation".
 * Equation encompasses all single-variable expressions in the form f(x)=g(x).
 * Two methods are possible for equation objects, the bisection method and...
 * ...the Newton-Raphson Method. Both solveByBisection and solveByNewtonRaphson...
 * ...do not show steps, they return 1 valid solution or approximation.
 * The root function F(x) = f(x) - g(x)
 * A JavaScript engine is used to allow a value to be input to the root function.
 * The JavaScript engine allows F(n) to be found where n is any real number.
 * This is achieved by mapping x to whatever value is input, converting the function to JavaScript...
 * ...and running it in the engine.
 * The bisection method works by recursively narrowing down the range that the solution can be in
 * ...until either an exact solution is found or the solution is within a reasonable degree of accuracy.
 * The Newton Raphson method works by using the recursive formula x_(n+1) = x_n - f(x_n)/f'(x_n) for...
 * ...a number of iterations specified by the user.
 * rearrange converts an equation from f(x)=g(x) to f(x)-g(x)=0.
 * Polynomial, and thus Quadratic and Cubic, inherit from this class.
 */
public class Equation extends GeneralEquation
{
	private String equation;

	public String getEquation()
	{
		return equation;
	}

	public void setEquation(String equation)
	{
		this.equation = rearrange(equation);
	}

	//rearranges the equation in to the form f(x) - g(x) = 0.
	//it does this by storing a list of the index of every + and - in fx and gx.
	//the equation is then reconstructed, but where a + should be a - is placed and vice versa.
	private String rearrange(String equation)
	{
		String fx = equation;
		String gx = "";
		String gxFlipped = "";

		//stores the index of every + and -, so they can be flipped later.
		//A list is used as the number of elements is not known, and it's convenient as it maintains the stack of element locations.
		LinkedList<Integer> plusList = new LinkedList<>();
		LinkedList<Integer> minusList = new LinkedList<>();


		//finds the = to identify fx and gx
		for(int i = 0; i < fx.length(); i++)
		{
			if(fx.charAt(i) == '=')
			{
				gx = fx.substring(i+1, fx.length());

				//adds a sign to the front of gx if there isn't one already
				if(!(""+gx.charAt(0)).matches("[-]{1}"))
				{
					gx = "+"+gx;
				}
			}
		}

		//if g(x) is empty or 0 returns f(x) as no rearrangement needs to happen
		if(gx.equals("") || gx.equals("+0")) return fx;

		//stores indexes of any + or - in gx
		for(int i = 0; i < gx.length(); i++)
		{
			if(gx.charAt(i) =='+')
			{
				plusList.add(i);
			}
			else if(gx.charAt(i) == '-')
			{
				minusList.add(i);
			}
		}

		//reconstructs g(x) but every + is changed to a - and vice versa
		for(Integer i = 0; i < gx.length(); i++)
		{
			if(i == plusList.peekFirst())
			{
				gxFlipped = gxFlipped + "-";

				//removes the item from the stack so the next item can be peeked
				plusList.pop();
			}
			else if(i == minusList.peekFirst())
			{
				gxFlipped = gxFlipped + "+";
				minusList.pop();
			}
			else
			{
				gxFlipped = gxFlipped + ""+gx.charAt(i);
			}

		}


		//removes the leading + from g(x) so that the fx.replace can identify the function as the original
		if(gx.charAt(0) == '+')
		{
			gx = gx.substring(1, gx.length());
		}

		fx = fx.replace("="+gx, "");
		fx = fx + gxFlipped;

		return fx;
	}

	//Solves an equation using the bisection method.
	//The bisection works betweent two limits, either side of a solution.
	//These limits are gradually brought closer and closer to the solution...
	//...until they are so close together that they are equal to the solution.
	//a recursive implementation has been used here, with a maximum of 500 attempts at...
	//bisection in order to prevent a stack overflow error.
	public double solveUsingBisectionMethod(double a, double b, int count)
	{
		
		double c, y_c = 0, y_a = 0;

		//finds the midpoint of the two limits and stores it as c
		c = (a + b)/2;

		try
		{
			//applies the function to c and stores it as y_c
			y_c = f(c);

			//applies the function to a and stores it as y_a
			y_a = f(a);

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		//assumes if a solution is not found after 500 attempts, there musn't be a solution.
		//Also prevents a stack overflow error.
		//This is the base case.
		if (count == 500) return Double.NaN;
		
		//returns the midpoint if eventually it equals 0, or is very close to 0
		else if(y_c == 0 || ( y_c <= 0.00000001 && y_c >= -0.00000001)) return c;

		//compares the sign of y_c and y_a and if they are equal, the root must be between c and b
		else if ( (sign(y_c) == sign(y_a))) return solveUsingBisectionMethod(c, b, count+1);
		
		//the only other option is the root is between a and c
		else return solveUsingBisectionMethod(a, c, count+1);

	}

	//returns the sign of the number put in the function.
	private char sign(double x)
	{
		if(x > 0) return 'p';
		else if (x < 0) return 'n';
		else return 'z';
	}

	//uses first principles to find the value of a derivative at a point
	//the derivative is defined as the ratio of a very small change in y to a very small change in x
	//this function uses this idea in order to numerically differentiate a function at a point
	public double findDerivativeAtPoint(double x) throws Exception
	{
		return (f(x+0.0000000001) - f(x))/0.0000000001;
	}

	//solves the equation using the numerical form of the newton raphson method
	public double solveUsingNewtonRaphson(double x, double numIterations, int count)
	{
		try
		{
			x = x - ((f(x))/(findDerivativeAtPoint(x)));

			if(numIterations > count)
			{
				x =solveUsingNewtonRaphson(x, numIterations, count+1);
			}
			else
			{
				return x;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return x;
	}


	//Converts an equation in string form to a line of JavaScript code
	//It does this first by identifying where multiplication takes place...
	//...then by converting the powers to Math.pow form and then by converting trigonometric...
	//...and logarithmic functions to their JavaScript equivalents.
	private String convertToReadableFormat(String equation)
	{

		//replacing x with *x allows the JavaScript code to identify multiplication.
		//replacing ) with +0) allows the power code to function correctly...
		//...in order to prevent issues caused by directly nested powers.
		equation = equation.replaceAll("x", "*x")
				.replace(")","+0)");


		//runs for the length of
		for(int i = 0; i < equation.length(); i++)
		{
			boolean stop = false;
			String term = "";
			String power = "";
			int bracketCount = 0;

			//identifies a power based on ^
			//then extracts the number or term being multiplied by working backwards from the ^
			//also extracts the power by working forwards from the ^
			if(equation.charAt(i) == '^')
			{
				//Runs if the preceding char is a closing bracket.
				//The program will treat everything before the corresponding opening bracket as a term.
				if(equation.charAt(i-1) == ')') 
				{
					stop = false;

					//runs until the beginning of the expression
					for(int j = i-2; j >= 0; j--) 
					{

						//checks whether the power has a bracket before it 
						//if there is a bracket, a note is taken.
						//this ensures that the correct closing bracket is treated as the end of the term
						if(equation.charAt(j) == ')') 
						{
							bracketCount++;
						}
						
						//runs either if there are still "unfulfilled brackets" or the char is not a bracket
						if(bracketCount > 0 || equation.charAt(j) != '(') 
						{
							//if the closing bracket has not already been identified.
							if(!stop) 
							{
								term = equation.charAt(j) + term;
							}

							//reduces the bracket count for unfulfilled brackets
							if(equation.charAt(j) == '(') 
							{
								bracketCount--;	
							}
						}
						
						//runs when the closing bracket has been identified
						else 
						{
							stop = true;
						}

					}
					
					//brackets the term.
					term = "(" + term + ")";
				}

				//runs if there is not a bracket before the power.
				else 
				{
					stop = false;

					//works backwards from the power
					for(int j = i-1; j >= 0; j--)
					{
						//runs until a delimiter is found, the delimiters include +, -, * and ,.
						if(equation.charAt(j) != '+' && equation.charAt(j) != '-' && !stop
								&& equation.charAt(j) != '*' &&equation.charAt(j) != ',')
						{
							//constructs the term
							term = equation.charAt(j) + term;
						}
						else
						{
							stop = true;
						}
					}
				}

				bracketCount = 0;

				//This uses the same logic as above, but working forwards and...
				//with open brackets swapped with close brackets
				if(equation.charAt(i+1) == '(') 
				{
					stop = false;

					for(int j = i+2; j < equation.length(); j++) 
					{

	
						if(equation.charAt(j) == '(') 
						{
							bracketCount++;
						}


						if(bracketCount > 0 || equation.charAt(j) != ')') 
						{
							if(!stop) 
							{
								power = power + equation.charAt(j);
							}

							if(equation.charAt(j) == ')') 
							{
								bracketCount--;
							}
						}
						else 
						{
							stop = true;
						}

					}

					power = "(" + power + ")";

				}
				
				//uses the same logic as above, but working forwards.
				else 
				{
					stop = false;

					for(int k = i+1; k < equation.length(); k++)
					{
						if(equation.charAt(k) != '+' && equation.charAt(k) != '-' && !stop
								&& equation.charAt(k) != ',' && equation.charAt(k) != '*' )
						{
							power = power + equation.charAt(k);
						}
						else
						{
							stop = true;
						}
					}
				}

				equation = equation.replace(term+"^"+power,"Math.pow("+term+","+power+")");
			}

		}

		//converts mathematical functions to their JS equivalent
		equation = equation
				.replaceAll("arcsin|sin\\^-1", "Math.asin")
				.replaceAll("arccos|cos\\^-1", "Math.acos")
				.replaceAll("arctan|tan\\^-1", "Math.atan")
				.replaceAll("(?!a)sin", "Math.sin")
				.replaceAll("(?!a)cos", "Math.cos")
				.replaceAll("(?!a)tan", "Math.tan")
				.replaceAll("(?!co)sec", "1/Math.cos")
				.replaceAll("cosec|csc", "1/Math.sin")
				.replaceAll("cot", "1/Math.tan")
				.replaceAll("log", "Math.log10")
				.replaceAll("ln", "Math.log")
				.replaceAll("[e]{1}", "Math.E")
				.replaceAll("[u]{1}", "Math.PI")
				;

		return equation;
	}

	//uses a JS engine to apply an input to the function given in terms of x
	//this allows f of a value to be found.
	//uses a JS engine to apply an input to the function given in terms of x
	//The code to set up the JavaScript engine and map x to the numerical input is from...
	//...https://www.codota.com/code/java/classes/javax.script.SimpleBindings
	//The code to convert the equation to a line of JavaScript was written by me.
	//(i.e convertToReadableFormat was written by me)
	public double f(double x) throws Exception
	{
		String equation = getEquation();
		double y;

		//used to map the numerical input to x
		Map<String, Object> xmap = new HashMap<String, Object>();

		//Creates the JavaScript engine object, which is capable of reading JavaScript code
		ScriptEngineManager engineManager = new ScriptEngineManager(null);
		ScriptEngine engine = engineManager.getEngineByName("Nashorn");

		//converts the equation to a line of JavaScript code.
		//convertToReadableFormat was written by me.
		equation = convertToReadableFormat(equation);

		//maps "x" to the numerical input
		xmap.put("x", x);

		//evaluates the line of javaScript code, with each instance of x replaced with the numerical input.
		y = Double.parseDouble(""+engine.eval(equation, new SimpleBindings(xmap)));

		return y;
	}

	public void resetEquationParameters()
	{
		setEquation("");
	}

	public Equation(String userInputEquation)
	{
		resetEquationParameters();
		setEquation(userInputEquation);
	}
}

