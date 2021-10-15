/**
 * @author Kingston
 * This is the TwoVariable class.
 * Has one method of note, extractCoefficients.
 * extractCoefficients is used by TwoVariableSystem in order to form the matrix...
 * ...used to solve the equation.
 */
public class TwoVariable extends GeneralEquation {

	private String equation;

	public String getEquation() 
	{
		return equation;
	}

	public void setEquation(String equation) 
	{
		this.equation = equation;
	}

	//gets the coefficients of x, y and the result.
	//these are used to form the matrices required to solve three variable systems
	//does this by identifying x, y and working backwards to find the term.
	//consequently x and y can be entered in any order
	public double[] extractCoefficients() 
	{
		String equation = getEquation();
		int count;
		String result = "";
		String xCoeff = "";
		String yCoeff = "";

		//identifies the coefficient by the x value
		for(int i = 0; i < equation.length(); i++) 
		{
			//works backwards from x to find the coefficient until it hits + or -
			if( equation.charAt(i) == 'x')
			{
				count = i - 1;
				do 
				{
					xCoeff = equation.charAt(count) + xCoeff;
					if(count > 0) 
					{
						count = count - 1;
					}

				}while(count != 0 && equation.charAt(count) != '+' && equation.charAt(count+1) != '-');
				
				//appends a - if it stops on the -
				if(count == 0 && equation.charAt(count) == '-') 
				{
					xCoeff = equation.charAt(count) + xCoeff;
				}
			}

			//same logic as above but for y
			else if(equation.charAt(i) == 'y') 
			{
				count = i - 1;
				do 
				{
					yCoeff = equation.charAt(count) + yCoeff;
					
					if(count > 0) 
					{
						count = count - 1;
					}

				}while(count != 0 && equation.charAt(count) != '+' && equation.charAt(count+1) != '-');
				
				if(count == 0 && equation.charAt(count) == '-') 
				{
					yCoeff = equation.charAt(count) + yCoeff;
				}
				
			}
			
		}

		boolean cont = false;

		//looks for the = and when the equals is found everything after is stored as the result
		for(int i = 1; i < equation.length(); i++) 
		{
			if(cont || equation.charAt(i-1) == '=') 
			{
				result = result + equation.charAt(i);
				cont = true;
			}
		}

		//returns a double containing all the coefficients
		return new double[] 
				{
						Double.parseDouble(xCoeff),
						Double.parseDouble(yCoeff),
						Double.parseDouble(result)
				};
	}

	public void reset() 
	{
		setEquation("");
	}

	public TwoVariable(String equation)
	{
		reset();
		setEquation(equation);
	}


}
