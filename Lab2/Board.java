import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;
class Box8{
    int[][] box = new int[3][3];
    int gap;
    int distance;
    Box8(int[][] box, int distance){
        this.box = box;
        this.distance = distance;
    }
}

public class Board {
    //adding the 2d array from storage into a PriorityQueue which will have the object of Box8
    //the Box8 will have the 2d array and the distance
    //the distance will be calculated by the calculateEuclidean method
    //the calculateEuclidean method will calculate the distance between the 2d array and the goal state
    //the goal state will be the 2d array which will be the final state

    static PriorityQueue<Box8> pq = new PriorityQueue<>((a,b) -> a.distance - b.distance);
    static List<int[][]> closedList = new ArrayList<int[][]>();
    public static List<int[][]> storage = new ArrayList<>();

    static int board[][] = new int[3][3];
    static int goalBoard[][] = new int[3][3];
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int board[][] = new int[3][3];
        int tempBoard[] = new int[9];

        System.out.println("-----------------The Board Game-----------------------");

        System.out.println("Please enter the position where you want the gap to be: ");
        int gap = scan.nextInt();


        //iterate of the map and fill the board with the values from the map and the gap
        //print the board
        System.out.println("Enter the next value for the position : ");
         for(int i=0; i<9; i++) {
            
            if(i == gap){
                System.out.print(i+" : 0");
                tempBoard[i] = 0;

            }else {
                System.out.print(i+" : ");
                tempBoard[i] = scan.nextInt();
            }
         }

         //filling the board with the values from the tempBoard
            int k = 0;
            for(int i=0; i<3; i++) {
                for(int j=0; j<3; j++) {
                    board[i][j] = tempBoard[k];
                    k++;
                }
            }
            System.out.println("------------------------------------The Original Board Position is ------------------------------------");

       printBoard(board);

       System.out.println("------------------------------------The Goal Board Position is ------------------------------------");
        System.out.println("Please enter the position where you want the gap to be: ");
        gap = scan.nextInt();

        //iterate of the map and fill the board with the values from the map and the gap
        //print the board
        System.out.println("Enter the next value for the position : ");
         for(int i=0; i<9; i++) {
            
            if(i == gap){
                System.out.print(i+" : 0");
                tempBoard[i] = 0;

            }else {
                System.out.print(i+" : ");
                tempBoard[i] = scan.nextInt();
            }
         }

         //filling the board with the values from the tempBoard
             k = 0;
            for(int i=0; i<3; i++) {
                for(int j=0; j<3; j++) {
                    goalBoard[i][j] = tempBoard[k];
                    k++;
                }
            }

            printBoard(goalBoard);

        
       
       System.out.println("------------------------------------The Possible Moves are ------------------------------------");
       printAllPossibleMoves(board, gap);

       System.out.println("-------------------------------------Calculating the Euclidean Distance----------------------------");
      int cnt = calculateEuclidean(goalBoard);
      printBoard(storage.get(cnt));

      System.out.println("------------------------------------Printing the Priority Queue------------------------------------");
        printPriorityQueue();


        System.out.println("~~~~~~~~~~~~~~~~Making the Calculations for the Closed List~~~~~~~~~~~~~~~");
        //making the calculations for the closed list
        int chk = 0;
        while(chk != 1) {
            chk = removeBoard();
        }
        if(chk == 1) {
            System.out.println("The board is solved");
        }

        System.out.println("=================================================================================================");

        System.out.println();
        System.out.println("======================================The Final Board is=========================================");
        System.out.println("The closed list is : ");
        for(int[][] a: closedList) {
            printBoard(a);
        }

        System.out.println("The final board is : ");

    }

    private static int calculateEuclidean(int[][] board) {
       double minDist = Integer.MAX_VALUE;
       int count=-1;
            for(int a[][]: storage) {
                //check if the board already exists in the priority queue
                if(checkIfExistInPQ(a)){
                    continue;
                }

                //check if the board already exists in the closed list
                if(checkIfExistInClosedList(a)) {
                    continue;
                }
                
                    if(calculateDistance(board, a) != 0){
                    if(calculateDistance(board, a)< minDist) {
                        
                        minDist = calculateDistance(board, a);
                      
                    }
                    pq.add(new Box8(a, (int)calculateDistance(board, a)));
                    }
                     count = count+1;
            }
            System.out.println("The minimum distance is : "+minDist + " of the board move : "+count);
            return count;
    }

    private static boolean checkIfExistInClosedList(int[][] a) {
        //check if the board already exists in the closed list
        for(int[][] b: closedList) {
            if(Arrays.deepEquals(b, a)) {
                return true;
            }
        }
        return false;
    }

    private static boolean checkIfExistInPQ(int[][] a) {
        //check if the board already exists in the priority queue
        for(Box8 b: pq) {
            if(Arrays.deepEquals(b.box, a)) {
                return true;
            }
        }
        return false;
    }

    private static double calculateDistance(int[][] board, int[][] a) {
        //calulating the euclidean distance and returing it
        double dist = 0;
        for(int i=0; i<3; i++) {
            for(int j=0; j<3; j++) {
                dist += Math.pow((board[i][j] - a[i][j]), 2);
            }
        }
        dist = Math.sqrt(dist);
        // System.out.println("The distance is : "+dist);
        return dist;
    }

    private static void printAllPossibleMoves(int[][] board, int gap) {
        //print all the possible moves
        //print the board
        //int possiblevalues[][][] = new int[4][3][3];
        ArrayList<int[][]> list = new ArrayList<>();
        

        //find the position of the gap
        int gapRow = 0;
        int gapCol = 0;
        int k = 0;
        for(int i=0; i<3; i++) {
            for(int j=0; j<3; j++) {
                if(board[i][j] == 0) {
                    gapRow = i;
                    gapCol = j;
                    break;
                }
            }
        }

        
        //find the possible moves but can have only adjecent element
        //if the gap is in the first row
        
        for(int i=0; i<board.length; i++) {
            for(int j=0; j<board[0].length; j++) {
                int arr[][] = new int[3][3];
                if(board[i][j] == 0)
                    continue;
                if(gapRow == i ) {
                    if(j-gapCol <= 1){
                         int temp = board[gapRow][j];
                    board[gapRow][j] = board[gapRow][gapCol];
                    board[gapRow][gapCol] = temp;
                    
                    printBoardMove(board);
                    list.add(copyBoard(board));
                    
                    //storage.add(board);
                    temp = board[gapRow][j];
                    board[gapRow][j] = board[gapRow][gapCol];
                    board[gapRow][gapCol] = temp;
                    }
                   
                 
                
                } else if(gapCol == j) {
                    if(i-gapRow <= 1){
                        int temp = board[i][gapCol];
                    board[i][gapCol] = board[gapRow][gapCol];
                    board[gapRow][gapCol] = temp;
                    printBoardMove(board);
                    list.add(copyBoard(board));
                   
                    //storage.add(board);
                    temp = board[i][gapCol];
                    board[i][gapCol] = board[gapRow][gapCol];
                    board[gapRow][gapCol] = temp;
                    }
                   
                } 
            }
        }
        // System.out.println("The storage area:");
        // for(int a[][]: list) {
        //     printBoard(a);
        //     storage.add(a);
            
        // }
        
    }

    //is being used to assigne the values to the storage by making a copy of the board
    private static int[][] copyBoard(int[][] board) {
        //copy the board and return it
        int temp[][] = new int[3][3];
        for(int i=0; i<3; i++) {
            for(int j=0; j<3; j++) {
                temp[i][j] = board[i][j];
            }
        }
        return temp;
    }

    private static void printBoard(int[][] board) {
        //print the 8-puzzle board
        //design it like board
        System.out.println();
        for(int i=0; i<3; i++) {
            for(int j=0; j<3; j++) {
                if(board[i][j] == 0) {
                    System.out.print(" - ");
                }else {
                    System.out.print(" "+board[i][j]+" ");
                }
                if(j == 2) {
                    System.out.println();
                }else {
                    System.out.print("|");
                }
            }
        }

    }

    private static void printBoardMove(int[][] board) {
        //print the 8-puzzle board
        //design it like board
        System.out.println();
        for(int i=0; i<3; i++) {
            for(int j=0; j<3; j++) {
                if(board[i][j] == 0) {
                    System.out.print(" - ");
                }else {
                    System.out.print(" "+board[i][j]+" ");
                }
                if(j == 2) {
                    System.out.println();
                }else {
                    System.out.print("|");
                }
            }
        }

        storage.add(board);

    }
//printing the priority queue
public static void printPriorityQueue() {
    for(Box8 b: pq) {
    System.out.println("The board is : ");
    printBoard(b.box);
    System.out.println("The distance is : "+b.distance);
}
}

//to remove the board from the priorityqueue and then generating all possible moves and inserting them in the board
public static int removeBoard() {

    while(!pq.isEmpty()) {
        Box8 b = pq.poll();
        System.out.println("The board is : ");
        printBoard(b.box);
        System.out.println("The distance is : "+b.distance);
        //check if the board is the goal state
        if(checkGoalState(b.box)) {
            System.out.println("The goal state is reached");
            return 1;
        } else {
            //generate all the possible moves
            printAllPossibleMoves(b.box, 0);
            //insert them in the priority queue
            calculateEuclidean(goalBoard);
            //also put that removed board in the closed list
            closedList.add(b.box);
        }
    }

   

        return 0;
}

private static boolean checkGoalState(int[][] box) {
    //check if the board is the goal state
    
    for(int i=0; i<3; i++) {
        for(int j=0; j<3; j++) {
            if(box[i][j] != goalBoard[i][j]) {
                return false;
            }
        }
    }
    return true;
}
}

