package com.example.heremiStartup;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import kotlin.jvm.Synchronized;

public class SocketHandler {

    private static Socket mSocket;

    @Synchronized
    public static void setSocket(){
            try {
                mSocket = IO.socket(apiClient.BASEURL);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
    }


    @Synchronized
    public static Socket getSocket(){
        return mSocket;
    }
}
