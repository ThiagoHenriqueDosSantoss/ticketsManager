package com.example.ticketsManager.view;

import com.example.ticketsManager.controller.TicketController;
import com.example.ticketsManager.controller.UserController;
import com.example.ticketsManager.dto.CreateTicketDTO;
import com.example.ticketsManager.dto.UpdateTicketDTO;

import javax.swing.*;
import java.awt.*;

public class Ticket extends JFrame {
    private UserController userController;

    private TicketController ticketController;

    public Ticket(MainWindow mainWindow, UserController userController, TicketController ticketController) {

        this.userController = new UserController();
        this.ticketController = ticketController;

        // Característica do JFrame
        setTitle("Tickets Manager");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Característica do JPanel
        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS)); // Layout vertical
        painel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Características dos botões
        JButton jbCriarTicket = new JButton("Criar Ticket");
        JButton jbEditarTicket = new JButton("Editar Ticket");
        JButton jbVoltar = new JButton("Voltar");

        // Adicionando os botões ao painel
        JButton[] botoes = {jbCriarTicket, jbEditarTicket,jbVoltar};

        for (JButton botao : botoes) {
            botao.setAlignmentX(Component.LEFT_ALIGNMENT); // Centraliza os botões
            botao.setMaximumSize(new Dimension(200, 100)); // Tamanho máximo
            botao.setMinimumSize(new Dimension(100, 100)); // Tamanho mínimo
            botao.setFont(new Font("Arial", Font.BOLD, 16));

            botao.setFocusPainted(false); // Tira o foco feio quando clica
            botao.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
            painel.add(botao); // Adiciona os botões ao JPanel
            painel.add(Box.createVerticalStrut(25)); // Espaçamento vertical
        }
        jbCriarTicket.addActionListener(e -> criarTicket());
        jbEditarTicket.addActionListener(e -> editarTicket());
        jbVoltar.addActionListener(e -> {new MainWindow(userController,ticketController);
            mainWindow.setVisible(true);
        });

        add(painel);
    }

    public void criarTicket() {
        try {
            String idStr = JOptionPane.showInputDialog(null, "Informe o ID do usuário para o ticket:");
            if (idStr == null || !idStr.matches("\\d+")) {
                JOptionPane.showMessageDialog(null, "ID inválido!");
                return;
            }
            Long idUser = Long.parseLong(idStr);

            String qtdStr = JOptionPane.showInputDialog(null, "Informe a quantidade de tickets:");
            if (qtdStr == null || !qtdStr.matches("\\d+")) {
                JOptionPane.showMessageDialog(null, "Quantidade inválida!");
                return;
            }
            Long quantidade = Long.parseLong(qtdStr);
            if (quantidade <= 0) {
                JOptionPane.showMessageDialog(null, "A quantidade deve ser maior que zero!");
                return;
            }

            CreateTicketDTO dto = new CreateTicketDTO();
            dto.setIdUser(idUser);
            dto.setQuantidade(quantidade);

            ticketController.createTicket(dto);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public void editarTicket() {
        String idStr = JOptionPane.showInputDialog(null, "Informe o ID do ticket a editar:");
        if (idStr == null || !idStr.matches("\\d+")) {
            JOptionPane.showMessageDialog(null, "ID do ticket inválido!");
            return;
        }
        Long idTicket = Long.parseLong(idStr);

        Long idUser = null;
        Long novaQuantidade = null;

        int opcaoUsuario = JOptionPane.showConfirmDialog(null, "Deseja vincular o ticket a outro usuário?", "Alterar Usuário", JOptionPane.YES_NO_OPTION);
        if (opcaoUsuario == JOptionPane.YES_OPTION) {
            String idUserStr = JOptionPane.showInputDialog(null, "Informe o novo ID do usuário:");
            if (idUserStr == null || !idUserStr.matches("\\d+")) {
                JOptionPane.showMessageDialog(null, "ID de usuário inválido!");
                return;
            }
            idUser = Long.parseLong(idUserStr);
        }

        int opcaoQtd = JOptionPane.showConfirmDialog(null, "Deseja alterar a quantidade de tickets?", "Alterar Quantidade", JOptionPane.YES_NO_OPTION);
        if (opcaoQtd == JOptionPane.YES_OPTION) {
            String qtdStr = JOptionPane.showInputDialog(null, "Informe a nova quantidade de tickets:");
            if (qtdStr == null || !qtdStr.matches("\\d+")) {
                JOptionPane.showMessageDialog(null, "Quantidade inválida!");
                return;
            }
            novaQuantidade = Long.parseLong(qtdStr);
            if (novaQuantidade <= 0) {
                JOptionPane.showMessageDialog(null, "A quantidade deve ser maior que zero!");
                return;
            }
        }

        if (idUser == null && novaQuantidade == null) {
            JOptionPane.showMessageDialog(null, "Nenhum dado foi alterado.");
            return;
        }

        UpdateTicketDTO dto = new UpdateTicketDTO();
        dto.setIdUser(idUser);
        dto.setQuantidade(novaQuantidade);

        try {
            ticketController.updateTicket(idTicket, dto);

            if (ticketController == null) {
                JOptionPane.showMessageDialog(null, "Ticket não encontrado!", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar o ticket: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
