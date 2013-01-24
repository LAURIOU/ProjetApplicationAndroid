package com.esaip.projetcpi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.esaip.projetcpi.utils.InputStreamToString;

/**
 * @author Matthieu LAURIOU
 * @author Christian DONGMO
 *
 */
public class ListeMessageEnvoye extends Activity {
  /** Declaration de la liste de messages **/
  private ListView myList;
  
  /** Declaration du bouton de rafraichissement **/
  private Button buttonRafraichir;
  
  /** Declaration du login et mot de passe **/
  private String username;
  private String password;

  /** Fonction appeler lors de la création de la classe **/
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_list_messages);
    
    myList = (ListView)findViewById(R.id.listMessage);
    
    /** Definit le bouton rafraichir **/
    buttonRafraichir = (Button) findViewById(R.id.buttonRafraichir);
    
    /** Recuperation des identifiants et mots de passe **/
    username = getIntent().getStringExtra("name");
	password = getIntent().getStringExtra("password");
	
	new listerMessages().execute(username, password, "");
	
	buttonRafraichir.setOnClickListener(new OnClickListener() {
		public void onClick(View v) {
			// TODO Auto-generated method stub
			new listerMessages().execute(username, password, "");
		}
	});
  }
  
  /** Classe qui permet de venir interroger le serveur 
	 * et de recuperer la liste des messages envoyes **/
  private class listerMessages extends AsyncTask<String, String, String> {

		public static final String SERVER = "parlezvous.herokuapp.com";
		
		@Override
		protected void onPreExecute() {
		}
		
		@Override
		protected String doInBackground(String... params) {
			String username = params[0];
			String password = params[1];

			DefaultHttpClient client = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet("http://"+ SERVER + "/messages/" + username + "/" + password);

			String content = null;
			try {
				HttpResponse response = client.execute(httpGet);
				HttpEntity entity = response.getEntity();
				content = InputStreamToString.convert(entity.getContent());
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			return content;
		}

		@Override
		protected void onPostExecute(String result) {
			//Creation de la liste			
			List<Message> list = new ArrayList<Message>();
			
			String[] contentSplit = result.split(";");

			for (int i = 0; i < contentSplit.length; i++) {
				String[] strNameMessage = contentSplit[i].split(":");
				Message message = new Message(strNameMessage[0], strNameMessage[1]);
				list.add(message);
			}
			
			MessageAdapter adapter = new MessageAdapter(getApplicationContext(), list);
			
			myList.setAdapter(adapter);
		}
	}
}