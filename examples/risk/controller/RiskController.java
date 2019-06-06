/*
 * To change this license header, choose License Headers in Risk Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package risk.controller;

import java.util.ArrayList;
import org.hibernate.Criteria;
import risk.entity.Risks;
import risk.ui.Main;
import static risk.ui.Main.session;
import static risk.ui.Main.tx;

/**
 *
 * @author nguyennam
 */
public class RiskController {
        
    public static void create(Risks tempRisk){
        
            tx = session.beginTransaction();
            session.save(tempRisk);
            tx.commit();
    }
    
    public static ArrayList<Risks> index(){
        Criteria riskCr = Main.session.createCriteria(Risks.class);
        ArrayList<Risks> risks = (ArrayList<Risks>) riskCr.list();   
        return risks;
    }
    
    public static Risks show(int id){
        Risks risk = (Risks) Main.session.get(Risks.class,id);
        return risk;
    }
    
    public static Risks update(Risks tempRisk){
        
        Main.tx = session.beginTransaction();
        Risks risk = RiskController.show(tempRisk.getId());
        risk.setCode(tempRisk.getCode());
        risk.setProjects(tempRisk.getProjects());
        risk.setRiskTypes(tempRisk.getRiskTypes());
        risk.setName(tempRisk.getName());
        risk.setDescription(tempRisk.getDescription());
        risk.setFinancialImpact(tempRisk.getFinancialImpact());
        risk.setRiskLevel(tempRisk.getRiskLevel());
        risk.setDescription(tempRisk.getDescription());
        risk.setMethodses(tempRisk.getMethodses());
        session.update(risk);
        Main.tx.commit();
        return risk;
    }
    
    
    public static void delete(int id){
        tx = session.beginTransaction();
        Risks risk = (Risks) session.load(Risks.class, id);
        session.delete(risk);
        tx.commit();
    }
}
