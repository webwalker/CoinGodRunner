package com.webwalker.runner.task;

import com.webwalker.core.auth.AuthResolver;
import com.webwalker.core.config.ConfigItem;
import com.webwalker.core.config.ConfigResolver;
import com.webwalker.core.config.PlatformType;
import com.webwalker.core.config.TaskParams;
import com.webwalker.core.utility.FileUtil;
import com.webwalker.core.utility.JsonUtil;
import com.webwalker.main.CoinGodRunner;
import com.webwalker.runner.panel.MultiAccountPanel;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class TaskController {
    private static Map<String, ConfigItem> platforms = new HashMap<>();
    private static Map<String, RunTaskParams> tasks = new HashMap<>();

    public static String getStatus(String key) {
        RunTaskParams param = tasks.get(key);
        if (param != null) {
            if (param.running) {
                return "运行中";
            }
        }
        return "未运行";
    }

    public static boolean isRunning(String key) {
        RunTaskParams param = tasks.get(key);
        if (param != null) {
            return param.running;
        }
        return false;
    }

    public static void setStatus(String key, boolean running) {
        RunTaskParams param = tasks.get(key);
        if (param != null) {
            param.running = running;
        }
    }

    public static void loadPlatform(PlatformType platform) {
        ConfigItem c = ConfigResolver.getConfig(platform.getName());
        if (c == null || c.configs.size() == 0) return;
        platforms.put(platform.getName(), c);
        //添加或更新到任务列表
        for (TaskParams t : c.configs) {
            RunTaskParams r = tasks.get(t.accessKey);
            if (r == null) {
                r = new RunTaskParams().mapping(t);
            } else {
                boolean running = r.running;
                r = new RunTaskParams().mapping(t);
                r.running = running;
            }
            tasks.put(t.accessKey, r);
        }
    }

    public static ConfigItem getPlatform(PlatformType platform) {
        ConfigItem configItem = platforms.get(platform.getName());
        return configItem;
    }

    public static TaskParams getTask(String key) {
        TaskParams params = tasks.get(key);
        return params;
    }

    public static void start(JPanel panel, String platform, String key, String authCode) {
        if (!AuthResolver.isValidCode(key, authCode)) {
            showMessage(panel, "无效的授权码!");
            return;
        }
        CoinGodRunner.start(platform, key);

        TaskController.setStatus(key, true);
        MultiAccountPanel.getInstance(platform).reload();

        showMessage(panel, "机器人已启动!");
    }

    public static void pause(JPanel panel, String platform, String key) {
        CoinGodRunner.pause(key);

        TaskController.setStatus(key, false);
        MultiAccountPanel.getInstance(platform).reload();
    }

    public static void showMessage(JPanel panel, String message) {
        JOptionPane.showMessageDialog(panel, message);
    }
}
