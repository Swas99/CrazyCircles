package com.archer.crazy_circle_games;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.archer.crazy_circle_games.R;


/**
 * Created by Swastik on 14-03-2016.
 */
public class SelectGameAdapter extends BaseAdapter {
    private Context mContext;
    private Integer[] Cards = {R.drawable.ic_box_d_ball,R.drawable.ic_circle_run,
        R.drawable.ic_survival_circle,R.drawable.ic_survival_circle_2};
    private String[] GameName = {"Box-d-Ball","Circle Run","Survival Circle","Survival Circle 2"};


    public SelectGameAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return Cards.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        View adapter_select_game;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            adapter_select_game = inflater.inflate(R.layout.adapter_cards, null);
            TextView tvGameName = (TextView) adapter_select_game.findViewById(R.id.tvGameName);
            tvGameName.setText(GameName[position]);
            ImageView card = (ImageView)adapter_select_game.findViewById(R.id.ivCard);
            card.setBackgroundResource(Cards[position]);
            convertView = adapter_select_game;
        }
        return convertView;
    }


}