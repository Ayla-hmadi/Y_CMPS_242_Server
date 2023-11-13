package com.yplatform;

import com.yplatform.network.TCPServer;

public class App
{
    public static void main( String[] args ) {

        System.out.println( "TCP Server will start soon!" );
        TCPServer server = new TCPServer(43652);
        server.start();
        System.out.println( "Server started on port " + server.getPortNumber() );

    }
}
