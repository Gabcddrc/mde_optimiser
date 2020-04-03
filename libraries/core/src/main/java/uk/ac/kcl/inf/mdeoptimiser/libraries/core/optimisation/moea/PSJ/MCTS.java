
package uk.ac.kcl.inf.mdeoptimiser.libraries.core.optimisation.moea.PSJ;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.*;


import org.moeaframework.algorithm.AbstractEvolutionaryAlgorithm;
import org.moeaframework.core.EpsilonBoxDominanceArchive;
import org.moeaframework.core.EpsilonBoxEvolutionaryAlgorithm;
import org.moeaframework.core.Initialization;
import org.moeaframework.core.NondominatedSortingPopulation;
import org.moeaframework.core.Population;
import org.moeaframework.core.Problem;
import org.moeaframework.core.Selection;
import org.moeaframework.core.Solution;
import org.moeaframework.core.Variation;

import org.moeaframework.core.operator.TournamentSelection;

import uk.ac.kcl.inf.mdeoptimiser.libraries.core.optimisation.moea.problem.MoeaOptimisationProblem;
import org.eclipse.emf.ecore.EObject;
import java.lang.Math; 



public class MCTS extends AbstractEvolutionaryAlgorithm implements
		EpsilonBoxEvolutionaryAlgorithm {


	private final Selection selection;


	/**
	 * The variation operator.
	 */
	private final Variation variation;

	// private int counter; 


	private final TournamentSelection s;

	private Node root;
	private Node best;
	private Node choice;

	MoeaOptimisationProblem moeaProblem;

	/**
	 * Parameters needed for MDEO to accept MCTS without adapter, however not necessary
	 * 
	 * @param problem the problem being solved
	 * @param population the population used to store solutions
	 * @param archive the archive used to store the result; can be {@code null}
	 * @param selection the selection operator
	 * @param variation the variation operator
	 * @param initialization the initialization method
	 */
	public MCTS(Problem problem, NondominatedSortingPopulation population,
			EpsilonBoxDominanceArchive archive, Selection selection,
			Variation variation, Initialization initialization) {
		super(problem, population, archive, initialization);
		this.moeaProblem = (MoeaOptimisationProblem) problem;
		this.s = (TournamentSelection) selection;
		this.selection = selection;
		this.variation = variation;
		root = new Node();

	}

	@Override
	public void iterate() {

		NondominatedSortingPopulation population = getPopulation();

		if (root.solution == null) {
			initialization();
		}

	
		//select the Node/solution to expand
		selection();

		expansionMain();

		backpropagation();

		//determine if the selected solution is the best
		if(compareDomin(choice.getSolution(), best.getSolution()) == -1){
			best = choice;
		}

		population.clear();
		population.add(best.getSolution());


		// //nrp output
		// population.add(choice.getSolution());
		// population.truncate(population.size());

	}

	public void initialization(){
			root.setSolution( moeaProblem.initialModelasSolution());
			best = root;
	}

	//Expand selected Node
	public void expansionMain(){
			Solution[] next = new Solution[variation.getArity()];
			for (int i = 0; i < next.length; i++) {
				next[i] = choice.getSolution();
			}
			Node left = new Node(expand(next)[0], null, null, choice);
			Node right = new Node(expand(next)[0], null, null, choice);
			left.setGameValue(heuristicEstimate(left.getSolution()));
			right.setGameValue(heuristicEstimate(right.getSolution()));

			choice.setLeft(left);
			choice.setRight(right);
		
	}	

	//-1 solution 1 dominates 2
	public int compareDomin(Solution solution1, Solution solution2){
		return s.getComparator().compare(solution1, solution2);
	}

	
	public boolean compareHeuristic(Solution solution1, Solution solution2){
		return heuristicEstimate(solution1) > heuristicEstimate(solution2);
	}

	// estimate heuristic use fitnesses and unsatisfied constraints
	public double heuristicEstimate(Solution solution){
		evaluate(solution);
		return ( -1.0*Arrays.stream(solution.getObjectives()).sum() )
				 - 0.5 *Arrays.stream(solution.getConstraints()).sum()
				 ;
	}

	//Selection Strategy2
	public double selectionValue(Node node){

		//Selection Strategy 2
		if(node.getVisited() == 0){
			return Double.POSITIVE_INFINITY;
		}
		else if(node.getChildrenVisited() == 0){
			return Double.POSITIVE_INFINITY;
		}
		else{
		return node.getGameValue() + 0.2*Math.sqrt( (Math.log((double) node.getVisited())) / (double) node.getChildrenVisited() )
		;
		
		}
	}

	public double selectionValue1(Node node){

	//Selection Strategy 1
	if(node.getVisited() == 0){
		return Double.POSITIVE_INFINITY;
	}
	else{
	Node parent = node.getParent();
	return node.getGameValue()
			+ 0.1*Math.sqrt( (Math.log((double) parent.getVisited())  ) / (double) node.getVisited());

	}

	}


	// Create new Solutions based on parent
	public Solution[] expand(Solution[] parent) {

		//expansion Strategy 1
		// Solution[] solutions = new Solution[variation.getArity()];
			
		// 	solutions = variation.evolve(parent);
		// 	evaluateAll(solutions);
		// return solutions;


		//expansion Strategy 2
		Solution[] equal = null;
		Solution[] solutions = new Solution[variation.getArity()];
		for (int i = 0; i < 50; i++) {
			solutions = variation.evolve(parent);
			evaluateAll(solutions);
			if(compareDomin(solutions[0], parent[0]) == -1){
				return solutions;
			}
			else if(compareDomin(solutions[0], parent[0]) == 0){
				equal = solutions;
			} 
		}

		if(equal != null)
			{return equal;}
		else{
			return variation.evolve(parent);
			}	
	}

	// Update childrenVisted count/heuristics
	public void backpropagation(){
		Node back = choice;
		
		while(back != root && back.getParent() != root){
			back = back.getParent();

			back.setChildrenVisited(back.getLeft().getChildrenVisited()+back.getLeft().getVisited() + back.getRight().getChildrenVisited()+back.getRight().getVisited());
	
			//new heuristics update
			double difference = ((back.getLeft().getGameValue()+back.getRight().getGameValue())/ 2.0) - back.getGameValue();
			back.setGameValue(back.getGameValue() + difference);

			//old heuristic update
			// back.setGameValue((back.getLeft().getGameValue()+back.getRight().getGameValue())/ 2.0 );
	
		}
	}

	//select the next node to be expended
	public void selection() {
		Node select = root;
		select.visited();
		while (select.getRight() != null && select.getLeft() != null)  {

			Node left =  select.getLeft();
			Node right = select.getRight();
		
			// System.out.println(selectionValue(left));
			if(selectionValue(left)<selectionValue(right)){
				select = right;
			}
			else{
				select = left;
			}
			select.visited();
		}

		choice = select;
	}



	@Override
	public EpsilonBoxDominanceArchive getArchive() {
		return (EpsilonBoxDominanceArchive)super.getArchive();
	}



	@Override
	public NondominatedSortingPopulation getPopulation() {
		return (NondominatedSortingPopulation)super.getPopulation();
	}

}
