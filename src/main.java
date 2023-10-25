import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class main {
    public static void main(String[] args){
        Scanner scnr = new Scanner(System.in);
        scnr.useLocale(Locale.US);
        System.out.println("How many linear equations do you want to solve for?: ");
        int numEquations = scnr.nextInt();
        scnr.nextLine();
        double[][] matrix = new double[numEquations][numEquations+1];

        System.out.println("How do you want to enter the matrix \n1. Enter matrix manually \n2. Read from file");
        int userChoice = scnr.nextInt();
        scnr.nextLine();


        if(userChoice == 1){
            populateMatrixFromUserInput(matrix, scnr);
        }
        else if(userChoice == 2 ){
            //creating matrix
            boolean notDone = true;
            while(notDone){
                try{populateMatrixFromFile(matrix, scnr);
                    notDone = false;
                }
                catch(FileNotFoundException exception){
                    System.out.println("The file inputted can't be found.");
                    System.out.println(exception);
                }
            }
        }
        else{
            System.out.println("Invalid option");
            return;
        }


        System.out.println("Enter error tolerance: ");
        double precision = scnr.nextDouble();
        scnr.nextLine();



        System.out.println("Enter starting x solutions: ");
        String[] str = scnr.nextLine().split(" ");
        double[] startingX = new double[matrix.length];
        for (int i = 0; i < str.length; i++){
            startingX[i] = Double.parseDouble(str[i]);
        }

        double[] copyStartingX = Arrays.copyOf(startingX, startingX.length);
        if (checkConvergence(matrix)){
            System.out.println("The matrix is strictly diagonally dominant and therefore both the Jacobi and Gauss-Seidel methods will converge to an answer.");
        }
        else{
            System.out.println("The matrix is not strictly diagonally dominant, so the Jacobi and Gauss-Seidel methods might not converge to an answer.");
        }
        System.out.println(checkConvergence(matrix));
        guass_seidel(matrix, startingX, precision);
        jacobi(matrix,copyStartingX, precision);

    }

    public static void populateMatrixFromFile(double[][] matrix, Scanner scnr) throws FileNotFoundException {
        System.out.println("Enter file path: ");
        Scanner fileScnr = new Scanner(new File(scnr.nextLine()));

        for (double[] row : matrix) {
            for (int i = 0; i < row.length; i++) {
                row[i] = fileScnr.nextFloat();
            }
        }
        fileScnr.close();
    }
    public static void populateMatrixFromUserInput(double[][] matrix, Scanner scnr) {
        for (int row = 0; row < matrix.length; row++) {
            System.out.println("Enter row " + row + "'s values: ");
            String[] values = scnr.nextLine().split(" ");
            for (int column = 0; column < matrix[row].length; column++) {
                matrix[row][column] = Double.parseDouble(values[column]);
            }
        }
    }

    public static void printArray (double[] array){

        System.out.print("[");
        for(double num: array){
            System.out.print(num+ " ");

        }
        System.out.print("]T\t\t");

    }


    public static double[] guass_seidel (double[][] matrix, double[] xVal ,double precision){
        System.out.println();
        System.out.println("Guass-Sediel Method: ");
        System.out.println();
        int iteration = 0;
        int numColumns = matrix[0].length-1;

        double L2norm;

       do{
           double previousXVal[] = Arrays.copyOf(xVal, xVal.length);
           int diag = 0;
            for (int i = 0; i < matrix.length; i++){
                double sum = 0;
                double diagNum = matrix[i][diag];
                for (int j = 0; j < matrix[i].length-1; j++){
                    if (j != diag){
                        sum += matrix[i][j] * xVal[j];
                    }
                }

                xVal[i] = (matrix[i][numColumns]-sum)/diagNum;
                diag++;
            }
           double errorSum = 0;
           for (int i = 0; i < xVal.length; i++) {
               errorSum += Math.pow(xVal[i] - previousXVal[i], 2);
           }
           iteration++;
            L2norm = (Math.sqrt(errorSum));
            printArray(xVal);
            System.out.print(L2norm);
            System.out.println();
        }

       while(iteration < 50 && L2norm > precision);

        return xVal;
    }

    public static double[] jacobi(double[][] matrix, double[] xVal ,double precision){
        System.out.println();
        System.out.println("Jacobi Method: ");
        System.out.println();
        int iteration = 0;
        int numColumns = matrix[0].length-1;
        double L2norm;
        double newXVal[] = new double[xVal.length];
        do{
            int diag = 0;
            for (int i = 0; i < matrix.length; i++){
                double sum = 0;
                double diagNum = matrix[i][i];
                for (int j = 0; j < matrix[i].length-1; j++){
                    if (j != diag){
                        sum += matrix[i][j] * xVal[j];
                    }
                }
                newXVal[i] = (matrix[i][numColumns]-sum)/diagNum;
                diag++;
            }
            double errorSum = 0;
            for (int i = 0; i < xVal.length; i++) {
                errorSum += Math.pow(newXVal[i] - xVal[i], 2);
            }
            iteration++;
            L2norm = (Math.sqrt(errorSum));
            printArray(xVal);
            System.out.print(L2norm);
            System.out.println();


            xVal= Arrays.copyOf(newXVal, newXVal.length);
            if (iteration == 50 && L2norm <= precision){
                System.out.println("T");
            }
        }
        while(iteration < 50 && L2norm > precision);

        return newXVal;
    }
    public static boolean checkConvergence(double matrix[][]){
        //checks if the numbers diagonal in the matrix are the largest returns true or false
        int row = 0;
        int column = 0;
        int sum = 0;
        for(int i = 0; i < matrix.length; i++){
            double largest = matrix[row][column];
            for(int j = 0; j < matrix[i].length-1; j++){
                if (j != column){
                    sum += Math.abs(matrix[i][j]);
                }
            }
            if (sum >= largest){
                return false;
            }
            sum = 0;
            row++;
            column++;
        }
        return true;
    }
}
