package uk.ac.kcl.inf.mdeoptimiser.libraries.core.optimisation.moea.PSJ

import java.util.HashMap
import java.util.Map
import java.util.ArrayList
import org.eclipse.emf.henshin.interpreter.impl.EGraphImpl
import uk.ac.kcl.inf.mdeoptimiser.libraries.core.optimisation.interpreter.guidance.Solution
import uk.ac.kcl.inf.mdeoptimiser.libraries.core.optimisation.interpreter.henshin.HenshinExecutor
import uk.ac.kcl.inf.mdeoptimiser.libraries.core.optimisation.operators.mutation.selection.RandomOperatorSelector
import org.eclipse.emf.henshin.model.Unit
import uk.ac.kcl.inf.mdeoptimiser.libraries.core.optimisation.moea.problem.MoeaOptimisationSolution

class AllMatchingOneStepMutationStrategy{

	HenshinExecutor henshinExecutor

	RandomOperatorSelector operatorSelectionDecorator;

	new(HenshinExecutor henshinExecutor, RandomOperatorSelector randomOperatorSelector) {

		this.henshinExecutor = henshinExecutor
		this.operatorSelectionDecorator = randomOperatorSelector
	}

	def mutate(Solution model) {

		var candidateSolution = new Solution(model)
		
		//all solutions reachable by applying one step of transformation to the model
		var allReachableSolutions = new ArrayList<Solution>

		val graph = new EGraphImpl(candidateSolution.getModel)

		var transformations = applyOperators(candidateSolution, graph);


        while (transformations != null){
            candidateSolution.updateModel(graph.roots.head, transformations);
			allReachableSolutions.add(candidateSolution);
			candidateSolution = new Solution(model);
			transformations = applyOperators(candidateSolution, graph);
        }

		operatorSelectionDecorator.flushTriedOperators() 

		return allReachableSolutions;
	}

	/* Apply transformations according to the configured step size.
	 * @return a map of the ordered transformations applied in this step
	 */
	def Map<Integer, String> applyOperators(Solution candidateSolution, EGraphImpl egraph) {

        if(this.operatorSelectionDecorator.hasUntriedOperators == false){
            return null;
        }

		var stepTransformations = new HashMap<Integer, String>();

		    // Run the mutation for one steps
			var Unit operator = null;
			var operatorApplied = false;

			do {
				operator = this.operatorSelectionDecorator.getNextOperator();

				if (henshinExecutor.operatorApplied(operator, egraph, candidateSolution)) {
					stepTransformations.put(1, operator.name)
					operatorApplied = true;
					operator = null;
				}
			} while (!operatorApplied && operatorSelectionDecorator.hasUntriedOperators())


		return stepTransformations;
	}

}