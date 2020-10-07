package com.rnctech.nrdata.dbdata;

/**
 * Factory for fill database tables or schemas based on Column Definition
 * possibility to load schema info from online database and publish / fill with sample data
 * 
 * @Author Zilin Chen
 * @Date 2020/09/02
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import com.rnctech.nrdata.NrdataConstants;
import com.rnctech.nrdata.NrdataConstants.GENERATE_TYPE;
import com.rnctech.nrdata.NrdataConstants.VARIATION_TYPE;
import com.rnctech.nrdata.generator.DateTimeGenerator;
import com.rnctech.nrdata.generator.NameGenerator;
import com.rnctech.nrdata.generator.RandomWordGenerator;
import com.rnctech.nrdata.model.Column;
import com.rnctech.nrdata.model.RawDataTable;
import com.rnctech.nrdata.services.GeneralDataService;
import com.rnctech.nrdata.utils.DataUtils;

@Component
public class DBDataFactory {
	
	private static Map<String, String> variations = new HashMap<String, String>();
	public static Properties comap = new Properties();
	public static int totalrowGenerated = 100;
	private static String[] dataSets = {"COMMUNITY","CRM","HR"};
	private static String[][] Tables = {{"TYPE_ACCT","ACCT_TYPE","ACCT_NAMES"},{"ADDR","ADDRESS","ADDR_TYPE"},{"BRCH","BRCH","BRCH"}};

	@Autowired
	GeneralDataService dataGenService;
	
    private static final Logger LOGGER = LoggerFactory.getLogger(DBDataFactory.class.getName());

    @PostConstruct
    public Properties fetchProperties(){
        Properties properties = new Properties();
        try {
            File file = ResourceUtils.getFile("classpath:ColumnDefine.properties");
            InputStream in = new FileInputStream(file);
            comap.load(in);
    		
            variations =  DataUtils.getKeyValues(comap, "variation.", false);
    		
    		try {
    			totalrowGenerated = Integer.parseInt((String)comap.get("totalrow"));
    		} catch (NumberFormatException e) {
    		}
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        return properties;
    }	
	
	public static void main(String[] args) {
		String genpath = "/var/log/generated";
		String dbschema = "public";
		DBDataFactory ddf = new DBDataFactory();
		RawDataTable based = ddf.baseGeneration();
		String basefile = "TYPEACC";
		ddf.publishFiles(basefile, genpath, dbschema, Tables[0], true);
		basefile = "TYPEADD";
		ddf.publishFiles(basefile, genpath, dbschema, Tables[1], false);
		ddf.publishFiles(based, genpath, dbschema,Tables[2]);
	}
	
	private void publishFiles(String basefile, String path, String dbschema, String[] tables, boolean isExcp) {
		File f= new File(basefile+".csv");
		String[] colNames = {"ID","DESC","CREATOR","UPDATOR","CREATE_DATE"};
		int[] colTypes = {12,12,12,12,91};
		RawDataTable baseFFile = new RawDataTable(colTypes, colNames);
		try {
			BufferedReader br = new BufferedReader(new FileReader(f));
			String line = null;

			while(null != (line = br.readLine())){
				if(line.trim().length() > 0){
					String[] s = null;
					Object[] row = new Object[5];
					try {
						s = line.split(",");
					} catch (Exception e) {
						continue;
					}
					if(null !=s && s.length > 1){
						row[0] = s[0];
						row[1] = s[1];
					}else{
						row[0] = String.valueOf((s[0].charAt(0))).toUpperCase();
						row[1] = s[0];
					}
					row[2] = NameGenerator.genSimpleName();
					row[3] = NameGenerator.genSimpleName();
					row[4] = DateTimeGenerator.genSimpleDT(1971,2011);
					try {
						baseFFile.addRow(row);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}			
			}
			br.close();
			int total = baseFFile.getRowCount();
			
			File[] files = new File[tables.length];
			for(int i=0; i<tables.length;i++){			
				String tableName = dataSets[i]+"_"+tables[i];
				files[i] = new File(path+File.separator+tableName+".del");
				ArrayList<Column> tableCols = loadTableInfo(dbschema, tableName);
				Map<String, String> colmap = DataUtils.getKeyValues(comap, tableName, false);
				if(isExcp){
					int randommissing =RandomUtils.nextInt(1,total);
					int randomdup = RandomUtils.nextInt(1,total);
					boolean r = (randommissing == randomdup);
					while(r){
						randomdup = RandomUtils.nextInt(1,total);
						r = (randommissing == randomdup);
					}
					writeData(baseFFile, files[i], tableCols, colmap, 0, total-1, 0, randommissing, randomdup);
				}else{
					writeData(baseFFile, files[i], tableCols, colmap, 0, total-1, 0, -1, -1);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public RawDataTable baseGeneration() {				
		List<Column> cdecolumns = DataUtils.getBaseColumns(comap, "baseColumn");
		RawDataTable based = baseDateGeneration(cdecolumns, totalrowGenerated);
		return based;
	}
	
	public RawDataTable baseDateGeneration(List<Column> cdecolumns, int total) {
		String[] colNames = new String[cdecolumns.size()];
		int[] colTypes = new int[cdecolumns.size()];
		for(int i=0;i<cdecolumns.size();i++){
			colNames[i] = cdecolumns.get(i).getName();
			colTypes[i] = cdecolumns.get(i).getSqltype();
		}
		RawDataTable dt = new RawDataTable(colTypes, colNames);
		//generate data here
		for(int i=0; i<cdecolumns.size();i++){
			Column c = cdecolumns.get(i);
			switch(c.gtype){
				case NAME:
				{
					List<Object> names = dataGenService.genFullNames(total, c.getSize());
					addAllRows(dt, names, i);
					break;
				}
				case FN:
				{
					List<Object> names = dataGenService.genFNames(total, c.getSize());
					addAllRows(dt, names, i);
					break;
				}
				case MN:
				{
					List<Object> names = dataGenService.genMiddleNames(total, c.getSize());
					addAllRows(dt, names, i);
					break;
				}
				case LN:
				{
					List<Object> names = dataGenService.genLNames(total, c.getSize());
					addAllRows(dt, names, i);
					break;
				}
				case SSN:
				{
					List<Object> ssns = dataGenService.genSSNs(total);
					addAllRows(dt, ssns, i);
					break;
				}
				case ADDRESS:
				{
					List<Object> adds = dataGenService.genADDRESSs(total);
					addAllRows(dt, adds, i);
					break;
				}
				case CITY:
				{
					List<Object> citys = dataGenService.genCITYs(total);
					addAllRows(dt, citys, i);
					break;
				}
				case STATE:
				{
					List<Object> states = dataGenService.genSTATEs(total);
					addAllRows(dt, states, i);
					break;
				}
				case PHONE:
				{
					List<Object> ps = dataGenService.genPHONEs(total);
					addAllRows(dt, ps, i);
					break;
				}
				case POSTCODE:
				{
					List<Object> ps = dataGenService.genPOSTCODEs(total);
					addAllRows(dt, ps, i);
					break;
				}
				case FULLDATE:
				{
					List<Object> ds = dataGenService.genDATEs(total, GENERATE_TYPE.FULLDATE);
					addAllRows(dt, ds, i);
					break;
				}
				case SEQORDEER:
				{
					List<Object> ss = dataGenService.genSEQs(total,GENERATE_TYPE.SEQORDEER);
					addAllRows(dt, ss, i);
					break;
				}
				case IDALPHANUMBER:
				{
					List<Object> ids = dataGenService.genIDs(total, GENERATE_TYPE.IDALPHANUMBER);
					addAllRows(dt, ids, i);
					break;
				}
				case CREDITCARD:{
					List<Object> ids = dataGenService.genCreditCards(total);
					addAllRows(dt, ids, i);
					break;
				}
				case EMAIL:{
					List<Object> ids = dataGenService.genEmails(total);
					addAllRows(dt, ids, i);
					break;
				}
				case TYPEADD:
				case TYPEACC:{
					List<Object> ids = dataGenService.genTypeData(total, c);
					addAllRows(dt, ids, i);
					break;
				}
			}
		}	
		return dt;
	}
	
	
	private void addAllRows(RawDataTable dt, List<Object> objects, int i) {
		for(int rindex=0;rindex < objects.size(); rindex++){
			Object[] row = null;;
			try {
				row = dt.getRow(rindex);
			} catch (Exception e1) {
			}
			if(null == row){
				row = new Object[dt.getColumnCount()];
				try {
					dt.addRow(row);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			row[i] = objects.get(rindex);
		}
	}

	public void publishFiles(RawDataTable rtable, String path, String dbschema, String[] tables){
		File directory = new File(path);
		if(!directory.exists())directory.mkdirs();
		
		File basefile = new File(path+File.separator+"base.del");
		writeBaseData(rtable, basefile);
		
		
		File[] files = new File[tables.length];
		for(int i=0; i<tables.length;i++){	
			int h = 10 + RandomUtils.nextInt(0,10);
			int w = 200 + RandomUtils.nextInt(0,50);
			String tableName = dataSets[i]+"_"+tables[i];
			files[i] = new File(path+File.separator+tableName+".del");
			ArrayList<Column> tableCols = loadTableInfo(dbschema, tableName);
			Map<String, String> colmap = DataUtils.getKeyValues(comap, tableName, false);
			int randommissing = i*h + RandomUtils.nextInt(0,w);
			int randomdup = i*h + RandomUtils.nextInt(0,w);
			boolean r = (randommissing == randomdup);
			while(r){
				randomdup = i*h + RandomUtils.nextInt(0,w);
				r = (randommissing == randomdup);
			}
			writeData(rtable, files[i], tableCols, colmap, i*h, Math.min(i*h+w, totalrowGenerated), 3, randommissing, randomdup);
		}
	}
	
	private void writeBaseData(RawDataTable rtable, File bfile){
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(bfile);
			final int colCnt = rtable.getColumnCount();
			String[] values = new String[colCnt];
 	            
	            for (int i = 0; i < colCnt; i++) {
	                values[i] = rtable.getColumnName(i);
	            }
	            pw.println(formatRow(values));
	
	            for (int i = 0; i < colCnt; i++) {
	                values[i] = Integer.toString(rtable.getColumnType(i));
	            }
	            pw.println(formatRow(values));

            if(null != rtable.getObjects() && rtable.getObjects().size() !=0){
            for(Object[] rs : rtable.getObjects()){
            	for (int i = 0; i < colCnt; i++) {
                    final Object value = rs[i];
                    values[i] = null == value ? "" : value.toString().trim();
                }
                pw.println(formatRow(values));
            }
            }
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(null !=pw){
				pw.flush();
				pw.close();
			}
		}
	}
	
	private void writeData(RawDataTable rtable, File bfile, ArrayList<Column> tableCols, Map<String, String> colmap, int start, int end, int diffcount, int missing, int dup){
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(bfile);
			final int colCnt = tableCols.size();
			String[] values = new String[colCnt];
			int[] rdiffcounts = RandomWordGenerator.getRandomCounts(start, end - start, diffcount);
            if(null != rtable.getObjects() && rtable.getObjects().size() !=0){
            Map<Integer,Integer>  mappedColumnIndex = getMappedColumnIndex(rtable, tableCols, colmap);
            String tablename = tableCols.get(0).getTname();
            for(int j=start;j<=end;j++){
            	if(j==missing){
            		System.out.println("Missed: "+j+" for table "+tablename);
            		continue;
            	}
            	Object[] rs = rtable.getRow(j);
            	boolean isDiff = false;
    			int d=0;
    			while(!isDiff && d<diffcount){
    				if(j==rdiffcounts[d]){
    					isDiff = true;
    				}
    				d++;
    			}
            	for (int i = 0; i < colCnt; i++) {
            		if(mappedColumnIndex.containsKey(i)){            			
            			int mappedindex = mappedColumnIndex.get(i);
            			final Object value = rs[mappedindex];
            			values[i] = null == value ? "" : modifyValue(value, isDiff, tableCols.get(i)); 
            				//((isDiff && tableCols.get(i).getSqltype() == 12)?"XXX":"")+value.toString().trim();
            		}else{
            			values[i] = generateValue(tableCols.get(i));
            		}
                }            	
                pw.println(formatRow(values));
                if(j==dup){
                	System.out.println("Depulicated: "+j+" for table "+tablename);
                	pw.println(formatRow(values));
                }
                if(isDiff){
                	System.out.println("Diff for table "+ j+"  "+tablename+"\n : "+formatRow(values));
                }
            }
            }
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(null !=pw){
				pw.flush();
				pw.close();
			}
		}
	}
	
	private String modifyValue(Object value, boolean isDiff, Column column) {
		String ret = ((isDiff && column.getSqltype() == 12)?"X":"")+value.toString().trim();
		try {			
			String tname = column.getTname();
			if(null != tname && tname.length() > 0){
				String variation = variations.get(tname+"."+column.getName());
				if(null != variation){
					String[] vs = variation.split(":");
					VARIATION_TYPE vt = VARIATION_TYPE.valueOf(vs[0]);
					if(null != vt){
						switch(vt){
						case PREFIX:{
							ret = vs[1]+ret;
							break;
						}
						case SUFFIX:{
							ret = ret+vs[1];
							break;
						}
						case DELI:{
							String s = value.toString().trim();
							if(s.length() > 6){
								ret = s.substring(0,3)+vs[1]+s.substring(3,5)+vs[1]+s.substring(5);
							}else if(s.length()>3){
								ret = s.substring(0,3)+vs[1]+s.substring(3);
							}
							break;
						}
						case PARADELI:{
							String s = value.toString().trim();
							if(s.length() > 6){
								ret = "("+s.substring(0,3)+") "+s.substring(3,6)+vs[1]+s.substring(6);
							}
							break;
						}
						case PARA:{
							String s = value.toString().trim();
							if(s.length() > 3){
								ret = "("+s.substring(0,3)+") "+s.substring(3);
							}
							break;
						}
						case VARY:{
							String s = value.toString().trim();
							if(s.length() > 3){
							int i = RandomUtils.nextInt(0,5);
								if(i==1){
									ret = s.substring(3);
								}else if(i==2){
									ret = "1 ("+s.substring(0,3)+") "+s.substring(3);
								}else if(i==3){
									ret = s.substring(0,3)+"-"+s.substring(3,6)+"-"+s.substring(6);
								}else if(i==4){
									ret = s.substring(0,3)+" "+s.substring(3,6)+" "+s.substring(6);
								}
							}
							break;
						}
						}
					}
				}
			}
		} catch (Exception e) {}
		return ret;
	}

	private String generateValue(Column column) {
		String ret = "";
		if (!column.isNotnull()) {
			// generate 10% null value;
			if (RandomUtils.nextInt(0,10) > 7)
				return ret;
		}
		if(column.getName().startsWith("COUNTRY")){
			return "USA";
		}
		int sqltype = column.getSqltype();
		switch (sqltype) {
		case 12: {
			ret = RandomWordGenerator.getRandomWord(column.getSize() / 2);
			break;
		}
		case 4:
		case 5:
		case -6:
		case -5: {
			ret = String.valueOf(RandomUtils.nextInt(1,10000));
			break;
		}
		case 91:
		case 92:
		case 93: {
			ret = NrdataConstants.sdf.format(new Date());
			break;
		}
		case 2:
		case 3:
		case 6:
		case 7:
		case 8: {
			ret = String.valueOf(100.00 * RandomUtils.nextDouble());
			break;
		}
		case 16:{
			ret = String.valueOf(RandomUtils.nextBoolean());
			break;
		}
		case 1:{
			ret = (RandomUtils.nextBoolean()?"M":"F");
			break;
		}
		}
		return ret;
	}

	private Map<Integer, Integer> getMappedColumnIndex(RawDataTable rtable,
			ArrayList<Column> tableCols, Map<String, String> colmap) {
		Map<Integer, Integer> indexmap = new HashMap<Integer, Integer>();
		for (int index = 0; index < tableCols.size(); index++) {
			String colname = tableCols.get(index).getName();
			if (colmap.containsKey(colname)) {
				String basecolname = colmap.get(colname);
				for (int i = 0; i < rtable.getColumnCount(); i++) {
					String s = rtable.getColumnName(i);
					if (s.equalsIgnoreCase(basecolname)) {
						indexmap.put(index, i);
					}
				}
			}
		}
		return indexmap;
	}

	private String formatRow(String[] values) {
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<values.length;i++){
			sb.append(values[i]);
			if(i != values.length -1)
				sb.append(",");
		}
		return sb.toString();
	}

	public ArrayList<Column> loadTableInfo(String schema, String tableName){
		ArrayList<Column> columns = new ArrayList<Column>();
		Connection conn = null;
		ResultSet rs = null;
		Statement stmt = null;
		try {
			conn = getPostgresConnection();
			DatabaseMetaData dbmd = conn.getMetaData();
			ResultSet rsc = dbmd.getColumns(null, schema, tableName, "%");
			while (rsc.next()) {
				String tn = rsc.getString("TABLE_NAME");
				String cn = rsc.getString("COLUMN_NAME");
				String dt = rsc.getString("TYPE_NAME");
				int sqlType = rsc.getInt("DATA_TYPE");
				int cs = rsc.getInt("COLUMN_SIZE");
				int dd = rsc.getInt("DECIMAL_DIGITS");
				boolean notnull = rsc.getBoolean("IS_NULLABLE");
				Column c = new Column(cn, dt, false);
				c.setTname(tableName);
				c.setDdigits(dd);
				c.setSqltype(sqlType);
				c.setSize(cs);
				c.setNotnull(notnull);
				columns.add(c);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Exception: " + e.getMessage());
		} finally {
			try {
				if (null != rs)
					rs.close();
				if (null != stmt)
					stmt.close();
				if (null != conn)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return columns;
	}
	
	public static Connection getPostgresConnection() throws Exception {
		String driver = "org.postgresql.Driver";
		String url = "jdbc:postgresql://localhost:5432/nrdata";
		String username = "postgres";
		String password = "Admin123!";

		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url, username, password);
		return conn;
	}
	
	public List genTypeData(int total, Column c) {
		String fname = c.getGtype().name()+".txt";
		List<String> nameparts = new ArrayList<String>();
		String[] options = DataUtils.readFile(NrdataConstants.Data_Folder+fname, Integer.MAX_VALUE);
		int count = 0;
		while(count<total){
			String t = options[RandomUtils.nextInt(1, options.length)];
			if(c.getGtype().equals(GENERATE_TYPE.TYPEACC)){
				nameparts.add(t.substring(0, 3));
			}else if(c.getGtype().equals(GENERATE_TYPE.TYPEADD)){
				nameparts.add(String.valueOf(t.charAt(0)).toUpperCase());
			}else{
				nameparts.add(t);
			}
			count++;
		}
		return nameparts;
	}
}
