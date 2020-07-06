package com.bindstone.view;

import com.bindstone.service.GreetService;
import com.bindstone.service.RequestTransportService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Route
public class MainView extends VerticalLayout {

    public MainView(GreetService service, RequestTransportService requestTransportService) {

        TextField textField = new TextField("Your name");
        Button button = new Button("Say hello",
                e -> {
                    service.greet(textField.getValue()).subscribe(s -> {
                                Optional<UI> ui = this.getUI();
                                if (ui.isPresent()) {
                                    ui.get().access(() -> {
                                        Notification.show(s);
                                    });
                                }
                            });
         });

        Button button2 = new Button("Request",
                e -> {
                    requestTransportService.request().subscribe();
                });

        button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        button.addClickShortcut(Key.ENTER);
        addClassName("centered-content");
        add(textField, button, button2);
    }
}
