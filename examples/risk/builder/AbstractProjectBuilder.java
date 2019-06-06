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
 * @param <T>
 */
public abstract class AbstractProjectBuilder <T extends AbstractProjectBuilder<T,P>,P extends Projects>{
    protected P project;
    public abstract T addId(int id);
    public abstract T addCode(String code);
    public abstract T addName(String name);
    public abstract T addDescription(String description);
    public abstract T addFinished(int finished);
    public P build(){
        return this.project;
    }
}
