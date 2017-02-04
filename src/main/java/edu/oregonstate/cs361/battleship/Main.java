package edu.oregonstate.cs361.battleship;

import com.google.gson.Gson;
import spark.Request;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.staticFiles;
import java.io.OutputStream;

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
	BattleshipModel temp;
	assert temp;
	String jsonString = gson.toJson(temp);
	return jsonString;
}

//This function should accept an HTTP request and deseralize it into an actual Java object.
private static BattleshipModel getModelFromReq(Request req){
	//building new model
	Gson gson = new Gson();

	BattleshipModel temp = gson.fromJson(req.body(), BattleshipModel.class);

	return temp;
}

//This controller should take a json object from the front end, and place the ship as requested, and then return the object.
private static String placeShip(Request req) {
	req.
	BattleshipModel data = getModelFromReq(req);


	return null;
}

//Similar to placeShip, but with firing.
private static String fireAt(Request req) {


	String row = req.params(":col");
	String col = req.params(":row");

	System.out.println("row:" + row);
	System.out.println("col:" + col);

	//String result = java.net.URLDecoder.decode(url, "ASCII");

	return null;
}
}
