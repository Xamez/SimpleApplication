package fr.xamez.simpleapplication.render;

import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.network.protocol.game.PacketPlayOutEntityDestroy;
import net.minecraft.network.protocol.game.PacketPlayOutEntityMetadata;
import net.minecraft.network.protocol.game.PacketPlayOutSpawnEntity;
import net.minecraft.server.network.PlayerConnection;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.decoration.EntityItemFrame;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_19_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_19_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.jetbrains.annotations.NotNull;

import java.awt.image.BufferedImage;

public class Frame {

    private final Player p;

    private final PlayerConnection playerConnection;
    private MapView mapView;

    private int entityId;

    private boolean isCreated = false;

    private BufferedImage bufferedImage;

    public Frame(Player p, BufferedImage bufferedImage) {
        this.p = p;
        this.bufferedImage = bufferedImage;
        this.playerConnection = ((CraftPlayer) p).getHandle().b;
    }

    public void create(Location location) {
        createMap();
        update();
        createItemFrame(location);
    }

    private void createMap() {
        mapView = Bukkit.createMap(p.getWorld());
        mapView.getRenderers().clear();
        MapRenderer mapRendered = new MapRenderer() {
            @Override
            public void render(@NotNull MapView map, @NotNull MapCanvas canvas, @NotNull Player player) {
                canvas.drawImage(0, 0, bufferedImage.getScaledInstance(128, 128, 1));
            }
        };
        mapView.addRenderer(mapRendered);
        isCreated = true;
    }

    public void createItemFrame(Location location) {
        final BlockPosition position = new BlockPosition(location.getX(), location.getY(), location.getZ());
        EnumDirection dir = EnumDirection.c;
        switch(p.getFacing().getOppositeFace()) {
            case NORTH -> dir = EnumDirection.c;
            case SOUTH -> dir = EnumDirection.d;
            case WEST -> dir = EnumDirection.e;
            case EAST -> dir = EnumDirection.f;
        }
        final EntityItemFrame itemFrame = new EntityItemFrame(EntityTypes.U, ((CraftWorld) p.getWorld()).getHandle(), position, dir);
        entityId = itemFrame.ae();
        //itemFrame.j(true);
        final ItemStack map = new ItemStack(Material.FILLED_MAP);
        final MapMeta mapMeta = (MapMeta) map.getItemMeta();
        mapMeta.setMapView(mapView);
        map.setItemMeta(mapMeta);
        itemFrame.a(CraftItemStack.asNMSCopy(map));
        playerConnection.a(new PacketPlayOutSpawnEntity(itemFrame, dir.ordinal()));
        playerConnection.a(new PacketPlayOutEntityMetadata(entityId, itemFrame.ai(), true));
    }

    public void destroy() {
        if (isCreated) {
            mapView.getRenderers().clear();
            isCreated = false;
            playerConnection.a(new PacketPlayOutEntityDestroy(entityId));
        }
    }

    public void update() {
        if (isCreated)
            p.sendMap(mapView);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Frame frame = (Frame) o;

        return entityId == frame.entityId;
    }

    @Override
    public int hashCode() {
        return entityId;
    }
}
