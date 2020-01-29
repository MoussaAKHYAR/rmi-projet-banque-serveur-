package sn.isi.controller;
import sn.isi.entities.Client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class ClientDB extends UnicastRemoteObject implements IClient {
	private DB db = new DB();
	//private int ok;
	private ResultSet rs;

	public ClientDB() throws RemoteException {
	}

	@Override
	public int add(Client c) throws RemoteException {
		int ok=0;
		String sql = "INSERT INTO client VALUES(null,?,?,?,?,?,?,?,?)";
		try {

			db.initPrepar(sql);
			/*passage de valeur*/
			db.getPstm().setString(1, c.getNom());
			db.getPstm().setString(2, c.getPrenom());
			db.getPstm().setString(3, c.getAdress());
			db.getPstm().setString(4, c.getEmail());
			db.getPstm().setString(5, c.getTelephone());
			db.getPstm().setString(6, c.getProfession());
			db.getPstm().setString(7, c.getSalaire());
			db.getPstm().setInt(8, c.getNumero().getNumero());

			//execution de la requete
			ok = db.executeMaj();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ok;
	}

	@Override
	public int delete(int idclient) throws RemoteException{
		int ok=0;
		String sql = "DELETE FROM client where idclient=?";
		try {

			db.initPrepar(sql);
			db.getPstm().setInt(1,idclient);
			ok = db.executeMaj();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ok;
	}

	@Override
	public int update(Client c) throws RemoteException {
		int ok=0;
		String sql = "update client set nom=? ,prenom=? , adress=? ,email=? , telephone=? ,profession=? , salaire=? ,numero=? where idclient=?";
		try {

			db.initPrepar(sql);
			db.getPstm().setString(1, c.getNom());
			db.getPstm().setString(2, c.getPrenom());
			db.getPstm().setString(3, c.getAdress());
			db.getPstm().setString(4, c.getEmail());
			db.getPstm().setString(5, c.getTelephone());
			db.getPstm().setString(6, c.getProfession());
			db.getPstm().setString(7, c.getSalaire());
			db.getPstm().setInt(8, c.getNumero().getNumero());
			db.getPstm().setInt(9, c.getIdclient());
			ok = db.executeMaj();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ok;
	}

	@Override
	public List<Client> liste() throws RemoteException {
		String sql="select * from client";
		List<Client> co = new ArrayList<Client>();
		try {
				db.initPrepar(sql);
				rs=db.executeSelect();
				while(rs.next()){
					Client c= new Client();

					c.setIdclient(rs.getInt(1));
					c.setNom(rs.getString(2));
					c.setPrenom(rs.getString(3));
					c.setAdress(rs.getString(4));
					c.setEmail(rs.getString(5));
					c.setTelephone(rs.getString(6));
					c.setProfession(rs.getString(7));
					c.setSalaire(rs.getString(8));
					c.setNumero(new EmployeurDB().get((rs.getInt(9))));

					co.add(c);
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return co;
	}

	@Override
	public Client get(int idclient) throws RemoteException {
		String sql="select * from client where idclient=?";
	    Client c=null;
		try {
				db.initPrepar(sql);
				db.getPstm().setInt(1,idclient);
				rs=db.executeSelect();
				if(rs.next()){

					c= new Client();
					c.setIdclient(rs.getInt(1));
					c.setNom(rs.getString(2));
					c.setPrenom(rs.getString(3));
					c.setAdress(rs.getString(4));
					c.setEmail(rs.getString(5));
					c.setTelephone(rs.getString(6));
					c.setProfession(rs.getString(7));
					c.setSalaire(rs.getString(8));
					c.setNumero(new EmployeurDB().get(rs.getInt(9)));

				}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return c;
	}

}
