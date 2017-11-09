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

import java.util.List;
import java.util.Arrays;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

public class UsingSchedulers
{
	public static void main(String[] args) throws InterruptedException
	{
		buildFlowableFromListExample();
		Thread.sleep(1000);
		System.out.println("Done!!!");
	}

	public static void buildFlowableFromListExample() {

		final List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);

		// create flowable
		final Flowable<Integer> flowableOfInts = Flowable.fromIterable(list);

		// transform
		final Flowable<Integer> transformed = flowableOfInts.map(i->{
			System.out.println("map thread name: " + Thread.currentThread().getName());
			return i + 1;
		});

		// subscribe and observe using io Schedulers
		transformed
				.subscribeOn(Schedulers.io())
				.observeOn(Schedulers.io())
				.subscribe(UsingSchedulers::onSubscribe);
	}

	public static <T> void onSubscribe(T i){
		System.out.println("onSubscribe thread name: " + Thread.currentThread().getName());
	}
}
