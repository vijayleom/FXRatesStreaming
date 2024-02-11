package com.exercise.fx.stream.market.util;

import java.util.Stack;

import org.springframework.stereotype.Component;

import com.exercise.fx.stream.market.pojo.FXMarketData;

import lombok.Getter;
import lombok.ToString;

@Component
@Getter
@ToString
public class FXMarketRateDictionary {

	private Stack<FXMarketData> fxMXNUSDRates = new Stack<FXMarketData>();

	private Stack<FXMarketData> fxUSDEURRates = new Stack<FXMarketData>();
	
	public void addFxRatesDictionary(String fxRates, FXMarketData fxMarketData) {
		switch (fxRates) {
		case "MXN/USD":
			fxMXNUSDRates.add(fxMarketData);
			break;
		case "USD/EUR":
			fxUSDEURRates.add(fxMarketData);
			break;
		}
	}
}
