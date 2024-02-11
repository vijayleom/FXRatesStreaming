package com.exercise.fx.stream.market.pojo;

import lombok.ToString;
import lombok.Value;

@Value
@ToString
public class FXMarketData {
	
	private double fxRates;
	
	private long timeStamp;
	
	private String currency;
	
}
