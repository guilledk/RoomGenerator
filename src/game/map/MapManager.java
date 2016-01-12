package game.map;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

import game.Engine.DrawableObject;
import game.map.Room.RoomType;

public class MapManager implements DrawableObject{
	
	public static final int ROOM_WIDTH = 32, ROOM_HEIGHT = 16;
	
	public static Random ng = new Random();
	private Room[][] roomGrid;
	private int sizeX, sizeY;
	private int doorChance = 20;
	private int treasureChance = 5;
	private int roomCount;
	
	public MapManager(int sizeX, int sizeY, String seed){
		
		if(seed != null){
			
			ng.setSeed(seed.hashCode());
			
		} else {
			
			ng.setSeed(ng.nextInt(100001 - 0) + 0);
			
		}
		
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		
		GenerateMap();
		
	}
	
	public void GenerateMap(){
		
		roomCount = 0;
		
		roomGrid = new Room[sizeX][sizeY];
		List<Vector2f> todoRooms = new ArrayList<Vector2f>();
		
		int startX = (int)Math.ceil(this.sizeX / 2f) - 1;
		int startY = (int)Math.ceil(this.sizeY / 2f) - 1;
		
		roomGrid[startX][startY] = new Room(true,true,true,true,startX * (ROOM_WIDTH + 1), startY * (ROOM_HEIGHT + 1),RoomType.START);
		
		for(int door = 0; door < 4; door++){
			
			switch(door){
			
				case 0 : { //TOP
					
					GenRoom(startX,startY - 1,false,todoRooms);
					break;
					
				}
				case 1 : { //RIGHT
					
					GenRoom(startX + 1,startY,false,todoRooms);
					break;
					
				}
				case 2 : { //BOTTOM
					
					GenRoom(startX,startY + 1,false,todoRooms);
					break;
					
				}
				case 3 : { //LEFT
					
					GenRoom(startX - 1,startY,false,todoRooms);
					break;
					
				}
			
			}
			
		}
		
		for(int i = 0; i < todoRooms.size(); i++){
			
			GenRoom((int)todoRooms.get(i).x,(int)todoRooms.get(i).y,false,todoRooms);
			
		}
		
		if(roomCount < 7){
			
			ng.setSeed(ng.nextInt(100001 - 0) + 0);
			GenerateMap();
	
			
		}
			
		
	}
	
	private void GenRoom(int x, int y, boolean deadEnd, List<Vector2f> todoRooms){
		roomCount++;
		boolean top,right,bottom,left;
		boolean end = deadEnd;
		
		if(x <= 2 || x >= this.sizeX - 2 || y <= 2 || y >= this.sizeY - 2){
			
			end = true;
			
		}
		
		if(!end){
		
			top = getRandomRange(1,100) >= doorChance ? false : true;
			right = getRandomRange(1,100) >= doorChance ? false : true;
			bottom = getRandomRange(1,100) >= doorChance ? false : true;
			left = getRandomRange(1,100) >= doorChance ? false : true;
			
			if(roomGrid[x][y - 1] != null && roomGrid[x][y - 1].isBottom()){
				
				top = true;
				
			}
			if(roomGrid[x + 1][y] != null && roomGrid[x + 1][y].isLeft()){
				
				right = true;
				
			}
			if(roomGrid[x][y + 1] != null && roomGrid[x][y + 1].isTop()){
				
				bottom = true;
				
			}
			if(roomGrid[x - 1][y] != null && roomGrid[x - 1][y].isRight()){
				
				left = true;
				
			}
			
			//IF OTHER ROOM DONT HAVE A DOOR
			
			if(top && roomGrid[x][y - 1] != null && !roomGrid[x][y - 1].isBottom()){
				
				top = false;
				
			}
			if(right && roomGrid[x + 1][y] != null && !roomGrid[x + 1][y].isLeft()){
				
				right = false;
				
			}
			if(bottom && roomGrid[x][y + 1] != null && !roomGrid[x][y + 1].isTop()){
				
				bottom = false;
				
			}
			if(left && roomGrid[x - 1][y] != null && !roomGrid[x - 1][y].isRight()){
				
				left = false;
				
			}
			
			if(roomGrid[x][y - 1] == null && top){
				
				todoRooms.add(new Vector2f(x,y - 1));
				
			}
			if(roomGrid[x + 1][y] == null && right){
				
				todoRooms.add(new Vector2f(x + 1,y));
				
			}
			if(roomGrid[x][y + 1] == null && bottom){
				
				todoRooms.add(new Vector2f(x,y + 1));
				
			}
			if(roomGrid[x - 1][y] == null && left){
				
				todoRooms.add(new Vector2f(x - 1,y));
				
			}
			
		} else {
			
			top = false;
			right = false;
			bottom = false;
			left = false;
			if(roomGrid[x][y - 1] != null && roomGrid[x][y - 1].isBottom()){
				
				top = true;
				
			}
			if(roomGrid[x + 1][y] != null && roomGrid[x + 1][y].isLeft()){
				
				right = true;
				
			}
			if(roomGrid[x][y + 1] != null && roomGrid[x][y + 1].isTop()){
				
				bottom = true;
				
			}
			if(roomGrid[x - 1][y] != null && roomGrid[x - 1][y].isRight()){
				
				left = true;
				
			}
			
		}
		
		RoomType type = getRandomRange(1,100) >= treasureChance ? RoomType.NORMAL : RoomType.TREASURE;
		
		roomGrid[x][y] = new Room(top,right,bottom,left,x * (ROOM_WIDTH + 1), y * (ROOM_HEIGHT + 1),type);
		
	}
	
	public static int getRandomRange(int min, int max){
		
		max++;
		return ng.nextInt(max - min) + min;
		
	}

	@Override
	public void Draw(Graphics g) {
		
		for(int x = 0; x < sizeX; x++){
			
			for(int y = 0; y < sizeX; y++){
				
				if(roomGrid[x][y] != null)
					roomGrid[x][y].Draw(g);
				
			}
			
		}
		
	}

}
