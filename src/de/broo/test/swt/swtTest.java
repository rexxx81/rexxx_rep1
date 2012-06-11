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
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import de.broo.test.ebay.ebay_get_suggested;
import com.ebay.soap.eBLBaseComponents.SuggestedCategoryArrayType;
import com.ebay.soap.eBLBaseComponents.SuggestedCategoryType;

import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Combo;

public class swtTest {
	private static Text text;
	static String selectedDir = null;
	static String fileFilterPath = "C:\\Users\\Rexxx\\Dropbox\\Photos\\Export";
	static String projectName = null;
	static int template_id = 0;
	static int CategorieID = 0;

	private static Text text_1;
	private static Text text_2;
	private static Text text_3;
	private static Text text_4;
	private static Text text_5;

	static int transferFtpAll = 0;
	static int transferFtpLeft = 0;
	private static Text text_6;
	private static Text text_11;
	private static Table table;
	private static Text text_12;
	private static Text text_7;
	private static Text text_8;
	private static Text text_9;
	private static Text text_10;

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
						mb.setMessage("Das Verzeichnis enth�lt keine Dateien");
						val = mb.open();
						text.setText("");
						selectedDir = null;
						break;

					case 2:
						mb = new MessageBox(shell, SWT.OK);
						mb.setText("Fehler");
						mb.setMessage("Der Verzeichnisname enth�lt Leerzeichen und ist ung�ltig");
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
		text_11.setBounds(10, 22, 303, 19);

		Group grpKategorieAuswhlen = new Group(grpKategorie, SWT.NONE);
		grpKategorieAuswhlen.setText("Kategorie ausw\u00E4hlen");
		grpKategorieAuswhlen.setBounds(10, 47, 459, 300);

		table = new Table(grpKategorieAuswhlen, SWT.BORDER | SWT.FULL_SELECTION);
		table.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				text_12.setText(table.getSelection()[0].getText());
			}
		});
		table.setBounds(10, 20, 439, 238);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		final TableColumn tblclmnNr = new TableColumn(table, SWT.NONE);
		tblclmnNr.setWidth(57);
		tblclmnNr.setText("Nr.");

		TableColumn tblclmnName = new TableColumn(table, SWT.NONE);
		tblclmnName.setWidth(378);
		tblclmnName.setText("Name");

		Button btnAuswhlen = new Button(grpKategorieAuswhlen, SWT.NONE);
		btnAuswhlen.setText("Ausw\u00E4hlen");
		btnAuswhlen.setBounds(322, 264, 127, 23);

		final Label label_3 = new Label(grpKategorie, SWT.NONE);
		label_3.setBackground(SWTResourceManager.getColor(0, 100, 0));
		label_3.setBounds(323, 22, 23, 19);

		Button btnKategorieSuchen = new Button(grpKategorie, SWT.NONE);
		btnKategorieSuchen.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				if (!(text_11.getText().equals(""))) {
					label_3.setBackground(SWTResourceManager
							.getColor(129, 0, 0));
					label_3.setRedraw(true);
					String Query = text_11.getText();
					ebay_get_suggested suggested = new ebay_get_suggested();
					SuggestedCategoryType[] Array = suggested
							.ebayGetSuggeestedCatId(Query);

					String completeSuggested = "";
					String parentString = "";
					String[] parentArray = null;

					if (!(Array == null)) {
						for (int i = 0; i < Array.length; i++) {

							TableItem item = new TableItem(table, SWT.NONE);
							int c = 0;

							parentString = "";
							parentArray = Array[i].getCategory()
									.getCategoryParentName();
							for (int ii = 0; ii < parentArray.length; ii++) {
								parentString = parentString + " > "
										+ parentArray[ii];
							}

							completeSuggested = parentString + " > "
									+ Array[i].getCategory().getCategoryName();

							item.setText(c++, Array[i].getCategory()
									.getCategoryID());
							item.setText(c++, completeSuggested);
						}

					} else {
						MessageBox mb = new MessageBox(shell, SWT.OK);
						mb.setText("Fehler");
						mb.setMessage("Die Suche ergab keine Treffer. Versuchs nochmal");
						int val = mb.open();
						text_11.setFocus();

					}

					table.setRedraw(true);
					label_3.setBackground(SWTResourceManager
							.getColor(0, 100, 0));
					label_3.setRedraw(true);
				} else {
					MessageBox mb = new MessageBox(shell, SWT.OK);
					mb.setText("Fehler");
					mb.setMessage("Bitte geben Sie einen Suchtext ein");
					int val = mb.open();
					text_11.setFocus();
				}
			}
		});
		btnKategorieSuchen.setText("Kategorie suchen");
		btnKategorieSuchen.setBounds(352, 20, 117, 23);

		TabItem tbtmBeschreibung = new TabItem(tabFolder, SWT.NONE);
		tbtmBeschreibung.setText("Beschreibung");

		Group grpBeschreibung = new Group(tabFolder, SWT.NONE);
		grpBeschreibung.setText("\u00DCberschrift");
		tbtmBeschreibung.setControl(grpBeschreibung);

		text_6 = new Text(grpBeschreibung, SWT.BORDER);
		text_6.setBounds(10, 22, 393, 19);

		Group grpInhalt = new Group(grpBeschreibung, SWT.NONE);
		grpInhalt.setText("Inhalt");
		grpInhalt.setBounds(10, 47, 459, 300);

		text_7 = new Text(grpInhalt, SWT.BORDER);
		text_7.setBounds(10, 21, 439, 242);

		Label lblTemplate = new Label(grpInhalt, SWT.NONE);
		lblTemplate.setBounds(10, 277, 49, 13);
		lblTemplate.setText("Template");

		final Combo combo_2 = new Combo(grpInhalt, SWT.NONE);
		combo_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				template_id = combo_2.getSelectionIndex();

			}
		});
		combo_2.setBounds(65, 269, 111, 21);
		combo_2.add("AntikGalerie", 0);
		combo_2.add("Rene", 1);
		combo_2.add("Janette", 2);
		combo_2.select(0);

		Button btnNewButton_2 = new Button(grpInhalt, SWT.NONE);
		btnNewButton_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				createTemplate(template_id);
			}

		});
		btnNewButton_2.setBounds(311, 269, 138, 23);
		btnNewButton_2.setText("New Button");

		Label lblNewLabel_2 = new Label(grpBeschreibung, SWT.NONE);
		lblNewLabel_2.setAlignment(SWT.RIGHT);
		lblNewLabel_2.setBounds(406, 25, 63, 13);
		lblNewLabel_2.setText("noch (120)");

		TabItem tbtmEintragen = new TabItem(tabFolder, SWT.NONE);
		tbtmEintragen.setText("Eintragen");

		Group grpBitteberprfen = new Group(tabFolder, SWT.NONE);
		grpBitteberprfen.setText("Bitte \u00FCberpr\u00FCfen");
		tbtmEintragen.setControl(grpBitteberprfen);

		Group grpPreis = new Group(grpBitteberprfen, SWT.NONE);
		grpPreis.setText("Preis");
		grpPreis.setBounds(10, 21, 280, 56);

		Button button = new Button(grpPreis, SWT.RADIO);
		button.setText("1 EUR Auktion");
		button.setBounds(10, 22, 98, 16);

		Button button_2 = new Button(grpPreis, SWT.RADIO);
		button_2.setText("Festpreis");
		button_2.setSelection(true);
		button_2.setBounds(122, 22, 65, 16);

		text_8 = new Text(grpPreis, SWT.BORDER);
		text_8.setBounds(193, 22, 76, 19);

		Group grpVersand = new Group(grpBitteberprfen, SWT.NONE);
		grpVersand.setText("Versand DEU");
		grpVersand.setBounds(10, 83, 142, 56);

		text_9 = new Text(grpVersand, SWT.BORDER);
		text_9.setBounds(10, 27, 122, 19);

		Group grpVersandWeltweit = new Group(grpBitteberprfen, SWT.NONE);
		grpVersandWeltweit.setText("Versand Weltweit");
		grpVersandWeltweit.setBounds(158, 83, 131, 56);

		text_10 = new Text(grpVersandWeltweit, SWT.BORDER);
		text_10.setBounds(10, 27, 111, 19);

		Group grpTitelbild = new Group(grpBitteberprfen, SWT.NONE);
		grpTitelbild.setText("Titelbild");
		grpTitelbild.setBounds(296, 21, 172, 118);

		Button btnEintragen = new Button(grpBitteberprfen, SWT.NONE);
		btnEintragen.setText("Eintragen");
		btnEintragen.setBounds(341, 244, 127, 23);

		Button btnVorschau = new Button(grpBitteberprfen, SWT.NONE);
		btnVorschau.setText("Vorschau");
		btnVorschau.setBounds(209, 244, 127, 23);

		Group grpStartzeit = new Group(grpBitteberprfen, SWT.NONE);
		grpStartzeit.setText("Startzeit");
		grpStartzeit.setBounds(10, 145, 458, 93);

		Combo combo = new Combo(grpStartzeit, SWT.NONE);
		combo.setBounds(128, 26, 66, 21);

		Label lblTag = new Label(grpStartzeit, SWT.NONE);
		lblTag.setBounds(106, 31, 49, 13);
		lblTag.setText("Tag");

		Label lblZeit = new Label(grpStartzeit, SWT.NONE);
		lblZeit.setText("Zeit");
		lblZeit.setBounds(216, 26, 24, 13);

		Combo combo_1 = new Combo(grpStartzeit, SWT.NONE);
		combo_1.setBounds(240, 23, 92, 21);

		Button btnRadioButton = new Button(grpStartzeit, SWT.RADIO);
		btnRadioButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		btnRadioButton.setBounds(10, 28, 83, 16);
		btnRadioButton.setText("Geplant");

		Button btnJetzt = new Button(grpStartzeit, SWT.RADIO);
		btnJetzt.setText("Jetzt");
		btnJetzt.setBounds(10, 50, 83, 16);

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
		grpProjektname.setBounds(0, 0, 399, 45);

		text_1 = new Text(grpProjektname, SWT.BORDER);
		text_1.setEnabled(false);
		text_1.setBounds(10, 16, 379, 19);

		Group grpKategorie_1 = new Group(shell, SWT.NONE);
		grpKategorie_1.setText("KategorieID");
		grpKategorie_1.setBounds(405, 0, 81, 45);

		text_12 = new Text(grpKategorie_1, SWT.BORDER);
		text_12.setEnabled(false);
		text_12.setBounds(10, 16, 61, 19);
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

	public static String createTemplate(int template_id) {
		String template = null;
		String imgString = null;
		int imgCount = 0;

		imgCount = swtTestFtpUtil.countFTP();
		// System.out.println(imgCount);

		// imgString = imgString +
		// "<img border='0' src='http://www.kunsthaus-poorhosaini.de/antik_ebay/upload/".$art_id."/"(i+1)
		// +".jpg' align='center'><br><br><br>";

		switch (template_id) {

		case 0:

			template = "<!DOCTYPE HTML PUBLIC '-//W3C//DTD HTML 4.01 Transitional//EN' 'http://www.w3.org/TR/html4/loose.dtd'><html><head>"
					+ "<title>Auktion</title><meta http-equiv='content-type' content='text/html; charset=utf-8'>"
					+ "<meta http-equiv='content-language' content='de'>"
					+ "<link rel='stylesheet' href='http://www.kunsthaus-poorhosaini.de/antik_ebay/template/style.css' type='text/css' media='screen' />"
					+ "</head><body><table id='container'><tr><td colspan=2 id='back1_td'>&nbsp;</td></tr><tr><td id='back2_td'></td><td>"
					+ "<br><br><p id='main_text'><u id='headline'>"
					+ text_6.getText()
					+ "</u><br><br>"
					+ text_7.getText()
					+ "</p><br><br></td><tr><td colspan=2>"
					+ "<p id='main_text'><u id='headline'>Detailansicht</u></p><p id='main_text'>"
					+ imgString
					+ "</p><p></p><p></p><hr><p><br><br><br><img border='0' src='http://www.kunsthaus-poorhosaini.de/antik_ebay/template/Laden4.jpg' width='450' height='310' align='center'></td></tr><tr>"
					+ "<td colspan='2'><p id='main_text'><u>Unsere �ffnungszeiten:</u></font></p><p id='main_text'>Montag bis Freitag: 10:30 - 18:30 Uhr<br>Samstag: 10:30 - 15:30 Uhr</p>"
					+ "<a href='http://www.kunsthaus-poorhosaini.de/antik_ebay/template/Anfahrtsskizze.jpg'>"
					+ "Anfahrtsskizze</a></p>"
					+ "<p>"
					+ "</p>"
					+ "<p id='main_text'>"
					+ "Nat�rlich k�nnen Sie uns zu den Gesch�ftszeiten auch jederzeit"
					+ "<br>telefonisch unter 06151-25688 oder per E-Mail an<br> <a href='mailto:info@antikgalerie24.de'>info@antikgalerie24.de</a> "
					+ "erreichen.</p>"
					+ "<p>&nbsp;</p>"
					+ "<p id='main_text'>Wir verkaufen - sofern nicht anders angegeben - "
					+ "ausschlie�lich Gebrauchtware.<br>"
					+ "<br></p>"
					+

					"<p id='main_text'><u>"
					+ "Inhaber:</u><br><br>"
					+ "Tobias Poorhosaini<br>Dipl. Ing. S.N. Poorhosaini<br>�ffentlich bestellter und "
					+ "staatlich vereidigter Auktionator</p>"
					+ "<p id='main_text'>"
					+ "Schulstra�e 1 - 64283 Darmstadt</p>"
					+ "<p id='main_text'>"
					+ "Gem�lde - Antiquit�ten - Schmuck</p>"
					+ "<p id='main_text'><img border='0' src='http://www.kunsthaus-poorhosaini.de/antik_ebay/template/Laden1.JPG' width='200' height='298' align='center'></p>"
					+

					"<p id='main_text'><br>Die Antik Galerie in Darmstadt wurde 1980 von Dipl. Ing."
					+ "S. N. Poorhosaini er�ffnet. Bei uns finden Sie exklusive Gegenst�nde aus allen "
					+ "Kunstepochen: Gem�lde, Skulpturen, Schmuck, Taschenuhren, Jugendstil, "
					+ "Meissnerporzellan, M�bel, Glas und vieles mehr. Die Preise beginnen schon bei "
					+ "5�, aber auch f�r Kunstkenner und Sammler von seltenen und ausgew�hlten "
					+ "Kunstgegenst�nden ist bei uns auf 600qm� Ladenfl�che f�r jeden etwas dabei.<br><br><br>"
					+ "</p>"
					+ "<hr><p id='main_text'><u><br>"
					+ "Allgemeine Gesch�ftsbedingungen:</u></p>"
					+ "<h3>� 1 Geltungsbereich</h3>"
					+ " <p>Diese Gesch�ftsbedingungen gelten f�r alle Vertr�ge,"
					+ "Lieferungen und sonstigen Leistungen durch antik-galerie-24"
					+ "(nachstehend: Verk�ufer), gegen�ber seinen Kunden beim "
					+ " Vertragsschluss �ber eBay. Abweichende Vorschriften der Kunden "
					+ " gelten nicht, es sei denn der Verk�ufer hat dies ausdr�cklich und"
					+ "  schriftlich best�tigt. </p>"
					+ "<h3>� 2 Vertragsinhalt, Vertragsabschluss</h3>"
					+ "<p>(1) Der Verk�ufer bietet den Kunden �ber eBay verschiedene"
					+ "Produkte zum Kauf an. </p>"
					+ "<p>(2) Bei einer Online-Auktion �ber eBay kommt ein Kaufvertrag"
					+ "zwischen dem Verk�ufer und demjenigen Kunden zustande, der bei "
					+ "Ablauf der Laufzeit der Auktion das h�chste Gebot abgegeben hat."
					+ "Bei Verwendung einer 'Sofort-Kaufen-Option' kommt der Kaufvertrag"
					+ "zwischen dem Verk�ufer und dem Kunden unabh�ngig vom Ablauf der "
					+ "Angebotszeit und ohne weitere Durchf�hrung der Auktion bereits"
					+ "dann zu dem in der Option bestimmten Preis zustande, wenn ein "
					+ "Kunde die Option aus�bt. �ber den Vertragsschluss wird der Kunde"
					+ "per E-Mail informiert. </p>"
					+ "<h3>� 3 Preise und Zahlung</h3>"
					+ "<p>(1) S�mtliche Preise verstehen sich zuz�glich der Versand- und "
					+ "Verpackungskosten, die dem Kunden vor Abgabe der Bestellung "
					+ "bekannt gegeben werden. Alle Preise beinhalten die gesetzliche"
					+ "Mehrwertsteuer. </p>"
					+ "<p>(2) Die Belieferung der Kunden durch den Verk�ufer erfolgt "
					+ "grunds�tzlich nur gegen Vorkasse (�berweisung oder Zahlung per "
					+ "Paypal). Die Waren werden erst nach Eingang der Zahlung "
					+ "versendet. Der Verk�ufer stellt dem Kunden eine Rechnung aus, die "
					+ "ihm bei Lieferung der Ware ausgeh�ndigt wird oder sonst in "
					+ "Textform zugeht. </p>"
					+ "<h3>� 4 Lieferung</h3>"
					+ "<p>(1) Die bestellten Waren werden, sofern vertraglich nicht "
					+ "abweichend vereinbart, an die vom Kunden angegebene Adresse "
					+ "geliefert. Die Lieferung erfolgt aus dem Lager des Verk�ufers "
					+ "oder direkt vom Hersteller. </p>"
					+ "<p>(2) Der Verk�ufer beh�lt sich vor, eine Teillieferung "
					+ "vorzunehmen, sofern dies f�r eine z�gige Abwicklung vorteilhaft "
					+ "erscheint und die Teillieferung f�r den Kunden nicht "
					+ "ausnahmsweise unzumutbar ist. Durch Teillieferungen entstehende"
					+ "Mehrkosten werden dem Kunden nicht in Rechnung gestellt. </p>"
					+ "<p>(3) Angaben �ber die Lieferfrist sind unverbindlich, sofern "
					+ "nicht ausnahmsweise der Liefertermin vom Verk�ufer verbindlich "
					+ "zugesagt wurde. Ware, die am Lager ist, versendet der Verk�ufer "
					+ "innerhalb von 3 Werktagen nach Eingang der Zahlung des Kunden. "
					+ "Ist die Ware bei Zahlungseingang nicht vorr�tig, bem�ht sich die "
					+ "Verk�ufer um schnellstm�gliche Lieferung. Falls die "
					+ "Nichteinhaltung einer Liefer- oder Leistungsfrist auf h�here "
					+ "Gewalt, Arbeitskampf, unvorhersehbare Hindernisse oder sonstige "
					+ "vom Verk�ufer nicht zu vertretende Umst�nde zur�ckzuf�hren ist, "
					+ "wird die Frist angemessen verl�ngert. </p>"
					+ "<h3>� 5 WIDERRUFSRECHT</h3>"
					+ "<p>Sie k�nnen Ihre Vertragserkl�rung innerhalb von einem Monat "
					+ "ohne Angabe von Gr�nden in Textform (z. B. Brief, E-Mail) oder - "
					+ "wenn Ihnen die Sache vor Fristablauf �berlassen wird - durch "
					+ "R�cksendung der Sache widerrufen. Die Frist beginnt nach Erhalt "
					+ "dieser Belehrung in Textform, jedoch nicht vor Eingang der Ware "
					+ "beim Empf�nger (bei der wiederkehrenden Lieferung gleichartiger "
					+ "Waren nicht vor Eingang der ersten Teillieferung) und auch nicht "
					+ "vor Erf�llung unserer Informationspflichten gem�� � 312c Abs. 2 "
					+ "BGB in Verbindung mit � 1 Abs. 1, 2 und 4 BGB-InfoV sowie unserer "
					+ "Pflichten gem�� � 312e Abs. 1 Satz 1 BGB in Verbindung mit � 3 "
					+ "BGB-InfoV. Zur Wahrung der Widerrufsfrist gen�gt die rechtzeitige "
					+ "Absendung des Widerrufs oder der Sache. </p>"
					+ "<p>Der Widerruf ist zu richten an: Name S. N. Poorhosaini, "
					+ "Schulstra�e 1, 64283 Darmstadt; ebay@antikgalerie24.de.</p>"
					+ "<p>&nbsp;</p>"
					+ "<p><strong>Widerrufsfolgen</strong> </p>"
					+ "<p>Im Falle eines wirksamen Widerrufs sind die beiderseits "
					+ "empfangenen Leistungen zur�ckzugew�hren und ggf. gezogene "
					+ "Nutzungen (z. B. Zinsen) herauszugeben. K�nnen Sie uns die "
					+ "empfangene Leistung ganz oder teilweise nicht oder nur in "
					+ "verschlechtertem Zustand zur�ckgew�hren, m�ssen Sie uns insoweit "
					+ "ggf. Wertersatz leisten. Bei der �berlassung von Sachen gilt dies "
					+ "nicht, wenn die Verschlechterung der Sache ausschlie�lich auf "
					+ "deren Pr�fung - wie sie Ihnen etwa im Ladengesch�ft m�glich "
					+ "gewesen w�re - zur�ckzuf�hren ist. Im �brigen k�nnen Sie die "
					+ "Pflicht zum Wertersatz f�r eine durch die bestimmungsgem��e "
					+ "Ingebrauchnahme der Sache entstandene Verschlechterung vermeiden,"
					+ "indem Sie die Sache nicht wie Ihr Eigentum in Gebrauch nehmen und"
					+ "alles unterlassen, was deren Wert beeintr�chtigt. F�r eine durch "
					+ "die bestimmungsgem��e Ingebrauchnahme der Sache entstandene "
					+ "Verschlechterung m�ssen Sie keinen Wertersatz leisten. </p>"
					+ "<p>Paketversandf�hige Sachen sind auf unsere Gefahr "
					+ "zur�ckzusenden. Sie haben die Kosten der R�cksendung zu tragen, "
					+ "wenn die gelieferte Ware der bestellten entspricht und wenn der "
					+ "Preis der zur�ckzusendenden Sache einen Betrag von 40 Euro nicht "
					+ "�bersteigt oder wenn Sie bei einem h�heren Preis der Sache zum "
					+ "Zeitpunkt des Widerrufs noch nicht die Gegenleistung oder eine "
					+ "vertraglich vereinbarte Teilzahlung erbracht haben. Anderenfalls "
					+ "ist die R�cksendung f�r Sie kostenfrei. Nicht paketversandf�hige "
					+ "Sachen werden bei Ihnen abgeholt. Verpflichtungen zur Erstattung "
					+ "von Zahlungen m�ssen innerhalb von 30 Tagen erf�llt werden. Die "
					+ "Frist beginnt f�r Sie mit der Absendung Ihrer Widerrufserkl�rung "
					+ "oder der Sache, f�r uns mit deren Empfang. [Ende der "
					+ "Widerrufsbelehrung] </p>"
					+ "<h3>� 6 Eigentumsvorbehalt, Aufrechnung</h3>"
					+ "<p>(1) Die gelieferten Waren bleiben bis zur Erf�llung aller "
					+ "Forderungen aus dem Vertrag im Eigentum des Verk�ufers; im Fall,"
					+ "dass der Kunde eine juristische Person des �ffentlichen Rechts, "
					+ "ein �ffentlich-rechtliches Sonderverm�gen oder ein Unternehmer in"
					+ "Aus�bung seiner gewerblichen oder selbstst�ndigen beruflichen "
					+ "T�tigkeit ist, auch dar�ber hinaus aus der laufenden "
					+ "Gesch�ftsbeziehung bis zum Ausgleich aller Forderungen, die dem "
					+ "Verk�ufer im Zusammenhang mit dem Vertrag zustehen. </p>"
					+ "<p>(2) Das Recht zur Aufrechnung steht dem Kunden nur zu, wenn "
					+ "seine Gegenanspr�che vom Verk�ufer anerkannt oder rechtskr�ftig "
					+ "festgestellt sind. Zur Aus�bung eines Zur�ckbehaltungsrechts ist "
					+ "der Kunde nur insoweit befugt, als sein Gegenanspruch auf "
					+ "demselben Vertragsverh�ltnis beruht. </p>"
					+ "<h3>� 7 M�ngelgew�hrleistung und Haftung</h3>"
					+ "<p>Ist die bestellte Ware bei Gefahr�bergang mit einem Mangel "
					+ "behaftet, so dass sie sich nicht mehr f�r die nach dem Vertrag "
					+ "vorausgesetzte Verwendung eignet, hat der Besteller innerhalb der "
					+ "gesetzlichen Gew�hrleistungsfrist zun�chst das Recht, zwischen "
					+ "Beseitigung des Mangels durch Reparatur/Nachbesserung oder "
					+ "Lieferung einer neuen Ware zu w�hlen. Liegen jedoch leichte "
					+ "behebbare M�ngel vor, kann der Besteller nicht auf einer "
					+ "Neulieferung bestehen. Schl�gt die Nachbesserung fehlt, kann der"
					+ "Besteller den Kaufpreis mindern oder vom Vertrag zur�cktreten. "
					+ "Die Verj�hrungsfrist zu dem Gew�hrleistungsanspruch hinsichtlich "
					+ "der gelieferten Ware betr�gt 2 Jahre vom Zeitpunkt der "
					+ "Ablieferung der Ware. Wir haften unbegrenzt nach den Bestimmungen "
					+ "des Produkthaftungsgesetzes oder etwaiger anderer zwingender "
					+ "gesetzlicher Haftungsvorschriften. Bei leicht fahrl�ssiger "
					+ "Verletzung wesentlicher Vertragspflichten haften wir der H�he "
					+ "nach beschr�nkt auf den vertragstypischen vorhersehbaren Schaden, "
					+ "der in der Regel den Kaufpreis der bestellten Ware nicht "
					+ "�berschreitet. Dar�ber hinaus haften wir nicht. Es gelten die "
					+ "gesetzlichen Verj�hrungsvorschriften. </p>"
					+ "<h3>� 8 Transportsch�den</h3>"
					+ "<p>Werden Waren mit offensichtlichen Sch�den an der Verpackung "
					+ "oder am Inhalt angeliefert, so hat der Kunde dies unbeschadet "
					+ "seiner Gew�hrleistungsrechte sofort beim Spediteur/ Frachtdienst "
					+ "zu reklamieren und unverz�glich durch eine E Mail an den "
					+ "Verk�ufer oder auf sonstige Weise (Fax/Post) mit dem Verk�ufer "
					+ "Kontakt aufzunehmen, damit dieser etwaige Rechte gegen�ber dem"
					+ "Spediteur/Frachtdienst wahren kann. Der Kunde hilft bei der "
					+ "Durchsetzung der Anspr�che des Verk�ufers gegen den "
					+ "Spediteur/Frachtdienst. </p>"
					+ "<h3>� 9 Haftung f�r Sach- und Rechtsm�ngel</h3>"
					+ "<p>Soweit M�ngel vorliegen, stehen dem Kunden nach Ma�gabe der "
					+ "folgenden Bestimmungen die gesetzlichen Gew�hrleistungsrechte zu:"
					+ "</p>"
					+ "<p>(1) Der Kunde kann offensichtliche M�ngel an den gelieferten "
					+ "Waren oder Falschlieferungen nur geltend machen, wenn er diese "
					+ "innerhalb eines Monats nach der Lieferung unter genauer "
					+ "Beschreibung in Textform gegen�ber dem Verk�ufer r�gt. Ma�geblich"
					+ "f�r die Rechtzeitigkeit ist die Absendung der M�ngelr�ge. </p>"
					+ "<p>(2) M�ngel, die keine offensichtlichen M�ngel sind, sind "
					+ "innerhalb einer Gew�hrleistungsfrist von 2 Jahren bei neuen "
					+ "Sachen bzw. von 1 Jahr bei gebrauchten Sachen in Textform beim "
					+ "Verk�ufer zu r�gen. Ist der Kunde Unternehmer, so betr�gt die "
					+ "Gew�hrleistungsfrist stets 1 Jahr. Sind an dem Vertrag nur "
					+ "Kaufleute beteiligt, so gelten erg�nzend die �� 377 ff. HGB. </p>"
					+ "<p>(3) Liegen M�ngel vor und wurden diese rechtzeitig geltend "
					+ "gemacht, so ist der Verk�ufer zur Nacherf�llung berechtigt. "
					+ "Schl�gt die Nacherf�llung fehl, ist der Kunde berechtigt, den "
					+ "Kaufpreis zu mindern oder vom Vertrag zur�ckzutreten. Im �brigen "
					+ "gelten die gesetzlichen Bestimmungen. </p>"
					+ "<h3>� 10 Haftung</h3>"
					+ "<p>(1) Schadenersatzanspr�che au�erhalb der Haftung f�r Sach- und "
					+ "Rechtsm�ngel kann der Kunde gegen�ber dem Verk�ufer nur bei "
					+ "Vorsatz oder grobfahrl�ssigem Verhalten geltend machen. Der "
					+ "Haftungsausschluss gilt nicht bei der Verletzung des Lebens, des"
					+ "K�rpers oder der Gesundheit und bei der Verletzung wesentlicher "
					+ "Vertragspflichten. Au�er bei Vorsatz, grober Fahrl�ssigkeit und "
					+ "Sch�den aus der Verletzung des Lebens, des K�rpers oder der "
					+ "Gesundheit ist die Haftung des Verk�ufers der H�he nach auf die "
					+ "bei Vertragsschluss typischerweise vorhersehbaren Sch�den "
					+ "begrenzt. </p>"
					+ "<p>(2) Die Haftung nach dem Produkthaftungsgesetz und sonstigen "
					+ "zwingenden gesetzlichen Vorschriften bleibt unber�hrt. </p>"
					+ "<h3>� 11 Anwendbares Recht, Gerichtsstand</h3>"
					+ "<p>Die Gesch�ftsbeziehungen zwischen dem Verk�ufer und dem "
					+ "Besteller unterliegen dem Recht der Bundesrepublik Deutschland "
					+ "unter Ausschlussdes UN-Kaufrechts. Gerichtsstand ist Darmstadt, "
					+ "soweit der Besteller Kaufmann ist. Der Anbieter ist berechtigt,"
					+ "den Besteller wahlweise auch an einem anderen Ort zu verklagen."
					+ "</p>"
					+ "<h3>� 12 Datenschutz</h3>"
					+ "<p>Der Besteller stimmt der Erhebung, Verarbeitung und Nutzung "
					+ "personenbezogener Daten ausdr�cklich zu. Die f�r die "
					+ "Gesch�ftsabwicklung notwendigen Daten werden gespeichert und im "
					+ "Rahmen der Bestellabwicklung gegebenenfalls an verbundene "
					+ "Unternehmen weitergegeben. Alle pers�nlichen Daten werden "
					+ "selbstverst�ndlich vertraulich behandelt. Zum Zwecke der "
					+ "Kreditpr�fung beh�lt sich der Verk�ufer einen Datenaustausch mit"
					+ "anderen Konzernunternehmen sowie gegebenenfalls mit Auskunfteien "
					+ "vor.</p>"
					+ "<p id='main_text'>&nbsp;"
					+ "</p>"
					+ "<p id='main_text'><u>"
					+ "Was uns noch wichtig ist:</u></p>"
					+ "<p id='main_text'>Die Zufriedenheit des Kunden steht bei uns immer an oberster "
					+ "Stelle.</p>"
					+ "<p id='main_text'>Wir sind der Meinung, dass auf eBay grunds�tzlich keine "
					+

					"negativen Bewertungen vergeben werden m�ssen.</p>"
					+ "<p id='main_text'>    Bitte nehmen Sie deswegen bei jedem Problem zuerst Kontakt mit "
					+ "uns auf. Wir werden in jedem Fall eine unkomplizierte L�sung "
					+ "finden.</p>"
					+ "<p id='main_text'>&nbsp;</p>"
					+ "<p id='main_text'>&nbsp;</p>"
					+

					" <!-- ENDE TEXTLICHER INHALT -->"
					+ "</td>"
					+ "</tr>"
					+ "</table>" + "</body>" + "</HTML>";

			break;
		case 1:
			break;
		case 2:
			break;

		}

		return template;
	}
}