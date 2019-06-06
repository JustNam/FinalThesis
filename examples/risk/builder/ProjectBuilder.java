/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package risk.builder;

import risk.entity.Projects;

/**
 *
 * @author nguyennam
 */
public class ProjectBuilder extends AbstractProjectBuilder<ProjectBuilder, Projects> {

    public ProjectBuilder(){
        project = new Projects();
    }
    
    @Override
    public ProjectBuilder addId(int id) {
        this.project.setId(id);
        return this;
    }
   
    @Override
    public ProjectBuilder addCode(String code) {
        this.project.setCode(code);
        return this;
    }

    @Override
    public ProjectBuilder addName(String name) {
        this.project.setName(name);
        return this;
    }

    @Override
    public ProjectBuilder addDescription(String description) {
        this.project.setDescription(description);
        return this;
    }

    @Override
    public ProjectBuilder addFinished(int finished) {
        this.project.setFinished(finished);
        return this;
    }

    
    
}
