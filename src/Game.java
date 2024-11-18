import org.w3c.dom.ls.LSOutput;

import java.util.*;

public class Game {
    private char[][] board;
    private Player player1;
    private Player player2;
    private AI computer;
    private boolean isSinglePlayer;


    public Game() {
        this.board = new char[][]{
                {' ', '|', ' ', '|', ' '},
                {'-', '+', '-', '+', '-'},
                {' ', '|', ' ', '|', ' '},
                {'-', '+', '-', '+', '-'},
                {' ', '|', ' ', '|', ' '},
        };
    }

    public static boolean isPositionFree(char[][] board, int pos) {
        switch (pos) {
            case 1:
                return board[0][0] == ' ';
            case 2:
                return board[0][2] == ' ';
            case 3:
                return board[0][4] == ' ';
            case 4:
                return board[2][0] == ' ';
            case 5:
                return board[2][2] == ' ';
            case 6:
                return board[2][4] == ' ';
            case 7:
                return board[4][0] == ' ';
            case 8:
                return board[4][2] == ' ';
            case 9:
                return board[4][4] == ' ';
            default:
                return false;
        }
    }
        List<Integer> userPositions = new ArrayList<>();
        List<Integer> computerPositions = new ArrayList<>();


            public String checkWinner () {
                List<Integer> topRow = Arrays.asList(1, 2, 3);
                List<Integer> midRow = Arrays.asList(4, 5, 6);
                List<Integer> botRow = Arrays.asList(7, 8, 9);
                List<Integer> leftCol = Arrays.asList(1, 4, 7);
                List<Integer> midCol = Arrays.asList(2, 5, 8);
                List<Integer> rightCol = Arrays.asList(3, 6, 9);
                List<Integer> cross1 = Arrays.asList(1, 5, 9);
                List<Integer> cross2 = Arrays.asList(3, 5, 7);

                List<List<Integer>> winning = new ArrayList<>();
                winning.add(topRow);
                winning.add(midRow);
                winning.add(botRow);
                winning.add(leftCol);
                winning.add(midCol);
                winning.add(rightCol);
                winning.add(cross1);
                winning.add(cross2);

                for (List<Integer> l : winning) {
                    if (userPositions.containsAll(l)) {
                        return "Congratulations! You won!";
                    } else if (computerPositions.containsAll(l)) {
                        return "Computer wins!";
                    } else if (userPositions.size() + computerPositions.size() == 9) {
                        return "Draw!";
                    }
                }
                return "";
            }


    public void run() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Choose game mode: 1 for one-player, 2 for two-player");
        int mode = sc.nextInt();
        sc.nextLine();

        if (mode == 1) {
            isSinglePlayer = true;
            System.out.println("Please enter your name: ");
            String name = sc.nextLine();
            player1 = new Player(name);
            computer = new AI();
        } else {
            isSinglePlayer = false;
            System.out.println("Player 1, please enter your name: ");
            String name1 = sc.nextLine();
            player1 = new Player(name1);

            System.out.println("Player 2, please enter your name: ");
            String name2 = sc.nextLine();
            player2 = new Player(name2);

        }

        Board.printBoard(board);

        Random rand = new Random();
        boolean player1Starts = rand.nextBoolean();
        if (player1Starts) {
            System.out.println(player1.getName() + " starts playing!");
        } else {
            if (isSinglePlayer) {
                System.out.println("Computer starts playing!");
            } else {
                System.out.println(player2.getName() + " starts playing!");
            }
        }

        boolean player1Turn = player1Starts;

        while (true) {
            if (player1Turn) {
                System.out.println(player1.getName() + ", enter a position to place your mark in (1-9):");
                int pos = sc.nextInt();
                placeMark(board, pos, player1);
                userPositions.add(pos); //Add position to player list
            } else {
                if (isSinglePlayer) {
                    int pos;
                    do {
                        pos = computer.makeRandomMove();
                    } while (!isPositionFree(board, pos));
                    System.out.println("Computer chose: " + pos);
                    placeMark(board, pos, computer);
                    computerPositions.add(pos); // Add position to computer list
                } else {
                    System.out.println(player2.getName() + ", enter a position to place your mark in (1-9):");
                    int pos = sc.nextInt();
                    placeMark(board, pos, player2);
                    userPositions.add(pos);
                }
            }

            Board.printBoard(board);


            String result = checkWinner();
            if (!result.equals("")) {
                System.out.println(result);
                break; // End game if we have a winner or draw
            }

            player1Turn = !player1Turn;
        }

        System.out.println("Thank you for playing!");
    }


    public static void placeMark(char[][] board, int pos, User user) {
        char symbol = user.getSymbol();
        boolean validPosition = false;

        while (!validPosition) {
            switch (pos) {
                case 1:
                    if (board[0][0] == ' ') {
                        board[0][0] = symbol;
                        validPosition = true;
                    }
                    break;
                case 2:
                    if (board[0][2] == ' ') {
                        board[0][2] = symbol;
                        validPosition = true;
                    }
                    break;
                case 3:
                    if (board[0][4] == ' ') {
                        board[0][4] = symbol;
                        validPosition = true;
                    }
                    break;
                case 4:
                    if (board[2][0] == ' ') {
                        board[2][0] = symbol;
                        validPosition = true;
                    }
                    break;
                case 5:
                    if (board[2][2] == ' ') {
                        board[2][2] = symbol;
                        validPosition = true;
                    }
                    break;
                case 6:
                    if (board[2][4] == ' ') {
                        board[2][4] = symbol;
                        validPosition = true;
                    }
                    break;
                case 7:
                    if (board[4][0] == ' ') {
                        board[4][0] = symbol;
                        validPosition = true;
                    }
                    break;
                case 8:
                    if (board[4][2] == ' ') {
                        board[4][2] = symbol;
                        validPosition = true;
                    }
                    break;
                case 9:
                    if (board[4][4] == ' ') {
                        board[4][4] = symbol;
                        validPosition = true;
                    }
                    break;
                default:
                    System.out.println("Invalid position. Try again.");
                    return;
            }
            if (!validPosition) {
                System.out.println("Position not free. Choose another position (1-9): ");
                Scanner sc = new Scanner(System.in);
                pos = sc.nextInt();
            }
        }
    }

}



