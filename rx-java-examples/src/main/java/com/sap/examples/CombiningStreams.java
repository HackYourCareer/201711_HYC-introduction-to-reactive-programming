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

import java.util.Arrays;
import io.reactivex.Flowable;

public class CombiningStreams
{

	public static void main(String[] args) throws InterruptedException
	{
		Flowable<Integer> f1 = Flowable.fromIterable(Arrays.asList(1, 2, 3, 4, 5));
		Flowable<Integer> f2 = Flowable.fromIterable(Arrays.asList(6, 7, 8, 9, 10));

		f1.mergeWith(f2).subscribe(System.out::println);
		f1.zipWith(f2, (x,y)->x + y).subscribe(System.out::println);
		f1.startWith(0).subscribe(System.out::println);
	}
}
