package controllers;

import java.util.List;

import models.ExCharge;
import models.ExRate;
import models.Institution;
import models.Product;
import play.mvc.Controller;
import play.mvc.Result;

public class DataSetupController extends Controller {
	
	public Result setupInst() {
    	System.out.println("Setting up Institutions");
    	instituteSetup();
    	
    	return redirect(routes.HomeController.index());
        
        
    }
	
	
	public void instituteSetup ()
	{
		Institution institution = new Institution();
    	institution.name = "ICICI Bank";
    	institution.instType ="Bank";
    	institution.url = "https://m2inet.icicibank.co.in/m2iNet/m2iNetLoginForm.jsp?site=m2ilogin";
    	institution.logoUrl ="ICICI_Bank_Logo.png";
    	
    	institution.save();
    	
    	Product productICICI = new Product();
    	productICICI.institution = institution;
    	productICICI.name = "Net Express";
    	
    	productICICI.save();
			  
    	
    
    	Institution institution2 = new Institution();
    	institution2.name = "State Bak of India";
    	institution2.instType ="Bank";
    	institution2.url = "https://remit.onlinesbi.com/";
    	institution2.logoUrl ="SBI-logo.png";
    	
    	institution2.save();	
    	
    	Product productSBI = new Product();
    	productSBI.institution = institution2;
    	productSBI.name = "Express Remit";
    	
    	productSBI.save();
	}
	public Result setupRates() {
		
		//ICICI
		List<Product> products = Product.find.where().ilike("name", "Net Express").findList();
		
		if (products.size() ==0) {
			instituteSetup();
			products = Product.find.where().ilike("name", "Net Express").findList();
		}
		Product productICICI = products.get(0);
		System.out.println(" ICICI ID is " +productICICI.id);
		
		
		setupRateTranche (productICICI, 0,1000,"GBP",97.21);
		setupRateTranche (productICICI, 1000,2999,"GBP",97.21);
		setupRateTranche (productICICI, 3000,9999,"GBP",97.21);
		setupRateTranche (productICICI, 10000,50000,"GBP",97.21);
		setupRateTranche (productICICI, 50000,0,"GBP",97.21);
		
		products = Product.find.where().ilike("name", "Express Remit").findList();
		Product productSBI = products.get(0);
		
		setupRateTranche (productSBI, 0,500.99,"GBP",97.80);
		setupRateTranche (productSBI, 501,5000.99,"GBP",97.90);
		setupRateTranche (productSBI, 5001,15000,"GBP",98.00);
		setupRateTranche (productSBI, 15000,0,"GBP",-999);
		
	
		return redirect(routes.HomeController.index());
	}	
	
	public Result setupCharges() {
		
	List<Product> products = Product.find.where().ilike("name", "Net Express").findList();
		
		if (products.size() ==0) {
			instituteSetup();
			products = Product.find.where().ilike("name", "Net Express").findList();
		}
		Product productICICI = products.get(0);
		
		setupChargeTranche(productICICI,true,true,"GBP", 0.00,499.99,2.00,"GBP",0.00,null,0.00,0.00,"Service Charge");
		
		setupChargeTranche(productICICI,true,true,"GBP", 500.00,0.00,0.00,"GBP",0.00,null,0.00,0.00, "Service Charge");
		
		products = Product.find.where().ilike("name", "Express Remit").findList();
		Product productSBI = products.get(0);
		setupChargeTranche(productSBI,false,false,"INR", 0.00,0.00,0.250,"INR",0.00,null,0.00,0.00,"Foreign Currency Conversion Charges");
		setupChargeTranche(productSBI,true,false,"INR", 0.00,100000.00,0.00,null,0.145,"INR",00.00,35.00,"Service Charge");
		setupChargeTranche(productSBI,true,false,"INR", 100000.00,1000000.00,145.00,"INR",0.0725,"INR",0.00,0.00 ,"Service Charge");
		setupChargeTranche(productSBI,true,false,"INR", 1000000.00,0.00,798.00,"INR",0.0145,"INR",7000.00,0.00,"Service Charge");
		
		return redirect(routes.HomeController.index());
	}
	
	public void setupChargeTranche(Product product,  boolean dependsOnAmt,boolean dependsOnCurrency, String amtCurrency, double fromAmt, double toAmt, double absRate, 
			String absRateCurrency, double percentRate, String percentCurrency , double maxCharge, double minCharge, String chargeType){
		ExCharge charge = new ExCharge();
		charge.dependsOnAmt =dependsOnAmt;
		charge.dependsOnCurrency =dependsOnCurrency;
		charge.product = product;
		charge.amtCurrency=amtCurrency;
		charge.fromAmt=fromAmt;
		charge.toAmt= toAmt;
		
		charge.absRate=absRate;
		charge.absRateCurrency=absRateCurrency;
		charge.percentRate=percentRate;
		charge.maxCharge=maxCharge;
		charge.minCharge=minCharge;
		charge.chargeType=chargeType;
		
		charge.percentCurrency =percentCurrency;
		charge.save();
	}

	
	public void setupRateTranche(Product product, double fromAmt, double toAmt, String fromCurrency, double rate){
		ExRate exRate = new ExRate();
		exRate.product = product;
		exRate.fromAmt= fromAmt;
		exRate.toAmt = toAmt;
		exRate.fromCurrency =fromCurrency;
		exRate.exRate = rate;
		exRate.save();
	}
}
