package othellogame;
import java.util.*;

public abstract class GameSearch {

    public static final boolean DEBUG = false;

    /*
     * Note: the abstract Position also needs to be
     *       subclassed to write a new game program.
     */
    /*
     * Note: the abstract class Move also needs to be subclassed.
     *       
     */

    public static boolean PROGRAM = false;
    public static boolean HUMAN = true;
    public static boolean HUMAN2 = false;
    private Position np=null;
    Header head;
    public void setNP (Position np){ this.np = np;
            System.out.print("\n setNP entred \n");
}
  

    /**
     *  Notes:  PROGRAM false -1,  HUMAN true 1
     */

    /*
     * Abstract methods:
     */

    public abstract boolean drawnPosition(Position p);
    public abstract boolean wonPosition(Position p, boolean player);
    public abstract float positionEvaluation(Position p, boolean player);
    public abstract void printPosition(Position p);
    public abstract Position [] possibleMoves(Position p, boolean player);
    public abstract Position makeMove(Position p, boolean player, Move move);
    public abstract boolean reachedMaxDepth(Position p, int depth);
    public abstract Move createMove(boolean player);
    public abstract void addtotrace(Position p);
    /*
     * Search utility methods:
     */
//alpha et beat sont initialisé par la pire valeur pour 
    protected Vector alphaBeta(int depth, Position p, boolean player) {
        Vector v = alphaBetaHelper(depth, p, player, 1000000.0f, -1000000.0f);
        //System.out.println("^^ v(0): " + v.elementAt(0) + ", v(1): " + v.elementAt(1));
        return v;
    }

    protected Vector alphaBetaHelper(int depth, Position p,boolean player, float alpha, float beta) {
        if (GameSearch.DEBUG) System.out.println("alphaBetaHelper("+depth+","+p+","+alpha+","+beta+")");
        //dead end soit un des jours a gangé ou le board est totalement remplis 
        if (reachedMaxDepth(p, depth)) {
            Vector v = new Vector(2);
            float value = positionEvaluation(p, player);
            v.addElement(new Float(value));
            v.addElement(null);
            if(GameSearch.DEBUG) {
                System.out.println(" alphaBetaHelper: mx depth at " + depth+", value="+value);
            }
            return v;
        }
        Vector best = new Vector();
        Position [] moves = possibleMoves(p, player);
        if(moves==null) {
            System.out.println("nulllll");
            return null;
        }
        for (int i=0; i<moves.length; i++) {
            //on va apeller la fonction reccursivement jusqu'a un dead end pour qu'on puisse recuperer evamuer les poitions 
            Vector v2 = alphaBetaHelper(depth + 1, moves[i], !player, -beta, -alpha);
            if (v2 == null || v2.size() < 1) continue;
            float value = -((Float)v2.elementAt(0)).floatValue();
            if (value > beta) {
                if(GameSearch.DEBUG) System.out.println(" ! ! ! value="+value+", beta="+beta);
                beta = value;
                best = new Vector();
                best.addElement(moves[i]);
                Enumeration enum2 = v2.elements();
                enum2.nextElement(); // skip previous value
                while (enum2.hasMoreElements()) {
                    Object o = enum2.nextElement();
                 if (o != null) best.addElement(o);
                }
            }
            /**
             * Use the alpha-beta cutoff test to abort search if we
             * found a move that proves that the previous move in the
             * move chain was dubious
             */
            if (beta >= alpha) {
                break;
            }
        }
        Vector v3 = new Vector();
        v3.addElement(new Float(beta));
        Enumeration enum2 = best.elements();
        while (enum2.hasMoreElements()) {
            v3.addElement(enum2.nextElement());
        }
        return v3;
    }
    public void playGame(Position startingPosition, boolean humanPlayFirst) {

        if (humanPlayFirst == false) {
            Vector v = alphaBeta(0, startingPosition, PROGRAM);
            startingPosition = (Position)v.elementAt(1);
        }
        while (true) {
            try{
            printPosition(startingPosition);
            }catch(NullPointerException e){}
            if (wonPosition(startingPosition, PROGRAM)) {
                System.out.println("Program won");
                head.wwon.setVisible(true);
                break;
            }
            if (wonPosition(startingPosition, HUMAN)) {
                System.out.println("Human won");
                head.bwon.setVisible(true);
                break;
            }
            if (drawnPosition(startingPosition)) {
                System.out.println("Drawn game");
                break;
            }
         possibleMoves(startingPosition,HUMAN);
            Move move = createMove(HUMAN);
            try{
            if(move==null) 
            {
             Vector v = alphaBeta(0, startingPosition, HUMAN);
            
            Enumeration enum2 = v.elements();
            startingPosition = (Position)v.elementAt(1);
              addtotrace(startingPosition);
            printPosition(startingPosition);
            }
            else {
            startingPosition = makeMove(startingPosition, HUMAN, move);
            printPosition(startingPosition);
            }
            }catch(java.lang.NullPointerException e)
                {
                System.out.println("Error1");
                }
            try {
                 Vector v = alphaBeta(0, startingPosition, PROGRAM);
            Enumeration enum2 = v.elements();   
            startingPosition = (Position)v.elementAt(1); 
            }
            catch(java.lang.NullPointerException e) { System.out.println("Error3");}
            catch(java.lang.ArrayIndexOutOfBoundsException ex) { System.out.println("Error4");}
        }
    }
       public void playGamewithAHuman(Position startingPosition, boolean playfirst) {
        while (true) {
           
            printPosition(startingPosition);
            possibleMoves(startingPosition,HUMAN);
            Move move = createMove(HUMAN);
            try{
             if(move==null) 
            {
             Vector v = alphaBeta(0, startingPosition, HUMAN);
            Enumeration enum2 = v.elements();
            startingPosition = (Position)v.elementAt(1);  
             printPosition(startingPosition);
              addtotrace(startingPosition);
            } else {
            startingPosition = makeMove(startingPosition, HUMAN, move);
            printPosition(startingPosition);
             }
            }catch(java.lang.NullPointerException e)
                {}
                catch(java.lang.ArrayIndexOutOfBoundsException ec)
                {}
            if (wonPosition(startingPosition, HUMAN2 )) {
                System.out.println("Human2 won");
                head.wwon.setVisible(true);
                break;
            }
            if (wonPosition(startingPosition, HUMAN )) {
                System.out.println("Human1 won");
                head.bwon.setVisible(true);
                break;
            }
            if (drawnPosition(startingPosition)) {
                System.out.println("Drawn game");
                break;
            }     
           possibleMoves(startingPosition,HUMAN2);
            Move move2 = createMove(HUMAN2);
            try{
                 if(move==null) 
            {
             Vector v = alphaBeta(0, startingPosition, HUMAN2);
            
            Enumeration enum2 = v.elements();
            startingPosition = (Position)v.elementAt(1);  
             addtotrace(startingPosition);
             printPosition(startingPosition);
            }
             else {
            startingPosition = makeMove(startingPosition, HUMAN2, move2);
            printPosition(startingPosition);  
            }
            }catch(java.lang.NullPointerException e)
                {}
                catch(java.lang.ArrayIndexOutOfBoundsException ec)
                {}
           
            if (wonPosition(startingPosition, HUMAN2 )) {
                System.out.println("Human2 won");
                head.wwon.setVisible(true);
                break;
            }
            if (wonPosition(startingPosition, HUMAN )) {
                System.out.println("Human1 won");
                head.bwon.setVisible(true);
                break;
            }
            if (drawnPosition(startingPosition)) {
                System.out.println("Drawn game");
                break;
            }  
            
           
        }
    }
}