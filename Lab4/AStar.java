import java.util.*;

class Node {
    int[][] ibp;
    int[][] mbp;
    int ga;
    int fa;
    Node next;

    Node(int[][] initial, int[][] mvpos, int ftemp, int gtemp) {
        ibp = new int[3][3];
        mbp = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                ibp[i][j] = initial[i][j];
                mbp[i][j] = mvpos[i][j];
            }
        }
        ga = gtemp;
        fa = ftemp;
        next = null;
    }
}

public class AStar {
    static int mov = 0;
    static int[][] board = new int[3][3];
    static int[][] moves = new int[4][9];
    static int[][] goal = new int[3][3];
    // static int[] d = new int[4];

    static Node createNode(int[][] initial, int[][] mvpos, int ftemp, int gtemp) {
        return new Node(initial, mvpos, ftemp, gtemp);
    }

    static Node insertNode(Node node, int[][] initial, int[][] mvpos, int ftemp, int gtemp) {
        Node temp = createNode(initial, mvpos, ftemp, gtemp);
        if (node == null) {
            node = temp;
            node.next = null;
        } else if (node.fa >= ftemp) {
            temp.next = node;
            node = temp;
        } else {
            Node start = node;
            while (start.next != null && start.next.fa < ftemp) {
                start = start.next;
            }
            temp.next = start.next;
            start.next = temp;
        }
        return node;
    }

    static Node insertClosed(Node node, int[][] initial, int[][] mvpos, int ftemp, int gtemp) {
        Node temp = createNode(initial, mvpos, ftemp, gtemp);
        if (node == null) {
            node = temp;
            node.next = null;
        } else {
            Node start = node;
            while (start.next != null) {
                start = start.next;
            }
            temp.next = start.next;
            start.next = temp;
        }
        return node;
    }

    public static void updateParent(Node CLOSED, Node OPEN, int[][] newParent, int[][] parent, int gtemp) {
        Node temp = CLOSED;
        Node temp1 = OPEN;
        while (temp != null) {
            if (Arrays.deepEquals(temp.ibp, parent)) {
                // System.out.println("updating----"); // function not tested
                while (temp1 != null) {
                    temp1.fa = temp1.fa - temp1.ga + gtemp + 1;
                    temp1.ga = gtemp + 1;
                    temp1 = temp1.next;
                }
                temp.ibp = newParent;
                temp.fa = temp.fa - temp.ga + gtemp;
                temp.ga = gtemp;
            }
            temp = temp.next;
        }
    }

    // static int findHeuristicValue(int[][] goal, int[][] bps) {
    // // euclidean distance
    // int hval = 0;
    // for (int i = 0; i < 3; i++) {
    // for (int j = 0; j < 3; j++) {
    // hval += Math.pow(goal[i][j] - bps[i][j], 2);
    // }
    // }
    // return (int) Math.sqrt(hval);
    // }

    static int findHeuristicValue(int[][] goal, int[][] bps) {
        // manhattan distance
        int hval = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                hval += Math.abs(goal[i][j] - bps[i][j]);
            }
        }
        return hval;
    }

    // static int findHeuristicValue(int[][] goal, int[][] bps) {
    // // misplaced tiles
    // int hval = 0;
    // for (int i = 0; i < 3; i++) {
    // for (int j = 0; j < 3; j++) {
    // if (goal[i][j] != bps[i][j]) {
    // hval++;
    // }
    // }
    // }
    // return hval;
    // }

    static Node heuristic(Node open, int[][] moves, int[][] goal, int gtemp, int[][] initial, Node closed) {
        int di = 0;
        while (di < mov) {
            int dj = 0;
            int[][] arr = new int[3][3];
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    arr[i][j] = moves[di][dj];
                    dj++;
                }
            }
            // d[di] = findHeuristicValue(goal, arr);
            int ftemp = findHeuristicValue(goal, arr) + gtemp;
            boolean check = false;
            Node temp = open;
            Node temp1 = closed;
            if (temp == null && temp1 == null) {
                open = insertNode(open, initial, arr, ftemp, gtemp);
            } else {
                while (temp != null) {
                    boolean isDifferent = !isEqual(temp.mbp, arr);
                    if (isDifferent == false) {
                        if (temp.fa > ftemp) {
                            temp.ibp = initial;
                            temp.fa = ftemp;
                            temp.ga = gtemp;
                        }
                        check = true;
                        break;
                    }
                    temp = temp.next;
                }
                if (check == false) {
                    while (temp1 != null) {
                        boolean isDifferent = !isEqual(temp1.mbp, arr);
                        if (isDifferent == false) {
                            if (temp1.fa > ftemp) {
                                // System.out.println(ftemp + " " + gtemp + " " + temp1.fa + " " + temp1.ga);
                                // System.out.println("updating closed");
                                updateParent(closed, open, initial, temp1.ibp, gtemp);
                            }
                            check = true;
                            break;
                        }
                        temp1 = temp1.next;
                    }
                }
                if (check == false) {
                    open = insertNode(open, initial, arr, ftemp, gtemp);
                } else {
                    // System.out.println("existing node");
                    // for (int i = 0; i < 3; i++) {
                    // for (int j = 0; j < 3; j++) {
                    // System.out.print(arr[i][j] + " ");
                    // }
                    // System.out.println();
                    // }
                    check = false;
                }
            }
            di++;
        }
        // matix with all generated child

        // System.out.println("\nfinal matrix: ");
        // for (int i = 0; i < mov; i++) {
        // for (int j = 0; j < 9; j++) {
        // System.out.print(moves[i][j] + " ");
        // }
        // System.out.print("-> distance: " + d[i]);
        // System.out.println();
        // }
        return open;
    }

    static int[][] createMatrix(int[][] board, int x, int y, int p, int q, int fi) {
        int fj = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (i == x && j == y) {
                    moves[fi][fj++] = board[p][q];
                } else if (i == p && j == q) {
                    moves[fi][fj++] = 0;
                } else {
                    moves[fi][fj++] = board[i][j];
                }
            }
        }
        return moves;
    }

    static void findPosition(int i, int j, int[][] arr) {
        int k = 0;
        if ((3 > (i - 1) && i - 1 >= 0) && (3 > j && j >= 0)) {
            arr[k][0] = i - 1;
            arr[k][1] = j;
            k++;
        }
        if (3 > i + 1 && i + 1 >= 0 && 3 > j && j >= 0) {
            arr[k][0] = i + 1;
            arr[k][1] = j;
            k++;
        }
        if ((3 > i && i >= 0) && (3 > (j + 1) && j + 1 >= 0)) {
            arr[k][0] = i;
            arr[k][1] = j + 1;
            k++;
        }
        if ((3 > i && i >= 0) && (3 > j - 1 && j - 1 >= 0)) {
            arr[k][0] = i;
            arr[k][1] = j - 1;
            k++;
        }
    }

    static void printQueue(Node node) {
        if (node == null) {
            System.out.println("\nList is empty");
            return;
        }
        Node ptr = node;
        while (ptr != null) {
            System.out.print("\nf(A): " + ptr.fa);
            System.out.println("\tg(A): " + ptr.ga);
            System.out.println("Parent Node");
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    System.out.print(ptr.ibp[i][j] + " ");
                }
                System.out.println();
            }

            System.out.println("Child Node");
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    System.out.print(ptr.mbp[i][j] + " ");
                }
                System.out.println();
            }
            ptr = ptr.next;
        }
    }

    public static void storePosition(int x, int y, int arr[][], int bpos[][]) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if ((i == x && j == y) && ((i == 0 || i == 2) && (j == 0 || j == 2))) {
                    mov = 2; // corner pos.
                    findPosition(i, j, arr); // find x,y cords. to replace with 0
                } else if ((i == x && j == y) && (i == 1 && j == 1)) {
                    mov = 4; // center
                    findPosition(i, j, arr);
                } else if ((i == x && j == y)) {
                    mov = 3;
                    findPosition(i, j, arr);
                }
            }
        }
        // stored childs of bpos in moves[][]
        int fi = 0;
        for (int i = 0; i < mov; i++) {
            createMatrix(bpos, x, y, arr[i][0], arr[i][1], fi);
            fi++;
        }
    }

    static boolean isEqual(int[][] arr1, int[][] arr2) {
        boolean flag = true;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (arr1[i][j] != arr2[i][j]) {
                    flag = false;
                    break;
                }
            }
        }
        return flag;
    }

    public static void printPath(Node CLOSED, int[][] initial, int[][] goal) {
        if (isEqual(initial, goal)) {
            System.out.println("Initial Node: f(A): " + CLOSED.fa + " and g(A): " + CLOSED.ga);
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    System.out.print(initial[i][j] + " ");
                }
                System.out.println();
            }
            return;
        }
        Node temp = CLOSED;
        while (temp != null && temp.next != null && !isEqual(temp.mbp, goal)) {
            temp = temp.next;
        }
        if (temp != null) {
            System.out.println("Node: f(A): " + temp.fa + " and g(A): " + temp.ga);
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    System.out.print(temp.mbp[i][j] + " ");
                }
                System.out.println();
            }
            printPath(CLOSED, initial, temp.ibp);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter valid goal state: ");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                goal[i][j] = sc.nextInt();
            }
        }

        System.out.print("Enter valid initial state: ");
        int x = 0, y = 0; // 0-index
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = sc.nextInt();
                if (board[i][j] == 0) {
                    x = i;
                    y = j;
                }
            }
        }

        Node OPEN = null;
        int ga = 0;
        int[][] a = { { 0, 0, 0 }, { 0, 0, 0 }, { 0, 0, 0 } };
        OPEN = insertNode(OPEN, a, board, findHeuristicValue(goal, board) + ga, ga);
        Node CLOSED = null;
        int[][] arr = new int[4][2];
        boolean flag = false;
        while (OPEN != null) {
            Node current = OPEN;
            OPEN = OPEN.next;
            current.next = null;

            CLOSED = insertClosed(CLOSED, current.ibp, current.mbp, current.fa, current.ga);

            if (current.fa - current.ga == 0) {
                flag = true;
                break;
            }
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (current.mbp[i][j] == 0) {
                        x = i;
                        y = j;
                    }
                }
            }
            storePosition(x, y, arr, current.mbp);
            OPEN = heuristic(OPEN, moves, goal, current.ga + 1, current.mbp, CLOSED);
        }
        if (flag == false) {
            System.out.println("Goal state could not be reached!");
        } else {
            System.out.println("\nList OPEN");
            printQueue(OPEN);
            System.out.println("\nList Closed");
            printQueue(CLOSED);
            System.out.println("\nActual Path--(goal-initial)");
            printPath(CLOSED, CLOSED.mbp, goal);
        }
    }
}