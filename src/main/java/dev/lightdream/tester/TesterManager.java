package dev.lightdream.tester;

import dev.lightdream.logger.Logger;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class TesterManager {

    private static TesterManager instance;
    public List<Test<?>> tests = new ArrayList<>();

    public TesterManager() {
        if (instance != null) {
            return;
        }
        instance = this;
    }

    public static void init() {
        new TesterManager();
    }

    public void registerTest(Test<?> test) {
        tests.add(test);
    }

    public void testAll(TestableApplication app) {
        if (!app.test()) {
            Logger.setting("Tests are disabled");
            return;
        }

        Logger.setting("Tests are enabled. The test suite will start running immediately");

        for (Test<?> test : tests) {
            test.run();
        }
    }

}
