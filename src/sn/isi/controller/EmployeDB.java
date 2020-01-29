package sn.isi.controller;

import sn.isi.entities.Employe;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class EmployeDB extends UnicastRemoteObject implements IEmploye {
	private DB db = new DB();
	//private int ok;
	private ResultSet rs;

	public EmployeDB() throws RemoteException {
	}

	@Override
	public int add(Employe e) throws RemoteException {
		int ok=0;
		String sql = "INSERT INTO employe VALUES(null,?,?,?,?,?,?,?,?)";
		try {

			db.initPrepar(sql);
			/*passage de valeur*/
			db.getPstm().setString(1, e.getNom());
			db.getPstm().setString(2, e.getPrenom());
			db.getPstm().setString(3, e.getTel());
			db.getPstm().setString(4, e.getSalaire());
			db.getPstm().setString(5, e.getType());
			db.getPstm().setString(6, e.getLogin());
			db.getPstm().setString(7, e.getPassword());
			db.getPstm().setInt(8, e.getIdA().getIdA());

			//execution de la requete
			ok = db.executeMaj();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ok;
	}

	@Override
	public int delete(int idemp) throws RemoteException {
		int ok=0;
		String sql = "DELETE FROM employe where idemp=?";
		try {

			db.initPrepar(sql);
			db.getPstm().setInt(1,idemp);
			ok = db.executeMaj();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ok;
	}

	@Override
	public int update(Employe e) throws RemoteException{
		int ok=0;
		String sql = "update employe set nom=? ,prenom=? , tel=? ,salaire=? , type=? ,login=? , password=? ,idA=? where idemp=?";
		try {

			db.initPrepar(sql);
			db.getPstm().setString(1, e.getNom());
			db.getPstm().setString(2, e.getPrenom());
			db.getPstm().setString(3, e.getTel());
			db.getPstm().setString(4, e.getSalaire());
			db.getPstm().setString(5, e.getType());
			db.getPstm().setString(6, e.getLogin());
			db.getPstm().setString(7, e.getPassword());
			db.getPstm().setInt(8,e.getIdA().getIdA());
			db.getPstm().setInt(9,e.getIdemp());
			ok = db.executeMaj();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ok;
	}

	@Override
	public List<Employe> liste() throws RemoteException{
		String sql="select * from employe";
		List<Employe> co = new ArrayList<Employe>();
		try {
				db.initPrepar(sql);
				rs=db.executeSelect();
				while(rs.next()){
					Employe e= new Employe();

					e.setIdemp(rs.getInt(1));
					e.setNom(rs.getString(2));
					e.setPrenom(rs.getString(3));
					e.setTel(rs.getString(4));
					e.setSalaire(rs.getString(5));
					e.setType(rs.getString(6));
					e.setLogin(rs.getString(7));
					e.setPassword(rs.getString(8));
					e.setIdA(new AgenceDB().get((rs.getInt(9))));

					co.add(e);
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return co;
	}

	@Override
	public Employe get(int idemp) throws RemoteException{
		String sql="select * from employe where idemp=?";
		Employe e=null;
		try {
				db.initPrepar(sql);
				db.getPstm().setInt(1,idemp);
				rs=db.executeSelect();
				if(rs.next()){

					e= new Employe();
					e.setIdemp(rs.getInt(1));
					e.setNom(rs.getString(2));
					e.setPrenom(rs.getString(3));
					e.setTel(rs.getString(4));
					e.setSalaire(rs.getString(5));
					e.setType(rs.getString(6));
					e.setLogin(rs.getString(7));
					e.setPassword(rs.getString(8));
					e.setIdA(new AgenceDB().get((rs.getInt(9))));
				}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return e;
	}
	@Override
	public Employe getlogin(String login, String password) throws RemoteException{
		Employe e=null;
		String sql = "select * from employe where login= ? and password= ?";
		try {
			//initialise la requete sql
			db.initPrepar(sql);
			db.getPstm().setString(1,login );
			db.getPstm().setString(2,password);
			rs =db.executeSelect();
		while(rs.next())
		{

			e= new Employe();
			e.setIdemp(rs.getInt(1));
			e.setNom(rs.getString(2));
			e.setPrenom(rs.getString(3));
			e.setTel(rs.getString(4));
			e.setSalaire(rs.getString(5));
			e.setType(rs.getString(6));
			e.setLogin(rs.getString(7));
			e.setPassword(rs.getString(8));
			e.setIdA(new AgenceDB().get((rs.getInt(9))));

		}
		rs.close();
		db.closeConnexion();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return e;
	}
	@Override
	public String getType(String login) throws RemoteException  {
		String type = "";
		String sql = "select type from employe  where login=? ";
		try {
			//initialise la requete sql
			db.initPrepar(sql);
			db.getPstm().setString(1,login );
		ResultSet rs=db.executeSelect();
		while(rs.next())
		{
			type=rs.getString("type");

		}
		rs.close();
		db.closeConnexion();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return type;
	}

}
