package com.example.ticketsManager;

import com.example.ticketsManager.controller.TicketController;
import com.example.ticketsManager.controller.UserController;
import com.example.ticketsManager.view.MainWindow;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javax.swing.*;

@SpringBootApplication
public class TicketsManagerApplication {

	public static void main(String[] args) {

		System.setProperty("java.awt.headless", "false");

		SpringApplication app = new SpringApplication(TicketsManagerApplication.class);
		app.setWebApplicationType(WebApplicationType.NONE);
		ConfigurableApplicationContext context = app.run(args);

		UserController userController = context.getBean(UserController.class);
		TicketController ticketController = context.getBean(TicketController.class);

		SwingUtilities.invokeLater(() -> {
			MainWindow mainWindow = new MainWindow(userController,ticketController);
			mainWindow.setVisible(true);
		});
	}

}
