package org.springframework.samples.SevenIslands.util;

import java.util.Objects;

public class Pair {
	public Integer x;
	public Integer y;

	public Pair(Integer x, Integer y) {
		this.x = x;
		this.y = y;
	}

	public String toString() {
		return this.x + " " + this.y;
	}

	public int compareTo(Pair pair) {
        int result = x.compareTo(pair.x);
        return (result == 0) ? y.compareTo(pair.y) : result;
    }

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}
	@Override
	public boolean equals(Object obj) {
		if(this == obj ) return true;
		if(obj == null || getClass() != obj.getClass()) return false;
		Pair point = (Pair) obj;
		return x == point.x && y == point.y;
	}
}