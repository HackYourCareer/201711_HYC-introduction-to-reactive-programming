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
package com.sap.examples;

import com.sap.examples.http.OpenWeatherService;
import com.sap.examples.http.model.CurrentWeather;

import io.vavr.concurrent.Future;
import io.vavr.control.Try;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

public class UsingFuture
{
	public static void main(String[] args) throws InterruptedException, IOException
	{
		System.out.println("Main thread name: " + Thread.currentThread().getName());
		System.out.println("Start calling service");

		OpenWeatherService ws = new OpenWeatherService();
		 
		ws.getCurrentWeather("London")
				.map(UsingFuture::tempFromKToC)
				.flatMap(w->writeToTextFile(System.getenv("TMPDIR"), w))
				.onSuccess(System.out::println);

		System.out.println("Done");
		System.in.read();
		ws.close();
	}

	public static void onComplete(Try<CurrentWeather> weatherResponse)
	{
		System.out.println("Future thread name: " + Thread.currentThread().getName());
		if (weatherResponse.isSuccess())
		{
			System.out.println(weatherResponse.get());
		}
		else
		{
			weatherResponse.getCause().printStackTrace();
		}
	}

	public static CurrentWeather tempFromKToC(CurrentWeather weatherData)
	{
		final String city = weatherData.getCity();
		final double temperature = kelvinToCelsius(weatherData.getTemp());
		final double pressure = weatherData.getPressure();
		final double humidity = weatherData.getHumidity();
		final double minTemp = kelvinToCelsius(weatherData.getTemp_min());
		final double maxTemp = kelvinToCelsius(weatherData.getTemp_max());

		return new CurrentWeather(city,temperature, pressure, humidity, minTemp, maxTemp);
	}

	public static double kelvinToCelsius(double t)
	{
		return t - 273.15;
	}

	public static Future<Boolean> writeToTextFile(String filePath, CurrentWeather currentWeather){
		return Future.of(()->{
			PrintWriter writer = new PrintWriter(filePath, "UTF-8");
			writer.println(currentWeather);
			writer.close();

			return true;
		});
	}

	public static List<String> CITIES = Arrays.asList("London", "Paris", "Warsaw", "Berlin", "Moscow", "New York", "Washington", "Rome");
}
