package edu.oregonstate.cs361.battleship;

import com.google.gson.Gson;
import spark.Request;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.staticFiles;
import static sun.org.mozilla.javascript.internal.regexp.NativeRegExp.TEST;

public class Main {

public class BattleshipModel {
	{
		"aircraftCarrier":
		{
			"name": "AircraftCarrier",
				"length": 5,
				"start":
			{
				"Across": 0,
					"Down": 0 },
			"end":
			{
				"Across": 0,
					"Down": 0}
		},
		"battleship":
		{
			"name": "Battleship",
				"length": 4,
				"start":
			{
				"Across": 0,
					"Down": 0
			},
			"end":
			{
				"Across": 0,
					"Down": 0
			}
		},
		"cruiser":
		{
			"name": "Cruiser",
				"length": 3,
				"start":
			{
				"Across": 0,
					"Down": 0 },
			"end":
			{
				"Across": 0,
					"Down": 0}
		},
		"destroyer":
		{
			"name": "Destroyer",
				"length": 2,
				"start":
			{
				"Across": 0,
					"Down": 0 },
			"end":
			{
				"Across": 0,
					"Down": 0}
		},
		"submarine":
		{
			"name": "Submarine",
				"length": 2,
				"start":
			{
				"Across": 0,
					"Down": 0
			},
			"end":
			{
				"Across": 0,
					"Down": 0
			}
		},
		"computer_aircraftCarrier":
		{
			"name": "Computer_AircraftCarrier",
				"length": 5,
				"start":
			{
				"Across": 2,
					"Down": 2
			},
			"end":
			{
				"Across": 2,
					"Down": 7
			}
		},
		"computer_battleship":
		{
			"name": "Computer_Battleship",
				"length": 4,
				"start":
			{
				"Across": 2,
					"Down": 8
			},
			"end":
			{
				"Across": 6,
					"Down": 8
			}
		},
		"computer_cruiser":
		{
			"name": "Computer_Cruiser",
				"length": 3,
				"start":
			{
				"Across": 4,
					"Down": 1 },
			"end":
			{
				"Across": 4,
					"Down": 4}
		},
		"computer_destroyer":
		{
			"name": "Computer_Destroyer",
				"length": 2,
				"start":
			{
				"Across": 7,
					"Down": 3
			},
			"end":
			{
				"Across": 7,
					"Down": 5
			}
		},
		"computer_submarine":
		{
			"name": "Computer_Submarine",
				"length": 2,
				"start":
			{
				"Across": 9,
					"Down": 6
			},
			"end":
			{
				"Across": 9,
					"Down": 8
			}
		},
		"playerHits": [],
		"playerMisses": [],
		"computerHits": [],
		"computerMisses": []
	}
}

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
	Gson gson = new Gson();
	BattleshipModel temp = gson.fromJson(req.body());
	assert temp;
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
	return null;
}

}
