package y111studios;

/**
 * Stores the camera position, velocity and scale.
 */
public class Camera {
  @SuppressWarnings("MemberName")
  public float x;
  @SuppressWarnings("MemberName")
  public float y;

  public int width;
  public int height;

  public float vx;
  public float vy;
  public float vZoom;

  public float scale;

  private float time;
  // class variable in case of usage in future animations
  private final float timeStepSize = 1/1000f;

    /**
   * Initializes the camera at the given coordinates.
   */
  public Camera(int x, int y, int width, int height) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    vx = 0;
    vy = 0;
    vZoom = 0;
    scale = 1080f / height;
  }

  /**
   * Updates camera position based on velocity.
   */
  public void shift() {
    x += (int) (vx * scale);
    y += (int) (vy * scale);
  }

  /**
   * Changes the velocity of the camera.
   *
   * @param vx The change to horizontal velocity.
   * @param vy The change to vertical velocity.
   */
  public void addVelocity(float vx, float vy) {
    this.vx += vx;
    this.vy += vy;
  }

  /**
   * Resets the camera's velocity.
   * 
   */
  public void velocityReset() {
    this.vx = 0;
    this.vy = 0;
  }

  public void resize(int width, int height) {
    x += this.width * scale / 2;
    y += this.height * scale / 2;
    scale *= (float)this.height / height;
    this.width = width;
    this.height = height;
    x -= this.width * scale / 2;
    y -= this.height * scale / 2;
  }

  public void zoom(float factor) {
    x += width * scale / 2;
    y += height * scale / 2;
    scale *= factor;
    if (scale > 3500f / height) {
      scale = 3500f / height;
    } else if (scale < 500f / height) {
      scale = 500f / height;
    }
    x -= width * scale / 2;
    y -= height * scale / 2;
  }

  public void updateZoom(float delta) {
    time += delta;
    while (time > 0) {
      vZoom *= 0.99f;
      zoom(1.0f + vZoom);
      time -= timeStepSize;
    }
  }

  public void pan(int x, int y) {
    this.x += x * scale;
    this.y += y * scale;
  }
}
