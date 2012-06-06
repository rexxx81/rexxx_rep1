package de.broo.test.swt;

import java.io.File;
import java.io.IOException;
import java.util.StringTokenizer;

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
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

public class swtTest {
	private static Text text;
	static String selectedDir = null;
	static String fileFilterPath = "C:\\Users\\Rexxx\\Dropbox\\Photos\\Export";
	static String projectName = null;

	private static Text text_1;
	private static Text text_2;
	private static Text text_3;
	private static Text text_4;
	private static Text text_5;

	static int transferFtpAll = 0;
	static int transferFtpLeft = 0;
	private static Text text_6;
	private static Text text_7;
	private static Text text_8;
	private static Text text_9;
	private static Text text_10;
	private static Text text_11;
	private static Table table;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Display display = new Display();
		final Shell shell = new Shell(display);
		shell.setSize(494, 480);
		shell.setMinimumSize(new Point(494, 480));
		shell.setText("Rene's Ebay Manager v0.1");
		shell.pack();

		final TabFolder tabFolder = new TabFolder(shell, SWT.NONE);
		tabFolder.setBounds(0, 51, 486, 383);

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

		Group grpVon = new Group(grpFtpUpload, SWT.NONE);
		grpVon.setText("Von");
		grpVon.setBounds(10, 23, 439, 46);

		text_2 = new Text(grpVon, SWT.BORDER);
		text_2.setBounds(10, 17, 419, 19);

		Group grpNach = new Group(grpFtpUpload, SWT.NONE);
		grpNach.setText("Nach");
		grpNach.setBounds(10, 75, 439, 46);

		text_3 = new Text(grpNach, SWT.BORDER);
		text_3.setBounds(10, 17, 419, 19);

		final Group grpUploadProzess = new Group(grpFtpUpload, SWT.NONE);
		grpUploadProzess.setText("Upload Prozess");
		grpUploadProzess.setBounds(10, 202, 439, 80);
		grpUploadProzess.setVisible(false);

		Label lblNewLabel = new Label(grpUploadProzess, SWT.NONE);
		lblNewLabel.setBounds(272, 25, 83, 13);
		lblNewLabel.setText("Dateien Gesamt");

		text_4 = new Text(grpUploadProzess, SWT.BORDER);
		text_4.setBounds(380, 22, 49, 19);

		Label lblDateienVerbleibend = new Label(grpUploadProzess, SWT.NONE);
		lblDateienVerbleibend.setText("Dateien verbleibend");
		lblDateienVerbleibend.setBounds(272, 47, 107, 13);

		text_5 = new Text(grpUploadProzess, SWT.BORDER);
		text_5.setBounds(380, 44, 49, 19);

		final ProgressBar progressBar = new ProgressBar(grpUploadProzess,
				SWT.NONE);
		progressBar.setBounds(21, 42, 162, 18);

		Label lblNewLabel_1 = new Label(grpUploadProzess, SWT.NONE);
		lblNewLabel_1.setBounds(21, 25, 49, 13);
		lblNewLabel_1.setText("Fortschritt");

		Button btnNewButton_1 = new Button(grpFtpUpload, SWT.NONE);
		btnNewButton_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				if ((swtTestFtpUtil.checkFTP() == 1)) {
					// System.out.println("hier gestoppt");
					// System.out.println(String.valueOf(swtTestFtpUtil.checkFTP()));

					MessageBox mb = new MessageBox(shell, SWT.OK);
					mb.setText("Fehler");
					mb.setMessage("Das Verzeichnis existiert bereits");
					int val = mb.open();
					text.setText("");
					text_1.setText("");
					grpFtpUpload.setVisible(false);

				} else {
					showUploadInfo(6, 6);
					grpUploadProzess.setVisible(true);
					swtTestFtpUtil.uploadToFtp();
					progressBar.setSelection(100);
					tabFolder.setSelection(1);

				}
			}

		});
		btnNewButton_1.setBounds(331, 127, 118, 23);
		btnNewButton_1.setText("Upload starten");

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
						text_2.setText(selectedDir);
						text_3.setText(swtTestFtpUtil.getFtpHomeDir() + "/"
								+ projectName);
					}

				}
			}

		});

		btnNewButton.setBounds(401, 20, 68, 23);
		btnNewButton.setText("Ausw\u00E4hlen");

		TabItem tbtmKategorie = new TabItem(tabFolder, 0);
		tbtmKategorie.setText("Kategorie");

		Group grpKategorie = new Group(tabFolder, SWT.NONE);
		grpKategorie.setText("Kategorie");
		tbtmKategorie.setControl(grpKategorie);

		text_11 = new Text(grpKategorie, SWT.BORDER);
		text_11.setBounds(10, 22, 326, 19);

		Group grpKategorieAuswhlen = new Group(grpKategorie, SWT.NONE);
		grpKategorieAuswhlen.setText("Kategorie ausw\u00E4hlen");
		grpKategorieAuswhlen.setBounds(10, 55, 459, 292);
		
		table = new Table(grpKategorieAuswhlen, SWT.BORDER | SWT.FULL_SELECTION);
		table.setBounds(10, 20, 439, 238);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableColumn tblclmnNr = new TableColumn(table, SWT.NONE);
		tblclmnNr.setWidth(53);
		tblclmnNr.setText("Nr.");
		
		TableColumn tblclmnName = new TableColumn(table, SWT.NONE);
		tblclmnName.setWidth(178);
		tblclmnName.setText("Name");
		
		Button btnAuswhlen = new Button(grpKategorieAuswhlen, SWT.NONE);
		btnAuswhlen.setText("Ausw\u00E4hlen");
		btnAuswhlen.setBounds(322, 264, 127, 23);

		Button btnKategorieSuchen = new Button(grpKategorie, SWT.NONE);
		btnKategorieSuchen.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			
				
				
			}
		});
		btnKategorieSuchen.setText("Kategorie suchen");
		btnKategorieSuchen.setBounds(342, 20, 127, 23);

		TabItem tbtmBeschreibung = new TabItem(tabFolder, SWT.NONE);
		tbtmBeschreibung.setText("Beschreibung");

		Group grpBeschreibung = new Group(tabFolder, SWT.NONE);
		grpBeschreibung.setText("Beschreibung");
		tbtmBeschreibung.setControl(grpBeschreibung);

		text_6 = new Text(grpBeschreibung, SWT.BORDER);
		text_6.setBounds(10, 22, 385, 19);

		Group group_1 = new Group(grpBeschreibung, SWT.NONE);
		group_1.setText("FTP- Upload");
		group_1.setBounds(10, 55, 459, 292);

		Group group_2 = new Group(group_1, SWT.NONE);
		group_2.setText("Von");
		group_2.setBounds(10, 23, 439, 46);

		text_7 = new Text(group_2, SWT.BORDER);
		text_7.setBounds(10, 17, 419, 19);

		Group group_3 = new Group(group_1, SWT.NONE);
		group_3.setText("Nach");
		group_3.setBounds(10, 75, 439, 46);

		text_8 = new Text(group_3, SWT.BORDER);
		text_8.setBounds(10, 17, 419, 19);

		Group group_4 = new Group(group_1, SWT.NONE);
		group_4.setVisible(false);
		group_4.setText("Upload Prozess");
		group_4.setBounds(10, 202, 439, 80);

		Label label = new Label(group_4, SWT.NONE);
		label.setText("Dateien Gesamt");
		label.setBounds(272, 25, 83, 13);

		text_9 = new Text(group_4, SWT.BORDER);
		text_9.setBounds(380, 22, 49, 19);

		Label label_1 = new Label(group_4, SWT.NONE);
		label_1.setText("Dateien verbleibend");
		label_1.setBounds(272, 47, 107, 13);

		text_10 = new Text(group_4, SWT.BORDER);
		text_10.setBounds(380, 44, 49, 19);

		ProgressBar progressBar_1 = new ProgressBar(group_4, SWT.NONE);
		progressBar_1.setBounds(21, 42, 162, 18);

		Label label_2 = new Label(group_4, SWT.NONE);
		label_2.setText("Fortschritt");
		label_2.setBounds(21, 25, 49, 13);

		Button button = new Button(group_1, SWT.NONE);
		button.setText("Upload starten");
		button.setBounds(331, 127, 118, 23);

		Button button_1 = new Button(grpBeschreibung, SWT.NONE);
		button_1.setText("Ausw\u00E4hlen");
		button_1.setBounds(401, 20, 68, 23);

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
		grpProjektname.setBounds(0, 0, 486, 45);

		text_1 = new Text(grpProjektname, SWT.BORDER);
		text_1.setEnabled(false);
		text_1.setBounds(10, 16, 231, 19);
		shell.open();
		// checkFTP();

		// Create and check the event loop
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();

		}
		display.dispose();

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

	public static String getSelectedDir() {
		return selectedDir;
	}

	public static void setSelectedDir(String selectedDir) {
		swtTest.selectedDir = selectedDir;
	}

	public static String getFileFilterPath() {
		return fileFilterPath;
	}

	public static void setFileFilterPath(String fileFilterPath) {
		swtTest.fileFilterPath = fileFilterPath;
	}

	public static String getProjectName() {
		return projectName;
	}

	public static void setProjectName(String projectName) {
		swtTest.projectName = projectName;
	}

	public static void showUploadInfo(int all, int left) {

		text_4.setText(String.valueOf(all));
		text_4.pack();
		text_5.setText(String.valueOf(left));
		text_5.pack();
	}
}