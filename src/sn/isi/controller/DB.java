package sn.isi.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DB {

	private Connection cnx;
	private ResultSet rs;
	private PreparedStatement pstm;
	private int ok;

	private void getConnexion() {
		String url = "jdbc:mysql://localhost:3306/projetbanque";
		String user = "root";
		String password = "";

		try {
			Class.forName("com.mysql.jdbc.Driver");
			cnx = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void initPrepar(String sql) {
		try {
			getConnexion();
			pstm = cnx.prepareStatement(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public int executeMaj() {
		ok = 0;
		try {
			ok = pstm.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ok;
	}
	public ResultSet executeSelect() {
		try {
			rs = pstm.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

	public PreparedStatement getPstm() {
		return this.pstm;
	}
	public void closeConnexion()
	{
		try {
			if(cnx!=null)
			{
				cnx.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
