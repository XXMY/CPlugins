package com.cfw.plugins.netty.http;

import com.cfw.plugins.netty.http.request.HttpRequestDispatchHandler;
import com.cfw.plugins.netty.http.response.HttpResponseHandler;
import com.cfw.plugins.netty.http.request.HttpRequestDataParseHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.Socket;

/**
 * Reference:
 * http://www.cnblogs.com/cyfonly/p/5616493.html
 */
public class HttpServer extends Thread {
    private Logger logger = LoggerFactory.getLogger(HttpServer.class);

    private int port;
    private int maxContentLenth;
    private HttpResponseHandler responseHandler;
    private HttpRequestDataParseHandler dataParseHandler;
    private HttpRequestDispatchHandler dispatchHandler;

    public HttpServer(int port){
        this.port = port;
        this.maxContentLenth = 1048576;
    }

    /**
     * If this thread was constructed using a separate
     * <code>Runnable</code> run object, then that
     * <code>Runnable</code> object's <code>run</code> method is called;
     * otherwise, this method does nothing and returns.
     * <p>
     * Subclasses of <code>Thread</code> should override this method.
     *
     * @see #start()
     * @see #stop()
     * @see #Thread(ThreadGroup, Runnable, String)
     */
    @Override
    public void run() {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup,workGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,1024)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChildChannelHandler());

            ChannelFuture channelFuture = serverBootstrap.bind(this.port).sync();
            this.logger.info("Netty Server startup succeed with port: {}", this.port);
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            this.logger.error(e.getMessage(),e);
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

    class ChildChannelHandler extends ChannelInitializer<SocketChannel> {

        /**
         * This method will be called once the {@link Channel} was registered. After the method returns this instance
         * will be removed from the {@link ChannelPipeline} of the {@link Channel}.
         *
         * @param ch the {@link Channel} which was registered.
         * @throws Exception is thrown if an error occurs. In that case it will be handled by
         *                   {@link #exceptionCaught(ChannelHandlerContext, Throwable)} which will by default close
         *                   the {@link Channel}.
         */
        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            ChannelPipeline pipeline = ch.pipeline();
            pipeline.addLast(new HttpServerCodec())
                    .addLast(new HttpObjectAggregator(HttpServer.this.maxContentLenth))
                    .addLast(HttpServer.this.responseHandler)
                    .addLast(HttpServer.this.dataParseHandler)
                    .addLast(HttpServer.this.dispatchHandler);
        }
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getMaxContentLenth() {
        return maxContentLenth;
    }

    public void setMaxContentLenth(int maxContentLenth) {
        this.maxContentLenth = maxContentLenth;
    }

    public HttpResponseHandler getResponseHandler() {
        return responseHandler;
    }

    public void setResponseHandler(HttpResponseHandler responseHandler) {
        this.responseHandler = responseHandler;
    }

    public HttpRequestDataParseHandler getDataParseHandler() {
        return dataParseHandler;
    }

    public void setDataParseHandler(HttpRequestDataParseHandler dataParseHandler) {
        this.dataParseHandler = dataParseHandler;
    }

    public HttpRequestDispatchHandler getDispatchHandler() {
        return dispatchHandler;
    }

    public void setDispatchHandler(HttpRequestDispatchHandler dispatchHandler) {
        this.dispatchHandler = dispatchHandler;
    }

    public static void main(String [] args) throws InterruptedException {
        new HttpServer(8080).start();
    }
}
