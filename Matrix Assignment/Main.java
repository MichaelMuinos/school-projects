import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
	
	private static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) throws IOException {
		Matrix m1;
		Matrix m2;
		
		System.out.println("Choose an option:");
		System.out.println("1. Default Matrix Size and Values");
		System.out.println("2. Custom Matrix Size and Random Values");
		int choice = sc.nextInt();
		if(choice == 1) {
			m1 = new Matrix(5);
			m1.fillMatrix(new File("src/MatrixOneElements"));
			
			m2 = new Matrix(5);
			m2.fillMatrix(new File("src/MatrixTwoElements"));
		} else {
			System.out.println("Enter a size for the matrix (n x n):");
			int size = sc.nextInt();
			
			m1 = new Matrix(size);
			m1.fillMatrix();
			
			m2 = new Matrix(size);
			m2.fillMatrix();
		}
		
		float start;
		float end;
		
		boolean exit = false;
			while(!exit) {
			System.out.println("Select an option:");
			System.out.println("1. M3 = M1 + M2");
			System.out.println("2. M4 = M1 - M2");
			System.out.println("3. M5 = M1 * M2");
			System.out.println("4. Exit");
			
			int num = sc.nextInt();
			switch(num) {
			case 1:
				start = System.currentTimeMillis();
				Matrix resultAdd = m1.add(m2);
				end = System.currentTimeMillis();
				System.out.println("Execution of add: " + (end - start) + " ns");
				m1.printMatrix("M1");
				m2.printMatrix("M2");
				resultAdd.printMatrix("M3");
				break;
			case 2:
				start = System.currentTimeMillis();
				Matrix resultSub = m1.subtract(m2);
				end = System.currentTimeMillis();
				System.out.println("Execution of add: " + (end - start) + " ns");
				m1.printMatrix("M1");
				m2.printMatrix("M2");
				resultSub.printMatrix("M4");
				break;
			case 3:
				start = System.currentTimeMillis();
				Matrix resultMult = m1.multiply(m2);
				end = System.currentTimeMillis();
				System.out.println("Execution of add: " + (end - start) + " ns");
				m1.printMatrix("M1");
				m2.printMatrix("M2");
				resultMult.printMatrix("M5");
				break;
			case 4:
				exit = true;
				break;
			default:
				System.out.println("Enter a number between 1 - 4!\n");
				break;
			}
		}
	}
}
