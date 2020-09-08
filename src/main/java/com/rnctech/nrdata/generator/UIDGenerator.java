package com.rnctech.nrdata.generator;

import java.util.ArrayList;
import java.util.List;
/* 
* @Author Zilin Chen
* @Date 2020/09/03
*/
public class UIDGenerator {

    public static void main(String args[]) {

        generateUID(20, 5000, 1000);
    }

    public static ArrayList generateUID(int total, int max, int min) {
        final ArrayList<String> UID = new ArrayList<String>(total);
        final String[] alpha = { "A", "B", "C", "D", "E", "F", "G", "H", "I",
                "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
                "V", "W", "X", "Y", "Z" };
        String ID = null;
        while (UID.size() != total) {
            final int itrno = total - UID.size();

            for (int i = 0; i < itrno; i++) {
                final int no = (int) ((Math.random() * (max - min)) + min);
                final int ano = (int) (Math.random() * 25);
                final String nos = Integer.toString(no);

                ID = alpha[ano] + nos;
                if (!UID.contains(ID)) {
                    UID.add(ID);
                }

            }
        }
        return UID;
    }

	public static List generateOrder(int total, int start, int inteval) {
		final List<String> OID = new ArrayList<String>(total);
		for(int i=0; i<total; i++){
			int o = start + i*inteval;
			OID.add(String.valueOf(o));
		}
		return OID;
	}

}
