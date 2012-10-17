package org.cloudfs.io.storage.dropbox;

import org.cloudfs.io.FSManager;
import org.cloudfs.io.FileEntry;
import org.cloudfs.io.IOException;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.DropboxAPI.Entry;
import com.dropbox.client2.exception.DropboxException;
import com.dropbox.client2.session.Session;
import com.google.common.base.Preconditions;

public class DropboxFS<S extends Session> implements FSManager {
	
	private final DropboxAPI<S> api;
	
	public DropboxFS(DropboxAPI<S> api)
	{
		this.api = Preconditions.checkNotNull(api);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public FileEntry mount() throws IOException {
		try {
			Entry root = this.api.metadata("/", 0, null, true, null);
			return new FileEntry(DropboxFile.valueOf(root, api));
		} catch (DropboxException exception) {
			throw new IOException(exception);
		}
	}
}