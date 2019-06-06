/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package risk.builder;

import java.util.Set;
import risk.entity.Methods;
import risk.entity.Projects;
import risk.entity.RiskTypes;
import risk.entity.Risks;

/**
 *
 * @author nguyennam
 */
public class RiskBuilder extends AbstractRiskBuilder<RiskBuilder, Risks>{

    public RiskBuilder() {
        risk = new Risks();
    }
    
    @Override
    public RiskBuilder addId(int id) {
        this.risk.setId(id);
        return this;
    }

    @Override
    public RiskBuilder addProject(Projects projects) {
        this.risk.setProjects(projects);
        return this;
    }

    @Override
    public RiskBuilder addRiskType(RiskTypes riskTypes) {
        this.risk.setRiskTypes(riskTypes);
        return this;
    }

    @Override
    public RiskBuilder addCode(String code) {
        this.risk.setCode(code);
        return this;
    }

    @Override
    public RiskBuilder addName(String name) {
        this.risk.setName(name);
        return this;
    }

    @Override
    public RiskBuilder addDescription(String description) {
        this.risk.setDescription(description);
        return this;
    }

    @Override
    public RiskBuilder addFinancialImpact(int financialImpact) {
        this.risk.setFinancialImpact(financialImpact);
        return this;
    }

    @Override
    public RiskBuilder addRiskLevel(String riskLevel) {
        this.risk.setRiskLevel(riskLevel);
        return this;
    }

    @Override
    public RiskBuilder addMethods(Set<Methods> methodses) {
        this.risk.setMethodses(methodses);
        return this;
    }

    @Override
    public RiskBuilder addProbability(int probability) {
        this.risk.setProbability(probability);
        return this;
    }
    
}
