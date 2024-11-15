import java.util.Random;
import java.util.Scanner;

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
            } else {
                if (isSinglePlayer) {
                    int pos = computer.makeRandomMove();
                    System.out.println("Computer chose: " + pos);
                    placeMark(board, pos, computer);
                } else {
                    System.out.println(player2.getName() + ", enter a position to place your mark in (1-9):");
                    int pos = sc.nextInt();
                    placeMark(board, pos, player2);
                }
            }

            Board.printBoard(board);


            player1Turn = !player1Turn;
        }
    }

    public void playerTurn(User user) {
        Scanner sc = new Scanner(System.in);
        System.out.println(user.getName() + "Enter the number of the cell you want to place your mark in (1-9): ");
        int pos = sc.nextInt();

        System.out.println("Chosen position:" + pos);
        placeMark(board, pos, user);
        Board.printBoard(board);
    }

    public static void placeMark(char[][] board, int pos, User user) {
        char symbol = user.getSymbol();

        switch (pos) {
            case 1:
                board[0][0] = symbol;
                break;
            case 2:
                board[0][2] = symbol;
                break;
            case 3:
                board[0][4] = symbol;
                break;
            case 4:
                board[2][0] = symbol;
                break;
            case 5:
                board[2][2] = symbol;
                break;
            case 6:
                board[2][4] = symbol;
                break;
            case 7:
                board[4][0] = symbol;
                break;
            case 8:
                board[4][2] = symbol;
                break;
            case 9:
                board[4][4] = symbol;
                break;
            default:
                System.out.println("Position not free. Try again");
                break;
        }
    }
}


