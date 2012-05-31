package de.broo.test.swt;



import java.io.IOException;

import javax.swing.tree.DefaultMutableTreeNode;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;




public class swtTest {
	private static Text text;
	static String selectedDir = null;
	String fileFilterPath = "C:\Users\Rexxx\Dropbox\Photos\Export";
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Display display = new Display();
		final Shell shell = new Shell(display);
		shell.setMinimumSize(new Point(640, 480));
		shell.setText("Rene's Ebay Manager v0.1");
		shell.pack();
		
		TabFolder tabFolder = new TabFolder(shell, SWT.NONE);
		tabFolder.setBounds(0, 0, 632, 434);
		
		TabItem tbtmArtikelnr = new TabItem(tabFolder, SWT.NONE);
		tbtmArtikelnr.setText("Verzeichnis");
		
		Group grpVerzeichnis = new Group(tabFolder, SWT.NONE);
		grpVerzeichnis.setText("Verzeichnis");
		tbtmArtikelnr.setControl(grpVerzeichnis);
		
		text = new Text(grpVerzeichnis, SWT.BORDER);
		text.setBounds(10, 22, 145, 19);
		
		
		
		Button btnNewButton = new Button(grpVerzeichnis, SWT.NONE);
		btnNewButton.addListener(SWT.Selection, new Listener() {
		      public void handleEvent(Event event) {
		        DirectoryDialog directoryDialog = new DirectoryDialog(shell);
		        
		        directoryDialog.setFilterPath(selectedDir);
		        directoryDialog.setMessage("Please select a directory and click OK");
		        
		        String dir = directoryDialog.open();
		        if(dir != null) {
		          text.setText( dir);
		          selectedDir = dir;
		        }
		      }
		    });
		 
		btnNewButton.setBounds(161, 20, 68, 23);
		btnNewButton.setText("Ausw\u00E4hlen");
		
		TabItem tbtmFotos = new TabItem(tabFolder, SWT.NONE);
		tbtmFotos.setText("Fotos");
		
		TabItem tbtmBeschreibung = new TabItem(tabFolder, SWT.NONE);
		tbtmBeschreibung.setText("Beschreibung");
		
		TabItem tbtmEintragen = new TabItem(tabFolder, SWT.NONE);
		tbtmEintragen.setText("Eintragen");
		
		Menu menu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menu);
		
		
		
		MenuItem mntmDatei = new MenuItem(menu, SWT.CASCADE);
		mntmDatei.setText("Datei");
		
		Menu menu_2 = new Menu(mntmDatei);
		mntmDatei.setMenu(menu_2);
		
		MenuItem mntmBeenden = new MenuItem(menu_2, SWT.NONE);
		mntmBeenden.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.exit(0);
			}
		});
		mntmBeenden.setText("Beenden");
		
		MenuItem mntmHelp = new MenuItem(menu, SWT.CASCADE);
		mntmHelp.setText("Help");
		
		Menu menu_3 = new Menu(mntmHelp);
		mntmHelp.setMenu(menu_3);
		
		MenuItem mntmHilfe = new MenuItem(menu_3, SWT.NONE);
		mntmHilfe.setText("Hilfe");
		
		Menu menu_1 = new Menu(shell);
		shell.setMenu(menu_1);
		shell.open();
		checkFTP();
		
		// Create and check the event loop
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
			
		}
		display.dispose();

	}
	
	


private static int checkFTP (){
	int ftp_id = 0;
	FTPClient ftp = new FTPClient();
	String server = "www.kunsthaus-poorhosaini.de";
	String username ="kunsthaus-6p";
	String password ="Rhein@Kunst";
	String directory ="httpdocs/ebay_rene"; 
	
			
	 
	try {
      int reply;
      ftp.connect(server);
      ftp.login(username,password);
      System.out.println("Connected to " + server + ".");
      System.out.print(ftp.getReplyString());

      // After connection attempt, you should check the reply code to verify
      // success.
      reply = ftp.getReplyCode();

      if(!FTPReply.isPositiveCompletion(reply)) {
        ftp.disconnect();
        System.err.println("FTP server refused connection.");
        System.exit(1);
      }
      

//      }
  
      

 ftp.setFileType(FTP.BINARY_FILE_TYPE);
System.out.print(ftp.getReplyString());
  ftp.enterLocalPassiveMode();
      System.out.print("Enter local passive mode.\n");
      ftp.changeWorkingDirectory(directory);
     ftp.pwd();
      System.out.print(ftp.getReplyString());

      
      System.out.print(ftp.getReplyString());
      
      FTPFile[] files = ftp.listDirectories("/"+directory+"/");
      System.out.print(ftp.getReplyString());
      for (FTPFile ftpFile : files) {
              System.out.println(ftpFile.getName());
      }     
      
      ftp.logout();
    } catch(IOException e) {
      e.printStackTrace();
    } finally {
      if(ftp.isConnected()) {
        try {
          ftp.disconnect();
        } catch(IOException ioe) {
          // do nothing
        }
      }
    //   System.exit(error ? 1 : 0);
    }
	
	
	return ftp_id;
}
}