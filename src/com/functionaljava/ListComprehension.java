package com.functionaljava;

import java.util.function.Function;

public class ListComprehension {

	public static void main(String... args) {
		Pair<Integer> pair = new Pair<Integer>(32,
				(new Pair<Integer>(1, new Pair<Integer>(2, new Pair<Integer>(3, null)))));
		System.out.println(pair);

		System.out.println(ImperativeHelper.pairListToString(range(1, 10)));
		System.out
				.println(ImperativeHelper.pairListToString(new PairMapper<Integer>().apply(range(1, 10), f -> f * 2)));

		Pair<String> stringpair = new Pair<String>("Hello", new Pair<String>("World", new Pair<String>("Now", null)));
		System.out.println(ImperativeHelper.pairListToString(stringpair));
		System.out.println(
				ImperativeHelper.pairListToString(new PairMapper<String>().apply(stringpair, f -> f.toUpperCase())));
	}

	static Pair<Integer> range(Integer low, Integer high) {
		if (low > high)
			return null;

		return new Pair<Integer>(low, range(low + 1, high));
	}

}

class Pair<T> {

	private T head;
	private Pair<T> tail;

	Pair(T head, Pair<T> tail) {
		this.head = head;
		this.tail = tail;
	}

	public T getHead() {
		return head;
	}

	public void setHead(T head) {
		this.head = head;
	}

	public Pair<T> getTail() {
		return tail;
	}

	public void setTail(Pair<T> tail) {
		this.tail = tail;
	}

	@Override
	public String toString() {
		return "(" + head + "," + tail + ")";
	}
}

@FunctionalInterface
interface PairFunction<T> {

	Pair<T> apply(Pair<T> pair, Function<T, T> function);
}

class PairMapper<T> implements PairFunction<T> {

	@Override
	public Pair<T> apply(Pair<T> pair, Function<T, T> function) {
		if (pair == null)
			return null;

		return new Pair<T>(function.apply((T) pair.getHead()), apply(pair.getTail(), function));
	}
}

class ImperativeHelper {

	static <T> String pairListToString(Pair<T> pair) {
		StringBuilder sb = new StringBuilder();
		Pair<T> temppair = pair;

		while (temppair != null) {
			sb.append(temppair.getHead());
			sb.append(" : ");
			temppair = temppair.getTail();
		}

		sb.append(" []");

		return sb.toString();
	}
}