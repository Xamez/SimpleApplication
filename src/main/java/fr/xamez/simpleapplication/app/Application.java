package fr.xamez.simpleapplication.app;

import fr.xamez.simpleapplication.app.component.Component;
import fr.xamez.simpleapplication.render.Renderer;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayDeque;
import java.util.Deque;

public class Application {

    private final Player p;
    private final String name;
    private final int mapW;
    private final int mapH;
    private final Renderer renderer;
    private final Deque<Component> components;


    public Application(Player p, String name, int mapW, int mapH) {
        this.p = p;
        this.name = name;
        if (mapW % 128 != 0 || mapH % 128 != 0) throw new IllegalArgumentException("Map width and height must be a multiple of 128");
        this.mapW = mapW;
        this.mapH = mapH;
        this.renderer = new Renderer(p);
        this.components = new ArrayDeque<>();
    }

    public void addComponent(Component component) {
        this.components.addLast(component);
    }

    public void removeComponent(Component component) {
        this.components.removeFirstOccurrence(component);
    }

    // components are rendered in the order they were added
    public void render(Location location) {
        this.renderer.renderApplication(this, location);
    }

    public int getMapW() {
        return mapW;
    }

    public int getMapH() {
        return mapH;
    }

    public Deque<Component> getComponents() {
        return components;
    }

}
