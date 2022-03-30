package dev.lightdream.tester;

import dev.lightdream.lambda.LambdaExecutor;
import dev.lightdream.logger.Logger;

public class Test<R> {

    private final LambdaExecutor.NoArgLambdaExecutor<R> test;
    public int id = -1;
    public R desiredOutput;
    private LambdaExecutor.NoReturnNoArgLambdaExecutor testCleanup = null;

    @SuppressWarnings("unused")
    public Test(int id, LambdaExecutor.NoArgLambdaExecutor<R> test, R desiredOutput, LambdaExecutor.NoReturnNoArgLambdaExecutor testCleanup) {
        this.id = id;
        this.test = test;
        this.desiredOutput = desiredOutput;
        this.testCleanup = testCleanup;
    }

    @SuppressWarnings("unused")
    public Test(int id, LambdaExecutor.NoArgLambdaExecutor<R> test, R desiredOutput) {
        this.id = id;
        this.test = test;
        this.desiredOutput = desiredOutput;
    }

    @SuppressWarnings("unused")
    public Test(LambdaExecutor.NoArgLambdaExecutor<R> test, R desiredOutput, LambdaExecutor.NoReturnNoArgLambdaExecutor testCleanup) {
        this.test = test;
        this.desiredOutput = desiredOutput;
        this.testCleanup = testCleanup;
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
            } else {
                Logger.error("Test failed: " + output + " != " + desiredOutput);
            }
        } else {
            if (id != -1) {
                Logger.log("Test " + id + " passed.");
            } else {
                Logger.log("Test passed.");
            }
        }

        if (testCleanup != null) {
            testCleanup.execute();
        }
    }

    public void setID(int id) {
        this.id = id;
    }

}
