package com.example.examplemod;

import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
import net.arikia.dev.drpc.DiscordUser;
import net.arikia.dev.drpc.callbacks.ReadyCallback;

public class DiscordManager {

    private static final String APP_ID = "1477348226414678077";

    public static void start() {

        DiscordEventHandlers handlers = new DiscordEventHandlers.Builder()
                .setReadyEventHandler(new ReadyCallback() {
                    @Override
                    public void apply(DiscordUser user) {
                        System.out.println("Discord RPC запущен для пользователя: " + user.username);
                    }
                }).build();

        // Инициализация
        DiscordRPC.discordInitialize(APP_ID, handlers, true);

        // Установка начального статуса
        updatePresence("В главном меню", "Подготовка к игре");
    }

    public static void updatePresence(String details, String state) {
        DiscordRichPresence presence = new DiscordRichPresence.Builder(state)
                .setDetails(details)
                .setBigImage("logo", "ZOV Mod")
                .setStartTimestamps(System.currentTimeMillis())
                .build();

        DiscordRPC.discordUpdatePresence(presence);
    }

    public static void stop() {
        DiscordRPC.discordShutdown();
    }
}