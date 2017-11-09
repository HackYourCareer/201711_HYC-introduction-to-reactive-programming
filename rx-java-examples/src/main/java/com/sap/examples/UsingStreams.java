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

import com.sap.examples.reactivestream.model.Customer;
import com.sap.examples.reactivestream.CustomerService;
import com.sap.examples.reactivestream.DefaultCustomersService;

import java.io.IOException;
import java.util.List;

import io.reactivex.Observable;

public class UsingStreams
{
	public static void main(String[] args) throws InterruptedException, IOException
	{
		CustomerService customerService = new DefaultCustomersService();

		nonBlockingUseOfStream(customerService.getCustomers());

		System.in.read();
	}

	public static void nonBlockingUseOfStream(Observable<Customer> customersStream) {
		customersStream.subscribe(UsingStreams::consume, UsingStreams::onError, UsingStreams::onComplete);
	}

	public static void consume(Customer customer) {
		System.out.println(customer);
	}

	public static void onError(Throwable t) {
		t.printStackTrace();
	}

	public static void onComplete(){
		System.out.println("Stream completed");
	}

	public static void blockingUseOfStream(Observable<Customer> customersStream) {
		final List<Customer> customers = customersStream
				.toList()
				.blockingGet();

		System.out.println(customers.toString());
	}

	public static Observable<String> getIdsOfCustomersFromCity(Observable<Customer> customersStream, String city){
		return customersStream
				.filter(c->c.getCity().equals(city))
				.map(c->c.getId());
	}
	
}
