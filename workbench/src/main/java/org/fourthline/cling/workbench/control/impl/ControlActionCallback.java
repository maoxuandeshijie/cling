/*
 * Copyright (C) 2013 4th Line GmbH, Switzerland
 *
 * The contents of this file are subject to the terms of either the GNU
 * Lesser General Public License Version 2 or later ("LGPL") or the
 * Common Development and Distribution License Version 1 or later
 * ("CDDL") (collectively, the "License"). You may not use this file
 * except in compliance with the License. See LICENSE.txt for more
 * information.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 */

package org.fourthline.cling.workbench.control.impl;

import org.fourthline.cling.controlpoint.ActionCallback;
import org.fourthline.cling.model.action.ActionArgumentValue;
import org.fourthline.cling.model.action.ActionCancelledException;
import org.fourthline.cling.model.action.ActionInvocation;
import org.fourthline.cling.model.message.UpnpResponse;
import org.fourthline.cling.workbench.Workbench;

import java.util.logging.Level;


abstract public class ControlActionCallback extends ActionCallback {

    public ControlActionCallback(ActionInvocation actionInvocation) {
        super(actionInvocation);
    }

    @Override
    public void success(final ActionInvocation invocation) {
        onSuccess(invocation.getOutput());
        Workbench.log(
            "Action Invocation",
            "Completed invocation: " + invocation.getAction().getName()
        );
    }

    @Override
    public void failure(ActionInvocation invocation,
                        UpnpResponse operation,
                        String defaultMsg) {
        if (invocation.getFailure() instanceof ActionCancelledException) {
            Workbench.log(
                "Action Invocation",
                "Action execution of '" + invocation.getAction().getName() + "' was cancelled, cause: "
                    + invocation.getFailure().getCause().getMessage()
            );
        } else {
            Workbench.log(
                Level.SEVERE,
                "Action Invocation",
                defaultMsg
            );
        }
    }

    abstract protected void onSuccess(ActionArgumentValue[] values);

}
