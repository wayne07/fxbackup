package de.seliger.fxbackup.backup;

import java.io.File;

public class FileNode {

	private final String filename;
	private final String fileSize;
	private final String modifiedDate;

	private final FileSizeFormatter fileSizeFormatter = new FileSizeFormatter();
	private final File file;

	public FileNode(File file) {
		this.file = file;
		this.filename = file.getName();
		this.fileSize = Long.toString(file.length());
		this.modifiedDate = fileSizeFormatter.format(file.lastModified());
	}

	public String getFilename() {
		return filename;
	}

	public String getFileSize() {
		return fileSize;
	}

	public String getModifiedDate() {
		return modifiedDate;
	}

	public boolean isDirectory() {
		return file.isDirectory();
	}

	public File[] listFiles() {
		return file.listFiles();
	}

}
