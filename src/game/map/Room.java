package game.map;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import game.Engine.DrawableObject;

public class Room implements DrawableObject{
	
	private boolean top,right,bottom,left;
	public float worldX, worldY;
	private RoomType type;
	private boolean partOfBigRoom = false;
	
	public Room(boolean top,boolean right,boolean bottom,boolean left,float worldX, float worldY, RoomType type){
		
		this.top = top;
		this.right = right;
		this.bottom = bottom;
		this.left = left;
		
		this.worldX = worldX;
		this.worldY = worldY;
		
		this.type = type;
		
	}
	
	@SuppressWarnings("incomplete-switch")
	@Override
	public void Draw(Graphics g){
		
		if(this.type == RoomType.NORMAL)
			g.setColor(new Color(32,32,32));
		if(this.type == RoomType.START)
			g.setColor(new Color(51,153,255));
		if(this.type == RoomType.TREASURE)
			g.setColor(new Color(204,204,0));
		if(this.type == RoomType.BIG_ROOM_0 || this.type == RoomType.BIG_ROOM_1 || this.type == RoomType.BIG_ROOM_2 || this.type == RoomType.BIG_ROOM_3)
			g.setColor(new Color(204,204,203));
		
		if(!partOfBigRoom){
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
		} else {
			
			switch(this.type){
			
				case BIG_ROOM_0 : {
					
					g.fillRect(worldX, worldY, MapManager.ROOM_WIDTH + 1, MapManager.ROOM_HEIGHT + 1);
					g.setColor(Color.darkGray);
					g.setLineWidth(2);
					g.drawLine(worldX, worldY, worldX + MapManager.ROOM_WIDTH, worldY);
					g.drawLine(worldX, worldY, worldX, worldY + MapManager.ROOM_HEIGHT);
					g.setColor(Color.lightGray);
					if(left){
						
						g.fillRect(worldX, (worldY + MapManager.ROOM_HEIGHT / 2) - 2, 4, 4);
						
					}
					if(top){
						
						g.fillRect((worldX + MapManager.ROOM_WIDTH / 2) - 2, worldY, 4, 4);
						
					}
					
					break;
					
				}
				case BIG_ROOM_1 : {
					
					g.fillRect(worldX, worldY, MapManager.ROOM_WIDTH + 1, MapManager.ROOM_HEIGHT + 1);
					g.setColor(Color.darkGray);
					g.setLineWidth(2);
					g.drawLine(worldX, worldY, worldX + MapManager.ROOM_WIDTH, worldY);
					g.drawLine(worldX + MapManager.ROOM_WIDTH, worldY, worldX + MapManager.ROOM_WIDTH, worldY + MapManager.ROOM_HEIGHT);
					g.setColor(Color.lightGray);
					if(right){
						
						g.fillRect((worldX + MapManager.ROOM_WIDTH) - 4, (worldY + MapManager.ROOM_HEIGHT / 2) - 2, 4, 4);
						
					}
					if(top){
						
						g.fillRect((worldX + MapManager.ROOM_WIDTH / 2) - 2, worldY, 4, 4);
						
					}
					
					break;
					
				}
				case BIG_ROOM_2 : {
					
					g.fillRect(worldX, worldY, MapManager.ROOM_WIDTH + 1, MapManager.ROOM_HEIGHT + 1);
					g.setColor(Color.darkGray);
					g.setLineWidth(2);
					g.drawLine(worldX, worldY + MapManager.ROOM_HEIGHT, worldX + MapManager.ROOM_WIDTH, worldY + MapManager.ROOM_HEIGHT);
					g.drawLine(worldX + MapManager.ROOM_WIDTH, worldY + MapManager.ROOM_HEIGHT, worldX + MapManager.ROOM_WIDTH, worldY);
					g.setColor(Color.lightGray);
					if(right){
						
						g.fillRect((worldX + MapManager.ROOM_WIDTH) - 4, (worldY + MapManager.ROOM_HEIGHT / 2) - 2, 4, 4);
						
					}
					if(bottom){
						
						g.fillRect((worldX + MapManager.ROOM_WIDTH / 2) - 2, worldY + MapManager.ROOM_HEIGHT - 4, 4, 4);
						
					}
					
					break;
					
				}
				case BIG_ROOM_3 : {
					
					g.fillRect(worldX, worldY, MapManager.ROOM_WIDTH + 1, MapManager.ROOM_HEIGHT + 1);
					g.setColor(Color.darkGray);
					g.setLineWidth(2);
					g.drawLine(worldX, worldY, worldX, worldY + MapManager.ROOM_HEIGHT);
					g.drawLine(worldX, worldY + MapManager.ROOM_HEIGHT, worldX+ MapManager.ROOM_WIDTH, worldY + MapManager.ROOM_HEIGHT);
					g.setColor(Color.lightGray);
					if(left){
						
						g.fillRect(worldX, (worldY + MapManager.ROOM_HEIGHT / 2) - 2, 4, 4);
						
					}
					if(bottom){
						
						g.fillRect((worldX + MapManager.ROOM_WIDTH / 2) - 2, worldY + MapManager.ROOM_HEIGHT - 4, 4, 4);
						
					}
					
					break;
					
				}
			
			}
			
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

	public RoomType getType() {
		return type;
	}

	public void setType(RoomType type) {
		this.type = type;
	}

	public boolean isPartOfBigRoom() {
		return partOfBigRoom;
	}

	public void setPartOfBigRoom(boolean partOfBigRoom) {
		this.partOfBigRoom = partOfBigRoom;
	}
	
}
