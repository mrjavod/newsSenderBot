package com.is.bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.is.utils.ConnectionPool;
import com.is.utils.ISLogger;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MyBot extends TelegramLongPollingBot {


	@Override
	public void onUpdateReceived(Update update) {

		SendMessage message = new SendMessage();

		// when text writing
		if (update.hasMessage() && update.getMessage().hasText()) {

			String cilent_message = update.getMessage().getText();

			// start
			if (cilent_message.equals("/start") || cilent_message.equals("start")) {

                message = BotHandler.start(update);
			}

            // stop
            if (cilent_message.equals("/stop") || cilent_message.equals("stop")) {

                message = BotHandler.stop(update);
            }

        }

		else if(update.getMessage().getContact() != null){

            message = BotHandler.registrate(update);
        }

		else {
			message = BotHandler.errorMessage(update);
		}

		try {

            if (message != null){
                execute(message);
            }

		} catch (TelegramApiException e) {
			ISLogger.getLogger().error(ConnectionPool.getPstr(e));
		}
	}

	@Override
	public String getBotUsername() {
		return ConnectionPool.getBotUsername();
	}

	@Override
	public String getBotToken() {
		return ConnectionPool.getBotToken();
	}

	public void runTimer(){

        TimerTask repeatedTask = new TimerTask() {

            public void run() {

                List<SendObject> list = BotHandler.send();

            	sendNews(list);

            }
        };
        Timer timer = new Timer("Timer");

        long delay  = 1000L;
        long period = 10 * 60 * 1000L;
        timer.scheduleAtFixedRate(repeatedTask, delay, period);

    }

	private void sendNews(List<SendObject> list){

        System.out.println("sending news");

        for(SendObject sendObject: list){
            try {
                execute(sendObject.getSendPhoto());
                BotService.successfullIsSend(sendObject.getId());
            } catch (TelegramApiException e) {
                //e.printStackTrace();
            }
        }
	}

}
