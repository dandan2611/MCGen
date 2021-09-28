package com.dandan2611.mcgen.common.networking;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class EncodedPacket {

    private int packetId;
    private ByteBuf buffer;

    public EncodedPacket(int packetId) {
        this.packetId = packetId;
        this.buffer = Unpooled.buffer();
    }

    public void setData(JsonObject element) {
        if(buffer.readableBytes() > 0)
            throw new UnsupportedOperationException("Data already set");

        JsonObject object = new JsonObject();

        object.addProperty("packetId", packetId);
        object.add("data", element);

        String json = new Gson().toJson(object);
        buffer.writeBytes(json.getBytes());
    }

    public ByteBuf getBuffer() {
        return this.buffer;
    }

    public void release() {
        buffer.release();
    }

}
