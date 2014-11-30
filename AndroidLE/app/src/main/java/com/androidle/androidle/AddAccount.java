package com.androidle.androidle;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import JavaLEWrapper.Empire;
import Server.AsyncServer;
import Server.ServerRequest;
import Server.serverFinishedListener;


public class AddAccount extends Activity implements serverFinishedListener {
    @Override
    public void onResponseRecieved(String reply) {
        Context context = getApplicationContext();
        CharSequence text = "Hello toast!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    public void OnLoginClick(View v){
        EditText etusername = (EditText)findViewById(R.id.username);
        String username = etusername.getText().toString();
        EditText etpassword = (EditText)findViewById(R.id.password);
        String password = etpassword.getText().toString();
        RadioGroup rg=(RadioGroup)findViewById(R.id.radioGroupServerSelection);
        CheckBox cbdfAccount = (CheckBox)findViewById(R.id.checkBoxMakeDefault);
        String server ="";
        int serverid = rg.getCheckedRadioButtonId();
        switch (serverid) {
        	case -1: server = null;
        		break;
        	case 0: server = "https://us1.lacunaexpanse.com";
        		break;
        	case 1: server = "https://pt.lacunaexpanse.com";
        		break;
        }
        Empire e = new Empire();
        String request = e.Login(username, password, 1);
        ServerRequest sRequest = new ServerRequest(server, Empire.url, request);
        AsyncServer s = new AsyncServer();
        s.addListener(this);
        s.execute();
        //Server.Server s = new Server.Server().execute(sRequest);
        
        //dfAccount.
        //String radiovalue=  (RadioButton)this.findViewById(rg.getCheckedRadioButtonId()).getId();

        //AccountMan.AccountMan.AddAccount(username.getText(), password.getText());

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_account, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_add_account, container, false);
            return rootView;
        }
    }
}
