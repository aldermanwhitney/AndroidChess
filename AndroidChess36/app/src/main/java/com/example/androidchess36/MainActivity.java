package com.example.androidchess36;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import controller.Chessboard;
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

import static controller.Chessboard.*;

public class MainActivity extends AppCompatActivity {
    public static ArrayList<Game> games;

    public static ArrayList<Game> result = new ArrayList<Game>();
    public static ArrayList<Pieces[][]> moves;
    static GridAdapter gridAdapter;
    static GridView boardGridView;
    public String[] pieces;
    static Chessboard board = new Chessboard();
    Pieces[][] savedBoard = new Pieces[8][8];
    static int numButtonClicks = 0;
    boolean b = true;
    int destinationPosition = -1;
    int originPosition = -1;
    static boolean gameOver = false;
    static boolean undochosen = false;
    boolean playinggame = false;
    public static final String SHARED_PREFS = "Shared preferences";
    boolean firstmovemade = false;


    //saved one thousand moves


    int moveSavedIndex = 0;

    EditText promotion;

    Button undo, draw, resign;
    Button AI, rcrdings;

    public void undoMove(View view){

        if (!firstmovemade){ //returns if a move has not yet been made to undo
            return;
        }

        //disables this button if the game is already over
        if (gameOver){
            Game g = new Game(moves);
            games.add(g);
            saveData();
            return;
        }
        if (undochosen){
            return;
        }

        returnToLastBoardState();
        //saveLastState();
        if (!moves.isEmpty()) {
            moves.remove((moves.size() - 1));
        }
       // saveData();

        //let player chose a different move
        if (whitesTurn){
            whitesTurn=false;
            blacksTurn=true;
        }
        else{
            whitesTurn=true;
            blacksTurn=false;
        }

        undochosen=true;
    }

    public void AI(View view){


        if (gameOver){
            Game g = new Game(moves);
            games.add(g);
            saveData();
            return;
        }

        saveLastBoardState();

        //arraylist holds integer arrays of valid move positions
        ArrayList<Integer[]> validmovesarraylist = new ArrayList<Integer[]>();

        board.promotion = 'Q'; //AI defaults to a queen promotion

        //iterate through entire board to get a player's piece and check for a valid move
        for (int i=0; i<8; i++) {
            for (int j=0; j<8; j++) {

                //if we have come across a piece
                if (Chessboard.chessBoard[i][j].getPiece()!=null)
                {


                    //only check current players pieces
                    if ((whitesTurn	&& Chessboard.chessBoard[i][j].getPiece().isWhite()) ||
                            (blacksTurn && !Chessboard.chessBoard[i][j].getPiece().isWhite())){

                        //iterate through the board again, looking for all valid moves this piece can make

                        for (int k=0; k<8; k++) {
                            for (int l=0; l<8; l++) {

                                //System.out.println("in second for loop");

                                if (Chessboard.chessBoard[i][j].getPiece()!=null) {

                                    //found valid move this piece can make
                                    if (Chessboard.chessBoard[i][j].getPiece().isValidMove(j, i, l, k)) {

                                        Integer array[] = new Integer[4];
                                        array[0]=k;
                                        array[1]=l;
                                        array[2]=i;
                                        array[3]=j;

                                        //saves all valid moves in arraylist to randomly choose from later
                                        validmovesarraylist.add(array);



                                    }
                                }}}}}}}


        boolean movemade = false;

        //no valid moves for AI to make
        //this means current player is in checkmate
        if (validmovesarraylist.isEmpty()){
           // System.out.println("no valid moves");

            //checkmate, white wins
            if (board.gameStatus()=='m' && Chessboard.whitesTurn) {
                Toast toast= Toast.makeText(getApplicationContext(),
                        "Checkmate! Black wins!", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();
                gameOver=true;
                return;
            }

            //checkmate, black wins
            if (board.gameStatus()=='m' && Chessboard.blacksTurn) {
                Toast toast= Toast.makeText(getApplicationContext(),
                        "Checkmate! White wins!", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();
                gameOver = true;
                return;
            }
        //checkGameStatus();
        return;
        }

        int count = 0;
        while(!movemade) {
            //choose random index from arraylist for valid move to make
            int randomIndex = (int) (Math.random() * validmovesarraylist.size());
            Integer chosenmove[] = chosenmove = validmovesarraylist.get(randomIndex);

            movemade = Chessboard.chessBoard[(chosenmove[2])][(chosenmove[3])].getPiece().move((chosenmove[3]), (chosenmove[2]), (chosenmove[1]), (chosenmove[0]), 'Q');
            count++;
            if (count>=5000){ //none of the valid moves could be made because the king will still be in check, ie checkmate
               // System.out.println("no valid moves, count above 2000");

                //checkmate, white wins
                if (board.gameStatus()=='m' && Chessboard.whitesTurn) {
                    Toast toast= Toast.makeText(getApplicationContext(),
                            "Checkmate! Black wins!", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.show();
                    gameOver=true;
                    return;
                }

                //checkmate, black wins
                if (board.gameStatus()=='m' && Chessboard.blacksTurn) {
                    Toast toast= Toast.makeText(getApplicationContext(),
                            "Checkmate! White wins!", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.show();
                    gameOver = true;
                    return;
                }
                checkGameStatus();

                if (whitesTurn){
                    Toast toast= Toast.makeText(getApplicationContext(),
                            "Checkmate! Black wins!", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.show();
                    gameOver = true;
                    return;
                }
                if (blacksTurn){
                    Toast toast= Toast.makeText(getApplicationContext(),
                            "Checkmate! White wins!", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.show();
                    gameOver = true;
                    return;
                }

                return;
            }
        }

        firstmovemade=true; //this is to fix undo button
        undochosen = false;
        gridAdapter.notifyDataSetChanged();
        boardGridView.setAdapter(gridAdapter);
        saveLastState();
        //saveData();
        checkGameStatus();

        if (whitesTurn){
            whitesTurn=false;
            blacksTurn=true;
        }
        else{
            whitesTurn=true;
            blacksTurn=false;
        }
        return;

    }

    public void draw(View view) {
        //disables this button if the game is already over
        if (gameOver){
            Game g = new Game(moves);
            games.add(g);
            saveData();
            gameOver();
            return;
        }

        if (blacksTurn) {
            AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
            alert.setTitle("Draw Requested");
            alert.setMessage("White has requested a draw");

            alert.setPositiveButton("Agree", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    gameOver=true;
                    Game g = new Game(moves);
                    games.add(g);
                    saveData();
                    Toast toast= Toast.makeText(getApplicationContext(),
                            "Draw", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.show();
                    gameOver();
                }
            });

            alert.setNegativeButton("Disagree", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    Toast toast= Toast.makeText(getApplicationContext(),
                            "Draw request denied", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.show();

                }
            });



            alert.show();
        }
        else{
            AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
            alert.setTitle("Draw Requested");
            alert.setMessage("Black has requested a draw");

            alert.setPositiveButton("Agree", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    gameOver=true;
                    Game g = new Game(moves);
                    games.add(g);
                    saveData();

                    Toast toast= Toast.makeText(getApplicationContext(),
                            "Draw", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.show();

                    gameOver();
                }
            });

            alert.setNegativeButton("Disagree", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    Toast toast= Toast.makeText(getApplicationContext(),
                            "Draw request denied", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.show();

                }
            });



            alert.show();
        }
        /*else{
            AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
            alert.setTitle("Draw Requested");
            alert.setMessage("Black has requested a draw. Do you agree to it?");
            alert.show();
        }



        gameOver=true;
        Toast toast= Toast.makeText(getApplicationContext(),
                "Draw", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();


         */
    }
    public void resign(View view) {

        //disables this button if the game is already over
        if (gameOver){
            Game g = new Game(moves);
            games.add(g);
            saveData();
            gameOver();
            return;
        }

        gameOver=true;
        Game g = new Game(moves);
        games.add(g);
        saveData();

        if (whitesTurn){
            Toast toast= Toast.makeText(getApplicationContext(),
                    "White Resigns, Black Wins!", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.show();
        }
        else{
            Toast toast= Toast.makeText(getApplicationContext(),
                    "Black Resigns, White Wins!", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.show();
        }
        gameOver();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


      // System.out.println("MainActivity: On Create");
        //Bundle bun = getIntent().getExtras();
        //int index = b.getInt("index");

        //this.getSharedPreferences("SHARED_PREFS", 0).edit().clear().apply();
        //result.remove(12);
        //result.remove(13);

        loadData();
        games = result;
        moves = new ArrayList<Pieces[][]>();
        //games.clear();
        //result.clear();

        if(games==null && gameOver==false){
            games = new ArrayList<Game>();
        }

        if (gameOver==true) {
            Bundle bundle = getIntent().getExtras();
            //games = (ArrayList<Game>) bundle.getSerializable("arraylist");
            games = (ArrayList<Game>) bundle.getSerializable("gamelist");
            //result = games;
           // System.out.println("getting bundle in main activity size " + games.size());
            if (!games.isEmpty()) {
                System.out.println("game title received in main: " + games.get(0).getTitle());
            }
            saveData();
            gameOver = false;
        }



        /*
        if (playinggame==true){
            return;
        }
        if (playinggame==false){
            playinggame=true;
        }
        */

        // undo = findViewById(R.id.idUndo);


        //  pieces = getResources().getStringArray(R.array.pieces);
        //gets "pieces" array resource from strings xml file
        // getResources() - knows to look in resource (res) folder


        //resets everything, pieces and booleans
        //gameOver=false;


        Chessboard.whitesTurn=true;
        Chessboard.blacksTurn=false;



        //sets individual squares to their correct color, no pieces implemented yet
        for (int i=0; i<8; i++) {
            for (int j=0; j<8; j++) {

                if ((i%2!=0 && j%2==0) || (i%2==0 && j%2!=0)) {
                    Block b = new Block("black");
                    chessBoard[i][j] = b;
                    //board[i][j] = "## ";
                }
                else {
                    Block w = new Block("white");
                    chessBoard[i][j] = w;
                    //board[i][j] = "   ";
                }
            }
        }



        //sets pieces on board in starting positions
        for (int i=0; i<8; i++) {
            for (int j=0; j<8; j++) {

                if (i==0) {
                    if (j==0 || j==7) {
                        chessBoard[i][j].setPiece(new Rook("black"));
                    }
                    else if (j==1 || j==6) {
                        chessBoard[i][j].setPiece(new Knight("black"));
                    }
                    else if (j==2 || j==5) {
                        chessBoard[i][j].setPiece(new Bishop("black"));
                    }
                    else if (j==3) {
                        chessBoard[i][j].setPiece(new Queen("black"));
                    }
                    else {
                        chessBoard[i][j].setPiece(new King("black"));
                    }
                }
                if (i==1) {
                    chessBoard[i][j].setPiece(new Pawn("black"));
                }

                if (i==7) {
                    if (j==0 || j==7) {
                        chessBoard[i][j].setPiece(new Rook("white"));
                    }
                    else if (j==1 || j==6) {
                        chessBoard[i][j].setPiece(new Knight("white"));
                    }
                    else if (j==2 || j==5) {
                        chessBoard[i][j].setPiece(new Bishop("white"));
                    }
                    else if (j==3) {
                        chessBoard[i][j].setPiece(new Queen("white"));
                    }
                    else {
                        chessBoard[i][j].setPiece(new King("white"));
                    }
                }
                if (i==6) {
                    chessBoard[i][j].setPiece(new Pawn("white"));
                }

            }
        }


        //Chessboard.whitesTurn=true;
        //Chessboard.blacksTurn=false;

        boardGridView = findViewById(R.id.boardGridView); //gets ahold of id element in routes_list
        gridAdapter = new GridAdapter(chessBoard, this);
       // System.out.println("grid adapter update in main activity");
        gridAdapter.notifyDataSetChanged();
        boardGridView.setAdapter(gridAdapter);
        //rcrdings.setActivated(false);
        //promotion.setActivated(true);



        //saveLastState();



        //saveData();
        /*
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, R.layout.piecename_layout, pieces);
        //ArrayAdapter - getting source from an array
        //R.layout.route is individual UI that you apply to each list item
        //routeNames is the source
        boardGridView.setAdapter(adapter);
        //adapter is like the observable array list
*/



        //trying to get click squares to work
        boardGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //onItemClick is a functional interface
            //parent is the parent of each individual list item
            //parent is listview in this case
            //view is individual list item that was clicked
            //position is position of item in the list
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                move(position, view);

            }
        });



    }

    private void move(int pos, View view) {

       // System.out.println(pos);

        //save last move for undo button
        saveLastBoardState();


        //disables any further moves once the game is won
        if (gameOver){
            Game g = new Game(moves);
            games.add(g);
            saveData();
            gameOver();
            return;
        }

        //user's first click, this will be origin of the piece
        if (numButtonClicks==0){

            //if player is attempting to move from a space that does not have a piece
            if (chessBoard[pos / 8][pos % 8].getPiece()==null){
                Toast toast= Toast.makeText(getApplicationContext(),
                        "Must select a piece to move", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();
                return;
            }



            //player trying to move wrong piece
            if (Chessboard.whitesTurn && !(chessBoard[pos / 8][pos % 8].getPiece().isWhite)){
                Toast toast= Toast.makeText(getApplicationContext(),
                        "White's turn! must move a white piece", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();
                return;
            }

            //player trying to move wrong piece
            if (!Chessboard.whitesTurn && (chessBoard[pos / 8][pos % 8].getPiece().isWhite)){
                Toast toast= Toast.makeText(getApplicationContext(),
                        "Black's turn! must move a black piece", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();
                return;
            }


            originPosition = pos;

            view.setBackgroundColor(Color.parseColor("#eebdff"));


          //  System.out.println("one click");
            numButtonClicks++;
        }

        //user's second click, this will be the destination of the piece
        else if (numButtonClicks==1){
           // System.out.println("two clicks");
            destinationPosition = pos;


            //check for pawn promotion
            if (chessBoard[originPosition / 8][originPosition % 8].getPiece().getType().equals("pawn")){

                    //checking valid move for promotion

                //if white player moving to back row and no piece forward, can move forward
                    if ((whitesTurn && destinationPosition <= 7 &&
                            destinationPosition==originPosition-8 &&
                            chessBoard[destinationPosition / 8][destinationPosition % 8].getPiece()==null) ||
                            //if white player moving to back row horizontally and a piece is there
                (whitesTurn && destinationPosition <= 7 &&
                        (destinationPosition==originPosition-7 || destinationPosition==originPosition-9 ) &&
                        chessBoard[destinationPosition / 8][destinationPosition % 8].getPiece()!=null)){
                     //   System.out.println("white eligible for promo");
                        //if (chessBoard[originPosition / 8][originPosition % 8].getPiece().
                         //       isValidMove(originPosition % 8, originPosition / 8, destinationPosition % 8, destinationPosition / 8)) {
                            promotion(originPosition, destinationPosition);
                            return;
                       // }

                    }
                    if ((blacksTurn && destinationPosition >= 56 &&
                            destinationPosition==originPosition+8 &&
                            chessBoard[destinationPosition / 8][destinationPosition % 8].getPiece()==null) ||
                //if white player moving to back row horizontally and a piece is there
                (blacksTurn && destinationPosition >= 56 &&
                        (destinationPosition==originPosition+7 || destinationPosition==originPosition+9 ) &&
                        chessBoard[destinationPosition / 8][destinationPosition % 8].getPiece()!=null)){
                       // System.out.println("black eligible for promo");
                        //if (chessBoard[originPosition / 8][originPosition % 8].getPiece().
                          //      isValidMove(originPosition % 8, originPosition / 8, destinationPosition % 8, destinationPosition / 8)) {
                            promotion(originPosition, destinationPosition);
                           return;
                       // }
                    }


            }

            b = board.gameMove(originPosition, destinationPosition);

            if (b==false) {//invalid move
                //Toast.makeText(MainActivity.this, "Invalid move, try again", Toast.LENGTH_LONG).show();
                Toast toast= Toast.makeText(getApplicationContext(),
                        "Invalid move, try again", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();
                numButtonClicks=0;
                returnSquareToOriginalColor(pos, view);
                gridAdapter.notifyDataSetChanged();
                boardGridView.setAdapter(gridAdapter);
                return;
            }






            firstmovemade=true; //this is to fix undo button
            //so that the user cannot undo when no moves have been made
            saveLastState();
            //saveData();
            undochosen = false;
            gridAdapter.notifyDataSetChanged();
            boardGridView.setAdapter(gridAdapter);

            if (board.gameStatus()=='m' && Chessboard.whitesTurn) {
                Toast toast= Toast.makeText(getApplicationContext(),
                        "Checkmate! White wins!", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();
                gameOver=true;
                Game g = new Game(moves);
                games.add(g);
                saveData();
                gameOver();
                return;
            }
            if (board.gameStatus()=='m' && Chessboard.blacksTurn) {
                Toast toast= Toast.makeText(getApplicationContext(),
                        "Checkmate! Black wins!", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();
                gameOver = true;
                Game g = new Game(moves);
                games.add(g);
                saveData();
                gameOver();
                return;
            }

            if (board.gameStatus()=='c') {
                Toast toast= Toast.makeText(getApplicationContext(),
                        "Check!", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();;
            }



            if (Chessboard.whitesTurn==true){
                Chessboard.whitesTurn=false;
                Chessboard.blacksTurn=true;
            }
            else{
                Chessboard.whitesTurn=true;
                Chessboard.blacksTurn=false;
            }


            numButtonClicks=0;
        }
        else {
            //System.out.println("three clicks");
            numButtonClicks=0;
        }

        //Chessboard.whitesTurn=true;
        //Chessboard.blacksTurn=false;
        //board.gameMove(pos);




    }
    public void checkGameStatus(){

        //checkmate, white wins
        if (board.gameStatus()=='m' && Chessboard.whitesTurn) {
            Toast toast= Toast.makeText(getApplicationContext(),
                    "Checkmate! White wins!", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.show();
            gameOver=true;
            Game g = new Game(moves);
            games.add(g);
            saveData();
            gameOver();
            return;
        }

        //checkmate, black wins
        if (board.gameStatus()=='m' && Chessboard.blacksTurn) {
            Toast toast= Toast.makeText(getApplicationContext(),
                    "Checkmate! Black wins!", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.show();
            gameOver = true;
            Game g = new Game(moves);
            games.add(g);
            saveData();
            gameOver();
            return;
        }

        //check
        if (board.gameStatus()=='c') {
            Toast toast= Toast.makeText(getApplicationContext(),
                    "Check!", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.show();
        }
    }

    public void promotion(int originPosition, int destinationPosition){ //method i need to call
        Bundle bundle = new Bundle();

        Intent intent = new Intent(this, promotion.class);
        intent.putExtras(bundle);
        intent.putExtra("originPosition", originPosition);
        intent.putExtra("destinationPosition", destinationPosition);
       // System.out.println("promotion method origin pos: " + originPosition);
        //System.out.println("promotion method dest pos: " + destinationPosition);
        startActivity(intent);
    }



    public static void returnSquareToOriginalColor(int position, View view) {

        int col = position / 8 % 2;
        if (col == 0) {
            if (position % 2 == 0) {
                view.setBackgroundColor(Color.parseColor("#b276c4"));
            } else {
                view.setBackgroundColor(Color.parseColor("#5d1d70"));
            }

        } else {
            if (position % 2 == 0) {
                view.setBackgroundColor(Color.parseColor("#5d1d70"));
            } else {
                view.setBackgroundColor(Color.parseColor("#b276c4"));
            }
        }


    }


    //saves the previous state of the board for undo
    //handles when multiple pieces are moved
    //or a pawn in promoted etc and then undo button is hit
    public void saveLastBoardState() {
        //Pieces[][] mov = new Pieces[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                //there is a piece on the block, save it into pieces array to access later if needed
                if (chessBoard[i][j].getPiece() != null) {
                    savedBoard[i][j] = chessBoard[i][j].getPiece();
                    //mov[i][j] = chessBoard[i][j].getPiece();
                }
                else{
                    savedBoard[i][j]=null;
                    //mov[i][j] = null;
                }
            }
        }
        //moves.add(mov);

        //saves all moves in array
        //each element of this array is a pieces[][] double array which holds all the pieces in
        //the correct board positions
        // allmoves[moveSavedIndex] = saveBoard;
        // movesavedIndex++;


    }

    public static void saveLastState(){
        Pieces[][] mov = new Pieces[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                //there is a piece on the block, save it into pieces array to access later if needed
                if (chessBoard[i][j].getPiece() != null) {
                    //savedBoard[i][j] = chessBoard[i][j].getPiece();
                    mov[i][j] = chessBoard[i][j].getPiece();
                }
                else{
                    //savedBoard[i][j]=null;
                    mov[i][j] = null;
                }
            }
        }
        moves.add(mov);
        //saveData();
    }
    public void returnToLastBoardState(){
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                //there is a piece on the block, save it into pieces array to access later if needed
                if (savedBoard[i][j] != null) {
                    chessBoard[i][j].setPiece(savedBoard[i][j]);
                }
                else{
                    chessBoard[i][j].setPiece(null);
                }
            }
        }

        gridAdapter.notifyDataSetChanged();
        boardGridView.setAdapter(gridAdapter);

    }
/*
    public void saveData() {
        try {
            FileOutputStream fos = new FileOutputStream("res/raw/data.dat");
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(moves);

            oos.close();
            //fos.close();
        } catch (Exception exception) {

            exception.printStackTrace();
        }
    }

    /*
    void playbackGame(){
        allmoves[moveSavedIndex] = saveBoard;
        movesavedIndex++;

        //each int i is a new board state
        for (int i = 0; i<movesavedIndex-1; i++){

        }
*/


    //  }



    /*
    public void queenClicked(View view){
        System.out.println("queen");
    }
    public void bishopClicked(View view){
        System.out.println("bishop");

    }
    public void knightClicked(View view){
        System.out.println("knight");

    }
    public void rookClicked(View view){
        System.out.println("rook");
    }
    */

    public static Chessboard getBoard(){
        return board;
    }

    public static GridView getGridView(){
        return boardGridView;
    }

    public static GridAdapter getGridAdapter(){
        return gridAdapter;
    }

    public void onPause(){
        super.onPause();
       // System.out.println("in pause");
    }

    public void onResume(){
        super.onResume();
        //System.out.println("in resume");
    }

    protected void onDestroy(){
        super.onDestroy();
       // System.out.println("in destroy");
    }


    public void onBackPressed() {
       // System.out.println("back pressed");
        //super.onBackPressed();
        onPause();
        moveTaskToBack(true);
       // android.os.Process.killProcess(android.os.Process.myPid());
       // System.exit(1);

    }

    public void saveData() {
       // System.out.println("Game over. Saving data, size: " + games.size());
        if (!games.isEmpty()){
         //   System.out.println("In saveData, game title:" + games.get(0).getTitle());
        }
       // System.out.println("Number of moves: " + moves.size());
        SharedPreferences sharedPref = this.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
       // System.out.println("line 945");
        SharedPreferences.Editor editor = sharedPref.edit();
       // System.out.println("line 947");
        Gson gson = new Gson();
       // System.out.println("line 949");
        //games.clear();
        String json = gson.toJson(games);
       // System.out.println("line 951");
        editor.putString("task list", json);

        editor.commit();
        
       // System.out.println("line 952: second data save. Size: " + games.size());

        //editor.apply();
    }

    public void loadData(){
        SharedPreferences sharedPref = this.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);

        Gson gson = new Gson();
        String json = sharedPref.getString("task list", null);
        Type type = new TypeToken<ArrayList<Game>>() {}.getType();

        //result is being set to something strange, not getting game data i dont think
        result = gson.fromJson(json,type);
        //result.clear();
       // System.out.println("loading data, line 964 Main Activity. result Size: " + result.size());
        if(result==null){
            result = new ArrayList<Game>();
         //   System.out.println("list was null");
        }
       /* if (result!=null){
            System.out.println("game title: " + result.get(0).getTitle());
        }*/
       // return result;
    }

    public void recordList(View view) {
        loadData();
        Bundle bundle = new Bundle();
        bundle.putSerializable("arraylist", (Serializable) result);
       // System.out.println("recordList method: sending bundle with result " + result.size());
       // System.out.println("game title:" +  result.get(0).getTitle());
        Intent intent = new Intent(this, recordList.class);
        intent.putExtras(bundle);
        startActivity(intent);

    }

    public void gameOver(){
        loadData();
        Bundle bundle = new Bundle();
        bundle.putSerializable("games", (Serializable) result);
       // System.out.println("sending bundle with result " + result.size());
        Intent intent = new Intent(this, gameOver.class);
        intent.putExtras(bundle);
        startActivity(intent);


    }

}




