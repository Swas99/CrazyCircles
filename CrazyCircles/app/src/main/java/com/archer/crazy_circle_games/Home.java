package com.archer.crazy_circle_games;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final GridView gridview = (GridView) findViewById(R.id.gdCards);
        final SelectGameAdapter adapter = new SelectGameAdapter(getApplicationContext());
        gridview.setAdapter(adapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position)
                {
                    case 0:
                        Intent box_d_ball = new Intent(
                                Home.this, com.archer.crazy_circle_games.box_d_ball.MainActivity.class);
                        startActivity(box_d_ball);
                        break;
                    case 1:
                        Intent circle_run = new Intent(
                                Home.this, com.archer.crazy_circle_games.circle_run.MainActivity.class);
                        startActivity(circle_run);
                        break;
                    case 2:
                        Intent survival_circle = new Intent(
                                Home.this, com.archer.crazy_circle_games.survival_circle.MainActivity.class);
                        startActivity(survival_circle);
                        break;
                    case 3:
                        Intent survival_circle_2 = new Intent(
                                Home.this, com.archer.crazy_circle_games.survival_circle_2.MainActivity.class);
                        startActivity(survival_circle_2);
                        break;
                }

                finish();
            }
        });
    }

}
