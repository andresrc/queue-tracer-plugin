package org.jenkinsci.plugins.queuetracer;

import hudson.Extension;
import hudson.model.Queue.BlockedItem;
import hudson.model.Queue.BuildableItem;
import hudson.model.Queue.Item;
import hudson.model.Queue.LeftItem;
import hudson.model.Queue.Task;
import hudson.model.Queue.WaitingItem;
import hudson.model.queue.QueueListener;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Queue listener that logs all intercepted queue operations.
 */
@Extension
public class QueueTracer extends QueueListener {

    /** Logger to use. */
    private static final Logger LOGGER = Logger.getLogger(QueueTracer.class.getName());

    private void log(String name, Item item) {
        if (!LOGGER.isLoggable(Level.FINEST)) {
            return;
        }
        final String msg;
        if (item == null) {
            msg = String.format("[%s] called with null item", name);
        } else {
            Task task = item.task;
            final String taskMsg;
            if (task == null) {
                // Should not happen
                taskMsg = "null";
            } else {
                taskMsg = String.format("[%s]-[%s]-[%s]", task.getClass().getName(), task.getDisplayName(), task.getCauseOfBlockage());
            }
            msg = String.format("[%s] called with item [%s], item task %s", name, item, taskMsg);
        }
        LOGGER.log(Level.FINEST, msg);
    }

    @Override
    public void onEnterWaiting(WaitingItem wi) {
        log("onEnterWaiting", wi);
    }

    @Override
    public void onLeaveWaiting(WaitingItem wi) {
        log("onLeaveWaiting", wi);
    }

    @Override
    public void onEnterBlocked(BlockedItem bi) {
        log("onEnterBlocked", bi);
    }

    @Override
    public void onLeaveBlocked(BlockedItem bi) {
        log("onLeaveBlocked", bi);
    }

    @Override
    public void onEnterBuildable(BuildableItem bi) {
        log("onEnterBuildable", bi);
    }

    @Override
    public void onLeaveBuildable(BuildableItem bi) {
        log("onLeaveBuildable", bi);
    }

    @Override
    public void onLeft(LeftItem li) {
        log("onLeft", li);
    }
}
