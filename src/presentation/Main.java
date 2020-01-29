package presentation;

import sn.isi.controller.*;

import java.rmi.AlreadyBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Main {
    public static void main(String[] args) {

        try {
            System.setSecurityManager(new RMISecurityManager());

            Registry registry = LocateRegistry.createRegistry(1488);

            System.setProperty("java.rmi.server.hostname", "127.0.0.1");

            IAgence iAgence = new AgenceDB();
            IClient iClient = new ClientDB();
            ICompte iCompte = new CompteDB();
            IEmploye iEmploye = new EmployeDB();
            IEmployeur iEmployeur = new EmployeurDB();
            IOperation iOperation = new OperationDB();


            registry.bind("agencedao", iAgence);
            registry.bind("clientdao",iClient);
            registry.bind("comptedao",iCompte);
            registry.bind("employedao",iEmploye);
            registry.bind("employeurdao",iEmployeur);
            registry.bind("operationdao",iOperation);

            System.out.println("Tous les serveurs sont enregistrés");

        } catch (RemoteException | AlreadyBoundException e) {
            e.printStackTrace();
        }
    }
}
