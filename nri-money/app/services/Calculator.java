package services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import models.ExCharge;
import models.ExRate;
import models.ExRateRequest;
import models.ProductDisplay;

public class Calculator {

	public static double calculateExRate(List<ExRate> rateList, String fromCurrency, Integer amount) throws Exception {
		double rate =0.00;
		for(ExRate exRate : rateList) {
			// On the top tranche to Amt will be 0
			if(amount >= exRate.fromAmt && (amount <=exRate.toAmt || exRate.toAmt ==0.00)) {
				rate = exRate.exRate;
				break;
				
			}
		
		}
		
		if (rate ==0.00) {
			throw new Exception ("Exchage Rate not found for " + fromCurrency + " -> " + "INR");
		}
		return rate;
		
	}
	
	public static void calculateCharges(ProductDisplay product, List<ExCharge> chargeList, double toAmt, double fromAmt, String fromCurrency, Integer ReqAmount, double exRate)
	{
		
		double chargesToCurrency=0.00;
		double chargesFromCurrency=0.00;
		
		//Group the list of charge Type 
		HashMap<String, List<ExCharge>> chargeMap = new HashMap<String, List<ExCharge>>();
		for(ExCharge charge: chargeList){
			if (!chargeMap.containsKey(charge.chargeType)) {
				chargeMap.put(charge.chargeType,new ArrayList<ExCharge>() );
			} 
			chargeMap.get(charge.chargeType).add(charge);
		}
		   Iterator<Map.Entry<String, List<ExCharge>>> it = chargeMap.entrySet().iterator();
		 while (it.hasNext()) {
			Map.Entry<String, List<ExCharge>> pair = it.next();
		        
				List<ExCharge> chareSubList =pair.getValue();
		   
		
		for(ExCharge charge: chareSubList){
		if (!charge.dependsOnAmt){
			// add abs rate in appropriate currency, same logic as below
			if(charge.absRate >0) {
				if(charge.absRateCurrency.equals(fromCurrency)){
					chargesFromCurrency = chargesFromCurrency+charge.absRate;
				} else {
					chargesToCurrency = chargesToCurrency+charge.absRate;
				}
			} else if(charge.percentRate >0) {
				if(charge.percentCurrency.equals(fromCurrency)){
					chargesFromCurrency = chargesFromCurrency+ fromAmt*charge.percentRate;
				} else {
					chargesToCurrency = chargesToCurrency + toAmt*charge.percentRate;
				}
				
			}
		
			break; // Should be only one record for a type if not dependant on amount
			
		} else  {  
			// Use From Amount if AmtCurrency matches with Request Currency otherwise toAmt i.e. INR
			double amtToUse = toAmt;
			if(fromCurrency.equals(charge.amtCurrency)) {
				amtToUse = fromAmt;
			}
			// On the top tranche to Amt will be 0
			if(amtToUse >= charge.fromAmt && (amtToUse <=charge.toAmt || charge.toAmt ==0.00)) {
				//same logic as above
				if(charge.absRate >0) {
					if(charge.absRateCurrency.equals(fromCurrency)){
						chargesFromCurrency = chargesFromCurrency+charge.absRate;
					} else {
						chargesToCurrency = chargesToCurrency+charge.absRate;
					}
				} else if(charge.percentRate >0) {
					if(charge.percentCurrency.equals(fromCurrency)){
						chargesFromCurrency = chargesFromCurrency+ fromAmt*charge.percentRate;
					} else {
						chargesToCurrency = chargesToCurrency + toAmt*charge.percentRate;
					}
					
				}
				
				break; // Exit after first match for a type; Issue - need to look at ordering
				
			}
			
			
		}
		
		
			
	
		}
		}
		// Convert into return currency , INR if even one charge is in INR
		
				if (chargesToCurrency > 0.00) {
					chargesToCurrency =chargesToCurrency + chargesFromCurrency*exRate;
					product.charges = chargesToCurrency;
					product.chargeCurrency = "INR";
					
				} else {
					product.charges = chargesFromCurrency;
					product.chargeCurrency = fromCurrency;
					
				}
		
	}
	
}
