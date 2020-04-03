package uk.ac.kcl.inf.mdeoptimiser.libraries.core.optimisation.moea.PSJ;

import org.moeaframework.core.Solution;
import org.sidiff.common.emf.access.path.axis.Parent;
import uk.ac.kcl.inf.mdeoptimiser.libraries.core.optimisation.moea.problem.MoeaOptimisationSolution;
import java.util.ArrayList;

public class UnrestrictedNode {

    protected MoeaOptimisationSolution solution = null;
    protected UnrestrictedNode parent = null;                 
    protected int visited = 0;
    protected double gameValue = 0; // heuristic
    protected int childrenVisited = 0;
    public UnrestrictedNode[] children = null;

    UnrestrictedNode(MoeaOptimisationSolution solution) {
        this.solution = solution;
    }

    public UnrestrictedNode() {
    }


    public void setChildren(UnrestrictedNode[] children){
        this.children = children;
    }

    public UnrestrictedNode[] getChildren(){
        return this.children;
    }

    public MoeaOptimisationSolution getSolution() {
        return this.solution;
    }

    public void setSolution(MoeaOptimisationSolution solution) {
        this.solution = solution;
    }

    public void visited(){
        visited++;
    }

    public int getChildrenVisited(){
        return childrenVisited;
    }
    public void setChildrenVisited(int c){
        childrenVisited = c;
    }
    public double setGameValue(double g){
        gameValue = g;
        return gameValue;
    }

    public double getGameValue(){
        return gameValue;
    }

    public int getVisited(){
        return visited;
    }

    public UnrestrictedNode getParent() {
        return this.parent;
    }

    public void setParent(UnrestrictedNode parent) {
        this.parent = parent;
    }
}
