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

import java.util.Scanner;

public class Chess {

    /**
     * main(String[] args)
     * : main method to start the game and take user input
     * @param args - arguments on command line
     *
     */
  /*ublic static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Chessboard board = new Chessboard();

        board.resetBoard();
        board.printBoard();

        boolean b = true;


        while (board.gameOver()==false) {

            System.out.print("\nWhite's move: ");
            Chessboard.whitesTurn=true;
            Chessboard.blacksTurn=false;
            String FileRank = scanner.nextLine();

            b = board.gameMove(FileRank);

            while (board.gameOver()==false && b==false) {
                System.out.println("Illegal move, try again");
                System.out.print("\nWhite's move: ");
                FileRank = scanner.nextLine();

                b = board.gameMove(FileRank);

                //if (board.gameStatus()=='c') {
                //		System.out.println("Check");
                //}
            }


            if (board.gameStatus()=='c') {
                System.out.println("Check");
            }



            // if game over or checkmate
            if (board.gameOver()==true || board.gameStatus()=='m') {
                System.out.println();
                board.printBoard();
                System.out.println();
                break;
            }



            System.out.println();
            board.printBoard();

            System.out.print("\nBlack's move: ");
            Chessboard.whitesTurn=false;
            Chessboard.blacksTurn=true;
            FileRank = scanner.nextLine();

            b = board.gameMove(FileRank);




            while (board.gameOver()==false && b==false) {
                System.out.println("Illegal move, try again");
                System.out.print("\nBlack's move: ");
                FileRank = scanner.nextLine();
                b = board.gameMove(FileRank);
                //if (board.gameStatus()=='c') {
                //		System.out.println("Check");
                //}

            }


            if (board.gameStatus()=='c') {
                System.out.println("Check");
            }



            // if game over or checkmate
            if (board.gameOver()==true || board.gameStatus()=='m') {
                System.out.println();
                board.printBoard();
                System.out.println();
                break;
            }

            System.out.println();
            board.printBoard();

        }

        //Game over

        //checkmate
        if (board.gameStatus()=='m' && Chessboard.whitesTurn==true) {
            System.out.println("Checkmate");
            System.out.println("White wins");
        }
        else if (board.gameStatus()=='m' && Chessboard.blacksTurn==true) {
            System.out.println("Checkmate");
            System.out.println("Black wins");
        }
        else {


            //non checkmate
            switch(board.gameStatus())
            {
                case 'w': System.out.println("White wins"); break;
                case 'b': System.out.println("Black wins"); break;
                case 'd': System.out.println("Draw"); break;
                default: System.out.print("");
            }

        }


    }
*/
}
