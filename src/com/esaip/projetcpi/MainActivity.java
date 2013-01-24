package com.esaip.projetcpi;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.esaip.projetcpi.R.id;
import com.esaip.projetcpi.utils.InputStreamToString;

/**
 * @author Matthieu LAURIOU
 * @author Christian DONGMO
 *
 */
public class MainActivity extends Activity {

	/** Declaration des elements privees de la classe MainActivity **/
	private final String TAG = MainActivity.class.getSimpleName();
	private EditText userNameField;
	private EditText userPasswordField;
	private Button viderButton;
	private Button validerButton;
	private ProgressBar loading;
	private TextView errorMessage;

	/** Fonction appeler a la creation de la classe **/
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(TAG, "onCreate!");
		setContentView(R.layout.activity_main);

		userNameField = (EditText) findViewById(R.id.editUserName);
		userPasswordField = (EditText) findViewById(R.id.editPassword);
		viderButton = (Button) findViewById(id.clearButton);
		validerButton = (Button) findViewById(id.validButton);
		loading = (ProgressBar) findViewById(R.id.loading);
		errorMessage = (TextView) findViewById(R.id.error_message);
		
		/** Listener sur le bouton de vidage du login et mot de passe **/
		viderButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				userNameField.setText("");
				userPasswordField.setText("");
			}
		});
		
		/** Listener sur le bouton de validation de connexion **/
		validerButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (hasEmptyFields()) {
					errorMessage.setVisibility(View.VISIBLE);
				} else {
					String username = userNameField.getText().toString();
					String password = userPasswordField.getText().toString();
					new MainTask().execute(username, password);
				}
			}
		});
	
	}

	/** Fonction qui verifie que le login et mot de passe soient saisis **/
	private boolean hasEmptyFields() {
		return TextUtils.isEmpty(userNameField.getText()) || TextUtils.isEmpty(userPasswordField.getText());
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.i(TAG, "onPause!");
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		Log.i(TAG, "onRestart!");
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		Log.i(TAG, "onRestoreInstanceState!");
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.i(TAG, "onResume!");
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		Log.i(TAG, "onSaveInstanceState!");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.i(TAG, "onDestroy!");
	}

	/** Classe qui permet de venir interroger le serveur 
	 * et d'autoriser la connexion ou non **/
	private class MainTask extends AsyncTask<String, String, Boolean> {
		
		public static final String SERVER = "http://parlezvous.herokuapp.com";
		private String username;
		private String password;
		
		@Override
		protected void onPreExecute() {
			loading.setVisibility(View.VISIBLE);
		}
		
		@Override
		protected Boolean doInBackground(String... params) {
			username = params[0];
			password = params[1];

			DefaultHttpClient client = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(SERVER + "/connect/" + username + "/" + password);

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

			return Boolean.valueOf(content);
		}

		@Override
		protected void onPostExecute(Boolean result) {
			loading.setVisibility(View.INVISIBLE);
			String message;
			if (result) {
				message = "Utilisateur connecte";
				Intent intent = new Intent(MainActivity.this, MessageActivity.class);
				intent.putExtra("name", username);
				intent.putExtra("password", password);
				SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
				editor.putString("username", username);
				editor.putString("password", password);
				editor.commit();
				startActivity(intent);
			} else {
				message = "Utilisateur non connecte";
			}
			Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
		}
	}
}