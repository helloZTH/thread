package com.hui.aqs;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class ForkJoinExample extends RecursiveTask<Integer> {

	private final int threadHold = 5;
	private int start;
	private int end;

	public ForkJoinExample(int start, int end) {
		super();
		this.start = start;
		this.end = end;
	}

	@Override
	protected Integer compute() {
		int result = 0;
		if (end - start <= threadHold) {
			for (int i = start; i <= end; i++) {
				result += i;
			}
		} else {
			int middle = start + (end - start) / 2;
			ForkJoinExample leftFork = new ForkJoinExample(start, middle);
			ForkJoinExample rightFork = new ForkJoinExample(middle + 1, end);

			leftFork.fork();
			rightFork.fork();

			result = leftFork.join() + rightFork.join();
		}

		return result;

	}
	
	
	public static void main(String[] args) throws Exception {
		ForkJoinExample example = new ForkJoinExample(1, 10000);
		ForkJoinPool pool = new ForkJoinPool();
		ForkJoinTask<Integer> task = pool.submit(example);
		System.out.println(task.get());
	}

}
