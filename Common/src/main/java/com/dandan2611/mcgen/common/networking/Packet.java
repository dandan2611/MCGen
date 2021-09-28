package com.dandan2611.mcgen.common.networking;


import com.google.gson.JsonObject;

public abstract class Packet<T extends Packet<?>> {

    public abstract int id();

    public abstract EncodedPacket encode();
    public abstract T decode(JsonObject data);

}
