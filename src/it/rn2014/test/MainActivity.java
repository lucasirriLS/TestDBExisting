package it.rn2014.test;

import it.rn2014.R;
import it.rn2014.db.QueryManager;
import it.rn2014.downloader.Utils;
import it.rn2014.downloader.DownloadActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    
    public void SaveEmployee(View v)
    {
    	EditText txtName = (EditText)findViewById(R.id.txtName);
    	EditText txtEmail = (EditText)findViewById(R.id.txtEmail);
    	
    	String name = txtName.getText().toString();
    	String email = txtEmail.getText().toString();
    	
    	
    	QueryManager mDbHelper = new QueryManager(this);         
    	mDbHelper.createDatabase();       
    	mDbHelper.open(); 
    	
//    	if(mDbHelper.SaveEmployee(name, email))
//    	{
//    		Utils.ShowMessageBox(this,"Data saved.");
//    	}
//    	else
//    	{
//    		Utils.ShowMessageBox(this,"OOPS try again!");
//    	}
    }
    
    public void startDowload(View view){
    	Intent intent = new Intent(this,DownloadActivity.class);
    	startActivity(intent);
    	
    }
    
//    public void LoadEmployee(View v)
//    {
//    	QueryManager dataAdapter = new QueryManager(this);         
//    	dataAdapter.createDatabase();       
//    	dataAdapter.open(); 
//    	 
//    	Cursor testdata = dataAdapter.getDBCursor(); 
//    	 
//    	String nome = DBUtils.GetColumnValue(testdata, "nome");
//    	String codiceAgesci = DBUtils.GetColumnValue(testdata, "codiceAgesci");
//    	
//    	DBUtils.ShowMessageBox(this, "Name: "+ nome + " and Email: "+ codiceAgesci);
//    	dataAdapter.close();
//    	
//    }
	
	
}
