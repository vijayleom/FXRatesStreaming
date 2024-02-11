package com.exercise.fx.stream.market.process;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.exercise.fx.stream.market.pojo.FXMarketData;
import com.exercise.fx.stream.market.util.ApplicationPropertiesUtil;
import com.exercise.fx.stream.market.util.FXMarketRateDictionary;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FXMarketDataProcessor {

	private final ApplicationPropertiesUtil appPropsUtil;

	private final FXMarketRateDictionary fxDictionary;

	private SseEmitter emitter;

	public SseEmitter calculateFXMarketRates() {
		log.info(" ************************************** ");
		log.info(" Endpoint Invocation - Process Started ");
		emitter = new SseEmitter(Long.MAX_VALUE);
		ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2);
		log.info(" Executor Service using Scheduled format starts ");
		appPropsUtil.getMarketRates().stream().forEach(fxData -> {
			Runnable task = new Runnable() {
				@Override
				public void run() {
					String currency = fxData.getCurrency();
					double currentRange = calculate(fxData.getRangeStart(), fxData.getRangeEnd());
					FXMarketData fxMarketData = new FXMarketData(currentRange, System.currentTimeMillis(), currency);
					fxDictionary.addFxRatesDictionary(currency, fxMarketData);
					try {
						emitter.send(fxMarketData);
					} catch (IOException e) {
						log.info(" Shutting Down the Service due to interruption ");
						executorService.shutdown();
						emitter.complete();
					}
				}
			};
			executorService.scheduleAtFixedRate(task, 5, fxData.getRangeInterval(),
					TimeUnit.valueOf(fxData.getRangeUnit()));
		});
		log.info(" Endpoint Invocation - Process Completed ");
		log.info(" ************************************** ");
		return emitter;
	}

	// Round off to 4 digit decimal point
	private double calculate(double start, double end) {
		double range = (end - start);
		return Math.round(((Math.random() * range) + start) * 10000) / 10000.00;
	}

	// Round off to 4 digit decimal point
	private double calculateNewRates(double source, double dest) {
		return Math.round((source / dest) * 10000) / 10000.00;
	}

	@Scheduled(fixedRate = 60)
	public void sendComputedRates() {
		try {
			if (!fxDictionary.getFxMXNUSDRates().isEmpty() && !fxDictionary.getFxUSDEURRates().isEmpty()) {
				FXMarketData fxMXNUSD = fxDictionary.getFxMXNUSDRates().pop(); //gets the last value in the stack[Latest]
				FXMarketData fxUSDEUR = fxDictionary.getFxUSDEURRates().pop(); //gets the last value in the stack[Latest]
				double newRate = calculateNewRates(fxMXNUSD.getFxRates(), fxUSDEUR.getFxRates());
				emitter.send(new FXMarketData(newRate, System.currentTimeMillis(), appPropsUtil.getNewCurrency()));
			}
		} catch (IOException e) {
			log.info(" Shutting Down the Service due to interruption ");
			emitter.complete();
		} finally {
			fxDictionary.getFxMXNUSDRates().clear();
			fxDictionary.getFxUSDEURRates().clear();
		}
	}
}
