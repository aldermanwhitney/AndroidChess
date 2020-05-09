package controller;

/**
 * @authors Gazal Arora and Whitney Alderman
 */
//package chess;
//import pieces.*;
import controller.*;
import view.*;
import model.*;
import controller.Chessboard;


public class Chessboard {

    // global chessboard
    public static Block[][] chessBoard = new Block[8][8];


    public int originRow;
    public int originColumn;

    public int destRow;
    public int destColumn;

    public char promotion;


    public static boolean whitesTurn = false;
    public static boolean blacksTurn = false;


    public static boolean gameOver = false;
    public static boolean whiteResigns = false;
    public static boolean blackResigns = false;
    public static boolean drawRequest = false;
    public static boolean drawAgreement = false;
    public static boolean whiteincheck = false;
    public static boolean blackincheck = false;
    public static boolean checkMate = false;
    public static boolean whiteWins = false;
    public static boolean blackWins = false;

    /**Chessboard()
     * empty constructor as other methods initialize and update the same global chessBoard real time with every move
     */
    public Chessboard() {

    }

    /**
     * resetBoard()
     * : initializes the chess board with all chess pieces in there initial positions,
     *  initializes all the blocks on the board as black and white
     *
     */
    public void resetBoard(){

        //sets individual squares to their correct color, no pieces implemented yet
        for (int i=0; i<8; i++) {
            for (int j=0; j<8; j++) {

                if ((i%2!=0 && j%2==0) || (i%2==0 && j%2!=0)) {
                    Block b = new Block("black");
                    chessBoard[i][j] = b;
                    //board[i][j] = "## ";
                }
                else {
                    Block w = new Block("white");
                    chessBoard[i][j] = w;
                    //board[i][j] = "   ";
                }
            }
        }

        //sets pieces on board in starting positions
        for (int i=0; i<8; i++) {
            for (int j=0; j<8; j++) {

                if (i==0) {
                    if (j==0 || j==7) {
                        chessBoard[i][j].setPiece(new Rook("black"));
                    }
                    else if (j==1 || j==6) {
                        chessBoard[i][j].setPiece(new Knight("black"));
                    }
                    else if (j==2 || j==5) {
                        chessBoard[i][j].setPiece(new Bishop("black"));
                    }
                    else if (j==3) {
                        chessBoard[i][j].setPiece(new Queen("black"));
                    }
                    else {
                        chessBoard[i][j].setPiece(new King("black"));
                    }
                }
                if (i==1) {
                    chessBoard[i][j].setPiece(new Pawn("black"));
                }

                if (i==7) {
                    if (j==0 || j==7) {
                        chessBoard[i][j].setPiece(new Rook("white"));
                    }
                    else if (j==1 || j==6) {
                        chessBoard[i][j].setPiece(new Knight("white"));
                    }
                    else if (j==2 || j==5) {
                        chessBoard[i][j].setPiece(new Bishop("white"));
                    }
                    else if (j==3) {
                        chessBoard[i][j].setPiece(new Queen("white"));
                    }
                    else {
                        chessBoard[i][j].setPiece(new King("white"));
                    }
                }
                if (i==6) {
                    chessBoard[i][j].setPiece(new Pawn("white"));
                }

            }
        }
    }



    /**
     * printBoard()
     * will print any updates to the chess board
     *
     */
    public void printBoard(){

        for (int i=0; i<8; i++) {
            for (int j=0; j<9; j++) {

                //prints right hand side column of row number
                if (j==8) {
                    int z = 8-i;
                    System.out.print(Integer.toString(z));
                    System.out.println();
                    continue;
                }

                else if (chessBoard[i][j].isSquareBlack() && chessBoard[i][j].getPiece() == null){
                    System.out.print("## ");
                }
                else if (!chessBoard[i][j].isSquareBlack() && chessBoard[i][j].getPiece() == null){
                    System.out.print("   ");
                }

                //there is a piece on the block
                else {
                    if (chessBoard[i][j].getPiece().getType()=="rook") {
                        if (chessBoard[i][j].getPiece().isWhite()) {
                            System.out.print("wR ");
                        }
                        else {
                            System.out.print("bR ");
                        }
                    }
                    if (chessBoard[i][j].getPiece().getType()=="knight") {
                        if (chessBoard[i][j].getPiece().isWhite()) {
                            System.out.print("wN ");
                        }
                        else {
                            System.out.print("bN ");
                        }

                    }
                    if (chessBoard[i][j].getPiece().getType()=="bishop") {
                        if (chessBoard[i][j].getPiece().isWhite()) {
                            System.out.print("wB ");
                        }
                        else {
                            System.out.print("bB ");
                        }

                    }
                    if (chessBoard[i][j].getPiece().getType()=="queen") {
                        if (chessBoard[i][j].getPiece().isWhite()) {
                            System.out.print("wQ ");
                        }
                        else {
                            System.out.print("bQ ");
                        }

                    }
                    if (chessBoard[i][j].getPiece().getType()=="king") {
                        if (chessBoard[i][j].getPiece().isWhite()) {
                            System.out.print("wK ");
                        }
                        else {
                            System.out.print("bK ");
                        }

                    }
                    if (chessBoard[i][j].getPiece().getType()=="pawn") {
                        if (chessBoard[i][j].getPiece().isWhite()) {
                            System.out.print("wp ");
                        }
                        else {
                            System.out.print("bp ");
                        }

                    }

                }
            }
        }
        System.out.print(" a  b  c  d  e  f  g  h ");
        System.out.println("");
    }


    /** addressBlock(String s)
     * parses user input string into integers to refer to block addresses [row][column]
     *
     * @param s - user input for filerank filerank
     * @return boolean
     */
    public boolean addressBlock(int originPosition, int destinationPosition) {

        originRow = originPosition / 8;
        originColumn = originPosition % 8;
        destRow = destinationPosition / 8;
        destColumn = destinationPosition % 8;

        return true;
        /*
        if (s.length()<4) {
            //System.out.println("invalid input");
            return false;
        }

        if (s.length()==4 && !s.equals("draw")) {
            return false;
        }


        s.trim();

        if (s.equals("resign")) {
            gameOver = true;
            if (whitesTurn==true) {
                blackWins = true;
            }
            if (blacksTurn==true) {
                whiteWins = true;
            }
            return true;
        }

        if (s.length()>=11) {
            if (s.substring(6).equals("draw?")) {
                drawRequest = true;
                //System.out.println("draw requested");
            }
        }

        if (s.equals("draw")) {
            drawAgreement = true;
            gameOver = true;
            return true;
        }



        char originLetter = s.charAt(0);
        char originNumber = s.charAt(1);

        char destLetter = s.charAt(3);
        char destNumber = s.charAt(4);






        if(s.length()==7)
            promotion = s.charAt(6);
        else if (s.length()==5)
            promotion = 'Q';

        //System.out.println("promotion value: " + promotion);



        switch(originLetter)
        {
            case 'a': originColumn = 0; break;
            case 'b': originColumn = 1; break;
            case 'c': originColumn = 2; break;
            case 'd': originColumn = 3; break;
            case 'e': originColumn = 4; break;
            case 'f': originColumn = 5; break;
            case 'g': originColumn = 6; break;
            case 'h': originColumn = 7; break;
            default: return false;
        }

        switch(originNumber)
        {
            case '1': originRow = 7; break;
            case '2': originRow = 6; break;
            case '3': originRow = 5; break;
            case '4': originRow = 4; break;
            case '5': originRow = 3; break;
            case '6': originRow = 2; break;
            case '7': originRow = 1; break;
            case '8': originRow = 0; break;
            default: return false;
        }

        switch(destLetter)
        {
            case 'a': destColumn = 0; break;
            case 'b': destColumn = 1; break;
            case 'c': destColumn = 2; break;
            case 'd': destColumn = 3; break;
            case 'e': destColumn = 4; break;
            case 'f': destColumn = 5; break;
            case 'g': destColumn = 6; break;
            case 'h': destColumn = 7; break;
            default: return false;
        }

        switch(destNumber)
        {
            case '1': destRow = 7; break;
            case '2': destRow = 6; break;
            case '3': destRow = 5; break;
            case '4': destRow = 4; break;
            case '5': destRow = 3; break;
            case '6': destRow = 2; break;
            case '7': destRow = 1; break;
            case '8': destRow = 0; break;
            default: return false;
        }

        return true;

    }
*/
    }
    /** gameMove(String FileRank)
     * Takes user input filerank to move a piece from old position to new position and performs the move
     * Returns true if its able to perform the move else returns false
     * @param FileRank - user input for filerank filerank
     * @return boolean
     */


    public boolean gameMove(int originPosition, int destinationPosition) {
          //  System.out.println("in gamemove method");
          //  int originPosition, int destinationPosition) {
                /*
                originRow = originPosition / 8;
                originColumn = originPosition % 8;
                destRow = destinationPosition / 8;
                destColumn = destinationPosition % 8;
*/



                if (gameOver==true) {
           // System.out.println("false line 368");
            return false;
        }


        boolean validinput = addressBlock(originPosition, destinationPosition);
        if (validinput==false) {
            //System.out.println("false line 375");
            return false;
        }
        if (chessBoard[originRow][originColumn].getPiece()==null){
            //System.out.println("false line 379");
            return false;
        }

        if (whitesTurn==true) {
            if (!(chessBoard[originRow][originColumn].getPiece().isWhite())) {
              //  System.out.println("false line 385");
                return false;
            }
        }

        if (blacksTurn==true) {
            if (chessBoard[originRow][originColumn].getPiece().isWhite()) {
                //System.out.println("false line 392");
                return false;
            }
        }

        //change this later
        //promotion = 'Q';

            //System.out.println("PROMOTION:" + Character.toString(promotion));

        boolean b = chessBoard[originRow][originColumn].getPiece().move(originColumn, originRow, destColumn, destRow, promotion);


        return b;

    }
    /**
     *  gameOver()
     *  Checks if game is over and returns true if it is
     * @return boolean
     */
    public boolean gameOver() {

        if (gameOver==true) {
            return true;
        }

        else {
            return false;
        }

    }
    /**
     * gameStatus()
     * Checks the current status of the game, in check, checkmate, winner, draw or resign and returns appropriate character for the same
     * @return char
     */
    public char gameStatus() {
        //gameOver = false;
        //drawRequest = false;

        if (checkMate==true) {
            return 'm';
        }


        if (drawAgreement == true) {
            return 'd';
        }

        if (whiteincheck==true || blackincheck==true) {
            return 'c';
        }

        if (whiteWins == true || blackResigns==true) {
            return 'w';
        }
        if (blackWins == true || whiteResigns==true) {
            return 'b';
        }


        return 'a';
    }







}
