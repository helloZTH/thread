package com.hui.unsafe;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadUnsafeExample {

	private int cut = 0;

	public void add() {
		cut++;
	}

	public int get() {
		return cut;
	}

	public static void main(String[] args) throws InterruptedException {
		final int threadSize = 1000;

		CountDownLatch latch = new CountDownLatch(threadSize);
		ThreadUnsafeExample example = new ThreadUnsafeExample();
		ExecutorService pool = Executors.newCachedThreadPool();

		for (int i = 0; i < threadSize; i++) {
			pool.execute(()->{
				example.add();
				latch.countDown();
			});
		}
		
		latch.await();
		pool.shutdown();
		
		System.out.println(example.get());
	}
}
