/*
 *
 *   Copyright 2020 Marcin Grzyb
 *
 *   Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 *   The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

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

    private static final Map<Integer,FeedbackEnum  > _map = new HashMap<>();
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
