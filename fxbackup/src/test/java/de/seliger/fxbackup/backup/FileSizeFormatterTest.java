package de.seliger.fxbackup.backup;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class FileSizeFormatterTest {

    private final FileSizeFormatter formatter = new FileSizeFormatter();

    @Test
    public void formatsBytes() {
        assertThat(formatter.format(42l), is("42 B"));
    }

    @Test
    public void formatsMoreBytes() {
        assertThat(formatter.format(1023l), is("1.023 B"));
    }

    @Test
    public void formats1024Bytes() {
        assertThat(formatter.format(1024l), is("1 KB"));
    }

    @Test
    public void formats1376Bytes() {
        assertThat(formatter.format(1024l), is("1,3 KB"));
    }

    @Test
    public void formats1048575Bytes() {
        assertThat(formatter.format(1048575l), is("1.023 KB"));
    }

    @Test
    public void formats1048576Bytes() {
        assertThat(formatter.format(1048576l), is("1 MB"));
    }

    @Test
    public void formats1073741823Bytes() {
        assertThat(formatter.format(1048575l), is("1.023 MB"));
    }

    @Test
    public void formats1073741824Bytes() {
        assertThat(formatter.format(1048576l), is("1 GB"));
    }

}
