package sn.isi.controller;

import sn.isi.entities.Employeur;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EmployeurDB extends UnicastRemoteObject implements  IEmployeur{
	private DB db = new DB();
	//private int ok;
	private ResultSet rs;

	public EmployeurDB() throws RemoteException {
	}

	@Override
	public int add(Employeur e) throws RemoteException {
		int ok=0;
		String sql = "INSERT INTO employeur VALUES(null,?,?,?)";
		try {

			db.initPrepar(sql);

			db.getPstm().setString(1, e.getNomemployeur());
			db.getPstm().setString(2, e.getAdresse());
			db.getPstm().setString(3, e.getRaisonsocial());
			//execution de la requete
			ok = db.executeMaj();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ok;
	}

	@Override
	public int delete(int numero) throws RemoteException {
		String sql= "DELETE  from employeur where numero=?";
	int	ok=0;
		try {
			db.initPrepar(sql);
			db.getPstm().setInt(1,numero);
			ok=db.executeMaj();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ok;
	}

	@Override
	public int update(Employeur e) throws RemoteException {
		String sql= "update employeur set nomemployeur= ? ,adresse=? ,raisonsocial=? where numero =?";
		int ok=0;
		try {
			db.initPrepar(sql);
			db.getPstm().setString(1, e.getNomemployeur());
			db.getPstm().setString(2, e.getAdresse());
			db.getPstm().setString(3, e.getRaisonsocial());
			db.getPstm().setInt(4, e.getNumero());

			ok=db.executeMaj();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ok;
	}

	@Override
	public List<Employeur> liste() throws RemoteException{
		String sql="select * from employeur";
		List<Employeur> co = new ArrayList<Employeur>();
		try {
				db.initPrepar(sql);
				rs=db.executeSelect();
				while(rs.next()){
					Employeur e= new Employeur();
					e.setNumero(rs.getInt(1));
					e.setNomemployeur(rs.getString(2));
					e.setAdresse(rs.getString(3));
					e.setRaisonsocial(rs.getString(4));
					co.add(e);
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return co;
	}

	@Override
	public Employeur get(int numero) throws RemoteException {
		String sql="select * from employeur where numero=?";
		Employeur e=null;
		try {
				db.initPrepar(sql);
				db.getPstm().setInt(1,numero);
				rs=db.executeSelect();
				if(rs.next()){
				e= new Employeur();
				    e.setNumero(rs.getInt(1));
					e.setNomemployeur(rs.getString(2));
					e.setAdresse(rs.getString(3));
					e.setRaisonsocial(rs.getString(4));
				}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return e;
	}

}
