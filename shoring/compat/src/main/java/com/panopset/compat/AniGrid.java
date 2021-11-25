package com.panopset.compat;

public class AniGrid {

  public final int mapw;
  public final int maph;
  private final Integer h;
  private final Integer ht;
  private final Integer w;
  private Double multiplier = 1.0;

  public AniGrid(final int mapWidth, final int mapHeight, final int gridWidth, final int gridHeight,
      final double multiplier) {
    this(mapWidth, mapHeight, gridWidth, gridHeight);
    this.multiplier = multiplier;
  }

  public AniGrid(final int mapWidth, final int mapHeight, final int gridWidth,
      final int gridHeight) {
    this.mapw = mapWidth;
    this.maph = mapHeight;
    this.w = mapw / gridWidth;
    this.h = maph / gridHeight;
    Integer wt = this.w + ((int) ((this.w * .5) * 0));
    this.ht = (wt * this.h) / this.w;
  }

  public int getMultiplier() {
    return multiplier.intValue();
  }

  public int getDspWidth() {
    double rtn = multiplier * w;
    return (int) rtn;
  }

  public int getDspHeight() {
    double rtn = multiplier * ht;
    return (int) rtn;
  }

  public int[] getPaintDimensions(final int gridx, final int gridy, final int x, final int y) {
    int sx1 = gridx * w;
    int sy1 = gridy * h;
    return new int[] {sx1, sy1, w, h, x, y, getDspWidth(), getDspHeight()};
  }

}
