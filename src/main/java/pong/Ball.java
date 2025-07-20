package pong;

import dev.gamekit.components.RigidBody;
import dev.gamekit.components.Transform;
import dev.gamekit.core.Component;
import dev.gamekit.core.Constants;
import dev.gamekit.core.Entity;
import dev.gamekit.core.Renderer;
import dev.gamekit.utils.Vector;
import org.dyn4j.geometry.MassType;

import java.awt.*;
import java.util.List;

public class Ball extends Entity {
  private final double radius = 0.125;

  public Ball() {
    super("Ball");
  }

  @Override
  protected List<Component> getComponents() {
    RigidBody rb = new RigidBody(
      MassType.NORMAL, new Vector(), 0.25, 1
    );

    rb.setGravityScale(0);
    rb.addCircleFixture(radius, (fx, shape) -> {
      fx.setDensity(12);
      fx.setRestitution(1);
      fx.setFriction(0);
    });

    rb.applyImpulse(2, 1);

    return List.of(rb);
  }

  @Override
  protected void render() {
    Transform tx = findComponent(Transform.class);
    int x = (int) (tx.getX() * Constants.PIXELS_PER_METER);
    int y = (int) (tx.getY() * Constants.PIXELS_PER_METER);
    int radius = (int) (this.radius * Constants.PIXELS_PER_METER);
    Renderer.fillCircle(x, y, radius).withColor(Color.GREEN);
  }
}
