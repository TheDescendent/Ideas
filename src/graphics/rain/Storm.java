package graphics.rain;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import essentials.tools.helpers.dimensions.Aspect;
import essentials.tools.helpers.shapes.Rectangle;

public class Storm {

	private Aspect size;
	private ArrayList<Rectangle> drops;

	private int level, speedRange = 3, angle;

	private static Random random = new Random();

	private void init() {
		size = new Aspect();
		drops = new ArrayList<Rectangle>();
	}

	public Storm(Aspect size, int maxDrops, int level, int angle) {
		init();
		this.angle = angle;
		this.size = size;
		this.level = level;
		for (int i = 0; i < maxDrops; i++) {
			drops.add(new Rectangle());
			drops.get(i).getSize().width = drops.get(i).getSize().height = 2;
			drops.get(i).setSpeed(random.nextInt(speedRange) + level);
			drops.get(i).getLocation().y = -random.nextInt(10000);
			drops.get(i).setColor(Color.blue);
		}

	}

	public void update() {
		for (Rectangle drop : drops) {
			if (drop.getLocation().x <= size.width && drop.getLocation().y <= size.height) {
				drop.getLocation().x += 0 + angle;
				drop.getLocation().y += drop.getSpeed();
			} else if (drop.getLocation().y > size.height) {
				drop.getLocation().x = random.nextInt(size.width);
				if (drop.getLocation().x == 0) drop.getLocation().y = random.nextInt(size.height);
				else drop.getLocation().y = 0;
			} else if (drop.getLocation().x > size.width) {
				drop.getLocation().x = 0;
			}

		}
	}

	public void render(Graphics g) {
		for (Rectangle drop : drops) {
			g.setColor(drop.getColor());
			if (drop.getLocation().x <= size.width && drop.getLocation().y <= size.height) g.fillRect(drop.getLocation().x, drop.getLocation().y,
					drop.getSize().width, drop.getSize().height);
		}

	}

	public void resetDrop(Rectangle drop) {
		drop.setSpeed(random.nextInt(speedRange) + level);

	}
}
