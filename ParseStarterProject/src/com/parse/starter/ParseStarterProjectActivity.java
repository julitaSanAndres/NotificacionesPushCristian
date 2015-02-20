package com.parse.starter;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.SaveCallback;

public class ParseStarterProjectActivity extends Activity {

	private RadioGroup rg;
	private RadioButton r1, r2, r3;
	private CheckBox goles, noticias;
	private EditText mensaje;

	/** Called when the activity is first created. */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		rg = (RadioGroup) findViewById(R.id.radioGroup1);
		r1 = (RadioButton) findViewById(R.id.radio0);
		r2 = (RadioButton) findViewById(R.id.radio1);
		r3 = (RadioButton) findViewById(R.id.radio2);
		goles = (CheckBox) findViewById(R.id.cb_goles);
		noticias = (CheckBox) findViewById(R.id.cb_noticias);
		mensaje = (EditText) findViewById(R.id.et_mensaje);

		ParseAnalytics.trackAppOpenedInBackground(getIntent());

		
		//Seleccina una opción del campo definidos por nosotros Equipo
		rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if (checkedId == R.id.radio0) {
					ParseInstallation.getCurrentInstallation().put("Equipo",
							"Real Madrid");
				} else if (checkedId == R.id.radio1) {
					ParseInstallation.getCurrentInstallation().put("Equipo",
							"Barcelona");
				} else if (checkedId == R.id.radio2) {
					ParseInstallation.getCurrentInstallation().remove("Equipo");
				}

				ParseInstallation.getCurrentInstallation().saveInBackground(
						new SaveCallback() {

							@Override
							public void done(ParseException e) {
								// TODO Auto-generated method stub

							}
						});
			}

		});

		//Suscribirse o darse de baja del canal goles
		goles.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (goles.isChecked()) {
					ParsePush.subscribeInBackground("Goles");
				} else {
					ParsePush.unsubscribeInBackground("Goles");
				}

			}
		});

		//Suscribirse o darse de baja del canal noticias
		noticias.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (noticias.isChecked()) {
					ParsePush.subscribeInBackground("Noticias");
				} else {
					ParsePush.unsubscribeInBackground("Noticias");
				}

			}
		});

	}

	@Override
	public void onStart() {
		super.onStart();
	
		displayUserProfile();

	}

	// Obtiene los datos del cliente y los actualiza para que se muestren en pantalla
	private void displayUserProfile() {
		String equipo = ParseInstallation.getCurrentInstallation().getString(
				"Equipo");
		List<String> subscribedChannels = ParseInstallation
				.getCurrentInstallation().getList("channels");

		if (equipo != null) {
			r1.setChecked(equipo.equalsIgnoreCase("Real Madrid"));
			r2.setChecked(equipo.equalsIgnoreCase("Barcelona"));
		} else {
			r1.setChecked(false);
			r2.setChecked(false);
			r3.setChecked(true);
		}

		goles.setChecked(subscribedChannels.contains("Goles"));

		noticias.setChecked(subscribedChannels.contains("Noticias"));

	}

	//Envia una notificacion desde cliente a los suscriptores de noticias
	public void enviarPush(View view) {
		ParsePush push = new ParsePush();
		push.setChannel("Noticias");
		String noticia = mensaje.getText().toString();
		mensaje.setText("");
		push.setMessage(noticia);
		push.sendInBackground();
	}
}
