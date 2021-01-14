package com.is;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import com.is.bot.MyBot;
import com.is.utils.ConnectionPool;

public class Main {

	public static void main(String[] args) {

		ConnectionPool.loadProperties();
		ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();

        try {
            MyBot myBot = new MyBot();
            telegramBotsApi.registerBot(myBot);
            myBot.runTimer();
            System.out.println("Bot is running");
        } catch (TelegramApiRequestException e) {
            //e.printStackTrace();
        }

	}
}
