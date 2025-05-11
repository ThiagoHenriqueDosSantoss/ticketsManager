package com.example.ticketsManager.view;

import com.example.ticketsManager.controller.TicketController;
import com.example.ticketsManager.controller.UserController;
import com.example.ticketsManager.dto.TicketDTO.CreateTicketDTO;
import com.example.ticketsManager.dto.TicketDTO.UpdateTicketDTO;

import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.*;
import java.awt.*;

public class Ticket extends JFrame {
    private UserController userController;

    private TicketController ticketController;

    public Ticket(MainWindow mainWindow, UserController userController, TicketController ticketController) {

        this.userController = new UserController();
        this.ticketController = ticketController;

        // Configurações da janela principal
        setTitle("Tickets Manager");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Painel principal (vertical)
        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBackground(Color.LIGHT_GRAY);
        painel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Painel de botões (horizontal)
        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new BoxLayout(painelBotoes, BoxLayout.LINE_AXIS));
        painelBotoes.setBackground(Color.LIGHT_GRAY);
        painelBotoes.setAlignmentX(Component.CENTER_ALIGNMENT);

        ImageIcon iconeAddTicket = new ImageIcon("C:\\Users\\th650\\IdeaProjects\\ticketsManager\\src\\main\\java\\com\\example\\ticketsManager\\view\\resources\\add-ticket.png");
        ImageIcon iconeEditTicket = new ImageIcon("C:\\Users\\th650\\IdeaProjects\\ticketsManager\\src\\main\\java\\com\\example\\ticketsManager\\view\\resources\\edit-ticket.png");
        ImageIcon iconeVoltar = new ImageIcon("C:\\Users\\th650\\IdeaProjects\\ticketsManager\\src\\main\\java\\com\\example\\ticketsManager\\view\\resources\\back.png");

        // Características dos botões
        JButton jbCriarTicket = new JButton(iconeAddTicket);
        JButton jbEditarTicket = new JButton(iconeEditTicket);
        JButton jbListarTicket = new JButton("Listar Tickets");
        JButton jbVoltar = new JButton(iconeVoltar);

        // Adicionando os botões ao painel
        JButton[] botoes = {jbCriarTicket, jbEditarTicket,jbListarTicket,jbVoltar};

        for (JButton botao : botoes) {
            botao.setPreferredSize(new Dimension(200, 100));
            botao.setMaximumSize(new Dimension(200, 100));
            botao.setMinimumSize(new Dimension(100, 100));

            botao.setFont(new Font("Arial", Font.BOLD, 14));
            botao.setFocusPainted(false);
            botao.setBackground(new Color(249, 249, 249));
            botao.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
            botao.setOpaque(true);
            botao.setContentAreaFilled(true);

            painelBotoes.add(Box.createHorizontalStrut(15));
            painelBotoes.add(botao);
        }
        jbCriarTicket.addActionListener(e -> criarTicket());
        jbEditarTicket.addActionListener(e -> editarTicket());
        jbListarTicket.addActionListener(e -> listarTickets());
        jbVoltar.addActionListener(e -> {new MainWindow(userController,ticketController);
            mainWindow.setVisible(true);
            this.setVisible(false);
            this.dispose();
        });

        painel.add(Box.createVerticalGlue());
        painel.add(painelBotoes);
        painel.add(Box.createVerticalGlue());

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
    public void listarTickets(){
        List<com.example.ticketsManager.entities.Ticket> ticketList = ticketController.listTickets();

        if (ticketList == null || ticketList.isEmpty()){
            JOptionPane.showMessageDialog(null,"Nenhum ticket encontrado!");
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        StringBuilder lista = new StringBuilder();

        for (com.example.ticketsManager.entities.Ticket ticket : ticketList) {
            lista.append("ID: ").append(ticket.getIdTicket()).append("\n");
            lista.append("Data Criação Ticket: ").append(ticket.getDataEntregaTicket().format(formatter)).append("\n");

            lista.append("Data Atualização Ticket: ");
            if (ticket.getAtualizaoEntregaTicket() != null) {
                lista.append(ticket.getAtualizaoEntregaTicket().format(formatter));
            } else {
                lista.append("N/A");
            }
            lista.append("\n");

            lista.append("Quantidade: ").append(ticket.getQuantidade()).append("\n");

            if (ticket.getUser() != null) {
                lista.append("Usuário Vinculado: ").append(ticket.getUser().getNome()).append("\n");
            } else {
                lista.append("Usuário Vinculado: N/A\n");
            }

            lista.append("-------------------------\n");
        }
        JTextArea textArea = new JTextArea(lista.toString());
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(400, 300));

        JOptionPane.showMessageDialog(null, scrollPane, "Lista de Usuários", JOptionPane.INFORMATION_MESSAGE);
    }
}
