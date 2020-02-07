package uk.ac.kcl.inf.mdeoptimiser.libraries.core.optimisation.moea.a;
// import uk.ac.kcl.inf.mdeoptimiser.libraries.core.optimisation.interpreter.guidance.Solution;
import uk.ac.kcl.inf.mdeoptimiser.libraries.core.optimisation.IGuidanceFunction;
import uk.ac.kcl.inf.mdeoptimiser.libraries.core.optimisation.moea.problem.MoeaOptimisationProblem;
import uk.ac.kcl.inf.mdeoptimiser.libraries.core.optimisation.moea.problem.MoeaOptimisationSolution;
import org.eclipse.emf.ecore.EObject;
import org.moeaframework.core.Solution;
 
public class SolutionConverter{
    public uk.ac.kcl.inf.mdeoptimiser.libraries.core.optimisation.interpreter.guidance.Solution convert(Solution solution){
        // MoeaOptimisationSolution lightCopy = new MoeaOptimisationSolution(solution.getNumberOfObjectives(), solution.getNumberOfConstraints());
        System.out.println("create light copy");
        MoeaOptimisationSolution lightCopy = new MoeaOptimisationSolution((MoeaOptimisationSolution) solution);
        // lightCopy.setConstraints(solution.getConstraints());
		// lightCopy.setObjectives(solution.getObjectives());
        return lightCopy.getModel();
    }

}