/*
 * To change this license header, choose License Headers in Conflict Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package risk.controller;

import java.util.ArrayList;
import org.hibernate.Criteria;
import risk.entity.Conflicts;
import risk.ui.Main;
import static risk.ui.Main.session;
import static risk.ui.Main.tx;

/**
 *
 * @author nguyennam
 */
public class ConflictController {
    
    public static void create(Conflicts tempConflict){
        
            tx = session.beginTransaction();
            session.save(tempConflict);
            tx.commit();
    }
    
    public static ArrayList<Conflicts> index(){
        Criteria conflictCr = Main.session.createCriteria(Conflicts.class);
        ArrayList<Conflicts> conflicts = (ArrayList<Conflicts>) conflictCr.list();   
        return conflicts;
    }
    
    public static Conflicts show(int id){
        Conflicts conflict = (Conflicts) Main.session.get(Conflicts.class,id);
        return conflict;
    }
    
    public static Conflicts update(Conflicts tempConflict){
        Main.tx = session.beginTransaction();
        Conflicts conflict = ConflictController.show(tempConflict.getId());
        conflict.setCode(tempConflict.getCode());
        session.update(conflict);
        Main.tx.commit();
        return conflict;
    }
    
    
    public static void delete(int id){
        tx = session.beginTransaction();
        Conflicts conflict = (Conflicts) session.load(Conflicts.class, id);
        session.delete(conflict);
        tx.commit();
    }
}
