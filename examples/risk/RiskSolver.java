package risk;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.moeaframework.Executor;
import org.moeaframework.algorithm.single.LinearObjectiveComparator;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;
import org.moeaframework.util.Vector;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;

import risk.entity.Results;
import risk.entity.RiskTypes;
import risk.entity.Risks;

public class RiskSolver {

	// att can inferred from TestData.a
	public int aAfter;
	public static SessionFactory factory;
	public static Session session;
	static Transaction tx = null;
        private int projectId;
        private int fitnessId;

	public RiskSolver() {

	}
        
	public RiskSolver(int projectId, int fitnessId) {
            
	}

	public int getaAfter() {
		return aAfter;
	}

	public void setaAfter(int aAfter) {
		this.aAfter = aAfter;
	}

	// constructor
	public RiskSolver(String path) {

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

        public Results evaluate(int projectId, int fitnessId, String algorithm, int noEvaluations) {


            PrintWriter writer = null;
            int noTest = 1;
            ArrayList<Long> adaArr = new ArrayList<>();
            ArrayList<Long> cosArr = new ArrayList<>();
            ArrayList<Long> comArr = new ArrayList<>();
            ArrayList<Long> timeArr = new ArrayList<>();
            ArrayList<int[]> methodChoice = new ArrayList<>();
            try {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(algorithm);
                stringBuilder.append("-results.txt");
                String filename = stringBuilder.toString();
                writer = new PrintWriter(filename, "UTF-8");
            } catch (FileNotFoundException | UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            // Run multiple times
            for (int k = 0; k < noTest; k++) {
                Instant start = Instant.now();
                RiskProblem riskProblem = new RiskProblem(1, fitnessId);

                NondominatedPopulation result = new Executor().withProblem(riskProblem).withAlgorithm(algorithm)
                        .distributeOnAllCores().withMaxEvaluations(noEvaluations).run();

                double[] objectiveWeights = {1, 1};
                LinearObjectiveComparator comparator = new LinearObjectiveComparator(objectiveWeights);
                result.sort(comparator);

                /* print results */
                for (int i = 0; i < result.size(); ++i) {

                    Solution solution = result.get(i);
                    double[] objectives = solution.getObjectives();

                    objectives = Vector.negate(objectives);
                    System.out.println("Solution " + (i + 1) + ":");
                    long ada = -((Double) objectives[0]).longValue();
                    long cos = -((Double) objectives[1]).longValue();
                    long com = ((Double) (-objectiveWeights[0] * objectives[0] - objectiveWeights[1] * objectives[1]))
                            .longValue();
                    adaArr.add(ada);
                    cosArr.add(cos);
                    comArr.add(com);
                    System.out.println(String.format("	Adaptive Function : %,d", ada));
                    System.out.println(String.format("	Cost : %,d", cos));
                    System.out.println("	Comparative value: " + com);

                    /* Print detailed result */
                    riskProblem.setPrint(false);
                    riskProblem.evaluate(solution);
                    methodChoice.add(riskProblem.getFinalMethodChoice());

                    /* Print to the file */
                    writer.println("Result " + (k + 1) + ":");
                    writer.println(String.format("	Adaptive Function : %,d", ada));
                    writer.println(String.format("	Cost : %,d", cos));
                    writer.println(String.format("	Comparative value: %,d", com));
                }
                //Log time
                Instant finish = Instant.now();
                long timeElapsed = Duration.between(start, finish).toMillis();
                timeArr.add(timeElapsed);
                writer.println(String.format("	Execution time: %,d", timeElapsed));
                writer.println();
            }

            // Calculate
            float adaAvarage = 0;
            float cosAvarage = 0;
            float comAvarage = 0;
            float timeAvarage = 0;

            for (int i = 0; i < adaArr.size(); i++) {
                adaAvarage += (float) adaArr.get(i);
                cosAvarage += (float) cosArr.get(i);
                comAvarage += (float) comArr.get(i);
    //            timeAvarage += (float) timeArr.get(i);
            }

            writer.println("AVARAGE RESULT:");
            writer.println(String.format("	Adaptive Function: %.2f", adaAvarage / noTest));
            writer.println(String.format("	Cost : %.2f", cosAvarage / noTest));
            writer.println(String.format("	Comparative value: %.2f", comAvarage / noTest));
            writer.println(String.format("	Execution time: %.2f miliseconds", timeAvarage / noTest));
            writer.close();
            return new Results(adaArr, cosArr, comArr, timeArr, methodChoice);
        }

        public void printResults(NondominatedPopulation result) {
            
            Instant start = Instant.now();
            double[] objectiveWeights = {1, 1};
            LinearObjectiveComparator comparator = new LinearObjectiveComparator(objectiveWeights);
            result.sort(comparator);

            /* print results */
            for (int i = 0; i < result.size(); i = i + 1) {

                Solution solution = result.get(i);
                double[] objectives = solution.getObjectives();

                objectives = Vector.negate(objectives);
                System.out.println("Solution " + (i + 1) + ":");
                long ada = -((Double) objectives[0]).longValue();
                long cos = -((Double) objectives[1]).longValue();
                long com = ((Double) (-objectiveWeights[0] * objectives[0] - objectiveWeights[1] * objectives[1]))
                        .longValue();
                System.out.println(String.format("	Adaptive Function : %,d", ada));
                System.out.println(String.format("	Cost : %,d", cos));
                System.out.println("	Comparative value: " + com);

                /* Print detailed result */
//					riskProblem.setPrint(true);
//					riskProblem.evaluate(solution);
//					System.out.println();
            }
            //Log time
            Instant finish = Instant.now();
            long timeElapsed = Duration.between(start, finish).toMillis();
            System.out.println("	Execution time: " + timeElapsed);

        }
	public int getNumRisk() {
		Session session = RiskSolver.factory.openSession();
		Transaction tx = null;
		int resu = 0;

		try {

			// Getting session and create transaction
			tx = session.beginTransaction();

			Criteria cr = session.createCriteria(Risks.class);
			cr.add(Restrictions.eq("id", 4));
			List result = cr.list();
			Risks risk = (Risks) result.get(0);
			resu = risk.getId();
			// sqlQuery.setParameter("mpm", maphieumuon);

			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		// return (int)
		// session.createCriteria("Book").setProjection(Projections.rowCount()).uniqueResult();
		return resu;
	}

	// List all riskTypes
	public void listRiskTypes() {

		// Getting session and create transaction
		Session session = factory.openSession();
		Transaction tx = null;

		try {

			// Getting session and create transaction
			tx = session.beginTransaction();
			Query query = session.createQuery("FROM RiskTypes");
			// Query query1 = session.createQuery("FROM risks");
			List riskTypes = query.list();
			for (Iterator iterator = riskTypes.iterator(); iterator.hasNext();) {
				RiskTypes riskType = (RiskTypes) iterator.next();
				System.out.println("ID: " + riskType.getId());
			}
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

	}

	// List all risks
	public List listRisks() {

		// Getting session and create transaction
		Session session = factory.openSession();
		Transaction tx = null;
		List risks = null;

		try {

			// Getting session and create transaction
			tx = session.beginTransaction();
			Query query = session.createQuery("FROM Risks");
			risks = query.list();

			// Test
			Iterator iterator = risks.iterator();
			Risks risk = (Risks) iterator.next();
			System.out.println(risk.getProjects().getCode());

			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return risks;
	}

	// Add new risk type
	public Integer addRiskType(String code, String name, String description, Date createdAt, int deleted,
			Set<Risks> riskses) {

		// Getting session and create transaction
		Session session = factory.openSession();
		Transaction tx = null;

		// Configure riskType att
		Integer riskTypeID = null;

		try {
			// Begin transaction and transfer data
			tx = session.beginTransaction();
			RiskTypes rt = new RiskTypes(code, name, description, createdAt, deleted, riskses);
			riskTypeID = (Integer) session.save(rt);

			System.out.println("done");
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

		return riskTypeID;
	}

}
