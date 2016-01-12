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
	private int minRoomCount = 7;
	
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
		
		int roomCount = 1;
		roomGrid = new Room[sizeX][sizeY];
		List<Vector2f> todoRooms = new ArrayList<Vector2f>();
		
		int startX = (int)Math.ceil(this.sizeX / 2f) - 1;
		int startY = (int)Math.ceil(this.sizeY / 2f) - 1;
		
		GenRoom(startX,startY,false,todoRooms,true);
		for(int i = 0; i < todoRooms.size(); i++){
			
			GenRoom((int)todoRooms.get(i).x,(int)todoRooms.get(i).y,false,todoRooms,false);
			roomCount++;
			
		}
		
		if(roomCount < minRoomCount){
			
			ng.setSeed(ng.nextInt(100001 - 10) + 10);
			GenerateMap();
			return;
	
			
		}
		
	}
	
	private void GenRoom(int x, int y, boolean deadEnd, List<Vector2f> todoRooms, boolean startingRoom){
		
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
		
		if(startingRoom)
			type = RoomType.START;
		
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
