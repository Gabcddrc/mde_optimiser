/* Copyright 2009-2016 David Hadka
 *
 * This file is part of the MOEA Framework.
 *
 * The MOEA Framework is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * The MOEA Framework is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with the MOEA Framework.  If not, see <http://www.gnu.org/licenses/>.
 */
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
import org.moeaframework.core.PRNG;
import org.moeaframework.core.Population;
import org.moeaframework.core.Problem;
import org.moeaframework.core.Selection;
import org.moeaframework.core.Solution;
import org.moeaframework.core.Variation;
import org.moeaframework.core.comparator.ChainedComparator;
import org.moeaframework.core.comparator.CrowdingComparator;
import org.moeaframework.core.comparator.DominanceComparator;
import org.moeaframework.core.comparator.ParetoDominanceComparator;
import org.moeaframework.core.operator.TournamentSelection;

import uk.ac.kcl.inf.mdeoptimiser.libraries.core.optimisation.IGuidanceFunction;
import uk.ac.kcl.inf.mdeoptimiser.libraries.core.optimisation.moea.problem.MoeaOptimisationProblem;
import org.eclipse.emf.ecore.EObject;
import java.lang.Math; 

/**
 * Implementation of NSGA-II, with the ability to attach an optional 
 * &epsilon;-dominance archive.
 * <p>
 * References:
 * <ol>
 *   <li>Deb, K. et al.  "A Fast Elitist Multi-Objective Genetic Algorithm:
 *       NSGA-II."  IEEE Transactions on Evolutionary Computation, 6:182-197, 
 *       2000.
 *   <li>Kollat, J. B., and Reed, P. M.  "Comparison of Multi-Objective 
 *       Evolutionary Algorithms for Long-Term Monitoring Design."  Advances in
 *       Water Resources, 29(6):792-807, 2006.
 * </ol>
 */
public class MCTS extends AbstractEvolutionaryAlgorithm implements
		EpsilonBoxEvolutionaryAlgorithm {

	/**
	 * The selection operator.  If {@code null}, this algorithm uses binary
	 * tournament selection without replacement, replicating the behavior of the
	 * original NSGA-II implementation.
	 */
	private final Selection selection;

	// private MoeaOptimisationProblem moeaProblem;
	// private List<IGuidanceFunction> fitnessFunctions;
	/**
	 * The variation operator.
	 */
	private final Variation variation;

	// private int counter; 


	private final TournamentSelection s;

	private Node root;
	private Node best;
	private Node choice;
	/**
	 * Constructs the NSGA-II algorithm with the specified components.
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
		// this.moeaProblem = (MoeaOptimisationProblem) problem;
		// this.fitnessFunctions = moeaProblem.getConstraintFunctions();
		this.s = (TournamentSelection) selection;
		this.selection = selection;
		this.variation = variation;
		root = new Node();

	}

	@Override
	public void iterate() {
		System.out.println("start MCTS");
		NondominatedSortingPopulation population = getPopulation();

		if (root.solution == null) {
			initialization();
		}
		else{
			expansionMain();
		}

		//select the Node/solution to expand
		selection();

		backpropagation();

		if(compareDomin(choice.getSolution(), best.getSolution()) == -1){
			best = choice;
		}
		population.clear();
		population.add(best.getSolution());


		// //nrp output
		// population.add(choice.getSolution());
		// population.truncate(35);

	}

	public void initialization(){
			root.setSolution(population.get(0));
			Solution[] next = new Solution[variation.getArity()];
			for (int i = 0; i < next.length; i++) {
				next[i] = root.getSolution();
			}

			Node left = new Node(expand(next)[0], null, null, root);
			Node right = new Node(expand(next)[0], null, null, root);
			left.setGameValue(heuristicEstimate(left.getSolution()));
			right.setGameValue(heuristicEstimate(right.getSolution()));
			// Node d = chooseDominateNode(left, right);
			// if(d != null){
			// 	root.setSolution(d.getSolution());
			// }
			// else{
			root.setLeft(left);
			root.setRight(right);
			// }
		
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
				 - 0.5 * Arrays.stream(solution.getConstraints()).sum()
				 ;
	}

	//Selection Strategy
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


		// //Selection Strategy 2
		// if(node.getVisited() == 0){
		// 	return Double.POSITIVE_INFINITY;
		// }
		// else if(node.getChildrenVisited() == 0){
		// 	return Double.POSITIVE_INFINITY;
		// }
		// else{
		// return node.getGameValue() + 0.5*Math.sqrt( (Math.log((double) node.getVisited())) / (double) node.getChildrenVisited() )
		// //  + (node.getGameValue() - node.getParent().getGameValue())
		// ;
		
		// }
	}


	// Create new Solutions based on parent
	public Solution[] expand(Solution[] parent) {

		// //expansion Strategy 1
		// Solution[] solutions = new Solution[variation.getArity()];
			
		// 	solutions = variation.evolve(parent);
		// 	evaluateAll(solutions);
		// return solutions;

		// for (int i = 0; i < 50; i++) {
		// 	solutions = variation.evolve(parent);
		// 	evaluateAll(solutions);
		// 	if(compareHeuristic(solutions[0], root.getSolution())){
		// 		return solutions;
		// 	}
		// }
		// return solutions;


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

	// Update childrenVisted count
	public void backpropagation(){
		Node back = choice;
		
		while(back != root && back.getParent() != root){
			back = back.getParent();

			back.setChildrenVisited(back.getLeft().getChildrenVisited()+back.getLeft().getVisited() + back.getRight().getChildrenVisited()+back.getRight().getVisited());

			// back.setChildrenGameValuesq(back.getLeft().getGameValue()*back.getLeft().getGameValue() + back.getLeft().getChildrenGameValueSq() +  back.getRight().getGameValue()*back.getRight().getGameValue() + back.getRight().getChildrenGameValueSq());	
	

			back.setGameValue((back.getLeft().getGameValue()+back.getRight().getGameValue())/ 2.0 );
	
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
