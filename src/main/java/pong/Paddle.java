package pong;

import dev.gamekit.components.BoxCollider;
import dev.gamekit.components.RigidBody;
import dev.gamekit.components.Transform;
import dev.gamekit.core.*;
import dev.gamekit.core.Component;
import dev.gamekit.core.Window;
import dev.gamekit.utils.Position;
import dev.gamekit.utils.Vector;

import java.awt.*;
import java.util.List;

public class Paddle extends Entity {
  private static final double HALF_PI = Math.PI / 2;

  private final double width = 115.2;
  private final double height = 19.2;
  private final double distance;

  public Paddle(double distanceFromCenter) {
    super("Paddle");
    this.distance = distanceFromCenter;
  }

  @Override
  protected List<Component> getComponents() {
    BoxCollider collider = new BoxCollider(width, height);
    collider.setMetaData(Tag.PADDLE);
    collider.setFriction(0);

    RigidBody rb = new RigidBody();

    return List.of(collider, rb);
  }

  @Override
  protected void update() {
    Window.Info winInfo = Window.getInfo();
    Position mousePos = Input.getMousePosition();
    int centerX = mousePos.x - winInfo.displayCenterX();
    int centerY = mousePos.y - winInfo.displayCenterY();
    double rotation = HALF_PI + Math.atan2(centerY, centerX);

    RigidBody rb = findComponent(RigidBody.class);
    rb.setRotation(rotation, 0, 0, 0, distance);
  }

  @Override
  protected void render() {
    Transform tx = findComponent(Transform.class);
    Vector globalPosition = tx.getGlobalPosition();
    int x = (int) globalPosition.x;
    int y = (int) globalPosition.y;
    Renderer.fillRect(x, y, (int) width, (int) height)
      .withRotation(x, y, tx.getGlobalRotation()).withColor(Color.PINK);
  }
}
