package com.dorokhov.telegrambotm1.service.impl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


class BusInfoServiceImplTest {

    String url = "http://www.m.gortransperm.ru/time-table/24/108302";

    @BeforeEach
    void setUp() {
    }

    @Test
    void getBusInfo() {
        try {
            Document expected = Jsoup.connect(url).get();
            Elements liElements = expected.getElementsByClass("time-table").get(0).getElementsByTag("li");
            Assertions.assertNotEquals(0, liElements.size());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}