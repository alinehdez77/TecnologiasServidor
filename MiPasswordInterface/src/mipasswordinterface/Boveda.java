/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mipasswordinterface;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Monse
 */
public class Boveda implements Serializable{
  
  private static final long serialVersionUID = 1L;
  
  private int id;
  private String nombre;
  private Usuario owner;
  private List<Llave> llaves;
  
  public Boveda() {
    
  }
  
  public Boveda(String nombre, Usuario owner) {
    llaves = new ArrayList<>();
    this.nombre = nombre;
    this.owner = owner;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public Usuario getOwner() {
    return owner;
  }

  public void setOwner(Usuario owner) {
    this.owner = owner;
  }

  public List<Llave> getLlaves() {
    return llaves;
  }
   
  public int getId(){
    return id;
  }
   @Override
   public String toString(){
     return nombre;
   }
  
}
