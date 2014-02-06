package de.seliger.fxbackup.backup;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class FileNode {

    private final String filename;
    private final String fileSize;
    private final String modifiedDate;
    private BooleanProperty selected;

    private final DateFormat dateformat = SimpleDateFormat.getDateTimeInstance();
    private final FileSizeFormatter fileSizeFormatter = new FileSizeFormatter();
    private final File file;

    public FileNode(File file) {
        this.file = file;
        this.filename = file.getName();
        this.fileSize = fileSizeFormatter.format(file.length());
        this.modifiedDate = dateformat.format(new Date(file.lastModified()));
        this.selected = new SimpleBooleanProperty();
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

    public BooleanProperty getSelected() {
        return selected;
    }

    public void setSelected(BooleanProperty selected) {
        this.selected = selected;
    }

}
