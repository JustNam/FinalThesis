/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package risk.builder;

import risk.entity.Conflicts;
import risk.entity.Methods;
import risk.entity.Projects;

/**
 *
 * @author nguyennam
 */
public class ConflictBuilder extends AbstractConflictBuilder<ConflictBuilder, Conflicts>{

    public ConflictBuilder(){
        conflict = new Conflicts();
    }
        
    @Override
    public ConflictBuilder addId(int id) {
        this.conflict.setId(id);
        return this;
    }

    @Override
    public ConflictBuilder addCode(String code) {
        this.conflict.setCode(code);
        return this;
    }

    @Override
    public ConflictBuilder addMethod1(Methods method) {
        this.conflict.setMethodsByMethod1Id(method);
        return this;
    }

    @Override
    public ConflictBuilder addMethod2(Methods method) {
        this.conflict.setMethodsByMethod2Id(method);
        return this;
    }

    @Override
    public ConflictBuilder addProject(Projects project) {
        this.conflict.setProjects(project);
        return this;
    }
    
    
}
