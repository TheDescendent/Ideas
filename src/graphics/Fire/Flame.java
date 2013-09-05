package graphics.Fire;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import essentials.tools.helpers.dimensions.Aspect;
import essentials.tools.helpers.shapes.Rectangle;
import essentials.tools.helpers.vectors.Vector2D;

public class Flame {

	private Aspect size;
	private Vector2D location;

	private ArrayList<Rectangle> particles;
	private Color[] colors = { Color.yellow, Color.orange, Color.red, Color.gray };
	private int[] heights;

	private int speedBase = 3, speedRange = 3, heightRange = 25;
	private static Random random = new Random();

	private void init() {
		size = new Aspect();
		location = new Vector2D();
		particles = new ArrayList<Rectangle>();
		heights = new int[4];
	}

	public Flame(Vector2D location, Aspect size, int maxParticles, int SIZE) {
		init();
		this.location = location;
		this.size = size;
		heights[0] = (size.width / 10) * 1;
		heights[1] = (size.width / 10) * 4;
		heights[2] = (size.width / 10) * 6;
		heights[3] = size.width;

		for (int i = 0; i < maxParticles; i++) {
			particles.add(new Rectangle());
			particles.get(i).getSize().width = particles.get(i).getSize().height = SIZE;
			resetParticle(particles.get(i));
		}

	}

	public void update() {
		for (Rectangle particle : particles) {
			if (particles.indexOf(particle) < particles.size() / 4) {
				particle.setColor(colors[0]);
				if (particle.getLocation().y >= (location.y - heights[0]) - randomHeightRange(heightRange)) {
					particle.getLocation().y -= particle.getSpeed();
				} else resetParticle(particle);
			}
			if (particles.indexOf(particle) < particles.size() / 2 && particles.indexOf(particle) >= particles.size() / 4) {
				particle.setColor(colors[1]);
				if (particle.getLocation().y >= (location.y - heights[1]) - randomHeightRange(heightRange)) {
					particle.getLocation().y -= particle.getSpeed();
				} else resetParticle(particle);
			}
			if (particles.indexOf(particle) < particles.size() / 4 * 3 && particles.indexOf(particle) >= particles.size() / 2) {
				particle.setColor(colors[2]);
				if (particle.getLocation().y >= (location.y - heights[2]) - randomHeightRange(heightRange)) {
					particle.getLocation().y -= particle.getSpeed();
				} else resetParticle(particle);
			}
			if (particles.indexOf(particle) < particles.size() && particles.indexOf(particle) >= particles.size() / 4 * 3) {
				particle.setColor(colors[3]);
				if (particle.getLocation().y >= (location.y - heights[3]) - randomHeightRange(heightRange)) {
					particle.getLocation().y -= particle.getSpeed();
				} else resetParticle(particle);
			}
		}
	}

	public void render(Graphics g) {
		for (Rectangle particle : particles) {
			g.setColor(particle.getColor());
			g.fillRect(particle.getLocation().x, particle.getLocation().y, particle.getSize().width, particle.getSize().height);
		}
	}

	public void setColors(Color c1, Color c2, Color c3, Color c4) {
		colors[0] = c1;
		colors[1] = c2;
		colors[2] = c3;
		colors[3] = c4;
	}

	public int randomHeightRange(int range) {
		return random.nextInt(range * 2) ;
	}

	public void resetParticle(Rectangle particle) {
		particle.getLocation().y = location.y;
		particle.getLocation().x = random.nextInt(size.width) + location.x;
		particle.setSpeed(random.nextInt(speedRange) + speedBase);
	}

}
