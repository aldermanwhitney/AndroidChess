package com.example.androidchess36;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Set;

import model.Game;
import model.Pieces;

public class recordList extends AppCompatActivity {
    RadioButton sortDate, sortTitle;
    ListView recordlist;
    TextView savedgames;
    ArrayList<Game> games;
   // Button delete;
   // public static final String SHARED_PREFS = "Shared prefs";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recordlist);
        recordlist = findViewById(R.id.recordlist);
        savedgames = findViewById(R.id.savedgames);
        sortDate = findViewById(R.id.sortDate);
        sortTitle = findViewById(R.id.sortTitle);
        //delete = findViewById(R.id.delete);
        //MainActivity.loadData();
        Bundle bundle = getIntent().getExtras();
        games = (ArrayList<Game>) bundle.getSerializable("arraylist");
       // games = bundle.getSerializeable("gamelist");
       // System.out.println("games size" + games.size());
        //games.sort(new TitleCompare());
        //games.clear();
        if(games==null){
            games = new ArrayList<Game>();
        }
        ArrayAdapter<Game> adapter = new ArrayAdapter<Game>(this, R.layout.textview, games);
       // System.out.println("writing to adapter");
        recordlist.setAdapter(adapter);
       // System.out.println("setting to adapter");

        recordlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int p, long id) {
                playback(p);
            }
        });

    }
/*
    public void deleteGame(View view){
        for(int i = 0 ; i < games.size(); i++) {
            if(games.get(i).getTitle().equals("unnamed")){
                games.remove(games.get(i));
                i = i-1;
            }
        }
    }*/

    class TitleCompare implements Comparator<Game> {

        @Override
        public int compare(Game o1, Game o2) {
            // TODO Auto-generated method stub
    if(o1.getTitle()==null) o1.setTitle("unnamed");
    if(o2.getTitle()==null) o2.setTitle("unnamed");
            //if((o1.getTitle().toUpperCase()).compareTo(o2.getTitle().toUpperCase()) != 0) {
                return ((o1.getTitle().toUpperCase()).compareTo(o2.getTitle().toUpperCase()));
            //}
            //return (o1.getArtist().toUpperCase()).compareTo(o2.getArtist().toUpperCase());

        }

    }

    class DateCompare implements Comparator<Game> {

        @Override
        public int compare(Game o1, Game o2) {
            // TODO Auto-generated method stub
            //if(o1.getTitle()==null) o1.setTitle("unnamed");
            //if(o2.getTitle()==null) o2.setTitle("unnamed");
            //if((o1.getTitle().toUpperCase()).compareTo(o2.getTitle().toUpperCase()) != 0) {
           // o1.getDate().getTime().compareTo(o2.getDate().getTime());
            if(o1==null || o2==null){
                return -1;
            }
            return ((o1.getDate()).compareTo(o2.getDate()));
            //}
            //return (o1.getArtist().toUpperCase()).compareTo(o2.getArtist().toUpperCase());

        }

    }

    public void sortbyTitle(View view){
        if(games==null){
            return;
        }
        games.sort(new TitleCompare());
        ArrayAdapter<Game> adapter = new ArrayAdapter<Game>(this, R.layout.textview, games);
       // System.out.println("writing to adapter");
        recordlist.setAdapter(adapter);
        //deleteGame(view);
    }

    public void sortbyDate(View view){
        if(games==null){
            return;
        }
        games.sort(new DateCompare());
        ArrayAdapter<Game> adapter = new ArrayAdapter<Game>(this, R.layout.textview, games);
        //System.out.println("writing to adapter");
        recordlist.setAdapter(adapter);
    }

    public void playback(int p) {
        //loadData();
      //  System.out.println("p in rec list" + p);
        Game g = games.get(p);
        Bundle bundle = new Bundle();
        bundle.putSerializable("game", (Serializable) g);
      //  System.out.println("sending bundle with result " + g.toString());
        Intent intent = new Intent(this, playback.class);
        intent.putExtras(bundle);
        startActivity(intent);

    }
    /*

        public static void saveSharedPreferencesLogList(List<Pieces[][]> boards) {
            SharedPreferences sharedPref = this.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            Gson gson = new Gson();
            String json = gson.toJson(boards);
            prefsEditor.putString("myJson", json);
            prefsEditor.commit();
        }

/*
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS_FILE, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = prefs.edit();
        try {
            editor.put .putString(TASKS, ObjectSerializer.serialize(currentTasks));
        } catch (IOException e) {
            e.printStackTrace();
        }
        editor.commit();
    /*
        File temp = this.getFileStreamPath("res/raw/data.dat");
        if(temp == null || !temp.exists()) {
            return;
        }
       else {
            try {
                //FileInputStream fis = this.openFileInput("res/raw/data.dat");
                ObjectInputStream ois = new ObjectInputStream(getResources().openRawResource(R.raw.data));
                boards = (ArrayList<Pieces[][]>) ois.readObject();
                ois.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        ArrayAdapter<Pieces[][]> adapter =
                new ArrayAdapter<>(this, R.layout.recordlist, boards);

        recordlist.setAdapter(adapter);

        //sortDate.is

        // get the name and detail from bundle
        Bundle bundle = getIntent().getExtras();
       // String routeName = bundle.getString(Routes.ROUTE_NAME);
       // String routeDetail = bundle.getString(Routes.ROUTE_DETAIL);

        // get the name and detail view objects
       // TextView routeNameView = findViewById(R.id.route_name);
       // TextView routeDetailView = findViewById(R.id.route_detail);

        // set name and detail on the views
        //routeNameView.setText(routeName);
        //routeDetailView.setText(routeDetail);
*/



}
