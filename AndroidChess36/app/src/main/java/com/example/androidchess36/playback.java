package com.example.androidchess36;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import model.Bishop;
import model.Block;
import model.Game;
import model.King;
import model.Knight;
import model.Pawn;
import model.Pieces;
import model.Queen;
import model.Rook;
import view.GridAdapter;

//import static controller.Chessboard.chessBoard;

public class playback extends AppCompatActivity {
    GridView boardview;
    Button next;
    int nextmove=0;
    Game game;
    TextView playback;
    static GridAdapter gridAdapter;
    Block[][] board = new Block[8][8];

    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.playback);
        boardview = findViewById(R.id.boardview);
        next = findViewById(R.id.next);
        playback = findViewById(R.id.playback);
        for (int i=0; i<8; i++) {
            for (int j=0; j<8; j++) {

                if ((i%2!=0 && j%2==0) || (i%2==0 && j%2!=0)) {
                    Block b = new Block("black");
                    board[i][j] = b;
                    //board[i][j] = "## ";
                }
                else {
                    Block w = new Block("white");
                    board[i][j] = w;
                    //board[i][j] = "   ";
                }
            }
        }



        //sets pieces on board in starting positions
        for (int i=0; i<8; i++) {
            for (int j=0; j<8; j++) {

                if (i==0) {
                    if (j==0 || j==7) {
                        board[i][j].setPiece(new Rook("black"));
                    }
                    else if (j==1 || j==6) {
                        board[i][j].setPiece(new Knight("black"));
                    }
                    else if (j==2 || j==5) {
                        board[i][j].setPiece(new Bishop("black"));
                    }
                    else if (j==3) {
                        board[i][j].setPiece(new Queen("black"));
                    }
                    else {
                        board[i][j].setPiece(new King("black"));
                    }
                }
                if (i==1) {
                    board[i][j].setPiece(new Pawn("black"));
                }

                if (i==7) {
                    if (j==0 || j==7) {
                        board[i][j].setPiece(new Rook("white"));
                    }
                    else if (j==1 || j==6) {
                        board[i][j].setPiece(new Knight("white"));
                    }
                    else if (j==2 || j==5) {
                        board[i][j].setPiece(new Bishop("white"));
                    }
                    else if (j==3) {
                        board[i][j].setPiece(new Queen("white"));
                    }
                    else {
                        board[i][j].setPiece(new King("white"));
                    }
                }
                if (i==6) {
                    board[i][j].setPiece(new Pawn("white"));
                }

            }
        }


        Bundle b = getIntent().getExtras();
        game = (Game) b.getSerializable("game");
        //boardview = findViewById(R.id.boardview); //gets ahold of id element in routes_list
        gridAdapter = new GridAdapter(board, this);

        gridAdapter.notifyDataSetChanged();
        boardview.setAdapter(gridAdapter);

    }



    public void next(View view){

        ArrayList<Pieces[][]> boards = game.getBoards();
        if(boards.size()==nextmove){
            Toast toast= Toast.makeText(getApplicationContext(),
                    "All moves played for this game!", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.show();
            return;
        }
        Pieces[][] current = boards.get(nextmove);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                //there is a piece on the block, save it into pieces array to access later if needed
                if (current[i][j] != null) {
                    board[i][j].setPiece(current[i][j]);
                }
                else{
                    board[i][j].setPiece(null);
                }
            }
        }

        gridAdapter.notifyDataSetChanged();
        boardview.setAdapter(gridAdapter);
        nextmove++;

    }
}
