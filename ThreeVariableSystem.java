/**
 * @author Kingston
 * This is the ThreeVariableSystem class.
 * Has one method of note, solve
 * solve uses extractCoefficients in order to populate a 3x3 and 3x1 matrix,
 * the 3x3 matrix is inverted, multiplied by the 3x1 matrix and scaled by 1/determinant...
 * ...in order to find the matrix containing the solutions.
 * The solutions are then displayed to the user in non-matrix form.
 */
public class ThreeVariableSystem 
{
	private ThreeVariable firstEquation;
	private ThreeVariable secondEquation;
	private ThreeVariable thirdEquation;
	
	public ThreeVariable getFirstEquation() 
	{
		return firstEquation;
	}
	public void setFirstEquation(ThreeVariable firstEquation) 
	{
		this.firstEquation = firstEquation;
	}
	public ThreeVariable getSecondEquation() 
	{
		return secondEquation;
	}
	public void setSecondEquation(ThreeVariable secondEquation) 
	{
		this.secondEquation = secondEquation;
	}
	
	public ThreeVariable getThirdEquation() 
	{
		return thirdEquation;
	}
	public void setThirdEquation(ThreeVariable thirdEquation) 
	{
		this.thirdEquation = thirdEquation;
	}
	
	//method to solve the three-variable equation system
	//does this by extracting the coefficients from the three three variable equations...
	//...then forming matrices based on those coefficients, and then manipulating...
	//...those matrices to reach a final result.
	public String solve() 
	{
		//the three equations
		ThreeVariable firstEquation = getFirstEquation();
		ThreeVariable secondEquation = getSecondEquation();
		ThreeVariable thirdEquation = getThirdEquation();
		
		//the three equation coefficients are stored in these
		double [] eq1Coeffs = new double [4];
		double [] eq2Coeffs = new double [4];
		double [] eq3Coeffs = new double [4];
		
		//the 3x3 matrix of the 3 equations
		Matrix equationMatrix = new Matrix(3,3);
		
		//the 3x1 matrix of the results
		Matrix resultMatrix = new Matrix (3,1);
		
		//the inverse of the 3x3 matrix
		Matrix inverse;
		
		//the final result 3x1 matrix
		Matrix finalResult;
		
		//the final result converted to text so the user can read
		String resultInTextForm;
		
		//gets the coefficients for all 3 equations
		for(int i = 0; i < 4; i++) 
		{
			eq1Coeffs[i] = firstEquation.extractCoefficients()[i];
			eq2Coeffs[i] = secondEquation.extractCoefficients()[i];
			eq3Coeffs[i] = thirdEquation.extractCoefficients()[i];
		}
		
		//fills the equation matrix and result matrix
		equationMatrix.populateMatrix(new double[][]{{eq1Coeffs[0], eq1Coeffs[1], eq1Coeffs[2]},
													{ eq2Coeffs[0], eq2Coeffs[1], eq2Coeffs[2]}, 
													{eq3Coeffs[0], eq3Coeffs[1], eq3Coeffs[2]}});
		resultMatrix.populateMatrix(new double[][] {{eq1Coeffs[3]}, {eq2Coeffs[3]}, {eq3Coeffs[3]}});
		
		//inverts the equation matrix.
		inverse = equationMatrix.invert();
		
		//multiplies the inverted matrix by the result matrix
		finalResult = inverse.multiply(resultMatrix);
		
		//constructs the result in text form to display to the user
		resultInTextForm = "x = " +finalResult.getElement(1,1)
		+"\ny = "+finalResult.getElement(2,1)
		+"\nz = "+finalResult.getElement(3,1);
		
		return resultInTextForm;
	}
	
	public void reset() 
	{
		setFirstEquation(new ThreeVariable(""));
		setSecondEquation(new ThreeVariable(""));
		setThirdEquation(new ThreeVariable(""));
	}
	
	public ThreeVariableSystem(ThreeVariable firstEquation, ThreeVariable secondEquation, ThreeVariable thirdEquation)
	{
		reset();
		setFirstEquation(firstEquation);
		setSecondEquation(secondEquation);
		setThirdEquation(thirdEquation);

	}
	

}
