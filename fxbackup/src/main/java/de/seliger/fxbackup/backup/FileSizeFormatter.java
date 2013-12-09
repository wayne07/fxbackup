package de.seliger.fxbackup.backup;

import java.text.NumberFormat;

public class FileSizeFormatter {

    private final NumberFormat numberFormat = NumberFormat.getInstance();

    public FileSizeFormatter() {
        numberFormat.setMaximumFractionDigits(2);
    }

    public String format(long length) {
        String result = "";
        String formattedSize = getFormattedSizeFor(length);
        result = formattedSize + getSuffixFor(length);
        System.out.println("FileBrowserTreeTableView.createSizeColumn(): " + result);
        return result;
    }

    private String getFormattedSizeFor(long length) {
        if (isBytes(length)) {
            return numberFormat.format(length);
        }
        if (isKiloBytes(length)) {
            return numberFormat.format(length / 1024);
        }
        if (isMegaBytes(length)) {
            return numberFormat.format(length / 1024 / 1024);
        }
        return numberFormat.format(length / 1024 / 1024 / 1024);
    }

    private String getSuffixFor(long length) {
        if (isBytes(length)) {
            return " B";
        }
        if (isKiloBytes(length)) {
            return " KB";
        }
        if (isMegaBytes(length)) {
            return " MB";
        }
        return " GB";
    }

    private boolean isBytes(long length) {
        return length < 1024;
    }

    private boolean isKiloBytes(long length) {
        return length < (1024 * 1024);
    }

    private boolean isMegaBytes(long length) {
        return length < (1024 * 1024 * 1024);
    }

    private boolean isGigaBytes(long length) {
        return length < (1024 * 1024 * 1024 * 1024);
    }
}
