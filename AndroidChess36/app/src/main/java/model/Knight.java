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

public class Knight extends Pieces{

    /**
     * Knight(String color)
     * Constructor for a Knight Piece with given color and type knight,
     *
     * @param color - color black or white using parameters
     */
    public Knight(String color) {
        super(color);
        type = "knight";
    }

    /**
     * isValidMove(int oldCol, int oldRow, int newCol, int newRow)
     * checks to see if a move is valid from old position to new position for a Knight piece
     * @param oldCol - old block's File
     * @param oldRow - old block's Rank
     * @param newCol - new block's File
     * @param newRow - new blocks Rank
     *
     *
     * @return boolean
     */
    public boolean isValidMove(int oldCol, int oldRow, int newCol, int newRow) {

        //System.out.println("debug: knight");

        //check correct dimensions
        if (oldRow+2!=newRow && oldRow-2!=newRow && oldRow+1!=newRow && oldRow-1!=newRow) {
            //System.out.println("debug: k1");
            return false;
        }

        if (oldCol+2!=newCol && oldCol-2!=newCol && oldCol+1!=newCol && oldCol-1!=newCol) {
            //System.out.println("debug: k2");
            return false;
        }

        //make sure it is an L shaped jump:
        //2 horizontal and 1 vertical space or
        //1 vertical and 2 horizontal spaces
        if ((oldRow+2==newRow || oldRow-2==newRow) && !(oldCol+1 == newCol || oldCol-1 == newCol)) {
            //	System.out.println("debug: k3");
            return false;
        }
        if ((oldCol+2==newCol || oldCol-2==newCol) && !(oldRow+1 == newRow || oldRow-1 == newRow)) {
            //System.out.println("debug: k4");
            return false;
        }

        if ((oldCol+1==newCol || oldCol-1==newCol) && (oldRow+1==newRow || oldRow-1==newRow)) {
            //System.out.println("debug: k4.5");
            return false;
        }

        if ((oldCol+2==newCol || oldCol-2==newCol) && (oldRow+2==newRow || oldRow-2==newRow)) {
            //System.out.println("debug: k4.6");
            return false;
        }



        //There is no chess piece in destination
        if (Chessboard.chessBoard[newRow][newCol].getPiece()==null) {
            //move the piece
            //Chessboard.chessBoard[newRow][newCol].setPiece(Chessboard.chessBoard[oldRow][oldCol].getPiece());
            //Chessboard.chessBoard[oldRow][oldCol].setPiece(null);

            //Chessboard.chessBoard[newRow][newCol].getPiece().moved();

            //	System.out.println("debug: k5");
            return true;
        }



        //There is a chess piece already in destination
        if (Chessboard.chessBoard[newRow][newCol].getPiece()!=null) {

            //System.out.println("debug: k6");


            //if the player's own piece is in the destination
            if ((Chessboard.chessBoard[oldRow][oldCol].getPiece().isWhite() && Chessboard.chessBoard[newRow][newCol].getPiece().isWhite()) ||
                    (!Chessboard.chessBoard[oldRow][oldCol].getPiece().isWhite() && !Chessboard.chessBoard[newRow][newCol].getPiece().isWhite())) {
                //System.out.println("debug: k7");
                return false;
            }

            //if the current player's opponent's piece is in the destination
            if ((Chessboard.chessBoard[oldRow][oldCol].getPiece().isWhite() && !Chessboard.chessBoard[newRow][newCol].getPiece().isWhite()) ||
                    (!Chessboard.chessBoard[oldRow][oldCol].getPiece().isWhite() && Chessboard.chessBoard[newRow][newCol].getPiece().isWhite())) {

                //move the piece
                //Chessboard.chessBoard[newRow][newCol].setPiece(Chessboard.chessBoard[oldRow][oldCol].getPiece());
                //Chessboard.chessBoard[oldRow][oldCol].setPiece(null);
                //Chessboard.chessBoard[newRow][newCol].getPiece().moved();
                //System.out.println("debug: k8");
                return true;

            }

            //System.out.println("debug: k");
            return false;
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
     *
     * @return boolean
     */
    public boolean pathClear(int oldCol, int oldRow, int newCol, int newRow) {
        return false;
    }


    /**
     * move(int oldCol, int oldRow, int newCol, int newRow, char promo)
     * moves a Knight piece if isValidMove is true and destroys any pieces on applicable collision at new position
     * @param oldCol - old block's File
     * @param oldRow - old block's Rank
     * @param newCol - new block's File
     * @param newRow - new blocks Rank
     * @param promo - promotion value Q/R/N/B for pawn promotion
     *
     * @return boolean
     */
    public boolean move(int oldCol, int oldRow, int newCol, int newRow, char promo) {
        if (isValidMove(oldCol, oldRow, newCol, newRow)) {

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
        //Chessboard.chessBoard;
    }

}
