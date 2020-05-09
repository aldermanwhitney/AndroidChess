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

public class Pawn extends Pieces{

    static int oR = -1, oC = -1;

    static Pieces enpL = null , enpR = null;

    static boolean enpassantL, enpassantR;
    static boolean canEnp;
    public static int r = -1, c = -1;

    /**
     * Pawn(String color)
     * Constructor for a Pawn Piece with given color and type pawn,
     *
     * @param color - color black or white using parameter
     */
    public Pawn(String color) {
        super(color);
        type = "pawn";
    }

    /**
     * isValidMove(int oldCol, int oldRow, int newCol, int newRow)
     * checks to see if a move is valid from old position to new position for a Pawn piece
     * @param oldCol - old block's File
     * @param oldRow - old block's Rank
     * @param newCol - new block's File
     * @param newRow - new blocks Rank
     *
     *
     * @return boolean
     */


    public boolean isValidMove(int oldCol, int oldRow, int newCol, int newRow){

        //System.out.println("In is valid move for pawn");

        //forward move with in an empty block
        if(Chessboard.chessBoard[newRow][newCol].getPiece()!=null) {
            {

                //diagonal move of pawn where it can beat opponent's piece
                //first checks if pieces at new and old positions are of opposing sides

                if((Chessboard.chessBoard[newRow][newCol].getPiece().isWhite() && !Chessboard.chessBoard[oldRow][oldCol].getPiece().isWhite()) ||
                        (!Chessboard.chessBoard[newRow][newCol].getPiece().isWhite() && Chessboard.chessBoard[oldRow][oldCol].getPiece().isWhite())) {
                    //adjacent column only
                    if(newCol==oldCol-1 || newCol==oldCol+1) {
                        //for black piece and next row
                        if(!Chessboard.chessBoard[oldRow][oldCol].getPiece().isWhite() && newRow==oldRow+1) {
                            //enpassantL = false;
                            //enpassantR = false;
                            return true;
                        }
                        //white piece and forward row
                        else if (Chessboard.chessBoard[oldRow][oldCol].getPiece().isWhite() && newRow==oldRow-1) {
                            //	enpassantL = false;
                            //enpassantR = false;
                            return true;
                        }


                    }

                }
                //enpassantL = false;
                //enpassantR = false;
                return false;
            }
        }


        else {
            //vertical forward move for white piece

            //System.out.println("Moving white piece with no piece in way");
            if(Chessboard.chessBoard[oldRow][oldCol].getPiece().isWhite()) {

                //System.out.println("here 1");
                //first move of white pawn is at row 6 and
                //clear path checks when first move is moving over 2 blocks
                if(oldRow==6 && (newRow ==oldRow-2) ) {
                    //System.out.println("here 2");
                    //System.out.println("first move valid");
                    //moves the pawn only if new position has no pieces
                    if(pathClear(oldCol,oldRow, newCol, newRow) ) {
                        //System.out.println("path is clear");

                        if(newCol != 0 && newCol!= 7) {
                            if(Chessboard.chessBoard[newRow][newCol-1].getPiece()!=null && !Chessboard.chessBoard[newRow][newCol-1].getPiece().isWhite())
                            {
                                if(Chessboard.chessBoard[newRow][newCol-1].getPieceType().equals("p")) {

                                    enpL= Chessboard.chessBoard[newRow][newCol-1].getPiece();
                                    enpassantL = true;
                                    //System.out.println("here 3 enpL = " + enpassantL);
                                }

                                //else {enpassantL =false;}
                            }

                            if(Chessboard.chessBoard[newRow][newCol+1].getPiece()!=null && !Chessboard.chessBoard[newRow][newCol+1].getPiece().isWhite())
                            {

                                if(Chessboard.chessBoard[newRow][newCol+1].getPieceType().equals("p")) {
                                    enpR= Chessboard.chessBoard[newRow][newCol+1].getPiece();
                                    enpassantR = true;
                                    //System.out.println("here 3 enpR = " + enpassantR);
                                }

                                //else {enpassantR = false;}
                            }
							/*
							if(Chessboard.chessBoard[newRow][newCol-1].getPiece()==null || Chessboard.chessBoard[newRow][newCol-1].getPiece().isWhite()) {
								enpassantL = false;
							}

							if(Chessboard.chessBoard[newRow][newCol+1].getPiece()==null || Chessboard.chessBoard[newRow][newCol+1].getPiece().isWhite()) {
								enpassantR = false;
							}
							*/
                            //System.out.println("here 3");
                        }

                        else if (newCol==0) {

                            if(Chessboard.chessBoard[newRow][newCol+1].getPiece()!=null && !Chessboard.chessBoard[newRow][newCol+1].getPiece().isWhite())
                            {
                                if(Chessboard.chessBoard[newRow][newCol+1].getPieceType().equals("p")) {
                                    enpR= Chessboard.chessBoard[newRow][newCol+1].getPiece();
                                    enpassantR = true;
                                }
                                //else {enpassantR = false;}
                            }

							/*else {

								enpassantR = false;
							}*/
                            //	System.out.println("here 4");
                        }
                        else if (newCol == 7) {
                            if(Chessboard.chessBoard[newRow][newCol-1].getPiece()!=null && !Chessboard.chessBoard[newRow][newCol-1].getPiece().isWhite())
                            {
                                if(Chessboard.chessBoard[newRow][newCol-1].getPieceType().equals("p")) {
                                    enpL= Chessboard.chessBoard[newRow][newCol-1].getPiece();
                                    enpassantL = true;
                                }
                                //else { enpassantL = false; }
                            }
						/*	else {

									enpassantL = false;

								}*/
                        }

                        //	System.out.println("here 5");

                        if(enpassantL || enpassantR) {
                            oR = newRow;
                            oC = newCol;
                        }

                        //System.out.println("here 7");
                        return true;
                    }



                }




                else if (oldRow==6 && newRow == oldRow-1) {
                    //System.out.println("here 8");
                    if(pathClear(oldCol,oldRow, newCol, newRow) ) {
                        //System.out.println("path is clear");
                        //	enpassantL = false;
                        //	enpassantR = false;
                        canEnp=false;
                        return true;
                    }


                }

                //if its not the first move of respective pawn, then we can only move one block forward
                //given that new position has no piece in that block
                else if ((oldRow!=6 && newRow == oldRow-1) && (oldCol == newCol)) {
                    //System.out.println("here 9");
                    //	System.out.println(enpassantL + " enpassant value 1" + enpassantR +" " + r + c);
                    //	enpassantL = false;
                    //enpassantR = false;
                    r = -1;
                    c = -1;
                    canEnp=false;
                    return true;


                }

                else if ((oldRow==3 && newRow == 2) && (newCol==oldCol+1  || newCol == oldCol-1)) {
                    //System.out.println("here 10");
                    //System.out.println(enpassantL + " enpassant value 2" + enpassantR+" " + r + c);
                    if( enpassant(oldCol, oldRow, newCol, newRow)) canEnp = true;
                    else canEnp = false;
                    return canEnp;

                }

                /*else{
                    canEnp=false;
                }*/

                //System.out.println("here 11");
            }



            //vertical forward move for black piece
            else if (!Chessboard.chessBoard[oldRow][oldCol].getPiece().isWhite()) {

                //first move of black pawn is at row 1 and
                //clear path checks when first move is moving over 2 blocks
                if(oldRow==1 && (newRow ==oldRow+2) ) {

                    //moves the pawn only if new position has no pieces
                    if(pathClear(oldCol,oldRow, newCol, newRow)) {
                        //System.out.println("here");

                        if((newCol != 0 && newCol!= 7)) {
                            if(Chessboard.chessBoard[newRow][newCol-1].getPiece()!=null && Chessboard.chessBoard[newRow][newCol-1].getPiece().isWhite())
                            {
                                if(Chessboard.chessBoard[newRow][newCol-1].getPieceType().equals("p")) {

                                    enpL= Chessboard.chessBoard[newRow][newCol-1].getPiece();
                                    enpassantL = true;
                                    //System.out.println("here 3 enpL = " + enpassantL);
                                }

                                //	else {enpassantL =false;}
                            }


                            if(Chessboard.chessBoard[newRow][newCol+1].getPiece()!=null && Chessboard.chessBoard[newRow][newCol+1].getPiece().isWhite())
                            {

                                if(Chessboard.chessBoard[newRow][newCol+1].getPieceType().equals("p")) {
                                    enpR= Chessboard.chessBoard[newRow][newCol+1].getPiece();
                                    enpassantR = true;
                                    //System.out.println("here 3 enpR = " + enpassantR);
                                }

                                //else {enpassantR = false;}
                            }
					/*


						if(Chessboard.chessBoard[newRow][newCol-1].getPiece()==null || !Chessboard.chessBoard[newRow][newCol-1].getPiece().isWhite()) {
							enpassantL = false;
						}

						if(Chessboard.chessBoard[newRow][newCol+1].getPiece()==null || !Chessboard.chessBoard[newRow][newCol+1].getPiece().isWhite()) {
							enpassantR = false;
						}
						*/
                            //System.out.println("here 3 ");



                        }

                        else if (newCol==0) {
                            if(Chessboard.chessBoard[newRow][newCol+1].getPiece()!=null && Chessboard.chessBoard[newRow][newCol+1].getPiece().isWhite())
                            {	if(Chessboard.chessBoard[newRow][newCol+1].getPieceType().equals("p")) {
                                enpR= Chessboard.chessBoard[newRow][newCol+1].getPiece();
                                enpassantR = true;
                            }
                                //else {enpassantR = false;}
                            }

						/*	else {

								enpassantR = false;
							}*/
                        }
                        else if (newCol == 7) {
                            if(Chessboard.chessBoard[newRow][newCol-1].getPiece()!=null && Chessboard.chessBoard[newRow][newCol-1].getPiece().isWhite())
                            {
                                if(Chessboard.chessBoard[newRow][newCol-1].getPieceType().equals("p")) {
                                    enpL= Chessboard.chessBoard[newRow][newCol-1].getPiece();
                                    enpassantL = true;
                                }
						/*		else
								{ enpassantL = false; }
							}
							else {

									enpassantL = false;
								*/
                            }
                        }

                        if(enpassantL || enpassantR) {
                            oR = newRow;
                            oC = newCol;
                        }

                        return true;
                    }

                }

                else if (oldRow==1 && (newRow == oldRow+1) ) {
                    if(pathClear(oldCol,oldRow, newCol, newRow)) {
                        //System.out.println("here");
                        //	enpassantL = false;
                        //	enpassantR = false;
                        canEnp=false;
                        return true;
                    }
                }

                //if its not the first move of respective pawn, then we can only move one block forward
                //given that new position has no piece in that block
                else if ((oldRow!=1 && newRow == oldRow+1) && (oldCol == newCol)){

                    //	enpassantL = false;
                    //	enpassantR = false;
                    r = -1;
                    c = -1;
                    canEnp=false;
                    return true;


                }

                else if ((oldRow==4 && newRow == 5) && (newCol==oldCol+1  || newCol ==oldCol-1)) {
                    //	System.out.println(enpassantL + " enpassant value " + enpassantR);
                    if( enpassant(oldCol, oldRow, newCol, newRow)) canEnp = true;
                    else canEnp = false;
                    return canEnp;
                }
                /*
                else{
                    canEnp=false;
                }*/

            }
            //System.out.println("here 12");
            return false; //false if new position has a piece
        }



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
    public boolean pathClear(int oldCol, int oldRow, int newCol, int newRow) {

        //only need vertical path check for pawn, if not in the same column then returns false
        //which in turn makes isValidMove false
        if(oldCol == newCol) {
            while (oldRow!=newRow) {
                if(oldRow>newRow) {
                    if (Chessboard.chessBoard[oldRow-1][oldCol].getPiece() != null) {
                        return false;
                    }
                    oldRow--;
                }
                else if (oldRow<newRow) {
                    if (Chessboard.chessBoard[oldRow+1][oldCol].getPiece() != null) {
                        return false;
                    }
                    oldRow++;
                }
                Pawn.r = oldRow; Pawn.c = oldCol;
                //System.out.println("row col: " + oldRow + oldCol);
                return true;
            }
        }


        return false;
    }

    /**
     * move(int oldCol, int oldRow, int newCol, int newRow, char promo)
     * moves a Pawn piece if isValidMove is true and destroys any pieces on applicable collision at new position
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



            if((oldRow==1 && newRow == 0) && Chessboard.chessBoard[oldRow][oldCol].getPiece().isWhite() ) {

                Chessboard.chessBoard[oldRow][oldCol].setPiece(null);
                //	Chessboard.chessBoard[newRow][newCol].getPiece().moved();
                //System.out.println("promotion value: " + promo);
                switch(promo) {
                    case 'Q' : Chessboard.chessBoard[newRow][newCol].setPiece(new Queen("white")); Chessboard.chessBoard[newRow][newCol].getPiece().moved(); break;
                    case 'N' : Chessboard.chessBoard[newRow][newCol].setPiece(new Knight("white")); Chessboard.chessBoard[newRow][newCol].getPiece().moved();break;
                    case 'B' : Chessboard.chessBoard[newRow][newCol].setPiece(new Bishop("white")); Chessboard.chessBoard[newRow][newCol].getPiece().moved();break;
                    case 'R' : Chessboard.chessBoard[newRow][newCol].setPiece(new Rook("white")); Chessboard.chessBoard[newRow][newCol].getPiece().moved();break;
                    default: return true;

                }
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
                //System.out.println("promotion value: " + promo);
                return true;
            }

            else if ((oldRow==6 && newRow == 7) && !Chessboard.chessBoard[oldRow][oldCol].getPiece().isWhite() ) {
                Chessboard.chessBoard[oldRow][oldCol].setPiece(null);
                //	Chessboard.chessBoard[newRow][newCol].getPiece().moved();
                //	System.out.println("promotion value: " + promo);
                switch(promo) {
                    case 'Q' : Chessboard.chessBoard[newRow][newCol].setPiece(new Queen("black")); Chessboard.chessBoard[newRow][newCol].getPiece().moved();break;
                    case 'N' : Chessboard.chessBoard[newRow][newCol].setPiece(new Knight("black")); Chessboard.chessBoard[newRow][newCol].getPiece().moved(); break;
                    case 'B' : Chessboard.chessBoard[newRow][newCol].setPiece(new Bishop("black")); Chessboard.chessBoard[newRow][newCol].getPiece().moved();break;
                    case 'R' : Chessboard.chessBoard[newRow][newCol].setPiece(new Rook("black")); Chessboard.chessBoard[newRow][newCol].getPiece().moved();break;
                    default: return true;

                }
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

                //	System.out.println("promotion value: " + promo);
                return true;
            }
            else {

                if(canEnp){
                    Chessboard.chessBoard[oR][oC].setPiece(null);
                }
                Chessboard.chessBoard[newRow][newCol].setPiece(Chessboard.chessBoard[oldRow][oldCol].getPiece());
                Chessboard.chessBoard[oldRow][oldCol].setPiece(null);
                Chessboard.chessBoard[newRow][newCol].getPiece().moved();



                //this will probably need to be added after your switch statements too, idk
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







                return true;
            }

        }
        return false;
    }


    /**
     * enpassant(int oldCol, int oldRow, int newCol, int newRow)
     * Checks if an enpassant Pawn move is valid and allowed. If so, return true.
     * @param oldCol - old block's File
     * @param oldRow - old block's Rank
     * @param newCol - new block's File
     * @param newRow - new blocks Rank
     *
     * @return boolean
     */
    public boolean enpassant(int oldCol, int oldRow, int newCol, int newRow) {
        if(Pawn.r==newRow && Pawn.c == newCol) {

            if(enpassantL) {

                if(Chessboard.chessBoard[oldRow][oldCol].getPiece().equals(enpL)) {
                    enpassantL = false;
                    enpassantR = false;
                    enpL = null;
                    enpR = null;
                    return true;
                }

            }

            if(enpassantR) {
                if(Chessboard.chessBoard[oldRow][oldCol].getPiece().equals(enpR) ){
                    enpassantL = false;
                    enpassantR = false;
                    enpL = null;
                    enpR = null;
                    return true;
                }
            }


        }

        enpassantL = false;
        enpassantR = false;
        enpL = null;
        enpR = null;

        return false;
    }

}



