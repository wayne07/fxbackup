package de.seliger.fxbackup.backup;

import java.io.File;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class FileNode {

	private final StringProperty filename;
	private final StringProperty fileSize;
	private final StringProperty modifiedDate;

	private final FileSizeFormatter fileSizeFormatter = new FileSizeFormatter();

	public FileNode(File file) {
		this.filename = new SimpleStringProperty(file.getName());
		this.fileSize = new SimpleStringProperty(Long.toString(file.getTotalSpace()));
		this.modifiedDate = new SimpleStringProperty(fileSizeFormatter.format(file.lastModified()));
	}

	public StringProperty getFilename() {
		return filename;
	}

	public StringProperty getFileSize() {
		return fileSize;
	}

	public StringProperty getModifiedDate() {
		return modifiedDate;
	}

}
