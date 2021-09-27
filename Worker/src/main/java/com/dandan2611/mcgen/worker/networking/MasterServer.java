package com.dandan2611.mcgen.worker.networking;

import com.dandan2611.mcgen.worker.networking.handlers.ChannelRegisterHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MasterServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(MasterServer.class);

    private int port;

    private EventLoopGroup bossGroup, workerGroup;
    private ChannelFuture channelFuture;

    private Thread syncThread;

    public MasterServer(int port) {
        this.port = port;
    }

    public void init() {
        LOGGER.info("Initializing master server");

        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 128)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline()
                                .addLast(new ChannelRegisterHandler());
                    }
                });

        this.channelFuture = serverBootstrap.bind(port);

        this.syncThread = new Thread(() -> {
            try {
                channelFuture.sync();
            }
            catch (InterruptedException exception) {
                LOGGER.info("Master ChannelFuture sync() interrupted");
            }
        }, "masterserver_sync");
        syncThread.start();

        LOGGER.info("Master server initialized!");
    }

    public void exit() {
        LOGGER.info("Exiting master server");

        try {
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        workerGroup.shutdownGracefully();
        bossGroup.shutdownGracefully();

        LOGGER.info("Master server exited!");
    }

}
