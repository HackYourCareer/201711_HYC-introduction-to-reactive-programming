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
package com.sap.examples.reactivestream;

import java.util.Arrays;
import java.util.List;

import com.sap.examples.reactivestream.model.Customer;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class DefaultCustomersService implements CustomerService
{
	@Override
	public Observable<Customer> getCustomers()
	{
		List<Customer> list = Arrays.asList(
				new Customer("1", "John", "Doe", "Gliwice", "Akademicka"),
				new Customer("2", "Jane", "Doe", "Gliwice", "Akademicka"),
				new Customer("3", "Jan", "Kowalski", "Katowice", "Rynek"),
				new Customer("4", "Anna", "Kowalska", "Katowice", "Rynek")
				);

		return Observable.fromIterable(list).observeOn(Schedulers.computation());
	}
}
