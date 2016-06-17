package models;


import com.avaje.ebean.*;

import javax.persistence.*;

@Entity
public class ExRate extends Model {

	@Id
	public Long id;
	
	@ManyToOne()
	public Product product;
	public double fromAmt;
	public double toAmt;
	public String fromCurrency;
	public String toCurrency ="INR";
	
	public double exRate;
	
	public static Finder<Long, ExRate> find = new Finder<Long,ExRate>(ExRate.class);
	
}
