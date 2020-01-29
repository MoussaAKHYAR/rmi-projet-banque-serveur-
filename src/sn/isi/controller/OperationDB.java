package sn.isi.controller;

import sn.isi.entities.Operation;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class OperationDB extends UnicastRemoteObject implements IOperation {

	private DB db = new DB();
	//private int ok;
	private ResultSet rs;

	public OperationDB() throws RemoteException {
	}

	@Override
	public int retrait(Operation o) throws RemoteException{
		int ok=0;
		String sql = "INSERT INTO operation VALUES(null,?,?,?,?,?,?)";
		try {

			db.initPrepar(sql);
			/*passage de valeur*/
			db.getPstm().setString(1, o.getDateOp());
			db.getPstm().setInt(2, o.getMontantOp());
			db.getPstm().setString(3, o.getTypeOp());
			db.getPstm().setString(4, o.getNumCpt().getNumCpt());
			db.getPstm().setInt(5, o.getIdA().getIdA());
			db.getPstm().setInt(6, o.getIdclient().getIdclient());


			//execution de la requete
			ok = db.executeMaj();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ok;
	}

	@Override
	public int depot(Operation o) throws RemoteException {
		int ok=0;
		String sql = "INSERT INTO operation VALUES(null,?,?,?,?,?,?)";
		try {

			db.initPrepar(sql);
			/*passage de valeur*/
			db.getPstm().setString(1, o.getDateOp());
			db.getPstm().setInt(2, o.getMontantOp());
			db.getPstm().setString(3, o.getTypeOp());
			db.getPstm().setString(4, o.getNumCpt().getNumCpt());
			db.getPstm().setInt(5, o.getIdA().getIdA());
			db.getPstm().setInt(6, o.getIdclient().getIdclient());
			//execution de la requete
			ok = db.executeMaj();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ok;
	}
	@Override
	public int verser(int montant, String numCpt) throws RemoteException {
		String sql= "update compte set solde=solde+'"+montant+"' where numCpt =?";
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
	public int retirer(int montant, String numCpt) throws RemoteException {
		String sql= "update compte set solde=solde-'"+montant+"' where numCpt =?";
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
	public List<Operation> liste() throws RemoteException {
		String sql="select * from operation";
		List<Operation> co = new ArrayList<Operation>();
		try {
				db.initPrepar(sql);
				rs=db.executeSelect();
				while(rs.next()){
					Operation e= new Operation();

					e.setIdOp(rs.getInt(1));
					e.setDateOp(rs.getString(2));
					e.setMontantOp(rs.getInt(3));
					e.setTypeOp(rs.getString(4));
					e.setNumCpt(new CompteDB().get(rs.getString(5)));
					e.setIdA(new AgenceDB().get((rs.getInt(6))));
					e.setIdclient(new ClientDB().get((rs.getInt(7))));
					co.add(e);
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return co;
	}

	@Override
	public List<Operation> listevirement() throws RemoteException {
		String sql="select * from operation where typeOp='Virement' ";
		List<Operation> co = new ArrayList<Operation>();
		try {
				db.initPrepar(sql);
				rs=db.executeSelect();
				while(rs.next()){
					Operation e= new Operation();

					e.setIdOp(rs.getInt(1));
					e.setDateOp(rs.getString(2));
					e.setMontantOp(rs.getInt(3));
					e.setTypeOp(rs.getString(4));
					e.setNumCpt(new CompteDB().get(rs.getString(5)));
					e.setIdA(new AgenceDB().get((rs.getInt(6))));
					e.setIdclient(new ClientDB().get((rs.getInt(7))));
					co.add(e);
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return co;
	}

	@Override
	public List<Operation> listeothers() throws RemoteException{
		String sql="select * from operation where typeOp='Depot' or typeOp='Retrait'  ";
		List<Operation> co = new ArrayList<Operation>();
		try {
				db.initPrepar(sql);
				rs=db.executeSelect();
				while(rs.next()){
					Operation e= new Operation();

					e.setIdOp(rs.getInt(1));
					e.setDateOp(rs.getString(2));
					e.setMontantOp(rs.getInt(3));
					e.setTypeOp(rs.getString(4));
					e.setNumCpt(new CompteDB().get(rs.getString(5)));
					e.setIdA(new AgenceDB().get((rs.getInt(6))));
					e.setIdclient(new ClientDB().get((rs.getInt(7))));
					co.add(e);
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return co;
	}

	@Override
	public int delete(int idOp) throws RemoteException {
		String sql= "DELETE  from operation where idOp=?";
		int	ok=0;
			try {
				db.initPrepar(sql);
				db.getPstm().setInt(1,idOp);
				ok=db.executeMaj();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return ok;
	}



}
