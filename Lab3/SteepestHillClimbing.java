import java.util.*;
class Node{
    int[][] box = new int[3][3];
    double distance;
    Node(int[][] box, double distance){
        this.box = box;
        this.distance = distance;
    }
}
public class SteepestHillClimbing {
 //the open for storing the nodes yet to visit
    static ArrayList<Node> allPossibleMoves = new ArrayList<>();
    static ArrayList<Node> closedList = new ArrayList<>();

    //for taking the initial board from the user
    public static int board[][] = {{1,2,3},
                            {5,6,0},
                            {7,8,4}};

    //for taking the goal board from the user
    static int goalBoard[][] = {{1,2,3},
                            {0,5,6},
                            {7,8,4}};


    public static void main(String args[]) {
        System.out.println("------------------------------------The Board Game------------------------------------");

        System.out.println("------------------------------------The Original Board Position is ------------------------------------");
        printBoard(board);
        System.out.println("------------------------------------------------------------------------------------------------------");

        System.out.println("------------------------------------The Goal Board Position is ------------------------------------");
        printBoard(goalBoard);
        System.out.println("------------------------------------------------------------------------------------------------------");


        //calculating the euclidian distance of the initial board position
        double distance = calculateEuclidean(board, goalBoard);
            Node box = new Node(board, distance);
            closedList.add(box);
        
        //printing the initial board position with the distance
        System.out.println("------------------------------------The Original Board Position with the distance is ------------------------------------");
        printBoard(board);
        System.out.println("The distance is : "+distance);

        //simpleHillClimbing 
        simpleHillClimbing(board, goalBoard, closedList, allPossibleMoves);

        //printing the closed list
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                System.out.println("\t\t ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                System.out.println("The Closed List is : ");
                printClosedList(closedList);
                System.out.println("\t\t ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

    }

    

    private static void simpleHillClimbing(int[][] board2, int[][] goalBoard2, ArrayList<Node> closedList2,ArrayList<Node> allPossibleMoves2) {

        while(true) {
            //getting the current board position
            Node current = closedList.get(closedList.size()-1);
            //getting the current board position
            int[][] currentBoard = current.box;
            //getting the current distance
            double currentDistance = current.distance;

            //checking if the current board position is the goal board position
            if(currentDistance == 0) {
                System.out.println("------------------------------------The Goal Board Position is Reached------------------------------------");
                printBoard(currentBoard);
                System.out.println("------------------------------------------------------------------------------------------------------");
                break;
            }

            //getting all the possible moves from the current board position
            allPossibleMoves = getAllPossibleMoves(currentBoard, goalBoard2, closedList2, allPossibleMoves2);

            //printing the all possible moves that are generated from the current board position along with distance
            System.out.println("///////////////-------The All Possible Moves from the Current Board Position with the distance is -------/////////////");
            for(int i=0; i<allPossibleMoves.size(); i++) {
                printBoard(allPossibleMoves.get(i).box);
                System.out.println("The distance is : "+allPossibleMoves.get(i).distance);
            }
            //getting the best move from the all possible moves
            Node bestMove = getBestMove(allPossibleMoves, currentBoard, currentDistance);
            //empty teh all possible moves for the next iteration
            allPossibleMoves.clear();
            //printing the best move selected
            System.out.println("=============================================");
            System.out.println("The Best Move Selected is : ");
            printBoard(bestMove.box);
            System.out.println("The distance is : "+bestMove.distance);
            System.out.println("=============================================");

            //checking if the best move is better than the current board position
            if(bestMove.distance < currentDistance) {
                //if the best move is better than the current board position then add it to the closed list
                closedList.add(bestMove);
            }else {
                //if the best move is not better than the current board position then break the loop
                System.out.println("------------------------------------The Goal Board Position is Not Reached------------------------------------");
                printBoard(currentBoard);
                System.out.println("------------------------------------------------------------------------------------------------------");
                break;
            }
        }
    }



    private static void printClosedList(ArrayList<Node> closedList2) {
        //printing the closed list
        for(int i=0; i<closedList.size(); i++) {
            printBoard(closedList.get(i).box);
            System.out.println("The distance is : "+closedList.get(i).distance);
        }
    }



    private static Node getBestMove(ArrayList<Node> allPossibleMoves2, int[][] currentBoard, double currentDistance) {
        //getting the best move from the all possible moves with respect to current board distance
        Node bestMove = new Node(currentBoard, currentDistance);
        for(int i=0; i<allPossibleMoves.size(); i++) {
            if(allPossibleMoves.get(i).distance < bestMove.distance) {
                bestMove = allPossibleMoves.get(i);
            }
        }
        return bestMove;
    }



    private static ArrayList<Node> getAllPossibleMoves(int[][] currentBoard, int[][] goalBoard2, ArrayList<Node> closedList2, ArrayList<Node> allPossibleMoves2) {
        //getting all the possible moves from the current board position
        //getting the position of the blank space
        int x = 0, y = 0;
        for(int i=0; i<3; i++) {
            for(int j=0; j<3; j++) {
                if(currentBoard[i][j] == 0) {
                    x = i;
                    y = j;
                    break;
                }
            }
        }

        //defining two arrays for storing the steps to move the blank space 
        //and the steps should be up down left right
        int[] xMove = {-1, 1, 0, 0};
        int[] yMove = {0, 0, -1, 1};

        //getting all the possible moves from the current board position
        for(int i=0; i<4; i++) {
            int[][] newBoard = new int[3][3];
            for(int j=0; j<3; j++) {
                for(int k=0; k<3; k++) {
                    newBoard[j][k] = currentBoard[j][k];
                }
            }
            if(x+xMove[i] >= 0 && x+xMove[i] < 3 && y+yMove[i] >= 0 && y+yMove[i] < 3) {
                int temp = newBoard[x][y];
                newBoard[x][y] = newBoard[x+xMove[i]][y+yMove[i]];
                newBoard[x+xMove[i]][y+yMove[i]] = temp;
                double distance = calculateEuclidean(newBoard, goalBoard2);
                Node box = new Node(newBoard, distance);
                allPossibleMoves.add(box);
            }
        }

        //sorting the allPossibleMoves with respect to distance this will give us the best Move from all
        Collections.sort(allPossibleMoves, new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                return Double.compare(o1.distance, o2.distance);
            }
        });

        return allPossibleMoves;
    }



    //----------For calculating the euclidean distance-----------//
    private static double calculateEuclidean(int[][] board, int[][] a) {
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

    //----------For Printing the Board-----------//
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
}
