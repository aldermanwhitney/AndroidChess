package model;


/**
 * @authors Gazal Arora and Whitney Alderman
 */
//package pieces
//import chess.Block;
//import chess.Chessboard;

import controller.*;
import view.*;
import model.*;

public class Bishop extends Pieces{

    /**
     * Bishop(String color)
     * Constructor for a Bishop Piece with given color and type Bishop
     *
     * @param color - color black or white using parameter
     */
    public Bishop(String color) {
        super(color);
        type = "bishop";
    }

    //note: only thing left to edit in this, or maybe a different class/method
    //is the case in which each player only has one bishop left
    // one player may also have one or two pawns more than the other (see wiki)
    //- this is a draw


    /**
     * isValidMove(int oldCol, int oldRow, int newCol, int newRow)
     * checks to see if a move is valid from old position to new position for a Bishop piece
     * @param oldCol - old block's File
     * @param oldRow - old block's Rank
     * @param newCol - new block's File
     * @param newRow - new blocks Rank
     *
     * @return boolean
     */
    public boolean isValidMove(int oldCol, int oldRow, int newCol, int newRow) {


        //System.out.println("debug: Bishop");

        //can only move diagonally
        if (Math.abs(newRow - oldRow) != Math.abs(newCol-oldCol)) {
            //	System.out.println("b2");
            return false;
        }

        if (!pathClear (oldCol, oldRow, newCol, newRow)) {
            //	System.out.println("b3");
            return false;
        }

        //if there is no piece there move it
        if (Chessboard.chessBoard[newRow][newCol].getPiece()==null){
            //System.out.println("b4");
            //move the piece
            //Chessboard.chessBoard[newRow][newCol].setPiece(Chessboard.chessBoard[oldRow][oldCol].getPiece());
            //Chessboard.chessBoard[oldRow][oldCol].setPiece(null);
            //Chessboard.chessBoard[newRow][newCol].getPiece().moved();
            return true;
        }

        //there is a piece there
        else {

            //System.out.println("b5");
            //if the player's own piece is in the destination
            if ((Chessboard.chessBoard[oldRow][oldCol].getPiece().isWhite() && Chessboard.chessBoard[newRow][newCol].getPiece().isWhite()) ||
                    (!Chessboard.chessBoard[oldRow][oldCol].getPiece().isWhite() && !Chessboard.chessBoard[newRow][newCol].getPiece().isWhite())) {
                //	System.out.println("b6");
                return false;
            }

            //if the current player's opponent's piece is in the destination
            if ((Chessboard.chessBoard[oldRow][oldCol].getPiece().isWhite() && !Chessboard.chessBoard[newRow][newCol].getPiece().isWhite()) ||
                    (!Chessboard.chessBoard[oldRow][oldCol].getPiece().isWhite() && Chessboard.chessBoard[newRow][newCol].getPiece().isWhite())) {
                //System.out.println("b7");

                //move the piece
                //Chessboard.chessBoard[newRow][newCol].setPiece(Chessboard.chessBoard[oldRow][oldCol].getPiece());
                //Chessboard.chessBoard[oldRow][oldCol].setPiece(null);
                //Chessboard.chessBoard[newRow][newCol].getPiece().moved();
                return true;



            }



            return false;
        }
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

        do{
            if (oldCol<newCol) {
                oldCol++;
            }
            else if (oldCol>newCol){
                oldCol--;
            }
            else {

            }


            if(oldRow<newRow) {
                oldRow++;
            }
            else if (oldRow>newRow) {
                oldRow--;
            }
            else {

            }

            if (Chessboard.chessBoard[oldRow][oldCol].getPiece()!=null) {

                ///null ptr and index out of bounds -1
                if ((Chessboard.chessBoard[oldRow][oldCol].getPiece()!=null) && (oldCol!=newCol && oldRow!=newRow)) {
                    //System.out.println("b10");
                    return false;
                }
            }

        }
        while (oldCol!=newCol && oldRow != newRow);

        //	System.out.println("b11");
        return true;
    }

    /**
     * move(int oldCol, int oldRow, int newCol, int newRow, char promo)
     * moves a Bishop piece if isValidMove is true and destroys any pieces on applicable collision at new position
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


            //move the piece
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

