package pong;

import dev.gamekit.animation.Animation;
import dev.gamekit.core.Application;
import dev.gamekit.core.Audio;
import dev.gamekit.core.Renderer;
import dev.gamekit.core.Scene;
import dev.gamekit.ui.enums.Alignment;
import dev.gamekit.ui.enums.CrossAxisAlignment;
import dev.gamekit.ui.enums.MainAxisAlignment;
import dev.gamekit.ui.events.MouseEvent;
import dev.gamekit.ui.widgets.*;
import dev.gamekit.ui.widgets.Button;
import dev.gamekit.ui.widgets.Panel;

import java.awt.*;

public class MenuScene extends Scene {
  private final Animation rotateAnim = new Animation(3000, Animation.RepeatMode.RESTART);
  private final Animation bounceAnim = new Animation(1500, Animation.RepeatMode.ALTERNATE);
  private boolean showHelp = false;

  public MenuScene() {
    super("Menu");
  }

  @Override
  protected void start() {
    rotateAnim.start();
    bounceAnim.start();
    Audio.get(Constants.MAIN_MENU_MUSIC_KEY).play(true);
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
    return Theme.create(
      Theme.config().textFont(Constants.DEFAULT_FONT)
        .buttonDefaultBackground(Constants.BUTTON_BG)
        .buttonHoverBackground(Constants.BUTTON_HOVER_BG)
        .buttonPressedBackground(Constants.BUTTON_PRESSED_BG),
      Stack.create(
        Align.create(
          Align.config().horizontalAlignment(Alignment.CENTER).verticalAlignment(Alignment.START),
          Padding.create(
            Padding.config().padding(128, 72),
            Column.create(
              Column.config().crossAxisAlignment(CrossAxisAlignment.CENTER).gapSize(2),
              Text.create(
                Text.config().font(Constants.HEADER_FONT).fontSize(80).alignment(Alignment.CENTER),
                "Circle Pong"
              ),
              Text.create(
                Text.config().fontSize(24).alignment(Alignment.CENTER),
                "Pong + Circle = Pong Circle!"
              )
            )
          )
        ),
        Align.create(
          Align.config().horizontalAlignment(Alignment.CENTER).verticalAlignment(Alignment.CENTER),
          Padding.create(
            Padding.config().padding(128, 0, 0, 0),
            Sized.create(
              Sized.config().width(160).intrinsicHeight(),
              Column.create(
                Column.config().crossAxisAlignment(CrossAxisAlignment.STRETCH),
                Button.create(
                  Button.config().mouseListener(this::handleStartGame),
                  Padding.create(
                    Padding.config().padding(32, 16),
                    Text.create("Play")
                  )
                ),
                Button.create(
                  Button.config().mouseListener(this::handleToggleHelpPanel),
                  Padding.create(
                    Padding.config().padding(32, 16),
                    Text.create("Help")
                  )
                ),
                Button.create(
                  Button.config().mouseListener(this::handleQuit),
                  Padding.create(
                    Padding.config().padding(32, 16),
                    Text.create("Quit")
                  )
                )
              )
            )
          )
        ),
        Align.create(
          Align.config().horizontalAlignment(Alignment.CENTER).verticalAlignment(Alignment.END),
          Theme.create(
            Theme.config().textFontSize(20).textAlignment(Alignment.CENTER),
            Padding.create(
              Padding.config().padding(24),
              Column.create(
                Column.config().crossAxisAlignment(CrossAxisAlignment.CENTER).gapSize(4),
                Text.create(
                  Text.config(),
                  "Kwame Opare Asiedu"
                ),
                Text.create(
                  Text.config(),
                  "GameKit v0.5.0-SNAPSHOT-1"
                )
              )
            )
          )
        ),
        createHelpPanel()
      )
    );
  }

  private Widget createHelpPanel() {
    if (!showHelp)
      return Empty.create();

    return Padding.create(
      Padding.config().padding(72, 0, 0, 0),
      Sized.create(
        Sized.config().fractionalWidth(0.5).height(420),
        Panel.create(
          Panel.config().background(Constants.PANEL_BG).ninePatch(16),
          Padding.create(
            Padding.config().padding(32),
            Theme.create(
              Theme.config().textColor(Color.BLACK)
                .textAlignment(Alignment.CENTER).textFontSize(24),
              Column.create(
                Column.config().mainAxisAlignment(MainAxisAlignment.START)
                  .crossAxisAlignment(CrossAxisAlignment.CENTER),
                Text.create(
                  Text.config().alignment(Alignment.CENTER)
                    .font(Constants.HEADER_FONT).fontSize(48),
                  "HELP"
                ),
                Text.create(
                  Text.config(),
                  "Keep the ball inside the circle for as long as possible."
                ),
                Text.create(
                  Text.config(),
                  "Move your mouse to rotate your paddle around the circle"
                ),
                Text.create(
                  Text.config(),
                  "It's so simple until it isn't. How long will you last?"
                ),
                Button.create(
                  Button.config().mouseListener(this::handleToggleHelpPanel),
                  Padding.create(
                    Padding.config().padding(32, 16),
                    Text.create("Close")
                  )
                )
              )
            )
          )
        )
      )
    );
  }

  private void handleStartGame(MouseEvent ev) {
    if (ev.type == MouseEvent.Type.CLICK) {
      Application.getInstance().loadScene(new PlayScene());
      Audio.get(Constants.HIT_SFX_KEY).play();
    }
  }

  private void handleToggleHelpPanel(MouseEvent ev) {
    if (ev.type == MouseEvent.Type.CLICK) {
      showHelp = !showHelp;
      updateUI();
    }
  }

  private void handleQuit(MouseEvent ev) {
    if (ev.type == MouseEvent.Type.CLICK) {
      Application.getInstance().quit();
    }
  }
}
