package com.restaurant.waiterapp.api.resources;

import java.util.HashMap;
import java.util.Map;

public enum FeedbackEnum {
    GREAT(5),GOOD(4), OKAY(3), BAD(2), TERRIBLE(1);

    protected int grade;
    FeedbackEnum(int grade){
        this.grade = grade;
    }

    public int getGrade() {
        return grade;
    }

    private static final Map<Integer,FeedbackEnum  > _map = new HashMap<Integer, FeedbackEnum >();
    static
    {
        for (FeedbackEnum  feedbackEnum  : FeedbackEnum.values())
            _map.put(feedbackEnum .grade, feedbackEnum );
    }

    public static FeedbackEnum fromInt(int grade)
    {
        return _map.get(grade);
    }
}
