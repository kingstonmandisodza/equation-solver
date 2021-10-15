/**
 * 
 * @author Kingston
 * This class is for Matrices.
 * Matrices are lists of elements which can be used for calculations.
 * In this project, I have used matrices to solve 2 and 3 variable linear equations.
 * This class is used by TwoVariableSystem and ThreeVariableSystem to solve...
 * ...the equations specified by the TwoVariable and ThreeVariable objects.
 * Methods include invert, which finds the "inverse matrix" which...
 * ...when multiplied by the "solution matrix", provides the solutions to a system of equations.
 * multiply is also a method, which can take either a scalar or another matrix as a parameter.
 * multiply is capable of using matrix multiplication to multiply one matrix by another.
 * getDeterminant is also a method, this finds a property of a matrix which is used to find the inverse...
 * of that matrix.
 * Matrices, Matrix multiplication and solving linear systems of equations using matrices is...
 * ...part of the A Level Further Mathematics course.
 */
public class Matrix 
{
	private int rows;
	private int columns;
	private double[][] contents;

	public int getRows() 
	{
		return rows;
	}

	public void setRows(int rows) 
	{
		this.rows = rows;
	}

	public double[][] getContents() 
	{
		return contents;
	}

	public void setContents(double contents[][]) 
	{
		this.contents = contents;
	}

	public int getColumns() 
	{
		return columns;
	}

	public void setColumns(int columns) 
	{
		this.columns = columns;
	}

	public void reset() 
	{
		setRows(0);
		setColumns(0);
		setContents(new double [0][0]);
	}

	//takes in the elements of the matrix in double form
	//allows you to change the entire contents of a matrix at once.
	public void populateMatrix(double[][] matrixElements) 
	{
		setContents(matrixElements);
	}

	//replaces an individual element of a matrix
	public void replaceElement(int elementRow, int elementColumn, double replacement) 
	{
		double[][] contents = getContents();
		contents[elementRow][elementColumn] = replacement;
		setContents(contents);	
	}

	//replaces a row of the matrix
	public void replaceRow(int row, double[] replacement) 
	{
		double[][] contents = getContents();
		for(int i = 0; i < replacement.length; i++) 
		{
			contents[row][i] = replacement[i];
		}
		setContents(contents);	
	}

	//replaces a column of the matrix
	public void replaceColumn(int column, double[] replacement) 
	{
		double[][] contents = getContents();
		for(int i = 0; i < replacement.length; i++) 
		{
			contents[i][column] = replacement[i];
		}
		setContents(contents);	
	}


	//adds two matrices together
	public Matrix add(Matrix secondMatrix) 
	{
		//gets the row and column length, so they can be checked to ensure they're the same.
		final int RESULT_ROW_LENGTH = getRows();
		int RESULT_COLUMN_LENGTH = getColumns();
		
		//stores the result of the addition as an array
		double[][] resultArray = new double[RESULT_ROW_LENGTH][RESULT_COLUMN_LENGTH];
		
		//gets the array contents of the first matrix
		double[][] matrixArray1 = getContents();
		
		//gets the array contents of the second matrix
		double[][] matrixArray2 = secondMatrix.getContents();
		Matrix result = new Matrix(RESULT_ROW_LENGTH, RESULT_COLUMN_LENGTH);

		//runs if the lengths aren't the same
		if(secondMatrix.getRows() != RESULT_ROW_LENGTH || 
				secondMatrix.getColumns() != RESULT_COLUMN_LENGTH) 
		{
			return new Matrix(0,0); 
		}

		//runs if the lengths are the same. Adds each element of the two matrices.
		else 
		{
			for(int i = 0; i < RESULT_ROW_LENGTH; i++) 
			{
				for(int j = 0; j < RESULT_COLUMN_LENGTH; j++) 
				{
					resultArray[i][j] = matrixArray1[i][j] + matrixArray2[i][j];
				}
			}

			//changes the contents of the matrix
			result.populateMatrix(resultArray);

			return result;
		}

	}

	//same logic as add, only a subtraction
	public Matrix subtract(Matrix secondMatrix) 
	{
		final int RESULT_ROW_LENGTH = getRows();
		int RESULT_COLUMN_LENGTH = getColumns();
		double[][] resultArray = new double[RESULT_ROW_LENGTH][RESULT_COLUMN_LENGTH];
		double[][] matrixArray1 = getContents();
		double[][] matrixArray2 = secondMatrix.getContents();

		Matrix result = new Matrix(RESULT_ROW_LENGTH, RESULT_COLUMN_LENGTH);

		if(secondMatrix.getRows() != RESULT_ROW_LENGTH) 
		{
			return new Matrix(0,0); 
		}

		else if(secondMatrix.getColumns() != RESULT_COLUMN_LENGTH) 
		{
			return new Matrix(0,0); 
		}

		else 
		{
			for(int i = 0; i < RESULT_ROW_LENGTH; i++) 
			{
				for(int j = 0; j < RESULT_COLUMN_LENGTH; j++) 
				{
					resultArray[i][j] = matrixArray1[i][j] - matrixArray2[i][j];
				}
			}

			result.populateMatrix(resultArray);

			return result;
		}
	}

	//The first of two Matrix.multiply(). This one takes in another matrix as a parameter.
	//Multiplying matrices is a process covered in Further Pure 1 Mathematics
	//To multiply a matrix, every element in each row is multiplied by the corresponding element in each column...
	//..in a certain order.
	//Another way of thinking about it is that every element in the result matrix represents the dot product of each
	//...row and column
	//the dot product is akin to multiplying two vectors.
	public Matrix multiply(Matrix secondMatrix) 
	{
		final int RESULT_ROW_LENGTH = getRows();
		int RESULT_COLUMN_LENGTH = secondMatrix.getColumns();
		
		//double array to store both matrices and the result
		double[][] resultArray = new double[RESULT_ROW_LENGTH][RESULT_COLUMN_LENGTH];
		double[][] matrixArray1 = getContents();
		double[][] matrixArray2 = secondMatrix.getContents();

		//matrix to store the result
		Matrix result = new Matrix(RESULT_ROW_LENGTH, RESULT_COLUMN_LENGTH);

		//matrices matrices can only be multiplied if in the form m x n * k x n
		if(getColumns() != RESULT_ROW_LENGTH) 
		{
			return new Matrix(0,0); 
		}

		else 
		{
			//this multiplies each element of a row in the first matrix by the corresponding column in the second matrix
			//first loop ensures every row is run.
			//second loop ensures every column is run for every row
			//third loop ensures the element in every row is multiplied by the corresponding element in every column
			for(int i = 0; i < RESULT_ROW_LENGTH; i++) 
			{
				for(int j = 0; j < RESULT_COLUMN_LENGTH; j++) 
				{
					for(int k = 0; k < RESULT_ROW_LENGTH; k++) 
					{
						resultArray[i][j] = resultArray[i][j] +( matrixArray1[i][k]
								* matrixArray2[k][j]);
					}
				}
			}

			result.setContents(resultArray);

			return result;
		}
	}

	//second multiply method, this one scales a matrix.
	public Matrix multiply(double scalar) 
	{
		final int ROWS = getRows();
		final int COLUMNS = getColumns();
		double[][] contents = getContents();
		Matrix returnMatrix = new Matrix (ROWS,COLUMNS);

		//multiplies every element by the scalar
		for(int i = 0; i < ROWS; i++) 
		{
			for(int j = 0; j < COLUMNS; j++) 
			{
				contents[i][j] = contents[i][j] * scalar;
			}
		}

		returnMatrix.populateMatrix(contents);

		return returnMatrix;
	}

	public double getElement(int row, int column) 
	{
		return getContents()[row-1][column-1];
	}

	//finds the determinant of a matrix
	//the determinant is the scale factor of the area of the result of a linear transformation.
	//a property of a matrix used when finding the inverse of a matrix.
	public double findDeterminant() 
	{
		final int ROWS = getRows();
		final int COLUMNS = getColumns();
		double determinant = 0;

		//for a 2x2 matrix A, detA = ad - bc
		if(ROWS == 2 && COLUMNS == 2) 
		{
			determinant = getElement(1,1) * getElement(2,2) - ( getElement(1,2) * getElement(2,1) );
		}
		
		//for a 3x3 matrix B, detB = minor(top left element) - minor(top middle element) + minor(top right element)
		else if(ROWS == 3 && COLUMNS == 3) 
		{
			determinant =
					+ ( (getElement(1,1) ) * ( (getElement(2,2) * getElement(3,3) ) - ( getElement(3,2) * getElement(2,3) ) ) ) 
					+ ( -1*(getElement(1,2) ) * ( (getElement(2,1) * getElement(3,3) ) - ( getElement(2,3) * getElement(3,1) ) ) )
					+ ( (getElement(1,3)) * ( (getElement(2,1) * getElement(3,2) ) - ( getElement(2,2) * getElement(3,1) ) ) );
		}

		return determinant;
	}

	//the inverse of a matrix is the transformation, when applied directly after the original transformation, 
	//returns a point on a plane to its starting point.
	//the inverse can be found for a 2x2 matrix A by using 1/detA * (d -b)
	//                                                              (-c a)
	//the inverse can be found for a 3x3 matrix by using a method including the matrix of minors and cofactors	
	public Matrix invert() 
	{
		final int ROWS = getRows();
		final int COLUMNS = getColumns();
		double determinant = findDeterminant();
		Matrix cofactors = new Matrix(3,3);
		Matrix inverse = new Matrix(2,2);

		//elements of the matrix of cofactors
		double e11, e12, e13, e21, e22, e23, e31, e32, e33;

		if(ROWS == COLUMNS) 
		{
			if(ROWS == 3) 
			{
				//finds every element of the matrix of cofactors
				//eN1N2 is such that e = element, N1 = row, N2 = column
				e11 = ( getElement(2,2) * getElement(3,3) ) - ( getElement(3,2) * getElement(2,3) );
				e12 = -( ( getElement(2,1) * getElement(3,3) ) - ( getElement(2,3) * getElement(3,1) ) );
				e13 = ( getElement(2,1) * getElement(3,2) ) - ( getElement(2,2) * getElement(3,1) );
				e21 = -( ( getElement(1,2) * getElement(3,3) ) - ( getElement(3,2) * getElement(1,3) ) );
				e22 = ( getElement(1,1) * getElement(3,3) ) - ( getElement(1,3) * getElement(3,1) );
				e23 = -( ( getElement(1,1) * getElement(3,2) ) - ( getElement(1,2) * getElement(3,1) ) );
				e31 = ( getElement(1,2) * getElement(2,3) ) - ( getElement(1,3) * getElement(2,2) );
				e32 = -( ( getElement(1,1) * getElement(2,3) ) - ( getElement(1,3) * getElement(2,1) ) );
				e33 = ( getElement(1,1) * getElement(2,2) ) - ( getElement(1,2) * getElement(2,1) );

				//makes the matrix of cofactors as a new 3x3 matrix
				cofactors.populateMatrix(new double[][] {{e11,e12,e13},{e21,e22,e23},{e31,e32,e33}});
				
				//transposes this matrix and multiplies by the determinant to find the inverse
				cofactors = cofactors.transpose().multiply(1/determinant);

				return cofactors;
			}
			else if (ROWS == 2) 
			{
				//carries out the aforementioned method
				inverse.populateMatrix(new double[][] {{getElement(2,2),-1 * getElement(1,2)},{-1 * getElement(2,1), getElement(1,1)}});
				inverse.multiply(1/determinant);
				return inverse;
			}
			else 
			{
				//returns the original matrix if the inverse cannot be found
				return new Matrix(ROWS, COLUMNS);
			}
		}
		else 
		{
			return new Matrix(ROWS, COLUMNS);
		}

	}

	//essentially "tips" a matrix onto its side.
	//required for finding the inverse
	public Matrix transpose() 
	{
		final int ROWS = getRows();
		final int COLUMNS = getColumns();
		double[][] contents = getContents();
		double[][] newContents = new double[COLUMNS][ROWS];
		Matrix transposed = new Matrix(COLUMNS, ROWS);

		for(int i = 0; i < COLUMNS; i++) 
		{
			for(int j = 0; j < ROWS; j++) 
			{
				newContents[i][j] = contents[j][i];
			}
		}

		transposed.populateMatrix(newContents);

		return transposed;
	}

	//displays the matrix to the user, for testing purposes. The user will never see the matrix
	public String displayMatrix() 
	{
		final int COLUMNS = getColumns();
		final int ROWS = getRows();
		final double[][] contents = getContents();
		String output = "";

		for(int i = 0; i < ROWS; i++) 
		{
			output = output + "|";

			for(int j = 0; j < COLUMNS; j++) 
			{
				output = output + "   "+ contents[i][j] + "   ";
			}

			output = output+"|\n";
		}

		return output;
	}
	

	public Matrix(int rows, int columns)
	{
		reset();
		setRows(rows);
		setColumns(columns);
		setContents(new double[rows][columns]);
	}

}
