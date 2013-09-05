package graphics.Fire;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

import essentials.frameworks.game.GameBase;
import essentials.tools.helpers.dimensions.Aspect;
import essentials.tools.helpers.vectors.Vector2D;

public class FireAnimation extends GameBase implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ArrayList<Flame> flames;

	// private Mouse mouse;

	public void load() {
		// mouse = new Mouse();
		flames = new ArrayList<Flame>();
		int flameW = 200, flameH = 250;
		flames.add(new Flame(new Vector2D((width - flameW) / 2, (height + flameH) / 2), new Aspect(flameW, flameH), 240, 5));
	}

	public FireAnimation() {
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
		for (Flame flame : flames)
			flame.update();
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
		for (Flame flame : flames)
			flame.render(g);

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
		FireAnimation main = new FireAnimation();
		main.frame.init(main, main.title);
		main.start();
	}

}
