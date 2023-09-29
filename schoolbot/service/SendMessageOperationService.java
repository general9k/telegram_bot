package ru.schoolbot.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import static java.util.Arrays.asList;
import static ru.schoolbot.constant.VarConstant.*;

public class SendMessageOperationService {
    private final String GREETING_MESSAGE = "Здравствуйте, желаете посмотреть расписание на завтра?";
    private final ButtonService buttonService = new ButtonService();


    // Кнопки для первого представления
    public SendMessage createGreetingInformation(Update update) {
        SendMessage message = createSimpleMessage(update, GREETING_MESSAGE);
        ReplyKeyboardMarkup keyboardMarkup = buttonService.setButtons(buttonService.createButtons(
                (asList(TABLE))));
        message.setReplyMarkup(keyboardMarkup);
        return message;
    }

    // Пользователь выбирает первую часть всех классов
    public SendMessage classSelectionA(Update update) {
        SendMessage message = createSimpleMessage(update, TAKE_CLASS_A);
        ReplyKeyboardMarkup keyboardMarkup = buttonService.setButtons(buttonService.createButtons(CLASS_GROUPS_A));
        message.setReplyMarkup(keyboardMarkup);
        return message;
    }

    // Пользователь выбирает вторую часть всех классов
    public SendMessage classSelectionB(Update update) {
        SendMessage message = createSimpleMessage(update, TAKE_CLASS_B);
        ReplyKeyboardMarkup keyboardMarkup = buttonService.setButtons(buttonService.createButtons(CLASS_GROUPS_B));
        message.setReplyMarkup(keyboardMarkup);
        return message;
    }

    // Написание расписания для конкретного класса
    public SendMessage findClass(Update update) {
        int indexMessage = 0;
        boolean flag = false;
        SendMessage message = null;
        for (String mess: CLASS_GROUPS_A) {
            if (update.getMessage().getText().equals(mess)) {
                indexMessage = CLASS_GROUPS_A.indexOf(mess);
                flag = false;
            }
        }
        for (String mess: CLASS_GROUPS_B) {
            if (update.getMessage().getText().equals(mess)) {
                indexMessage = CLASS_GROUPS_B.indexOf(mess);
                flag = true;
            }
        }

        if (flag) {
            message = createSimpleMessage(update, TABLE_CLASS.get(indexMessage + 8));
        }
        else {
            message = createSimpleMessage(update, TABLE_CLASS.get(indexMessage));
        }
        return message;
    }

    // Отправка сообщения именно нашему пользователю
    private SendMessage createSimpleMessage(Update update, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
        sendMessage.setText(message);
        return sendMessage;
    }

}
