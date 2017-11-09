/*
 * [y] hybris Platform
 *
 * Copyright (c) 2000-2017 hybris AG
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of hybris
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with hybris.
 */
package com.sap.examples.http;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;

import com.google.gson.Gson;
import com.sap.examples.http.model.CurrentWeather;
import com.sap.examples.http.model.OpenWeatherAPIData;

import io.vavr.concurrent.Future;

public class OpenWeatherService implements WeatherService, Closeable
{
	public OpenWeatherService(){
		client = HttpAsyncClients.createDefault();
	}

	@Override
	public Future<CurrentWeather> getCurrentWeather(final String city)
	{
		CloseableHttpAsyncClient client = HttpAsyncClients.custom().setThreadFactory(OpenWeatherService::newDaemonThread).build();

		try
		{
			final HttpGet request = getRequest(city);
			client.start();
			
			final Future<HttpResponse> future = Future.of(executor, client.execute(request, null)::get);

			return future
					.flatMap(this::handleCurrentWeather)
					.map(w->new CurrentWeather(city, w.main.getTemp(), w.main.getPressure(), w.main.getHumidity(), w.main.getTemp_min(), w.main.getTemp_max()));
		}
		catch(Exception e){
			return Future.failed(new Exception("Failed to call the OpenWeatherAPI!"));
		}
	}

	private HttpGet getRequest(final String city) throws URISyntaxException
	{
		final String apiKey = System.getenv("WEATHER_API_KEY");
		final String host = "api.openweathermap.org";

		URIBuilder builder = new URIBuilder();
		builder.setScheme("http").setHost(host).setPath("/data/2.5/weather")
		.setCustomQuery(String.format("q=%s&APPID=%s", city, apiKey));

		final String url = builder.build().toString();

		return new HttpGet(url);
	}


	private Future<OpenWeatherAPIResponse> handleCurrentWeather(final HttpResponse r){
		if (r.getStatusLine().getStatusCode() == 200){
			try{
				return Future.of(executor,()->{
					Reader reader = new InputStreamReader(r.getEntity().getContent(), "UTF-8");

					return new Gson().fromJson(reader, OpenWeatherAPIResponse.class);
				});
			} catch (Exception e) {
				return Future.failed(e);
			}
			finally{
				try
				{
					client.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		} else {
			return Future.failed(new Exception("Weather Service returned status : " + r.getStatusLine().getStatusCode()));
		}
	}

	static ExecutorService executor = Executors.newFixedThreadPool(4, OpenWeatherService::newDaemonThread);

	static Thread newDaemonThread(Runnable r){
		final Thread t = Executors.defaultThreadFactory().newThread(r);
		t.setDaemon(true);
		return t;
	}

	private final CloseableHttpAsyncClient client;

	@Override
	public void close() throws IOException
	{
		client.close();
	}

	public class OpenWeatherAPIResponse
	{
		public OpenWeatherAPIResponse(){

		}

		public OpenWeatherAPIData main;
	}
	
}
