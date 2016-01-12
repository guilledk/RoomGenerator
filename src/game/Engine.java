package game;

import java.util.Random;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import game.map.MapManager;

public class Engine extends BasicGame{
	
	public interface DrawableObject{
		
		public void Draw(Graphics g);
		
	}
	
	public static final int WIDTH = 352, HEIGHT = 176;
	public MapManager mm;
	
	public static void main(String[] args){
		
		try
        {
            AppGameContainer app = new AppGameContainer(new Engine("Room gen test v0.1"));
            app.setDisplayMode(WIDTH, HEIGHT, false);
            app.start();
        }
        catch (SlickException e)
        {
            e.printStackTrace();
        }
		
	}

	public Engine(String title) {
		super(title);
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		
		g.setBackground(Color.white);
		mm.Draw(g);
		g.setColor(Color.black);
		//g.drawString("[R] - To regenerate map", 200, 450);
		
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		
		mm = new MapManager(11, 11, "seed test please ignore");
		
	}

	@Override
	public void update(GameContainer gc, int dt) throws SlickException {
		
		if(gc.getInput().isKeyPressed(Input.KEY_R)){
			
			Random temp = new Random();
			MapManager.ng.setSeed(temp.nextInt(100001 - 0) + 0);
			mm.GenerateMap();
			
		}
		
	}

}
