package com.rnctech.nrdata.model;

import com.rnctech.nrdata.NrdataConstants.GENERATE_TYPE;
/* 
* @Author Zilin Chen
* @Date 2020/09/03
*/
public class Column {
	String tname;
	String name; 
	String type;
	int sqltype;
	boolean notnull;
	int size;
	int ddigits = 0;
	public GENERATE_TYPE gtype;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isNotnull() {
		return notnull;
	}

	public void setNotnull(boolean notnull) {
		this.notnull = notnull;
	}

	public Column(String name, String type, boolean notnull) {
		super();
		this.name = name;
		this.type = type;
		this.notnull = notnull;
	}

	public Column(String name, int sqltype, GENERATE_TYPE t, boolean notnull) {
		super();
		this.name = name;
		this.sqltype = sqltype;
		this.gtype = t;
		this.notnull = notnull;
	}
	
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getSqltype() {
		return sqltype;
	}

	public void setSqltype(int sqltype) {
		this.sqltype = sqltype;
	}

	public GENERATE_TYPE getGtype() {
		return gtype;
	}

	public void setGtype(GENERATE_TYPE gtype) {
		this.gtype = gtype;
	}

	public String getTname() {
		return tname;
	}

	public void setTname(String tname) {
		this.tname = tname;
	}

	public int getDdigits() {
		return ddigits;
	}

	public void setDdigits(int ddigits) {
		this.ddigits = ddigits;
	}
}
