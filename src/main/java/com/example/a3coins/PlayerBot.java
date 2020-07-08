package com.example.a3coins;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.a3coins.MiniMax;

public class PlayerBot extends AppCompatActivity {

    // 0:yellow , 1:red , 2:empty
    int gamePlayed[][]=new int[3][3];
    char player='X',opponent='O';
    char activePlayer = opponent, count;
    TextView text;
    Button btn;
    ImageView im;
    boolean gameActive = true;
    char[][] board= {{'_','_','_'},
            {'_','_','_'},
            {'_','_','_'}};
    ImageView[][] imageViews=new ImageView[3][3];

    private int row,col;
    PlayerBot(){
        row=-1;
        col=-1;

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        setContentView(R.layout.activity_player_bot);
        text = findViewById(R.id.textView);
        Intent intent=getIntent();
        im=findViewById(R.id.imageView);
        imageViews[0][0]=findViewById(R.id.coin_1);
        imageViews[0][1]=findViewById(R.id.coin_2);
        imageViews[0][2]=findViewById(R.id.coin_3);
        imageViews[1][0]=findViewById(R.id.coin_4);
        imageViews[1][1]=findViewById(R.id.coin_5);
        imageViews[1][2]=findViewById(R.id.coin_6);
        imageViews[2][0]=findViewById(R.id.coin_7);
        imageViews[2][1]=findViewById(R.id.coin_8);
        imageViews[2][2]=findViewById(R.id.coin_9);

    }

    private void init(){
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                gamePlayed[i][j]=2;
            }
        }
    }
    public void dropIn(View v) throws InterruptedException {
        btn = findViewById(R.id.button);
        ImageView coin = (ImageView) v;
        String[] s =(v.getTag().toString()).split(" ");

        int rowP=Integer.parseInt(s[0]);
        int colP=Integer.parseInt(s[1]);
        count = 0;
        if ( isMovesLeft() && gameActive) {

            String winner="";
            if (activePlayer == opponent && gamePlayed[rowP][colP]==2) {
                coin.setTranslationY(-1500);
                board[rowP][colP] = activePlayer;
                coin.setImageResource(R.drawable.yellow);
                int score=evaluate(board);
                coin.animate().translationYBy(1500).setDuration(200);
                gamePlayed[rowP][colP]=1;
                if (score==-10){
                    winner = "Yellow";
                    gameActive = false;
                    im.setImageResource(R.drawable.yellow);
                    text.setText(winner + " has won");
                    btn.setVisibility(View.VISIBLE);
                }
                activePlayer = player;
            }
            if ( isMovesLeft() && gameActive && activePlayer==player) {
                PlayerBot playerBot = findBestMove(board);
                row = playerBot.getRow();
                col = playerBot.getCol();
                imageViews[row][col].setTranslationY(-1500);
                board[row][col] = activePlayer;
                imageViews[row][col].setImageResource(R.drawable.red);
                activePlayer = opponent;
                int score = evaluate(board);
                imageViews[row][col].animate().translationYBy(1500).setDuration(800);
                gamePlayed[row][col]=1;
                if (score == 10) {
                    winner = "Red";
                    gameActive = false;
                    im.setImageResource(R.drawable.red);
                    text.setText(winner + " has won");
                    btn.setVisibility(View.VISIBLE);
                }
            }
            if (!isMovesLeft()) {
                btn.setVisibility(View.VISIBLE);
                text.setText("Match Tied");
            }

        }
    }

    int evaluate(char board[][]){
        //check vertical
        for(int i=0;i<3;i++){
            if(board[0][i]==board[1][i] && board[1][i]==board[2][i]){
                if (board[0][i]==player){
                    return 10;
                }
                else if (board[0][i]==opponent){
                    return -10;
                }
            }
        }
        //check horizontal
        for(int i=0;i<3;i++){
            if(board[i][0]==board[i][1] && board[i][1]==board[i][2]){
                if (board[i][0]==player){
                    return 10;
                }
                else if (board[i][0]==opponent){
                    return -10;
                }
            }
        }
        //check diagonal
        if((board[0][2]==board[1][1] && board[1][1]==board[2][0])||(board[0][0]==board[1][1] && board[1][1]==board[2][2])){
            if (board[1][1]==player)
                return 10;
            else if (board[1][1]==opponent)
                return -10;
        }

        return 0;
    }
    public void playAgain(View v){
        GridLayout grid=findViewById(R.id.board);
        for(int i=0;i<grid.getChildCount();i++){
            ImageView im=(ImageView) grid.getChildAt(i);
            im.setImageDrawable(null);
        }
        for (int j = 0; j < 3; j++) {
            for(int i=0;i<3;i++)
                board[j][i] = '_';
        }
        init();
        text.setText("");
        im.setImageDrawable(null);
        btn.setVisibility(View.INVISIBLE);
        gameActive=true;
        activePlayer=opponent;
    }

    private int minimax(char board[][],int depth,boolean isMax){
        int score=evaluate(board);
        if(score==10)
            return 10-depth;
        if(score==-10)
            return -10+depth;
        if (!isMovesLeft())
            return 0;
        if(isMax){
            int best=-1000;
            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){
                    if(board[i][j]=='_'){
                        board[i][j]=player;
                        best=Math.max(best,minimax(board,depth+1,!isMax));
                        board[i][j]='_';
                    }
                }
            }
            return best;
        }
        else{
            int best=1000;
            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){
                    if(board[i][j]=='_'){
                        board[i][j]=opponent;
                        best=Math.min(best,minimax(board,depth+1,!isMax));
                        board[i][j]='_';
                    }
                }
            }
            return best;
        }

    }

    PlayerBot findBestMove(char[][] board){
        int bestMove=-1000;
        for (int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(board[i][j]=='_'){
                    board[i][j]=player;
                    int moveVal=minimax(board,0,false);
                    board[i][j]='_';

                    if(moveVal>bestMove){
                        this.row=i;
                        this.col=j;
                        bestMove=moveVal;
                    }
                }

            }
        }
        return this;
    }

    int getRow(){
        return this.row;
    }

    int getCol(){
        return this.col;
    }
    boolean isMovesLeft(){
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(board[i][j]=='_')
                    return true;
            }
        }
        return false;
    }

}
