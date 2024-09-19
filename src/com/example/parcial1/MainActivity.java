package com.example.parcial1;



import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	int nivel;
	
	Calculadora cal; 
	
	Button btnBasico, btnIntermedio , btnAvanzado; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		nivel=1;
		btnBasico=(Button) findViewById(R.id.button1);
		btnIntermedio=(Button) findViewById(R.id.button2); 
		btnAvanzado=(Button) findViewById(R.id.button3);
		
		
		cal= new Calculadora(0); 
		
		btnIntermedio.setEnabled(false); 
		btnAvanzado.setEnabled(false); 
	}
	
	public void basico(View Vista) {		
		Intent nuevaV= new Intent(this, OpeActivity.class);
		cal.setNivel(1); 
		nuevaV.putExtra("Calculadora", cal);
		//startActivity(nuevaV);
		startActivityForResult(nuevaV, 1); 
		nivel=1; 
	}
	
	public void intermedio(View Vista) {		
		Intent nuevaV= new Intent(this, OpeActivity.class);
		cal.setNivel(2); 
		nuevaV.putExtra("Calculadora", cal);
		//nuevaV.putExtra("nivel", 2);
		//startActivity(nuevaV);
		startActivityForResult(nuevaV, 1); 
	
	}
	
	public void avanzado(View Vista) {		
	 
		Intent nuevaV= new Intent(this, OpeActivity.class);
		cal.setNivel(3); 
		nuevaV.putExtra("Calculadora", cal);
		//nuevaV.putExtra("nivel", 3);
		
		startActivityForResult(nuevaV, 1); 
		//startActivity(nuevaV);
	}
	
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            // Comprobar desde qué nivel se retornó
            //int nivelRetornado = data.getIntExtra("nivel", -1);
        	cal= (Calculadora) data.getSerializableExtra("Calculadora"); 
        	switch (cal.getNivel()) {
			case 1:				
				btnBasico.setText(String.format("Basico  %.1f %%", cal.getPuntuacion()));
				break;
			case 2:				
				btnIntermedio.setText(String.format("Intermedio  %.1f %%", cal.getPuntuacion()));
				break;
			case 3:				
				btnAvanzado.setText(String.format("Avanzado  %.1f %%", cal.getPuntuacion()));
				break;
			default:
				break;
			}
        	
        	if(cal.getNivel()<=1 && cal.getPuntuacion()>=51){
        		btnIntermedio.setEnabled(true);
        	}
        	if(cal.getNivel()>=2 && cal.getPuntuacion()>=51){
        		btnAvanzado.setEnabled(true);
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
