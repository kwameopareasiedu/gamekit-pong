package pong;

import dev.gamekit.components.RigidBody;
import dev.gamekit.components.Transform;
import dev.gamekit.core.*;
import dev.gamekit.core.Component;
import dev.gamekit.core.Constants;
import dev.gamekit.utils.Vector;
import org.dyn4j.geometry.MassType;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class Ball extends Entity {
  private static final BufferedImage ARROW = IO.getResourceImage("arrow.png");

  private final ActionListener actionListener;
  private final double radius = 0.125;
  private boolean launched = false;

  public Ball(ActionListener actionListener) {
    super("Ball");
    this.actionListener = actionListener;
  }

  @Override
  protected List<Component> getComponents() {
    RigidBody rb = new RigidBody(
      MassType.NORMAL, new Vector(), 0.25, 1
    );

    rb.setGravityScale(0);
    rb.setPosition(0, -1);
    rb.addCircleFixture(radius, (fx, shape) -> {
      fx.setDensity(12);
      fx.setRestitution(1);
      fx.setFriction(0);
    });

    rb.addCollisionListener((body1, fixture1, body2, fixture2) -> {
      Object data = body2.getUserData();

      if (data instanceof Tag tag)
        actionListener.onTagCollision(tag);
    });

    return List.of(rb);
  }

  @Override
  protected void render() {
    Transform tx = findComponent(Transform.class);
    int x = (int) (tx.getX() * Constants.PIXELS_PER_METER);
    int y = (int) (tx.getY() * Constants.PIXELS_PER_METER);
    int radius = (int) (this.radius * Constants.PIXELS_PER_METER);
    Renderer.fillCircle(x, y, radius).withColor(Color.GREEN);

    if (!launched) {
      int iconY = (int) (-1.5 * Constants.PIXELS_PER_METER);
      Renderer.drawImage(ARROW, 0, iconY, 48, 48).withRotation(0, iconY, 90);
    }
  }

  void launch() {
    RigidBody rb = findComponent(RigidBody.class);
    rb.applyImpulse(0, -1.5);
    launched = true;
  }

  public interface ActionListener {
    void onTagCollision(Tag tag);
  }
}
