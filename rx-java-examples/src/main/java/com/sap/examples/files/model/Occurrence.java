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
package com.sap.examples.files.model;

public class Occurrence
{
	@Override
	public String toString()
	{
		return "Occurrence{" + "word='" + word + '\'' + ", count=" + count + '}';
	}
	
	public Occurrence(String word, int count){
		this.word = word;
		this.count = count;
	}
	public String word;
	public int count;
}

