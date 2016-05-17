/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dropboxclient;

import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxException;
import java.io.IOException;

/**
 *
 * @author NOMAN
 */
public class Main {
    public static void main(String[] args) {
        DropboxManager dropboxManager = new DropboxManager();
        
        try{
            DbxClient client = dropboxManager.getDbxClient();
            dropboxManager.uploadFile(client, "C:\\Users\\NOMAN\\Desktop\\RHR_2015-06-04.sql", "RHR_2015-06-04.sql");
            //dropboxManager.readFile(client, "RHR_2015-06-04.sql");
            
        }catch(DbxException e){
            e.printStackTrace();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
}
