package model;


/**
 * @authors Gazal Arora and Whitney Alderman
 */
//package pieces;

//import chess.Block;
//import chess.Chessboard;

import controller.*;
import view.*;
import model.*;
import controller.Chessboard;
import model.Block;

public class Queen extends Pieces {

    /**
     * Queen(String color)
     * Constructor for a Queen Piece with given color and type queen,
     *
     * @param color - color black or white using parameter
     */
    public Queen(String color) {
        super(color);
        type = "queen";
    }
    /**
     * isValidMove(int oldCol, int oldRow, int newCol, int newRow)
     * checks to see if a move is valid from old position to new position for a Queen piece
     * @param oldCol - old block's File
     * @param oldRow - old block's Rank
     * @param newCol - new block's File
     * @param newRow - new blocks Rank

     * @return boolean
     */
    public boolean isValidMove(int oldCol, int oldRow, int newCol, int newRow) {

        boolean pathClear = pathClear(oldCol, oldRow, newCol, newRow);

        if (pathClear) {
            if(Chessboard.chessBoard[newRow][newCol].getPiece() != null) {


                if((Chessboard.chessBoard[newRow][newCol].getPiece().isWhite() && !Chessboard.chessBoard[oldRow][oldCol].getPiece().isWhite()) ||
                        (!Chessboard.chessBoard[newRow][newCol].getPiece().isWhite() && Chessboard.chessBoard[oldRow][oldCol].getPiece().isWhite())) {
                    //	System.out.println("debug 1");
                    return true;
                }
                else {
                    return false;
                }

            }

            else {
                //	System.out.println("debug 2");
                return true;
            }
        }

        return false;
    }


    /**
     * pathClear(int oldCol, int oldRow, int newCol, int newRow)
     * Checks if the path from old position to new position is clear, returns false if it isn't
     * @param oldCol - old block's File
     * @param oldRow - old block's Rank
     * @param newCol - new block's File
     * @param newRow - new blocks Rank
     * @return boolean
     */
    public boolean pathClear(int oldCol, int oldRow, int newCol, int newRow ) {

        //vertical check
        if(oldCol == newCol) {
            while (oldRow!=newRow) {
                if(oldRow>newRow) {
                    if (Chessboard.chessBoard[oldRow-1][oldCol].getPiece() != null && newRow != oldRow - 1) {
                        return false;
                    }
                    oldRow--;
                    //System.out.println("row " + oldRow +" column"+ oldCol);
                }
                else if (oldRow<newRow) {
                    if (Chessboard.chessBoard[oldRow+1][oldCol].getPiece() != null && newRow != oldRow + 1) {
                        return false;
                    }
                    oldRow++;
                    //System.out.println("row " + oldRow +" column"+ oldCol);
                }

            }
            //System.out.println("debug a");
            return true;
        }

//horizontal check
        else if (oldRow==newRow){
            while (oldCol!=newCol) {
                if(oldCol>newCol) {
                    if (Chessboard.chessBoard[oldRow][oldCol-1].getPiece() != null && newCol != oldCol - 1) {
                        return false;
                    }
                    oldCol--;
                    //System.out.println("row " + oldRow +" column"+ oldCol);
                }
                else if (oldCol<newCol) {
                    if (Chessboard.chessBoard[oldRow][oldCol+1].getPiece() != null && newCol != oldCol + 1) {
                        return false;
                    }
                    oldCol++;
                    //	System.out.println("row " + oldRow +" column"+ oldCol);
                }

            }
            //	System.out.println("debug b");
            return true;
        }

        //diagonal check
        else if(oldRow!=newRow && oldCol != newCol){


            //can only move diagonally the SAME number of spaces each direction
            if (Math.abs(newRow - oldRow) != Math.abs(newCol-oldCol)) {
                //System.out.println("number of rows and columns not equal");
                return false;
            }



            //System.out.println("In diagonal check");
            //System.out.println("old Row:" + oldRow);
            //System.out.println("old Col:" + oldCol);
            //System.out.println("new Row:" + newRow);
            //System.out.println("new Col:" + newCol);

            do{
                //System.out.println("In do");
                //System.out.println("old Row:" + oldRow);
                //System.out.println("old Col:" + oldCol);
                //System.out.println("new Row:" + newRow);
                //System.out.println("new Col:" + newCol);

                if (oldCol<newCol) {
                    oldCol++;
                }
                else {
                    oldCol--;
                }

                if(oldRow<newRow) {
                    oldRow++;
                }
                else {
                    oldRow--;
                }


                if ((Chessboard.chessBoard[oldRow][oldCol].getPiece()!=null) && (oldCol!=newCol && oldRow!=newRow)) {

                    return false;
                }

            } while (oldCol!=newCol && oldRow != newRow);
            //System.out.println("debug c");
            return true;



        }

        return false;
    }

    /**
     * move(int oldCol, int oldRow, int newCol, int newRow, char promo)
     * moves a Queen piece if isValidMove is true and destroys any pieces on applicable collision at new position
     * @param oldCol - old block's File
     * @param oldRow - old block's Rank
     * @param newCol - new block's File
     * @param newRow - new blocks Rank
     * @param promo - promotion value Q/R/N/B for pawn promotion
     *
     * @return boolean
     */
    public boolean move(int oldCol, int oldRow, int newCol, int newRow , char promo) {


        if(isValidMove(oldCol, oldRow, newCol, newRow)) {


            // not a valid move for current player for ANY piece
            // if current players king will be in check as a result
            // move is disallowed
            if (King.isPlayerKingInCheck(oldRow, oldCol, newRow, newCol)) {
                return false;
            }
            else {

                if (Chessboard.whitesTurn==true) {
                    Chessboard.whiteincheck=false;
                }
                if (Chessboard.blacksTurn==true) {
                    Chessboard.blackincheck=false;
                }
            }



            Chessboard.chessBoard[newRow][newCol].setPiece(Chessboard.chessBoard[oldRow][oldCol].getPiece());
            Chessboard.chessBoard[oldRow][oldCol].setPiece(null);
            Chessboard.chessBoard[newRow][newCol].getPiece().moved();

            if (King.isOpponentKingInCheck(newRow, newCol)){
                if (Chessboard.whitesTurn==true) {
                    Chessboard.blackincheck=true;
                }
                if (Chessboard.blacksTurn==true) {
                    Chessboard.whiteincheck=true;
                }
            }
            else {
                if (Chessboard.whitesTurn==true) {
                    Chessboard.blackincheck=false;
                }
                if (Chessboard.blacksTurn==true) {
                    Chessboard.whiteincheck=false;
                }
            }
            if(King.isOpponentKinginCheckmate(newRow, newCol)) {
                Chessboard.checkMate=true;
            }


            Pawn.r = -1;
            Pawn.c = -1;
            return true;
        }
        return false;
    }

}
