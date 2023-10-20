public class main {
    public static void main(String[] args){
        double[][] matrix = {{5, -1, 0, 7},
                          {-1, 3, -1, 4},
                          {0, -1, 2 ,5}};
        System.out.println(checkConvergence(matrix));

    }

    public static double[] guass_seidel (double[][] matrix, double[] xVal ,double precision){
        int iteration = 0;
        double sum = 0;
        int diag = 0;
        int numColumns = matrix[0].length-1;
        int error = 100;
        while(iteration < 50 || error <= precision){
            for (int i = 0; i < matrix.length; i++){
                double diagNum = matrix[i][diag];
                for (int j = 0; j < matrix[i].length; j++){
                    if (j != diag){
                        sum += matrix[i][numColumns]-matrix[i][j] * xVal[i];
                    }
                }
                xVal[i] = sum/diagNum;

                diag++;
            }
            iteration++;
        }
        return xVal;
    }

    public static double[] jacobi(double[][] matrix, double[] xVal ,double precision){
        int iteration = 0;
        double sum = 0;
        int diag = 0;
        int numColumns = matrix[0].length-1;
        int error = 100;
        double newXVal[] = xVal;
        while(iteration < 50 || error <= precision){
            for (int i = 0; i < matrix.length; i++){
                double diagNum = matrix[i][diag];
                for (int j = 0; j < matrix[i].length; j++){
                    if (j != diag){
                        sum += matrix[i][numColumns]-matrix[i][j] * xVal[i];
                    }
                }
                newXVal[i] = sum/diagNum;

                diag++;
            }
            iteration++;
        }
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
