/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package risk.controller;

import java.util.ArrayList;
import org.hibernate.Criteria;
import risk.builder.ProjectBuilder;
import risk.entity.Projects;
import risk.ui.Main;
import static risk.ui.Main.session;
import static risk.ui.Main.tx;

/**
 *
 * @author nguyennam
 */
public class ProjectController {
    
    public static void create(Projects tempProject){
        
            tx = session.beginTransaction();
            session.save(tempProject);
            tx.commit();
    }
    
    public static ArrayList<Projects> index(){
        Criteria projectCr = Main.session.createCriteria(Projects.class);
        ArrayList<Projects> projects = (ArrayList<Projects>) projectCr.list();   
        return projects;
    }
    
    public static Projects show(int id){
        Projects project = (Projects) Main.session.get(Projects.class,id);
        return project;
    }
    
    public static Projects update(Projects tempProject){
        
        Main.tx = session.beginTransaction();
        Projects project = ProjectController.show(tempProject.getId());
        project.setCode(tempProject.getCode());
        project.setName(tempProject.getName());
        project.setDescription(tempProject.getDescription());
        project.setFinished(tempProject.getFinished());
        session.update(project);
        Main.tx.commit();
        return project;
    }
    
    
    public static void delete(int id){
        tx = session.beginTransaction();
        Projects project = (Projects) session.load(Projects.class, id);
        session.delete(project);
        tx.commit();
    }
}
