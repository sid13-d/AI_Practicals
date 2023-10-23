import java.util.ArrayList;


import javax.swing.*;
import java.awt.*;

public class ANN {
    public static void main(String[] args) {
        // For the input data
        ArrayList<double[]> inputData = new ArrayList<>();
        inputData.add(new double[] {1, -2, 1.5, 0});
        inputData.add(new double[] {1, -0.5, -2, 1.5});
        inputData.add(new double[] {0, 1, -1, 1.5});

        // Weight Wo
        //double[] weightO = new double[] {1, -1, 0, 0.5};
        //double[] weightO = new double[] {1, -1, 0, 0.5};
        double[] weightO = new double[] {-1, 0, 4, 3.5 };



        // Desired output for each input
        double[] desiredOutput = new double[] {1, -1, 1};

        // Learning rate (c)
        double c = 1.0;

        // ArrayList to store errors
        ArrayList<Double> errors = new ArrayList<>();

        double e = Double.MAX_VALUE;
        int epochs = 0;

       

     

        

        while (e != 0) {
            e = 0;
           
            for (int i = 0; i < inputData.size(); i++) {
                double[] input = inputData.get(i);
                double net = 0;
                double output = 0;
                for (int j = 0; j < input.length; j++) {
                    net += input[j] * weightO[j];
                }

                output = sgn(net);

                 
                e += desiredOutput[i] - output;

                    for (int j = 0; j < input.length; j++) {
                        weightO[j] += c * (desiredOutput[i] - output) * input[j];
                    }
                

                
                //e += Math.abs(error);
            }

            // Record the error for this epoch
            errors.add(e);
            epochs++;
        }

        // Print errors
        System.out.println("Errors per epoch:");
        for (int i = 0; i < errors.size(); i++) {
            System.out.println("Epoch " + (i + 1) + ": " + errors.get(i));
        }
        System.out.println();
        System.out.println();

        System.out.println("=====================================");
        System.out.println("The Final weights are: ");
        for (int i = 0; i < weightO.length; i++) {
            System.out.print(weightO[i] + " ");
        }
        System.out.println("\n=====================================");
        System.out.println("The epochs: "+ epochs);


       // Print errors as a text-based plot
       System.out.println("Errors per epoch:");
       for (int i = 0; i < errors.size(); i++) {
           System.out.print("Epoch " + (i + 1) + ": ");
           printTextPlot(errors.get(i).intValue());
       }
    }

    private static void printTextPlot(int value) {
        for (int i = 0; i < value; i++) {
            System.out.print("#");
        }
        System.out.println();
    }

    private static double sgn(double net) {
        if (net > 0.0) return 1.0;
       
        return -1.0;
    }
}
