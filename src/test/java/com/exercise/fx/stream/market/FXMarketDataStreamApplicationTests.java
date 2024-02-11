package com.exercise.fx.stream.market;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.apache.commons.lang3.Range;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.exercise.fx.stream.market.pojo.FXMarketData;
import com.exercise.fx.stream.market.util.ApplicationPropertiesUtil;
import com.exercise.fx.stream.market.util.FXMarketRateDictionary;

@SpringBootTest
class FXMarketDataStreamApplicationTests {

	@Autowired
	private ApplicationPropertiesUtil appUtil;

	@Autowired
	private FXMarketRateDictionary fxDictionary;
	
	@Test
	void contextLoads() {
		assertThat(appUtil).isNotNull();
	}

	@Test
	void testMXNUSDCurency() {
		Optional<String> content = appUtil.getMarketRates().stream()
				.filter(fxRates -> Range.between(fxRates.getRangeStart(), fxRates.getRangeEnd()).isAfter(0.5000))
				.map(fx -> fx.getCurrency()).findAny();
		assertThat(content.get()).isEqualTo("MXN/USD");
	}

	@Test
	void testUSDEURCurency() {
		Optional<String> content = appUtil.getMarketRates().stream()
				.filter(fxRates -> Range.between(fxRates.getRangeStart(), fxRates.getRangeEnd()).isAfter(0.890))
				.map(fx -> fx.getCurrency()).findAny();
		assertThat(content.get()).isEqualTo("USD/EUR");
	}

	void testServiceCall() throws Exception { //This test couldn't be completed.
		fxDictionary.getFxMXNUSDRates().add(new FXMarketData(0.2, 1234, "MXN/USD"));
		fxDictionary.getFxUSDEURRates().add(new FXMarketData(0.5, 1234, "USD/EUR"));
		Thread.sleep(600L);
	}
}
