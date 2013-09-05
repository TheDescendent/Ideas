package graphics.soundwave;

public class Screen {

	public double[] pixels;
	public int height, width;

	public double ya;
	public boolean topped = false;

	public Screen(int width, int height) {
		this.width = width;
		this.height = height;
		pixels = new double[width * height];

	}

	public void update() {

	}

	public void render() {
		for (int y = 0; y < height; y++) {
			ya = y;
			for (int x = 0; x < width; x++) {
				if (topped) ya -= 0.3;
				if (!topped) ya += 0.3;
				if (ya > height / 2) topped = true;
				if (ya < 0) topped = false;

				pixels[x + (int) ya * width] = 0xffFF0000;
			}
		}
	}
}
