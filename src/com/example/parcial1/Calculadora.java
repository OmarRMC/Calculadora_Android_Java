package com.example.parcial1;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

import android.widget.TextView;

public class Calculadora  implements Serializable{
   private  char  ope;
   private  int nivel;
   private  Random random ; 
   
   private  float sol; 
   
   private String[] literal={"Basico","Intermedio","Avanzado"};
   
   private HashMap<Integer, Integer[]> puntuacion ;  
   public Calculadora(char ope , int  nivel) {
	  this.ope=ope ; 
	  this.nivel= nivel;
	  random = new Random();
	  sol=0;    
	  puntuacion= new  HashMap<Integer, Integer[]>(){{
		  put(1,new Integer[]{0,0,0,0});
		  put(2,new Integer[]{0,0,0,0});
		  put(3,new Integer[]{0,0,0,0});		  
	  }}; 
   }
   public Calculadora(int  nivel) {
		  this.nivel= nivel;
		  random = new Random();
		  sol=0;    
		  puntuacion= new  HashMap<Integer, Integer[]>(){{
			  put(1,new Integer[]{0,0,0,0});
			  put(2,new Integer[]{0,0,0,0});
			  put(3,new Integer[]{0,0,0,0});		  
		  }}; 
  }
   public String getNivelLiteral() {
	return literal[getNivel()-1]; 
   }
   public float getSol() {
	return sol;
   }
   public void setSol(float sol) {
	this.sol = sol;
   }
   
   public HashSet<String>  randomNumero(TextView num1, TextView num2 , int catidad ) {
	int a= random.nextInt((int)Math.pow(10, this.nivel));
	int b= random.nextInt((int)Math.pow(10, nivel)); 
	if(this.ope=='/'){
		while(b==0){
			b= random.nextInt((int)Math.pow(10, nivel));
		}
	}
	num1.setText(""+a);
	num2.setText(""+b);
	
	HashSet<String> conjunto = new HashSet<String>(); 
    boolean addSol=false;
    float  c=0; 
    String c1=""; 
	while(conjunto.size() < catidad){		
		c=random.nextInt((int)Math.pow(10, this.nivel+1));
		if(a<c && c<b && !addSol){
			c1=getSol(a,b);
			setSol(Float.parseFloat(c1)); 
			addSol=true; 			
		}else {
			if(this.ope =='/'){	
				c1=String.format("%.2f",c); 
			}else {
				c1=String.format("%.0f",c); 
			}					
		}
	     conjunto.add(c1); 
		if(!addSol && conjunto.size()==catidad-1){
			c1=getSol(a,b);
			setSol(Float.parseFloat(c1));
			conjunto.add(c1);
			addSol=true; 
		}
	}	
	return conjunto; 	
}


public  Float getPuntuacion() {
	Integer[] auxi =puntuacion.get(this.nivel);
	int suma=0; 
	for(int n:auxi){
		suma+=n ; 
	}
	// Cada operacion tiene 5 seleciones multiples 
	// en total de todas la opraciones seria 20  
	return suma*100/20.0f ;
}
public void setPuntuacion(int ope, int cantidad ) {
	// ope:0 es +
	//ope:1 es -
	//ope:2 es *
   Integer[] auxi = puntuacion.get(this.nivel); 
   try {	
	   auxi[ope]=cantidad;
   } catch (Exception e) {
	// TODO: handle exception
   }
   puntuacion.put(nivel, auxi);
   
}


public int getOpeNumber() {
	switch (getOpe()) {
	case '+':
		return 0 ; 
	case '-':
		return 1 ; 
	case '*':
		return 2 ; 
	case '/':
		return 3 ; 

	default:
		return -1; 
		
	}
}

public String getSol(int a , int b){
	float c=0 ; 
	String auxi=""; 
	switch (this.ope) {
	case '+':
		c=a+b; 		
		auxi=String.format("%.0f", c); 
		break;
	case '-':
		c=a-b;
		auxi=String.format("%.0f", c);
		break;
	case '*':
		c=a*b; 
		auxi=String.format("%.0f", c);
		break;
	case '/':
		if(b!=0){
			c=(float)a/b; 
			auxi=String.format("%.2f", c);
		}
		break;
	default:
		c=0; 
		break;
	
	}
	return auxi; 
}

public char getOpe() {
	return ope;
}

public void setOpe(char ope) {
	this.ope = ope;
}

public int getNivel() {
	return nivel;
}

public void setNivel(int nivel) {
	this.nivel = nivel;
}
   
   
   
}
