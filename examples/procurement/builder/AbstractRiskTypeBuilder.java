package procurement.builder;

import java.util.Set;
import procurement.entity.risk.Methods;
import procurement.entity.risk.Projects;
import procurement.entity.risk.RiskTypes;
import procurement.entity.risk.Risks;

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
public abstract class AbstractRiskTypeBuilder <T extends AbstractRiskTypeBuilder<T,R>,R extends RiskTypes>{
    protected R riskType;
    public abstract T addId(int id);
    public abstract T addCode(String code);
    public abstract T addName(String name);
    public abstract T addDescription(String description);
    public R build(){
        return this.riskType;
    }
}