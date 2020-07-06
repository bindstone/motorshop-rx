package com.bindstone;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.shared.communication.PushMode;
import com.vaadin.flow.shared.ui.Transport;

@PWA(name = "Motorshop", shortName = "motorshop")
@Push(value = PushMode.AUTOMATIC, transport = Transport.WEBSOCKET)
public class AppShell implements AppShellConfigurator {
}

