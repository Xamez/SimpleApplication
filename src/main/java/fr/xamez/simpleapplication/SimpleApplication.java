package fr.xamez.simpleapplication;

import com.destroystokyo.paper.event.player.PlayerUseUnknownEntityEvent;
import fr.xamez.simpleapplication.app.Application;
import fr.xamez.simpleapplication.app.component.Oval;
import fr.xamez.simpleapplication.app.component.Rectangle;
import fr.xamez.simpleapplication.app.component.Text;
import fr.xamez.simpleapplication.render.Frame;
import fr.xamez.simpleapplication.render.FrameManager;
import fr.xamez.simpleapplication.utils.ColoredText;
import io.netty.channel.Channel;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import net.minecraft.server.network.PlayerConnection;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.plugin.java.JavaPlugin;

import java.awt.*;

public class SimpleApplication extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(this, this);
        FrameManager.getInstance().renderAll();
    }

    @Override
    public void onDisable() {
        Frame[] frames = FrameManager.APPLICATION_FRAMES.get(this.getServer().getPlayer("Xamez"));
        if (frames != null)
            FrameManager.getInstance().destroyFrames(frames);
    }

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        if (e.getItem() == null) return;
        if (e.getHand().equals(EquipmentSlot.HAND) && e.getAction().isRightClick() && e.getItem().getType().equals(Material.STICK)) {
            int w = 3;
            int h = 2;
            ColoredText text = ColoredText.color(new Color(237, 29, 36)).text("Ceci est un test avec des accents (é, è)")
                    .append(Component.text(" et des couleurs")
                    .color(TextColor.color(255, 255, 255)));
            Application application = new Application(e.getPlayer(), "Hey", w*128, h*128);
            application.addComponent(new Rectangle(0, 0, application.getMapW(), application.getMapH(), Color.BLACK));
            application.addComponent(new Rectangle(0, 0, 30, 30, Color.CYAN));
            application.addComponent(new Rectangle(20, 10, 30, 30, Color.ORANGE));
            application.addComponent(new Rectangle(0, 30, 30, 30, Color.GREEN));
            application.addComponent(new Oval(10, 10, 10, 10, Color.PINK));
            application.addComponent(new Text(124, 124, text));
            application.render(e.getPlayer().getLocation().add(0, h-1, 0));
            e.getPlayer().sendMessage(Component.text().color(TextColor.color(0x00FF00)).append(Component.text("Frame rendered")));
        } else if (e.getHand().equals(EquipmentSlot.HAND) && e.getAction().isRightClick() && e.getItem().getType().equals(Material.NETHER_STAR)) {
            e.getPlayer().performCommand("plug reload SimpleApplication");
            e.getPlayer().performCommand("plug confirm");
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        PlayerConnection playerConnection = ((CraftPlayer) e.getPlayer()).getHandle().b;
        playerConnection.a().m.pipeline().addBefore("packet_handler", "simpleApplication", new ChannelListener(e.getPlayer()));
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        PlayerConnection playerConnection = ((CraftPlayer) e.getPlayer()).getHandle().b;
        Channel channel = playerConnection.a().m;
        channel.eventLoop().submit(() -> {
            channel.pipeline().remove(e.getPlayer().getName());
            return null;
        });
    }

}
