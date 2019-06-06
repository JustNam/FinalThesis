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
 */
public class FitnessBuilder extends AbstractFitnessBuilder<FitnessBuilder, Fitness>{
    
    public FitnessBuilder(){
        this.fitness = new Fitness();
    }
    @Override
    public FitnessBuilder addId(int id) {
    	this.fitness.setId(id); 
    	return this;
    }

    @Override
    public FitnessBuilder addProjects(Projects projects) {
    	this.fitness.setProjects(projects); 
    	return this;
    }

    @Override
    public FitnessBuilder addRisk(Integer risk) {
    	this.fitness.setRisk(risk); 
    	return this;
    }

    @Override
    public FitnessBuilder addMethod(Integer method) {
    	this.fitness.setMethod(method); 
    	return this;
    }

    @Override
    public FitnessBuilder addFinancialImpact(Integer financialImpact) {
    	this.fitness.setFinancialImpact(financialImpact); 
    	return this;
    }

    @Override
    public FitnessBuilder addRiskLevel(Integer riskLevel) {
    	this.fitness.setRiskLevel(riskLevel); 
    	return this;
    }

    @Override
    public FitnessBuilder addCost(int cost) {
    	this.fitness.setCost(cost); 
    	return this;
    }

    @Override
    public FitnessBuilder addDiff(int diff) {
    	this.fitness.setDiff(diff); 
    	return this;
    }

    @Override
    public FitnessBuilder addPriority(int priority) {
    	this.fitness.setPriority(priority); 
    	return this;
    }

    @Override
    public FitnessBuilder addTime(int time) {
    	this.fitness.setTime(time); 
    	return this;
    }
}
