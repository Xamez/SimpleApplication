package fr.xamez.simpleapplication;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.network.protocol.game.PacketPlayInEntityAction;
import net.minecraft.network.protocol.game.PacketPlayInUseEntity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ChannelListener extends ChannelDuplexHandler {

    private final Player p;

    public ChannelListener(Player p ) {
        this.p = p;
    }

    @Override
    public void channelRead(@NotNull ChannelHandlerContext ctx, @NotNull Object msg) throws Exception {
        if (msg instanceof PacketPlayInUseEntity packet) {
            p.sendMessage(packet.toString());
        }
        super.channelRead(ctx, msg);
    }
}
