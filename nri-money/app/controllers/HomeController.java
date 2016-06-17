package controllers;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import models.ExCharge;
import models.ExRate;
import models.ExRateRequest;
import models.Product;
import models.ProductDisplay;
import controllers.routes;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.*;
import services.Calculator;
import views.html.*;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {
	private  final Form<ExRateRequest> exRateRequestForm;
    
    public Result index() {
    	System.out.println("Home Page Rendering");
        return ok(index.render("NRI Money"));
    }

    @Inject
    public HomeController(FormFactory formFactory) {
        exRateRequestForm =  formFactory.form(ExRateRequest.class);
    }
    
    
    public   Result getExRates(String fromCurrency, Integer amount) throws Exception{
    	List<ProductDisplay> productDisplayList = new ArrayList<ProductDisplay>();
    	List<Product> products = Product.find.all();
    	for (Product product :products){
    	
    	
    	List<ExRate> rateList = ExRate.find.fetch("product").fetch("product.institution").where().eq("fromCurrency",fromCurrency).eq("product.id", product.id).orderBy("fromAmt desc").findList();
		double rate = Calculator.calculateExRate(rateList, fromCurrency, amount);
		ProductDisplay productDisplay = new ProductDisplay();
		productDisplay.exRate = rate;
		
		List<ExCharge> chargeList = ExCharge.find.where().eq("product.id", product.id).orderBy("fromAmt desc").findList();
		
		Calculator.calculateCharges(productDisplay, chargeList, amount*rate, amount, fromCurrency, amount, rate);
		
		productDisplay.exRate = rate;
		productDisplay.instName = product.institution.name;
		productDisplay.logoUrl = product.institution.logoUrl;
		productDisplay.productName = product.name;
		productDisplay.url = product.institution.url;
		
		if(fromCurrency.equals(productDisplay.chargeCurrency)){
			productDisplay.toAmt = (amount - productDisplay.charges)*rate;
			
		} else {
			productDisplay.toAmt = amount*rate - productDisplay.charges;
		}
		productDisplayList.add(productDisplay);
		System.out.println (productDisplay.instName +" " +productDisplay.exRate);
    	}
    	return ok(rates.render(productDisplayList));
    
    }
    public   Result requestExRates() throws Exception{
    	System.out.println("Rates Requested");
    	ExRateRequest exRateRequest = exRateRequestForm.bindFromRequest().get();
    	System.out.println(exRateRequest.amount);
    	System.out.println(exRateRequest.fromCurrency);
    	
    	return redirect(controllers.routes.HomeController.getExRates(exRateRequest.fromCurrency, exRateRequest.amount));
    }
    
 
    
}
