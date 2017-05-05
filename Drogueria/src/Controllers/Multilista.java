/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Model.NodoDrogueria;
import Model.NodoHijoDrogueria;
import javax.swing.JOptionPane;

/**
 *
 * @author Miguel
 */
public class Multilista {
    
    public NodoDrogueria raiz;
    
    public Multilista(){
        raiz = null;
    }
    
    public NodoHijoDrogueria UltimoHijo(NodoHijoDrogueria lista) {
        NodoHijoDrogueria temp = lista;
        while (temp != null) {
            if (temp.sig == null) {
                return temp;
            }
            temp = temp.sig;
        }
        return null;
    }
    
    public NodoDrogueria BuscarPadre(int padrecodigo) {
        NodoDrogueria temp = raiz;
        while (temp != null) {
            if (temp.codigo == padrecodigo) {
                return temp;
            }
            temp = temp.sig;
        }
        return null;
    }
    
    public NodoHijoDrogueria BuscarHijo(int padrecodigo,int hijocodigo) {
        NodoDrogueria buscar = BuscarPadre(padrecodigo);
        NodoHijoDrogueria q;
        if (buscar != null) {
            q = buscar.hijo;
            while (q != null) {
                if (q.codigo == hijocodigo) {
                    return q;
                }
                q = q.sig;
            }
        }
        return null;
    }
    
    public NodoHijoDrogueria Anterior(int padrecodigo,int hijocodigo) {
        NodoDrogueria buscar = BuscarPadre(padrecodigo);
        NodoHijoDrogueria q;
        NodoHijoDrogueria ant = null;
        if (buscar != null) {
            q = buscar.hijo;
            while (q != null) {
                if (q.codigo == hijocodigo) {
                    return ant;
                }
                ant = q;
                q = q.sig;
            }
        }
        return null;
    }
    
    public NodoDrogueria UltimoPadre() {
        NodoDrogueria temp = raiz;
        while (temp != null) {
            if (temp.sig == null) {
                return temp;
            }
            temp = temp.sig;
        }
        return null;
    }
    
    public void InsertarPadre(NodoDrogueria dato) {
        NodoDrogueria ultimo = UltimoPadre();
        NodoDrogueria nuevo = new NodoDrogueria(dato);
        if (ultimo != null) {
            ultimo.sig = nuevo;
            nuevo.ant = ultimo;
        } else {
            raiz = nuevo;
        }
    }
    
    public void InsertarHijo(int codigo, NodoHijoDrogueria hijo) {
        NodoDrogueria padre = BuscarPadre(codigo);
        if (padre != null) {
            NodoHijoDrogueria ultimo = UltimoHijo(padre.hijo);
            NodoHijoDrogueria nuevo = new NodoHijoDrogueria(hijo);
            if (ultimo != null) {
                ultimo.sig = nuevo;
            } else {
                padre.hijo = nuevo;
            }
        } else {
            JOptionPane.showMessageDialog(null, "No existe Padre ");
        }
    }
    
    public void Eliminar(int padrecodigo, int hijocodigo) {
        NodoDrogueria buscar = BuscarPadre(padrecodigo);
        NodoHijoDrogueria q = BuscarHijo(padrecodigo, hijocodigo);
        NodoHijoDrogueria ant = Anterior(padrecodigo, hijocodigo);
        if (buscar != null) {
            if (buscar.hijo == q) {
                buscar.hijo = q.sig;
            }else{
                ant.sig = q.sig;
            }
        } else {
            JOptionPane.showMessageDialog(null, "Error no existe el Padre");
        }
    }
    
}
