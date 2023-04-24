package com.dorokhov.telegrambotm1.service.impl;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

public class BusInfoServiceTest {

    @Test
    public void testGetBusInfo_returnsList() throws IOException {

        URL url = new URL("http://www.m.gortransperm.ru");
        HttpURLConnection huc = (HttpURLConnection) url.openConnection();

        int responseCode = huc.getResponseCode();

        assertEquals(HttpURLConnection.HTTP_OK, responseCode);

    }
}
