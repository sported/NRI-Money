package models;
import com.avaje.ebean.Model;

import javax.persistence.*;


public class ExRateRequest {
	
    
	
    public String fromCurrency;
	public Integer amount;
	public String toCurrency = "INR";

}
