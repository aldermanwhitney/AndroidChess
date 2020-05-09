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

public class King extends Pieces {

    /**
     * King(String color)
     * Constructor for a King Piece with given color and type King,
     *
     * @param color - color black or white using parameter
     */
    public King(String color) {
        super(color);
        type = "king";
    }

    public static int oldRookRow = -1;
    public static int oldRookCol = -1;
    public static int newRookRow = -1;
    public static int newRookCol = -1;

    public static boolean castlingcheck = false;
    public static boolean iscastlingmove = false;

    /**
     * isValidMove(int oldCol, int oldRow, int newCol, int newRow)
     * checks to see if a move is valid from old position to new position for a King piece
     * @param oldCol - old block's File
     * @param oldRow - old block's Rank
     * @param newCol - new block's File
     * @param newRow - new blocks Rank
     *
     * @return boolean
     */
    public boolean isValidMove(int oldCol, int oldRow, int newCol, int newRow) {

        //System.out.println("in isValidMove(): King");

        if (Chessboard.chessBoard[oldRow][oldCol].getPiece()!=null) {
            //System.out.println("in isValidMove for king");
            //System.out.println(Chessboard.chessBoard[oldRow][oldCol].getPiece().getType());


            ///return false;
        }



        // a king can only move one spot away in any direction
        //UNLESS it is castling

        if (castleMove(oldCol, oldRow, newCol, newRow)) {
            iscastlingmove = true;
            return true;
        }
        else {
            iscastlingmove = false;
        }

        if (newCol!=oldCol+1 && newCol!=oldCol-1 && newCol!=oldCol) {
            return false;
        }
        if (newRow!=oldRow+1 && newRow!=oldRow-1 && newRow!=oldRow) {
            return false;
        }


        // not a valid move for current player for ANY piece
        // if current players king will be in check as a result
        //if (isKingInCheck(oldRow, oldCol, newRow, newCol)) {
        //	return false;
        //}




        //There is a chess piece already in destination
        if (Chessboard.chessBoard[newRow][newCol].getPiece()!=null) {

            //if the player's own piece is in the destination
            if ((Chessboard.chessBoard[oldRow][oldCol].getPiece().isWhite() && Chessboard.chessBoard[newRow][newCol].getPiece().isWhite()) ||
                    (!Chessboard.chessBoard[oldRow][oldCol].getPiece().isWhite() && !Chessboard.chessBoard[newRow][newCol].getPiece().isWhite())) {
                return false;
            }

            //if the current player's opponent's piece is in the destination
            if ((Chessboard.chessBoard[oldRow][oldCol].getPiece().isWhite() && !Chessboard.chessBoard[newRow][newCol].getPiece().isWhite()) ||
                    (!Chessboard.chessBoard[oldRow][oldCol].getPiece().isWhite() && Chessboard.chessBoard[newRow][newCol].getPiece().isWhite())) {
                return true;


            }




            return false;
        }


        //no chess piece in destination and king is not in check
        //move piece
        return true;

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
     * castleMove(int oldCol, int oldRow, int newCol, int newRow)
     * checks if a King can make a castling move with a Rook,
     * Returns true if move is valid
     *
     * @param oldCol - old block's File
     * @param oldRow - old block's Rank
     * @param newCol - new block's File
     * @param newRow - new blocks Rank
     *
     * @return boolean
     */

    public boolean castleMove(int oldCol, int oldRow, int newCol, int newRow) {


        if (Chessboard.chessBoard[oldRow][oldCol].getPiece()!=null) {
            //System.out.println("in castleMove");
            //System.out.println(Chessboard.chessBoard[oldRow][oldCol].getPiece().getType());


            ///return false;
        }




        if (Chessboard.chessBoard[oldRow][oldCol].getPiece()==null) {
            //System.out.println("piece is null old row, old col: " + oldRow + oldCol);
            return false;
        }


        //System.out.println("In CastleMove");
        //System.out.println("oldrow and oldcol: " + oldRow + " " + oldCol);
        //System.out.println(Chessboard.whitesTurn);


        //cannot castle if players king is already in check
        if (Chessboard.whitesTurn==true && Chessboard.whiteincheck==true){
            return false;
        }
        if (Chessboard.blacksTurn==true && Chessboard.blackincheck==true){
            return false;
        }



        //do not check to see if opponent has a castle move - not applicable
        //NULL POINTER
        if (Chessboard.chessBoard[oldRow][oldCol].getPiece()!=null) {
            if (Chessboard.whitesTurn==true && Chessboard.chessBoard[oldRow][oldCol].getPiece().getType()=="king" &&
                    !(Chessboard.chessBoard[oldRow][oldCol].getPiece().isWhite())) {
                return false;
            }
        }

        if (Chessboard.chessBoard[oldRow][oldCol].getPiece()!=null) {
            if (Chessboard.blacksTurn==true && Chessboard.chessBoard[oldRow][oldCol].getPiece().getType()=="king" &&
                    (Chessboard.chessBoard[oldRow][oldCol].getPiece().isWhite())) {

                return false;
            }
        }






        //there are only two possible spots for a king to move in castling for each player
        //if (Chessboard.chessBoard[oldRow][oldCol].getPiece().isWhite()) {
        if (Chessboard.whitesTurn==true) {


            //if the player's king is in check, it cannot make this move
            if (Chessboard.whiteincheck==true) {
                return false;
            }




            //white king must be in this position
            if (!(oldRow==7 && oldCol==4)) {
                return false;
            }

            //king must not have moved
            //null pointer here
            if (Chessboard.chessBoard[oldRow][oldCol].getPiece().hasMoved()) {
                return false;
            }


            //move has two possible choices
            if (!(newRow==7 && (newCol==2 ||newCol==6))) {
                return false;
            }


            if (newRow==7 && newCol==2) {

                if (Chessboard.chessBoard[7][0].getPiece()==null) {
                    return false;
                }


                //chosen position must have a white rook in the corner that must not have moved
                if (!((Chessboard.chessBoard[7][0].getPiece().getType()=="rook") &&
                        (Chessboard.chessBoard[7][0].getPiece().isWhite()))){
                    return false;
                }

                if (Chessboard.chessBoard[7][0].getPiece().hasMoved()) {
                    return false;
                }


                //chosen rook
                oldRookRow = 7;
                oldRookCol = 0;

                //where it will move if conditions are satisfied
                newRookRow = 7;
                newRookCol = 3;



                //there must be no pieces between king and chosen rook
                if (!(Chessboard.chessBoard[7][1].getPiece()==null &&
                        Chessboard.chessBoard[7][2].getPiece()==null &&
                        Chessboard.chessBoard[7][3].getPiece()==null)) {
                    return false;
                }


                //The king must not be in check in the beginning, middle or ending square
                //ie must never be in check throughout move



                //if (isPlayerKingInCheck(oldRow, oldCol, oldRow, oldCol)) {
                //   return false;
                //}
                if (isPlayerKingInCheck(oldRow, oldCol, 7, 3)) {
                    return false;
                }
                castlingcheck = true;
                if (isPlayerKingInCheck(oldRow, oldCol, newRow, newCol)) {
                    return false;
                }


                return true;

                //end of row 7 col 2
            }




            if (newRow==7 && newCol==6) {

                if (Chessboard.chessBoard[7][7].getPiece()==null) {
                    return false;
                }



                //chosen position must have a white rook in the corner that must not have moved
                if (!((Chessboard.chessBoard[7][7].getPiece().getType()=="rook") &&
                        (Chessboard.chessBoard[7][7].getPiece().isWhite()))){
                    return false;
                }

                if (Chessboard.chessBoard[7][7].getPiece().hasMoved()) {
                    return false;
                }


                //chosen rook
                oldRookRow = 7;
                oldRookCol = 7;

                //where it will move if conditions are satisfied
                newRookRow = 7;
                newRookCol = 5;



                //there must be no pieces between king and chosen rook
                if (!(Chessboard.chessBoard[7][5].getPiece()==null &&
                        Chessboard.chessBoard[7][6].getPiece()==null)) {
                    return false;
                }


                //The king must not be in check in the beginning, middle or ending square
                //ie must never be in check throughout move

                //  if (isPlayerKingInCheck(oldRow, oldCol, oldRow, oldCol)) {
                //	   return false;
                //  }
                if (isPlayerKingInCheck(oldRow, oldCol, 7, 6)) {
                    return false;
                }
                castlingcheck = true;
                if (isPlayerKingInCheck(oldRow, oldCol, newRow, newCol)) {
                    return false;
                }


                oldRookRow = 7;
                oldRookCol = 7;
                newRookRow = 7;
                newRookCol = 5;

                return true;
            }//end of row 7 col 6



            return false;

        }//end of white king



        //black player

        //there are only two possible spots for a king to move in castling for each player
        if (!(Chessboard.chessBoard[oldRow][oldCol].getPiece().isWhite())) {

            //if the player's king is in check, it cannot make this move
            if (Chessboard.blacksTurn==true) {
                if (Chessboard.blackincheck==true) {
                    return false;
                }
            }



            //black king must be in this position
            if (!(oldRow==0 && oldCol==4)) {
                return false;
            }

            //king must not have moved
            if (Chessboard.chessBoard[oldRow][oldCol].getPiece().hasMoved()) {
                return false;
            }


            //move has two possible choices
            if (!(newRow==0 && (newCol==2 ||newCol==6))) {
                return false;
            }


            if (newRow==0 && newCol==2) {

                if (Chessboard.chessBoard[0][0].getPiece()==null) {
                    return false;
                }


                //chosen position must have a black rook in the corner that must not have moved
                if (!((Chessboard.chessBoard[0][0].getPiece().getType()=="rook") &&
                        !(Chessboard.chessBoard[0][0].getPiece().isWhite()))){
                    return false;
                }

                if (Chessboard.chessBoard[0][0].getPiece().hasMoved()) {
                    return false;
                }


                //chosen rook
                oldRookRow = 0;
                oldRookCol = 0;

                //where it will move if conditions are satisfied
                newRookRow = 0;
                newRookCol = 3;



                //there must be no pieces between king and chosen rook
                if (!(Chessboard.chessBoard[0][1].getPiece()==null &&
                        Chessboard.chessBoard[0][2].getPiece()==null &&
                        Chessboard.chessBoard[0][3].getPiece()==null)) {
                    return false;
                }



                //The king must not be in check in the beginning, middle or ending square
                //ie must never be in check throughout move

                //if (isPlayerKingInCheck(oldRow, oldCol, oldRow, oldCol)) {
                //   return false;
                //}
                if (isPlayerKingInCheck(oldRow, oldCol, 0, 3)) {
                    return false;
                }
                castlingcheck = true;
                if (isPlayerKingInCheck(oldRow, oldCol, newRow, newCol)) {
                    return false;
                }


                return true;

                //end of row 0 col 2
            }



            //black player row 0 col 6
            if (newRow==0 && newCol==6) {

                if (Chessboard.chessBoard[0][7].getPiece()==null) {
                    return false;
                }



                //chosen position must have a black rook in the corner that must not have moved
                if (!((Chessboard.chessBoard[0][7].getPiece().getType()=="rook") &&
                        !(Chessboard.chessBoard[0][7].getPiece().isWhite()))){
                    return false;
                }

                if (Chessboard.chessBoard[0][7].getPiece().hasMoved()) {
                    return false;
                }


                //chosen rook
                oldRookRow = 0;
                oldRookCol = 7;

                //where it will move if conditions are satisfied
                newRookRow = 0;
                newRookCol = 5;



                //there must be no pieces between king and chosen rook
                if (!(Chessboard.chessBoard[0][5].getPiece()==null &&
                        Chessboard.chessBoard[0][6].getPiece()==null)) {
                    return false;
                }


                //The king must not be in check in the beginning, middle or ending square
                //ie must never be in check throughout move
                //castlingcheck = true;
                // if (isPlayerKingInCheck(oldRow, oldCol, oldRow, oldCol)) {
                //	   return false;
                // }
                if (isPlayerKingInCheck(oldRow, oldCol, 0, 5)) {
                    return false;
                }
                castlingcheck = true;
                if (isPlayerKingInCheck(oldRow, oldCol, newRow, newCol)) {
                    return false;
                }


                oldRookRow = 0;
                oldRookCol = 7;
                newRookRow = 0;
                newRookCol = 5;

                return true;
            }//end of row 0 col 6



            return false;

        }//end of black king








        return false;
    }

    /**
     * static method isOpponentKinginCheckmate(int newRow, int newCol)
     * Checks if the current move will put opponent's king in a checkmate position, returns true if it does
     *
     * @param newCol - new block's File
     * @param newRow - new blocks Rank
     *
     * @return boolean
     */
    public static boolean isOpponentKinginCheckmate(int newRow, int newCol) {

        //System.out.println("in checkmate method");

        //if the opponent king is not in check, it obviously cannot be in checkmate
        if (!(isOpponentKingInCheck(newRow, newCol))) {
            return false;
        }

        int OppKingRow = -1;
        int OppKingCol = -1;


        //first, find location of opponent's king
        for (int i=0; i<8; i++) {
            for (int j=0; j<8; j++) {

                //if we have come across a piece
                if (Chessboard.chessBoard[i][j].getPiece()!=null)
                {

                    //only check for opponents king
                    if ((!Chessboard.chessBoard[newRow][newCol].getPiece().isWhite()	&&
                            Chessboard.chessBoard[i][j].getPiece().isWhite()) ||
                            (Chessboard.chessBoard[newRow][newCol].getPiece().isWhite() &&
                                    !Chessboard.chessBoard[i][j].getPiece().isWhite())){

                        if (Chessboard.chessBoard[i][j].getPiece().getType()=="king") {
                            OppKingRow = i;
                            OppKingCol = j;
                        }

                    }
                }

            }
        }
        //end of for loop, this is the case where the opponents king has not been found
        //this should not happen
        if (OppKingRow==-1) {
            Chessboard.checkMate=false;
            return false;
        }






        //iterate through entire board to see if any of the moves the opponent may make
        // will get the king out of check
        for (int i=0; i<8; i++) {
            for (int j=0; j<8; j++) {
                //System.out.println("is first for loop");
                //if we have come across a piece
                if (Chessboard.chessBoard[i][j].getPiece()!=null)
                {

                    //System.out.println("got non null piece");

                    //only check opponent players pieces
                    if ((Chessboard.chessBoard[newRow][newCol].getPiece().isWhite()	&&
                            !Chessboard.chessBoard[i][j].getPiece().isWhite()) ||
                            (!Chessboard.chessBoard[newRow][newCol].getPiece().isWhite() &&
                                    Chessboard.chessBoard[i][j].getPiece().isWhite())){

                        //System.out.println("got opponent players piece: " + Chessboard.chessBoard[i][j].getPiece().getType());

                        //System.out.println("row: " + i);
                        //System.out.println("column: " + j);

                        //is there a a valid move in the opponents pieces which will save the King?

                        //iterate through the board again, looking for all valid moves this piece
                        // may make, and whether it gets the king out of check

                        for (int k=0; k<8; k++) {
                            for (int l=0; l<8; l++) {

                                //System.out.println("in second for loop");

                                if (Chessboard.chessBoard[i][j].getPiece()!=null) {

                                    //there is a valid move this piece can make, but will it save the king
                                    if (Chessboard.chessBoard[i][j].getPiece().isValidMove(j, i, l, k)) {

                                        //System.out.println("there is a valid move" + Chessboard.chessBoard[i][j].getPiece().getType());

                                        //hypothetically make the move
                                        Pieces temp = Chessboard.chessBoard[k][l].getPiece();
                                        Chessboard.chessBoard[k][l].setPiece(Chessboard.chessBoard[i][j].getPiece());
                                        Chessboard.chessBoard[i][j].setPiece(null);



                                        //moving this piece to l, k takes the king out of check
                                        if (!(isOpponentKingInCheck(newRow, newCol))){
                                            //System.out.println("opponent king will not be in check");
                                            //System.out.println("can be saved by piece moving the following piece to the following position " + Chessboard.chessBoard[k][l].getPiece().getType());
                                            //System.out.println("row: " + k);
                                            //System.out.println("column: " + l);
                                            //this means the king is not in checkmate!

                                            //System.out.println("Opponent King NOT in Checkmate");
                                            //System.out.println(Chessboard.chessBoard[i][j].getPiece().getType());

                                            //put pieces back
                                            Chessboard.chessBoard[i][j].setPiece(Chessboard.chessBoard[k][l].getPiece());
                                            Chessboard.chessBoard[k][l].setPiece(temp);
                                            //Pieces temp = Chessboard.chessBoard[k][l].getPiece();

                                            Chessboard.checkMate=false;
                                            return false;
                                        }


                                        //put pieces back
                                        Chessboard.chessBoard[i][j].setPiece(Chessboard.chessBoard[k][l].getPiece());
                                        Chessboard.chessBoard[k][l].setPiece(temp);



                                    }

                                }
                            }


                        }


                    }
                }
            }

        }
        //opponent king IS in checkmate
        //System.out.println("Opponent King in Checkmate");
        Chessboard.checkMate=true;
        return true;
    }



    /**static method isPlayerKingInCheck(int oldRow, int oldCol, int newRow, int newCol)
     * this is checked before a move is made
     *returns true if the move to be made to a new position by any of the current players pieces
     *will place the current player's own king in check

     *  @param oldCol - old block's File
     * @param oldRow - old block's Rank
     * @param newCol - new block's File
     * @param newRow - new blocks Rank
     *
     *@return boolean
     */
    public static boolean isPlayerKingInCheck(int oldRow, int oldCol, int newRow, int newCol) {

        //first, find the current player's King
        int PlayerKingRow = -1;
        int PlayerKingCol = -1;

        // make hypothetical changes to board temporarily
        Pieces rook = null;
        Pieces king = null;
        Pieces temp = null;

        if (Chessboard.chessBoard[oldRow][oldCol].getPiece()!=null) {
            //System.out.println("in isPlayerKinginCheck");
            //System.out.println(Chessboard.chessBoard[oldRow][oldCol].getPiece().getType());


            ///return false;
        }





        if (castlingcheck==true) {

            //System.out.println("castling check is true, moving rook and king");

            //move rook
            rook = Chessboard.chessBoard[newRookRow][newRookCol].getPiece();
            Chessboard.chessBoard[newRookRow][newRookCol].setPiece(Chessboard.chessBoard[oldRookRow][oldRookCol].getPiece());
            Chessboard.chessBoard[oldRookRow][oldRookCol].setPiece(null);

            //System.out.println("initally moving rook");
            //System.out.println("saving the piece in newRookRow and newRookCol: " + newRookRow + " " + newRookCol);
            //System.out.println("setting newrookrow and col to oldRookRow and oldRookCol: " + oldRookRow + " " + oldRookCol);
            //System.out.println("setting piece at old rook row and old rook col to null");


            //move king
            king = Chessboard.chessBoard[newRow][newCol].getPiece();
            Chessboard.chessBoard[newRow][newCol].setPiece(Chessboard.chessBoard[oldRow][oldCol].getPiece());
            Chessboard.chessBoard[oldRow][oldCol].setPiece(null);


            //System.out.println("initally moving king");
            //System.out.println("saving the piece in newRow and newCol: " + newRow + " " + newCol);
            //System.out.println("setting newrow and col to oldRow and oldCol: " + oldRow + " " + oldCol);
            //System.out.println("setting piece at old row and old col to null");


        }
        else {

            temp = Chessboard.chessBoard[newRow][newCol].getPiece();
            Chessboard.chessBoard[newRow][newCol].setPiece(Chessboard.chessBoard[oldRow][oldCol].getPiece());
            Chessboard.chessBoard[oldRow][oldCol].setPiece(null);
        }

        if (castlingcheck==true) {
            PlayerKingRow=newRow;
            PlayerKingCol=newCol;
        }

        else {
            //first, find location of the current players king
            for (int i=0; i<8; i++) {
                for (int j=0; j<8; j++) {

                    //if we have come across a piece
                    if (Chessboard.chessBoard[i][j].getPiece()!=null)
                    {

                        //only check for current players king
                        //null pointer here for castling

                        if ((Chessboard.whitesTurn==true && Chessboard.chessBoard[i][j].getPiece().isWhite()) ||
                                (Chessboard.blacksTurn==true && !Chessboard.chessBoard[i][j].getPiece().isWhite())) {

                            //if ((!Chessboard.chessBoard[newRow][newCol].getPiece().isWhite()	&&
                            //!Chessboard.chessBoard[i][j].getPiece().isWhite()) ||
                            //(Chessboard.chessBoard[newRow][newCol].getPiece().isWhite() &&
                            //Chessboard.chessBoard[i][j].getPiece().isWhite())){

                            if (Chessboard.chessBoard[i][j].getPiece().getType()=="king") {
                                PlayerKingRow = i;
                                PlayerKingCol = j;
                            }

                        }
                    }

                }
            }
        }
        //end of for loop, this is the case where the opponents king has not been found
        //this should not happen
        if (PlayerKingRow==-1) {
            //System.out.println("BUG");
            return false;
        }

        //System.out.println("old Row: " + oldRow);
        //System.out.println("old Col: " + oldCol);
        //System.out.println("new Row: " + newRow);
        //System.out.println("new Col: " + newCol);
        //System.out.println("king Row: " + PlayerKingRow);
        //System.out.println("king Col: " + PlayerKingCol);





        //iterate through entire board to see if any opponent pieces would be able to
        //attack the king if the piece makes the move in question
        for (int i=0; i<8; i++) {
            for (int j=0; j<8; j++) {

                //if we have come across an opponent piece
                if (Chessboard.chessBoard[i][j].getPiece()!=null)
                {

                    //only check opponent pieces
                    if ((Chessboard.whitesTurn==true && !Chessboard.chessBoard[i][j].getPiece().isWhite()) ||
                            (Chessboard.blacksTurn==true && Chessboard.chessBoard[i][j].getPiece().isWhite())) {
                        //if ((Chessboard.chessBoard[newRow][newCol].getPiece().isWhite()	&&
                        //!Chessboard.chessBoard[i][j].getPiece().isWhite()) ||
                        //(!Chessboard.chessBoard[newRow][newCol].getPiece().isWhite() &&
                        //Chessboard.chessBoard[i][j].getPiece().isWhite())){


                        //is there a a valid move from this opponent piece to the current players king?
                        if (Chessboard.chessBoard[i][j].getPiece().isValidMove(j, i, PlayerKingCol, PlayerKingRow)) {
                            //System.out.println("Invalid move: the king will be in check by: ");
                            //System.out.println(Chessboard.chessBoard[i][j].getPiece().getType() + " "+Chessboard.chessBoard[i][j].getPiece().isWhite());

                            if (castlingcheck==true) {
                                //move rook back to original spot
                                Chessboard.chessBoard[oldRookRow][oldRookCol].setPiece(Chessboard.chessBoard[newRookRow][newRookCol].getPiece());
                                Chessboard.chessBoard[newRookRow][newRookCol].setPiece(rook);

                                //System.out.println("Moving rook back");
                                //System.out.println("old rook row and old rook col: " + oldRookRow + " " + oldRookCol);
                                //System.out.println("setting oldrookrow and oldrookcol to newRookRow and newRookCol: " + newRookRow + " " + newRookCol);
                                //System.out.println("setting piece at new rook row and new rook col to saved rook value");






                                //move king back to original spot
                                Chessboard.chessBoard[oldRow][oldCol].setPiece(Chessboard.chessBoard[newRow][newCol].getPiece());
                                Chessboard.chessBoard[newRow][newCol].setPiece(king);

                                //System.out.println("Moving king back");
                                //System.out.println("oldrow and oldcol: " + oldRow + " " + oldCol);
                                //System.out.println("setting oldrow and oldcol to newRow and newCol: " + newRow + " " + newCol);
                                //System.out.println("setting piece at newrow and newcol to saved king value");






                            }
                            else {
                                //return board to original state
                                Chessboard.chessBoard[oldRow][oldCol].setPiece(Chessboard.chessBoard[newRow][newCol].getPiece());
                                Chessboard.chessBoard[newRow][newCol].setPiece(temp);
                            }










                            castlingcheck = false;
                            return true;
                        }


                    }
                }
            }

        }
        //return board to original state
        if (castlingcheck==true) {
            //move rook back to original spot
            Chessboard.chessBoard[oldRookRow][oldRookCol].setPiece(Chessboard.chessBoard[newRookRow][newRookCol].getPiece());
            Chessboard.chessBoard[newRookRow][newRookCol].setPiece(rook);


            //System.out.println("Moving rook back");
            //System.out.println("old rook row and old rook col: " + oldRookRow + " " + oldRookCol);
            //System.out.println("setting oldrookrow and oldrookcol to newRookRow and newRookCol: " + newRookRow + " " + newRookCol);
            //System.out.println("setting piece at new rook row and new rook col to saved rook value");





            //move king back to original spot
            Chessboard.chessBoard[oldRow][oldCol].setPiece(Chessboard.chessBoard[newRow][newCol].getPiece());
            Chessboard.chessBoard[newRow][newCol].setPiece(king);

            //System.out.println("Moving king back");
            //System.out.println("oldrow and oldcol: " + oldRow + " " + oldCol);
            //System.out.println("setting oldrow and oldcol to newRow and newCol: " + newRow + " " + newCol);
            //System.out.println("setting piece at newrow and newcol to saved king value");




            //castlingcheck = false;


        }
        else {
            //return board to original state
            Chessboard.chessBoard[oldRow][oldCol].setPiece(Chessboard.chessBoard[newRow][newCol].getPiece());
            Chessboard.chessBoard[newRow][newCol].setPiece(temp);
        }

        castlingcheck = false;
        return false;
    }

    /** static method isOpponentKingInCheck(int newRow, int newCol)
     * this is checked after the move to a new position has already been made
     returns true if the move made by any of the current players pieces
     places the opponent player's king in check
     * @param newCol - new block's File
     * @param newRow - new blocks Rank
     *
     * @return boolean
     */

    public static boolean isOpponentKingInCheck(int newRow, int newCol) {

        //first, find the opponents's King
        int OppKingRow = -1;
        int OppKingCol = -1;


        //first, find location of the opponent players king
        for (int i=0; i<8; i++) {
            for (int j=0; j<8; j++) {

                //if we have come across a piece
                if (Chessboard.chessBoard[i][j].getPiece()!=null)
                {

                    //only check for opponent players king
                    if ((Chessboard.chessBoard[newRow][newCol].getPiece().isWhite()	&&
                            !Chessboard.chessBoard[i][j].getPiece().isWhite()) ||
                            !(Chessboard.chessBoard[newRow][newCol].getPiece().isWhite() &&
                                    Chessboard.chessBoard[i][j].getPiece().isWhite())){

                        if (Chessboard.chessBoard[i][j].getPiece().getType()=="king") {
                            OppKingRow = i;
                            OppKingCol = j;
                        }

                    }
                }

            }
        }
        //end of for loop, this is the case where the opponents king has not been found
        //this should not happen
        if (OppKingRow==-1) {
            if (Chessboard.whitesTurn==true) {
                Chessboard.blackincheck=false;
            }
            if (Chessboard.blacksTurn==true) {
                Chessboard.whiteincheck=false;
            }
            return false;
        }


        //iterate through entire board to see if any CURRENT players pieces would be able to
        //attack the opponent king if the piece makes the move in question
        for (int i=0; i<8; i++) {
            for (int j=0; j<8; j++) {

                //if we have come across a piece
                if (Chessboard.chessBoard[i][j].getPiece()!=null)
                {

                    //only check current player pieces
                    if (!(Chessboard.chessBoard[newRow][newCol].getPiece().isWhite()	&&
                            !Chessboard.chessBoard[i][j].getPiece().isWhite()) ||
                            (Chessboard.chessBoard[newRow][newCol].getPiece().isWhite() &&
                                    Chessboard.chessBoard[i][j].getPiece().isWhite())){


                        //is there a a valid move from this players piece to the opponents king?
                        if (Chessboard.chessBoard[i][j].getPiece().isValidMove(j, i, OppKingCol, OppKingRow)) {

                            //System.out.println("Check! Valid move: the opponent's king will be in check by: ");
                            //System.out.println(Chessboard.chessBoard[i][j].getPiece().getType() + " "+Chessboard.chessBoard[i][j].getPiece().isWhite());

                            //if (Chessboard.whitesTurn==true) {
                            //	Chessboard.blackincheck=true;
                            //}
                            //if (Chessboard.blacksTurn==true) {
                            //	Chessboard.whiteincheck=true;
                            //}
                            return true;
                        }


                    }
                }
            }

        }
        //if (Chessboard.whitesTurn==true) {
        //	Chessboard.blackincheck=false;
        //}
        //if (Chessboard.blacksTurn==true) {
        //	Chessboard.whiteincheck=false;
        //}
        return false;
    }








    /**
     * move(int oldCol, int oldRow, int newCol, int newRow, char promo)
     * moves a King piece if isValidMove is true and destroys any pieces on applicable collision at new position
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

            //System.out.println("in isValidMove=true");



            // not a valid move for current player for ANY piece
            // if current players king will be in check as a result
            // move is disallowed
            if (isPlayerKingInCheck(oldRow, oldCol, newRow, newCol)) {
                //System.out.println("in playerkingincheck=true");
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

            if (castleMove(oldCol, oldRow, newCol, newRow)) {
                //if (iscastlingmove == true) {
                //System.out.println("in castling move is true, moving pieces");
                //move king
                Chessboard.chessBoard[newRow][newCol].setPiece(Chessboard.chessBoard[oldRow][oldCol].getPiece());
                Chessboard.chessBoard[oldRow][oldCol].setPiece(null);
                Chessboard.chessBoard[newRow][newCol].getPiece().moved();

                //move rook
                Chessboard.chessBoard[newRookRow][newRookCol].setPiece(Chessboard.chessBoard[oldRookRow][oldRookCol].getPiece());
                Chessboard.chessBoard[oldRookRow][oldRookCol].setPiece(null);
                Chessboard.chessBoard[newRookRow][newRookCol].getPiece().moved();

                if (isOpponentKingInCheck(newRow, newCol)){

                    //System.out.println("in is opponent king in check");


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

            Chessboard.chessBoard[newRow][newCol].setPiece(Chessboard.chessBoard[oldRow][oldCol].getPiece());
            Chessboard.chessBoard[oldRow][oldCol].setPiece(null);
            Chessboard.chessBoard[newRow][newCol].getPiece().moved();

            ///needs to go in all classes at any point after move is made
            //need to keep track of who is in check for castling

            if (isOpponentKingInCheck(newRow, newCol)){
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

            ////////

            if(isOpponentKinginCheckmate(newRow, newCol)) {
                Chessboard.checkMate=true;
            }


            Pawn.r = -1;
            Pawn.c = -1;
            return true;
        }
        return false;

    }


}
