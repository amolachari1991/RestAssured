package SerializationDeserialisation;

public class SerializationSyntax {
	
	  private String message; 
	  private String greet ;
	  
	  public String getMessage (){
	  	return message ;
	  }
	  public void setMessage(String message ) {
	  this.message = message ;
	  }
	  public String greet (){
	  	return greet ;
	  }
	  public void setGreet(String greet ) {
	  this.greet = greet ;
	  }
	  

	/*  {
	  "message":"",
	  "greet":""
	  }
	  
	  create Java object
	  
		SerializationSyntax p = new SerializationSyntax();		
		p.setMessage("Hello");
		p.setGreet("Hi");
	  
	  rest assured 
	  
	  given().body(p);// at runtime this java object 'p' will be converted into json body */
	
}


	 


