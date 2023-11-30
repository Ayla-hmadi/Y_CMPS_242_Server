package com.yplatform.network;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPServer {
    private static final Logger logger = LoggerFactory.getLogger(TCPServer.class);
    private final int portNumber;
    private final int tcpPort;

    public UDPServer(int portNumber, int tcpPort) {
        this.portNumber = portNumber;
        this.tcpPort = tcpPort;
    }

    public void start() {
        try {
            var socket = new DatagramSocket(portNumber);

            byte[] receiveData = new byte[1024];
            while (true) {
                var receivePacket = new DatagramPacket(receiveData, receiveData.length);
                socket.receive(receivePacket);

                String message = new String(receivePacket.getData(), 0, receivePacket.getLength());
                logger.info("Received message from client: " + message);

                if (message.equals("Hello Server")) {
                    var details = socket.getLocalAddress().getHostAddress() +
                            "," + tcpPort;
                    byte[] responseData = details.getBytes();

                    var sendPacket = new DatagramPacket(
                            responseData,
                            responseData.length,
                            receivePacket.getAddress(),
                            receivePacket.getPort()
                    );
                    socket.send(sendPacket);
                }
            }
        } catch (IOException e) {
            logger.error("UDP server", e);
        }
    }
}
