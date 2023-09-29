package ru.schoolbot.core;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.schoolbot.service.SendMessageOperationService;

import static ru.schoolbot.constant.VarConstant.*;


public class CoreBot extends TelegramLongPollingBot {
    SendMessageOperationService sendMessageOperationService = new SendMessageOperationService();
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            switch (update.getMessage().getText()) {
                case START:
                    executeMessage(sendMessageOperationService.createGreetingInformation(update));
                    break;
                case TABLE:
                case OLD_CLASS:
                    executeMessage(sendMessageOperationService.classSelectionA(update));
                    break;
                case NEXT_CLASS:
                    executeMessage(sendMessageOperationService.classSelectionB(update));
                    break;
                default:
                    executeMessage(sendMessageOperationService.findClass(update));
                    break;
            }
        }
    }

    @Override
    public String getBotUsername() {
        return UserName;
    }

    @Override
    public String getBotToken() {
        return Token;
    }

    protected <T extends BotApiMethod> void executeMessage(T sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }
}
