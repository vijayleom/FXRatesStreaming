package com.exercise.fx.stream.market.util;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.exercise.fx.stream.market.pojo.FXMarketDataObject;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Component
@ConfigurationProperties(prefix = "fx")
@Setter
@Getter
@ToString
public class ApplicationPropertiesUtil {

	String newCurrency;

	List<FXMarketDataObject> marketRates;
}
