import java.util.*;
import java.util.ArrayList;
import java.lang.System;
import java.io.IOException;
import java.util.Random;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.FileReader;
/**
 * Tae Myles
 * COMP 3270
 * Algorithm Project: Maximum Sum Contiguous Subvector (MSCS) Problem.
 * 
 * Compile and run with javac
 * IDE used: VSC
 * 
 * References: (1) To find out how to read in file: http://www.java2s.com/Code/Java/File-Input-Output/Readeachlineinacommaseparatedfileintoanarray.htm
 *             (2) To find out how to measure time by nanoseconds: https://www.baeldung.com/java-measure-elapsed-time
 *             (3) To find out how to write out file: https://stackoverflow.com/questions/2885173/how-do-i-create-a-file-and-write-to-it-in-java
 * 
 * Certification statement: I certify that I wrote the code I am submitting. I did not copy whole or parts of it from another student or have another person write the code for me.
 * Any code I am reusing in my program is clearly marked as such with its source clearly identified in comments. 
 */

class AlgorithmProject {
    // Algorithm 1 from pdf
    public int algorithmOne(ArrayList<Integer> X) {
        int P = 0;
        int Q = X.size();
        int maxSoFar = 0;
        for (int L = P; L < Q; L++) {
            for (int U = L; U < Q; U++) {
                int sum = 0;
                for (int I = L; I <= U; I++) {
                    sum = sum + X.get(I);
                     /* sum now contains the sum of X[L..U]  */
                }
                maxSoFar = max(maxSoFar, sum);
            }
        }
        return maxSoFar;
    }
    // Algorithm 2 from pdf
    public int algorithmTwo(ArrayList<Integer> X) {
        int maxSoFar = 0;
        int P = 0;
        int Q = X.size();
        for (int L = P; L < Q; L++) {
            int sum = 0;
            for (int U = L; U < Q; U++) {
                sum = sum + X.get(U);
                /* sum now contains the sum of X[L..U] */
                maxSoFar = max(maxSoFar, sum);
            }
        }
        return maxSoFar;
    }
    // Algorithm 3 from pdf
    public int algorithmThree(ArrayList<Integer> X, int L, int U) {
        if (L > U) {
            return 0; /* zero- element vector */
        }

        if (L == U) {
            return max(0, X.get(L)); /* one-element vector */
        }

        int M = (L + U) / 2; /* A is X[L..M], B is X[M+1..U] */
        /* Find max crossing to left */
        int sum = 0;
        int maxToLeft = 0;
        for (int I = M; I >= L; I--) {
            sum = sum + X.get(I);
            maxToLeft = max(maxToLeft, sum);
        }

        sum = 0;
        int maxToRight = 0;
        for (int I = M + 1; I <= U; I++) {
            sum = sum + X.get(I);
            maxToRight = max(maxToRight, sum);
        }

        int maxCrossing = maxToLeft + maxToRight;
        int maxInA = algorithmThree(X, L, M);
        int maxInB = algorithmThree(X, M + 1, U);
        return max(maxCrossing, maxInA, maxInB);
    }
    // Algorithm 4 from pdf
    public int algorithmFour(ArrayList<Integer> X) {
        int maxSoFar = 0;
        int maxEndingHere = 0;
        int P = 0;
        int Q = X.size();
        for (int I = P; I < Q; I++) {
            maxEndingHere = max(0, maxEndingHere + X.get(I));
            maxSoFar = max(maxSoFar, maxEndingHere);
        }
        return maxSoFar;
    }
    
    // Returns maximum of two inputs, i or j.
    public int max(int i, int j) {
        if (i >= j) {
            return i;
        }
        else {
            return j;
        }
    }

    // Returns maximum of three inputs, i,j or k.
    public int max(int i, int j , int k) {
        if (i >= j && i >= k) {
            return i;
        }
        else if (j >= i && j >= k) {
            return j;
        }
        else {
            return k;
        }
    }

    // Sets random integers in range of min and max. Returns the random number in range of set min and max.
    public static int setRandomInts(int min, int max) {
        Random rNumber = new Random();
        int result = rNumber.nextInt((max - min) + 1) + min;
        return result;
    }

    // Runs empircally calculated complexity on four algorithms from the table
    double retComplexity(int algorithm, int n) {
        double calc = 0;
        if (algorithm == 0) {
            calc = (7/6*n*n*n) + (7*n*n) + (41/6*n) + (6);
        }
        else if (algorithm == 1) {
            calc = (6*n*n) + (8*n) + (5);
        }
		else if (algorithm == 2) {
            if (n == 0) {
                calc = 4;
            }
            calc = ((11*n) + ((12*n)+38)*(n-1));
        }
		else {
			calc = (14*n) + 5;
        }
        calc /= 100;
        return Math.ceil(calc);
    }

    public static void main(String[] args) throws IOException {
        AlgorithmProject algorithm = new AlgorithmProject();
        ArrayList<Integer> val = new ArrayList<Integer>();

        // Start reading in file | Reference(1)
        BufferedReader read = new BufferedReader(new FileReader("phw_input.txt"));
        String line = null;
            while ((line = read.readLine()) != null) {
                String[] value = line.split(",");
                for (String str : value) {
                    int intVal = Integer.parseInt(str);
                    val.add(intVal);
                }
            }
        read.close();

        // End Reading in file

        // Running the values taken from the file and running it on four algorithms
        // Generates random integers ranging -100 to 100.
        System.out.println("algorithm-1: " + algorithm.algorithmOne(val));
        System.out.println("algorithm-2: " + algorithm.algorithmTwo(val));
        System.out.println("algorithm-3: " + algorithm.algorithmThree(val, 0, val.size() - 1));
        System.out.println("algorithm-4: " + algorithm.algorithmFour(val));
        System.out.println(algorithm.algorithmFour(val) + " is the MSCS as determined by each of the algorithms");

        // Creating 19 integer sequences with random ints with setRandomInt() method.
        ArrayList<ArrayList<Integer>> randomInt = new ArrayList<ArrayList<Integer>>();
            for (int i = 0; i < 19; i++) {
                ArrayList<Integer> row = new ArrayList<Integer>();
                for (int j = 0; j < (i + 2) * 5; j++) {
                    int numbers = setRandomInts(-100, 100);
                    row.add(numbers);
                }
                randomInt.add(row);
            }
    
        // Running 4 algorithms using the system clock to measure time (nanoseconds) | Reference(2)
        ArrayList<ArrayList<Double>> result2 = new ArrayList<ArrayList<Double>>();
        int N = 1000;
        double t1 = 0, t2 = 0, t3 = 0, t4 = 0;
        // Begins looping and calcualtes the average, 
        for (int i = 0; i < 19; i++) {
            ArrayList<Double> result = new ArrayList<Double>();
            for (int j = 0; j < 8; j++) {
                for (int k = 0; k < N; k++) {
                    t1 = 0; t2 = 0; t3 = 0; t4 = 0;

                    // Running algorithm 1
                    long start = System.nanoTime();
                    algorithm.algorithmOne(randomInt.get(i));
                    long finish = System.nanoTime();
                    long elapsedTime = finish - start;
                    t1 += (double)elapsedTime;

                    // Running algorithm 2
                    start = System.nanoTime();
                    algorithm.algorithmTwo(randomInt.get(i));
                    finish = System.nanoTime();
                    elapsedTime = finish - start;
                    t2 += (double)elapsedTime;

                    // Running algorithm 3
                    start = System.nanoTime();
                    algorithm.algorithmThree(randomInt.get(i), 0, randomInt.get(i).size() - 1);
                    finish = System.nanoTime();
                    elapsedTime = finish - start;
                    t3 += (double)elapsedTime;
                    
                    // Running algorithm 4
                    start = System.nanoTime();
                    algorithm.algorithmFour(randomInt.get(i));
                    finish = System.nanoTime();
                    elapsedTime = finish - start;
                    t4 += (double)elapsedTime;
                }
                // Getting the average
                t1 /= 100;
                t2 /= 100;
                t3 /= 100;
                t4 /= 100;
                // Adding average time into result ArrayList<Double>
                result.clear();
                result.add(t1);
                result.add(t2);
                result.add(t3);
                result.add(t4);

                // Filling in the rest of the column by running algorithms through the retComplexity() method
                int capacity = (i + 2) * 5;
                for (int n = 0; n <= 3; n++) {
                    result.add(algorithm.retComplexity(n, capacity));
                }
            
            }
            result2.add(result);
        }
        // Start writing out the file | Reference(3)
        PrintWriter writer = new PrintWriter("TaeMyles_phw_output.txt", "UTF-8");
        writer.println("algorithm-1,algorithm-2,algorithm-3,algorithm-4,T1(n),T2(n),T3(n),T4(n)");
        ArrayList<String> writeOut = new ArrayList<>();
        
        for (int i = 0; i < 19; i++) {
            String rowString = "";
            for (int j = 0; j < 8; j++) {
                if (j < 7) {
                    rowString += String.valueOf(result2.get(i).get(j)) + ",";
                }
                else {
                    rowString += String.valueOf(result2.get(i).get(j));
                }
            }
            writeOut.add(rowString);
        }

        for (String str : writeOut) {
            writer.println(str);
        }
        writer.close();
    }
}