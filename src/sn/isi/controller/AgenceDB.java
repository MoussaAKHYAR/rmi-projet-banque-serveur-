package sn.isi.controller;

import sn.isi.entities.Agence;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class AgenceDB extends UnicastRemoteObject implements IAgence {
	private DB db = new DB();
	//private int ok;
	private ResultSet rs;

	public AgenceDB() throws RemoteException {
	}

	@Override
	public int add(Agence a) throws RemoteException{
		int ok=0;
		String sql = "INSERT INTO agence VALUES(null,?,?)";
		try {

			db.initPrepar(sql);
			/*passage de valeur*/
			db.getPstm().setString(1, a.getNomAgence());
			db.getPstm().setString(2, a.getAdress());
		//execution de la requete
			ok = db.executeMaj();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ok;
	}

	@Override
	public int delete(int idA) throws RemoteException {
		int ok=0;
		String sql = "DELETE FROM agence where idA=?";
		try {

			db.initPrepar(sql);
			db.getPstm().setInt(1,idA);
			ok = db.executeMaj();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ok;
	}

	@Override
	public int update(Agence a) throws RemoteException {
		int ok=0;
		String sql = "update agence set nomAgence=? , adress=?  where idA=?";
		try {

			db.initPrepar(sql);
			db.getPstm().setString(1, a.getNomAgence());
			db.getPstm().setString(2, a.getAdress());
			db.getPstm().setInt(3, a.getIdA());
			ok = db.executeMaj();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ok;
	}

	@Override
	public List<Agence> liste() throws RemoteException{
		String sql="select * from agence";
		List<Agence> co = new ArrayList<Agence>();
		try {
				db.initPrepar(sql);
				rs=db.executeSelect();
				while(rs.next()){
					Agence a= new Agence();

					a.setIdA(rs.getInt(1));
					a.setNomAgence(rs.getString(2));
					a.setAdress(rs.getString(3));
					co.add(a);
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return co;
	}

	@Override
	public Agence get(int idA) throws RemoteException{
		String sql="select * from agence where idA=?";
		Agence a=null;
		try {
				db.initPrepar(sql);
				db.getPstm().setInt(1,idA);
				rs=db.executeSelect();
				while(rs.next()){
				a= new Agence();

					a.setIdA(rs.getInt(1));
					a.setNomAgence(rs.getString(2));
					a.setAdress(rs.getString(3));

				}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return a;
	}

}
