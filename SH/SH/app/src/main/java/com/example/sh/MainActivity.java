package com.example.sh;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class MainActivity extends AppCompatActivity {

    private EditText txtFecha, txtActividades;
    private TextView labelModo;
    private View parentView;
    private SwitchMaterial switchTheme;
    private UserSettings settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtFecha = findViewById(R.id.txtFecha);
        txtActividades = findViewById(R.id.txtActividades);
        parentView = findViewById(R.id.parentView);
        switchTheme = findViewById(R.id.switchTheme);
        settings = (UserSettings) getApplication();
        labelModo = findViewById(R.id.labelModo);

        cargarSharedPreference();
        actualizarVista();
        switchListener();
    }

    private void switchListener() {
        switchTheme.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if (checked){
                    settings.setCustomTheme(UserSettings.DARK_THEME);
                } else {
                    settings.setCustomTheme(UserSettings.LIGHT_THEME);
                }
                SharedPreferences.Editor editor = getSharedPreferences(UserSettings.PREFERENCES, MODE_PRIVATE).edit();
                editor.putString(UserSettings.CUSTOM_NAME, settings.getCustomTheme());
                editor.apply();
                actualizarVista();
            }
        });
    }

    private void cargarSharedPreference() {
        SharedPreferences sp = getSharedPreferences(UserSettings.PREFERENCES, MODE_PRIVATE);
        String theme = sp.getString(UserSettings.CUSTOM_NAME, UserSettings.LIGHT_THEME);
        settings.setCustomTheme(theme);
    }

    private void actualizarVista() {
        final int black = ContextCompat.getColor(this, R.color.black);
        final int white = ContextCompat.getColor(this, R.color.white);
        final int dark = ContextCompat.getColor(this, R.color.dark);

        if (settings.getCustomTheme().equals(UserSettings.DARK_THEME)){
            parentView.setBackgroundColor(dark);
            txtFecha.setTextColor(white);
            txtActividades.setTextColor(black);
            labelModo.setTextColor(white);
            txtFecha.setHighlightColor(white);
            txtFecha.setHintTextColor(white);

            labelModo.setText("Modo Obscuro");
            switchTheme.setChecked(true);
        } else {
            parentView.setBackgroundColor(white);
            txtFecha.setTextColor(black);
            txtActividades.setTextColor(black);
            labelModo.setTextColor(black);
            txtFecha.setHighlightColor(black);
            txtFecha.setHintTextColor(black);

            labelModo.setText("Modo Claro");
            switchTheme.setChecked(false);
        }
    }

    public void guardar(View v){
        SharedPreferences sp = getSharedPreferences("agenda", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        String fecha = txtFecha.getText().toString();
        String resultado = txtActividades.getText().toString();
        editor.putString(fecha, resultado);
        editor.commit();
        txtFecha.setText("");
        txtActividades.setText("");
        Toast.makeText(this, "Se guardaron correctamente las actividades", Toast.LENGTH_SHORT).show();
    }

    public void consultar(View v){
        SharedPreferences sp =getSharedPreferences("agenda", Context.MODE_PRIVATE);
        String dato = sp.getString(txtFecha.getText().toString(),"");
        if(dato.equals("")){
            Toast.makeText(this, "No hay actividades para esa fecha", Toast.LENGTH_SHORT).show();
        } else {
            txtActividades.setText(dato);
        }
    }

    public void limpiarAgenda(View v){
        SharedPreferences sp = getSharedPreferences("agenda", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.apply();
        txtFecha.setText("");
        txtActividades.setText("");
    }
}