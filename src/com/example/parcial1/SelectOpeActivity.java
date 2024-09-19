package com.example.parcial1;

import java.util.ArrayList;
import java.util.HashSet;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class SelectOpeActivity extends Activity {
	Calculadora  cal; 
	TextView txtNivel; 
	TextView txtOpe , txtNum1, txtNum2, txtRes , txt_correc, txt_incorectos , txt_paginacion; 
	ArrayList<RadioButton> lista_opc; 
	
	private int correctos;
	private int incorrectos; 
	private int pag;
	private int pag_end; 
    
    RadioButton op1 , op2, op3,op4 ;
    HashSet<String> Opciones;
	String valor; 
	float solucion; 
	Button btnNext, btnMenu; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_ope);		
		txtNum1=(TextView)findViewById(R.id.textView4); 
		txtOpe=(TextView)findViewById(R.id.textView5); 
		txtNum2=(TextView)findViewById(R.id.textView6);
		txtRes = (TextView) findViewById(R.id.textView7);
		txt_correc = (TextView) findViewById(R.id.textView8);
		txt_incorectos = (TextView) findViewById(R.id.textView9);
		txt_paginacion = (TextView) findViewById(R.id.textView10);
		
		op1= (RadioButton) findViewById(R.id.radioButton1);
		op2= (RadioButton) findViewById(R.id.radioButton2); 
		op3= (RadioButton) findViewById(R.id.radioButton3); 
		op4= (RadioButton) findViewById(R.id.radioButton4);

		btnMenu = (Button) findViewById(R.id.button5);
		btnNext = (Button) findViewById(R.id.button6);
		
		Intent intent = getIntent();
		//int nivel= intent.getIntExtra("nivel",0);
		txtNivel = (TextView) findViewById(R.id.textView1);
	    cal = (Calculadora) intent.getSerializableExtra("Calculadora");
	    if(cal != null){	    	
	    	txtNivel.setText("Nivel: "+cal.getNivelLiteral());
	    	txtOpe.setText(" "+cal.getOpe()+" ");
//	    	cal.setPuntuacion(100.0f);	    	
	    } 
	    Opciones= new HashSet(); 
	    lista_opc = new ArrayList<RadioButton>(); 	    
	    lista_opc.add(op1);
	    lista_opc.add(op2); 
	    lista_opc.add(op3); 
	    lista_opc.add(op4); 
	    	    
	    valor=""; 
	    generar(null); 
	    solucion=-1.001f; 
	    
	    incorrectos=0; correctos=0;
	    pag=1 ; 
	    pag_end=5; 
	    txt_correc.setText("Correctos: 0");
	    txt_incorectos.setText("Incorrectos: 0");
	    txt_paginacion.setText(String.format("preg. %d de %d", pag,pag_end)); 	    
	  }
	
	 public void selecionado(View vista) {
			RadioButton r1= (RadioButton) vista; 
			r1.getId(); 			
			for (RadioButton btn : lista_opc){
				if(btn.getId()!=r1.getId()){
					btn.setChecked(false); 
				}
			}
			valor=r1.getText().toString(); 
			solucion=Float.parseFloat(valor); 
			//Toast.makeText(getApplicationContext(), ""+r1.getText(), Toast.LENGTH_SHORT).show(); 
			txtRes.setText("___");	
	 }
	
	 public void generar(View vista) {		
		 Opciones=cal.randomNumero(txtNum1, txtNum2, 4 );		 
		 int i=0; 
		 for(String x: Opciones){
			 if(i<lista_opc.size()){				 
				 lista_opc.get(i).setText(x);
			 }else {
				 break; 
			 }
			 i++; 
		 }
		 for (RadioButton btn : lista_opc){				
			 btn.setChecked(false); 				
		}
			
		 //txtRes.setText("___");
		 solucion=-1.001f; 
	}
	
	public void verificar(View vista) {
		if(solucion==-1.001f){
			txtRes.setText("Seleccione al menos uno");
		}else if(cal.getSol()==solucion){
			correctos++;
			if(cal.getOpe()=='/'){
				//txtRes.setText("Correcto : "+cal.getSol());	
				txtRes.setText("Correcto");
			}else{				
				//txtRes.setText(String.format("Correcto : %.0f", cal.getSol()));
				txtRes.setText(String.format("Correcto"));
			}
			txt_correc.setText(String.format("Corecto: %d", correctos));
			txt_correc.setTextColor(Color.parseColor("#039803"));
			txtRes.setTextColor(Color.parseColor("#039803")); 
		} else {
			incorrectos++; 
			if(cal.getOpe() !='/'){
				txtRes.setText(String.format("Incorrecto (sol: %.0f", cal.getSol())+")");	
			}else {
				txtRes.setText("Incorrecto (sol:"+cal.getSol()+")");
			}
			txt_incorectos.setText(String.format("Incorrecto: %d ", incorrectos));
			txt_incorectos.setTextColor(Color.parseColor("#f91736"));
			txtRes.setTextColor(Color.parseColor("#f91736")); 
		}
		if(solucion!=-1.001f){
			pag++; 
			if(pag>pag_end){					
					btnNext.setVisibility(View.GONE); 
					btnMenu.setText(String.format("Finalizar(%s)", cal.getOpe()));
					op1.setEnabled(false);
					op2.setEnabled(false); 
					op3.setEnabled(false); 
					op4.setEnabled(false); 
			}else {
				txt_paginacion.setText(String.format("preg. %d de %d", pag,pag_end)); 
				generar(null); 
			}			
		}
	
	}
	public void volver(View vista) {
		
		if(pag==pag_end && cal.getSol()==solucion){
			correctos++; 
		}
		
		Intent resultIntent = new Intent();
		//cal.setPuntuacion(100.0f); 
		if(cal.getOpeNumber()>=0){			
			cal.setPuntuacion(cal.getOpeNumber(), correctos); 
		}
        resultIntent.putExtra("Calculadora", cal);
        setResult(RESULT_OK, resultIntent);
		finish(); 
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}