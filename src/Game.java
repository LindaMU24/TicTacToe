import java.util.Scanner;

public class Game {
    private char[][] board;
   // private Player playerX;
   // private Player playerO;


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
    Board.printBoard(board);

    Scanner sc = new Scanner(System.in);
    System.out.println("Enter the number of the cell you want to place your mark in (1-9): ");
    int pos = sc.nextInt();

    System.out.println(pos);


        switch (pos) {
            case 1:
                board[0][0] = 'X';
                break;
            case 2:
                board[0][2] = 'X';
                break;
            case 3:
                board[0][4] = 'X';
                break;
            case 4:
                board[2][0] = 'X';
                break;
            case 5:
                board[2][2] = 'X';
                break;
            case 6:
                board[2][4] = 'X';
                break;
            case 7:
                board[4][0] = 'X';
                break;
            case 8:
                board[4][2] = 'X';
                break;
            case 9:
                board[4][4] = 'X';
                break;
        }
        Board.printBoard(board);
    }
}

