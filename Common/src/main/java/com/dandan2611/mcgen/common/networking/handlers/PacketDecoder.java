package com.dandan2611.mcgen.common.networking.handlers;

import com.dandan2611.mcgen.common.networking.Packet;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

public class PacketDecoder extends ChannelInboundHandlerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(PacketDecoder.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buffer = (ByteBuf) msg;

        String json = buffer.toString(StandardCharsets.UTF_8);

        try {
            JsonObject object = new Gson().fromJson(json, JsonObject.class);

            if(object.has("packetId") && object.has("data")) {
                // TODO Packet listing & decoding

                super.channelRead(ctx, msg);
            }
            else {
                LOGGER.info("<{}> Unknown data received (no packet id/data)", ctx.channel().remoteAddress().toString());
            }
        }
        catch (Exception exception) {
            LOGGER.info("<{}> Unknown data received", ctx.channel().remoteAddress().toString(), exception);
        }
    }

}
