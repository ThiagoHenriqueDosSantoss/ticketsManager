package com.example.ticketsManager.view;

import com.example.ticketsManager.controller.UserController;
import com.example.ticketsManager.dto.CreateUserDTO;
import com.example.ticketsManager.entities.User;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    private  UserController userController;

    public MainWindow(UserController userController) {

        this.userController = userController;

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
        JButton jbCriarUsuarios = new JButton("Criar Usuário");
        JButton jbEditarUsuario = new JButton("Editar Usuário");
        JButton jbCriarTicket = new JButton("Criar Ticket");
        JButton jbEditarTicket = new JButton("Editar Ticket");
        JButton jbRelatorio = new JButton("Relatório");

        // Adicionando os botões ao painel
        JButton[] botoes = {jbCriarUsuarios,jbEditarUsuario,jbCriarTicket,jbEditarTicket,jbRelatorio};

        for (JButton botao: botoes){
            botao.setAlignmentX(Component.LEFT_ALIGNMENT); // Centraliza os botões
            botao.setMaximumSize(new Dimension(100, 50)); // Tamanho máximo
            botao.setMinimumSize(new Dimension(100, 50)); // Tamanho mínimo
            botao.setFont(new Font("Arial", Font.BOLD, 16));

            botao.setFocusPainted(false); // Tira o foco feio quando clica
            botao.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
            painel.add(botao); // Adiciona os botões ao JPanel
            painel.add(Box.createVerticalStrut(25)); // Espaçamento vertical
        }
        jbCriarTicket.addActionListener(e ->adicionarUsuário());
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
}