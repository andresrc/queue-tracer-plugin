package org.jenkinsci.plugins.queuetracer;

import hudson.Extension;
import hudson.model.Cause;
import hudson.model.FreeStyleProject;
import hudson.model.Queue.BlockedItem;
import hudson.model.Queue.BuildableItem;
import hudson.model.Queue.Item;
import hudson.model.Queue.LeftItem;
import hudson.model.Queue.Task;
import hudson.model.Queue.WaitingItem;
import hudson.model.queue.QueueListener;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.jvnet.hudson.test.JenkinsRule;

import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Queue listener basic test.
 */
public class QueueTracerTest extends QueueListener {
    @Rule
    public JenkinsRule rule = new JenkinsRule();

    @Test
    public void testBuild() throws Exception {
        FreeStyleProject p = rule.createFreeStyleProject();
        Assert.assertTrue(p.getBuilds().isEmpty());
        Handler handler = new ConsoleHandler();
        handler.setLevel(Level.FINEST);
        Logger logger = Logger.getLogger(QueueTracer.class.getName());
        logger.setLevel(Level.FINEST);
        logger.addHandler(handler);
        Assert.assertTrue(p.scheduleBuild(new Cause.UserIdCause()));
        rule.waitUntilNoActivity();
        Assert.assertFalse(p.getBuilds().isEmpty());
    }

}
