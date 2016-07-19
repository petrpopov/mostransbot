package com.innerman.service;

import com.google.common.base.Strings;
import com.innerman.dto.StreetEntity;
import com.innerman.geo.LocationEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.CallbackQuery;
import org.telegram.telegrambots.api.objects.Location;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by petrpopov on 29/06/16.
 */

@Component
public class StreetBotHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private StreetService streetService;

    public List<SendMessage> handleMessage(Update mes) {

        List<SendMessage> res = new ArrayList<>();
        logger.info("Received message {}", mes);

        if(mes.hasCallbackQuery()) {
            CallbackQuery callbackQuery = mes.getCallbackQuery();
            String streetId = callbackQuery.getData();

            if(!Strings.isNullOrEmpty(streetId)) {
                String streetInfo = streetService.getStreetInfo(streetId);
                return getMessagesForStreetInfo(callbackQuery, streetInfo);
            }
        }

        if(mes.getMessage() == null) {
            return res;
        }

        Location location = mes.getMessage().getLocation();
        res = getMessagesForLocation(mes, location);

        return res;
    }

    private List<SendMessage> getMessagesForStreetInfo(CallbackQuery callbackQuery, String info) {

        List<SendMessage> res = new ArrayList<>();

        SendMessage message = new SendMessage();
        message.enableMarkdown(true);
        message.setChatId(callbackQuery.getMessage().getChatId().toString());
        message.setText(info);
        res.add(message);

        return res;
    }

    private List<SendMessage> getMessagesForLocation(Update mes, Location location) {

        List<SendMessage> res = new ArrayList<>();

        if(location != null) {
            LocationEntity loc = new LocationEntity(location.getLatitude(), location.getLongitude());

            logger.info("Searching for streets for location {}", loc);
            List<StreetEntity> streets = streetService.findNearestStreets(loc);
            logger.info("Found {} streets for locatio {}", streets.size(), loc);

            streets.forEach(s -> {
                SendMessage message = new SendMessage();
                message.enableMarkdown(true);
                message.setChatId(mes.getMessage().getChatId().toString());
                message.setReplyMarkup(getKeyboardForMessage(s.getId()));
                message.setText(s.getCells().getAddress());
                res.add(message);
            });
        }
        return res;
    }

    private InlineKeyboardMarkup getKeyboardForMessage(String id) {

        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText("подробнее");
        button.setCallbackData(id);

        List<InlineKeyboardButton> buttons = new ArrayList<>();
        buttons.add(button);

        List<List<InlineKeyboardButton>> btngrp = new ArrayList<>();
        btngrp.add(buttons);

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(btngrp);
        return markup;
    }
}
