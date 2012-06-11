package de.broo.test.swt;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

public class swtTestFtpUtil {

	static String server = "www.kunsthaus-poorhosaini.de";
	static String username = "kunsthaus-6p";
	static String password = "Rhein@Kunst";
	static String ftpHomeDir = "httpdocs/ebay_rene";

	public swtTestFtpUtil() {

	}

	public static void uploadToFtp() {
		// TODO Auto-generated method stub
		int ftp_id = 0;
		FTPClient ftp = new FTPClient();

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
			// System.out.print(ftp.getReplyString());
			ftp.enterLocalPassiveMode();
			// System.out.print("Enter local passive mode.\n");
			ftp.changeWorkingDirectory(ftpHomeDir);
			ftp.pwd();
			// System.out.print(ftp.getReplyString());
			ftp.makeDirectory(swtTest.getProjectName());
			// System.out.print(ftp.getReplyString());
			ftp.changeWorkingDirectory(ftpHomeDir + "/"
					+ swtTest.getProjectName());

			File dir = new File(swtTest.selectedDir);
			String localSourceFile = null;
			String remoteResultFile = null;
			File[] fileList = dir.listFiles();

			int i = fileList.length;

			swtTest.showUploadInfo(fileList.length, i);
			for (File f : fileList) {

				if (!(f.getName().toString().equals("titel.jpg"))) {
					remoteResultFile = ftpHomeDir + "/"
							+ swtTest.getProjectName() + "/" + f.getName();

					localSourceFile = swtTest.getSelectedDir() + "\\"
							+ f.getName();

					upload(localSourceFile, remoteResultFile, server, 21,
							username, password, false);
					i--;
					swtTest.showUploadInfo(fileList.length, i);

					System.out.println("Upload -> " + f.getName());
				} else {
					System.out.println("Upload -> " + "titel.jpg übersprungen");

				}
			}
			System.out.println("Upload abgeschlossen");

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
	}

	public static int checkFTP() {
		int ftpDirCheck = 0;
		FTPClient ftp = new FTPClient();

		try {
			int reply;
			ftp.connect(server);
			ftp.login(username, password);

			// System.out.println("Connected to " + server + ".");
			// System.out.print(ftp.getReplyString());

			// After connection attempt, you should check the reply code to
			// verify
			// success.

			reply = ftp.getReplyCode();

			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				System.err.println("FTP server refused connection.");
				System.exit(1);
			}

			FTPFile[] files = ftp.listDirectories("/" + ftpHomeDir + "/");
			// System.out.print(ftp.getReplyString());
			for (FTPFile ftpFile : files) {

				if (ftpFile.getName().equals(swtTest.getProjectName())) {
					ftpDirCheck = 1;
				}

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

		return ftpDirCheck;
	}

	public static int countFTP() {
		int ftpDirCount = 0;
		FTPClient ftp = new FTPClient();

		try {
			int reply;
			ftp.connect(server);
			ftp.login(username, password);

			// System.out.println("Connected to " + server + ".");
			// System.out.print(ftp.getReplyString());

			// After connection attempt, you should check the reply code to
			// verify
			// success.

			reply = ftp.getReplyCode();

			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				System.err.println("FTP server refused connection.");
				System.exit(1);
			}

			FTPFile[] files = ftp.listFiles("/" + ftpHomeDir + "/"
					+ swtTest.getProjectName());

			ftpDirCount = files.length;
			System.out.println(ftpDirCount);
			// System.out.print(ftp.getReplyString());
			for (FTPFile ftpFile : files) {

				if (ftpFile.getName().equals(swtTest.getProjectName())) {

				}

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

		return ftpDirCount;
	}

	public static boolean upload(String localSourceFile,
			String remoteResultFile, String host, int port, String usr,
			String pwd, boolean showMessages) throws IOException {
		FTPClient ftpClient = new FTPClient();
		FileInputStream fis = null;
		boolean resultOk = true;

		try {
			ftpClient.connect(host, port);
			if (showMessages) {
				System.out.println(ftpClient.getReplyString());
			}
			resultOk &= ftpClient.login(usr, pwd);
			if (showMessages) {
				System.out.println(ftpClient.getReplyString());
			}

			resultOk &= ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			if (showMessages) {
				System.out.println(ftpClient.getReplyString());
			}

			// resultOk &= ftpClient.enterLocalPassiveMode();
			// if (showMessages) {
			// System.out.println(ftpClient.getReplyString());
			// }

			fis = new FileInputStream(localSourceFile);
			resultOk &= ftpClient.storeFile(remoteResultFile, fis);
			if (showMessages) {
				System.out.println(ftpClient.getReplyString());
			}
			resultOk &= ftpClient.logout();
			if (showMessages) {
				System.out.println(ftpClient.getReplyString());
			}
		} finally {
			try {
				if (fis != null) {
					fis.close();
				}
			} catch (IOException e) {/* nothing to do */
			}
			ftpClient.disconnect();
		}

		return resultOk;
	}

	public static String getFtpHomeDir() {
		return ftpHomeDir;
	}

	public static void setFtpHomeDir(String ftpHomeDir) {
		swtTestFtpUtil.ftpHomeDir = ftpHomeDir;
	}

}
