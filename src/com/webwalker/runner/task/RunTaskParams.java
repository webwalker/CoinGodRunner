package com.webwalker.runner.task;

import com.webwalker.core.config.TaskParams;
import com.webwalker.core.utility.JsonUtil;

public class RunTaskParams extends TaskParams {
    public String platform;
    public boolean running;

    public RunTaskParams() {
    }

    public RunTaskParams mapping(TaskParams p) {
        String json = JsonUtil.toJson(p);
        return JsonUtil.fromJson(json, this.getClass());
    }
}
