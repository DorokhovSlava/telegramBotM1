package com.dorokhov.telegrambotm1.service.impl;

import com.dorokhov.telegrambotm1.service.BusInfoService;
import lombok.AllArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class BusInfoServiceImpl implements BusInfoService {

    /**
     * @param msg
     * @param url
     */
    @Override
    public List<String> getBusInfo(Message msg, String url) throws IOException {
        Document busInfo = Jsoup.connect(url).get();
        List<String> answer = new ArrayList<>();

        // Получаем содержимое указанного класса и элементы указанного тега
        Elements liElements = busInfo.getElementsByClass("time-table").get(0).getElementsByTag("li");
        String hour = "Час: ";
        String minutes = "Минуты: ";
        for (Element liElement : liElements) {
            answer.add(hour + liElement.getElementsByClass("hour").text() +
                    "  " + minutes + liElement.getElementsByClass("minute").text());
        }
        return answer;
    }
}
