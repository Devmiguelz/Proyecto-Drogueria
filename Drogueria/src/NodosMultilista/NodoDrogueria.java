/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Miguel
 */
public class NodoDrogueria {
    
    public NodoDrogueria ant;
    public int codigo;
    public String nombre;
    public NodoHijoDrogueria hijo;
    public NodoDrogueria sig;
    
    public NodoDrogueria(){
        sig = null;
        ant = null;
    }
    
    public NodoDrogueria(NodoDrogueria r){
        sig = null;
        codigo = r.codigo;
        nombre = r.nombre;
        hijo = null;
        ant = null;
    }
    
}
