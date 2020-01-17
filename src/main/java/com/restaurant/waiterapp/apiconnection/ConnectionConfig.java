package com.restaurant.waiterapp.apiconnection;

public class ConnectionConfig {
    private static final String IPADDRESS="http://10.0.2.2:8080";
    public static String getConnectionConfig() {
        return IPADDRESS;
    }
    private ConnectionConfig() {

    }
}
