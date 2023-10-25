import java.util.Arrays;

public class main {
    public static void main(String[] args){
        double[][] matrix = {{5, -1, 0, 7},
                             {-1, 3, -1, 4},
                             {0, -1, 2 , 5}};
        System.out.println(checkConvergence(matrix));
        double[] initX = {0,0,0};
        guass_seidel(matrix, initX, 0);
        initX = new double[]{0, 0, 0};
        jacobi(matrix,initX, 0);

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
        System.out.println("Guass Sediel Method: ");
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
