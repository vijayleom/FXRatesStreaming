package com.exercise.fx.stream.market.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.exercise.fx.stream.market.process.FXMarketDataProcessor;

@Controller
public class FXMarketRateController {
	
	@Autowired
	private FXMarketDataProcessor fxProcessor;
	
	@GetMapping(path = "/fxrates", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribeForFXRates() {
        return fxProcessor.calculateFXMarketRates();
    }
}
