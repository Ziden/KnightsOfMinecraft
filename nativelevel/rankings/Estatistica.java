/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.rankings;

/**
 *
 * @author vntgasl
 */
public enum Estatistica {

    // adiciona as porra tudo aqui 
    // TODOS PLAYER PODE TER TUDO SASPORRA

    MOB_KILLS("Heroi"),
    BUILDER("Construtor"),
    MINERADOR("Minerador"),
    FERREIRO("Ferreiro"),
    FAZENDEIRO("Fazendeiro"),
    LENHADOR("Lenhador"),
    ALQUIMISTA("Alquimista"),
    DOMINADOR("Poderoso"),
    AJUDANTE("Ajudante"),
    ARENA("Gladiador"),
    DUNGEONS("Explorador");
    
    
    public String titulo;
    
    private Estatistica(String titulo) {
        this.titulo = titulo;
    }

}
