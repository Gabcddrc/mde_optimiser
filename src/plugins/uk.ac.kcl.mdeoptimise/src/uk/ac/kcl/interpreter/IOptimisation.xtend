package uk.ac.kcl.interpreter

import org.eclipse.emf.ecore.EObject
import uk.ac.kcl.mdeoptimise.OptimisationSpec
import uk.ac.kcl.optimisation.SolutionGenerator
import java.util.Iterator

interface IOptimisation {
	
	/**
	 * Returns an optimisation outcome as a set of objects.
	 * Maybe this should also include population information, such as objective values.
	 * TODO Look at MOEA Instrumentation?
	 */
	def Iterator<EObject> execute(OptimisationSpec optimisationSpec, SolutionGenerator solutionGenerator)
	
}
