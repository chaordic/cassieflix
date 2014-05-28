package br.com.chaordic.cassieflix.core.pojo;

public enum ActionType {
    click, watch, like;
    
//    @JsonCreator
//    public static ActionType fromValue(String value){
//        return ActionType.valueOf(value);
//    }
//    
//    @JsonValue
//    public static String value(ActionType actionType){
//        return actionType.name();
//    }
}
