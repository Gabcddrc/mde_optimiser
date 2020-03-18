/**
 * generated by Xtext 2.17.0
 */
package uk.ac.kcl.inf.mdeoptimiser.languages.mopt.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

import uk.ac.kcl.inf.mdeoptimiser.languages.mopt.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see uk.ac.kcl.inf.mdeoptimiser.languages.mopt.MoptPackage
 * @generated
 */
public class MoptSwitch<T> extends Switch<T>
{
  /**
   * The cached model package
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected static MoptPackage modelPackage;

  /**
   * Creates an instance of the switch.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public MoptSwitch()
  {
    if (modelPackage == null)
    {
      modelPackage = MoptPackage.eINSTANCE;
    }
  }

  /**
   * Checks whether this is a switch for the given package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param ePackage the package in question.
   * @return whether this is a switch for the given package.
   * @generated
   */
  @Override
  protected boolean isSwitchFor(EPackage ePackage)
  {
    return ePackage == modelPackage;
  }

  /**
   * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the first non-null result returned by a <code>caseXXX</code> call.
   * @generated
   */
  @Override
  protected T doSwitch(int classifierID, EObject theEObject)
  {
    switch (classifierID)
    {
      case MoptPackage.OPTIMISATION:
      {
        Optimisation optimisation = (Optimisation)theEObject;
        T result = caseOptimisation(optimisation);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case MoptPackage.PROBLEM_SPEC:
      {
        ProblemSpec problemSpec = (ProblemSpec)theEObject;
        T result = caseProblemSpec(problemSpec);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case MoptPackage.GOAL_SPEC:
      {
        GoalSpec goalSpec = (GoalSpec)theEObject;
        T result = caseGoalSpec(goalSpec);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case MoptPackage.SEARCH_SPEC:
      {
        SearchSpec searchSpec = (SearchSpec)theEObject;
        T result = caseSearchSpec(searchSpec);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case MoptPackage.SOLVER_SPEC:
      {
        SolverSpec solverSpec = (SolverSpec)theEObject;
        T result = caseSolverSpec(solverSpec);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case MoptPackage.BASE_PATH_SPEC:
      {
        BasePathSpec basePathSpec = (BasePathSpec)theEObject;
        T result = caseBasePathSpec(basePathSpec);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case MoptPackage.META_MODEL_SPEC:
      {
        MetaModelSpec metaModelSpec = (MetaModelSpec)theEObject;
        T result = caseMetaModelSpec(metaModelSpec);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case MoptPackage.MODEL_PATH_SPEC:
      {
        ModelPathSpec modelPathSpec = (ModelPathSpec)theEObject;
        T result = caseModelPathSpec(modelPathSpec);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case MoptPackage.OBJECTIVE_INTERPRETER_SPEC:
      {
        ObjectiveInterpreterSpec objectiveInterpreterSpec = (ObjectiveInterpreterSpec)theEObject;
        T result = caseObjectiveInterpreterSpec(objectiveInterpreterSpec);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case MoptPackage.CONSTRAINT_INTERPRETER_SPEC:
      {
        ConstraintInterpreterSpec constraintInterpreterSpec = (ConstraintInterpreterSpec)theEObject;
        T result = caseConstraintInterpreterSpec(constraintInterpreterSpec);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case MoptPackage.MODEL_INITIALISER_SPEC:
      {
        ModelInitialiserSpec modelInitialiserSpec = (ModelInitialiserSpec)theEObject;
        T result = caseModelInitialiserSpec(modelInitialiserSpec);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case MoptPackage.RULEGEN_SPEC:
      {
        RulegenSpec rulegenSpec = (RulegenSpec)theEObject;
        T result = caseRulegenSpec(rulegenSpec);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case MoptPackage.RULEGEN_NODE:
      {
        RulegenNode rulegenNode = (RulegenNode)theEObject;
        T result = caseRulegenNode(rulegenNode);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case MoptPackage.RULEGEN_EDGE:
      {
        RulegenEdge rulegenEdge = (RulegenEdge)theEObject;
        T result = caseRulegenEdge(rulegenEdge);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case MoptPackage.REPORT_INTERPRETER_SPEC:
      {
        ReportInterpreterSpec reportInterpreterSpec = (ReportInterpreterSpec)theEObject;
        T result = caseReportInterpreterSpec(reportInterpreterSpec);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case MoptPackage.EVOLVER_SPEC:
      {
        EvolverSpec evolverSpec = (EvolverSpec)theEObject;
        T result = caseEvolverSpec(evolverSpec);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case MoptPackage.MULTIPLICITY_REFINEMENT_SPEC:
      {
        MultiplicityRefinementSpec multiplicityRefinementSpec = (MultiplicityRefinementSpec)theEObject;
        T result = caseMultiplicityRefinementSpec(multiplicityRefinementSpec);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case MoptPackage.EVOLVER_PARAMETER:
      {
        EvolverParameter evolverParameter = (EvolverParameter)theEObject;
        T result = caseEvolverParameter(evolverParameter);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case MoptPackage.PARAMETER_FUNCTION:
      {
        ParameterFunction parameterFunction = (ParameterFunction)theEObject;
        T result = caseParameterFunction(parameterFunction);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case MoptPackage.ALGORITHM_SPEC:
      {
        AlgorithmSpec algorithmSpec = (AlgorithmSpec)theEObject;
        T result = caseAlgorithmSpec(algorithmSpec);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case MoptPackage.TERMINATION_CONDITION_SPEC:
      {
        TerminationConditionSpec terminationConditionSpec = (TerminationConditionSpec)theEObject;
        T result = caseTerminationConditionSpec(terminationConditionSpec);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case MoptPackage.PARAMETER:
      {
        Parameter parameter = (Parameter)theEObject;
        T result = caseParameter(parameter);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case MoptPackage.PARAMETER_VALUE:
      {
        ParameterValue parameterValue = (ParameterValue)theEObject;
        T result = caseParameterValue(parameterValue);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      default: return defaultCase(theEObject);
    }
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Optimisation</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Optimisation</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseOptimisation(Optimisation object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Problem Spec</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Problem Spec</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseProblemSpec(ProblemSpec object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Goal Spec</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Goal Spec</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseGoalSpec(GoalSpec object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Search Spec</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Search Spec</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseSearchSpec(SearchSpec object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Solver Spec</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Solver Spec</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseSolverSpec(SolverSpec object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Base Path Spec</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Base Path Spec</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseBasePathSpec(BasePathSpec object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Meta Model Spec</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Meta Model Spec</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseMetaModelSpec(MetaModelSpec object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Model Path Spec</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Model Path Spec</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseModelPathSpec(ModelPathSpec object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Objective Interpreter Spec</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Objective Interpreter Spec</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseObjectiveInterpreterSpec(ObjectiveInterpreterSpec object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Constraint Interpreter Spec</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Constraint Interpreter Spec</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseConstraintInterpreterSpec(ConstraintInterpreterSpec object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Model Initialiser Spec</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Model Initialiser Spec</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseModelInitialiserSpec(ModelInitialiserSpec object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Rulegen Spec</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Rulegen Spec</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseRulegenSpec(RulegenSpec object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Rulegen Node</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Rulegen Node</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseRulegenNode(RulegenNode object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Rulegen Edge</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Rulegen Edge</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseRulegenEdge(RulegenEdge object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Report Interpreter Spec</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Report Interpreter Spec</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseReportInterpreterSpec(ReportInterpreterSpec object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Evolver Spec</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Evolver Spec</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseEvolverSpec(EvolverSpec object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Multiplicity Refinement Spec</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Multiplicity Refinement Spec</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseMultiplicityRefinementSpec(MultiplicityRefinementSpec object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Evolver Parameter</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Evolver Parameter</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseEvolverParameter(EvolverParameter object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Parameter Function</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Parameter Function</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseParameterFunction(ParameterFunction object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Algorithm Spec</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Algorithm Spec</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseAlgorithmSpec(AlgorithmSpec object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Termination Condition Spec</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Termination Condition Spec</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseTerminationConditionSpec(TerminationConditionSpec object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Parameter</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Parameter</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseParameter(Parameter object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Parameter Value</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Parameter Value</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseParameterValue(ParameterValue object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch, but this is the last case anyway.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject)
   * @generated
   */
  @Override
  public T defaultCase(EObject object)
  {
    return null;
  }

} //MoptSwitch