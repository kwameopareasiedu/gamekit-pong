package pong;

import dev.gamekit.core.Application;
import dev.gamekit.settings.*;

public class Pong extends Application {
  public Pong() {
    super(
      new Settings(
        "GameKit - Pong",
        Resolution.create(600, 840),
        WindowMode.WINDOWED,
        RenderingStrategy.SPEED,
        Antialiasing.ON
      )
    );
  }

  public static void main(String[] args) {
    Pong pong = new Pong();
    pong.loadScene(new MenuScene());
//    pong.loadScene(new PlayScene());
    pong.run();
  }
}
