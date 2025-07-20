package pong;

import dev.gamekit.components.RigidBody;
import dev.gamekit.core.Component;
import dev.gamekit.core.Entity;
import dev.gamekit.utils.Vector;

import java.util.List;

public class Wall extends Entity {
  private final double width;
  private final double distance;
  private final double rotation;

  public Wall(double width, double distanceFromCenter, double rotationDeg) {
    super("Wall");
    this.width = width;
    this.distance = distanceFromCenter;
    this.rotation = rotationDeg;
  }

  @Override
  protected List<Component> getComponents() {
    RigidBody rb = new RigidBody();

    rb.setCustomData("Wall");
    rb.addRectFixture(width, 0.1);
    rb.setRotation(rotation, 0, 0, 0, distance);

    return List.of(rb);
  }
}
