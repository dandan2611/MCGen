package com.dandan2611.mcgen.common.networking.packets;

import com.dandan2611.mcgen.common.networking.EncodedPacket;
import com.dandan2611.mcgen.common.networking.Packet;
import com.google.gson.JsonObject;

import java.util.UUID;

public class PacketPing extends Packet<PacketPing> {

    public UUID id;

    public PacketPing() {

    }

    @Override
    public int id() {
        return 0;
    }

    @Override
    public EncodedPacket encode() {
        EncodedPacket encodedPacket = new EncodedPacket(id());

        JsonObject data = new JsonObject();

        data.addProperty("id", id.toString());

        encodedPacket.setData(data);

        return encodedPacket;
    }

    @Override
    public PacketPing decode(JsonObject data) {
        id = UUID.fromString(data.get("id").getAsString());

        return this;
    }

}
