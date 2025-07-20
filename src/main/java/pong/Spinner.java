package pong;

import dev.gamekit.components.RigidBody;
import dev.gamekit.core.Component;
import dev.gamekit.core.Entity;
import dev.gamekit.core.Renderer;

import java.awt.*;
import java.util.List;

public class Spinner extends Entity {
  private final double width = 1.2;
  private final double height = 0.175;
  private double rotation = 0;

  public Spinner() {
    super("Spinner");
  }

  @Override
  protected void update() {
    rotation += 0.75;

    if (rotation >= 360)
      rotation -= 360;

    RigidBody rb = findComponent(RigidBody.class);
    rb.setRotation(rotation);
  }

  @Override
  protected List<Component> getComponents() {
    RigidBody rb = new RigidBody();

    rb.setCustomData("Spinner");
    rb.addRectFixture(width, height);

    return List.of(rb);
  }

  @Override
  protected void render() {
    int width = (int) (this.width * dev.gamekit.core.Constants.PIXELS_PER_METER);
    int height = (int) (this.height * dev.gamekit.core.Constants.PIXELS_PER_METER);
    Renderer.fillRect(0, 0, width, height).withColor(Color.CYAN).withRotation(0, 0, rotation);
  }
}
