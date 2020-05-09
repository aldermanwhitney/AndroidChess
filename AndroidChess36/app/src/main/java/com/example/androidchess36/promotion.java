package com.example.androidchess36;

import model.*;
import controller.*;
import view.*;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import controller.Chessboard;
import model.Pieces;
import view.GridAdapter;

import static com.example.androidchess36.MainActivity.board;

public class promotion extends AppCompatActivity {

    Button queen, rook, bishop, knight;
    TextView promotitle;

    char promo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.promotion);
        //R.layout.promotion looks okay
        queen = findViewById(R.id.queen);
        bishop = findViewById(R.id.bishop);
        knight = findViewById(R.id.knight);
        rook = findViewById(R.id.rook);
        promotitle = findViewById(R.id.promotitle);

        Toast toast= Toast.makeText(getApplicationContext(),
                "What would you like to promote your pawn to?", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER|Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();

    }


    public void queenClicked(View view){

        //get origin and destination positions passed through intents
        //from call to promotion
        Intent mIntent = getIntent();
        int op = mIntent.getIntExtra("originPosition", 0);
        int dp = mIntent.getIntExtra("destinationPosition", 0);

        //System.out.println("origin: " + op);
        //System.out.println("dest: " + dp);

        //make move if possible
        Chessboard b = MainActivity.getBoard();
        b.promotion = 'Q';
        boolean x = b.gameMove(op, dp);
       // System.out.println(x);

        if (x==false) {//invalid move
            //Toast.makeText(MainActivity.this, "Invalid move, try again", Toast.LENGTH_LONG).show();
            Toast toast= Toast.makeText(getApplicationContext(),
                    "Invalid move, try again", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER|Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.show();
            MainActivity.numButtonClicks=0;
            MainActivity.returnSquareToOriginalColor(op, view);
            MainActivity.gridAdapter.notifyDataSetChanged();
            MainActivity.boardGridView.setAdapter(MainActivity.gridAdapter);
          //  System.out.println("invalid move, send to finish");
            finish();
           // System.out.println("after finishing");
        }
        else {
           // System.out.println("line 85");

            MainActivity.saveLastState();
            MainActivity.undochosen = false;


            //update view
            GridAdapter gridAdapter = MainActivity.getGridAdapter();
            gridAdapter.notifyDataSetChanged();
            MainActivity.getGridView().setAdapter(gridAdapter);

            if (board.gameStatus() == 'm' && Chessboard.whitesTurn) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Checkmate! White wins!", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();
                MainActivity.gameOver = true;
                finish();
            }
            if (board.gameStatus() == 'm' && Chessboard.blacksTurn) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Checkmate! Black wins!", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();
                MainActivity.gameOver = true;
                finish();
            }

            if (board.gameStatus() == 'c') {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Check!", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();
            }


            if (Chessboard.whitesTurn == true) {
                Chessboard.whitesTurn = false;
                Chessboard.blacksTurn = true;
            } else {
                Chessboard.whitesTurn = true;
                Chessboard.blacksTurn = false;
            }


            MainActivity.numButtonClicks = 0;

        }

            //return to main activity
            finish();

        /*
        System.out.println("queen");
        //Bundle bundle = new Bundle();
        //Intent intent = new Intent(this, MainActivity.class);
        //intent.putExtras(bundle);
        //intent.putExtra("promo", 'Q');
        //startActivity(intent);
        Chessboard b = MainActivity.getBoard();
        System.out.println(b.chessBoard[55 / 8][55 % 8].getPiece().getType());

        MainActivity.getBoard().promotion = 'Q';
        boolean x = b.gameMove(55, 47);
        ///System.out.println(x);
        GridAdapter gridAdapter = MainActivity.getGridAdapter();
        gridAdapter.notifyDataSetChanged();
        MainActivity.getGridView().setAdapter(gridAdapter);
        //gridAdapter.notifyDataSetChanged();
        //boardGridView.setAdapter(gridAdapter);
        finish();
        */

    }
    public void bishopClicked(View view){
       // System.out.println("bishop");
        //get origin and destination positions passed through intents
        //from call to promotion
        Intent mIntent = getIntent();
        int op = mIntent.getIntExtra("originPosition", 0);
        int dp = mIntent.getIntExtra("destinationPosition", 0);

       // System.out.println("origin: " + op);
        //System.out.println("dest: " + dp);

        //make move if possible
        Chessboard b = MainActivity.getBoard();
        b.promotion = 'B';
        boolean x = b.gameMove(op, dp);
        //System.out.println(x);

        if (x==false) {//invalid move
            //Toast.makeText(MainActivity.this, "Invalid move, try again", Toast.LENGTH_LONG).show();
            Toast toast= Toast.makeText(getApplicationContext(),
                    "Invalid move, try again", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER|Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.show();
            MainActivity.numButtonClicks=0;
            MainActivity.returnSquareToOriginalColor(op, view);
            MainActivity.gridAdapter.notifyDataSetChanged();
            MainActivity.boardGridView.setAdapter(MainActivity.gridAdapter);
            finish();
        }
        else {

            MainActivity.saveLastState();
            MainActivity.undochosen = false;


            //update view
            GridAdapter gridAdapter = MainActivity.getGridAdapter();
            gridAdapter.notifyDataSetChanged();
            MainActivity.getGridView().setAdapter(gridAdapter);

            if (board.gameStatus() == 'm' && Chessboard.whitesTurn) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Checkmate! White wins!", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();
                MainActivity.gameOver = true;
                finish();
            }
            if (board.gameStatus() == 'm' && Chessboard.blacksTurn) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Checkmate! Black wins!", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();
                MainActivity.gameOver = true;
                finish();
            }

            if (board.gameStatus() == 'c') {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Check!", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();
            }


            if (Chessboard.whitesTurn == true) {
                Chessboard.whitesTurn = false;
                Chessboard.blacksTurn = true;
            } else {
                Chessboard.whitesTurn = true;
                Chessboard.blacksTurn = false;
            }


            MainActivity.numButtonClicks = 0;

        }

            //return to main activity
            finish();
        /*
        //get origin and destination positions passed through intents
        //from call to promotion
        Intent mIntent = getIntent();
        int op = mIntent.getIntExtra("originPosition", 0);
        int dp = mIntent.getIntExtra("destinationPosition", 0);

        //make move if possible
        Chessboard b = MainActivity.getBoard();
        MainActivity.getBoard().promotion = 'B';
        boolean x = b.gameMove(op, dp);
        System.out.println(x);

        //update view
        GridAdapter gridAdapter = MainActivity.getGridAdapter();
        gridAdapter.notifyDataSetChanged();
        MainActivity.getGridView().setAdapter(gridAdapter);

        //return to main activity
        finish();

  */

    }
    public void knightClicked(View view){
       // System.out.println("knight");
        //get origin and destination positions passed through intents
        //from call to promotion
        Intent mIntent = getIntent();
        int op = mIntent.getIntExtra("originPosition", 0);
        int dp = mIntent.getIntExtra("destinationPosition", 0);

       // System.out.println("origin: " + op);
       // System.out.println("dest: " + dp);

        //make move if possible
        Chessboard b = MainActivity.getBoard();
        b.promotion = 'N';
        boolean x = b.gameMove(op, dp);
       // System.out.println(x);

        if (x==false) {//invalid move
            //Toast.makeText(MainActivity.this, "Invalid move, try again", Toast.LENGTH_LONG).show();
            Toast toast= Toast.makeText(getApplicationContext(),
                    "Invalid move, try again", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER|Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.show();
            MainActivity.numButtonClicks=0;
            MainActivity.returnSquareToOriginalColor(op, view);
            MainActivity.gridAdapter.notifyDataSetChanged();
            MainActivity.boardGridView.setAdapter(MainActivity.gridAdapter);
            finish();
        }
        else {

            MainActivity.saveLastState();
            MainActivity.undochosen = false;


            //update view
            GridAdapter gridAdapter = MainActivity.getGridAdapter();
            gridAdapter.notifyDataSetChanged();
            MainActivity.getGridView().setAdapter(gridAdapter);

            if (board.gameStatus() == 'm' && Chessboard.whitesTurn) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Checkmate! White wins!", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();
                MainActivity.gameOver = true;
                finish();
            }
            if (board.gameStatus() == 'm' && Chessboard.blacksTurn) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Checkmate! Black wins!", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();
                MainActivity.gameOver = true;
                finish();
            }

            if (board.gameStatus() == 'c') {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Check!", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();
            }


            if (Chessboard.whitesTurn == true) {
                Chessboard.whitesTurn = false;
                Chessboard.blacksTurn = true;
            } else {
                Chessboard.whitesTurn = true;
                Chessboard.blacksTurn = false;
            }


            MainActivity.numButtonClicks = 0;


        }//return to main activity
        finish();
    }
    public void rookClicked(View view){
      //  System.out.println("rook");
        //get origin and destination positions passed through intents
        //from call to promotion
        Intent mIntent = getIntent();
        int op = mIntent.getIntExtra("originPosition", 0);
        int dp = mIntent.getIntExtra("destinationPosition", 0);

        //System.out.println("origin: " + op);
        //System.out.println("dest: " + dp);

        //make move if possible
        Chessboard b = MainActivity.getBoard();
        b.promotion = 'R';
        boolean x = b.gameMove(op, dp);
      //  System.out.println(x);

        if (x==false) {//invalid move
            //Toast.makeText(MainActivity.this, "Invalid move, try again", Toast.LENGTH_LONG).show();
            Toast toast= Toast.makeText(getApplicationContext(),
                    "Invalid move, try again", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER|Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.show();
            MainActivity.numButtonClicks=0;
            MainActivity.returnSquareToOriginalColor(op, view);
            MainActivity.gridAdapter.notifyDataSetChanged();
            MainActivity.boardGridView.setAdapter(MainActivity.gridAdapter);
            finish();
        }
        else {

            MainActivity.saveLastState();
            MainActivity.undochosen = false;


            //update view
            GridAdapter gridAdapter = MainActivity.getGridAdapter();
            gridAdapter.notifyDataSetChanged();
            MainActivity.getGridView().setAdapter(gridAdapter);

            if (board.gameStatus() == 'm' && Chessboard.whitesTurn) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Checkmate! White wins!", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();
                MainActivity.gameOver = true;
                finish();
            }
            if (board.gameStatus() == 'm' && Chessboard.blacksTurn) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Checkmate! Black wins!", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();
                MainActivity.gameOver = true;
                finish();
            }

            if (board.gameStatus() == 'c') {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Check!", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();
            }


            if (Chessboard.whitesTurn == true) {
                Chessboard.whitesTurn = false;
                Chessboard.blacksTurn = true;
            } else {
                Chessboard.whitesTurn = true;
                Chessboard.blacksTurn = false;
            }


            MainActivity.numButtonClicks = 0;

        }
        //return to main activity
        finish();
    }


}



