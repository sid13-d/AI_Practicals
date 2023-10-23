import java.util.*;

public class TicTacToe {
    static int board[] = new int[9];
   static ArrayList<int[]> mat = new ArrayList<>();
   static int[] score = new int[mat.size()];

   //fillMatrix function
    public void fillMatrix() {
        int s=0;
        for(int i=0; i<9; i++) {
            if(board[i]==0){
                board[i] = 2;
               
                mat.add(board);
                printBoard();
                board[i] = 0;
            }else{
                if(board[i] == 2) {
                    board[i] = 5;
                }
            }
           
        }
    }
    //fillBoard function 
    public void fillBoard(int m, int pos) {
        Scanner scan = new Scanner(System.in);
        int i = 0;
        while (i < pos) {
            System.out.println("Enter the position  : ");
            int p = scan.nextInt();
            if (board[p] == 0) {
                board[p] = m;
                i++;
            } else {
                System.out.println("Enter the position again  : ");
                p = scan.nextInt();
            }
        }
        printBoard();
    }
    public void play() {
        System.out.println("Enter whose move it is first  : ");
        Scanner scan = new Scanner(System.in);
        int m = scan.nextInt();
        System.out.println("Enter the num of positions it want to enter  : ");
        int pos = scan.nextInt();
        fillBoard(m, pos);

        //fillBoard for O
        System.out.println("Enter the num of positions it want to enter  : ");
        pos = scan.nextInt();
        fillBoard(2, pos);

        //filling the resultant matrix
        fillMatrix();
        score = new int[mat.size()];
        //Calculating the score
        calculateScore();

    }

    //printBoard function
    public void printBoard() {
        System.out.println();
        for (int i = 0; i < 9; i++) {
            if (board[i] == 0) {
                System.out.print(" - ");
            } else if (board[i] == 1) {
                System.out.print(" X ");
            } else if (board[i] == 2) {
                System.out.print(" O ");
            }

            if (i == 2 || i == 5 || i == 8) {
                System.out.println();
            } else {
                System.out.print("|");
            }
        }
        System.out.println();
    }
    public static void main(String[] args) {
        TicTacToe game = new TicTacToe();
        Arrays.fill(board, 0);
        game.printBoard();

        game.play();
    }

    //calculateScore function
    public void calculateScore() {
        for(int i=0; i<score.length; i++){
            int count=0;
        count += checkForRow();
        score[i] = count;
        }

        System.out.println(score[0]);
        
    }

    //checkForRow function
    public int checkForRow() {
        int flagX=0;
        int flagO=0;
        for (int i = 0; i < 9; i++) {
            if (board[i] == 1) {
                flagX=1;
            } else if (board[i] == 2) {
                flagO = 1;
            }
            if(flagX==1 && flagO==1) {
                return 1;
            }

            if (i == 2 || i == 5 || i == 8) {
                flagO=0;
                flagX=0;
            } 
        }
        return 0;
    }
}
//take input from user board position form a puzzle and generates all possible moves form computer memory