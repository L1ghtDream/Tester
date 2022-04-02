package dev.lightdream.tester;

import dev.lightdream.lambda.LambdaExecutor;
import dev.lightdream.logger.Logger;

import java.util.concurrent.atomic.AtomicBoolean;

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
    public Test(R desiredOutput, int id, LambdaExecutor.NoArgLambdaExecutor<R> test) {
        this.id = id;
        this.test = test;
        this.desiredOutput = desiredOutput;
    }

    @SuppressWarnings("unused")
    public Test(R desiredOutput, LambdaExecutor.NoArgLambdaExecutor<R> test, LambdaExecutor.NoReturnNoArgLambdaExecutor testCleanup) {
        this.test = test;
        this.desiredOutput = desiredOutput;
        this.testCleanup = testCleanup;
    }

    @SuppressWarnings("unused")
    public Test(R desiredOutput, LambdaExecutor.NoArgLambdaExecutor<R> test) {
        this.test = test;
        this.desiredOutput = desiredOutput;
    }

    public void run() {

        AtomicBoolean error = new AtomicBoolean(false);

        R output = LambdaExecutor.LambdaCatch.ReturnLambdaCatch.executeCatch(test::execute, e -> {
            Logger.error("Test " + id + " failed with exception: " + e.getMessage());
            error.set(true);
            return null;
        });

        if (error.get()) return;

        boolean check = output == null && desiredOutput == null;
        if (!check && output != null) {
            check = output.equals(desiredOutput);
        }

        if (!check) {
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
