package com.dorokhov.telegrambotm1.service;

import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.IOException;
import java.util.List;

public interface BusInfoService {

    List<String> getBusInfo(Message msg, String url) throws IOException;

}
