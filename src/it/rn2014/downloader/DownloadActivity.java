package it.rn2014.downloader;
 
import it.rn2014.R;
import it.rn2014.db.DataBaseManager;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
 
public class DownloadActivity extends Activity {
 
    // button to show progress dialog
    Button btnShowProgress;
    // Progress Dialog
    private ProgressDialog pDialog;
    // Progress dialog type (0 - for Horizontal progress bar)
    public static final int progress_bar_type = 0; 
 
    // File url to download
//    private static String file_url = "http://www.jesolo1.com/sites/all/themes/fresh/logo.png";
    private static String file_url = "http://www.jesolo1.com/" + DataBaseManager.DB_NAME;
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_aggiornamento);
 
        // show progress bar button
        btnShowProgress = (Button) findViewById(R.id.btnProgressBar);
        /**
         * Show Progress bar click event
         * */
        btnShowProgress.setOnClickListener(new View.OnClickListener() {
 
            @Override
            public void onClick(View v) {
                // starting new Async Task
                new DownloadFileFromURL().execute(file_url);
            }
        });
    }
 
    /**
     * Showing Dialog
     * */
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
        case progress_bar_type: // we set this to 0
            pDialog = new ProgressDialog(this);
            pDialog.setMessage("Downloading file. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setMax(100);
            pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pDialog.setCancelable(true);
            pDialog.show();
            return pDialog;
        default:
            return null;
        }
    }
 
    /**
     * Background Async Task to download file
     * */
    class DownloadFileFromURL extends AsyncTask<String, String, String> {
 
        /**
         * Before starting background thread
         * Show Progress Bar Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showDialog(progress_bar_type);
        }
 
        /**
         * Downloading file in background thread
         * */
        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                URL url = new URL(f_url[0]);
                URLConnection conection = url.openConnection();
                conection.connect();
                // this will be useful so that you can show a tipical 0-100% progress bar
                int lenghtOfFile = conection.getContentLength();
 
                // download the file
                InputStream input = new BufferedInputStream(url.openStream(), 8192);
                OutputStream output = new FileOutputStream("/data/data/" + getPackageName() + DataBaseManager.DB_NAME );
 
                byte data[] = new byte[1024];
                long total = 0;
 
                while ((count = input.read(data)) != -1) {
                    total += count;
                    // publishing the progress....
                    // After this onProgressUpdate will be called
                    publishProgress(""+(int)((total*100)/lenghtOfFile));
 
                    // writing data to file
                    output.write(data, 0, count);
                }
 
                // flushing output
                output.flush();
 
                // closing streams
                output.close();
                input.close();
 
            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }
 
            return null;
        }
 
        /**
         * Updating progress bar
         * */
        protected void onProgressUpdate(String... progress) {
            // setting progress percentage
            pDialog.setProgress(Integer.parseInt(progress[0]));
       }
 
        /**
         * After completing background task
         * Dismiss the progress dialog
         * **/
        @Override
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after the file was downloaded
//            dismissDialog(progress_bar_type);
        	String pathfile = "/data/data/" + getPackageName() + DataBaseManager.DB_NAME ;
        	
        	DataBaseManager databaseManager = new DataBaseManager(getApplicationContext());
        	deleteDataBase(pathfile);
            pDialog.dismiss();
            
        }
        
    	private boolean deleteDataBase(String pathfile){
    		boolean deleteFile = false ;
    		File dbFile = new File(pathfile);
    		if(dbFile.exists()){
    			deleteFile = dbFile.delete();
    		}
    		return deleteFile;
    	}
 
    }
}