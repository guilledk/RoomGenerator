package game.map;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
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
	public int mapsGenerated = 0;
	public int mapsWithBigRooms = 0;
	public static long currentSeed;
	
	public MapManager(int sizeX, int sizeY, String seed){
		
		if(seed != null){
			
			currentSeed = seed.hashCode();
			ng.setSeed(seed.hashCode());
			
		} else {
			
			//currentSeed  =	ng.nextInt(100001 - 0) + 0;
			currentSeed = ng.nextInt(100001 - 0) + 0;
			ng.setSeed(currentSeed);
			
		}
		
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		
		GenerateMap();
		
	}
	public void GenerateMap(){
		
		
		while(true){
			int roomCount = 1;
			boolean bigRoomGenerated = false;
			roomGrid = new Room[sizeX][sizeY];
			List<Vector2f> todoRooms = new ArrayList<Vector2f>();
			
			int startX = (int)Math.ceil(this.sizeX / 2f) - 1;
			int startY = (int)Math.ceil(this.sizeY / 2f) - 1;
			
			GenRoom(startX,startY,todoRooms,true);
			
			Set<Vector2f> hs = new HashSet<Vector2f>(todoRooms);
			todoRooms.clear();
			todoRooms.addAll(hs);
			for(int i = 0; i < todoRooms.size(); i++){

				GenRoom((int)todoRooms.get(i).x,(int)todoRooms.get(i).y,todoRooms,false);
				roomCount++;
				
			}
			bigRoomGenerated = GenerateBigRooms();
			DeleteUnconectedRooms();
			if(roomCount >= minRoomCount + 2){
				mapsGenerated++;
				if(bigRoomGenerated)
					mapsWithBigRooms++;
				System.out.println(currentSeed + ", " + bigRoomGenerated + " [" + mapsGenerated + "," + mapsWithBigRooms + "] Chance = " + ((mapsWithBigRooms * 100) / (float)mapsGenerated) + "%");
				break;
			}
			
		}
		
	}
	
	private boolean GenerateBigRooms() {

		boolean result = false;
		
		for(int x = 0; x < sizeX; x++){
			
			for(int y = 0; y < sizeY; y++){
				
				if(x < sizeX - 1 && y < sizeY - 1){
					if(roomGrid[x][y] != null && roomGrid[x + 1][y] != null && roomGrid[x][y + 1] != null && roomGrid[x + 1][y + 1] != null){
						
						boolean leftTop = roomGrid[x][y].isRight() && roomGrid[x][y].isBottom() && roomGrid[x][y].getType() == RoomType.NORMAL;
						boolean rightTop = roomGrid[x + 1][y].isLeft() && roomGrid[x + 1][y].isBottom() && roomGrid[x + 1][y].getType() == RoomType.NORMAL;
						boolean leftBottom = roomGrid[x][y + 1].isRight() && roomGrid[x][y + 1].isTop() && roomGrid[x + 1][y + 1].getType() == RoomType.NORMAL;
						boolean rightBottom = roomGrid[x + 1][y + 1].isLeft() && roomGrid[x + 1][y + 1].isTop() && roomGrid[x][y + 1].getType() == RoomType.NORMAL;
						
						if(leftTop && rightTop && leftBottom && rightBottom){
							
							roomGrid[x][y].setType(RoomType.BIG_ROOM_0);
							roomGrid[x + 1][y].setType(RoomType.BIG_ROOM_1);
							roomGrid[x + 1][y + 1].setType(RoomType.BIG_ROOM_2);
							roomGrid[x][y + 1].setType(RoomType.BIG_ROOM_3);
							
							roomGrid[x][y].setPartOfBigRoom(true);
							roomGrid[x + 1][y].setPartOfBigRoom(true);
							roomGrid[x + 1][y + 1].setPartOfBigRoom(true);
							roomGrid[x][y + 1].setPartOfBigRoom(true);
							
							result = true;
							
						}
						
					}
				}
				
			}
			
		}
		
		return result;
		
	}
	private void GenRoom(int x, int y, List<Vector2f> todoRooms, boolean startingRoom){
		
		boolean top,right,bottom,left;
		
		if(x <= 2 || x >= this.sizeX - 2 || y <= 2 || y >= this.sizeY - 2){
			
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
			
		} else {
		
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
		
		}
		
		RoomType type = getRandomRange(1,100) >= treasureChance ? RoomType.NORMAL : RoomType.TREASURE;
		
		if(startingRoom)
			type = RoomType.START;
		roomGrid[x][y] = new Room(top,right,bottom,left,x * (ROOM_WIDTH + 1), y * (ROOM_HEIGHT + 1),type);
		
	}
	
	public void DeleteUnconectedRooms(){
		
		for(int x = 0; x < sizeX; x++){
			
			for(int y = 0; y < sizeY; y++){
				
				if(roomGrid[x][y] != null && !roomGrid[x][y].isTop() && !roomGrid[x][y].isBottom() && !roomGrid[x][y].isRight() && !roomGrid[x][y].isLeft())
					roomGrid[x][y] = null;
				
			}
			
		}
		
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
