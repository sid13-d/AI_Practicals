import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TicTacToe2 {
    private int[] matrix;
    private int xCount;
    private int yCount;

    public TicTacToe2() {
        matrix = new int[9];
        xCount = 0;
        yCount = 0;
        input();
        if (isValid()) {
            System.out.println("The value is :: " + calculate());
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter the target variable :: ");
            int target = scanner.nextInt();
            List<int[]> moveMatrix = generateMove(target);
            calculateScore(target, moveMatrix);
            view();
        }
    }

    public void input() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of X in the current board position :: ");
        int x = scanner.nextInt();
        System.out.print("Enter the number of O in the current board position :: ");
        int y = scanner.nextInt();

        for (int i = 0; i < x; i++) {
            System.out.print("Enter the position for X :: ");
            int pos = scanner.nextInt();
            matrix[pos] = 1;
            xCount++;
        }

        for (int i = 0; i < y; i++) {
            System.out.print("Enter the position for O :: ");
            int pos = scanner.nextInt();
            if (matrix[pos] != 0) {
                System.out.println("Already occupied by X");
                i--;
                continue;
            }
            matrix[pos] = 2;
            yCount++;
        }
        System.out.println("Matrix Representation of the current position ::");
        System.out.println(Arrays.toString(matrix));
    }

    public boolean isValid() {
        if (Math.abs(xCount - yCount) >= 2) {
            System.out.println("The board position is not valid.");
            return false;
        }
        // Implement the winning condition check here if needed
        // For example, check for rows, columns, and diagonals
        // Return false if a winning condition is met
        System.out.println("This is a valid board position.");
        return true;
    }

    public int calculate() {
        int[] arr = new int[9];
        System.arraycopy(matrix, 0, arr, 0, 9);
        int count = 0;
        int sum = 0;
        int base = 1;
        for (int i = arr.length - 1; i >= 0; i--) {
            sum += arr[i] * base;
            count++;
            base *= 3;
        }
        return sum;
    }

    public void view() {
        System.out.println("GUI not implemented in Java.");
    }

    public List<int[]> generateMove(int target) {
        List<int[]> moveMatrix = new ArrayList<>();
        int i = 0;
        int m = 0;
        int emptyCount = countEmptyCells();
        while (i < emptyCount) {
            int[] temp = Arrays.copyOf(matrix, matrix.length);
            if (temp[m] == 0) {
                temp[m] = target;
                moveMatrix.add(temp);
                i++;
            }
            m++;
        }
        System.out.println("All the possible moves for the current matrix position for target " + target + " is ::");
        for (int[] move : moveMatrix) {
            System.out.println(Arrays.toString(move));
        }
        return moveMatrix;
    }

    public void calculateScore(int target, List<int[]> moveMatrix) {
        int emptyCount = countEmptyCells();
        int[] score = new int[emptyCount];
        int[][][] reshapedMatrix = new int[emptyCount][3][3];

        for (int i = 0; i < emptyCount; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    reshapedMatrix[i][j][k] = moveMatrix.get(i)[j * 3 + k];
                }
            }
        }

        for (int i = 0; i < emptyCount; i++) {
            for (int j = 0; j < 3; j++) {
                if (Arrays.stream(reshapedMatrix[i][j]).anyMatch(value -> value == target)) {
                    score[i]++;
                    break;
                }
            }
        }

        for (int i = 0; i < emptyCount; i++) {
            for (int j = 0; j < 3; j++) {
                int[] column = new int[3];
                for (int k = 0; k < 3; k++) {
                    column[k] = reshapedMatrix[i][k][j];
                }
                if (Arrays.stream(column).anyMatch(value -> value == target)) {
                    score[i]++;
                    break;
                }
            }
        }

        int[][] diagonalElements = new int[emptyCount][3];
        int[][] oppositeDiagonalElements = new int[emptyCount][3];

        for (int i = 0; i < emptyCount; i++) {
            for (int j = 0; j < 3; j++) {
                diagonalElements[i][j] = reshapedMatrix[i][j][j];
                oppositeDiagonalElements[i][j] = reshapedMatrix[i][j][2 - j];
            }
        }

        for (int i = 0; i < emptyCount; i++) {
            if (Arrays.stream(diagonalElements[i]).anyMatch(value -> value == target)) {
                score[i]++;
            }
            if (Arrays.stream(oppositeDiagonalElements[i]).anyMatch(value -> value == target)) {
                score[i]++;
            }
        }

        System.out.println("Scores: " + Arrays.toString(score));
    }

    private int countEmptyCells() {
        int count = 0;
        for (int value : matrix) {
            if (value == 0) {
                count++;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        new TicTacToe2();
    }
}
