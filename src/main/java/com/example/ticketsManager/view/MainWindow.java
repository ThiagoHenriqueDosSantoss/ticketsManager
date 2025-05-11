package com.example.ticketsManager.view;

import com.example.ticketsManager.controller.TicketController;
import com.example.ticketsManager.controller.UserController;
import com.example.ticketsManager.dto.*;
import com.example.ticketsManager.entities.User;
import org.apache.coyote.BadRequestException;
import org.aspectj.weaver.Shadow;
import org.springframework.http.ResponseEntity;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

import javax.swing.*;
import javax.swing.border.StrokeBorder;
import java.awt.*;
import java.util.Map;

public class MainWindow extends JFrame {
    private  UserController userController;

    private TicketController ticketController;

    public MainWindow(UserController userController, TicketController ticketController) {

        this.userController = userController;
        this.ticketController = ticketController;

        ImageIcon iconeTicketManager = new ImageIcon("C:\\Users\\th650\\IdeaProjects\\ticketsManager\\src\\main\\java\\com\\example\\ticketsManager\\view\\resources\\ticket-manager.png");

        // Configurações da janela principal
        setTitle("Tickets Manager");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setIconImage(iconeTicketManager.getImage());
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

        // Ícones
        ImageIcon iconeUser = new ImageIcon("C:\\Users\\th650\\IdeaProjects\\ticketsManager\\src\\main\\java\\com\\example\\ticketsManager\\view\\resources\\user.png");
        ImageIcon iconeTicket = new ImageIcon("C:\\Users\\th650\\IdeaProjects\\ticketsManager\\src\\main\\java\\com\\example\\ticketsManager\\view\\resources\\ticket.png");
        ImageIcon iconeRelatorio = new ImageIcon("C:\\Users\\th650\\IdeaProjects\\ticketsManager\\src\\main\\java\\com\\example\\ticketsManager\\view\\resources\\report.png");
        ImageIcon iconePorta = new ImageIcon("C:\\Users\\th650\\IdeaProjects\\ticketsManager\\src\\main\\java\\com\\example\\ticketsManager\\view\\resources\\door.png");

        // Botões
        JButton jbUser = new JButton("USUÁRIOS", iconeUser);
        JButton jbTickets = new JButton("TICKETS", iconeTicket);
        JButton jbRelatorio = new JButton("RELATÓRIO", iconeRelatorio);
        JButton jbEncerrar = new JButton("ENCERRAR",iconePorta);

        JButton[] botoes = {jbUser, jbTickets, jbRelatorio,jbEncerrar};

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

        painel.add(Box.createVerticalGlue());
        painel.add(painelBotoes);
        painel.add(Box.createVerticalGlue());

        // Ações dos botões
        jbRelatorio.addActionListener(e -> emitirRelatorio());

        jbUser.addActionListener(e -> {
            Usuario usuario = new Usuario(this, userController, ticketController);
            usuario.setVisible(true);
            this.setVisible(false);
            this.dispose();
        });

        jbTickets.addActionListener(e -> {
            Ticket ticket = new Ticket(this, userController, ticketController);
            ticket.setVisible(true);
            this.setVisible(false);
            this.dispose();
        });
        jbEncerrar.addActionListener(e -> {
            int resposta = JOptionPane.showConfirmDialog(null, "Deseja encerrar o programa?", "Encerrar programa", JOptionPane.YES_NO_OPTION);
            if (resposta == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
        add(painel);
    }

    public void emitirRelatorio() {
        try {
            // Entrada da data
            String dataFimStr = JOptionPane.showInputDialog(null, "Informe a data de fim (yyyy-MM-dd):");
            if (dataFimStr == null || dataFimStr.trim().isEmpty()) return;

            // Converte string para LocalDateTime com fim do dia
            LocalDate dataFim = LocalDate.parse(dataFimStr);
            LocalDateTime dataFimDateTime = dataFim.atTime(23, 59, 59);

            // Chama diretamente o controller ou service
            ResponseEntity<List<RelatorioTicketDTO>> response = ticketController.gerarRelatorio(dataFimDateTime);
            List<RelatorioTicketDTO> lista = response.getBody();

            if (lista.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nenhum ticket encontrado no período informado.");
                return;
            }

            // Agrupa os dados
            Map<String, Long> totalPorUsuario = new HashMap<>();
            long totalGeral = 0;

            for (RelatorioTicketDTO ticket : lista) {
                if (ticket.getNome() == null) continue;
                String nome = ticket.getNome();
                long qtd = ticket.getTotalTickets();

                totalPorUsuario.put(nome, totalPorUsuario.getOrDefault(nome, 0L) + qtd);
                totalGeral += qtd;
            }

            // Monta string do relatório
            StringBuilder relatorio = new StringBuilder("Relatório de Tickets por Funcionário:\n\n");
            for (Map.Entry<String, Long> entry : totalPorUsuario.entrySet()) {
                relatorio.append("Funcionário: ").append(entry.getKey())
                        .append(" - Total: ").append(entry.getValue()).append("\n");
            }
            relatorio.append("\nTotal Geral no Período: ").append(totalGeral);

            // Exibe
            JOptionPane.showMessageDialog(null, relatorio.toString());

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao emitir relatório: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}