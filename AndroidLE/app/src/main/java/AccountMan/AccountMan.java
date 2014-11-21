package AccountMan;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Alma on 11/17/2014.
 */
public class AccountMan {
    static Gson gson;
    public void CheckForAccounts(){

    }
    public void GetAccounts() {
        Accounts accounts=new Accounts();
        //if(!new File("accounts.jazz").isFile()) //if an account file doesn't exist one is created
        //    CreateAccount();
        //else{
        BufferedReader br = null;
        String i = "";
        try{
            br = new BufferedReader(new FileReader("accounts.jazz"));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                line = br.readLine();
                i = sb.toString();
            }
            accounts = gson.fromJson(i, Accounts.class);
        }catch (FileNotFoundException e){
           // CreateAccount();
        }catch(IOException e){

        }
    }
    public void AddAccount(String username, String Password, String Server){}
    public void DeleteAccount(){}
    public void ModifyAccount(){}

}
class Accounts{
    Accounts(){
        accounts = new ArrayList<AccountInfo>();
    }
    ArrayList<AccountInfo> accounts;
}
class AccountInfo{
    String userName, password, aPIKey, server, sessionID, sessionDate;
}