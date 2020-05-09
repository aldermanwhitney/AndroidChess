package com.example.androidchess36;
import java.io.Serializable;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import model.Game;
import model.Pieces;

import androidx.appcompat.app.AppCompatActivity;

public class gameOver extends AppCompatActivity {

    Button savegame;
    Button cancel;
    TextView textView;
    EditText title;
    ArrayList<Game> games;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameover);
        savegame = findViewById(R.id.savegame);
        cancel = findViewById(R.id.cancel);
        textView = findViewById(R.id.textView);
        title = findViewById(R.id.title);
        Bundle bundle = getIntent().getExtras();
        games = (ArrayList<Game>) bundle.getSerializable("games");
        //System.out.println("sending bundle from MainActivity to gameover with result " + games.size());


    }

    public void saveGame(View view){
        Game g = games.get(games.size()-1);
        String s = title.getText().toString();
        g.setTitle(s);
        //System.out.print("game title:" + g.title);
        mainActivity();

    }

    public void cancelGame(View view){
        //if(games!=null)
        games.remove(games.get(games.size()-1));

        //may need to change this later
        //in some way to create a new game instead of returning to old game that is now over
        mainActivity();
    }
    public void mainActivity() {

        //"gamelist" is never actually received in mainActivity.java at any point
        //basically were just throwing away this data
        //bundle needs to be received in onCreate
        //just set a condition so it does not try to reieve the bundle when there is no bundle
        //ie if gameOver==false

        Bundle bundle = new Bundle();
        bundle.putSerializable("gamelist", (Serializable) games);
       // System.out.println("sending bundle from gameOver to MainActivity with result " + games.size());
//        System.out.println("game name: " + games.get(0).getTitle());
        //System.out.println("game as object to string: " + games.get(0));

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtras(bundle);

        //Intent intent2 = new Intent(this, recordList.class);
        //intent2.putExtras(bundle);

        startActivity(intent);

    }

    public void onBackPressed() {
        games.remove(games.get(games.size()-1));
        mainActivity();
    }

}
