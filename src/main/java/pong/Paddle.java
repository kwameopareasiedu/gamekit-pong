package pong;

import dev.gamekit.components.RigidBody;
import dev.gamekit.components.Transform;
import dev.gamekit.core.*;
import dev.gamekit.core.Component;
import dev.gamekit.core.Constants;
import dev.gamekit.core.Window;
import dev.gamekit.utils.Position;

import java.awt.*;
import java.util.List;

import static dev.gamekit.utils.Math.radToDeg;

public class Paddle extends Entity {
  private final double width = 0.9;
  private final double height = 0.15;
  private final double distance;
  private double rotation = 0;

  public Paddle(double distanceFromCenter) {
    super("Paddle");
    this.distance = distanceFromCenter;
  }

  @Override
  protected List<Component> getComponents() {
    RigidBody rb = new RigidBody();

    rb.setUserData(Tag.PADDLE);
    rb.addRectFixture(
      width, height,
      (fx, shape) -> fx.setFriction(0)
    );
    rb.setPosition(0, distance);

    return List.of(rb);
  }

  @Override
  protected void update() {
    Window.Info winInfo = Window.getInfo();
    Position mousePos = Input.getMousePosition();
    int centerX = mousePos.x - winInfo.displayCenterX();
    int centerY = mousePos.y - winInfo.displayCenterY();
    rotation = 90 + radToDeg(Math.atan2(centerY, centerX));

    RigidBody rb = findComponent(RigidBody.class);
    rb.setRotation(rotation, 0, 0, 0, distance);
  }

  @Override
  protected void render() {
    Transform tx = findComponent(Transform.class);
    int x = (int) (tx.getX() * Constants.PIXELS_PER_METER);
    int y = (int) (tx.getY() * Constants.PIXELS_PER_METER);
    int width = (int) (this.width * dev.gamekit.core.Constants.PIXELS_PER_METER);
    int height = (int) (this.height * dev.gamekit.core.Constants.PIXELS_PER_METER);
    Renderer.fillRect(x, y, width, height).withColor(Color.RED).withRotation(x, y, rotation);
  }
}
