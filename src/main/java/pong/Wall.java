package pong;

import dev.gamekit.components.BoxCollider;
import dev.gamekit.components.RigidBody;
import dev.gamekit.core.Component;
import dev.gamekit.core.Entity;

import java.util.List;

import static dev.gamekit.utils.Math.degToRad;

public class Wall extends Entity {
  private final double distance;
  private final double rotation;

  public Wall(double distanceFromCenter, double rotationDeg) {
    super("Wall");
    this.distance = distanceFromCenter;
    this.rotation = degToRad(rotationDeg);
  }

  @Override
  protected List<Component> getComponents() {
    BoxCollider collider = new BoxCollider(56, 12.8);
    collider.setMetaData(Tag.WALL);
    collider.setSensor(true);

    RigidBody rb = new RigidBody();
    rb.setRotation(rotation, 0, 0, 0, distance);

    return List.of(collider, rb);
  }
}
