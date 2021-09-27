package com.dandan2611.mcgen.common.networking;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class EncodedPacket {

    private ByteBuf buf;

    public EncodedPacket() {
        this.buf = Unpooled.buffer();
    }

    private ByteBuf getBuffer() {
        return this.buf;
    }

}
