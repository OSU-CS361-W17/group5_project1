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

/**
 * Created by williamstribyjr on 2/3/17.
 */
class allTests {

    @Test
    public void testfireAt() {
        TestResponse res = request("POST", "/fire/3/1");
        assertEquals("3", col);
        assertEquals("1", row);
    }

package edu.oregonstate.cs361.battleship;

import static org.junit.jupiter.api.Assertions.*;

class placeshipTest{

}

class isInBoundsTest{

}