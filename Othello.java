package othellogame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Vector;
import java.util.Scanner;
import java.util.Vector;
import javax.swing.Timer;

public class Othello extends GameSearch {
// Tableau pour la fonction d'evaluation
private int [] tab = { 100, -20, 10, 5, 5, 10, -20,100,
                       -20, -50, -2, -2, -2, -2, -50, -20,
                       10, -2, -1, -1, -1,-1, -2, 10,
                       5, -2, -1, -1, -1, -1, -2, 5,
                       5, -2, -1, -1, -1, -1, -2, 5,
                       10, -2, -1, -1, -1,-1, -2, 10,
                       -20, -50, -2, -2, -2, -2, -50, -20,
                       100, -20, 10, 5, 5, 10, -20,100};
ArrayList<Integer> pm=new ArrayList<Integer>();
ArrayList<Integer> pm2=new ArrayList<Integer>();
private ArrayList<Position> trace= new ArrayList<Position>();
int nbrh=0;
Body b;
int ts=0;
Timer timer1;
boolean f=false;
int nbrp=0;
private boolean mode=false;
private int niveau=2;
boolean actualplayer; 
//Mode: Partie Humain vs Machine ou Humain vs Humain
public void setmode(boolean f)
{
this.mode=f;
}
//le niveau de difficulte
public void setNiveau(int n){
    this.niveau = n;
    this.mode=true;//si l'user a choisi une partie H vs M on met mode=true, Ps: le niveau de difficulte est appele juste lors d'une partie H vs M
    }
public int getNiveau()
{
return niveau;
}
public void addtotrace(Position p)
{
this.trace.add(p);
}
//Constructeur
 public Othello(OthelloPosition ticp,Body bod,Header h)
 {
     b=bod;
     b.setposition(ticp);
     b.setai(pm);
     this.head=h;  
 }
    public boolean drawnPosition(Position p) {
        if (GameSearch.DEBUG) System.out.println("drawnPosition("+p+")");
        boolean ret = true;
        OthelloPosition pos = (OthelloPosition)p;
        for (int i=0; i<64; i++) {
            if (pos.board[i] == OthelloPosition.BLANK){
                ret = false;
                break;
            }
        }
        if (GameSearch.DEBUG) System.out.println("     ret="+ret);
        return ret;
    }
    public boolean wonPosition(Position p, boolean player) {
        if (GameSearch.DEBUG) System.out.println("wonPosition("+p+","+player+")");
        int count=0;
        OthelloPosition pos = (OthelloPosition)p;
        int b;
        actualplayer=player;
        if(player)  b=1;
        else b=-1;
        //si toutes les cases sont remplis
        if(drawnPosition(p))
        {
        for(int i=0;i<64;i++)
        {
        if(pos.board[i]==b) 
            count++;
        }
        if(count>30) return true;
       else if(pm.isEmpty() && pm2.isEmpty() && count>64-count) return true;
        else return false;
        }
        //sinon 
        else {
         //nombre des pions noires  
         int p1=0;
          //nombre des pions blancs 
          int p2=0;
            for(int i=0;i<64;i++)
        {
           // calcul de nombres de pions pour chaque player
        if(pos.board[i]==b) 
            p1++;
        else if(pos.board[i]==b*-1)
            p2++;
        }
           /*si l'un des joueurs n' a plus de pions sur le board
            automatiquement l'autre joueur a gagné meme si le board n'est pas encore remplie
            */
           if(p2==0) return true;
           else if(p1==0) return false;
           else return false;
        }
    }

    public float positionEvaluation(Position p, boolean player) {
        int count = 0;
        actualplayer=player;
        OthelloPosition pos = (OthelloPosition)p;
        int b;
        if(player) b=1;
        else b=-1;
        for (int i=0; i<64; i++) {
            if (pos.board[i] ==b) count++;
        }
        int e=0;
        for(int i=0;i<64;i++)
        {
        if(pos.board[i]==1) e+=tab[i];
        else if(pos.board[i]==-1) e-=tab[i];
        }
        if (wonPosition(p, player))  {
            return e + (1.0f / 65-count);
        }
        if (wonPosition(p, !player))  {
            return -(e + (1.0f / count));
        }
        return e;
    }
    public void printPosition(Position p) {
        OthelloPosition pos = (OthelloPosition)p;
        nbreh(p);
        //afficher le nombre de pions pour chaque joueur sur JFrame
        this.head.jb1.setText(String.valueOf(nbrp));
        this.head.jb2.setText(String.valueOf(nbrh));
         head.repaint(head.jb1.getBounds());
           head.repaint(head.jb2.getBounds());
        //print new position
        b.setposition(pos);
       this.b.repaint(0, 0, 500, 600);
    }
    
    public Position [] possibleMoves(Position p, boolean player) {
        if (GameSearch.DEBUG) System.out.println("posibleMoves("+p+","+player+")");
        actualplayer=player;
        OthelloPosition pos = (OthelloPosition)p;
        int count = 0;
        int b;
        if(player) b=1;
        else b=-1;
        pm.clear();
        pm2.clear();
        ArrayList<Integer> ai=new ArrayList<Integer>();
        ArrayList<Position> ap = new ArrayList<Position>();
        for(int i=0;i<64;i++)
        {
            if(pos.board[i]==b)
            {
                int a=i-8;
                int a1=i+8;
                int c=i-1;
                int c1=i+1;
                int d=i-9;
                int d1=i+9;
                int d2=i-7;
                int d3=i+7;
                boolean f=false;
                //pour les voisins vertical en haut
                while(a>0)
                {
                    if(pos.board[a]==b*-1) 
                    {
                         ai.add(a);
                         a-=8;
                         f=true;
                    } 
                    else if(pos.board[a]==0){
                        if(f && !pm.contains(a) && !pm2.contains(a)) {
                            count++;
                            ai.add(a);
                            if(player)  pm.add(a);
                            if(!player)  pm2.add(a);
                            OthelloPosition pos2 = new  OthelloPosition();
                            //pour chaque cases vides on va créer une position qui contient un board 
                            //ensuite on va remplir les elements déjà existant dans le board 
                            for (int j=0; j<64; j++) {
                                if(ai.contains(j)) pos2.board[j] = b;
                                else pos2.board[j] =pos.board[j];
                            }
                            ap.add(pos2);
                        }
                        break;
                    }
                    else break;
                }
                //pour les voisins verticles en bas 
                f=false;
                ai.clear();
                while(a1<64)
                {
                    if(pos.board[a1]==b*-1) 
                    {
                        ai.add(a1);
                        a1+=8;
                        f=true;
                    } 
                    else if(pos.board[a1]==0){
                        if(f && !pm.contains(a1) && !pm2.contains(a1)) {
                            count++;
                            ai.add(a1);
                            if(player)   pm.add(a1);
                            if(!player)   pm2.add(a1);
                            OthelloPosition pos2 = new  OthelloPosition();
                             //pour chaque cases vides on va créer une position qui contient un board 
                            //ensuite on va remplir les elements déjà existant dans le board 
                            for (int j=0; j<64; j++) {
                                if(ai.contains(j)) pos2.board[j] = b;
                                else pos2.board[j] =pos.board[j];
                            }
                            ap.add(pos2);
                        }
                        break;
                    }
                    else break;
                }
                //pour les voisins horizontales à gauche
                f=false;
                int temp=i/8*8;
                ai.clear();
                while(c>temp-1)
                {
                    if(pos.board[c]==b*-1) 
                    {
                        ai.add(c);
                        c-=1;
                        f=true;
                    } 
                    else if(pos.board[c]==0){
                        if(f && !pm.contains(c) && !pm2.contains(c)){
                          count++;
                          ai.add(c);
                          if(player) pm.add(c);
                          if(!player) pm2.add(c);
                          OthelloPosition pos2 = new  OthelloPosition();
                          //pour chaque cases vides on va créer une position qui contient un board 
                          //ensuite on va remplir les elements déjà existant dans le board 
                          for (int j=0; j<64; j++) {
                            if(ai.contains(j)) pos2.board[j] = b;
                            else pos2.board[j] =pos.board[j];
                            }
                          ap.add(pos2);
                        }
                        break;
                    }
                    else break;
                }
                //pour les voisins horizontales à droite
                int temp2=(i/8*8)+8;
                f=false;
                ai.clear();
                while(c1<temp2 && c1<64)
                {
                    if(pos.board[c1]==b*-1) 
                    {
                        ai.add(c1);
                        c1+=1;
                        f=true;
                    } 
                    else if(pos.board[c1]==0){
                        if(f && !pm.contains(c1) && !pm2.contains(c1)) {
                            count++;
                            ai.add(c1);
                            if(player)  pm.add(c1);
                            if(!player)  pm2.add(c1);
                            OthelloPosition pos2 = new  OthelloPosition();
                            //pour chaque cases vides on va créer une position qui contient un board 
                            //ensuite on va remplir les elements déjà existant dans le board 
                            for (int j=0; j<64; j++) {
                                if(ai.contains(j)) pos2.board[j] = b;
                                else pos2.board[j] =pos.board[j];
                            }
                            ap.add(pos2);
                        }
                        break;
                    }
                    else break;
                }
                //Pour les voisins au diagonales 
                f=false;
                ai.clear();
                if(i%8!=0 && d>=0)
                {
                    do 
                    {
                        if(pos.board[d]==b*-1) 
                        {
                            ai.add(d);
                            d-=9;
                            f=true;
                        } 
                        else if(pos.board[d]==0){
                            if(f && !pm.contains(d) && !pm2.contains(d)) {count++;
                            ai.add(d);
                            if(player)  pm.add(d);
                            if(!player)  pm2.add(d);
                            OthelloPosition pos2 = new  OthelloPosition();
                            //pour chaque cases vides on va créer une position qui contient un board 
                            //ensuite on va remplir les elements déjà existant dans le board 
                            for (int j=0; j<64; j++) {
                                if(ai.contains(j)) pos2.board[j] = b;
                                else pos2.board[j] =pos.board[j];
                            }
                            ap.add(pos2);
                        }
                        break;
                    }
                    else break;
                }while(d%8!=0 && d>=0);
                }
                f=false;
                ai.clear();
                if(i%8!=0 && d3<64)
                {
                    do 
                    {
                        if(pos.board[d3]==b*-1) 
                        {
                            ai.add(d3);
                            d3+=7;
                            f=true;
                        } 
                        else if(pos.board[d3]==0){
                            if(f && !pm.contains(d3) && !pm2.contains(d3)) {
                                count++;
                                ai.add(d3);
                                if(player)  pm.add(d3);
                                if(!player)  pm2.add(d3);
                                OthelloPosition pos2 = new  OthelloPosition();
                                //pour chaque cases vides on va créer une position qui contient un board 
                                //ensuite on va remplir les elements déjà existant dans le board 
                                for (int j=0; j<64; j++) {
                                    if(ai.contains(j)) pos2.board[j] = b;
                                    else pos2.board[j] =pos.board[j];
                                }
                                ap.add(pos2);
                            }
                            break;
                        }
                        else break;
                    }while(d3%8!=0 && d3<64);
                }
                f=false;
                ai.clear();
                 if((i+1)%8!=0 && d2>=0)
                 {
                    do 
                    {
                        if(pos.board[d2]==b*-1) 
                        {
                            ai.add(d2);
                            if((d2+1)%8==0) break; 
                            d2-=7;
                            f=true;
                        } 
                    else if(pos.board[d2]==0){
                        if(f && !pm.contains(d2) && !pm2.contains(d2)) {
                            count++;
                            ai.add(d2);
                            if(player) pm.add(d2);
                            if(!player) pm2.add(d2);
                            OthelloPosition pos2 = new  OthelloPosition();
                            //pour chaque cases vides on va créer une position qui contient un board 
                            //ensuite on va remplir les elements déjà existant dans le board 
                            for (int j=0; j<64; j++) {
                                if(ai.contains(j)) pos2.board[j] = b;
                                else pos2.board[j] =pos.board[j];
                            }
                            ap.add(pos2);
                        }
                        break;
                    }
                    else break;
                }while((d2+1)%8!=0 && d2 >= 0);
                }
                f=false;
                ai.clear();
                while(d1<64)
                {
                    if(pos.board[d1]==b*-1) 
                    {
                        ai.add(d1);
                        if((d1+1)%8==0) 
                            break;
                        d1+=9;
                        f=true;
                    } 
                    else if(pos.board[d1]==0){
                        if(f && !pm.contains(d1) && !pm2.contains(d1)) {
                            count++;
                            ai.add(d1);
                            if(player)   pm.add(d1);
                            if(!player)   pm2.add(d1);
                            OthelloPosition pos2 = new  OthelloPosition();
                            //pour chaque cases vides on va créer une position qui contient un board 
                            //ensuite on va remplir les elements déjà existant dans le board 
                            for (int j=0; j<64; j++) {
                                if(ai.contains(j)) pos2.board[j] = b;
                                else pos2.board[j] =pos.board[j];
                            }
                            ap.add(pos2);
                        }
                        break;
                    }
                    else break;
                }
                ai.clear();
            }
        }
        //obtenir le nombre de cases vides qu'on peut remplir 
        if (count == 0) return null;//1,-1,0
        Position [] ret = new Position[count];
        for(int i=0;i<ap.size();i++) ret[i]=ap.get(i);
        return ret;
    }
    public Position makeMove(Position p, boolean player, Move move) {
        if (GameSearch.DEBUG) System.out.println("Entered TicTacToe.makeMove");
        actualplayer=player;
        //ON créé un objet move qui contient un seul attribut index
        OthelloMove m = (OthelloMove)move;
        //on copie la position trasmis en paramètre
        OthelloPosition pos = (OthelloPosition)p;
        //on créé un objet position 
        OthelloPosition pos2 = new  OthelloPosition();
        //on copie les éléments de l'objet transmis dans le nouveau objet 
        for (int i=0; i<64; i++) pos2.board[i] = pos.board[i];
        //variable qui va stocker 1 si le joueur est un humain ou -1 si c'est un programme
        int pp;
        if (player) pp =  1;
        else        pp = -1;
        if (GameSearch.DEBUG) System.out.println("makeMove: m.moveIndex = " + m.moveIndex);
        //remplissage du board dans l'index spécifié par 1 si c'est un humain ou -1 si c'est un programme 
        if(m.moveIndex!=-1)
        {
        pos2.board[m.moveIndex] = pp;
        int a=m.moveIndex-8;
                int a1=m.moveIndex+8;
                int c=m.moveIndex-1;
                int c1=m.moveIndex+1;
                int d=m.moveIndex-9;
                int d1=m.moveIndex+9;
                int d2=m.moveIndex-7;
                int d3=m.moveIndex+7;
                boolean f=false;
                ArrayList<Integer> ai=new ArrayList<Integer>();
                //pour les voisins vertical en haut
                while(a>0)
                {
                    if(pos.board[a]==pp*-1) 
                    {
                        ai.add(a);
                        a-=8;
                        f=true;
                    } 
                    else if(pos.board[a]==pp)
                    {
                        if(f)
                        {
                            for(int j=0;j<ai.size();j++) 
                            pos2.board[ai.get(j)]=pp;
                        }
                        break;
                    }
                    else break;
                }
                f=false;
                ai.clear();
                //pour les voisins verticles en bas 
                while(a1<64)
                {
                    if(pos.board[a1]==pp*-1) 
                    {
                        ai.add(a1);
                        a1+=8;
                        f=true;
                    } 
                    else if(pos.board[a1]==pp)
                    {
                        if(f)
                        {
                            for(int j=0;j<ai.size();j++) 
                            pos2.board[ai.get(j)]=pp;
                        }
                        break;
                    }
                    else break;
                }
                //pour les voisins horizontales à gauche
                int temp=c/8*8;
                ai.clear();
                f=false;
                while(c>temp-1)
                {
                    if(pos.board[c]==pp*-1) 
                    {
                        ai.add(c);
                        c-=1;
                        f=true;
                    }
                    else if(pos.board[c]==pp)
                    {
                        if(f)
                        {
                            for(int j=0;j<ai.size();j++) 
                            pos2.board[ai.get(j)]=pp;
                        }
                        break;
                    }
                    else break;
                }
                //pour les voisins horizontales à droite
                int temp2=(c1/8*8)+8;
                f=false;
                ai.clear();
                while(c1<temp2 && c1<64)
                {
                    if(pos.board[c1]==pp*-1) 
                    {
                        ai.add(c1);
                        c1+=1;
                        f=true;
                    } 
                    else if(pos.board[c1]==pp)
                    {
                        if(f)
                        {
                             for(int j=0;j<ai.size();j++) 
                             pos2.board[ai.get(j)]=pp;
                        }
                        break;
                    }
                    else break;
                }
                //Pour les voisins au diagonales 
                f=false;
                ai.clear();
                if(m.moveIndex%8!=0 && d>=0)
                {
                do 
                {
                 if(pos.board[d]==pp*-1) 
                    {
                        ai.add(d);
                        f=true;
                        d-=9;
                    } 
                 else if(pos.board[d]==pp)
                    {
                        if(f)
                        {
                             for(int j=0;j<ai.size();j++) 
                             pos2.board[ai.get(j)]=pp;
                        }
                        break;
                    }
                    else break;
                }while(d%8!=0 && d>=0);
                }
                f=false;
                ai.clear();
                if(m.moveIndex%8!=0 && d3<64)
                {
                    do 
                    {
                        if(pos.board[d3]==pp*-1) 
                        {
                            ai.add(d3);
                            f=true;
                            d3+=7;
                        }
                        else if(pos.board[d3]==pp)
                        {
                            if(f)
                            {
                                for(int j=0;j<ai.size();j++) 
                                pos2.board[ai.get(j)]=pp;
                            }
                            break;
                        }
                            else break;
                    }while(d3%8!=0 && d3<64);
                }
                f=false;
                ai.clear();
                 if((m.moveIndex+1)%8!=0 && d2>=0)
                 {
                    do 
                    {
                     if(pos.board[d2]==pp*-1) 
                        {
                            ai.add(d2);
                            f=true;
                            d2-=7;
                        } 
                     else if(pos.board[d2]==pp)
                        {
                            if(f)
                            {
                                for(int j=0;j<ai.size();j++) 
                                pos2.board[ai.get(j)]=pp;
                            }
                            break;
                        }
                        else break;
                    }while((d2+1)%8!=0 && d2 >= 0);
                }
                f=false;
                ai.clear();
                while(d1<64)
                {
                 if(pos.board[d1]==pp*-1) 
                    {
                      ai.add(d1);
                        d1+=9;
                        f=true;
                    } 
                 else if(pos.board[d1]==pp)
                    {
                        if(f)
                        {
                            for(int j=0;j<ai.size();j++) 
                            pos2.board[ai.get(j)]=pp;
                        }
                        break;
                    }
                    else break;
                }
                //Enregistrer la position sur la table trace pour le cas d'annulation des coups
                trace.add(pos2);
        return pos2;
        }
        return pos;
    }
    /* Fonction pour annuler les coups 
       retourne la derniere position
    */
    public Position annulation()
    {
        OthelloPosition pos = new OthelloPosition();
        OthelloPosition pos1 = new OthelloPosition();
        //recuperer la derniere position faite par le joueur
        int lasttrace = this.trace.size() - 2; // -2 car la derniere est la position faite par le programme
        if(lasttrace >=0)
        {
            pos = (OthelloPosition)this.trace.get(lasttrace);
            //supprimer la position annulee de la table trace
            trace.remove(this.trace.size()-1);
            return pos;
        }
        return null;
    }
    //Fonction pour calculer le nombre des pions pour chaque player
    public void nbreh(Position p)
    {
        OthelloPosition pos = (OthelloPosition)p;
        nbrh=0;
        nbrp=0;
        for(int i=0;i<64;i++)
        {
            if(pos.board[i]==1) nbrh++;
            else if(pos.board[i]==-1) nbrp++;
        }
    }
    public boolean reachedMaxDepth(Position p, int depth) {
        boolean ret = false;
        if(depth>=niveau) ret = true;
            //si le programme a gagné 
        else if (wonPosition(p, false)) ret = true;
        //si l'humain a gagné
        else if (wonPosition(p, true))  ret = true;
         //si toutes les cases sont remplis 
        if (GameSearch.DEBUG) {
            System.out.println("reachedMaxDepth: pos=" + p.toString() + ", depth="+depth
                               +", ret=" + ret);
        }
        return ret;
    }
    public Move createMove(boolean player) {
        if(player) {
            actualplayer=player;
            b.setai(pm);
            b.picked=-1;
        }
        else {
            b.setai(pm2);
            b.picked=-1;
        }
        this.b.repaint(0, 0, 500, 600);
        if(!mode)
            {
               if(player) head.i=2;
               else head.i=1;
            }
        this.head.repaint(0,0, 600, 200);
        head.askingforhelp=false;
        f=false;
        if (GameSearch.DEBUG) System.out.println("Enter blank square index [0,63]:");
        ts=0;
        ActionListener taskPerformer2 = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
               if(mode)
                 {
                    if(ts%10==0) 
                    {
                        head.jb4.setText("0"+String.valueOf(10-(ts/10)));
                        head.paintImmediately(head.jb4.getBounds());
                    }
                     if(ts==100)
                    {
                        if (player && !pm.isEmpty() )b.picked=pm.get(new Random().nextInt(pm.size())); 
                        ((Timer)evt.getSource()).stop();
                    }
                    else if(b.picked!=-1 && pm.contains(b.picked)  || b.picked!=-1 && pm2.contains(b.picked) ) {
                            ((Timer)evt.getSource()).stop();  
                        }
                      else if(head.askingforhelp)
                                     {
                                     f=true;
                                     ((Timer)evt.getSource()).stop();
                                     }
                     ts++;
               }
                    
                     else
               {
                if(b.picked!=-1 && pm.contains(b.picked) || b.picked!=-1 && pm2.contains(b.picked))
                    ((Timer)evt.getSource()).stop();
                else if(head.askingforhelp)
                                     {
                                     f=true;
                                     ((Timer)evt.getSource()).stop();
                                     }
               }
                }
            };
		    
            timer1=new Timer(100,taskPerformer2);
            timer1.start();
    
        while(timer1.isRunning())
            {
            }
        if(f) return null;//Si l'utilisateur a demandé l'assistance
            OthelloMove mm = new OthelloMove();
            mm.moveIndex = b.picked;
            return mm;
    }
}

