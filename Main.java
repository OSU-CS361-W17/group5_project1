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
	assert temp != null;
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
			data = gson.toJson(data);//serialize
			return data;	//return
		}
	}
	//alert of failure
	return data;
}

private static int isInBounds(String req) {
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
				if(x < 7 & x+length < 11 & x+(length-1)<11 & x+(length-2)<11 & x+(length-3)<11 & x+(length -4)<11){
					return 1;
				}
				else{
					return 0;
				}
			}
			else if(tokens [4] == "vertical"){
				if(y < 7 & y+length < 11 & y+(length-1)<11 & y+(length-2)<11 & y+(length-3)<11 & y+(length -4)<11){
					return 1;
				}
				else{
					return 0;
				}
			}
		case "battleship":
			x = tokens[2];
			y = tokens[3];
			length = 4;
			if(tokens[4] == "horizontal"){
				if(x < 8 & x+length < 11 & x+(length-1)<11 & x+(length-2)<11 & x+(length-3)<11){
					return 1;
				}
				else{
					return 0;
				}
			}
			else if(tokens [4] == "vertical"){
				if(y < 8 & y+length < 11 & y+(length-1)<11 & y+(length-2)<11 & y+(length-3)<11){
					return 1;
				}
				else{
					return 0;
				}
			}
		case "cruiser":
			x = tokens[2];
			y = tokens[3];
			length = 3;
			if(tokens[4] == "horizontal"){
				if(x < 9 & x+length < 11 & x+(length-1)<11 & x+(length-2)<11){
					return 1;
				}
				else{
					return 0;
				}
			}
			else if(tokens [4] == "vertical"){
				if(y < 7 & y+length < 11 & y+(length-1)<11 & y+(length-2)<11){
					return 1;
				}
				else{
					return 0;
				}
			}
		case "destroyer":
			x = tokens[2];
			y = tokens[3];
			length = 2;
			if(tokens[4] == "horizontal"){
				if(x < 9 & x+length < 11 & x+(length-1)<11){
					return 1;
				}
				else{
					return 0;
				}
			}
			else if(tokens [4] == "vertical"){
				if(y < 9 & y+length < 11 & y+(length-1)<11){
					return 1;
				}
				else{
					return 0;
				}
			}
		case "submarine":
			x = tokens[2];
			y = tokens[3];
			length = 2;
			if(tokens[4] == "horizontal"){
				if(x < 9 & x+length < 11 & x+(length-1)<11){
					return 1;
				}
				else{
					return 0;
				}
			}
			else if(tokens [4] == "vertical"){
				if(y < 9 & y+length < 11 & y+(length-1)<11){
					return 1;
				}
				else{
					return 0;
				}
			}
	}

}

private static int isNotOverlap(String req, BattleshipModel data){

	if(overlap) {
		return 0; //False
	}
	if(!overlap){
		return 1; //True
	}
}

//Similar to placeShip, but with firing.
private static String fireAt(Request req) {
	return null;
}

}
