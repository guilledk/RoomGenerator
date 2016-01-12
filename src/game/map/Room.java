package game.map;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import game.Engine.DrawableObject;

public class Room implements DrawableObject{
	
	public enum RoomType {
		
		NORMAL,START,TREASURE
		
	}
	
	private boolean top,right,bottom,left;
	public float worldX, worldY;
	private RoomType type;
	
	public Room(boolean top,boolean right,boolean bottom,boolean left,float worldX, float worldY, RoomType type){
		
		this.top = top;
		this.right = right;
		this.bottom = bottom;
		this.left = left;
		
		this.worldX = worldX;
		this.worldY = worldY;
		
		this.type = type;
		
	}
	
	@Override
	public void Draw(Graphics g){
		
		if(this.type == RoomType.NORMAL)
			g.setColor(Color.black);
		if(this.type == RoomType.START)
			g.setColor(Color.blue);
		if(this.type == RoomType.TREASURE)
			g.setColor(Color.yellow);
		g.fillRect(worldX, worldY, MapManager.ROOM_WIDTH, MapManager.ROOM_HEIGHT);
		g.setColor(Color.darkGray);
		g.setLineWidth(2);
		g.drawRect(worldX, worldY, MapManager.ROOM_WIDTH, MapManager.ROOM_HEIGHT);
		g.setColor(Color.lightGray);
		if(top){
			
			g.fillRect((worldX + MapManager.ROOM_WIDTH / 2) - 2, worldY, 4, 4);
			
		}
		if(right){
			
			g.fillRect((worldX + MapManager.ROOM_WIDTH) - 4, (worldY + MapManager.ROOM_HEIGHT / 2) - 2, 4, 4);
			
		}
		if(bottom){
			
			g.fillRect((worldX + MapManager.ROOM_WIDTH / 2) - 2, worldY + MapManager.ROOM_HEIGHT - 4, 4, 4);
			
		}
		if(left){
			
			g.fillRect(worldX, (worldY + MapManager.ROOM_HEIGHT / 2) - 2, 4, 4);
			
		}
		
	}

	public boolean isTop() {
		return top;
	}

	public void setTop(boolean top) {
		this.top = top;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public boolean isBottom() {
		return bottom;
	}

	public void setBottom(boolean bottom) {
		this.bottom = bottom;
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	
	
}
