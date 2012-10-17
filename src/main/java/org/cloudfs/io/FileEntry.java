package org.cloudfs.io;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.google.common.base.Preconditions;

public class FileEntry implements Iterable<File> {

	private final File file;
	
	public FileEntry(File file) {
		this.file = Preconditions.checkNotNull(file);
	}
	
	public String name() {
		return this.file().name();
	}

	@Override
	public Iterator<File> iterator() {
		List<File> list = Arrays.asList(this.file.list());
		return list.iterator();
	}

	public File file() {
		return this.file;
	}
	
	public boolean isDirectory() {
		return this.file().isDirectory();
	}
}
