package Classes.Global.Subs;

import java.io.*;
import java.sql.*;
import java.util.*;

public class SQLiteWrapper
{
    private String databaseName;

    public SQLiteWrapper(String databaseName)
    {
        if (!new File(databaseName).exists())
        {
            System.out.println("Database file not found");
        }

        this.databaseName = databaseName;
    }

    public boolean executeQuery(String query)
    {
        try 
        (
            Connection connection = DriverManager.getConnection(getConnectionString());
            Statement statement = connection.createStatement();
        )
        {
            statement.executeQuery(query);
        } 
        catch (SQLException e) 
        {
            return false;
        }

        return true;
    }

    public ArrayList<HashMap<String, DbResultSet>> executeReaderQuery(String query)
    {
        try
        (
            Connection connection = DriverManager.getConnection(getConnectionString());
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
        )
        {
            ArrayList<HashMap<String, DbResultSet>> result = new ArrayList<>(0);

            ResultSetMetaData metaData = rs.getMetaData();

            while (rs.next())
            {
                HashMap<String, DbResultSet> map = new HashMap<>(0);

                for (int i=0; i<metaData.getColumnCount(); i++)
                {
                    String columnName = metaData.getColumnLabel(i + 1);
                    DbResultSet bdResult = new DbResultSet(metaData.getColumnType(i + 1), rs.getObject(i + 1));

                    map.put(columnName, bdResult);
                }

                result.add(map);
            }

            return result;
        }
        catch (SQLException e)
        {
            return null;
        }
    }

    public String getConnectionString()
    {
        return String.format("jdbc:sqlite:%s", databaseName);
    }

    public class DbResultSet
    {
        private int type;
        private Object result;

        public DbResultSet(int type, Object result)
        {
            this.type = type;
            this.result = result;
        }

        public int getType()
        {
            return type;
        }

        public Object getResult()
        {
            return result;
        }
    }

    @Override
    public String toString()
    {
        return String.format("DatabaseName=%s", databaseName);
    }
}
