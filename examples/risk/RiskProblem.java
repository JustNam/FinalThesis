package risk;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.moeaframework.core.Problem;
import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;

import risk.entity.Conflicts;
import risk.entity.Fitness;
import risk.entity.Methods;
import risk.entity.Risks;

public class RiskProblem implements Problem {

	/* Weight for adaptive function */
	private Fitness fitness;
	
	/* Configuration for problem */
	private long numRisk;
	private long numMethod;
	private long numConflict;
	private ArrayList<Risks> risks;
	private ArrayList<ArrayList<Methods>> methodsOfRisk;
	private ArrayList<Methods> methods;
	private ArrayList<Conflicts> conflicts;
	private boolean print;
	// Accelerate speed of computation 
	private List<Object[]> conflictsList;
	private int[] finalMethodChoice;


	public RiskProblem(int projectId, int fitnessId) {
		super();
		/* Load data from db */
		load(projectId, fitnessId);
		print = false;
	}

	public void load(int projectId, int fitnessId) {

		/* Get fitness */
		Criteria cr0 = RiskSolver.session.createCriteria(Fitness.class);
		List result0 = cr0.add(Restrictions.eq("id", fitnessId)).list();
		fitness = (Fitness) result0.get(0);
		
		/* Get numRisk */
		Criteria cr1 = RiskSolver.session.createCriteria(Risks.class);
		Criteria cr2 = RiskSolver.session.createCriteria(Methods.class);
		List result1 = cr1.setProjection(Projections.countDistinct("id")).add(Restrictions.eq("projects.id", projectId))
				.list();
		
		/* Get conflicts */
		Criteria conflictCriteria = RiskSolver.session.createCriteria(Conflicts.class,"c");
		Criteria projectCriteria0 = conflictCriteria.createCriteria("projects");
		projectCriteria0.add(Restrictions.eq("id", projectId));
		
		/* Get numMethod */
		Criteria methodCriteria = RiskSolver.session.createCriteria(Methods.class, "med");
		Criteria riskCriteria = methodCriteria.createCriteria("risks", "r"); // add risk FK
		Criteria projectCriteria = riskCriteria.createCriteria("projects", "p"); // add project FK
		projectCriteria.add(Restrictions.eq("id", projectId));

		List result2 = methodCriteria.setProjection(Projections.countDistinct("id")).list();

		numRisk = (long) result1.get(0);
		numMethod = (long) result2.get(0);

		/* Get risks and methods, conflicts*/
		Criteria cr3 = RiskSolver.session
                        .createCriteria(Risks.class)
                        .createCriteria("projects","p")
                        .add(Restrictions.eq("id", projectId));
		Criteria cr4 = RiskSolver.session
                        .createCriteria(Methods.class)
                        .createCriteria("risks","r")
                        .createCriteria("projects","p")
                        .add(Restrictions.eq("id",projectId));
		Criteria cr5 = RiskSolver.session
                        .createCriteria(Conflicts.class)
                        .createCriteria("projects")
                        .add(Restrictions.eq("id", projectId));

		risks = (ArrayList<Risks>) cr3.list();
		methods = (ArrayList<Methods>) cr4.list();
		conflicts = (ArrayList<Conflicts>) cr5.list();
		
		/* Initiate methods for each risks (reduce the frequency of access database)*/
		methodsOfRisk = new ArrayList<ArrayList<Methods>>();
		for (int i = 0; i < numRisk; i++) {
			Risks risk = risks.get(i);
			Set<Methods> methodSet = risk.getMethodses();
			ArrayList<Methods> methodList = new ArrayList<Methods>(methodSet);
			methodsOfRisk.add(methodList);
		}
		
		/* Initiate Conflicts with risk_id */
		Criteria methodCriteria1 = conflictCriteria.createCriteria("methodsByMethod1Id","m1");
		Criteria methodCriteria2 = conflictCriteria.createCriteria("methodsByMethod2Id","m2");
		Criteria riskCriteria1 = methodCriteria1.createCriteria("risks","r1");
		Criteria riskCriteria2 = methodCriteria2.createCriteria("risks","r2");
		 
		ProjectionList properties = Projections.projectionList();
		properties.add(Projections.property("m1.id"));
		properties.add(Projections.property("m2.id"));
		properties.add(Projections.property("r1.id"));
		properties.add(Projections.property("r2.id"));
		
		conflictCriteria.setProjection(properties);
		conflictsList =  (List<Object[]>) conflictCriteria.list();
		
		/* Get numConflict LAZY*/
		numConflict = conflicts.size();
    	
	}

	public boolean isConflict(int[] result) {
		/* Pattern conflict variable : "m1 m2 r1 r2" */
		for (int j = 0; j < numConflict; j++) {

			Object[] conflict = conflictsList.get(j);
			int methodId1 = (int) conflict[0];
			int methodId2 = (int) conflict[1];
			int riskId1 = (int) conflict[2];
			int riskId2 = (int) conflict[3];
			if ((result[riskId1-1] == methodId1 && result[riskId2-1] == methodId2)
					|| (result[riskId1-1] == methodId2 && result[riskId2-1] == methodId1)) {
				return true;
			}
		}

		return false;
		
	}
	
	@Override
	public String getName() {
		return "Risk Problem";
	}

	@Override
	public int getNumberOfVariables() {
		return ((Long) numRisk).intValue();
	}

	@Override
	public int getNumberOfObjectives() {
		return 2;
	}

	@Override
	public int getNumberOfConstraints() {
		return 1;
	}

	@Override
	public void evaluate(Solution solution) {

		int[] methodChoice = EncodingUtils.getInt(solution);
		
		/* Calculate Objectives */
		float adaptiveFuntion = 0;
		float cost = 0;
		int[] methodIDs = new int[methodChoice.length];
		
		
		for (int i = 0; i < methodChoice.length; i++) {
			
			/* Load risk and method from id */
			Risks risk = risks.get(i);
			Set<Methods> methodsSet = risk.getMethodses();
			List methodList = new ArrayList<>();
			methodList.addAll(methodsSet);
			
			Methods method = (Methods) methodsOfRisk.get(i).get(methodChoice[i]);
			/* Used to print the result */
			methodIDs[i] = method.getId();
			
			/* Convert risk level into number */
			String riskLevelString = risk.getRiskLevel();
			int riskLevelNumber = 0;
                        if (riskLevelString.equals("Extreme"))
                            riskLevelNumber = 4;
                        else if (riskLevelString.equals("High"))
                            riskLevelNumber = 3;
                        else if (riskLevelString.equals("Medium"))
                            riskLevelNumber = 2;
			
			/* Calculate adaptive function value */
			float riskPara = ((float) fitness.getFinancialImpact() * risk.getFinancialImpact() / 1000
					+ fitness.getRiskLevel() * riskLevelNumber) * risk.getProbability();
			float methodPara = (float) (fitness.getCost() * method.getCost() / 1000
					+ fitness.getDiff() * method.getDiff() + fitness.getPriority() * method.getPriority()
					+ fitness.getTime() * method.getTime());

			adaptiveFuntion += riskPara * (100 - method.getEfficiency()) / 100 + methodPara;
			
			/* Calculate total of cost */
			cost += risk.getFinancialImpact() * risk.getProbability() * (100 - method.getEfficiency()) / 10000
				+ method.getCost();
		}
		
		
		/* Print detail of results */
		if (print) {
                        finalMethodChoice = methodIDs;
		}
		
		/* Set objectives, smallest */
        solution.setObjective(0, adaptiveFuntion);
        solution.setObjective(1, cost);
	
        /* Set constrains, 0 is satisfied */
        int isConflict = isConflict(methodChoice)?1:0;
        solution.setConstraint(0,isConflict);
        
        
	}

	@Override
	public Solution newSolution() {
		Solution solution = new Solution(getNumberOfVariables(), getNumberOfObjectives(), getNumberOfConstraints());
        for (int i = 0; i < numRisk; i++) {
            //contain the ID of joined provider, not the whole provider
            solution.setVariable(i, EncodingUtils.newInt(0, risks.get(i).getMethodses().size() - 1));
        }
		return solution;
	}

	@Override
	public void close() {

	}

	/* Default getter and setter */
        
        public int[] getFinalMethodChoice() {
            return finalMethodChoice;
        }

        public void setFinalMethodChoice(int[] finalMethodChoice) {
            this.finalMethodChoice = finalMethodChoice;
        }
	
	public long getNumRisk() {
		return numRisk;
	}

	public void setNumRisk(long numRisk) {
		this.numRisk = numRisk;
	}

	public long getNumMethod() {
		return numMethod;
	}

	public void setNumMethod(long numMethod) {
		this.numMethod = numMethod;
	}

	public ArrayList<Risks> getRisks() {
		return risks;
	}

	public void setRisks(ArrayList<Risks> risks) {
		this.risks = risks;
	}

	public ArrayList<Methods> getMethods() {
		return methods;
	}

	public void setMethods(ArrayList<Methods> methods) {
		this.methods = methods;
	}

	
	public boolean isPrint() {
		return print;
	}

	public void setPrint(boolean print) {
		this.print = print;
	}

	
}
