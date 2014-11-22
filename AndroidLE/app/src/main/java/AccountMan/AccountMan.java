package AccountMan;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.io.File;
import java.io.PrintWriter;

/**
 * Notes on usage
 * DeleteAccount and ModifyAccount both assume that a check has been made for the existance of an account file
 * Load and AddAccount will both meet the requirements for this check.
 * Support for a default account still needs to be added.  There is meant to be only a single default account
 */
public class AccountMan {
    private static Gson gson = new Gson();
    public static AccountInfo GetDefaultAccount(){
        Accounts a = Load();
        for(AccountInfo i: a.accounts){
            if(i.defaultAccount)
                return i;
        }
        return null;
    }
    public static void AddAccount(String username, String password, String server, String aPIKey, String sessionID, String sessionDate, Boolean defaultAccount){
        Accounts accounts = new Accounts();
        if(CheckForFile())
            accounts = Load();
        //if an account is being set as default this will reset all other accounts and then a default account will be set later
        if(defaultAccount){
            for(AccountInfo i: accounts.accounts){
                i.defaultAccount = false;
            }
        }
        //in case there is only 1 account in the file this ensures that one account will be default
        if(accounts.accounts.size() == 1)
            defaultAccount = true;
        AccountInfo a = new AccountInfo(username, password, server, aPIKey, sessionID, sessionDate, defaultAccount);
        accounts.accounts.add(a);
        Save(accounts);
    }
    //This method assumes a check has already been made for the existance of the accounts file
    public static void DeleteAccount(String username, String server){
        Accounts accounts = Load();
        for(AccountInfo i: accounts.accounts){
            if(i.userName.equals(username)&& i.server.equals(server) ){
                accounts.accounts.remove(accounts.accounts.indexOf(i));
                break;
            }
        }
    }
    //This method assumes you've already checked for the existance of an account file
    public static void ModifyAccount(String username, String password, String server, String aPIKey, String sessionID, String sessionDate, Boolean defaultAccount){
        AccountInfo a = new AccountInfo(username, password, server, aPIKey, sessionID, sessionDate, defaultAccount);
        Accounts accounts = Load();
        //if an account is being set as default this will reset all other accounts and then a default account will be set later
        if(defaultAccount){
            for(AccountInfo i: accounts.accounts){
                i.defaultAccount = false;
            }
        }
        for(AccountInfo i: accounts.accounts){
            if(i.userName.equals(username)&& i.server.equals(server) ){
                accounts.accounts.remove(accounts.accounts.indexOf(i));
                break;
            }
        }
        accounts.accounts.add(a);
        Save(accounts);
    }
    private static void Save(Accounts accounts){

        String i = gson.toJson(accounts, Accounts.class);
        try {
            PrintWriter writer = new PrintWriter("acnt.jazz");
            //System.out.println("saving");
            writer.print(i);
            //System.out.println("closing writer");
            writer.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public static Accounts Load(){
        Accounts accounts=new Accounts();
        //if(!new File("accounts.jazz").isFile()) //if an account file doesn't exist one is created
        //    CreateAccount();
        //else{
        BufferedReader br;
        String i = "";
        try{
            br = new BufferedReader(new FileReader("acnt.jazz"));
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
        return accounts;
    }
    private static boolean CheckForFile(){
        return new File("acnt.jazz").isFile();
    }

}
class Accounts{
    Accounts(){
        accounts = new ArrayList<AccountInfo>();
    }
    ArrayList<AccountInfo> accounts;
}
class AccountInfo{
    AccountInfo(String userName, String password, String aPIKey, String server, String sessionID, String sessionDate, Boolean defaultAccount){
        this.userName = userName;
        this.password = password;
        this.aPIKey = aPIKey;
        this.server = server;
        this.sessionID = sessionID;
        this.sessionDate = sessionDate;
        this.defaultAccount = defaultAccount;
    }
    String userName, password, aPIKey, server, sessionID, sessionDate;
    Boolean defaultAccount;
}
