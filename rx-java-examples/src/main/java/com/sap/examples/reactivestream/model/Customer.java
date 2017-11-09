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
package com.sap.examples.reactivestream.model;

public class Customer
{
	public Customer(final String id, final String name, final String surname, final String city, final String address)
	{
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.city = city;
		this.address = address;
	}

	@Override
	public String toString()
	{
		return "Customer{" + "id='" + id + '\'' + ", name='" + name + '\'' + ", surname='" + surname + '\'' + ", city='" + city
				+ '\'' + ", address='" + address + '\'' + '}';
	}

	public String getName()
	{
		return name;
	}

	public String getSurname()

	{
		return surname;
	}

	public String getCity()
	{
		return city;
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

		final Customer customer = (Customer) o;

		if (!getId().equals(customer.getId()))
		{
			return false;
		}
		if (!getName().equals(customer.getName()))
		{
			return false;
		}
		if (!getSurname().equals(customer.getSurname()))
		{
			return false;
		}
		if (!getCity().equals(customer.getCity()))
		{
			return false;
		}
		return getAddress().equals(customer.getAddress());
	}

	@Override
	public int hashCode()
	{
		int result = getId().hashCode();
		result = 31 * result + getName().hashCode();
		result = 31 * result + getSurname().hashCode();
		result = 31 * result + getCity().hashCode();
		result = 31 * result + getAddress().hashCode();
		return result;
	}

	public String getId()

	{
		return id;
	}

	public String getAddress()

	{
		return address;
	}

	private String id;
	private String name;
	private String surname;
	private String city;
	private String address;
}
