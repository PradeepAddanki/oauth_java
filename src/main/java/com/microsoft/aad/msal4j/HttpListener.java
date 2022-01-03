package com.microsoft.aad.msal4j;

import com.microsoft.aad.msal4j.Exception.VasaraCloudException;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;


class HttpListener {

    private final static Logger LOG = LoggerFactory.getLogger(HttpListener.class);

    private HttpServer server;

    private int port;

    public HttpServer getServer() {
        return server;
    }

    public void setServer(HttpServer server) {
        this.server = server;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    void startListener(int port, HttpHandler httpHandler) {
        try {
            server = HttpServer.create(new InetSocketAddress(port), 0);
            server.createContext("/", httpHandler);
            this.port = server.getAddress().getPort();
            server.start();
            LOG.debug("Http listener started. Listening on port: " + port);
        } catch (Exception e) {
            throw new VasaraCloudException(e.getMessage(),
                    AuthenticationErrorCode.UNABLE_TO_START_HTTP_LISTENER);
        }
    }

    void stopListener() {
        if (server != null) {
            server.stop(0);
            LOG.debug("Http listener stopped");

        }
    }
}
