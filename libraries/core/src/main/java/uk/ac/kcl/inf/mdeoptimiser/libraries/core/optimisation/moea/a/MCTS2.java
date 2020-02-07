package uk.ac.kcl.inf.mdeoptimiser.libraries.core.optimisation.moea.a;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.*;

import org.moeaframework.core.EpsilonBoxDominanceArchive;
import org.moeaframework.core.Selection;
import org.moeaframework.algorithm.AbstractAlgorithm;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Problem;
import org.moeaframework.core.NondominatedSortingPopulation;
import org.moeaframework.core.Population;
import org.moeaframework.core.Solution;
import org.moeaframework.core.Variation;
import org.moeaframework.core.comparator.ChainedComparator;
import org.moeaframework.core.comparator.CrowdingComparator;
import org.moeaframework.core.comparator.DominanceComparator;
import org.moeaframework.core.comparator.ParetoDominanceComparator;
import org.moeaframework.core.operator.TournamentSelection;

import uk.ac.kcl.inf.mdeoptimiser.libraries.core.optimisation.IGuidanceFunction;
import uk.ac.kcl.inf.mdeoptimiser.libraries.core.optimisation.moea.problem.MoeaOptimisationProblem;

public class MCTS2 extends AbstractAlgorithm {

    private MoeaOptimisationProblem moeaProblem;
    private final Variation variation;
    private final TournamentSelection s;

    private Node root;
    private Node best;
    private Node choice;

    public MCTS2(Problem problem, Variation variation, Selection selection) {
        super(problem);
        this.variation = variation;
        this.moeaProblem = (MoeaOptimisationProblem) problem;
        this.s = (TournamentSelection) selection;
        root = new Node(moeaProblem.newSolution(), null, null , null);
        choice = root;
        best = root;
    }

    @Override
    public void iterate() {
        System.out.println("start MCTS");

        Solution[] next = new Solution[variation.getArity()];
        for (int i = 0; i < next.length; i++) {
            next[i] = choice.getSolution();
        }
        Node left = new Node(expand(next)[0], null, null, root);
        Node right = new Node(expand(next)[0], null, null, root);
        left.setGameValue(heuristicEstimate(left.getSolution()));
        right.setGameValue(heuristicEstimate(right.getSolution()));
        choice.setLeft(left);
        choice.setRight(right);

        // select the Node/solution to expand
        selection();

        backpropagation();
        // moeaProblem.evaluate(choice.getSolution());
        System.out.println(choice.getSolution().getObjectives()[0]);
        // solutionConverter.convert(choice.getSolution());
        if (compareDomin(choice.getSolution(), best.getSolution()) == -1) {
            best = choice;
        }
    }

    // -1 solution 1 dominates 2
    public int compareDomin(Solution solution1, Solution solution2) {
        return s.getComparator().compare(solution1, solution2);
    }

    // estimate heuristic use fitnesses
    public double heuristicEstimate(Solution solution){
		System.out.println(Arrays.stream(solution.getObjectives()).sum());
		return Arrays.stream(solution.getObjectives()).sum();
	}

    public double selectionValue(Node node) {
        return node.setGameValue(node.gameValue
                + 0.5 * Math.sqrt((Math.log((double) node.getVisited())) / (double) node.getChildrenVisited()));
    }

    // Create new Solutions based on parent
    public Solution[] expand(Solution[] parent) {
        Solution[] solutions = new Solution[variation.getArity()];
        for (int i = 0; i < 50; i++) {
            solutions = variation.evolve(parent);
            evaluateAll(solutions);
            if (compareDomin(solutions[0], root.getSolution()) != 1) {
                return solutions;
            }
        }
        return solutions;
    }

    // Update childrenVisted count
    public void backpropagation() {
        Node back = choice;
        while (!back.getParent().equals(root)) {
            Node temp = back;
            back = back.getParent();
            back.setChildrenVisited(temp.getChildrenVisited() + temp.getVisited());
            // back.setGameValue((back.getGameValue()+temp.getGameValue())/2);
            back.setGameValue((back.getLeft().getGameValue() + back.getRight().getGameValue()) / 2);
        }
    }

    public void selection() {
        Node select = root;
        while (select.getRight() != null && select.getLeft() != null) {

            Node left = select.getLeft();
            Node right = select.getRight();
            if (selectionValue(left) < selectionValue(right)) {
                select = right;
            } else {
                select = left;
            }
            select.visited();
        }

        choice = select;
    }

    @Override
    public NondominatedPopulation getResult() {
        // TODO Auto-generated method stub
        NondominatedPopulation population = new NondominatedPopulation();
        population.add(best.getSolution());
        return population;
    }

	public EpsilonBoxDominanceArchive getArchive() {
		return null;
	}


	public NondominatedSortingPopulation getPopulation() {
        NondominatedSortingPopulation population = new NondominatedSortingPopulation();
        population.add(best.getSolution());
		return population;
	}

}