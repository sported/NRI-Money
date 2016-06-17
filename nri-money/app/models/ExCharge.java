package models;


import com.avaje.ebean.*;

import javax.persistence.*;

@Entity
public class ExCharge extends Model{

	@Id
	public Long id;
	public boolean dependsOnAmt;
	public boolean dependsOnCurrency;
	
	@ManyToOne()
	public Product product;
	public String amtCurrency;
	public double fromAmt;
	public double toAmt;
	
	public double absRate;
	public String absRateCurrency;
	public double percentRate;
	public String percentCurrency;
	public double maxCharge;
	public double minCharge;
	public String chargeType;
	
	public static Finder<Long, ExCharge> find = new Finder<Long,ExCharge>(ExCharge.class);
	
	
}
