package com.is.bot;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

public class BotHandler {

    private static  HashMap<String, String> sets = new HashMap<>();


    static {
        sets = BotService.getSets();
    }


    public static SendMessage start(Update update){

        long chat_id = update.getMessage().getChatId();

        SendMessage message = new SendMessage();
        message.setText(sets.get("START"));
        message.setChatId(chat_id);
        message.setReplyMarkup(BotHandler.getReplyKeyboardMarkup());

        return message;
    }


    public static SendMessage stop(Update update){

        long chat_id = update.getMessage().getChatId();
        BotService.changeUserState(chat_id, "DELETED");

        return null;
    }


    public static SendMessage errorMessage(Update update){

        long chat_id = update.getMessage().getChatId();

        SendMessage message = new SendMessage();
        message.setChatId(chat_id);
        message.setText("Noaniq habar jo'natildi !");

        return  message;
    }


    private static ReplyKeyboardMarkup getReplyKeyboardMarkup(){

        List<KeyboardRow> lkeyboardRow = new ArrayList<>();
        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();

        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add(new KeyboardButton("Telefon raqamni yuborish").setRequestContact(true));
        lkeyboardRow.add(keyboardRow);
        keyboard.setKeyboard(lkeyboardRow);
        keyboard.setOneTimeKeyboard(true);
        keyboard.setResizeKeyboard(true);
        keyboard.setSelective(true);

        return keyboard;
    }


	public static SendMessage registrate(Update update){

        long chat_id = update.getMessage().getChatId();

        SendMessage message = new SendMessage();
        message.setChatId(chat_id);
        message.setReplyMarkup(null);

        String username = update.getMessage().getFrom().getUserName();
        String phone_number = update.getMessage().getContact().getPhoneNumber();

        Users user = BotService.findUser(chat_id);
        if(user == null){

            boolean created = BotService.createUser(new Users(username, chat_id, phone_number));
            message.setText(sets.get("CREATED"));

            if(!created){
                message.setText(sets.get("NOT_CREATED"));
            }
        }

        else if(user.getStatus().equals("DELETED")){

            BotService.changeUserState(chat_id, "ACTIVE");
            message.setText(sets.get("USER_UPDATED"));
        }

        else{

            message.setText(sets.get("USER_EXISTS"));
        }

		return message;
	}


	public static List<SendObject> send(){

        List<SendObject> list = new ArrayList<>();
        List<NewsSender> newsList = BotService.sendingNews();

        for(NewsSender news: newsList){

            SendPhoto sendPhoto = new SendPhoto();
            sendPhoto.setParseMode("Markdown");
            sendPhoto.setChatId(news.getChat_id());
            sendPhoto.setPhoto(new File(sets.get("FILE_PATH") + news.getImage()));
            sendPhoto.setCaption("*" + news.getTitle()   + "*" + "\n\n"
                                     + news.getContent() + "\n\n" +
                                 "_" + news.getAuthor()  + "_");

            list.add(new SendObject(news.getId(), sendPhoto));
        }

        return list;
	}

}