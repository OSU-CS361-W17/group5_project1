package edu.oregonstate.cs361.battleship;

import com.google.gson.Gson;
import spark.Request;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.staticFiles;
import static sun.org.mozilla.javascript.internal.regexp.NativeRegExp.TEST;

public class Main {





public static void main(String[] args) {
	//This will allow us to server the static pages such as index.html, app.js, etc.
	staticFiles.location("/public");

	//This will listen to GET requests to /model and return a clean new model
	get("/model", (req, res) -> newModel());
	//This will listen to POST requests and expects to receive a game model, as well as location to fire to
	post("/fire/:row/:col", (req, res) -> fireAt(req));
	//This will listen to POST requests and expects to receive a game model, as well as location to place the ship
	post("/placeShip/:id/:row/:col/:orientation", (req, res) -> placeShip(req));
}

//This function should return a new model
private static String newModel() {
	Gson gson = new Gson();
	BattleshipModel temp = new BattleshipModel();
	String jsonString = gson.toJson(temp);
	return jsonString;
}

//This function should accept an HTTP request and deseralize it into an actual Java object.
private static BattleshipModel getModelFromReq(Request req){
	Gson gson = new Gson();
	BattleshipModel temp = gson.fromJson(req.body());
	assert temp != null;
	return temp;
}

//This controller should take a json object from the front end, and place the ship as requested, and then return the object.
private static String placeShip(Request req) {
	String place = req.url();
	BattleshipModel data = getModelFromReq(req);
	if(isInBounds(place)){
		if(isNotOverlap(place,data)){
				//write to object
				//serialize
				//return
		}
	}
	//alert of failure
	return data;
}

private static boolean isInBounds(String req) {
	req.substring(1);
	String delims = "[/]";
	String[] tokens = req.split(delims);
	int x,y,length,direction;

	switch(tokens[1]){
		case "aircraftCarrier":
			x = tokens[2];
			y = tokens[3];
			length = 5;
			if(tokens[4] == "horizontal"){
				if(x < 7 && x+length < 11 && x+(length-1)<11 && x+(length-2)<11 && x+(length-3)<11 && x+(length -4)<11){
					return true;
				}
				else{
					return false;
				}
			}
			else if(tokens [4] == "vertical"){
				if(y < 7 && y+length < 11 && y+(length-1)<11 && y+(length-2)<11 && y+(length-3)<11 && y+(length -4)<11){
					return true;
				}
				else{
					return false;
				}
			}
		case "battleship":
			x = tokens[2];
			y = tokens[3];
			length = 4;
			if(tokens[4] == "horizontal"){
				if(x < 8 && x+length < 11 && x+(length-1)<11 && x+(length-2)<11 && x+(length-3)<11){
					return 1;
				}
				else{
					return 0;
				}
			}
			else if(tokens [4] == "vertical"){
				if(y < 8 && y+length < 11 && y+(length-1)<11 && y+(length-2)<11 && y+(length-3)<11){
					return true;
				}
				else{
					return false;
				}
			}
		case "cruiser":
			x = tokens[2];
			y = tokens[3];
			length = 3;
			if(tokens[4] == "horizontal"){
				if(x < 9 && x+length < 11 && x+(length-1)<11 && x+(length-2)<11){
					return true;
				}
				else{
					return false;
				}
			}
			else if(tokens [4] == "vertical"){
				if(y < 7 && y+length < 11 && y+(length-1)<11 && y+(length-2)<11){
					return true;
				}
				else{
					return false;
				}
			}
		case "destroyer":
			x = tokens[2];
			y = tokens[3];
			length = 2;
			if(tokens[4] == "horizontal"){
				if(x < 9 && x+length < 11 && x+(length-1)<11){
					return true;
				}
				else{
					return false;
				}
			}
			else if(tokens [4] == "vertical"){
				if(y < 9 && y+length < 11 && y+(length-1)<11){
					return true;
				}
				else{
					return false;
				}
			}
		case "submarine":
			x = tokens[2];
			y = tokens[3];
			length = 2;
			if(tokens[4] == "horizontal"){
				if(x < 9 && x+length < 11 && x+(length-1)<11){
					return true;
				}
				else{
					return false;
				}
			}
			else if(tokens [4] == "vertical"){
				if(y < 9 && y+length < 11 && y+(length-1)<11){
					return true;
				}
				else{
					return false;
				}
			}
	}

}

private static boolean isNotOverlap(String req, BattleshipModel data){
	req.substring(1);
	String delims = "[/]";
	String[] tokens = req.split(delims);
	//2,3 hold x,y
	switch(tokens[1]) {
		case "aircraftCarrier"
			if(tokens[4] == "horizontal") {
				int x1 = Integer.parseInt(tokens[2]);
				int x2 = Integer.parseInt(tokens[2])+1;
				int x3 = Integer.parseInt(tokens[2])+2;
				int x4 = Integer.parseInt(tokens[2])+3;
				int x5 = Integer.parseInt(tokens[2])+4;
				int y = Integer.parseInt(tokens[3]);
				if(data.battleship.start.Across != 0 && data.battleship.end.Across != 0){
					if(data.battleship.start.Across == data.battleship.end.Across) {
						int tx = data.battleship.start.Across;
						int ty1 = data.battleship.start.Down;
						int ty2 = data.battleship.start.Down + 1;
						int ty3 = data.battleship.start.Down + 2;
						int ty4 = data.battleship.end.Down;
						if (x1 == tx || x2 == tx || x3 == tx || x4 == tx || x5 == tx) {
							return false;
						}
						if (y == ty1 || y == ty2 || y == ty3 || y == ty4) {
							return false;
						}
						return true;
					}
					else{
						int tx1 = data.battleship.start.Across;
						int tx2 = data.battleship.start.Across+1;
						int tx3 = data.battleship.start.Across+2;
						int tx4 = data.battleship.end.Across;
						int ty = data.battleship.start.Down;
						if(x1 == tx1 || x1 == tx2 || x1 == tx3 || x1 == tx4 || x2 == tx1 || x2 == tx2 || x2 == tx3 || x2 == tx4 || x3 == tx1 || x3 == tx2 || x3 == tx3 || x3 == tx4 || x4 == tx1 || x4 == tx2 || x4 == tx3 || x4 == tx4 || x5 == tx1 || x5 == tx2 || x5 == tx3 || x5 == tx4){
							return false;
						}
						if(y == ty){
							return false;
						}
						return true;
					}
				}
				if(data.cruiser.start.Across != 0 && data.cruiser.end.Across != 0){
					if(data.cruiser.start.Across == data.cruiser.end.Across) {
						int tx = data.cruiser.start.Across;
						int ty1 = data.cruiser.start.Down;
						int ty2 = data.cruiser.start.Down + 1;
						int ty3 = data.cruiser.end.Down;
						if (x1 == tx || x2 == tx || x3 == tx || x4 == tx || x5 == tx) {
							return false;
						}
						if (y == ty1 || y == ty2 || y == ty3) {
							return false;
						}
						return true;
					}
					else{
							int tx1 = data.cruiser.start.Across;
							int tx2 = data.cruiser.start.Across+1;
							int tx3 = data.cruiser.end.Across;
							int ty = data.cruiser.start.Down;
							if(x1 == tx1 || x1 == tx2 || x1 == tx3 || x2 == tx1 || x2 == tx2 || x2 == tx3 || x3 == tx1 || x3 == tx2 || x3 == tx3 || x4 == tx1 || x4 == tx2 || x4 == tx3|| x5 == tx1 || x5 == tx2 || x5 == tx3){
								return false;
							}
							if(y == ty){
								return false;
							}
							return true;
						}
				}
				if(data.destroyer.start.Across != 0 && data.destroyer.end.Across != 0) {
					if (data.destroyer.start.Across == data.destroyer.end.Across) {
						int tx = data.destroyer.start.Across;
						int ty1 = data.destroyer.start.Down;
						int ty2 = data.destroyer.end.Down;
						if (x1 == tx || x2 == tx || x3 == tx || x4 == tx || x5 == tx) {
							return false;
						}
						if (y == ty1 || y == ty2) {
							return false;
						}
						return true;
					}
					else{
							int tx1 = data.battleship.start.Across;
							int tx2 = data.battleship.end.Across;
							int ty = data.battleship.start.Down;
							if (x1 == tx1 || x1 == tx2 || x2 == tx1 || x2 == tx2 || x3 == tx1 || x3 == tx2 || x4 == tx1 || x4 == tx2 || x5 == tx1 || x5 == tx2) {
								return false;
							}
							if (y == ty) {
								return false;
							}
							return true;
						}
				}
				if (data.submarine.start.Across != 0 && data.submarine.end.Across != 0) {
					if (data.submarine.start.Across == data.submarine.end.Across) {
						int tx = data.submarine.start.Across;
						int ty1 = data.submarine.start.Down;
						int ty2 = data.submarine.end.Down;
						if (x1 == tx || x2 == tx || x3 == tx || x4 == tx || x5 == tx) {
							return false;
						}
						if (y == ty1 || y == ty2) {
							return false;
						}
						return true;
					}
				else{
							int tx1 = data.submarine.start.Across;
							int tx2 = data.submarine.end.Across;
							int ty = data.submarine.start.Down;
							if (x1 == tx1 || x1 == tx2 || x2 == tx1 || x2 == tx2 || x3 == tx1 || x3 == tx2 || x4 == tx1 || x4 == tx2 || x5 == tx1 || x5 == tx2) {
								return false;
							}
							if (y == ty) {
								return false;
							}
							return true;
					}
				}
			}
			else{
				int y1 = Integer.parseInt(tokens[3]);
				int y2 = Integer.parseInt(tokens[3])+1;
				int y3 = Integer.parseInt(tokens[3])+2;
				int y4 = Integer.parseInt(tokens[3])+3;
				int y5 = Integer.parseInt(tokens[3])+4;
				int x = Integer.parseInt(tokens[2]);
				if(data.battleship.start.Across != 0 && data.battleship.end.Across != 0){
					if(data.battleship.start.Across == data.battleship.end.Across) {
						int tx = data.battleship.start.Across;
						int ty1 = data.battleship.start.Down;
						int ty2 = data.battleship.start.Down + 1;
						int ty3 = data.battleship.start.Down + 2;
						int ty4 = data.battleship.end.Down;
						if (y1 == ty1 || y1 == ty2 || y1 == ty3 || y1 == ty4 || y2 == ty1 || y2 == ty2 || y2 == ty3 || y2 == ty4 || y3 == ty1 || y3 == ty2 || y3 == ty3 || y3 == ty4 || y4 == ty1 || y4 == ty2 || y4 == ty3 || y4 == ty4 || y5 == ty1 || y5 == ty2 || y5== ty3 || y5 == ty4) {
							return false;
						}
						if (x == tx) {
							return false;
						}
						return true;
					}
					else{
						int tx1 = data.battleship.start.Across;
						int tx2 = data.battleship.start.Across+1;
						int tx3 = data.battleship.start.Across+2;
						int tx4 = data.battleship.end.Across;
						int ty = data.battleship.start.Down;
						if(x == tx1 || x == tx2 || x == tx3 || x == tx4){
							return false;
						}
						if(y1 == ty || y2 == ty || y3 == ty || y4 == ty || y5 == ty){
							return false;
						}
						return true;
					}
				}
				if(data.cruiser.start.Across != 0 && data.cruiser.end.Across != 0){
					if(data.cruiser.start.Across == data.cruiser.end.Across) {
						int tx = data.cruiser.start.Across;
						int ty1 = data.cruiser.start.Down;
						int ty2 = data.cruiser.start.Down + 1;
						int ty3 = data.cruiser.end.Down;
						if (y1 == ty1 || y2 == ty1 || y3 == ty1 || y4 == ty1 || y5 == ty1 || y1 == ty2 || y2 == ty2 || y3 == ty2 || y4 == ty2 || y5 == ty2 || y1 == ty3 || y2 == ty3 || y3 == ty3 || y4 == ty3 || y5 == ty3) {
							return false;
						}
						if (x == tx) {
							return false;
						}
						return true;
					}
					else{
						int tx1 = data.cruiser.start.Across;
						int tx2 = data.cruiser.start.Across+1;
						int tx3 = data.cruiser.end.Across;
						int ty = data.cruiser.start.Down;
						if(x == tx1 || x == tx2 || x == tx3){
							return false;
						}
						if(y1 == ty || y2 == ty || y3 == ty || y4 == ty || y5 == ty){
							return false;
						}
						return true;
					}
				}
				if(data.destroyer.start.Across != 0 && data.destroyer.end.Across != 0) {
					if (data.destroyer.start.Across == data.destroyer.end.Across) {
						int tx = data.destroyer.start.Across;
						int ty1 = data.destroyer.start.Down;
						int ty2 = data.destroyer.end.Down;
						if (y1 == ty1 || y2 == ty1 || y3 == ty1 || y4 == ty1 || y5 == ty1|| y1 == ty2 || y2 == ty2 || y3 == ty2 || y4 == ty2 || y5 == ty2) {
							return false;
						}
						if (x == tx) {
							return false;
						}
						return true;
					}
					else{
						int tx1 = data.battleship.start.Across;
						int tx2 = data.battleship.end.Across;
						int ty = data.battleship.start.Down;
						if (x == tx1 || x == tx2) {
							return false;
						}
						if (y1 == ty || y2 == ty || y3 == ty || y4 == ty || y5 == ty) {
							return false;
						}
						return true;
					}
				}
				if (data.submarine.start.Across != 0 && data.submarine.end.Across != 0) {
					if (data.submarine.start.Across == data.submarine.end.Across) {
						int tx = data.submarine.start.Across;
						int ty1 = data.submarine.start.Down;
						int ty2 = data.submarine.end.Down;
						if (y1 == ty1 || y2 == ty1 || y3 == ty1 || y4 == ty1 || y5 == ty1|| y1 == ty2 || y2 == ty2 || y3 == ty2 || y4 == ty2 || y5 == ty2) {
							return false;
						}
						if (x == tx) {
							return false;
						}
						return true;
					}
					else{
						int tx1 = data.submarine.start.Across;
						int tx2 = data.submarine.end.Across;
						int ty = data.submarine.start.Down;
						if (x == tx1 || x == tx2) {
							return false;
						}
						if (y1 == ty || y2 == ty || y3 == ty || y4 == ty || y5 == ty) {
							return false;
						}
						return true;
					}
				}
			}
		case "battleship":
			if(tokens[4] == "horizontal") {
				int x1 = Integer.parseInt(tokens[2]);
				int x2 = Integer.parseInt(tokens[2])+1;
				int x3 = Integer.parseInt(tokens[2])+2;
				int x4 = Integer.parseInt(tokens[2])+3;
				int x5 = Integer.parseInt(tokens[2])+4;
				int y = Integer.parseInt(tokens[3]);
				if(data.battleship.start.Across != 0 && data.battleship.end.Across != 0){
					if(data.battleship.start.Across == data.battleship.end.Across) {
						int tx = data.battleship.start.Across;
						int ty1 = data.battleship.start.Down;
						int ty2 = data.battleship.start.Down + 1;
						int ty3 = data.battleship.start.Down + 2;
						int ty4 = data.battleship.end.Down;
						if (x1 == tx || x2 == tx || x3 == tx || x4 == tx || x5 == tx) {
							return false;
						}
						if (y == ty1 || y == ty2 || y == ty3 || y == ty4) {
							return false;
						}
						return true;
					}
					else{
						int tx1 = data.battleship.start.Across;
						int tx2 = data.battleship.start.Across+1;
						int tx3 = data.battleship.start.Across+2;
						int tx4 = data.battleship.end.Across;
						int ty = data.battleship.start.Down;
						if(x1 == tx1 || x1 == tx2 || x1 == tx3 || x1 == tx4 || x2 == tx1 || x2 == tx2 || x2 == tx3 || x2 == tx4 || x3 == tx1 || x3 == tx2 || x3 == tx3 || x3 == tx4 || x4 == tx1 || x4 == tx2 || x4 == tx3 || x4 == tx4 || x5 == tx1 || x5 == tx2 || x5 == tx3 || x5 == tx4){
							return false;
						}
						if(y == ty){
							return false;
						}
						return true;
					}
				}
				if(data.cruiser.start.Across != 0 && data.cruiser.end.Across != 0){
					if(data.cruiser.start.Across == data.cruiser.end.Across) {
						int tx = data.cruiser.start.Across;
						int ty1 = data.cruiser.start.Down;
						int ty2 = data.cruiser.start.Down + 1;
						int ty3 = data.cruiser.end.Down;
						if (x1 == tx || x2 == tx || x3 == tx || x4 == tx || x5 == tx) {
							return false;
						}
						if (y == ty1 || y == ty2 || y == ty3) {
							return false;
						}
						return true;
					}
					else{
						int tx1 = data.cruiser.start.Across;
						int tx2 = data.cruiser.start.Across+1;
						int tx3 = data.cruiser.end.Across;
						int ty = data.cruiser.start.Down;
						if(x1 == tx1 || x1 == tx2 || x1 == tx3 || x2 == tx1 || x2 == tx2 || x2 == tx3 || x3 == tx1 || x3 == tx2 || x3 == tx3 || x4 == tx1 || x4 == tx2 || x4 == tx3|| x5 == tx1 || x5 == tx2 || x5 == tx3){
							return false;
						}
						if(y == ty){
							return false;
						}
						return true;
					}
				}
				if(data.destroyer.start.Across != 0 && data.destroyer.end.Across != 0) {
					if (data.destroyer.start.Across == data.destroyer.end.Across) {
						int tx = data.destroyer.start.Across;
						int ty1 = data.destroyer.start.Down;
						int ty2 = data.destroyer.end.Down;
						if (x1 == tx || x2 == tx || x3 == tx || x4 == tx || x5 == tx) {
							return false;
						}
						if (y == ty1 || y == ty2) {
							return false;
						}
						return true;
					}
					else{
						int tx1 = data.battleship.start.Across;
						int tx2 = data.battleship.end.Across;
						int ty = data.battleship.start.Down;
						if (x1 == tx1 || x1 == tx2 || x2 == tx1 || x2 == tx2 || x3 == tx1 || x3 == tx2 || x4 == tx1 || x4 == tx2 || x5 == tx1 || x5 == tx2) {
							return false;
						}
						if (y == ty) {
							return false;
						}
						return true;
					}
				}
				if (data.submarine.start.Across != 0 && data.submarine.end.Across != 0) {
					if (data.submarine.start.Across == data.submarine.end.Across) {
						int tx = data.submarine.start.Across;
						int ty1 = data.submarine.start.Down;
						int ty2 = data.submarine.end.Down;
						if (x1 == tx || x2 == tx || x3 == tx || x4 == tx || x5 == tx) {
							return false;
						}
						if (y == ty1 || y == ty2) {
							return false;
						}
						return true;
					}
					else{
						int tx1 = data.submarine.start.Across;
						int tx2 = data.submarine.end.Across;
						int ty = data.submarine.start.Down;
						if (x1 == tx1 || x1 == tx2 || x2 == tx1 || x2 == tx2 || x3 == tx1 || x3 == tx2 || x4 == tx1 || x4 == tx2 || x5 == tx1 || x5 == tx2) {
							return false;
						}
						if (y == ty) {
							return false;
						}
						return true;
					}
				}
			}
			else{
				int y1 = Integer.parseInt(tokens[3]);
				int y2 = Integer.parseInt(tokens[3])+1;
				int y3 = Integer.parseInt(tokens[3])+2;
				int y4 = Integer.parseInt(tokens[3])+3;
				int y5 = Integer.parseInt(tokens[3])+4;
				int x = Integer.parseInt(tokens[2]);
				if(data.battleship.start.Across != 0 && data.battleship.end.Across != 0){
					if(data.battleship.start.Across == data.battleship.end.Across) {
						int tx = data.battleship.start.Across;
						int ty1 = data.battleship.start.Down;
						int ty2 = data.battleship.start.Down + 1;
						int ty3 = data.battleship.start.Down + 2;
						int ty4 = data.battleship.end.Down;
						if (y1 == ty1 || y1 == ty2 || y1 == ty3 || y1 == ty4 || y2 == ty1 || y2 == ty2 || y2 == ty3 || y2 == ty4 || y3 == ty1 || y3 == ty2 || y3 == ty3 || y3 == ty4 || y4 == ty1 || y4 == ty2 || y4 == ty3 || y4 == ty4 || y5 == ty1 || y5 == ty2 || y5== ty3 || y5 == ty4) {
							return false;
						}
						if (x == tx) {
							return false;
						}
						return true;
					}
					else{
						int tx1 = data.battleship.start.Across;
						int tx2 = data.battleship.start.Across+1;
						int tx3 = data.battleship.start.Across+2;
						int tx4 = data.battleship.end.Across;
						int ty = data.battleship.start.Down;
						if(x == tx1 || x == tx2 || x == tx3 || x == tx4){
							return false;
						}
						if(y1 == ty || y2 == ty || y3 == ty || y4 == ty || y5 == ty){
							return false;
						}
						return true;
					}
				}
				if(data.cruiser.start.Across != 0 && data.cruiser.end.Across != 0){
					if(data.cruiser.start.Across == data.cruiser.end.Across) {
						int tx = data.cruiser.start.Across;
						int ty1 = data.cruiser.start.Down;
						int ty2 = data.cruiser.start.Down + 1;
						int ty3 = data.cruiser.end.Down;
						if (y1 == ty1 || y2 == ty1 || y3 == ty1 || y4 == ty1 || y5 == ty1 || y1 == ty2 || y2 == ty2 || y3 == ty2 || y4 == ty2 || y5 == ty2 || y1 == ty3 || y2 == ty3 || y3 == ty3 || y4 == ty3 || y5 == ty3) {
							return false;
						}
						if (x == tx) {
							return false;
						}
						return true;
					}
					else{
						int tx1 = data.cruiser.start.Across;
						int tx2 = data.cruiser.start.Across+1;
						int tx3 = data.cruiser.end.Across;
						int ty = data.cruiser.start.Down;
						if(x == tx1 || x == tx2 || x == tx3){
							return false;
						}
						if(y1 == ty || y2 == ty || y3 == ty || y4 == ty || y5 == ty){
							return false;
						}
						return true;
					}
				}
				if(data.destroyer.start.Across != 0 && data.destroyer.end.Across != 0) {
					if (data.destroyer.start.Across == data.destroyer.end.Across) {
						int tx = data.destroyer.start.Across;
						int ty1 = data.destroyer.start.Down;
						int ty2 = data.destroyer.end.Down;
						if (y1 == ty1 || y2 == ty1 || y3 == ty1 || y4 == ty1 || y5 == ty1|| y1 == ty2 || y2 == ty2 || y3 == ty2 || y4 == ty2 || y5 == ty2) {
							return false;
						}
						if (x == tx) {
							return false;
						}
						return true;
					}
					else{
						int tx1 = data.battleship.start.Across;
						int tx2 = data.battleship.end.Across;
						int ty = data.battleship.start.Down;
						if (x == tx1 || x == tx2) {
							return false;
						}
						if (y1 == ty || y2 == ty || y3 == ty || y4 == ty || y5 == ty) {
							return false;
						}
						return true;
					}
				}
				if (data.submarine.start.Across != 0 && data.submarine.end.Across != 0) {
					if (data.submarine.start.Across == data.submarine.end.Across) {
						int tx = data.submarine.start.Across;
						int ty1 = data.submarine.start.Down;
						int ty2 = data.submarine.end.Down;
						if (y1 == ty1 || y2 == ty1 || y3 == ty1 || y4 == ty1 || y5 == ty1|| y1 == ty2 || y2 == ty2 || y3 == ty2 || y4 == ty2 || y5 == ty2) {
							return false;
						}
						if (x == tx) {
							return false;
						}
						return true;
					}
					else{
						int tx1 = data.submarine.start.Across;
						int tx2 = data.submarine.end.Across;
						int ty = data.submarine.start.Down;
						if (x == tx1 || x == tx2) {
							return false;
						}
						if (y1 == ty || y2 == ty || y3 == ty || y4 == ty || y5 == ty) {
							return false;
						}
						return true;
					}
				}
			}
		case "cruiser":
			if(tokens[4] == "horizontal") {
				int x1 = Integer.parseInt(tokens[2]);
				int x2 = Integer.parseInt(tokens[2])+1;
				int x3 = Integer.parseInt(tokens[2])+2;
				int x4 = Integer.parseInt(tokens[2])+3;
				int x5 = Integer.parseInt(tokens[2])+4;
				int y = Integer.parseInt(tokens[3]);
				if(data.battleship.start.Across != 0 && data.battleship.end.Across != 0){
					if(data.battleship.start.Across == data.battleship.end.Across) {
						int tx = data.battleship.start.Across;
						int ty1 = data.battleship.start.Down;
						int ty2 = data.battleship.start.Down + 1;
						int ty3 = data.battleship.start.Down + 2;
						int ty4 = data.battleship.end.Down;
						if (x1 == tx || x2 == tx || x3 == tx || x4 == tx || x5 == tx) {
							return false;
						}
						if (y == ty1 || y == ty2 || y == ty3 || y == ty4) {
							return false;
						}
						return true;
					}
					else{
						int tx1 = data.battleship.start.Across;
						int tx2 = data.battleship.start.Across+1;
						int tx3 = data.battleship.start.Across+2;
						int tx4 = data.battleship.end.Across;
						int ty = data.battleship.start.Down;
						if(x1 == tx1 || x1 == tx2 || x1 == tx3 || x1 == tx4 || x2 == tx1 || x2 == tx2 || x2 == tx3 || x2 == tx4 || x3 == tx1 || x3 == tx2 || x3 == tx3 || x3 == tx4 || x4 == tx1 || x4 == tx2 || x4 == tx3 || x4 == tx4 || x5 == tx1 || x5 == tx2 || x5 == tx3 || x5 == tx4){
							return false;
						}
						if(y == ty){
							return false;
						}
						return true;
					}
				}
				if(data.cruiser.start.Across != 0 && data.cruiser.end.Across != 0){
					if(data.cruiser.start.Across == data.cruiser.end.Across) {
						int tx = data.cruiser.start.Across;
						int ty1 = data.cruiser.start.Down;
						int ty2 = data.cruiser.start.Down + 1;
						int ty3 = data.cruiser.end.Down;
						if (x1 == tx || x2 == tx || x3 == tx || x4 == tx || x5 == tx) {
							return false;
						}
						if (y == ty1 || y == ty2 || y == ty3) {
							return false;
						}
						return true;
					}
					else{
						int tx1 = data.cruiser.start.Across;
						int tx2 = data.cruiser.start.Across+1;
						int tx3 = data.cruiser.end.Across;
						int ty = data.cruiser.start.Down;
						if(x1 == tx1 || x1 == tx2 || x1 == tx3 || x2 == tx1 || x2 == tx2 || x2 == tx3 || x3 == tx1 || x3 == tx2 || x3 == tx3 || x4 == tx1 || x4 == tx2 || x4 == tx3|| x5 == tx1 || x5 == tx2 || x5 == tx3){
							return false;
						}
						if(y == ty){
							return false;
						}
						return true;
					}
				}
				if(data.destroyer.start.Across != 0 && data.destroyer.end.Across != 0) {
					if (data.destroyer.start.Across == data.destroyer.end.Across) {
						int tx = data.destroyer.start.Across;
						int ty1 = data.destroyer.start.Down;
						int ty2 = data.destroyer.end.Down;
						if (x1 == tx || x2 == tx || x3 == tx || x4 == tx || x5 == tx) {
							return false;
						}
						if (y == ty1 || y == ty2) {
							return false;
						}
						return true;
					}
					else{
						int tx1 = data.battleship.start.Across;
						int tx2 = data.battleship.end.Across;
						int ty = data.battleship.start.Down;
						if (x1 == tx1 || x1 == tx2 || x2 == tx1 || x2 == tx2 || x3 == tx1 || x3 == tx2 || x4 == tx1 || x4 == tx2 || x5 == tx1 || x5 == tx2) {
							return false;
						}
						if (y == ty) {
							return false;
						}
						return true;
					}
				}
				if (data.submarine.start.Across != 0 && data.submarine.end.Across != 0) {
					if (data.submarine.start.Across == data.submarine.end.Across) {
						int tx = data.submarine.start.Across;
						int ty1 = data.submarine.start.Down;
						int ty2 = data.submarine.end.Down;
						if (x1 == tx || x2 == tx || x3 == tx || x4 == tx || x5 == tx) {
							return false;
						}
						if (y == ty1 || y == ty2) {
							return false;
						}
						return true;
					}
					else{
						int tx1 = data.submarine.start.Across;
						int tx2 = data.submarine.end.Across;
						int ty = data.submarine.start.Down;
						if (x1 == tx1 || x1 == tx2 || x2 == tx1 || x2 == tx2 || x3 == tx1 || x3 == tx2 || x4 == tx1 || x4 == tx2 || x5 == tx1 || x5 == tx2) {
							return false;
						}
						if (y == ty) {
							return false;
						}
						return true;
					}
				}
			}
			else{
				int y1 = Integer.parseInt(tokens[3]);
				int y2 = Integer.parseInt(tokens[3])+1;
				int y3 = Integer.parseInt(tokens[3])+2;
				int y4 = Integer.parseInt(tokens[3])+3;
				int y5 = Integer.parseInt(tokens[3])+4;
				int x = Integer.parseInt(tokens[2]);
				if(data.battleship.start.Across != 0 && data.battleship.end.Across != 0){
					if(data.battleship.start.Across == data.battleship.end.Across) {
						int tx = data.battleship.start.Across;
						int ty1 = data.battleship.start.Down;
						int ty2 = data.battleship.start.Down + 1;
						int ty3 = data.battleship.start.Down + 2;
						int ty4 = data.battleship.end.Down;
						if (y1 == ty1 || y1 == ty2 || y1 == ty3 || y1 == ty4 || y2 == ty1 || y2 == ty2 || y2 == ty3 || y2 == ty4 || y3 == ty1 || y3 == ty2 || y3 == ty3 || y3 == ty4 || y4 == ty1 || y4 == ty2 || y4 == ty3 || y4 == ty4 || y5 == ty1 || y5 == ty2 || y5== ty3 || y5 == ty4) {
							return false;
						}
						if (x == tx) {
							return false;
						}
						return true;
					}
					else{
						int tx1 = data.battleship.start.Across;
						int tx2 = data.battleship.start.Across+1;
						int tx3 = data.battleship.start.Across+2;
						int tx4 = data.battleship.end.Across;
						int ty = data.battleship.start.Down;
						if(x == tx1 || x == tx2 || x == tx3 || x == tx4){
							return false;
						}
						if(y1 == ty || y2 == ty || y3 == ty || y4 == ty || y5 == ty){
							return false;
						}
						return true;
					}
				}
				if(data.cruiser.start.Across != 0 && data.cruiser.end.Across != 0){
					if(data.cruiser.start.Across == data.cruiser.end.Across) {
						int tx = data.cruiser.start.Across;
						int ty1 = data.cruiser.start.Down;
						int ty2 = data.cruiser.start.Down + 1;
						int ty3 = data.cruiser.end.Down;
						if (y1 == ty1 || y2 == ty1 || y3 == ty1 || y4 == ty1 || y5 == ty1 || y1 == ty2 || y2 == ty2 || y3 == ty2 || y4 == ty2 || y5 == ty2 || y1 == ty3 || y2 == ty3 || y3 == ty3 || y4 == ty3 || y5 == ty3) {
							return false;
						}
						if (x == tx) {
							return false;
						}
						return true;
					}
					else{
						int tx1 = data.cruiser.start.Across;
						int tx2 = data.cruiser.start.Across+1;
						int tx3 = data.cruiser.end.Across;
						int ty = data.cruiser.start.Down;
						if(x == tx1 || x == tx2 || x == tx3){
							return false;
						}
						if(y1 == ty || y2 == ty || y3 == ty || y4 == ty || y5 == ty){
							return false;
						}
						return true;
					}
				}
				if(data.destroyer.start.Across != 0 && data.destroyer.end.Across != 0) {
					if (data.destroyer.start.Across == data.destroyer.end.Across) {
						int tx = data.destroyer.start.Across;
						int ty1 = data.destroyer.start.Down;
						int ty2 = data.destroyer.end.Down;
						if (y1 == ty1 || y2 == ty1 || y3 == ty1 || y4 == ty1 || y5 == ty1|| y1 == ty2 || y2 == ty2 || y3 == ty2 || y4 == ty2 || y5 == ty2) {
							return false;
						}
						if (x == tx) {
							return false;
						}
						return true;
					}
					else{
						int tx1 = data.battleship.start.Across;
						int tx2 = data.battleship.end.Across;
						int ty = data.battleship.start.Down;
						if (x == tx1 || x == tx2) {
							return false;
						}
						if (y1 == ty || y2 == ty || y3 == ty || y4 == ty || y5 == ty) {
							return false;
						}
						return true;
					}
				}
				if (data.submarine.start.Across != 0 && data.submarine.end.Across != 0) {
					if (data.submarine.start.Across == data.submarine.end.Across) {
						int tx = data.submarine.start.Across;
						int ty1 = data.submarine.start.Down;
						int ty2 = data.submarine.end.Down;
						if (y1 == ty1 || y2 == ty1 || y3 == ty1 || y4 == ty1 || y5 == ty1|| y1 == ty2 || y2 == ty2 || y3 == ty2 || y4 == ty2 || y5 == ty2) {
							return false;
						}
						if (x == tx) {
							return false;
						}
						return true;
					}
					else{
						int tx1 = data.submarine.start.Across;
						int tx2 = data.submarine.end.Across;
						int ty = data.submarine.start.Down;
						if (x == tx1 || x == tx2) {
							return false;
						}
						if (y1 == ty || y2 == ty || y3 == ty || y4 == ty || y5 == ty) {
							return false;
						}
						return true;
					}
				}
			}
		case "destroyer":
			if(tokens[4] == "horizontal") {
				int x1 = Integer.parseInt(tokens[2]);
				int x2 = Integer.parseInt(tokens[2])+1;
				int x3 = Integer.parseInt(tokens[2])+2;
				int x4 = Integer.parseInt(tokens[2])+3;
				int x5 = Integer.parseInt(tokens[2])+4;
				int y = Integer.parseInt(tokens[3]);
				if(data.battleship.start.Across != 0 && data.battleship.end.Across != 0){
					if(data.battleship.start.Across == data.battleship.end.Across) {
						int tx = data.battleship.start.Across;
						int ty1 = data.battleship.start.Down;
						int ty2 = data.battleship.start.Down + 1;
						int ty3 = data.battleship.start.Down + 2;
						int ty4 = data.battleship.end.Down;
						if (x1 == tx || x2 == tx || x3 == tx || x4 == tx || x5 == tx) {
							return false;
						}
						if (y == ty1 || y == ty2 || y == ty3 || y == ty4) {
							return false;
						}
						return true;
					}
					else{
						int tx1 = data.battleship.start.Across;
						int tx2 = data.battleship.start.Across+1;
						int tx3 = data.battleship.start.Across+2;
						int tx4 = data.battleship.end.Across;
						int ty = data.battleship.start.Down;
						if(x1 == tx1 || x1 == tx2 || x1 == tx3 || x1 == tx4 || x2 == tx1 || x2 == tx2 || x2 == tx3 || x2 == tx4 || x3 == tx1 || x3 == tx2 || x3 == tx3 || x3 == tx4 || x4 == tx1 || x4 == tx2 || x4 == tx3 || x4 == tx4 || x5 == tx1 || x5 == tx2 || x5 == tx3 || x5 == tx4){
							return false;
						}
						if(y == ty){
							return false;
						}
						return true;
					}
				}
				if(data.cruiser.start.Across != 0 && data.cruiser.end.Across != 0){
					if(data.cruiser.start.Across == data.cruiser.end.Across) {
						int tx = data.cruiser.start.Across;
						int ty1 = data.cruiser.start.Down;
						int ty2 = data.cruiser.start.Down + 1;
						int ty3 = data.cruiser.end.Down;
						if (x1 == tx || x2 == tx || x3 == tx || x4 == tx || x5 == tx) {
							return false;
						}
						if (y == ty1 || y == ty2 || y == ty3) {
							return false;
						}
						return true;
					}
					else{
						int tx1 = data.cruiser.start.Across;
						int tx2 = data.cruiser.start.Across+1;
						int tx3 = data.cruiser.end.Across;
						int ty = data.cruiser.start.Down;
						if(x1 == tx1 || x1 == tx2 || x1 == tx3 || x2 == tx1 || x2 == tx2 || x2 == tx3 || x3 == tx1 || x3 == tx2 || x3 == tx3 || x4 == tx1 || x4 == tx2 || x4 == tx3|| x5 == tx1 || x5 == tx2 || x5 == tx3){
							return false;
						}
						if(y == ty){
							return false;
						}
						return true;
					}
				}
				if(data.destroyer.start.Across != 0 && data.destroyer.end.Across != 0) {
					if (data.destroyer.start.Across == data.destroyer.end.Across) {
						int tx = data.destroyer.start.Across;
						int ty1 = data.destroyer.start.Down;
						int ty2 = data.destroyer.end.Down;
						if (x1 == tx || x2 == tx || x3 == tx || x4 == tx || x5 == tx) {
							return false;
						}
						if (y == ty1 || y == ty2) {
							return false;
						}
						return true;
					}
					else{
						int tx1 = data.battleship.start.Across;
						int tx2 = data.battleship.end.Across;
						int ty = data.battleship.start.Down;
						if (x1 == tx1 || x1 == tx2 || x2 == tx1 || x2 == tx2 || x3 == tx1 || x3 == tx2 || x4 == tx1 || x4 == tx2 || x5 == tx1 || x5 == tx2) {
							return false;
						}
						if (y == ty) {
							return false;
						}
						return true;
					}
				}
				if (data.submarine.start.Across != 0 && data.submarine.end.Across != 0) {
					if (data.submarine.start.Across == data.submarine.end.Across) {
						int tx = data.submarine.start.Across;
						int ty1 = data.submarine.start.Down;
						int ty2 = data.submarine.end.Down;
						if (x1 == tx || x2 == tx || x3 == tx || x4 == tx || x5 == tx) {
							return false;
						}
						if (y == ty1 || y == ty2) {
							return false;
						}
						return true;
					}
					else{
						int tx1 = data.submarine.start.Across;
						int tx2 = data.submarine.end.Across;
						int ty = data.submarine.start.Down;
						if (x1 == tx1 || x1 == tx2 || x2 == tx1 || x2 == tx2 || x3 == tx1 || x3 == tx2 || x4 == tx1 || x4 == tx2 || x5 == tx1 || x5 == tx2) {
							return false;
						}
						if (y == ty) {
							return false;
						}
						return true;
					}
				}
			}
			else{
				int y1 = Integer.parseInt(tokens[3]);
				int y2 = Integer.parseInt(tokens[3])+1;
				int y3 = Integer.parseInt(tokens[3])+2;
				int y4 = Integer.parseInt(tokens[3])+3;
				int y5 = Integer.parseInt(tokens[3])+4;
				int x = Integer.parseInt(tokens[2]);
				if(data.battleship.start.Across != 0 && data.battleship.end.Across != 0){
					if(data.battleship.start.Across == data.battleship.end.Across) {
						int tx = data.battleship.start.Across;
						int ty1 = data.battleship.start.Down;
						int ty2 = data.battleship.start.Down + 1;
						int ty3 = data.battleship.start.Down + 2;
						int ty4 = data.battleship.end.Down;
						if (y1 == ty1 || y1 == ty2 || y1 == ty3 || y1 == ty4 || y2 == ty1 || y2 == ty2 || y2 == ty3 || y2 == ty4 || y3 == ty1 || y3 == ty2 || y3 == ty3 || y3 == ty4 || y4 == ty1 || y4 == ty2 || y4 == ty3 || y4 == ty4 || y5 == ty1 || y5 == ty2 || y5== ty3 || y5 == ty4) {
							return false;
						}
						if (x == tx) {
							return false;
						}
						return true;
					}
					else{
						int tx1 = data.battleship.start.Across;
						int tx2 = data.battleship.start.Across+1;
						int tx3 = data.battleship.start.Across+2;
						int tx4 = data.battleship.end.Across;
						int ty = data.battleship.start.Down;
						if(x == tx1 || x == tx2 || x == tx3 || x == tx4){
							return false;
						}
						if(y1 == ty || y2 == ty || y3 == ty || y4 == ty || y5 == ty){
							return false;
						}
						return true;
					}
				}
				if(data.cruiser.start.Across != 0 && data.cruiser.end.Across != 0){
					if(data.cruiser.start.Across == data.cruiser.end.Across) {
						int tx = data.cruiser.start.Across;
						int ty1 = data.cruiser.start.Down;
						int ty2 = data.cruiser.start.Down + 1;
						int ty3 = data.cruiser.end.Down;
						if (y1 == ty1 || y2 == ty1 || y3 == ty1 || y4 == ty1 || y5 == ty1 || y1 == ty2 || y2 == ty2 || y3 == ty2 || y4 == ty2 || y5 == ty2 || y1 == ty3 || y2 == ty3 || y3 == ty3 || y4 == ty3 || y5 == ty3) {
							return false;
						}
						if (x == tx) {
							return false;
						}
						return true;
					}
					else{
						int tx1 = data.cruiser.start.Across;
						int tx2 = data.cruiser.start.Across+1;
						int tx3 = data.cruiser.end.Across;
						int ty = data.cruiser.start.Down;
						if(x == tx1 || x == tx2 || x == tx3){
							return false;
						}
						if(y1 == ty || y2 == ty || y3 == ty || y4 == ty || y5 == ty){
							return false;
						}
						return true;
					}
				}
				if(data.destroyer.start.Across != 0 && data.destroyer.end.Across != 0) {
					if (data.destroyer.start.Across == data.destroyer.end.Across) {
						int tx = data.destroyer.start.Across;
						int ty1 = data.destroyer.start.Down;
						int ty2 = data.destroyer.end.Down;
						if (y1 == ty1 || y2 == ty1 || y3 == ty1 || y4 == ty1 || y5 == ty1|| y1 == ty2 || y2 == ty2 || y3 == ty2 || y4 == ty2 || y5 == ty2) {
							return false;
						}
						if (x == tx) {
							return false;
						}
						return true;
					}
					else{
						int tx1 = data.battleship.start.Across;
						int tx2 = data.battleship.end.Across;
						int ty = data.battleship.start.Down;
						if (x == tx1 || x == tx2) {
							return false;
						}
						if (y1 == ty || y2 == ty || y3 == ty || y4 == ty || y5 == ty) {
							return false;
						}
						return true;
					}
				}
				if (data.submarine.start.Across != 0 && data.submarine.end.Across != 0) {
					if (data.submarine.start.Across == data.submarine.end.Across) {
						int tx = data.submarine.start.Across;
						int ty1 = data.submarine.start.Down;
						int ty2 = data.submarine.end.Down;
						if (y1 == ty1 || y2 == ty1 || y3 == ty1 || y4 == ty1 || y5 == ty1|| y1 == ty2 || y2 == ty2 || y3 == ty2 || y4 == ty2 || y5 == ty2) {
							return false;
						}
						if (x == tx) {
							return false;
						}
						return true;
					}
					else{
						int tx1 = data.submarine.start.Across;
						int tx2 = data.submarine.end.Across;
						int ty = data.submarine.start.Down;
						if (x == tx1 || x == tx2) {
							return false;
						}
						if (y1 == ty || y2 == ty || y3 == ty || y4 == ty || y5 == ty) {
							return false;
						}
						return true;
					}
				}
			}
		case "submarine":
			if(tokens[4] == "horizontal") {
				int x1 = Integer.parseInt(tokens[2]);
				int x2 = Integer.parseInt(tokens[2])+1;
				int y = Integer.parseInt(tokens[3]);
				if(data.aircraftCarrier.start.Across != 0 && data.aircraftCarrier.end.Across != 0){
					if(data.aircraftCarrier.start.Across == data.aircraftCarrier.end.Across) {
						int tx = data.aircraftCarrier.start.Across;
						int ty1 = data.aircraftCarrier.start.Down;
						int ty2 = data.aircraftCarrier.start.Down + 1;
						int ty3 = data.aircraftCarrier.start.Down + 2;
						int ty4 = data.aircraftCarrier.start.Down +3;
						int ty5 = data.aircraftCarrier.end.Down;
						if (x1 == tx || x2 == tx) {
							return false;
						}
						if (y == ty1 || y == ty2 || y == ty3 || y == ty4 ||y == ty5) {
							return false;
						}
						return true;
					}
					else{
						int tx1 = data.aircraftCarrier.start.Across;
						int tx2 = data.aircraftCarrier.start.Across+1;
						int tx3 = data.aircraftCarrier.start.Across+2;
						int tx4 = data.aircraftCarrier.start.Across+3;
						int tx5 = data.aircraftCarrier.end.Across;
						int ty = data.aircraftCarrier.start.Down;
						if(x1 == tx1 || x1 == tx2 || x1 == tx3 || x1 == tx4|| x1 == tx5 || x2 == tx1 || x2 == tx2 || x2 == tx3 || x2 == tx4 || x2 == tx5){
							return false;
						}
						if(y == ty){
							return false;
						}
						return true;
					}
				}
				if(data.battleship.start.Across != 0 && data.battleship.end.Across != 0){
					if(data.battleship.start.Across == data.battleship.end.Across) {
						int tx = data.battleship.start.Across;
						int ty1 = data.battleship.start.Down;
						int ty2 = data.battleship.start.Down + 1;
						int ty3 = data.battleship.start.Down + 2;
						int ty4 = data.battleship.end.Down;
						if (x1 == tx || x2 == tx) {
							return false;
						}
						if (y == ty1 || y == ty2 || y == ty3 || y == ty4) {
							return false;
						}
						return true;
					}
					else{
						int tx1 = data.battleship.start.Across;
						int tx2 = data.battleship.start.Across+1;
						int tx3 = data.battleship.start.Across+2;
						int tx4 = data.battleship.end.Across;
						int ty = data.battleship.start.Down;
						if(x1 == tx1 || x1 == tx2 || x1 == tx3 || x1 == tx4 || x2 == tx1 || x2 == tx2 || x2 == tx3 || x2 == tx4){
							return false;
						}
						if(y == ty){
							return false;
						}
						return true;
					}
				}
				if(data.cruiser.start.Across != 0 && data.cruiser.end.Across != 0){
					if(data.cruiser.start.Across == data.cruiser.end.Across) {
						int tx = data.cruiser.start.Across;
						int ty1 = data.cruiser.start.Down;
						int ty2 = data.cruiser.start.Down + 1;
						int ty3 = data.cruiser.end.Down;
						if (x1 == tx || x2 == tx) {
							return false;
						}
						if (y == ty1 || y == ty2 || y == ty3) {
							return false;
						}
						return true;
					}
					else{
						int tx1 = data.cruiser.start.Across;
						int tx2 = data.cruiser.start.Across+1;
						int tx3 = data.cruiser.end.Across;
						int ty = data.cruiser.start.Down;
						if(x1 == tx1 || x1 == tx2 || x1 == tx3 || x2 == tx1 || x2 == tx2 || x2 == tx3){
							return false;
						}
						if(y == ty){
							return false;
						}
						return true;
					}
				}
				if(data.destroyer.start.Across != 0 && data.destroyer.end.Across != 0) {
					if (data.destroyer.start.Across == data.destroyer.end.Across) {
						int tx = data.destroyer.start.Across;
						int ty1 = data.destroyer.start.Down;
						int ty2 = data.destroyer.end.Down;
						if (x1 == tx || x2 == tx ) {
							return false;
						}
						if (y == ty1 || y == ty2) {
							return false;
						}
						return true;
					}
					else{
						int tx1 = data.battleship.start.Across;
						int tx2 = data.battleship.end.Across;
						int ty = data.battleship.start.Down;
						if (x1 == tx1 || x1 == tx2 || x2 == tx1 || x2 == tx2) {
							return false;
						}
						if (y == ty) {
							return false;
						}
						return true;
					}
				}
			}
			else{
				int y1 = Integer.parseInt(tokens[3]);
				int y2 = Integer.parseInt(tokens[3])+1;
				int x = Integer.parseInt(tokens[2]);
				if(data.aircraftCarrier.start.Across != 0 && data.aircraftCarrier.end.Across != 0){
					if(data.aircraftCarrier.start.Across == data.aircraftCarrier.end.Across) {
						int tx = data.aircraftCarrier.start.Across;
						int ty1 = data.aircraftCarrier.start.Down;
						int ty2 = data.aircraftCarrier.start.Down + 1;
						int ty3 = data.aircraftCarrier.start.Down + 2;
						int ty4 = data.aircraftCarrier.start.Down +3;
						int ty5 = data.aircraftCarrier.end.Down;
						if (x == tx) {
							return false;
						}
						if (y1 == ty1 || y2 == ty1 || y1 == ty2 || y2 == ty2 || y1 == ty3 || y2 == ty3 || y1 == ty4 || y2 == ty4 || y1 == ty5 || y2 == ty5) {
							return false;
						}
						return true;
					}
					else{
						int tx1 = data.aircraftCarrier.start.Across;
						int tx2 = data.aircraftCarrier.start.Across+1;
						int tx3 = data.aircraftCarrier.start.Across+2;
						int tx4 = data.aircraftCarrier.start.Across+3;
						int tx5 = data.aircraftCarrier.end.Across;
						int ty = data.aircraftCarrier.start.Down;
						if(x == tx1 || x == tx2 || x == tx3 || x == tx4|| x == tx5){
							return false;
						}
						if(y1 == ty || y2 == ty){
							return false;
						}
						return true;
					}
				}
				if(data.battleship.start.Across != 0 && data.battleship.end.Across != 0){
					if(data.battleship.start.Across == data.battleship.end.Across) {
						int tx = data.battleship.start.Across;
						int ty1 = data.battleship.start.Down;
						int ty2 = data.battleship.start.Down + 1;
						int ty3 = data.battleship.start.Down + 2;
						int ty4 = data.battleship.end.Down;
						if (y1 == ty1 || y1 == ty2 || y1 == ty3 || y1 == ty4 || y2 == ty1 || y2 == ty2 || y2 == ty3 || y2 == ty4) {
							return false;
						}
						if (x == tx) {
							return false;
						}
						return true;
					}
					else{
						int tx1 = data.battleship.start.Across;
						int tx2 = data.battleship.start.Across+1;
						int tx3 = data.battleship.start.Across+2;
						int tx4 = data.battleship.end.Across;
						int ty = data.battleship.start.Down;
						if(x == tx1 || x == tx2 || x == tx3 || x == tx4){
							return false;
						}
						if(y1 == ty || y2 == ty){
							return false;
						}
						return true;
					}
				}
				if(data.cruiser.start.Across != 0 && data.cruiser.end.Across != 0){
					if(data.cruiser.start.Across == data.cruiser.end.Across) {
						int tx = data.cruiser.start.Across;
						int ty1 = data.cruiser.start.Down;
						int ty2 = data.cruiser.start.Down + 1;
						int ty3 = data.cruiser.end.Down;
						if (y1 == ty1 || y1 == ty2 || y1 == ty3 || y2 == ty1 || y2 == ty2 || y2 == ty3) {
							return false;
						}
						if (x == tx) {
							return false;
						}
						return true;
					}
					else{
						int tx1 = data.cruiser.start.Across;
						int tx2 = data.cruiser.start.Across+1;
						int tx3 = data.cruiser.end.Across;
						int ty = data.cruiser.start.Down;
						if(x == tx1 || x == tx2 || x == tx3){
							return false;
						}
						if(y1 == ty || y2 == ty){
							return false;
						}
						return true;
					}
				}
				if(data.destroyer.start.Across != 0 && data.destroyer.end.Across != 0) {
					if (data.destroyer.start.Across == data.destroyer.end.Across) {
						int tx = data.destroyer.start.Across;
						int ty1 = data.destroyer.start.Down;
						int ty2 = data.destroyer.end.Down;
						if (y1 == ty1 || y2 == ty1 || y1 == ty2|| y2 == ty2) {
							return false;
						}
						if (x == tx) {
							return false;
						}
						return true;
					}
					else{
						int tx1 = data.destroyer.start.Across;
						int tx2 = data.destroyer.end.Across;
						int ty = data.destroyer.start.Down;
						if (x == tx1 || x == tx2) {
							return false;
						}
						if (y1 == ty || y2 == ty) {
							return false;
						}
						return true;
					}
				}
			}
	}
}

//Similar to placeShip, but with firing.
private static String fireAt(Request req) {
	return null;
}

}
