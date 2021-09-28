package com.dandan2611.mcgen.common.networking;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class EncodedPacket {

    private ByteBuf buffer;

    public EncodedPacket() {
        this.buffer = Unpooled.buffer();
    }

    public void setData(JsonElement element) {
        if(buffer.readableBytes() > 0)
            throw new UnsupportedOperationException("Data already set");
        String json = new Gson().toJson(element);
        buffer.writeBytes(json.getBytes());
    }

    public ByteBuf getBuffer() {
        return this.buffer;
    }

    public void release() {
        buffer.release();
    }

}
