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

public class Rook extends Pieces {

    /**
     * Rook(String color)
     * Constructor for a Rook Piece with given color and type Rook,
     *
     * @param color - color black or white using parameter
     */
    public Rook(String color) {
        super(color);
        type = "rook";
    }

    /**
     * isValidMove(int oldCol, int oldRow, int newCol, int newRow)
     * checks to see if a move is valid from old position to new position for a Rook piece
     * @param oldCol - old block's File
     * @param oldRow - old block's Rank
     * @param newCol - new block's File
     * @param newRow - new blocks Rank
     * @return boolean
     */
    public boolean isValidMove(int oldCol, int oldRow, int newCol, int newRow) {
        if (oldCol != newCol && oldRow != newRow) {
            //System.out.println("in isvalid move 1");
            return false;
        }
        //System.out.println("in isvalid move 2");
        boolean pathClear = pathClear(oldCol, oldRow, newCol, newRow);
        //System.out.println("in isvalid move 3 "+ pathClear);
        if (pathClear) {
            //if (Chessboard.chessBoard[newRow][newCol].getPieceType().equals("king")) System.out.println("checkmate");
            //else {

            if(Chessboard.chessBoard[newRow][newCol].getPiece() != null) {
                if((Chessboard.chessBoard[newRow][newCol].getPiece().isWhite() && !Chessboard.chessBoard[oldRow][oldCol].getPiece().isWhite()) ||
                        (!Chessboard.chessBoard[newRow][newCol].getPiece().isWhite() && Chessboard.chessBoard[oldRow][oldCol].getPiece().isWhite())) {
                    return true;
                }
            }

            else {

                //System.out.println("in isvalid move 4");
                return true;
            }

        }
        //System.out.println("in isvalid move 5");
        return false;
    }

    /**
     * pathClear(int oldCol, int oldRow, int newCol, int newRow)
     * Checks if the path from old position to new position is clear, returns false if it isn't
     * @param oldCol - old block's File
     * @param oldRow - old block's Rank
     * @param newCol - new block's File
     * @param newRow - new blocks Rank
     *
     * @return boolean
     */
    public boolean pathClear(int oldCol, int oldRow, int newCol, int newRow) {

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

            return true;
        }

        //horizontal check
        else{
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
            return true;
        }


    }

    /**
     * move(int oldCol, int oldRow, int newCol, int newRow, char promo)
     * moves a Rook piece if isValidMove is true and destroys any pieces on applicable collision at new position
     * @param oldCol - old block's File
     * @param oldRow - old block's Rank
     * @param newCol - new block's File
     * @param newRow - new blocks Rank
     * @param promo - promotion value Q/R/N/B for pawn promotion
     *
     * @return boolean
     */
    public boolean move(int oldCol, int oldRow, int newCol, int newRow, char promo) {
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
