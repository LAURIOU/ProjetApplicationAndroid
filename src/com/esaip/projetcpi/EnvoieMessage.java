package com.esaip.projetcpi;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.esaip.projetcpi.utils.InputStreamToString;

/**
 * @author Matthieu LAURIOU
 * @author Christian DONGMO
 *
 */
public class EnvoieMessage extends Activity {
	
	/** Declaration des elements privee de la classe **/
	private Button buttonEnvoyer;
	private EditText editMessage;
	private String nom;
	private String password;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_message);
		//On affiche le bouton d'envoie et le champ de saisie du message
		buttonEnvoyer = (Button) findViewById(R.id.buttonEnvoyer);
		editMessage = (EditText) findViewById(R.id.editMessage);
		
		/**
		 * on reccupere le nom et le mot de passe de l'utilisateur
		 * pour les parametres d'envoie de message
		 */
		nom = getIntent().getStringExtra("name");
		password = getIntent().getStringExtra("password");
		
		/**
		 * On implemente un listener sur le bouton envoyer, qui verifiera que le message
		 * a ete envoye, appelera la classe EnvoieMessage et affichera une confirmation
		 * d'envoi si le message n'est pas vide, ou un message d'erreur dans le cas contraire
		 */
		
		buttonEnvoyer.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String sms = editMessage.getText().toString();
				
				if(sms.length() > 0){
					new EnvoiMessage().execute(nom, password, sms);
					
					//On efface l'EditText
					editMessage.setText("");
				}else {
					Toast.makeText(EnvoieMessage.this, "Entrez un message!!!", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
	
	
	private class EnvoiMessage extends AsyncTask<String, String, String> {

		public static final String SERVER = "parlezvous.herokuapp.com";
		
		@Override
		protected void onPreExecute() {
		}
		
		/**
		 * On passe en paramètre le nom, le ot de passe et le message
		 * a la fonction doInBackground
		 */
		@Override
		protected String doInBackground(String... params) {
			String username = params[0];
			String password = params[1];
			String message = params[2];

			DefaultHttpClient client = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet("http://"+ SERVER + "/message/" + username + "/" + password + "/" + message);

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
			
			Intent intent = new Intent(EnvoieMessage.this, MessageActivity.class);
			intent.putExtra("name", nom);
			intent.putExtra("password", password);
			startActivity(intent);
			Toast.makeText(EnvoieMessage.this, "Message envoye !!!", 
					Toast.LENGTH_SHORT).show();
		}
		
	}

}
