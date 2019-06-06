/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mipasswordinterface;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 *
 * @author Monse
 */
public interface MPInterface extends Remote {
  
  public void ConnectDatabase() throws  RemoteException;
  
  
  public Usuario getUsuario(String correo, String pass) throws RemoteException;
  public Usuario LogIn(Login login)throws RemoteException;
  public void registrarUsuario(Usuario usuario) throws RemoteException;
  public void editarUsuario(Usuario usuario) throws RemoteException;
  
  public ArrayList<Boveda> getAllBovedas(String username)throws RemoteException;
  public Boveda getBoveda(Boveda boveda) throws RemoteException;
  public void agregarBoveda(Boveda boveda) throws RemoteException;
  public void editarBoveda(Boveda boveda) throws RemoteException;
  public void eliminarBoveda(Boveda boveda) throws RemoteException;
  
  public ArrayList<Llave> getAllLlaves(Boveda boveda) throws RemoteException;
  public void agregarLlave(Llave llave)throws RemoteException;
  public Llave getLlave(Llave llave) throws RemoteException;
  public void editarLlave(Llave llave) throws RemoteException;
  public void eliminarLlave(Llave llave) throws RemoteException;
  
  
  
  
  
}
