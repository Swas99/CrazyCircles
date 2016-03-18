package com.archer.crazy_circle_games;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.archer.crazy_circle_games.R;
import com.archer.crazy_circle_games.box_d_ball.Helper;


/**
 * Created by Swastik on 14-03-2016.
 */
public class SelectGameAdapter extends BaseAdapter {
    private Context mContext;
    private Integer[] Cards = {R.drawable.ic_box_d_ball,R.drawable.ic_circle_run,
        R.drawable.ic_survival_circle,R.drawable.ic_survival_circle_2};
    private String[] GameName = {"Box-d-Ball","Circle Run","Survival Circle","Survival Circle 2"};
    String Box_d_Ball_description = "Motion sensor game.\nAim : Keep the ball inside the box." +
            "\n" + "Control the ball by moving the phone";
    String Circle_Run_description = "Aim : Move around the circle & complete as many circles as possible";
    String Survival_Circle_1_description = "Aim : Stay inside the circle\nTap on the virtual d-pad to move the ball";
    String Survival_Circle_2_description = "Aim : Stay inside the circle\nTouch the virtual d-pad to control the ball";

    private String[] GameDescription = {
            Box_d_Ball_description,
            Circle_Run_description,
            Survival_Circle_1_description,
            Survival_Circle_2_description
    };


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
            TextView tvDescription = (TextView) adapter_select_game.findViewById(R.id.tvDescription);
            tvDescription.setText(GameDescription[position]);
            ImageView card = (ImageView)adapter_select_game.findViewById(R.id.ivCard);
            card.setBackgroundResource(Cards[position]);
            convertView = adapter_select_game;
        }
        return convertView;
    }


}