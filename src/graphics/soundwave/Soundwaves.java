package graphics.soundwave;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import essentials.frameworks.game.GameBase;

public class Soundwaves extends GameBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Screen screen;

	public Soundwaves() {
		title = "waves";
		width = 400;
		height = width / 16 * 9;
		scale = 1;
		ups = 60;
		construct(width, height, scale);
		screen = new Screen(width, height);
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
		screen.update();
	}

	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(2);
			return;
		}

		Graphics g = bs.getDrawGraphics();
		// draw here
		screen.render();
		g.drawImage(image, 0, 0, size.width, size.height, null);

		for (int i = 0; i < pixels.length; i++)
			pixels[i] = (int) screen.pixels[i];

		g.dispose();
		bs.show();
	}

	public static void main(String[] args) {
		Soundwaves main = new Soundwaves();
		main.frame.init(main, main.title);

		main.start();
	}

	@Override
	public void load() {
		// TODO Auto-generated method stub
		
	}

}
