/**
 * 
 * @author Kingston
 * This is the Fraction class.
 * This is primarily used for the cubic methods.
 * The user input is taken in in quotient form, as the methods rely on a quotient solution.
 * The recursive Euclidean Algorithm, from the Decision module of Further Mathematics...
 * ...is used to simplify the quotient. 
 * 
 */

public class Fraction
{
	private int numerator;
	private int denominator;

	public int getNumerator()
	{
		return numerator;
	}
	public void setNumerator(int numerator)
	{
		this.numerator = numerator;
	}
	public int getDenominator()
	{
		return denominator;
	}
	public void setDenominator(int denominator)
	{
		this.denominator = denominator;
	}

	public void reset()
	{
		setNumerator(0);
		setDenominator(0);
	}

	//simply displays the fraction to the user.
	public String displayFraction()
	{
		return getNumerator() + "/" + getDenominator();
	}

	//determines the larger number out of the numerator and the denominator.
	//Then uses the Euclidean algorithm from Further Pure Mathematics 2 to simplify the fraction
	//The result is stored as gcf and a new fraction is formed by dividing both numbers by gcf
	//The new fraction is returned
	public Fraction simplify()
	{
		int numerator = getNumerator();
		int denominator = getDenominator();
		int gcf;

		//identifies the bigger and smaller number between the numerator and the denominator
		int biggerNum =   (numerator > denominator) ? numerator : denominator ;
		int smallerNum =  (numerator > denominator) ? denominator : numerator ;

		//returns 1/1 if the numerator = denominator
		if(biggerNum == smallerNum)  return new Fraction(1,1);

		//finds the greatest common factor using the private Euclidean Algorithm method
		gcf = performEuclideanAlgorithm(biggerNum,  smallerNum);

		
		//returns a new fraction, divided by the greatest common factor
		return new Fraction (numerator/gcf, denominator/gcf);
	}

	//if a/b is the fraction, returns -b/a
	public Fraction findNegativeReciprocal()
	{
		int denominator = getNumerator();
		int numerator = -1 * getDenominator();
		return new Fraction (numerator, denominator);
	}

	//converts the fraction to a decimal
	public double toDecimal()
	{
		return getNumerator()/getDenominator();
	}

	//performs the Euclidean algorithm
	//recursive implementation
	private int performEuclideanAlgorithm(int dividend, int divisor)
	{
		//this is the base case, the largest number which leaves remainder 0
		//(i.e the greatest common factor)
		if(divisor == 0) return dividend;
		
		//runs when the GCF has not yet been found
		else 
		{
			//a temporary variable to hold the old divisor, which is the new dividend
			int temp = divisor;
			
			//the new divisor is the remainder when the dividend is divided by the divisor
			divisor = dividend % divisor;
			
			//the new dividend is the old divisor
			dividend = temp;

			//the new dividend and divisor are used as the parameters for the recursive call
			return performEuclideanAlgorithm(dividend, divisor);
		}
	}

	public Fraction(int numerator, int denominator)
	{
		reset();
		setNumerator(numerator);
		setDenominator(denominator);
	}

}

