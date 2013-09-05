package graphics.rain;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import essentials.frameworks.game.GameBase;
import essentials.tools.helpers.dimensions.Aspect;

public class RainAnimation extends GameBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Storm storm;

	public void load() {
		storm = new Storm(new Aspect(size.width, size.height), 40, 7, 2);
	}

	public RainAnimation() {
		title = "flames";
		width = 400;
		height = width / 5 * 4;
		scale = 1;
		ups = 60;

		construct(width, height, scale);

		load();
	}

	public void start() {
		running = true;
		thread = new Thread(this, "display");
		thread.start();
	}

	public void stop() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void update() {
		storm.update();
	}

	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(2);
			return;
		}

		Graphics g = bs.getDrawGraphics();
		if (frame.getAssignGraphics() == null) frame.assignGraphics(g);
		// draw here
		g.fillRect(0, 0, size.width, size.height);

		storm.render(g);

		g.dispose();
		bs.show();
	}

	public void run() {
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / ups;
		double delta = 0;
		int frames = 0, updates = 0;

		frame.requestFocus();
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				update();
				updates++;
				delta--;
			}
			render();
			frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				frame.setTitle(title + " | fps: " + frames + ", ups: " + updates);
				frames = 0;
				updates = 0;
			}
		}
	}

	public static void main(String[] args) {
		RainAnimation main = new RainAnimation();
		main.frame.init(main, main.title);

		main.start();
	}
}
