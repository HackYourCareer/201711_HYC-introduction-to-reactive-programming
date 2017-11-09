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
package com.sap.examples.files;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.sap.examples.files.model.Occurrence;

import io.reactivex.Flowable;

public class FileStream
{

	public static void main(String[] args) throws InterruptedException, IOException
	{
		System.out.println();
		final String filePath = "./test_file.txt";
		Flowable<String> lines = getStreamOfLines(filePath);

		lines.subscribe(System.out::println, System.out::println);

		System.in.read();
	}

	public static Flowable<String> getStreamOfLines(String filePath)
	{
		return Flowable.generate( //
				() -> new BufferedReader(new FileReader(filePath)), //
				(reader, emitter) -> { //
					final String line = reader.readLine(); //
					if (line != null) //
					{ //
						emitter.onNext(line); //
					} //
					else //
					{ //
						emitter.onComplete(); //
					} //
				}, //
				reader -> reader.close());//
	}

	public static Flowable<String> getStreamOfWords(String filePath)
	{
		return getStreamOfLines(filePath) //
				.flatMap(line -> Flowable.fromArray(line.split(" ")));
	}

	public static Flowable<Occurrence> getCountingWordsStream(Flowable<String> wordStream)
	{
		return wordStream //
				.map(word -> new Occurrence(word, 1)) //
				.groupBy(o -> o.word) //
				.flatMap(g -> g.reduce((a, e) -> new Occurrence(a.word, a.count + e.count)).toFlowable());
	}
}
