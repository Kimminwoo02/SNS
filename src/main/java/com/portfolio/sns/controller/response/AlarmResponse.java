package com.portfolio.sns.controller.response;

import com.portfolio.sns.model.*;
import com.portfolio.sns.model.entity.AlarmEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.sql.Timestamp;
@Data
@AllArgsConstructor
public class AlarmResponse {

    private Integer id;
    private AlarmType alarmType;
    private AlarmArgs alarmArgs;
    private String text;
    private Timestamp registeredAt;
    private Timestamp updatedAt;
    private Timestamp deletedAt;



    public static AlarmResponse fromAlarm(Alarm alarm){
        return new AlarmResponse(
               alarm.getId(),
               alarm.getAlarmType(),
               alarm.getArgs(),
               alarm.getAlarmType().getAlarmText(),
               alarm.getRegisteredAt(),
               alarm.getUpdatedAt(),
               alarm.getDeletedAt()
        );
    }
}
