/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package procurement.builder;

import java.util.Set;
import procurement.entity.risk.Methods;
import procurement.entity.risk.Projects;
import procurement.entity.risk.RiskTypes;
import procurement.entity.risk.Risks;

/**
 *
 * @author nguyennam
 */
public class RiskTypeBuilder extends AbstractRiskTypeBuilder<RiskTypeBuilder, RiskTypes>{

    public RiskTypeBuilder() {
        riskType = new RiskTypes();
    }
    
    @Override
    public RiskTypeBuilder addId(int id) {
        this.riskType.setId(id);
        return this;
    }
    
    @Override
    public RiskTypeBuilder addCode(String code) {
        this.riskType.setCode(code);
        return this;
    }

    @Override
    public RiskTypeBuilder addName(String name) {
        this.riskType.setName(name);
        return this;
    }

    @Override
    public RiskTypeBuilder addDescription(String description) {
        this.riskType.setDescription(description);
        return this;
    }
}
