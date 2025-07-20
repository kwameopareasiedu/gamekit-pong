package pong;

import dev.gamekit.core.Constants;
import dev.gamekit.core.Renderer;
import dev.gamekit.core.Scene;
import dev.gamekit.ui.widgets.Widget;

import java.awt.*;

public class PlayScene extends Scene {
  private static final int WORLD_RADIUS = 2 * Constants.PIXELS_PER_METER;
  private static final int WALL_COUNT = 50;

  public PlayScene() {
    super("Play");
  }

  @Override
  protected void start() {
    if (WALL_COUNT < 5) {
      throw new IllegalArgumentException(
        "Wall count must be greater than 5"
      );
    }

    double distanceFromCenter = 2;
    double interval = 360.0 / WALL_COUNT;
    double width = 12.75 / WALL_COUNT;

    for (double idx = 0; idx < 360; idx += interval)
      addChild(new Wall(width, distanceFromCenter, idx));

    addChild(new Paddle(distanceFromCenter - 0.05));
    addChild(new Ball());
    addChild(new Spinner());
  }

  @Override
  protected void render() {
    Renderer.clear(Color.LIGHT_GRAY);
    Renderer.fillCircle(0, 0, WORLD_RADIUS).withColor(Color.BLACK);
  }

  @Override
  protected Widget createUI() {
    return super.createUI();
  }
}
