package model;

import android.os.Parcelable;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Game implements Serializable {
    public ArrayList<Pieces[][]> boards = new ArrayList<Pieces[][]> ();
    public String title;
    Date date;
    public static final String sdf = "MM/dd/yyyy HH:mm:ss";

    private static final long serialVersionUID = 1L;

    public Game(ArrayList<Pieces[][]> chessboards){
        this.boards = chessboards;

        //need to figure out to delete game when back button pressed without giving name else null pointer
        this.title="unnamed";
        this.date = Calendar.getInstance().getTime();

    }



    public String getCurrentTime() {
        if(this.date == null){
            return "-1";
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(sdf);
        dateFormat.setTimeZone(TimeZone.getTimeZone("America/New_York"));

        return dateFormat.format(this.date);
    }

    public void setTitle(String s){
        this.title = s;
    }

   /* public void setDate(Calendar c){
        this.date = c;
    }
*/
   public Date getDate(){ return this.date; }

    public String getTitle(){
        return this.title;
    }

    public void addBoard(Pieces[][] board){
        this.boards.add(board);
    }

    public ArrayList<Pieces[][]> getBoards(){
        return boards;
    }

    public String toString(){
       // System.out.println("trying to print game values");
       // System.out.println("game title: " + this.title);
        return this.title + " " + getCurrentTime();
        //return this.title;
    }

}
