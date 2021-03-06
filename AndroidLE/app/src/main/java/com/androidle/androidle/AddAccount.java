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

import com.google.gson.Gson;

import AccountMan.AccountInfo;
import JavaLEWrapper.Empire;
import LEWrapperResponse.Response;
import Server.AsyncServer;
import Server.ServerRequest;
import Server.serverFinishedListener;


public class AddAccount extends Activity implements serverFinishedListener {


    private AccountInfo account = new AccountInfo();

    @Override
    public void onResponseRecieved(String reply) {
        //This is the listener to the server event
        //if server request errors it returns error

        if(!reply.equals("error")) {
            Context context = getApplicationContext();
            CharSequence text = reply;
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            EditText serverReply = (EditText) findViewById(R.id.editTextServerReply);
            serverReply.setText((CharSequence) serverReply, EditText.BufferType.NORMAL);

            //Deserializing response and pulling session data out
            Response r = new Gson().fromJson(reply, Response.class);

            account.sessionID = r.result.session_id;

            AccountMan.AccountMan a = new AccountMan.AccountMan();
            a.AddAccount(account);
        }

    }
    public void ToastPopper(String t){ //This method is only for development testing puposes
        Context context = getApplicationContext();
        CharSequence text = t;
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        account.userName = etusername.getText().toString();
        EditText etpassword = (EditText)findViewById(R.id.password);
        account.password = etpassword.getText().toString();
        RadioGroup rg=(RadioGroup)findViewById(R.id.radioGroupServerSelection);
        CheckBox cbdfAccount = (CheckBox)findViewById(R.id.checkBoxMakeDefault);
        account.defaultAccount = cbdfAccount.isChecked();

        String server ="";
        int serverid = rg.getCheckedRadioButtonId();
        switch (serverid) {
        	case 0: account.server = "https://us1.lacunaexpanse.com";
        		break;
        	case 1: account.server = "https://pt.lacunaexpanse.com";
        		break;
        }
        //if all required fields are filled in then the request will be sent to the server
        if(!account.userName.isEmpty()&&!account.password.isEmpty()&&!account.server.isEmpty()){
            Empire e = new Empire();
            String request = e.Login(account.userName, account.password, 1);
            ServerRequest sRequest = new ServerRequest(server, Empire.url, request);
            AsyncServer s = new AsyncServer();
            s.addListener(this);
            s.execute(sRequest);
            //AsyncServer clears all listeners after the requests have been recieved.
        }

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
