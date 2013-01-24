package com.esaip.projetcpi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * @author Matthieu LAURIOU
 * @author Christian DONGMO
 *
 */
public class MessageActivity extends Activity {
	
	/** Declaration des elements prives de la classe **/
	private final String TAG = MainActivity.class.getSimpleName();
	private TextView message;
	private Button listSmsButton;
	private Button newSmsButton;
	private ProgressBar loading;
	private String nom;
	private String password;
	
	/** Fonction appeler a la creation de la classe **/
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message);
		
		/** Recuperation des elements depuis le layout **/
		listSmsButton = (Button) findViewById(R.id.listMessage);
		newSmsButton = (Button) findViewById(R.id.envoieMessage);
		nom = getIntent().getStringExtra("name");
		password = getIntent().getStringExtra("password");
		message = (TextView) findViewById(R.id.pageAccueil2);
		message.setText("Boujour "+ nom);
		loading = (ProgressBar) findViewById(R.id.loading);
		
		/** Listener sur le bouton d'envoi d'un message **/
		newSmsButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				loading.setVisibility(View.VISIBLE);
				Intent intent = new Intent(MessageActivity.this, EnvoieMessage.class);
				intent.putExtra("name", nom);
				intent.putExtra("password", password);
				startActivity(intent);
			}
		});
		
		/** Listener sur le bouton de listage des messages **/
		listSmsButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				loading.setVisibility(View.VISIBLE);
				Intent intent = new Intent(MessageActivity.this, ListeMessageEnvoye.class);
				intent.putExtra("name", nom);
				intent.putExtra("password", password);
				startActivity(intent);
			}
		});
	
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getTitle().equals("Deconnexion")){
			finish();
		}
		return super.onOptionsItemSelected(item);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add("Deconnexion");
		return super.onCreateOptionsMenu(menu);
	}

	
	@Override
	public Intent getIntent() {
		return super.getIntent();
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return super.onTouchEvent(event);
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
}