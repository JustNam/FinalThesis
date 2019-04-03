package procurement;

import org.moeaframework.Executor;
import org.moeaframework.algorithm.single.LinearObjectiveComparator;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;
import org.moeaframework.util.Vector;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class ProcurementSolver {

    public static void main(String[] args) throws IOException {

        // prefix path for data files
        String prefix = "data/";

//        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        
        
        ProcurementProblem procurementProblem = new ProcurementProblem(prefix + "big.json");
        // solve using EA ECEA
//        NondominatedPopulation result = new Executor()
//                .withProblem(procurementProblem)
//                .withAlgorithm("ECEA")
//                .distributeOnAllCores()
//                .withMaxEvaluations(50000)
//                .run();
        NondominatedPopulation result = new Executor()
        		.withProblem(procurementProblem)
        		.withAlgorithm("DBEA")
        		.distributeOnAllCores()
        		.withMaxEvaluations(50000)
        		.withProperty("seed", 6)
        		.withProperty("max_iterations", 6)
        		.run();

        // sort using decider's preference weights
        double[] objectiveWeights = {1.5, 1.0, 1E5, 1.0};
        LinearObjectiveComparator comparator = new LinearObjectiveComparator(objectiveWeights);
        result.sort(comparator);
        // print the results
        for (int i = 0; i < result.size(); i++) {
            Solution solution = result.get(i);
            double[] objectives = solution.getObjectives();

            // negate objectives to return them to their maximized form
            objectives = Vector.negate(objectives);

            // print available information
            System.out.println("Solution " + (i + 1) + ":");
            System.out.println(String.format("    Buyer Profit: %,d", ((Double) objectives[0]).longValue()));
            System.out.println(String.format("    SumOfProviders Profit: %,d", ((Double) objectives[1]).longValue()));
            System.out.println(String.format("    Quality: %.2f", objectives[2]));
            System.out.println(String.format("    DiffOfProviders Profit: %,d", -((Double) objectives[3]).longValue()));
            System.out.println(String.format("    Fitness: %.1f", -LinearObjectiveComparator.calculateFitness(solution, objectiveWeights)));

            System.out.println("    Buyer Choice: ");
//            int varCount = solution.getNumberOfVariables() / 2;
//            for (int j = 0; j < varCount; j++) {
//                int choice = EncodingUtils.getInt(solution.getVariable(j));
//                int providerIndex = procurementProblem.getPackages().get(j).getJoinedProviders().get(choice);
//                System.out.println(String.format("       Package %d :  Provider %d", j + 1, providerIndex, procurementProblem.getProviders().get(providerIndex).getDescription()));
//                int day = EncodingUtils.getInt(solution.getVariable(j + varCount));
//                System.out.println(String.format("                 Start date  %s", df.format(procurementProblem.addDay(procurementProblem.getPackages().get(j).getTimeline().getFrom(), day))));
//            }
//            System.out.println();

            procurementProblem.setDebug(true);
            procurementProblem.evaluate(solution);
            System.out.println();
        }
    }
}
