package risk.builder;

import java.util.Set;
import risk.entity.Methods;
import risk.entity.Projects;
import risk.entity.RiskTypes;
import risk.entity.Risks;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author nguyennam
 * @param <T>
 * @param <R>
 */
public abstract class AbstractRiskBuilder <T extends AbstractRiskBuilder<T,R>,R extends Risks>{
    protected R risk;
    public abstract T addId(int id);
    public abstract T addProject(Projects projects);
    public abstract T addRiskType(RiskTypes riskTypes);
    public abstract T addCode(String code);
    public abstract T addName(String name);
    public abstract T addDescription(String description);
    public abstract T addFinancialImpact(int financialImpact);
    public abstract T addRiskLevel(String riskLevel);
    public abstract T addProbability(int probability);
    public abstract T addMethods(Set<Methods> methodses);
    public R build(){
        return this.risk;
    }
}