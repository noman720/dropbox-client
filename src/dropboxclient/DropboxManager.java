/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dropboxclient;

/**
 *
 * @author NOMAN
 */

// Include the Dropbox SDK.
import com.dropbox.core.*;
import java.io.*;
import java.util.Locale;

public class DropboxManager {

    public DbxClient getDbxClient() throws DbxException {
        // Get your app key and secret from the Dropbox developers website.
        final String APP_KEY = "ewhx37k0tdwfnop";  //"INSERT_APP_KEY"
        final String APP_SECRET = "b0p6ipfalhj2ckl"; //"INSERT_APP_SECRET"

        DbxAppInfo appInfo = new DbxAppInfo(APP_KEY, APP_SECRET);

        DbxRequestConfig config = new DbxRequestConfig("RHR-TELECOM-DESKTOP",
            Locale.getDefault().toString());
        DbxWebAuthNoRedirect webAuth = new DbxWebAuthNoRedirect(config, appInfo);
        
        webAuth.start();
        
        String accessToken = "Ux66zCnnB0AAAAAAAAAACAzssundwuwpkYO9ix7_apiZ2pK-C1RUcjcqv-3aKyAa";
        
        DbxClient client = new DbxClient(config, accessToken);
        
        return client;
    }
    
    public void uploadFile(DbxClient client, String fileUrl, String fileName) throws IOException, DbxException{
        
        boolean isAlreadyExistsFile = false;
        String revisionNo = "";
        //check if exist or not
        DbxEntry.WithChildren listing = client.getMetadataWithChildren("/");
        //System.out.println("Files in the root path:");
        for (DbxEntry child : listing.children) {
            //System.out.println("File Name: " + child.name + "\nDetails: " + child.toString());
            if(child.name.equals(fileName)){
                isAlreadyExistsFile = true;
                revisionNo = child.asFile().rev;
                break;
            }
        }
        
        File inputFile = new File(fileUrl);
        FileInputStream inputStream = new FileInputStream(inputFile);
        
        DbxEntry.File uploadedFile = null;
        try {
            if(isAlreadyExistsFile){
                uploadedFile = client.uploadFile("/"+fileName,
                DbxWriteMode.update(revisionNo), inputFile.length(), inputStream);
            }else{
                uploadedFile = client.uploadFile("/"+fileName,
                DbxWriteMode.add(), inputFile.length(), inputStream);
            }
            
            System.out.println("Uploaded: " + uploadedFile.toString());
        } finally {
            inputStream.close();
        }
    }
    
    public void readFile(DbxClient client, String fileName) throws IOException, DbxException{
        //check if exist or not
        DbxEntry.WithChildren listing = client.getMetadataWithChildren("/");
        System.out.println("Files in the root path:");
        for (DbxEntry child : listing.children) {
            System.out.println("File Name: " + child.name + "\nDetails: " + child.toString());
        }

//        FileOutputStream outputStream = new FileOutputStream("/"+fileName);
//        try {
//            DbxEntry.File downloadedFile = client.getFile("/"+fileName, null,
//                outputStream);
//            System.out.println("Metadata: " + downloadedFile.toString());
//        } finally {
//            outputStream.close();
//        }
    }
    
    
    
    
//    public static void main(String[] args) throws IOException, DbxException {
//        // Get your app key and secret from the Dropbox developers website.
//        final String APP_KEY = "dfs1w7zh438ys4n";  //"INSERT_APP_KEY"
//        final String APP_SECRET = "jh4k3yfbvjdtyay"; //"INSERT_APP_SECRET"
//
//        DbxAppInfo appInfo = new DbxAppInfo(APP_KEY, APP_SECRET);
//
//        DbxRequestConfig config = new DbxRequestConfig("JavaTutorial/1.0",
//            Locale.getDefault().toString());
//        DbxWebAuthNoRedirect webAuth = new DbxWebAuthNoRedirect(config, appInfo);
//
//        // Have the user sign in and authorize your app.
//        String authorizeUrl = webAuth.start();
//        System.out.println("1. Go to: " + authorizeUrl);
//        System.out.println("2. Click \"Allow\" (you might have to log in first)");
//        System.out.println("3. Copy the authorization code.");
//        //String code = new BufferedReader(new InputStreamReader(System.in)).readLine().trim();
//
//        // This will fail if the user enters an invalid authorization code.
//        //DbxAuthFinish authFinish = webAuth.finish(code);  //code = 9sYJxG7hYboAAAAAAAACojf3gtuXxpvZZiUqw6-eTOc
//        //String accessToken = authFinish.accessToken;
//        String accessToken = "9sYJxG7hYboAAAAAAAACpKE4ywv-BezMFU2XIzHPsV4gGX8NNkueVIm0QojXDW-V";
//
//        DbxClient client = new DbxClient(config, accessToken);
//
//        System.out.println("Linked account: " + client.getAccountInfo().displayName);
//
//        File inputFile = new File("local-file.txt");
//        FileInputStream inputStream = new FileInputStream(inputFile);
//        try {
//            DbxEntry.File uploadedFile = client.uploadFile("/magnum-opus.txt",
//                DbxWriteMode.add(), inputFile.length(), inputStream);
//            System.out.println("Uploaded: " + uploadedFile.toString());
//        } finally {
//            inputStream.close();
//        }
//
//        DbxEntry.WithChildren listing = client.getMetadataWithChildren("/");
//        System.out.println("Files in the root path:");
//        for (DbxEntry child : listing.children) {
//            System.out.println("	" + child.name + ": " + child.toString());
//        }
//
//        FileOutputStream outputStream = new FileOutputStream("magnum-opus.txt");
//        try {
//            DbxEntry.File downloadedFile = client.getFile("/magnum-opus.txt", null,
//                outputStream);
//            System.out.println("Metadata: " + downloadedFile.toString());
//        } finally {
//            outputStream.close();
//        }
//    }
}
