package com.example.navigationandroid.utils.notifications;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationEvent {

    private String title;
    private String subject;
    private String body;
    private String entityId;
    private String entityName;
    private long eventDate;

    private String dataId;

}
