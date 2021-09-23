package othellogame;

import java.io.Serializable;

public class OthelloPosition extends Position implements Serializable{
     static final long serialVersionUID = -7588980448693010399L;
    final static public int BLANK = 0;
    final static public int HUMAN = 1;
    final static public int PROGRAM = -1;
    int [] board = new int[64];// othello est compose de 64 cases
    //constructeur par defaut
    public OthelloPosition()
    {
        /*initialisation du Board
          l'etat initial du jeu othello:
          quatre pions, deux noirs et deux blancs, sont posés au milieu du plateau 
          (les pions de la même couleur en diagonale)
        */
    board[27]=-1;
    board[28]=1;
    board[35]=1;
    board[36]=-1;
    }
}

