package Res;

public class rundaufeckig {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		float a,b,d,l,f,e,dicke;
		a = 0;
		b = 0;
		d = 0;
		l = 0;
		f = 0;
		e = 0;
		dicke = 0;
		
		
		
		
		
		float temp;
		
		if((d-a)/2 !=f)		/*Grundsymetrie nicht gegeben*/
		  {
		    if((d-b)/2 ==e)		/*Aber Symetrie vorhanden*/
		    {				/*Werkstück drehen*/
		      temp = a;
		      a = b;
		      b = temp;
		      temp = e;
		      e = f;
		      f = temp;
		    };
		  };
		  

	}

}
