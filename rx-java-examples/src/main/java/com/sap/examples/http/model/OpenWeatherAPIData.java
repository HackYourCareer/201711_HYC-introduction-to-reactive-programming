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
package com.sap.examples.http.model;

public class OpenWeatherAPIData
{
	OpenWeatherAPIData()
	{

	}

	public OpenWeatherAPIData(final double temp, final double pressure, final double humidity, final double temp_min,
			final double temp_max)
	{
		this.temp = temp;
		this.pressure = pressure;
		this.humidity = humidity;
		this.temp_min = temp_min;
		this.temp_max = temp_max;
	}

	public double getTemp()
	{
		return temp;
	}

	public double getPressure()
	{
		return pressure;
	}

	public double getHumidity()
	{
		return humidity;
	}

	public double getTemp_min()
	{
		return temp_min;
	}

	public double getTemp_max()
	{
		return temp_max;
	}

	@Override
	public String toString()
	{
		return "WeatherData{" + "temp=" + temp + ", pressure=" + pressure + ", humidity=" + humidity + ", temp_min=" + temp_min
				+ ", temp_max=" + temp_max + '}';
	}

	@Override
	public boolean equals(final Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (o == null || getClass() != o.getClass())
		{
			return false;
		}

		final OpenWeatherAPIData that = (OpenWeatherAPIData) o;

		if (Double.compare(that.getTemp(), getTemp()) != 0)
		{
			return false;
		}
		if (Double.compare(that.getPressure(), getPressure()) != 0)
		{
			return false;
		}
		if (Double.compare(that.getHumidity(), getHumidity()) != 0)
		{
			return false;
		}
		if (Double.compare(that.getTemp_min(), getTemp_min()) != 0)
		{
			return false;
		}
		return Double.compare(that.getTemp_max(), getTemp_max()) == 0;
	}

	@Override
	public int hashCode()
	{
		int result;
		long temp1;
		temp1 = Double.doubleToLongBits(getTemp());
		result = (int) (temp1 ^ (temp1 >>> 32));
		temp1 = Double.doubleToLongBits(getPressure());
		result = 31 * result + (int) (temp1 ^ (temp1 >>> 32));
		temp1 = Double.doubleToLongBits(getHumidity());
		result = 31 * result + (int) (temp1 ^ (temp1 >>> 32));
		temp1 = Double.doubleToLongBits(getTemp_min());
		result = 31 * result + (int) (temp1 ^ (temp1 >>> 32));
		temp1 = Double.doubleToLongBits(getTemp_max());
		result = 31 * result + (int) (temp1 ^ (temp1 >>> 32));
		return result;
	}

	private double temp;
	private double pressure;
	private double humidity;
	private double temp_min;
	private double temp_max;
}
