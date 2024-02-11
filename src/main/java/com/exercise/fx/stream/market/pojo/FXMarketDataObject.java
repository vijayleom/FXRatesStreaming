package com.exercise.fx.stream.market.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class FXMarketDataObject {
	String currency;
	double rangeStart;
	double rangeEnd;
	int rangeInterval;
	String rangeUnit;
}
