package fr.xamez.simpleapplication.render;

import fr.xamez.simpleapplication.app.Application;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Renderer {

    private final static int ITEM_FRAME_SIZE = 128;

    private final Player p;
    public Renderer(Player p) {
        this.p = p;
    }

    public void renderApplication(Application application, Location location) {
        int frameW = application.getMapW() / ITEM_FRAME_SIZE;
        int frameH = application.getMapH() / ITEM_FRAME_SIZE;
        int frameX = 0;
        int frameY = 0;

        final BufferedImage bufferedImage = new BufferedImage(application.getMapW(), application.getMapH(), BufferedImage.TYPE_INT_ARGB);
        final Graphics graphics = bufferedImage.getGraphics();
        application.getComponents().forEach(component -> component.render(graphics));
        final Frame[] frames = new Frame[frameW * frameH];

        final Location initialLocation = location.clone();
        switch (p.getFacing()) {
            case NORTH -> initialLocation.add(-frameH / 2, 0, -frameH);
            case EAST -> initialLocation.add(frameH, 0, -frameH / 2);
            case SOUTH -> initialLocation.add(frameH / 2, 0, frameH);
            case WEST -> initialLocation.add(-frameH, 0, frameH / 2);
        }

        for (int i = 0; i < frameH; i++) {
            for (int j = 0; j < frameW; j++) {
                final Frame frame = new Frame(p, bufferedImage.getSubimage(frameX, frameY, ITEM_FRAME_SIZE, ITEM_FRAME_SIZE));
                frames[j + i * frameW] = frame;
                switch (p.getFacing()) {
                    case NORTH -> frame.create(initialLocation.clone().add(j, -i, 0));
                    case EAST -> frame.create(initialLocation.clone().add(0, -i, j));
                    case SOUTH -> frame.create(initialLocation.clone().add(-j, -i, 0));
                    case WEST -> frame.create(initialLocation.clone().add(0, -i, -j));
                }
                frameX += ITEM_FRAME_SIZE;
            }
            frameX = 0;
            frameY += ITEM_FRAME_SIZE;
        }

        FrameManager.APPLICATION_FRAMES.put(p, frames);

    }

    public void destroyApplication(Player p) {
        FrameManager.getInstance().destroyFrames(FrameManager.APPLICATION_FRAMES.get(p));
        FrameManager.APPLICATION_FRAMES.remove(p);
    }

}
