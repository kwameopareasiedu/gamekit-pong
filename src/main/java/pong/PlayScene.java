package pong;

import dev.gamekit.animation.Animation;
import dev.gamekit.core.Application;
import dev.gamekit.core.Constants;
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

public class PlayScene extends Scene {
  private static final long SCORE_DEBOUNCE_TIME_MS = 250L;
  private static final int PLAY_RADIUS = 2;
  private static final int PLAY_RADIUS_PX = PLAY_RADIUS * Constants.PIXELS_PER_METER;
  private static final int WALL_COUNT = 50;

  private final Spinner spinner = new Spinner();
  private final Ball ball = new Ball(this::handleBallTagCollision);
  private final Animation countdownAnimation = new Animation(1000, Animation.RepeatMode.RESTART);
  private long scoreDebounceTime = 0;
  private int countdown = 3;
  private int score = 0;
  private boolean gameOver = false;

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

    double interval = 360.0 / WALL_COUNT;
    double width = 12.75 / WALL_COUNT;

    for (double idx = 0; idx < 360; idx += interval) {
      addChild(new Wall(width, PLAY_RADIUS + 0.15, idx));
    }

    addChild(new Paddle(PLAY_RADIUS - 0.05));
    addChild(spinner);
    addChild(ball);

    countdownAnimation.setValueListener(value -> {
      if (value == 1 && countdown >= 0) {
        countdown--;
        updateUI();

        if (countdown == 0) {
          ball.launch();
          spinner.beginSpin();
        } else if (countdown < 0) {
          countdownAnimation.stop();
        }
      }
    });

    countdownAnimation.start();
  }

  @Override
  protected void update() {
    if (scoreDebounceTime > 0)
      scoreDebounceTime -= Constants.FRAME_TIME_MS;
  }

  @Override
  protected void render() {
    Renderer.clear(Color.WHITE);
    Renderer.fillCircle(0, 0, PLAY_RADIUS_PX).withColor(Color.BLACK);
  }

  @Override
  protected Widget createUI() {
    return Theme.create(
      Theme.config().textFont(pong.Constants.DEFAULT_FONT).textColor(Color.BLACK)
        .buttonDefaultBackground(pong.Constants.BUTTON_BG)
        .buttonHoverBackground(pong.Constants.BUTTON_HOVER_BG)
        .buttonPressedBackground(pong.Constants.BUTTON_PRESSED_BG),
      Stack.create(
        Align.create(
          Align.config().horizontalAlignment(Alignment.CENTER).verticalAlignment(Alignment.START),
          Padding.create(
            Padding.config().padding(80, 0, 0, 0),
            Text.create(
              Text.config().alignment(Alignment.CENTER).fontSize(24),
              "Move your mouse to rotate your paddle around the circle"
            )
          )
        ),
        countdown >= 0 ? Align.create(
          Align.config().horizontalAlignment(Alignment.CENTER).verticalAlignment(Alignment.CENTER),
          Text.create(
            Text.config().color(Color.WHITE).fontStyle(Font.BOLD).fontSize(108),
            countdown > 0 ? String.valueOf(countdown) : "Go!"
          )
        ) : Empty.create(),
        Align.create(
          Align.config().horizontalAlignment(Alignment.CENTER).verticalAlignment(Alignment.END),
          Padding.create(
            Padding.config().padding(24),
            Column.create(
              Column.config().crossAxisAlignment(CrossAxisAlignment.CENTER).gapSize(0),
              Text.create("Score"),
              Text.create(
                Text.config().fontStyle(Font.BOLD).fontSize(80),
                String.valueOf(score)
              )
            )
          )
        ),
        gameOver ? Align.create(
          Align.config().horizontalAlignment(Alignment.CENTER).verticalAlignment(Alignment.CENTER),
          Sized.create(
            Sized.config().fractionalWidth(0.65).height(512),
            Panel.create(
              Panel.config().background(pong.Constants.PANEL_BG).ninePatch(16),
              Padding.create(
                Padding.config().padding(32),
                Theme.create(
                  Theme.config().textColor(Color.WHITE)
                    .textAlignment(Alignment.CENTER).textFontSize(24),
                  Column.create(
                    Column.config().mainAxisAlignment(MainAxisAlignment.START)
                      .crossAxisAlignment(CrossAxisAlignment.CENTER),
                    Text.create(
                      Text.config().alignment(Alignment.CENTER)
                        .font(pong.Constants.HEADER_FONT).fontSize(48),
                      "Game Over"
                    ),
                    Text.create(
                      Text.config(),
                      "Score"
                    ),
                    Text.create(
                      Text.config().fontStyle(Font.BOLD).fontSize(80),
                      String.valueOf(score)
                    ),
                    Row.create(
                      Row.config().mainAxisAlignment(MainAxisAlignment.CENTER),
                      Button.create(
                        Button.config().mouseListener(this::handleRestart),
                        Padding.create(
                          Padding.config().padding(32, 16),
                          Text.create("Restart")
                        )
                      ),
                      Button.create(
                        Button.config().mouseListener(this::handleQuit),
                        Padding.create(
                          Padding.config().padding(32, 16),
                          Text.create("Main Menu")
                        )
                      )
                    )
                  )
                )
              )
            )
          )
        ) : Empty.create()
      )
    );
  }

  private void handleBallTagCollision(Tag tag) {
    if (tag == Tag.PADDLE && scoreDebounceTime <= 0) {
      scoreDebounceTime = SCORE_DEBOUNCE_TIME_MS;
      score++;
      updateUI();
    } else if (tag == Tag.WALL) {
      Application.getInstance().scheduleTask(() -> {
        gameOver = true;
        updateUI();
      }, 1000);
    }
  }

  private void handleRestart(MouseEvent ev) {
    if (ev.type == MouseEvent.Type.CLICK)
      Application.getInstance().loadScene(new PlayScene());
  }

  private void handleQuit(MouseEvent ev) {
    if (ev.type == MouseEvent.Type.CLICK)
      Application.getInstance().loadScene(new MenuScene());
  }
}
