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
public class Login implements Serializable{
  private static final long serialVersionUID = 1L;
  
   public String correo;
  
  public String password;
  
  /*
  *Constructor de la clase.
  */
  public Login(String correo, String password){
    this.correo =  correo;
    this.password = password;
  }
  
}
