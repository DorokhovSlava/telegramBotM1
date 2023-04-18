package com.dorokhov.telegrambotm1.command;

import com.dorokhov.telegrambotm1.service.BusInfoService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.IOException;

@Component
public class GetBusInfoACommand implements Command {

    @Autowired
    private BusInfoService busInfoService;

    private final String url = "http://www.m.gortransperm.ru/time-table/24/8100";

    /**
     * @param msg 
     * @return
     */
    @SneakyThrows
    @Override
    public String execute(Message msg){
        return busInfoService.getBusInfo(msg, url).toString();
    }

    /**
     * @return 
     */
    @Override
    public String commandId() {
        return "/bus24_circus";
    }

    /**
     * @return 
     */
    @Override
    public String description() {
        return "Расписание автобуса 24 по остановкен «<Балмошная>», направление «ул.Памирская –> Пл.Дружбы»";
    }
}
