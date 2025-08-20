package pong;

import dev.gamekit.components.CircleCollider;
import dev.gamekit.components.Collider;
import dev.gamekit.components.RigidBody;
import dev.gamekit.components.Transform;
import dev.gamekit.core.*;
import dev.gamekit.core.Component;
import dev.gamekit.utils.Vector;
import org.dyn4j.geometry.MassType;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class Ball extends Entity {
  private static final BufferedImage ARROW = IO.getResourceImage("arrow.png");

  private final ActionListener actionListener;
  private final double radius = 16.0;
  private boolean launched = false;

  public Ball(ActionListener actionListener) {
    super("Ball");
    this.actionListener = actionListener;
  }

  @Override
  protected List<Component> getComponents() {
    CircleCollider collider = new CircleCollider(radius);
    collider.setDensity(12);
    collider.setRestitution(1);
    collider.setFriction(0);
    collider.setCollisionListener(
      new Physics.CollisionListener() {
        @Override
        public void onCollisionEnter(Collider.BodyAttachedFixture fx) {
          Object data = fx.getUserData();

          if (data instanceof Tag tag)
            actionListener.onTagCollision(tag);
        }
      }
    );

    RigidBody rb = new RigidBody(MassType.NORMAL, new Vector(), 0.25, 1);
    rb.setGravityScale(0);
    rb.setPosition(0, -128);

    return List.of(collider, rb);
  }

  @Override
  protected void render() {
    Transform tx = findComponent(Transform.class);
    Vector globalPosition = tx.getGlobalPosition();
    Renderer.fillCircle(
      (int) globalPosition.x,
      (int) globalPosition.y,
      (int) radius
    ).withColor(Color.GREEN);

    if (!launched) {
      int iconY = -192;
      Renderer.drawImage(ARROW, 0, iconY, 48, 48)
        .withRotation(0, iconY, Math.PI / 2);
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
