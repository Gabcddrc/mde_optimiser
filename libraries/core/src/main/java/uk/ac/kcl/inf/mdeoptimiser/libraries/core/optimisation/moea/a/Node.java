package uk.ac.kcl.inf.mdeoptimiser.libraries.core.optimisation.moea.a;
import org.moeaframework.core.Solution;
import org.sidiff.common.emf.access.path.axis.Parent;

public class Node {

    protected Solution solution;
    protected Node left = null;
    protected Node right = null;
    protected Node parent = null;                 
    protected int visited = 0;
    protected double gameValue = 0;
    protected int childrenVisited = 0;


    Node(Solution solution) {
        this.solution = solution;
    }

    public Node() {
    }

    public Node(Solution solution, Node left, Node right, Node parent) {
        this.solution = solution;
        this.left = left;
        this.right = right;
        this.parent = parent;
    }

    public Solution getSolution() {
        return this.solution;
    }

    public void setSolution(Solution solution) {
        this.solution = solution;
    }

    public void visited(){
        visited++;
    }

    public int getChildrenVisited(){
        return childrenVisited;
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

    public Node getLeft() {
        return this.left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return this.right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public Node getParent() {
        return this.parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }
}
