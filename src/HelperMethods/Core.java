package HelperMethods;

import java.util.Random;

import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.input.mouse.MouseEvent;
import org.osbot.rs07.script.Script;

public class Core {
	
	    public long startTime = System.currentTimeMillis() / 1000;

	    public void waitTime(int delay) {
	        int difference = delay / (this.rand(90, 110));
	        try {
	            Thread.sleep(this.rand(delay - difference, delay + difference)); //1000 milliseconds is one second.
	        } catch (InterruptedException ex) {
	            Thread.currentThread().interrupt();
	        }
	    }

	    public void moveClickInBox(Script script, boolean rightClick, int xMin,
	            int xMax, int yMin, int yMax) {
	        int xCoord = this.rand(xMin, xMax);
	        int yCoord = this.rand(yMin, yMax);
	        script.mouse.move(xCoord, yCoord);
	        script.mouse.click(false);
	    }

	    public int rand(int min, int max) {
	        int n;
	        int mean = (min + max) / 2;
	        int std = (max - mean) / 3;
	        Random r = new Random();
	        do {
	            double val = r.nextGaussian() * std + mean;
	            n = (int) Math.round(val);
	        } while (n < min || n > max);
	        return n;
	    }

	    public int getMyTotalLevel(Script script) {
	        int total = 0;
	        for (Skill skill : Skill.values()) {
	            total += script.getSkills().getStatic(skill);
	        }
	        return total;
	    }

	    public int[] distributeSeconds(long seconds) {
	        int d[] = new int[4];
	        d[0] = (int) (seconds / 86400); // Days.
	        d[1] = (int) ((seconds % 86400) / 3600); // Hours.
	        d[2] = (int) (((seconds % 86400) % 3600) / 60); // Minutes.
	        d[3] = (int) (((seconds % 86400) % 3600) % 60); // Seconds.
	        return d;
	    }

}
