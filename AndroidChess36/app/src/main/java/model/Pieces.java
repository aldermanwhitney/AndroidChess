package model;

/**
 * @authors Gazal Arora and Whitney Alderman
 */

//package pieces;

//import chess.Block;
//import chess.Chessboard;

import java.io.Serializable;

import controller.*;
import view.*;
import model.*;
import controller.Chessboard;

public class Pieces implements Serializable {

    private static final long serialVersionUID = 1L;

    /** isWhite boolean is true if piece is white, false if piece is black */
    public boolean isWhite;

    /** Type of the piece bishop/king/queen/rook/pawn/knight as a string
     *
     */
    public String type;

    /**
     * boolean value hasMoved for a piece when the piece has moved
     */
    boolean hasMoved;

    /**
     * Pieces(String color)
     * constructor that creates a new instance of Piece with type as null piece,
     * color black or white using parameters
     * @param color - color of the piece black or white
     */

    public Pieces(String color) {
        isWhite = color.equals("white");
        type = null;
        hasMoved = false;
    }




    /**
     * isWhite()
     * Checks to see whether a piece is white, returns true if white, false if black
     * @return boolean
     *
     *
     */
    public boolean isWhite() {
        return isWhite;
    }

    /**
     * hasMoved()
     * Checks to see whether a piece has moved previously, returns true if it has
     * @return boolean
     *
     *
     */
    public boolean hasMoved() {
        return hasMoved;
    }

    /**
     * getType()
     * returns a String as the type of the piece
     * @return String
     *
     *
     */
    public String getType() {
        return type;
    }

    /**
     * moved()
     * Updates the variable hasMoved as true to indicate that a piece has moved
     *
     *
     */
    public void moved() {
        hasMoved = true;
    }

	/*
	 * testForCheck()
	 * @param oldCol, oldRow, newRow, newC
	 * Checks whether a piece puts enemy king in check, returns true if king is in check
	 * @return boolean
	 *
	 *

	public boolean testForCheck(int oldCol, int oldRow, int newCol, int newRow, Block[][] board) {
		//needs to be implemented
		return false;
	}

	/**
	 * getEnemyKingLocation()
	 * Gets enemy's king's location as a string
	 * @return string
	 *
	 *


	public int getEnemyKingLocation(Block[][] board, boolean currWhite) {
		//needs to be implemented
		return 0;
	}*/

    /**
     * isValidMove abstract method
     * checks to see if move is valid from old position to new position
     * @param oldCol - old block's File
     * @param oldRow - old block's Rank
     * @param newCol - new block's File
     * @param newRow - new blocks Rank
     * @return boolean
     */
    public boolean isValidMove(int oldCol, int oldRow, int newCol, int newRow) {
        return false;
    }

    /**
     * move abstract method
     * moves a piece if isValidMove is true and destroys any pieces on applicable collision at new position
     * @param oldCol - old block's File
     * @param oldRow - old block's Rank
     * @param newCol - new block's File
     * @param newRow - new blocks Rank
     * @param promo - promotion value Q/R/N/B for pawn promotion
     *
     * @return boolean
     */
    public boolean move(int oldCol, int oldRow, int newCol, int newRow, char promo){
        return false;
    }

    /**
     * pathClear abstract method
     * Checks if the path from old position to new position is clear, returns false if it isn't
     * @param oldCol - old block's File
     * @param oldRow - old block's Rank
     * @param newCol - new block's File
     * @param newRow - new blocks Rank
     *
     * @return boolean
     */
    public boolean pathClear(int oldCol, int oldRow, int newCol, int newRow){
        return false;
    }

	/*
	 * canEnpassant()
	 * @return boolean
	 *
	 * Returns boolean indicating whether this piece can enpassant
	 *
	public boolean canEnpassant()
	{
		return false;
	}

	/**
	 * getEnpassant()
	 * @return boolean
	 *
	 * Returns boolean indicating whether this piece can be captured by enpassant
	 *
	public boolean getEnpassant()
	{
		return false;
	}

	public Object clone() throws CloneNotSupportedException {
	    return super.clone();
	}

	/**
	 * setHasMoved
	 * @param m
	 * true if has moved, else false
	 *
	public void setHasMoved(boolean m) {
		hasMoved = m;
	}
	*/

	public String getColorAndName(){
	 String piececolor;

	    if (this.isWhite){
	        piececolor="white";
        }
	    else{
	        piececolor="black";
        }
	    return piececolor+this.type;

    }


}
