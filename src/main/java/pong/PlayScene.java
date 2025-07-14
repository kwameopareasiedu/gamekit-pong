package pong;

import dev.gamekit.core.Scene;
import dev.gamekit.traits.Physical;

public class PlayScene extends Scene {
  public PlayScene() {
    super("Play");
  }

  @Override
  protected void start() {
    super.start();
    Physical.DEBUG_DRAW = true;
    addChild(new Ball());
  }
}
