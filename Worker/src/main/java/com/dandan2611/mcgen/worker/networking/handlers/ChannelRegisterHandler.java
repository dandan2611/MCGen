package com.dandan2611.mcgen.worker.networking.handlers;

import com.dandan2611.mcgen.worker.networking.MasterServer;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.SocketAddress;

/**
 * Channel register handler
 * Used to create basic client informations and store them
 */
public class ChannelRegisterHandler extends ChannelInboundHandlerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(MasterServer.class);

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        SocketAddress address = ctx.channel().remoteAddress();

        LOGGER.info("<{}> New channel registered", address.toString());

        super.channelRegistered(ctx);
    }

}
