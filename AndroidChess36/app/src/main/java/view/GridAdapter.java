package view;

import controller.*;
import view.*;
import model.*;
import controller.Chessboard;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class GridAdapter extends BaseAdapter {
    private Context context;
    private Block[][] board;

    public GridAdapter(Block[][] b, Context c){
        this.context = c;
        this.board = b;

    }

    @Override
    public int getCount() {
        return 64;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView box;
        if (convertView == null) {

            box = new ImageView(context);
            int size = 96;
            //System.out.println("size " + size); parent.getWidth() / 8
            //new Gridview for each square on the board

            box.setLayoutParams(new GridView.LayoutParams(size, size));
            //System.out.println("Position"+ position);
            //sets colour of the square based on number of the square
            int col = position / 8 % 2;
            if (col == 0) {
                if (position % 2 == 0){
                    //System.out.println("Column " + col + " and Position " +position);
                    box.setBackgroundColor(Color.parseColor("#b276c4"));}
                else{
                    //System.out.println("Column " + col + " and Position " +position);
                    box.setBackgroundColor(Color.parseColor("#5d1d70"));
                }


            } else {
                if (position % 2 == 0){
                    box.setBackgroundColor(Color.parseColor("#5d1d70"));
                    //System.out.println("Column " + col + " and Position " +position);
                    }
                else{
                    box.setBackgroundColor(Color.parseColor("#b276c4"));
                    //System.out.println("Column " + col + " and Position " +position);
                    }
            }

            //gets the piece at position
            Pieces p = board[position / 8][position % 8].getPiece();

            //sets the icon of appropriate piece in the box
            if (p != null) {
                box.setImageResource(context.getResources().getIdentifier(p.getColorAndName(), "drawable", context.getPackageName()));
            }
        }

        else {
            box = (ImageView) convertView;
        }


        return box;
    }

    @Override
    public boolean isEmpty() {
        return super.isEmpty();
    }
}
