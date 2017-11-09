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

import java.util.concurrent.CompletableFuture;

import com.sap.examples.http.model.CurrentWeather;

import io.vavr.concurrent.Future;

public interface WeatherService
{
	Future<CurrentWeather> getCurrentWeather(String city);
}
