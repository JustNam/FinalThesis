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
public abstract class AbstractMethodBuilder <T extends AbstractMethodBuilder<T,M>,M extends Methods>{
    protected M method;
    public abstract T addId(int id);
    public abstract T addCode(String code);
    public abstract T addName(String name);
    public abstract T addDescription(String description);
    public abstract T addCost(int cost);
    public abstract T addDiff(int diff);
    public abstract T addTime(int time);
    public abstract T addPriority(int priority);
    public abstract T addEfficiency(int efficiency);
    public abstract T addRisk(Risks risk);
    
    public M build(){
        return this.method;
    }
}