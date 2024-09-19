package com.example.parcial1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class OpeActivity extends Activity {
	Calculadora  cal; 
	TextView txtNivel, txtPromedio; 
	Button NextNivel;
	TextView mensaje; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ope);
		Intent intent = getIntent();
		txtNivel = (TextView) findViewById(R.id.textView1);
		txtPromedio=(TextView) findViewById(R.id.textView3); 
		//cal= new Calculadora(nivel);
		cal = (Calculadora) intent.getSerializableExtra("Calculadora");
		txtNivel.setText("Nivel: "+cal.getNivelLiteral()); 
		txtPromedio.setText(String.format("Puntuacion: %.1f%%", cal.getPuntuacion())); 
		txtNivel.setText("Nivel: "+cal.getNivelLiteral());
		mensaje=(TextView) findViewById(R.id.textView4); 
		
		NextNivel=(Button) findViewById(R.id.button6);
		if(cal.getPuntuacion()<51){
			NextNivel.setEnabled(false);
			NextNivel.setVisibility(View.GONE); 
		}
		if(cal.getNivel()==3){
			NextNivel.setVisibility(View.GONE);
			mensaje.setVisibility(View.GONE); 
			
		}
		
	}
	
	public void suma(View vista ) {
		Intent nuevaV= new Intent(this, SelectOpeActivity.class);
		cal.setOpe('+'); 
		nuevaV.putExtra("Calculadora",cal);  
		//startActivity(nuevaV);
		startActivityForResult(nuevaV, 1); 
	}
	public void resta(View vista ) {
		Intent nuevaV= new Intent(this, SelectOpeActivity.class);
		cal.setOpe('-'); 
		nuevaV.putExtra("Calculadora",cal); 
		startActivityForResult(nuevaV, 1);
		//startActivity(nuevaV); 
	}
	public void multiplicacion(View vista ) {
		Intent nuevaV= new Intent(this, SelectOpeActivity.class);
		cal.setOpe('*'); 
		nuevaV.putExtra("Calculadora",cal);
		startActivityForResult(nuevaV, 1);
		//startActivity(nuevaV); 
	}
	public void division(View vista ) {
		Intent nuevaV= new Intent(this, SelectOpeActivity.class);
		cal.setOpe('/'); 
		nuevaV.putExtra("Calculadora",cal); 
		startActivityForResult(nuevaV, 1);
		//startActivity(nuevaV); 
	}
	
	public void volver(View vista) {
		Intent resultIntent = new Intent();
        //resultIntent.putExtra("Puntuacion", cal.getNivel());
        
        resultIntent.putExtra("Calculadora", cal);
        
        setResult(RESULT_OK, resultIntent);
        finish();
	}
	
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
        	
        	cal= (Calculadora) data.getSerializableExtra("Calculadora"); 
        	txtPromedio.setText(String.format("Puntuacion: %.1f %% ", cal.getPuntuacion())); 
        	
        	if(cal.getPuntuacion()>50){
        		NextNivel.setVisibility(View.VISIBLE); 
        		NextNivel.setEnabled(true); 
        	}else {
        		NextNivel.setEnabled(false);
        		NextNivel.setVisibility(View.GONE); 
        	}
        	
        	if(cal.getNivel()==3){
    			NextNivel.setVisibility(View.GONE);
    			mensaje.setVisibility(View.GONE); 
    		}
        }
    }

		
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
