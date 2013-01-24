/**
 * 
 */
package com.esaip.projetcpi;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @author Matthieu LAURIOU
 * @author Christian DONGMO
 *
 */
public class MessageAdapter extends BaseAdapter{
	
	private List<Message> list;
	private LayoutInflater inflater;
	
	public MessageAdapter(Context context,List<Message> list) {
		 
		inflater = LayoutInflater.from(context); 
		this.list = list; 
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	/** Classe qui permet de memoriser les elements de la liste **/
	private class ViewHolder {
		TextView expediteur;
		TextView message; 
		}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;

		if(convertView == null) {
			holder = new ViewHolder();
			
			/** Implemente la vue sans parametrer les valeurs **/
			convertView = inflater.inflate(R.layout.simple_ligne, null);
			
			/** On recupere les expediteur et les messages pour les integrer dans la vue **/
			holder.expediteur = (TextView) convertView.findViewById(R.id.expediteur);
			holder.message = (TextView) convertView.findViewById(R.id.message);
	
			/** Parametrage de la vue **/
			convertView.setTag(holder);
	 
		}
		else
		{
			holder = (ViewHolder) convertView.getTag();
		}
		
		/** Parametrage du texte dans la vue **/
		holder.expediteur.setText(list.get(position).getUser());
		holder.message.setText(list.get(position).getMessage());
		
		/** Retourne la vue parametree **/
		return convertView;
	}
}
