/**
 * @author Kingston
 * This is the TwoVariableSystem class.
 * Has one method of note, solve
 * solve uses extractCoefficients in order to populate a 2x2 and 2x1 matrix,
 * the 2x2 matrix is inverted, multiplied by the 2x1 matrix and scaled by 1/determinant...
 * ...in order to find the matrix containing the solutions.
 * The solutions are then displayed to the user in non-matrix form.
 */
public class TwoVariableSystem 
{
	private TwoVariable firstEquation;
	private TwoVariable secondEquation;
	
	public TwoVariable getFirstEquation() 
	{
		return firstEquation;
	}
	public void setFirstEquation(TwoVariable firstEquation) 
	{
		this.firstEquation = firstEquation;
	}
	public TwoVariable getSecondEquation() 
	{
		return secondEquation;
	}
	public void setSecondEquation(TwoVariable secondEquation) 
	{
		this.secondEquation = secondEquation;
	}
	
	//method to solve the two-variable equation system
	//does this by extracting the coefficients from the two two variable equations...
	//...then forming matrices based on those coefficients, and then manipulating...
	//...those matrices to reach a final result.
	public String solve() 
	{
		//first and second equation
		TwoVariable firstEquation = getFirstEquation();
		TwoVariable secondEquation = getSecondEquation();
		
		//coefficients of first and second equation
		double [] eq1Coeffs = new double [3];
		double [] eq2Coeffs = new double [3];
		
		//matrix containing the coefficients, to be inverted
		Matrix equationMatrix = new Matrix(2,2);
		
		//matrix containing the equation results, multiplied by the above matrix
		Matrix resultMatrix = new Matrix (2,1);
		
		//stores the inverse
		Matrix inverse;
		
		//stores the final result matrix
		Matrix finalResult;
		
		//stores the result in text form
		String resultInTextForm;
		
		//gets the coefficients using .extractCoefficents()
		for(int i = 0; i < 3; i++) 
		{
			eq1Coeffs[i] = firstEquation.extractCoefficients()[i];
			eq2Coeffs[i] = secondEquation.extractCoefficients()[i];
		}
		
		//fills the matrices with the coefficients and results
		equationMatrix.populateMatrix(new double[][]{{eq1Coeffs[0], eq1Coeffs[1]},{ eq2Coeffs[0], eq2Coeffs[1]}});
		resultMatrix.populateMatrix(new double[][] {{eq1Coeffs[2]}, {eq2Coeffs[2]}});
		
		//inverts the matrix
		inverse = equationMatrix.invert();
		
		//multiplies the inverse by the result matrix to find the values of x and y
		finalResult = inverse.multiply(resultMatrix);
		

		//prints the result in text form to be displayed to the user.
		resultInTextForm = "x = " +finalResult.getElement(1,1)
		+"\ny = "+finalResult.getElement(2,1);
		
		return resultInTextForm;
	}
	
	public void reset() 
	{
		setFirstEquation(new TwoVariable(""));
		setSecondEquation(new TwoVariable(""));
	}
	
	
	public TwoVariableSystem(TwoVariable firstEquation, TwoVariable secondEquation)
	{
		reset();
		setFirstEquation(firstEquation);
		setSecondEquation(secondEquation);

	}
	
	

}
