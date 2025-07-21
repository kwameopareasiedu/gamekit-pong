package pong;

import dev.gamekit.audio.AudioClip2D;
import dev.gamekit.audio.AudioGroup;
import dev.gamekit.core.Application;
import dev.gamekit.core.Audio;
import dev.gamekit.settings.*;

public class Pong extends Application {
  static {
    Audio.preload(
      Constants.MAIN_MENU_MUSIC_KEY,
      new AudioClip2D("ambient-music.wav", AudioGroup.MUSIC, 0.5)
    );

    Audio.preload(
      Constants.HIT_SFX_KEY,
      new AudioClip2D("hit.wav", AudioGroup.EFFECTS, 0.5)
    );

    Audio.preload(
      Constants.GAME_OVER_SFX_KEY,
      new AudioClip2D("game-over.wav", AudioGroup.EFFECTS, 0.5)
    );

    Audio.preload(
      Constants.HOVER_BEEP_KEY,
      new AudioClip2D("hover-beep.wav", AudioGroup.EFFECTS, 0.5)
    );
  }

  public Pong() {
    super(
      new Settings(
        "GameKit - Circle Pong",
        Resolution.create(640, 1000),
        WindowMode.WINDOWED,
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
