package pong;

import dev.gamekit.components.RigidBody;
import dev.gamekit.core.Component;
import dev.gamekit.core.Entity;

import java.util.List;

public class Wall extends Entity {
  private final double distance;
  private final double rotation;

  public Wall(double distanceFromCenter, double rotationDeg) {
    super("Wall");
    this.distance = distanceFromCenter;
    this.rotation = rotationDeg;
  }

  @Override
  protected List<Component> getComponents() {
    RigidBody rb = new RigidBody();

    rb.setUserData(Tag.WALL);
    rb.addRectFixture(40, 12.8, (fx, shape) -> fx.setSensor(true));
    rb.setRotation(rotation, 0, 0, 0, distance);

    return List.of(rb);
  }
}
