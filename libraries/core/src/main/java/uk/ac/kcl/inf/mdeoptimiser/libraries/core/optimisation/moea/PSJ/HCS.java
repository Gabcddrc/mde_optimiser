
package uk.ac.kcl.inf.mdeoptimiser.libraries.core.optimisation.moea.PSJ;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


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
import java.lang.Math; 

public class HCS extends AbstractEvolutionaryAlgorithm implements
		EpsilonBoxEvolutionaryAlgorithm {


	private final Selection selection;

	/**
	 * The variation operator.
	 */
	private final Variation variation;


	private final TournamentSelection s;

	/**
	 * Parameters needed for MDEO to accept HCS without adapter, however not necessary
	 * 
	 * @param problem the problem being solved
	 * @param population the population used to store solutions
	 * @param archive the archive used to store the result; can be {@code null}
	 * @param selection the selection operator
	 * @param variation the variation operator
	 * @param initialization the initialization method
	 */

	public HCS(Problem problem, NondominatedSortingPopulation population,
			EpsilonBoxDominanceArchive archive, Selection selection,
			Variation variation, Initialization initialization) {
				
		super(problem, population, archive, initialization);
		this.s = (TournamentSelection) selection;
		this.selection = selection;
		this.variation = variation;
	}

	@Override
	public void iterate() {
		NondominatedSortingPopulation population = getPopulation();
		Iterator<Solution> itr = population.iterator();
        Solution besSolution = (Solution) itr.next();
		Solution[] next = new Solution[variation.getArity()];
		for (int i = 0; i < next.length; i++) {
			next[i] = besSolution;
		}
		besSolution = expand(next);
		if(besSolution!= null){
			population.clear();
			population.add(besSolution);

		// //nrp output	
		// population.add(besSolution);
		// population.truncate(population.size());
		}


	}

	// -1 if solution 1 dominated solution 2
	public int compareDomin(Solution solution1, Solution solution2){
		return s.getComparator().compare(solution1, solution2);
	}

	// Create new Solutions based on parent
	public Solution expand(Solution[] parent) {
		Solution equal = null;
		Solution[] solutions = new Solution[variation.getArity()];
		for (int i = 0; i < 50; i++) {
			solutions = variation.evolve(parent);
			evaluateAll(solutions);
			if(compareDomin(solutions[0], parent[0]) == -1){
				return solutions[0];
			}
			else if(compareDomin(solutions[0], parent[0]) == 0){
				equal = solutions[0];
			} 
		}
		return equal;
	

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
