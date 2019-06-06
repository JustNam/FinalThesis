/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package risk.builder;

import risk.entity.Fitness;
import risk.entity.Projects;

/**
 *
 * @author nguyennam
 * @param <T>
 * @param <F>
 */
public abstract class AbstractFitnessBuilder <T extends AbstractFitnessBuilder<T,F>,F extends Fitness>{
    protected F fitness;
    public abstract T addId(int id);
    public abstract T addProjects(Projects projects);
    public abstract T addRisk(Integer risk);
    public abstract T addMethod(Integer method);
    public abstract T addFinancialImpact(Integer financialImpact);
    public abstract T addRiskLevel(Integer riskLevel);
    public abstract T addCost(int cost);
    public abstract T addDiff(int diff);
    public abstract T addPriority(int priority);
    public abstract T addTime(int time);
    
    public F build(){
        return this.fitness;
    }
}