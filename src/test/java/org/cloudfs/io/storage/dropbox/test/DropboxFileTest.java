package org.cloudfs.io.storage.dropbox.test;

import static junit.framework.Assert.*;

import org.cloudfs.io.File;
import org.cloudfs.io.FileEntry;
import org.cloudfs.io.storage.dropbox.DropboxFS;
import org.junit.Test;

import com.dropbox.client2.DropboxAPI.Account;
import com.dropbox.client2.exception.DropboxException;
import com.dropbox.client2.session.WebAuthSession;

public class DropboxFileTest  extends DropboxTestSupport{
	
    @Test
    public void account_info() throws DropboxException  {

        Account info = getApi().accountInfo();
        assert info.country != null : "No country for account";
        assert info.displayName != null : "No displayName for account";
        assert info.quota > 0 : "0 quota in account";
        assert info.quotaNormal > 0 : "0 normal quota in account";
        assert info.referralLink != null : "No referral link for account";
        assert info.uid > 0 : "No uid for account";
    }
    
    @Test
    public void mount() {
    	FileEntry entry = new DropboxFS<WebAuthSession>(getApi()).mount();
    	
    	assertNotNull(entry);
    	assertTrue(entry.isDirectory());
    	
    	for(File file : entry) {
    		LOG.info("Name: {}, size: {}, path: {}", new Object[]{file.name(), file.size(), file.absolutePath()});
    	}
    }    
}