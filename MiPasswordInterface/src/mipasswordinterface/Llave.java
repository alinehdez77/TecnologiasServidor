/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mipasswordinterface;

import java.io.Serializable;

/**
 *
 * @author Monse
 */
public class Llave implements Serializable{
  
  private static final long serialVersionUID = 1L;
   private int id;
  private String nombre;
  private String url;
  private String username;
  private String password;
  private Boveda boveda;
  
  /*
  *Constructor vacio de la clase.
  */
  public Llave() {
    
  }
  
  /*
  *Constructor de la clase.
  */
  public Llave(String nombre,String url, String username, String password, Boveda boveda, int id) {
    this.nombre = nombre;
    this.url = url;
    this.username = username;
    this.password = password;
    this.boveda = boveda;
    this.id = id;
  }
  
  @Override
  public String toString(){
    return nombre + " - " + username + " - " + password;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Boveda getBoveda() {
    return boveda;
  }

  public void setBoveda(Boveda boveda) {
    this.boveda = boveda;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }
  
  
}
