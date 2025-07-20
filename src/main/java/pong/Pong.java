package pong;

import dev.gamekit.core.Application;
import dev.gamekit.settings.*;

public class Pong extends Application {
  public Pong() {
    super(
      new Settings(
        "GameKit - Pong",
        Resolution.create(600, 840),
        WindowMode.FULLSCREEN,
        RenderingStrategy.SPEED,
        TextAntialiasing.ON,
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
