package com.dandan2611.mcgen.worker.networking;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WorkerClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(WorkerClient.class);

    private String masterIp;
    private int masterPort;

    private EventLoopGroup workerGroup;
    private ChannelFuture channelFuture;

    private Thread syncThread;

    public WorkerClient(String masterIp, int masterPort) {
        this.masterIp = masterIp;
        this.masterPort = masterPort;
    }

    public void init(){
        LOGGER.info("Initializing worker net-client on {}:{}", masterIp, masterPort);

        workerGroup = new EpollEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(workerGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {

                    }
                });

        this.channelFuture = bootstrap.connect(masterIp, masterPort);

        this.syncThread = new Thread(() -> {
            try {
                channelFuture.sync();
            }
            catch (InterruptedException exception) {
                LOGGER.info("Worker net-client ChannelFuture sync() interrupted");
            }
        }, "netclient_sync");

        syncThread.start();

        LOGGER.info("Worker net-client successfully started");
    }

    public void exit() {
        LOGGER.info("Exiting worker net-client");

        try {
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        workerGroup.shutdownGracefully();

        LOGGER.info("Worker net-client exited");
    }

}
