package nativelevel.Equipment.Generator;

import nativelevel.Equipment.Atributo;

/**
 *
 * @author Ziden
 * 
 */

public class Mod {

    private String [] desc;
    private Atributo attr;
    
    public Mod(String [] desc, Atributo attr) {
        this.desc = desc;
        this.attr = attr;
    }

    public String[] getDesc() {
        return desc;
    }

    public void setDesc(String[] desc) {
        this.desc = desc;
    }

    public Atributo getAttr() {
        return attr;
    }

    public void setAttr(Atributo attr) {
        this.attr = attr;
    }
    
    
    
    
}
