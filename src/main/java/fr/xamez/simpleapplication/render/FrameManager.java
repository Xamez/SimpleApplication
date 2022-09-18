package fr.xamez.simpleapplication.render;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class FrameManager {

    public static final HashMap<Player, Frame[]> APPLICATION_FRAMES = new HashMap<>();

    private static FrameManager instance;

    public static FrameManager getInstance() {
        if (instance == null) instance = new FrameManager();
        return instance;
    }

    public void renderAll() {
        APPLICATION_FRAMES.forEach((player, frames) -> {
            for (Frame frame : frames) {
                frame.update();
            }
        });
    }

    public void destroyFrames(Frame[] frames) {
        for (Frame frame : frames) {
            frame.destroy();
        }
    }
}
