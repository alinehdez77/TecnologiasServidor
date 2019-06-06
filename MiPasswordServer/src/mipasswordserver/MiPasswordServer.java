/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mipasswordserver;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import mipasswordinterface.Boveda;
import mipasswordinterface.Llave;
import mipasswordinterface.Login;
import mipasswordinterface.MPInterface;
import mipasswordinterface.Usuario;

/**
 *
 * @author texch
 */
public class MiPasswordServer extends UnicastRemoteObject implements MPInterface {

  Connection conexion = null;
  private static final long serialVersionUID = 9090898209349823403L;
  private final int PORT = 5254;

  public MiPasswordServer() throws RemoteException {
    iniciarServer();
    ConnectDatabase();

  }

  public void iniciarServer() {
    try {

      String direccion = (InetAddress.getLocalHost().toString());
      System.out.println("Iniciando servidor en " + direccion + ":" + PORT);
      Registry registro = LocateRegistry.createRegistry(PORT);
      registro.bind("MiPasswordServer", (MPInterface) this);
      System.out.println("Servidor iniciado");

    } catch (UnknownHostException ex) {
      Logger.getLogger(MiPasswordServer.class.getName()).log(Level.SEVERE, null, ex);
    } catch (RemoteException ex) {
      Logger.getLogger(MiPasswordServer.class.getName()).log(Level.SEVERE, null, ex);
    } catch (AlreadyBoundException ex) {
      Logger.getLogger(MiPasswordServer.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) throws RemoteException {
    // TODO code application logic here
    MiPasswordServer server = new MiPasswordServer();
  }

  @Override
  public Usuario getUsuario(String correo, String pass) throws RemoteException {
    Usuario user = null;
    try {

      
      PreparedStatement st;
      st = conexion.prepareStatement("select * from usuario where correo = ?");
      st.setString(1, correo);
      ResultSet resultadoQuery = st.executeQuery();

      if (resultadoQuery.next()) {
        String usuario = resultadoQuery.getString("username");
        String nom = (resultadoQuery.getString("nombre"));
        String password = (resultadoQuery.getString("password"));
        String apellido = resultadoQuery.getString("apellido");
        String telefono = resultadoQuery.getString("telefono");
        String correoE = resultadoQuery.getString("correo");
        user = new Usuario(usuario, nom, apellido, telefono, password, correoE);
        
      }
    } catch (SQLException ex) {
      Logger.getLogger(MiPasswordServer.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return user;
  }

  @Override
  public void registrarUsuario(Usuario usuario) throws RemoteException {
    
    try {
      PreparedStatement stp;
      stp = conexion.prepareStatement("insert into usuario values(?,?,?,?,?,?,?,?)");
      stp.setString(1, usuario.getUsername());
      stp.setString(2, usuario.getNombre());
      stp.setString(3, usuario.getApellido());
     stp.setString(4, usuario.getTelefono());
     stp.setString(5, usuario.getCorreo());
     stp.setString(6, usuario.getPassword());
     stp.setString(7, usuario.getClavePrivada());
     stp.setString(8, usuario.getClavePublica());
     
      stp.executeUpdate();

    } catch (SQLException ex) {
      Logger.getLogger(MiPasswordServer.class.getName()).log(Level.SEVERE, null, ex);
    }
    
  }

  @Override
  public Usuario LogIn(Login login) throws RemoteException {
    Usuario u = null;
    try {

      System.out.println(login.correo);
      PreparedStatement st;
      st = conexion.prepareStatement("select * from usuario where correo = ? and password = ?");
      st.setString(1, login.correo);
      st.setString(2, login.password);
      ResultSet resultadoQuery = st.executeQuery();

      if (resultadoQuery.next()) {
        String user = resultadoQuery.getString("username");
        String nom = (resultadoQuery.getString("nombre"));
        String pass = (resultadoQuery.getString("password"));
        String apellido = resultadoQuery.getString("apellido");
        String telefono = resultadoQuery.getString("telefono");
        String correo = resultadoQuery.getString("correo");
        u = new Usuario(user, nom, apellido, telefono, pass, correo);
        System.out.println(u.toString());
      }
    } catch (SQLException ex) {
      Logger.getLogger(MiPasswordServer.class.getName()).log(Level.SEVERE, null, ex);
    }

    return u;
  }

  @Override
  public void editarUsuario(Usuario usuario) throws RemoteException {
    try {
      PreparedStatement st;
      st = conexion.prepareStatement("UPDATE usuario SET nombre = ?, correo = ?, telefono = ?, "
          + "apellido = ? where username = ?");
      st.setString(1, usuario.getNombre());
      st.setString(2, usuario.getCorreo());
      st.setString(3, usuario.getTelefono());
      st.setString(4, usuario.getApellido());
      st.setString(5, usuario.getUsername());
      st.executeUpdate();

    } catch (SQLException ex) {
      Logger.getLogger(MiPasswordServer.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  @Override
  public ArrayList<Boveda> getAllBovedas(String username) throws RemoteException {
    ArrayList<Boveda> bovedas = new ArrayList<>();
    Usuario u = null;
    try {

      PreparedStatement state = conexion.prepareStatement("select * from usuario where username = ?");
      state.setString(1, username);
      ResultSet rsQ = state.executeQuery();
      if (rsQ.next()) {
        String user = rsQ.getString("username");
        String nom = (rsQ.getString("nombre"));
        String pass = (rsQ.getString("password"));
        String apellido = rsQ.getString("apellido");
        String telefono = rsQ.getString("telefono");
        String correo = rsQ.getString("correo");
        u = new Usuario(user, nom, apellido, telefono, pass, correo);
      }

      PreparedStatement st;
      st = conexion.prepareStatement("select * from boveda where username = ?");
      st.setString(1, username);
      ResultSet resultadoQuery = st.executeQuery();
      while (resultadoQuery.next()) {
        String nombre = resultadoQuery.getString("nombreBoveda");
        int idBoveda = (resultadoQuery.getInt("idBoveda"));

        Boveda b = new Boveda(nombre, u, idBoveda);
        System.out.println("B: " + b.toString());
        bovedas.add(b);

      }
    } catch (SQLException ex) {
      Logger.getLogger(MiPasswordServer.class.getName()).log(Level.SEVERE, null, ex);
    }

    return bovedas;
  }

  @Override
  public void agregarBoveda(Boveda boveda) throws RemoteException {

    try {
      PreparedStatement stp = null;
      stp = conexion.prepareStatement("insert into boveda values(?,?,?)");
      stp.setString(1, boveda.getNombre());
      stp.setInt(2, (new Random()).nextInt());
      stp.setString(3, boveda.getOwner().getUsername());

      stp.executeUpdate();

    } catch (SQLException ex) {
      Logger.getLogger(MiPasswordServer.class.getName()).log(Level.SEVERE, null, ex);
    }

  }

  @Override
  public Boveda getBoveda(Boveda bov) throws RemoteException {
    Boveda boveda = null;
    Usuario u = null;
    try {

      PreparedStatement state = conexion.prepareStatement("select * from usuario where username = ?");
      state.setString(1, bov.getOwner().getUsername());
      ResultSet rsQ = state.executeQuery();
      if (rsQ.next()) {
        String user = rsQ.getString("username");
        String nom = (rsQ.getString("nombre"));
        String pass = (rsQ.getString("password"));
        String apellido = rsQ.getString("apellido");
        String telefono = rsQ.getString("telefono");
        String correo = rsQ.getString("correo");
        u = new Usuario(user, nom, apellido, telefono, pass, correo);
      }

      PreparedStatement st;
      st = conexion.prepareStatement("select * from boveda where idBoveda = ?");
      st.setInt(1, bov.getId());
      ResultSet resultadoQuery = st.executeQuery();
      if (resultadoQuery.next()) {
        String nombre = resultadoQuery.getString("nombreBoveda");
        int idBoveda = (resultadoQuery.getInt("idBoveda"));

        boveda = new Boveda(nombre, u);
      }
    } catch (Exception ex) {
      System.err.println(ex.getCause());

    }
    return boveda;
  }

  @Override
  public void editarBoveda(Boveda boveda) throws RemoteException {

    try {
      PreparedStatement st;
      st = conexion.prepareStatement("UPDATE boveda SET nombreBoveda = ? where idBoveda = ?");
      st.setString(1, boveda.getNombre());
      st.setInt(2, boveda.getId());
      st.executeUpdate();

    } catch (SQLException ex) {
      Logger.getLogger(MiPasswordServer.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  @Override
  public void eliminarBoveda(Boveda boveda) throws RemoteException {
    try {
      PreparedStatement st;
      st = conexion.prepareStatement("delete boveda from boveda where idBoveda = ?");
      st.setInt(1, boveda.getId());
      st.executeUpdate();
    } catch (SQLException ex) {
      Logger.getLogger(MiPasswordServer.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  @Override
  public ArrayList<Llave> getAllLlaves(Boveda boveda) throws RemoteException {
    ArrayList<Llave> llaves = new ArrayList<>();
    Boveda b = null;
    Usuario u = null;
    try {

      PreparedStatement sta = conexion.prepareStatement("select * from usuario where username = ?");
      sta.setString(1, boveda.getOwner().getUsername());
      ResultSet rsQ = sta.executeQuery();
      if (rsQ.next()) {
        String user = rsQ.getString("username");
        String nom = (rsQ.getString("nombre"));
        String pass = (rsQ.getString("password"));
        String apellido = rsQ.getString("apellido");
        String telefono = rsQ.getString("telefono");
        String correo = rsQ.getString("correo");
        u = new Usuario(user, nom, apellido, telefono, pass, correo);
      }

      PreparedStatement st;
      st = conexion.prepareStatement("select * from llave where idBoveda = ?");
      st.setInt(1, boveda.getId());
      ResultSet resultadoQuery = st.executeQuery();

      while (resultadoQuery.next()) {
        String nombre = resultadoQuery.getString("nombreLlave");
        String url = resultadoQuery.getString("url");
        String password = resultadoQuery.getString("password");
        String username = resultadoQuery.getString("username");
        int id = resultadoQuery.getInt("idLlave");

        Llave key = new Llave(nombre, url, username, password, b, id);

        llaves.add(key);

      }
    } catch (SQLException ex) {
      Logger.getLogger(MiPasswordServer.class.getName()).log(Level.SEVERE, null, ex);
    }

    return llaves;
  }

  @Override
  public void agregarLlave(Llave llave) throws RemoteException {
     try {
      PreparedStatement stp = null;
      stp = conexion.prepareStatement("insert into llave values(?,?,?,?,?,?)");
      stp.setString(1, llave.getNombre());
      stp.setString(2, llave.getUrl());
      stp.setString(3, llave.getUsername());
      stp.setString(4, llave.getPassword());
      stp.setInt(5, llave.getBoveda().getId());
      stp.setInt(6, llave.getId());

      stp.executeUpdate();

    } catch (SQLException ex) {
      Logger.getLogger(MiPasswordServer.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  @Override
  public Llave getLlave(Llave llave) throws RemoteException {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public void editarLlave(Llave llave) throws RemoteException {
    
    try {
      PreparedStatement st;
      st = conexion.prepareStatement("UPDATE llave SET nombreLlave = ?, url = ?, username = ?, password = ?"
          + " where idLlave = ?");
      st.setString(1, llave.getNombre());
      st.setString(2, llave.getUrl());
      st.setString(3, llave.getUsername());
      st.setString(4, llave.getPassword());
      st.setInt(5, llave.getId());
      
      st.executeUpdate();

    } catch (SQLException ex) {
      Logger.getLogger(MiPasswordServer.class.getName()).log(Level.SEVERE, null, ex);
    }
    
  }

  @Override
  public void eliminarLlave(Llave llave) throws RemoteException {
    
    try {
      PreparedStatement st;
      st = conexion.prepareStatement("delete llave from llave where idLlave = ?");
      st.setInt(1, llave.getId());
      st.executeUpdate();
    } catch (SQLException ex) {
      Logger.getLogger(MiPasswordServer.class.getName()).log(Level.SEVERE, null, ex);
    }
    
  }

  @Override
  public void ConnectDatabase() throws RemoteException {
    conexion = ConnectionDB.conectar();

  }

}
