package com.camon;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import org.junit.Test;

import java.util.Map;

public class TableTest {

    @Test
    public void tableTest() throws Exception {
        Table<Integer, String, String> mpsTable = HashBasedTable.create();
        mpsTable.put(1, "name", "jooyong");
        mpsTable.put(1, "id", "camon");
        mpsTable.put(2, "name", "jeongnam");
        mpsTable.put(2, "id", "ant103");
        mpsTable.put(3, "name", "minho");
        mpsTable.put(3, "id", "nomino");

        Map<String, String> row = mpsTable.row(1);
        Map<Integer, String> nameColumn = mpsTable.column("name");


        System.out.println(row);
        System.out.println(nameColumn);

        System.out.println(mpsTable.size());
        System.out.println(mpsTable);

        System.out.println(mpsTable.rowKeySet());
        System.out.println(mpsTable.rowMap());
        System.out.println(mpsTable.values());
    }
}
