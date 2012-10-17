/**
 * ====
 *     ====
 *         Copyright (c) 2012 Alessandro F. Leite
 *
 *         Permission is hereby granted, free of charge, to any person obtaining
 *         a copy of this software and associated documentation files (the
 *         "Software"), to deal in the Software without restriction, including
 *         without limitation the rights to use, copy, modify, merge, publish,
 *         distribute, sublicense, and/or sell copies of the Software, and to
 *         permit persons to whom the Software is furnished to do so, subject to
 *         the following conditions:
 *
 *         The above copyright notice and this permission notice shall be
 *         included in all copies or substantial portions of the Software.
 *
 *         THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 *         EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 *         MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 *         NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 *         LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 *         OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 *         WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *     ====
 *
 *     Copyright (c) 2012 Alessandro F. Leite
 *
 *     Permission is hereby granted, free of charge, to any person obtaining
 *     a copy of this software and associated documentation files (the
 *     "Software"), to deal in the Software without restriction, including
 *     without limitation the rights to use, copy, modify, merge, publish,
 *     distribute, sublicense, and/or sell copies of the Software, and to
 *     permit persons to whom the Software is furnished to do so, subject to
 *     the following conditions:
 *
 *     The above copyright notice and this permission notice shall be
 *     included in all copies or substantial portions of the Software.
 *
 *     THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 *     EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 *     MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 *     NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 *     LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 *     OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 *     WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * ====
 *
 * Copyright (c) 2012 Alessandro F. Leite
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package org.cloudfs.io.storage.dropbox;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.cloudfs.io.File;
import org.cloudfs.io.FileEntry;
import org.cloudfs.io.IOException;


import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.DropboxAPI.Entry;
import com.dropbox.client2.RESTUtility;
import com.dropbox.client2.exception.DropboxException;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

public class DropboxFile implements File {
	
	
	// ---------------------------------------
	// -       Dropbox API attributes        -
	// ---------------------------------------
	private final DropboxAPI<?> _api;
	private Entry _entry;
	
	
	// ---------------------------------------
	// -          File attributes            -
	// ---------------------------------------
	
	/** The file's name. */
	private String _name;
	
	/** Size of the file. */
	private long _size;
	
	/** True if this entry is a directory, or false if it's a file. */
	private boolean _directory;
	
	/**
     * Whether this entry has been deleted (hidden) but not removed from the metadata yet. 
     */
	private boolean _hidden;
	
	/** The file's MIME type. */
	private String _mimeType;
	
	/**
     * If a directory, the hash is its "current version". If the hash
     * changes between calls, then one of the directory's immediate
     * children has changed.
     */
	public String _hash;
	
    /**
     * Last modified date
     */
    public long _modified;
    
	/** The file's MIME type. */
	private String _path;
	
	/** The file's entries if this a directory. */
	private List<FileEntry> entries = new ArrayList<FileEntry>();
	
	// ---------------------------------------
	// -       Constructor Methods 			 -
	// ---------------------------------------
	
	public DropboxFile(DropboxAPI<?> api) {
		this._api = Preconditions.checkNotNull(api);
	}
	
	public DropboxFile(Entry root, DropboxAPI<?> api) {
		this(api);
		this._entry = Preconditions.checkNotNull(root);
		copy(_entry);
	}

	public DropboxFile (String name, DropboxAPI<?> api) {
		this(api);
		Preconditions.checkArgument(!Strings.isNullOrEmpty(name));
		this._name = name;
	}
	

	// ---------------------------------------
	// -          Factory Methods 			 -
	// ---------------------------------------
	
	public static File valueOf(Entry root, DropboxAPI<?> api) {
		return new DropboxFile(root, api);
	}
	

	public String mimeType(){
		return this._mimeType;
	}
	
	public long modified(){
		return this._modified;
	}
	
	// ---------------------------------------
	// -          File methods               -
	// ---------------------------------------
	
	@Override
	public long size() {
		return this._size;
	}

	@Override
	public File parent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String name() {
		return this._name;
	}

	@Override
	public String absolutePath() {
		return this._path;
	}

	@Override
	public boolean createNew() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public byte[] data() throws IOException{
		
		if (!exists())
			return new byte[0];
		
		try {
			return IOUtils.toByteArray(_api.getFileStream(this._path, this._hash));
		} catch (DropboxException exception) {
			throw new IOException(exception.getMessage(), exception);
		} catch (java.io.IOException exception) {
			throw new IOException(exception.getMessage(), exception);
		}
	}

	@Override
	public boolean delete() throws IOException{
		try {
			this._api.delete(this._path);
		} catch (DropboxException exception) {
			throw new IOException(exception.getMessage(), exception);
		}
		
		return true;
	}

	@Override
	public boolean moveTo(File file) {
		file.createNew();
		return false;
	}

	@Override
	public boolean rename(String name) throws IOException {
		try {
			this._api.copy(this._path, this._path + "/" + name);
		} catch (DropboxException exception) {
			throw new IOException(exception.getMessage(), exception);
		}
		return true;
	}

	@Override
	public boolean exists() {
		return this._entry != null;
	}

	@Override
	public boolean isDirectory() {
		return this._directory;
	}

	@Override
	public boolean isFile() {
		return !isDirectory();
	}

	@Override
	public boolean isHidden() {
		return this._hidden;
	}
	
	// ---------------------------------------
	// -        Comparable methods           -
	// ---------------------------------------

	@Override
	public int compareTo(File other) {
		return this.name().compareTo(other.name());
	}
	
	// ---------------------------------------
	// -          private methods            -
	// ---------------------------------------
	
	private void copy(Entry entry) {
		this._size = entry.bytes;
		this._directory = entry.isDir;
		this._hidden = entry.isDeleted;
		this._mimeType = entry.mimeType;
		this._name = entry.fileName();
		this._hash = entry.hash;
		this._modified = RESTUtility.parseDate(entry.modified).getTime();
		this._path = entry.path;
		
		if (entry.isDir) {
			for(Entry newEntry: entry.contents){
				this.entries.add(new FileEntry(new DropboxFile(newEntry, _api)));
			}
		}
	}
}