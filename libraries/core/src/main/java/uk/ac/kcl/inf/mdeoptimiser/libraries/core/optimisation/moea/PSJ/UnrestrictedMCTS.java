package uk.ac.kcl.inf.mdeoptimiser.libraries.core.optimisation.moea.PSJ;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.*;
import java.util.Iterator;

import uk.ac.kcl.inf.mdeoptimiser.libraries.core.optimisation.executor.SolutionGenerator;

//required for implementing mutation strategy
import uk.ac.kcl.inf.mdeoptimiser.libraries.core.optimisation.interpreter.henshin.HenshinExecutor;
import uk.ac.kcl.inf.mdeoptimiser.libraries.core.optimisation.operators.adaptation.MutationStepSizeStrategy;
import uk.ac.kcl.inf.mdeoptimiser.libraries.core.optimisation.operators.mutation.selection.RandomOperatorSelector;

import org.moeaframework.algorithm.AbstractEvolutionaryAlgorithm;
import org.moeaframework.core.EpsilonBoxDominanceArchive;
import org.moeaframework.core.EpsilonBoxEvolutionaryAlgorithm;
import org.moeaframework.core.Initialization;
import org.moeaframework.core.NondominatedSortingPopulation;
// import org.moeaframework.core.PRNG;
import org.moeaframework.core.Population;
import org.moeaframework.core.Problem;
import org.moeaframework.core.Selection;
import org.moeaframework.core.Solution;
import org.moeaframework.core.Variation;

import org.moeaframework.core.operator.TournamentSelection;

import uk.ac.kcl.inf.mdeoptimiser.libraries.core.optimisation.moea.problem.*;
import java.lang.Math; 



public class UnrestrictedMCTS extends AbstractEvolutionaryAlgorithm implements
		EpsilonBoxEvolutionaryAlgorithm {


	private final Selection selection;

	private AllMatchingOneStepMutationStrategy mutation;

	/**
	 * The variation operator.
	 */
	private final Variation variation;

	// private int counter; 


	private final TournamentSelection s;

	private UnrestrictedNode root;
	private UnrestrictedNode best;
	private UnrestrictedNode choice;


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

	public UnrestrictedMCTS(Problem problem, NondominatedSortingPopulation population,
			EpsilonBoxDominanceArchive archive, Selection selection,
			Variation variation, Initialization initialization) {
		super(problem, population, archive, initialization);

		this.moeaProblem = (MoeaOptimisationProblem) problem;

		this.mutation = new AllMatchingOneStepMutationStrategy(moeaProblem.getSolutionGenerator().getHenshinExcutor(),
																new RandomOperatorSelector(moeaProblem.getSolutionGenerator().getHenshinExcutor()));

		this.s = (TournamentSelection) selection;

		this.selection = selection;

		this.variation = variation;

		root = new UnrestrictedNode();

	}

	@Override
	public void iterate() {

		NondominatedSortingPopulation population = getPopulation();

		if (root.getSolution() == null) {

			initialization();
		}

		//select the Node/solution to expand
		selection();


		expansionMain();
	

		backpropagation();

		//determine if the selected solution is the best
		if(compareDomin((Solution) choice.getSolution(), (Solution) best.getSolution()) == -1){
			best = choice;
		}
		// if( heuristicEstimate((Solution) choice.getSolution()) > heuristicEstimate((Solution) best.getSolution())  ){
		// 	best = choice;
		// }
		population.clear();

		population.add( (Solution) best.getSolution());


		// //nrp output
		// population.add(choice.getSolution());
		// population.truncate(population.size());

	}

	public void initialization(){
			
			root.setSolution( moeaProblem.initialModelasSolution());
			root.setGameValue(heuristicEstimate((Solution) root.getSolution()));
			choice = root;
			best = root;
	}

	//Expand selected Node
	public void expansionMain(){

			choice.setChildren( expand(choice) );
				
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
				 - Arrays.stream(solution.getConstraints()).sum()
				 ;
	}

	//Selection Strategy2
	public double selectionValue2(UnrestrictedNode node){

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

	public double selectionValue(UnrestrictedNode node){

	//Selection Strategy 1
	if(node.getVisited() == 0){
		return Double.POSITIVE_INFINITY;
	}
	else{
	UnrestrictedNode parent = node.getParent();
	return node.getGameValue()
			+ 0.1*Math.sqrt( (Math.log((double) parent.getVisited())  ) / (double) node.getVisited());

	}

	}


	public UnrestrictedNode[] expand(UnrestrictedNode parent){

		MoeaOptimisationSolution parentSolution = parent.getSolution();
 		ArrayList<uk.ac.kcl.inf.mdeoptimiser.libraries.core.optimisation.interpreter.guidance.Solution>	newModels = mutation.mutate(parentSolution.getModel());

		UnrestrictedNode children[] = new UnrestrictedNode[newModels.size()];
	
		for(int i = 0; i < newModels.size(); i++) {
	
			MoeaOptimisationSolution copy = parentSolution.copy();
			copy.setModel(newModels.get(i));
			UnrestrictedNode child = new UnrestrictedNode();
			child.setSolution(copy);
			child.setParent(parent);
			child.setGameValue(heuristicEstimate((Solution) copy ));
			children[i] = child;
		}

		return children;
	}

	// Update childrenVisted count
	public void backpropagation(){
		UnrestrictedNode back = this.choice;
		
		while(back != root && back.getParent() != root){
			UnrestrictedNode[] children = back.getChildren();

			// back.setChildrenVisited(back.getLeft().getChildrenVisited()+back.getLeft().getVisited() + back.getRight().getChildrenVisited()+back.getRight().getVisited());
			back.setChildrenVisited(childrenVisted(children));
			// new heuristics update
			// double difference = ( sumHeuristics(children) / (double) children.length) - back.getGameValue();
			// back.setGameValue(back.getGameValue() + difference);

			//old heuristic update
			back.setGameValue(( sumHeuristics(children) / (double) children.length)  );
			
			back = back.getParent();
	
		}
	}

	public int childrenVisted(UnrestrictedNode[] children){
		int visited = 0;
        for(int i = 0; i < children.length; i++){
            visited = visited + children[i].getVisited() + children[i].getChildrenVisited();
        }
		return visited;
	}

	public double sumHeuristics(UnrestrictedNode[] children){
        double sum = 0;
        for(int i = 0; i < children.length; i++){
            sum += children[i].getGameValue();
        }
		return sum;
    }



	//select the next node to be expended
	public void selection() {
		UnrestrictedNode select = root;
		select.visited();
		while (select.getChildren() != null){
			 UnrestrictedNode[] children = select.getChildren();
			 UnrestrictedNode bestChild = children[0];
			 for(int i = 1; i < children.length; i++){
				 if( selectionValue( children[i] ) > selectionValue( bestChild ) ){
					 bestChild = children[i];
				 }
			 }
			 select = bestChild;
			 select.visited();
		}


		this.choice = select;
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
