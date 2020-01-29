package sn.isi.controller;

import sn.isi.entities.Compte;
import sn.isi.entities.Comptebloque;
import sn.isi.entities.Comptecourant;
import sn.isi.entities.Compteepargne;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CompteDB extends UnicastRemoteObject implements ICompte {

	private DB db = new DB();
	//private int ok;
	private ResultSet rs;

	public CompteDB() throws RemoteException {
	}

	@Override
	public int delete(String numCpt) throws RemoteException {
		int ok=0;
		String sql = "DELETE FROM compte where numCpt=?";
		try {

			db.initPrepar(sql);
			db.getPstm().setString(1,numCpt);
			ok = db.executeMaj();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ok;
	}

	@Override
	public int update(Compte c) throws RemoteException {
		return 0;

	}

	public int fermer(String numCpt) throws RemoteException {
		String sql= "update compte set etat='Fermer' where numCpt =?";
		System.out.println(sql);
		int ok=0;
		try {

			db.initPrepar(sql);

			db.getPstm().setString(1, numCpt);

			ok=db.executeMaj();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ok;
	}
	public int activer(String numCpt) throws RemoteException{
		String sql= "update compte set etat='Actif' where numCpt =?";
		System.out.println(sql);
		int ok=0;
		try {

			db.initPrepar(sql);

			db.getPstm().setString(1, numCpt);

			ok=db.executeMaj();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ok;
	}
	@Override
	public List<Compte> liste() throws RemoteException{
		String sql="select * from compte";
		List<Compte> co = new ArrayList<Compte>();
		try {
				db.initPrepar(sql);
				rs=db.executeSelect();
				while(rs.next()){
					Compte c= new Compte();
					c.setNumCpt(rs.getString(1));
					c.setDateouverture(rs.getString(2));
					c.setSolde(rs.getInt(3));
					c.setType(rs.getString(4));
					c.setEtat(rs.getString(5));
					c.setIdA(new AgenceDB().get((rs.getInt(6))));
					c.setIdclient(new ClientDB().get((rs.getInt(7))));
					c.setIdemp(new EmployeDB().get((rs.getInt(8))));


					co.add(c);
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return co;
	}

	@Override
	public Compte get(String numC) throws RemoteException {
		String sql="select * from compte where numCpt=?";
		Compte c=null;
		try {

				db.initPrepar(sql);
				db.getPstm().setString(1,numC);
				rs=db.executeSelect();
				while(rs.next()){
					c= new Compte();

					c.setNumCpt(rs.getString(1));
					c.setDateouverture(rs.getString(2));
					c.setSolde(rs.getInt(3));
					c.setType(rs.getString(4));
					c.setEtat(rs.getString(5));
					c.setIdA(new AgenceDB().get((rs.getInt(6))));
					c.setIdclient(new ClientDB().get((rs.getInt(7))));
					c.setIdemp(new EmployeDB().get((rs.getInt(8))));

				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return c;
	}

	@Override
	public int addcbloque(Comptebloque c)throws RemoteException {
		int ok=0;
		String sql = "INSERT INTO compte VALUES(?,?,?,?,?,?,?,?,?,?,?,null,null,null)";
		try {

			db.initPrepar(sql);
			/*passage de valeur*/
			db.getPstm().setString(1, c.getNumCpt());
			db.getPstm().setString(2,c.getDateouverture());
			db.getPstm().setInt(3, c.getSolde());
			db.getPstm().setString(4, c.getType());
			db.getPstm().setString(5, c.getEtat());
			db.getPstm().setInt(6, c.getIdA().getIdA());
			db.getPstm().setInt(7, c.getIdclient().getIdclient());
			db.getPstm().setInt(8, c.getIdemp().getIdemp());
			db.getPstm().setInt(9, c.getFarisouvertureCB());
			db.getPstm().setObject(10, c.getDelaiblocage());
			db.getPstm().setInt(11, c.getRemunerationCB());
		//execution de la requete
			ok = db.executeMaj();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ok;
	}

	@Override
	public int addcourant(Comptecourant c) throws RemoteException {
		int ok=0;
		String sql = "INSERT INTO compte VALUES(?,?,?,?,?,?,?,?,null,null,null,?,null,null)";
		try {

			db.initPrepar(sql);
			/*passage de valeur*/
			db.getPstm().setString(1, c.getNumCpt());
			db.getPstm().setString(2,c.getDateouverture());
			db.getPstm().setInt(3, c.getSolde());
			db.getPstm().setString(4, c.getType());
			db.getPstm().setString(5, c.getEtat());
			db.getPstm().setInt(6, c.getIdA().getIdA());
			db.getPstm().setInt(7, c.getIdclient().getIdclient());
			db.getPstm().setInt(8, c.getIdemp().getIdemp());
			db.getPstm().setInt(9, c.getMontantagio());

		//execution de la requete
			ok = db.executeMaj();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ok;
	}

	@Override
	public int addepargne(Compteepargne c) throws RemoteException{
		int ok=0;
		String sql = "INSERT INTO compte VALUES(?,?,?,?,?,?,?,?,null,null,null,null,?,?)";
		try {

			db.initPrepar(sql);
			/*passage de valeur*/

			db.getPstm().setString(1, c.getNumCpt());
			db.getPstm().setString(2, c.getDateouverture());
			db.getPstm().setInt(3, c.getSolde());
			db.getPstm().setString(4, c.getType());
			db.getPstm().setString(5, c.getEtat());
			db.getPstm().setInt(6, c.getIdA().getIdA());
			db.getPstm().setInt(7, c.getIdclient().getIdclient());
			db.getPstm().setInt(8, c.getIdemp().getIdemp());
			db.getPstm().setInt(9, c.getFraisouvertureCE());
			db.getPstm().setInt(10, c.getRenumerationCE());


		//execution de la requete
			ok = db.executeMaj();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ok;
	}






}
