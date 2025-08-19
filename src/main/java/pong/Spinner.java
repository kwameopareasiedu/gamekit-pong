package pong;

import dev.gamekit.components.BoxCollider;
import dev.gamekit.components.RigidBody;
import dev.gamekit.core.Component;
import dev.gamekit.core.Entity;
import dev.gamekit.core.Renderer;

import java.awt.*;
import java.util.List;

public class Spinner extends Entity {
  private final double width = 153.6;
  private final double height = 22.4;
  private double rotation = 0;
  private boolean spin = false;

  public Spinner() {
    super("Spinner");
  }

  @Override
  protected void update() {
    if (spin) {
      rotation += 0.75;

      if (rotation >= 360)
        rotation -= 360;

      RigidBody rb = findComponent(RigidBody.class);
      rb.setRotation(rotation);
    }
  }

  @Override
  protected List<Component> getComponents() {
    BoxCollider collider = new BoxCollider(width, height);
    collider.setMetaData(Tag.SPINNER);

    RigidBody rb = new RigidBody();

    return List.of(collider, rb);
  }

  @Override
  protected void render() {
    Renderer.fillRect(0, 0, (int) width, (int) height).withColor(Color.CYAN)
      .withRotation(0, 0, rotation);
  }

  void beginSpin() {
    spin = true;
  }
}
