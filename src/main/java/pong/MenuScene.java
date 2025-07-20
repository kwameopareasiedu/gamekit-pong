package pong;

import dev.gamekit.animation.Animation;
import dev.gamekit.core.Application;
import dev.gamekit.core.Renderer;
import dev.gamekit.core.Scene;
import dev.gamekit.ui.enums.Alignment;
import dev.gamekit.ui.enums.CrossAxisAlignment;
import dev.gamekit.ui.events.MouseEvent;
import dev.gamekit.ui.widgets.*;
import dev.gamekit.ui.widgets.Button;

import java.awt.*;

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
    Renderer.clear(Constants.BG_COLOR);

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
        Align.config().horizontalAlignment(Alignment.CENTER).verticalAlignment(Alignment.START),
        Padding.create(
          Padding.config().padding(72),
          Column.create(
            Column.config().crossAxisAlignment(CrossAxisAlignment.CENTER).gapSize(2),
            Text.create(
              Text.config().font(Constants.DEFAULT_FONT).fontSize(64).alignment(Alignment.CENTER)
                .shadowEnabled(true).shadowOffset(6, 4).shadowColor(Color.DARK_GRAY),
              "Circle Pong"
            ),
            Text.create(
              Text.config().fontSize(24).alignment(Alignment.CENTER)
                .shadowEnabled(true).shadowOffset(3, 2).shadowColor(Color.DARK_GRAY),
              "Pong but in a circle!"
            )
          )
        )
      ),
      Align.create(
        Align.config().horizontalAlignment(Alignment.CENTER).verticalAlignment(Alignment.CENTER),
        Padding.create(
          Padding.config().padding(128, 0, 0, 0),
          Button.create(
            Button.config().mouseListener(this::handleStartGame),
            Padding.create(
              Padding.config().padding(32, 16),
              Text.create("Play")
            )
          )
        )
      ),
      Align.create(
        Align.config().horizontalAlignment(Alignment.END).verticalAlignment(Alignment.END),
        Padding.create(
          Padding.config().padding(24),
          Column.create(
            Column.config().crossAxisAlignment(CrossAxisAlignment.END).gapSize(4),
            Text.create(
              Text.config().fontSize(16).alignment(Alignment.CENTER),
              "Kwame Opare Asiedu"
            ),
            Text.create(
              Text.config().fontSize(16).alignment(Alignment.CENTER),
              "GameKit v0.5.0"
            )
          )
        )
      )
    );
  }

  private void handleStartGame(MouseEvent ev) {
    if (ev.type == MouseEvent.Type.CLICK)
      Application.getInstance().loadScene(new PlayScene());
  }
}
