package com.innerman.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by petrpopov on 29/06/16.
 */

@Service
public class StreetBotService extends TelegramLongPollingBot {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${bot.token}")
    private String token;

    @Value("${bot.username}")
    private String username;

    @Value("${bot.name}")
    private String name;

    @Autowired
    private StreetBotHandler streetBotHandler;

    @PostConstruct
    public void init() {

        logger.info("Registering Telegram {} bot", username);

        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();

        try {
            telegramBotsApi.registerBot(this);
        } catch (TelegramApiException e) {
            logger.error("Failed to register Telegram bot");
            e.printStackTrace();
        }

        logger.info("Done registering Telegram {} bot", username);
    }

    @Override
    public void onUpdateReceived(Update update) {

        List<SendMessage> messages = streetBotHandler.handleMessage(update);
        if(messages.isEmpty()) {
            return;
        }

        messages.forEach(message -> {
            try {
                sendMessage(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return token;
    }
}
