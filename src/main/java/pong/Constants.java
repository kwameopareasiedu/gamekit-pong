package pong;

import dev.gamekit.core.IO;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Constants {
  public static final Color BG_COLOR = new Color(0x8E9BDD);
  public static final Font HEADER_FONT = IO.getResourceFont("crackman-font.otf");
  public static final Font DEFAULT_FONT = IO.getResourceFont("dosis-font.ttf");
  public static final BufferedImage PANEL_BG = IO.getResourceImage("panel-bg.png");
  public static final BufferedImage BUTTON_BG = IO.getResourceImage("button-bg.png");
  public static final BufferedImage BUTTON_HOVER_BG = IO.getResourceImage("button-bg-hover.png");
  public static final BufferedImage BUTTON_PRESSED_BG = IO.getResourceImage("button-bg-pressed.png");

  private Constants() { }
}
