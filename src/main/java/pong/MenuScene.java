package pong;

import dev.gamekit.animation.Animation;
import dev.gamekit.core.Renderer;
import dev.gamekit.core.Scene;
import dev.gamekit.ui.enums.Alignment;
import dev.gamekit.ui.enums.CrossAxisAlignment;
import dev.gamekit.ui.widgets.*;
import dev.gamekit.ui.widgets.Button;

import java.awt.*;

import static pong.Constants.BG_COLOR;
import static pong.Constants.DEFAULT_FONT;

public class MenuScene extends Scene {
  Animation rotateAnim = new Animation(3000, Animation.RepeatMode.RESTART);
  Animation bounceAnim = new Animation(1500, Animation.RepeatMode.ALTERNATE);

  public MenuScene() {
    super("Menu");
  }

  @Override
  protected void start() {
    rotateAnim.start();
    bounceAnim.start();
  }

  @Override
  protected void render() {
    Renderer.clear(BG_COLOR);

    Renderer.fillRect(0, 0, 96, 232)
      .withColor(Color.BLACK)
      .withRotation(0, -64, 360 * rotateAnim.getValue());

    Renderer.fillCircle(0, -64, 160).withColor(Color.WHITE);

    Renderer.fillCircle(0, -64 + (int) (-300 * (bounceAnim.getValue() - 0.5)), 16)
      .withColor(Color.GRAY);
  }

  @Override
  protected Widget createUI() {
    return Stack.create(
      Align.create(
        Align.options().horizontalAlignment(Alignment.CENTER).verticalAlignment(Alignment.START),
        Padding.create(
          Padding.options().padding(72),
          Column.create(
            Column.options().crossAxisAlignment(CrossAxisAlignment.CENTER).gapSize(2),
            Text.create(
              Text.options().font(DEFAULT_FONT).fontSize(64).alignment(Alignment.CENTER)
                .shadowEnabled(true).shadowOffset(6, 4).shadowColor(Color.DARK_GRAY),
              "Circular Pong"
            ),
            Text.create(
              Text.options().fontSize(24).alignment(Alignment.CENTER)
                .shadowEnabled(true).shadowOffset(3, 2).shadowColor(Color.DARK_GRAY),
              "Pong but in a circle!"
            )
          )
        )
      ),
      Align.create(
        Align.options().horizontalAlignment(Alignment.CENTER).verticalAlignment(Alignment.CENTER),
        Padding.create(
          Padding.options().padding(128, 0, 0, 0),
          Button.create(
            Button.options(),
            Padding.create(
              Padding.options().padding(32, 16),
              Text.create("Play")
            )
          )
        )
      ),
      Align.create(
        Align.options().horizontalAlignment(Alignment.CENTER).verticalAlignment(Alignment.END),
        Padding.create(
          Padding.options().padding(24),
          Column.create(
            Column.options().crossAxisAlignment(CrossAxisAlignment.CENTER).gapSize(4),
            Text.create(
              Text.options().fontSize(16).alignment(Alignment.CENTER),
              "Kwame Opare Asiedu"
            ),
            Text.create(
              Text.options().fontSize(16).alignment(Alignment.CENTER),
              "GameKit v0.5.0"
            )
          )
        )
      )
    );
  }
}
