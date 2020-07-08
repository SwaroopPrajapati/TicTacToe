package com.example.a3coins;

public class MiniMax {
    private char player='X',opponent='O';
    private int row,col;
    MiniMax(){
        row=-1;
        col=-1;

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

    private int minimax(char board[][],int depth,boolean isMax){
        int score=evaluate(board);
        if(score==10)
            return 10-depth;
        if(score==-10)
            return -10+depth;
        if (!isMovesLeft(board))
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

    MiniMax findBestMove(char[][] board){
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
    boolean isMovesLeft(char board[][]){
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(board[i][j]=='_')
                    return true;
            }
        }
        return false;
    }
}
