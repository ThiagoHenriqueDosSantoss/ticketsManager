package com.example.ticketsManager.view;

import com.example.ticketsManager.controller.TicketController;
import com.example.ticketsManager.controller.UserController;
import com.example.ticketsManager.dto.*;
import com.example.ticketsManager.entities.User;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class MainWindow extends JFrame {
    private  UserController userController;

    private TicketController ticketController;

    public MainWindow(UserController userController, TicketController ticketController) {

        this.userController = new UserController();
        this.ticketController = ticketController;

        // Característica do JFrame
        setTitle("Tickets Manager");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Característica do JPanel
        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS)); // Layout vertical
        painel.setAlignmentX(Component.CENTER_ALIGNMENT);

        //Icone dos botoes
        ImageIcon iconeUser = new ImageIcon("C:\\Users\\th650\\IdeaProjects\\ticketsManager\\src\\main\\java\\com\\example\\ticketsManager\\view\\resources\\user.png");
        ImageIcon iconeTicket = new ImageIcon("C:\\Users\\th650\\IdeaProjects\\ticketsManager\\src\\main\\java\\com\\example\\ticketsManager\\view\\resources\\ticket.png");
        ImageIcon iconeRelatorio = new ImageIcon("C:\\Users\\th650\\IdeaProjects\\ticketsManager\\src\\main\\java\\com\\example\\ticketsManager\\view\\resources\\report.png");

        // Características dos botões
        JButton jbUser = new JButton(iconeUser);
        JButton jbTickets = new JButton(iconeTicket);
        JButton jbRelatorio = new JButton(iconeRelatorio);
        // Adicionando os botões ao painel
        JButton[] botoes = {jbUser,jbTickets,jbRelatorio};

        for (JButton botao: botoes){
            botao.setAlignmentX(Component.LEFT_ALIGNMENT); // Centraliza os botões
            botao.setAlignmentY(Component.CENTER_ALIGNMENT);
            botao.setMaximumSize(new Dimension(200, 100)); // Tamanho máximo
            botao.setMinimumSize(new Dimension(100, 100)); // Tamanho mínimo
            botao.setFont(new Font("Arial", Font.BOLD, 16));
            botao.setFocusPainted(false); // Tira o foco feio quando clica
            botao.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
            painel.add(botao); // Adiciona os botões ao JPanel
            painel.add(Box.createVerticalStrut(25)); // Espaçamento vertical
        }
        jbRelatorio.addActionListener(e -> emitirRelatorio());
        jbUser.addActionListener(e -> {
            Usuario usuario = new Usuario(this,userController,ticketController);
            usuario.setVisible(true);
            this.setVisible(false);
            this.dispose();
        });
        jbTickets.addActionListener(e ->{
            Ticket ticket = new Ticket(this,userController,ticketController);
            ticket.setVisible(true);
            this.setVisible(false);
            this.dispose();
        });

        add(painel);
    }
    public void adicionarUsuário() {
        // Criar o JFrame
        JFrame frame = new JFrame("Tickets Manager");
        frame.setSize(700, 500); // Tamanho fixo
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel painel = new JPanel();
        painel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 2, 2);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Nome
        painel.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 2;
        JTextField textNome = new JTextField(20);
        painel.add(textNome, gbc);

        // CPF
        gbc.gridx = 0;
        gbc.gridy++;
        painel.add(new JLabel("CPF:"), gbc);
        gbc.gridx = 2;
        JTextField textCpf = new JTextField(20);
        painel.add(textCpf, gbc);

        // Status
        gbc.gridx = 0;
        gbc.gridy++;
        painel.add(new JLabel("Status:"), gbc);
        gbc.gridx = 2;
        String[] statusOptions = {"A", "I"};
        JComboBox<String> statusComboBox = new JComboBox<>(statusOptions);
        painel.add(statusComboBox, gbc);

        // Botões
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 5;
        gbc.anchor = GridBagConstraints.CENTER;

        JPanel botoesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        // Botão Salvar
        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(e -> {
            String nome = textNome.getText().trim();
            String cpf = textCpf.getText().trim();
            String status = (String) statusComboBox.getSelectedItem();

            if (nome.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Nome é obrigatório.");
                return;
            }

            if (cpf.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "CPF é obrigatório.");
                return;
            }

            CreateUserDTO dto = new CreateUserDTO();
            dto.setNome(nome);
            dto.setCpf(cpf);
            dto.setSituacaoUsuario(status);

            userController.createUser(dto);
            frame.dispose();
        });

        // Botão Voltar
        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(e -> {
            frame.dispose(); // Fecha a tela atual e volta à tela anterior
        });

        botoesPanel.add(btnSalvar);
        botoesPanel.add(btnVoltar);

        painel.add(botoesPanel, gbc);

        frame.add(painel);
        frame.setVisible(true);
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