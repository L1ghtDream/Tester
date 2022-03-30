package dev.lightdream.tester;

import dev.lightdream.logger.Logger;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class TesterManager {

    public static TesterManager instance;
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

    public static void registerTest(Test<?> test) {
        instance.tests.add(test);
    }

    public static void testAll(TestableApplication app) {
        testAll(app, true);
    }

    public static void testAll(TestableApplication app, boolean clear) {
        if (!app.test()) {
            Logger.setting("Tests are disabled");
            return;
        }

        Logger.setting("Tests are enabled. The test suite will start running immediately");

        for (Test<?> test : instance.tests) {
            test.run();
        }

        if (clear) {
            instance.tests = new ArrayList<>();
        }
    }


}
