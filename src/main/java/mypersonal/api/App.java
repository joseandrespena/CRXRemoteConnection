package mypersonal.api;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.jcr.ItemExistsException;
import javax.jcr.LoginException;
import javax.jcr.NoSuchWorkspaceException;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.PathNotFoundException;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.version.VersionException;
import javax.naming.NamingException;

import org.apache.jackrabbit.commons.JcrUtils;
import org.apache.sling.commons.testing.jcr.RepositoryUtil;
import org.apache.sling.jcr.api.SlingRepository;

/**
 * Connects to a Jackrabbit - CRX, Repository
 * @author jose
 */
public class App {

	public static void main(String[] args) throws ClassCastException, MalformedURLException, RemoteException, NotBoundException, LoginException, NoSuchWorkspaceException, RepositoryException, NamingException  {
		System.out.println("Hello World.");
		//RMI Connection
		Repository repository = JcrUtils.getRepository("rmi://localhost:1234/crx");
		SlingRepository slingRepo = new RepositoryUtil.RepositoryWrapper(repository);
		
		//Workspace Login
		SimpleCredentials creds = new SimpleCredentials("admin", "admin".toCharArray());
		Session session = null;
		session = repository.login(creds, "crx.default");
		//List Children
		System.out.println("Workspace: " + session.getWorkspace().getName() + "\n");
		listChildren( "", session.getRootNode() );
		
		//Create new node
		createNewNode(session.getRootNode());
		session.save();
		session.logout();
	}

	private static void listChildren(String indent, Node node ) throws RepositoryException {
		System.out.println(indent + node.getName() ); 
		NodeIterator ni = node.getNodes();
		while(ni.hasNext()) {
			listChildren(indent+"  ", ni.nextNode());
		}
	}
	
	private static void createNewNode(Node node) throws ItemExistsException, PathNotFoundException, VersionException, ConstraintViolationException, LockException, RepositoryException{
		node.addNode("content/myNodeisHere");
	}
}
