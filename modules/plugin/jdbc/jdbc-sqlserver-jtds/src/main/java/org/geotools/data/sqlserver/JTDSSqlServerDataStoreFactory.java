package org.geotools.data.sqlserver;

import java.io.IOException;
import java.util.Map;

public class JTDSSqlServerDataStoreFactory extends SQLServerDataStoreFactory {
    /** parameter for database type */
    public static final Param DBTYPE = new Param("dbtype", String.class, "Type", true, "jtds-sqlserver");
    /* (non-Javadoc)
     * @see org.geotools.data.sqlserver.SQLServerDataStoreFactory#getDescription()
     */
    @Override
    public String getDescription() {
        return "Microsoft SQL Server (JTDS Driver)";
    }

    /* (non-Javadoc)
     * @see org.geotools.data.sqlserver.SQLServerDataStoreFactory#getDriverClassName()
     */
    @Override
    protected String getDriverClassName() {

        return "net.sourceforge.jtds.jdbc.Driver";
    }


    /* (non-Javadoc)
     * @see org.geotools.data.sqlserver.SQLServerDataStoreFactory#getDatabaseID()
     */
    @Override
    protected String getDatabaseID() {
        return (String) DBTYPE.sample;
    }

    /* (non-Javadoc)
     * @see org.geotools.data.sqlserver.SQLServerDataStoreFactory#getJDBCUrl(java.util.Map)
     */
    @SuppressWarnings("unchecked")
    @Override
    protected String getJDBCUrl(Map params) throws IOException {
        //jdbc:jtds:<server_type>://<server>[:<port>][/<database>][;<property>=<value>[;...]]
        String host = (String) HOST.lookUp(params);
        Integer port = (Integer) PORT.lookUp(params);
        String db = (String) DATABASE.lookUp(params);
        String instance = (String) INSTANCE.lookUp(params);

        String url = "jdbc:jtds:sqlserver://" + host;
        if ( port != null ) {
            url += ":" + port;
        }

        if ( db != null ) {
            url += "/" + db;
        }


        if (instance != null) {
            url += ";instance="+instance;
        }

        Boolean intsec = (Boolean) INTSEC.lookUp(params);
        if (intsec != null && intsec.booleanValue()) {
            url = url + ";integratedSecurity=true";
        }

        return url;
    }

}
