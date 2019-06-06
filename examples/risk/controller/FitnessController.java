/*
 * To change this license header, choose License Headers in Fitness Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package risk.controller;

import java.util.ArrayList;
import org.hibernate.Criteria;
import risk.entity.Fitness;
import risk.ui.Main;
import static risk.ui.Main.session;
import static risk.ui.Main.tx;

/**
 *
 * @author nguyennam
 */
public class FitnessController {
    public static void create(Fitness tempFitness){
        
            tx = session.beginTransaction();
            session.save(tempFitness);
            tx.commit();
    }
    
    public static ArrayList<Fitness> index(){
        Criteria fitnessCr = Main.session.createCriteria(Fitness.class);
        ArrayList<Fitness> fitnesss = (ArrayList<Fitness>) fitnessCr.list();   
        return fitnesss;
    }
    
    public static Fitness show(int id){
        Fitness fitness = (Fitness) Main.session.get(Fitness.class,id);
        return fitness;
    }
    
    public static Fitness update(Fitness tempFitness){
        
        Main.tx = session.beginTransaction();
        Fitness fitness = FitnessController.show(tempFitness.getId());
        fitness.setId(tempFitness.getId());
        fitness.setProjects(tempFitness.getProjects());
        fitness.setRisk(tempFitness.getRisk());
        fitness.setMethod(tempFitness.getMethod());
        fitness.setFinancialImpact(tempFitness.getFinancialImpact());
        fitness.setRiskLevel(tempFitness.getRiskLevel());
        fitness.setCost(tempFitness.getCost());
        fitness.setDiff(tempFitness.getDiff());
        fitness.setPriority(tempFitness.getPriority());
        fitness.setTime(tempFitness.getTime());
        session.update(fitness);
        Main.tx.commit();
        return fitness;
    }
    
    
    public static void delete(int id){
        tx = session.beginTransaction();
        Fitness fitness = (Fitness) session.load(Fitness.class, id);
        session.delete(fitness);
        tx.commit();
    }
    
}
