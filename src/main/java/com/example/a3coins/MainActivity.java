package com.example.a3coins;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    // 0:yellow , 1:red , 2:empty
    int activePlayer = 0, count;
    TextView text;
    Button btn;
    ImageView im;
    boolean gameActive = true;
    int[] gamePlayed = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    int[][] winningPoints = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8},{0,4,8},{2,4,6}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = findViewById(R.id.textView);
        im=findViewById(R.id.imageView);
        Intent intent=getIntent();
    }

    public void dropIn(View v) {
        btn = findViewById(R.id.button);
        ImageView coin = (ImageView) v;
        int tapped = Integer.parseInt(v.getTag().toString());
        count = 0;
        if (gamePlayed[tapped] == 2 && gameActive == true) {
            coin.setTranslationY(-1500);
            if (activePlayer == 0) {
                gamePlayed[tapped] = activePlayer;
                coin.setImageResource(R.drawable.yellow);
                activePlayer = 1;
            } else {
                gamePlayed[tapped] = activePlayer;
                coin.setImageResource(R.drawable.red);
                activePlayer = 0;
            }
            coin.animate().translationYBy(1500).setDuration(200);
            for (int g : gamePlayed) {
                if (g == 2) {
                    count++;
                }
            }
            if (count == 0) {
                btn.setVisibility(View.VISIBLE);
                text.setText("Match Tied");
            }
            for (int[] w : winningPoints) {
                String winner = "";
                if (gamePlayed[w[0]] == gamePlayed[w[1]] && gamePlayed[w[1]] == gamePlayed[w[2]] && gamePlayed[w[0]] != 2) {
                    if (activePlayer == 1)
                        winner = "Yellow";
                    else
                        winner = "Red";
                    gameActive = false;
                    if(activePlayer==1)
                        im.setImageResource(R.drawable.yellow);
                    else
                        im.setImageResource(R.drawable.red);
                    text.setText(winner + " has won");
                    btn.setVisibility(View.VISIBLE);
                    for (int i = 0; i < gamePlayed.length; i++) {
                        gamePlayed[i] = 2;
                    }
                }
            }
        }
    }
    public void playAgain(View v){
        GridLayout grid=findViewById(R.id.board);
        for(int i=0;i<grid.getChildCount();i++){
            ImageView im=(ImageView) grid.getChildAt(i);
            im.setImageDrawable(null);
        }
        for (int j = 0; j < gamePlayed.length; j++) {
            gamePlayed[j] = 2;
        }
        text.setText("");
        im.setImageDrawable(null);
        btn.setVisibility(View.INVISIBLE);
        gameActive=true;
        activePlayer=0;
    }
}
