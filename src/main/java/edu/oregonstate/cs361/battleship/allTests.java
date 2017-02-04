package edu.oregonstate.cs361.battleship;

import com.google.gson.Gson;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import spark.Spark;
import spark.utils.IOUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.io.OutputStream;

import static org.junit.jupiter.api.Assertions.*;
import static spark.Spark.awaitInitialization;

class allTests {

    @Test
    public void testfireAt() {
        TestResponse res = request("POST", "/fire/3/1");
        assertEquals("3", col);
        assertEquals("1", row);
    }
    @Test
    public void testIsInBounds(){
        String testForPass = "/placeShip/cruiser/6/4/horizontal";
        String testForFail = "/placeShip/aircraftCarrier/12/1/horizontal";
        boolean tP = isInBounds(testForPass);
        boolean tF = isInBounds(testForFail);
        assertEquals("true",tP);
        assertEquals("false",tF);
    }

    @Test
    public void testIsNotOverlap(){
        String testForPass = "/placeShip/cruiser/6/4/horizontal";
        BattleshipModel temp = new BattleshipModel();
        temp.submarine.start.Across = 6;
        temp.submarine.start.Down = 4;
        temp.submarine.end.Across = 7;
        temp.submarine.end.Down = 4;
        boolean test = isNotOverlap(testForPass,temp);
        assertEquals("false",test);
    }

    @Test
    public void testPlaceShip(){
        TestResponse res = request("POST", "/placeShip/aircraftCarrier/1/1/horizontal");
        assertEquals(200, res.status);
        assertEquals("SHIP",res.body);
    }


    private TestResponse request(String method, String path) {
        try {
            URL url = new URL("http://localhost:4567" + path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method);
            connection.setDoOutput(true);
            connection.connect();
            String body = IOUtils.toString(connection.getInputStream());
            return new TestResponse(connection.getResponseCode(), body);
        } catch (IOException e) {
            e.printStackTrace();
            fail("Sending request failed: " + e.getMessage());
            return null;
        }
    }

    private static class TestResponse {

        public final String body;
        public final int status;

        public TestResponse(int status, String body) {
            this.status = status;
            this.body = body;
        }

        public Map<String,String> json() {
            return new Gson().fromJson(body, HashMap.class);
        }
    }
}


package edu.oregonstate.cs361.battleship;

import static org.junit.jupiter.api.Assertions.*;