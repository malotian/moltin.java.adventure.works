package com.moltin.adventure.works;

import java.util.ArrayList;

@SuppressWarnings("serial")
public class Table extends ArrayList<Tuple> {

	public Table addX(Tuple tuple) {
		super.add(tuple);
		return this;
	}
}