package com.rnctech.nrdata.model;
/* 
* @Author Zilin Chen
* @Date 2020/09/03
*/
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RawDataTable implements Serializable, Cloneable {
	
	private static final long serialVersionUID = -8095191608844372388L;
	private String name;
	private List<String> columnLabel = null;
	protected Map<String, Map<String, String>> columnProperties;
	private int[] columnType;
	private String[] columnName;
	private int columnCount;	
	private List<Object[]> objects;
	
	public RawDataTable(){
		super();
	}

	public RawDataTable(int[] columnType, String[] columnName) {
		this.columnType = columnType;
		this.columnName = columnName;
		columnCount = columnName.length;
		objects = new ArrayList<Object[]>();
	}
	
	public RawDataTable(int[] columnType, String[] columnName, List<String> columnLabel) {
		this(columnType, columnName);
		this.columnLabel = columnLabel;		
	}

	public int getColumnType(int index){
		return columnType[index];
	}
	
	public String getColumnName(int index){
		return columnName[index];
	}
	
	public String getColumnLabel(int index){
		return columnLabel.get(index);
	}
	
	public Object[] getRow(int rowcount){
		return objects.get(rowcount);
	}
	
	public void addRow(Object[] o) throws Exception {
		if(null == objects)
			objects = new ArrayList<Object[]>();
		if(null == o || o.length < columnCount)
			throw new Exception("Null row or value not enough.");
		objects.add(o);
	}
	
	public int getRowCount(){
		return (null == objects)?0:objects.size();
	}
	
	public int[] getColumnType() {
		return columnType;
	}

	public void setColumnType(int[] columnType) {
		this.columnType = columnType;
	}

	public String[] getColumnName() {
		return columnName;
	}

	public void setColumnName(String[] columnName) {
		this.columnName = columnName;
	}

	public int getColumnCount() {
		return columnCount;
	}

	public void setColumnCount(int columnCount) {
		this.columnCount = columnCount;
	}

	public List<Object[]> getObjects() {
		return objects;
	}

	public void setObjects(List<Object[]> objects) {
		this.objects = objects;
	}
	
	
}
