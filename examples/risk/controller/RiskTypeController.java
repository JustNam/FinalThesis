/*
 * To change this license header, choose License Headers in Risk Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package risk.controller;

import java.util.ArrayList;
import org.hibernate.Criteria;
import risk.entity.RiskTypes;
import risk.entity.Risks;
import risk.ui.Main;
import static risk.ui.Main.session;
import static risk.ui.Main.tx;

/**
 *
 * @author nguyennam
 */
public class RiskTypeController {
        
    public static void create(RiskTypes tempRiskType){
        
            tx = session.beginTransaction();
            session.save(tempRiskType);
            tx.commit();
    }
    
    public static ArrayList<RiskTypes> index(){
        Criteria riskCr = Main.session.createCriteria(RiskTypes.class);
        ArrayList<RiskTypes> risks = (ArrayList<RiskTypes>) riskCr.list();   
        return risks;
    }
    
    public static RiskTypes show(int id){
        RiskTypes risk = (RiskTypes) Main.session.get(RiskTypes.class,id);
        return risk;
    }
    
    public static RiskTypes update(RiskTypes tempRiskType){
        
        Main.tx = session.beginTransaction();
        RiskTypes risk = RiskTypeController.show(tempRiskType.getId());
        risk.setCode(tempRiskType.getCode());
        risk.setName(tempRiskType.getName());
        risk.setDescription(tempRiskType.getDescription());
        session.update(risk);
        Main.tx.commit();
        return risk;
    }
    
    
    public static void delete(int id){
        tx = session.beginTransaction();
        RiskTypes risk = (RiskTypes) session.load(RiskTypes.class, id);
        session.delete(risk);
        tx.commit();
    }
}
