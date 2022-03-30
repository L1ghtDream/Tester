package dev.lightdream.tester;

import dev.lightdream.lambda.LambdaExecutor;
import dev.lightdream.logger.Logger;

public class Test<R> {

    public int id = -1;
    public R desiredOutput;
    LambdaExecutor.NoArgLambdaExecutor<R> test;

    @SuppressWarnings("unused")
    public Test(int id, LambdaExecutor.NoArgLambdaExecutor<R> test, R desiredOutput) {
        this.id = id;
        this.test = test;
        this.desiredOutput = desiredOutput;
    }

    @SuppressWarnings("unused")
    public Test(LambdaExecutor.NoArgLambdaExecutor<R> test, R desiredOutput) {
        this.test = test;
        this.desiredOutput = desiredOutput;
    }

    public void run() {

        R output = test.execute();
        if (!output.equals(desiredOutput)) {
            if (id != -1) {
                Logger.log("Test " + id + " failed. Expected: " + desiredOutput + " but got: " + output);
                return;
            }
            Logger.error("Test failed: " + output + " != " + desiredOutput);
            return;
        }
        if (id != -1) {
            Logger.log("Test " + id + " passed.");
            return;
        }
        Logger.log("Test passed.");
    }

}
