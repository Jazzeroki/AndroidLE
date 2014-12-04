package com.androidle.androidle;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import AccountMan.AccountInfo;


public class select_Account extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select__account);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_select__account, menu);
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
        private ArrayAdapter<AccountInfo> accounts;
        private ArrayList<AccountInfo> savedAccountsFromFile;
        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_select__account, container, false);

            /* Load Account File */
            ArrayList<AccountMan.AccountInfo> accountsArrayList = AccountMan.AccountMan.GetAccounts();

            accounts = new ArrayAdapter<AccountMan.AccountInfo>(
                    getActivity(),
                    //this,
                    R.layout.fragment_select_account,
                    R.id.select_account_account_list,
                    savedAccountsFromFile
            );

            return rootView;
            /*
            public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sunshine_test, container, false);

        String[] forcastArray = {
                "Today - Sunny 86/63",
                "Tomorrow - Windy 65/36",
                "Thursday - Cloudy 80/56"
        };

        List<String> weekForcast = new ArrayList<String>(Arrays.asList(forcastArray));

        //mForcastAdapter = new ArrayAdapter<String>
        mForcastAdapter = new ArrayAdapter<String>(
                //Gets this fragments parent activity
                getActivity(),
                //ID of the list item layout
                //R.layout.
                R.layout.list_item_forcast,
                //ID of the textview within the list item
                R.id.list_item_forcast_textview,
                //data for the adapter to adapt
                weekForcast);
        // Get a reference to the Listview and attach this adapter
        ListView listView = (ListView) rootView.findViewById(R.id.listview_forcast);
        listView.setAdapter(mForcastAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String forecast = mForcastAdapter.getItem(position);
                //leaving out example of how to make a toast
                //Toast.makeText(getActivity(), forecast, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), DetailActivity.class)
                        .putExtra(Intent.EXTRA_TEXT, forecast);
                //the items passed to putExtra is a key value pair, any key may be used
                //just as long as it's the same key EXTRA_TEXT is just a built in key
                startActivity(intent);
            }
        });

        return rootView;
    }

             */
        }
    }
}
