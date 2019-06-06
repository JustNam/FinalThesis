/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package risk.builder;

import risk.entity.Methods;
import risk.entity.Risks;

/**
 *
 * @author nguyennam
 */
public class MethodBuilder extends AbstractMethodBuilder<MethodBuilder, Methods>{

    public MethodBuilder(){
        this.method = new Methods();
    }
    
    @Override
    public MethodBuilder addId(int id) {
        this.method.setId(id);
        return this;
    }

    @Override
    public MethodBuilder addCode(String code) {
        this.method.setCode(code);
        return this;
    }

    @Override
    public MethodBuilder addName(String name) {
        this.method.setName(name);
        return this;
    }

    @Override
    public MethodBuilder addDescription(String description) {
        this.method.setDescription(description);
        return this;
    }

    @Override
    public MethodBuilder addCost(int cost) {
        this.method.setCost(cost);
        return this;
    }

    @Override
    public MethodBuilder addDiff(int diff) {
        this.method.setDiff(diff);
        return this;
    }

    @Override
    public MethodBuilder addTime(int time) {
        this.method.setTime(time);
        return this;
    }

    @Override
    public MethodBuilder addPriority(int priority) {
        this.method.setPriority(priority);
        return this;
    }

    @Override
    public MethodBuilder addEfficiency(int efficiency) {
        this.method.setEfficiency(efficiency);
        return this;
    }

    @Override
    public MethodBuilder addRisk(Risks risk) {
        this.method.setRisks(risk);
        return this;
    }
    
}
