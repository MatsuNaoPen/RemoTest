package com.matusnao.remotest.enums

/**
 * Created by DevUser on 2018/05/04.
 */
enum class EventEnum(val key: String, val text: String) {
    GET_USER_EVENT("get_user_key", "ユーザー情報取得"),
    GET_DEVICES("devices", "機器情報取得"),
    GET_APPLIANCES("appliances", "登録機器情報取得"),
    POST_SIGNALS_XXX_SEND("signalSend", "信号送信")
}