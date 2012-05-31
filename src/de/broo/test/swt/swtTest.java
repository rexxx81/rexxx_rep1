package de.broo.test.swt;

import java.io.File;
import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;

public class swtTest {
	private static Text text;
	static String selectedDir = null;
	static String fileFilterPath = "C:\\Users\\Rexxx\\Dropbox\\Photos\\Export";
	static String projectName = null;
	private static Text text_1;

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
		tabFolder.setBounds(0, 51, 632, 383);

		TabItem tbtmArtikelnr = new TabItem(tabFolder, SWT.NONE);
		tbtmArtikelnr.setText("Verzeichnis");

		Group grpVerzeichnis = new Group(tabFolder, SWT.NONE);
		grpVerzeichnis.setText("Verzeichnis");
		tbtmArtikelnr.setControl(grpVerzeichnis);

		text = new Text(grpVerzeichnis, SWT.BORDER);
		text.setBounds(10, 22, 385, 19);

		final Group grpFtpUpload = new Group(grpVerzeichnis, SWT.NONE);
		grpFtpUpload.setText("FTP- Upload");
		grpFtpUpload.setBounds(10, 55, 459, 292);
		grpFtpUpload.setVisible(false);

		Button btnNewButton = new Button(grpVerzeichnis, SWT.NONE);
		btnNewButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				DirectoryDialog directoryDialog = new DirectoryDialog(shell);

				// directoryDialog.setFilterPath(selectedDir);
				directoryDialog.setFilterPath(fileFilterPath);
				directoryDialog
						.setMessage("Please select a directory and click OK");

				String dir = directoryDialog.open();

				if (dir != null) {
					text.setText(dir);
					selectedDir = dir;
					MessageBox mb;
					int val;

					switch (checkFiles(selectedDir)) {

					case 1:
						// Display the message box
						mb = new MessageBox(shell, SWT.OK);
						mb.setText("Fehler");
						mb.setMessage("Das Verzeichnis enthält keine Dateien");
						val = mb.open();
						text.setText("");
						selectedDir = null;
						break;

					case 2:
						mb = new MessageBox(shell, SWT.OK);
						mb.setText("Fehler");
						mb.setMessage("Der Verzeichnisname enthält Leerzeichen und ist ungültig");
						val = mb.open();
						text.setText("");
						selectedDir = null;
						break;

					default:
						projectName = getProjectName(selectedDir);
						text_1.setText(projectName);
						grpFtpUpload.setVisible(true);
					}

				}
			}

		});

		btnNewButton.setBounds(401, 20, 68, 23);
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

		Group grpProjektname = new Group(shell, SWT.NONE);
		grpProjektname.setText("Projektname");
		grpProjektname.setBounds(0, 0, 632, 45);

		text_1 = new Text(grpProjektname, SWT.BORDER);
		text_1.setEnabled(false);
		text_1.setBounds(10, 16, 199, 19);
		shell.open();
		checkFTP();

		// Create and check the event loop
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();

		}
		display.dispose();

	}

	private static int checkFTP() {
		int ftp_id = 0;
		FTPClient ftp = new FTPClient();
		String server = "www.kunsthaus-poorhosaini.de";
		String username = "kunsthaus-6p";
		String password = "Rhein@Kunst";
		String directory = "httpdocs/ebay_rene";

		try {
			int reply;
			ftp.connect(server);
			ftp.login(username, password);
			System.out.println("Connected to " + server + ".");
			System.out.print(ftp.getReplyString());

			// After connection attempt, you should check the reply code to
			// verify
			// success.
			reply = ftp.getReplyCode();

			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				System.err.println("FTP server refused connection.");
				System.exit(1);
			}

			// }

			ftp.setFileType(FTP.BINARY_FILE_TYPE);
			System.out.print(ftp.getReplyString());
			ftp.enterLocalPassiveMode();
			System.out.print("Enter local passive mode.\n");
			ftp.changeWorkingDirectory(directory);
			ftp.pwd();
			System.out.print(ftp.getReplyString());

			System.out.print(ftp.getReplyString());

			FTPFile[] files = ftp.listDirectories("/" + directory + "/");
			System.out.print(ftp.getReplyString());
			for (FTPFile ftpFile : files) {
				// System.out.println(ftpFile.getName());
			}

			ftp.logout();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
					// do nothing
				}
			}
			// System.exit(error ? 1 : 0);
		}

		return ftp_id;
	}

	public static int checkFiles(String dir) {
		int checkDir = 0;
		int fileCount = new File(dir).list().length;

		if (!(fileCount > 0)) {
			checkDir = 1;
		}
		if (dir.contains(" ")) {
			checkDir = 2;
		}

		return checkDir;

	}

	public static String getProjectName(String selectedDir) {
		String name = null;

		selectedDir = selectedDir.substring(
				selectedDir.indexOf(selectedDir) + 2, selectedDir.length());
		// System.out.println(selectedDir);

		StringTokenizer tokenizer = new StringTokenizer(selectedDir, ", \\ ");

		String token[] = new String[tokenizer.countTokens()];

		for (int i = 0; tokenizer.hasMoreTokens(); ++i) {
			token[i] = tokenizer.nextToken();
		}

		// System.out.println(token[token.length - 1]);
		name = token[token.length - 1];
		return name;
	}
}