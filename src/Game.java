import org.w3c.dom.ls.LSOutput;

import java.util.*;

public class Game {
    private char[][] board;
    private Player player1;
    private Player player2;
    private AI computer;
    private boolean isSinglePlayer;
    private int player1Wins = 0;
    private int player2Wins = 0;
    private int computerWins = 0;
    private int draw = 0;


    public Game() {
        this.board = new char[][]{ //Board graphic
                {' ', '|', ' ', '|', ' '},
                {'-', '+', '-', '+', '-'},
                {' ', '|', ' ', '|', ' '},
                {'-', '+', '-', '+', '-'},
                {' ', '|', ' ', '|', ' '},
        };
    }

    //Positions to put the mark in
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
        List<Integer> player1Positions = new ArrayList<>();
        List<Integer> computerPositions = new ArrayList<>();
        List<Integer> player2Positions = new ArrayList<>();

            //Winning lines
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

                //Congratulation plus add a win to the players list
                for (List<Integer> l : winning) {
                    if (player1Positions.containsAll(l)) {
                        player1Wins++;
                        return player1.getName() + ": Congratulations! You won!";
                    }  else if (isSinglePlayer && computerPositions.containsAll(l)) {
                        computerWins++;
                        return "Computer wins!";
                    } else if (!isSinglePlayer && player2Positions.containsAll(l)) {
                        player2Wins++;
                        return player2.getName() + ": Congratulations! You won!";
                    }
                }

                if (player1Positions.size() + (isSinglePlayer ? computerPositions.size() : player2Positions.size()) == 9) {
                    draw++;
                    return "Draw!";
                }
              return "";
          }

          //Setup start of game, choose players, add name.
public void setupGame() {
    Scanner sc = new Scanner(System.in);

    System.out.println("Choose game mode: 1 for one-player, 2 for two-player");
    int mode = sc.nextInt();
    sc.nextLine();

    if (mode == 1) {
        isSinglePlayer = true;
        System.out.println("Please enter your name: ");
        String name = sc.nextLine();
        player1 = new Player(name, 'X');
        computer = new AI();
    } else {
        isSinglePlayer = false;
        System.out.println("Player 1, please enter your name: ");
        String name1 = sc.nextLine();
        player1 = new Player(name1, 'X');

        System.out.println("Player 2, please enter your name: ");
        String name2 = sc.nextLine();
        player2 = new Player(name2, 'O');

    }
}
    //Run game, random who is starting, play game. (Play again starts here.)
    public void run() {
        Scanner sc = new Scanner(System.in);
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
            //Place mark, check if available, if not ask to choose again.
        boolean player1Turn = player1Starts;

        while (true) {
            if (player1Turn) {
                boolean validPosition = false;
                int pos = 0;
                while (!validPosition) {
                    System.out.println(player1.getName() + ", enter a position to place your mark in (1-9):");
                    String input = sc.next();

                    if (isValidInput(input)) {  // Check if value is correct
                        pos = Integer.parseInt(input);
                        if (isPositionFree(board, pos)) {
                            placeMark(board, pos, player1);
                            player1Positions.add(pos);
                            validPosition = true;
                        } else {
                            System.out.println("Position not free. Choose another position (1-9):");
                        }
                    } else {
                        System.out.println("Invalid input. Please enter a number between 1 and 9:");
                    }
                }
            } else {
                if (isSinglePlayer) {
                    int pos;
                    do {
                        pos = computer.makeRandomMove();
                    } while (!isPositionFree(board, pos));
                    System.out.println("Computer chose: " + pos);
                    placeMark(board, pos, computer);
                    computerPositions.add(pos);
                } else {
                    boolean validPosition = false;
                    int pos = 0;
                    while (!validPosition) {
                        System.out.println(player2.getName() + ", enter a position to place your mark in (1-9):");
                        String input = sc.next();

                        if (isValidInput(input)) { // Check if value is correct
                            pos = Integer.parseInt(input);
                            if (isPositionFree(board, pos)) {
                                placeMark(board, pos, player2);
                                player2Positions.add(pos);
                                validPosition = true;
                            } else {
                                System.out.println("Position not free. Choose another position (1-9):");
                            }
                        } else {
                            System.out.println("Invalid input. Please enter a number between 1 and 9:");
                        }
                    }
                }
            }

            //Show marks on board
            Board.printBoard(board);

            //Check for winner after mark
            String result = checkWinner();
            if (!result.equals("")) {
                System.out.println(result);
                break; // End game if we have a winner or draw
            }

            player1Turn = !player1Turn; //Other players turn
        }
        sc.nextLine();

        //Ask if player wants to play again. If yes, new board, clear all marks and run.
        // If no, show player stats.
        boolean playAgain = true;

        while (playAgain) {

            System.out.println("Do you want to play again? (yes/no)");
            String response = sc.nextLine().trim().toLowerCase();

            if (response.equals("yes") || response.equals("y") || response.equals("j")) {
            this.board = new char[][]{
                    {' ', '|', ' ', '|', ' '},
                    {'-', '+', '-', '+', '-'},
                    {' ', '|', ' ', '|', ' '},
                    {'-', '+', '-', '+', '-'},
                    {' ', '|', ' ', '|', ' '},
            };
            player1Positions.clear();
            computerPositions.clear();
            player2Positions.clear();

            run();

            } else if (response.equals("no") || response.equals("n")) {
                playAgain = false;
            } else {
                System.out.println("Invalid input. Please enter yes or no:");
            }
        }
            System.out.println("Game over! Final scores:");
            System.out.println(player1.getName() + " wins: " + player1Wins);
            if (isSinglePlayer) {
                System.out.println("Computer wins: " + computerWins);
                System.out.println("Draw: " + draw);
            } else {
                System.out.println(player2.getName() + " wins: " + player2Wins);
                System.out.println("Draw: " + draw);
            }
            System.out.println("Thanks for playing!");
            System.exit(0); //End game
        }


    //Check if input is as expected
    public static boolean isValidInput(String input) {
        try {
            int pos = Integer.parseInt(input);
            return pos >= 1 && pos <= 9;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    //Place mark if free based on this scheme
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
                    break;
            }
            if (!validPosition) {
                System.out.println("Position not free. Choose another position (1-9): ");
                Scanner sc = new Scanner(System.in);
                pos = sc.nextInt();
            }
        }
    }
    }





