/*
 * To change this license header, choose License Headers in Method Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package risk.controller;

import java.util.ArrayList;
import org.hibernate.Criteria;
import risk.entity.Methods;
import risk.ui.Main;
import static risk.ui.Main.session;
import static risk.ui.Main.tx;

/**
 *
 * @author nguyennam
 */
public class MethodController {
    
    public static void create(Methods tempMethod){
        
            tx = session.beginTransaction();
            session.save(tempMethod);
            tx.commit();
    }
    
    public static ArrayList<Methods> index(){
        Criteria methodCr = Main.session.createCriteria(Methods.class);
        ArrayList<Methods> methods = (ArrayList<Methods>) methodCr.list();   
        return methods;
    }
    
    public static Methods show(int id){
        Methods method = (Methods) Main.session.get(Methods.class,id);
        return method;
    }
    
    public static Methods update(Methods tempMethod){
        
        Main.tx = session.beginTransaction();
        Methods method = MethodController.show(tempMethod.getId());
        method.setCode(tempMethod.getCode());
        method.setName(tempMethod.getName());
        method.setDescription(tempMethod.getDescription());
        method.setCost(tempMethod.getCost());
        method.setDiff(tempMethod.getDiff());
        method.setPriority(tempMethod.getPriority());
        method.setTime(tempMethod.getTime());
        method.setEfficiency(tempMethod.getEfficiency());
        method.setRisks(tempMethod.getRisks());
        session.update(method);
        Main.tx.commit();
        return method;
    }
    
    
    public static void delete(int id){
        tx = session.beginTransaction();    
        Methods method = (Methods) session.load(Methods.class, id);
        session.delete(method);
        tx.commit();
    }
    
}
