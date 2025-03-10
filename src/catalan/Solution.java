package catalan;

import java.util.ArrayList;

public class Solution implements Comparable<Solution> {
    private ArrayList<Move> solution; //alle Moves eines Lösungsweges

    public Solution(ArrayList<Move> solution) {
        this.solution = solution;
    }

    private int getSize(){
        return this.solution.size();
    }

    // gibt die ID des selectedVertex eines Moves zurück
    private int getIDFromMove(int i){
        return this.solution.get(i).getSelectedVertex().getID();
    }

    public ArrayList<Move> getMoves(){
        return this.solution;
    }

    //Vergleicht zwei Solution-Objekte miteinander
    @Override
   public int compareTo(Solution o) {
       for (int i = 1; i < o.getSize(); i++) {
           if (!(this.getIDFromMove(i) == o.getIDFromMove(i))) { //--> True an der ersten Stelle, wo sich die Listen unterscheiden
               return o.getIDFromMove(i) - this.getIDFromMove(i);
           }
       }
       return 0;
   }
}
