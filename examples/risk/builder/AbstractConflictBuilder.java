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
public abstract class AbstractConflictBuilder <T extends AbstractConflictBuilder<T,C>,C extends Conflicts>{
    protected C conflict;
    public abstract T addId(int id);
    public abstract T addMethod1(Methods method);
    public abstract T addMethod2(Methods method);
    public abstract T addCode(String code);
    public abstract T addProject(Projects project);
    public C build(){
        return this.conflict;
    }
}
