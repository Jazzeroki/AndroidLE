AndroidLE
=========

Android Lacuna Expanse client

Basic overview of plan

So I'm looking at using the following breakdown for the app

-Account manager, as a static final  in it's own package.  The purpose of it will be to save and arraylist of 
account info files and to retrieve the infor back and to allow for the modification or deletion of accounts.

-LEWrapper, Again a static final package of classes that the whole purpose of which will be for the creation 
of json strings.  I'll need to revisit this as my current setup requires you to create an object first.  
The structure and function setup will match the api docs.

-LEComs Service, a service that will handle all of our comunications to and from the server.  
It will notify activities of responses.  the activities will be responsible for dealing with the replies.

-Response Class and Gson- Used for deserializing in conjunction with Gson something like
Response r = Gson.fromJson(JsonString, Response.class);

-AccountLoaderActivity- used for loading and switching which account is in use

-MailActivity, used to show messages.  I would default it to correspondence but then have options to 
select different tags or to even show all.  I think we may need a mail service as well to to record
messages retrieved by the LEComs service and that would also post notifications of correspondence 
recieved or non-ai attacks.  I think such a service may also be needed to tied to a scheduler to 
download new messages I think I would set that to occur when the device connects to a network, or 
about every 3 hours.  

For the mail I would display them in a listview.  I like the idea that in the gmail client you can 
swipe them to the left to delete a message.  


At the top of the screen later on I was considering adding some image buttons for planets, map, 
mail, and empire wide methods.  But those options I would want to save for later after we get the 
mail client working and nicely polished.  
