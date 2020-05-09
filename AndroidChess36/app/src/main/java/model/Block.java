package model;

/**
 * @authors Gazal Arora and Whitney Alderman
 */
//package chess;
//import pieces.Pieces;

import controller.*;
import view.*;
import model.*;

public class Block {

    /**
     * A block is an individual square/cell on the Chess Board
     * occupied by a piece blockP
     *
     */

    public Pieces blockP;

    /**
     * color of the block is given by boolean isBlack
     */
    public boolean isBlack;


    /**
     * constructor for a null Piece with given color
     * @param color - given color of the block black or white
     */

    public Block(String color) {
        isBlack = color.equals("black");
        blockP = null;
    }

    /**
     * constructor for a given Piece with given color
     * @param piece - given piece at the block
     * @param color - given color of the block black or white
     */
    public Block(Pieces piece, String color) {
        isBlack = color.equals("black");
        blockP = piece;
    }

    /**
     * getPieceType()
     * @return String
     *
     * Returns what type of piece is occupying the current block
     */
    public String getPieceType()
    {
        switch(blockP.type)
        {
            case "rook":  return "R";
            case "knight": return "N";
            case "bishop": return "B";
            case "pawn": return "p";
            case "king": return "K";
            case "queen": return "Q";
            default: return "";
        }
    }

    /**
     * setPiece()
     * This sets a given piece to the currently addressed block
     * @param piece - this is the piece to be placed on the block
     *
     *
     */
    public void setPiece(Pieces piece) {
        blockP = piece;
    }


    /** toString()
     * This prints information of the piece color and piece type
     * @return String
     *
     * Returns piece color as a String and type of piece occupying the block
     */


    public String toString() {
        return getPieceColor() + getPieceType();
    }
	/*
	public Object clone() throws CloneNotSupportedException {
	    return super.clone();
	}
	*/

    /**
     * isSquareBlack()
     * Checks if the block is black or not
     * @return boolean
     *
     * - Returns true if block is black
     */
    public boolean isSquareBlack()
    {
        return isBlack;
    }

    /**
     * getPiece()
     * Gets the piece that is on the current block
     * @return Piece
     *
     * - piece that is on the current block
     */
    public Pieces getPiece()
    {
        return blockP;
    }

    /**getPieceColor()
     *
     * @return String
     * Returns color of the piece on the current block
     *
     */
    public String getPieceColor()
    {
        if(blockP.isWhite())
            return "w";
        else
            return "b";
    }



}
