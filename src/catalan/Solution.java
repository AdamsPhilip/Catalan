package catalan;

import java.util.ArrayList;

public class Solution implements Comparable<Solution> {
    private ArrayList<Move> solution;

    public Solution(ArrayList<Move> solution) {
        this.solution = solution;
    }

    private int getSize(){
        return this.solution.size();
    }

    // gibt die ID des selectetVertex eines Moves zurück
    private int getIDFromMove(int i){
        return this.solution.get(i).getSelectedVertex().getID();
    }

    public ArrayList<Move> getMove(){
        return this.solution;
    }

   public int compareTo(Solution o) {
       for (int i = 1; i < o.getSize(); i++) {
           if (this.getIDFromMove(i) == o.getIDFromMove(i) ) {
               continue;
           }
           if (this.getIDFromMove(i) < o.getIDFromMove(i) ) {
               return -1;
           }
           if (this.getIDFromMove(i) > o.getIDFromMove(i) ) {
               return 1;
           }
       }
       return 0;
   }
}
