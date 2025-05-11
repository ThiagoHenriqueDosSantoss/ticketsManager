package com.example.ticketsManager.view;

import com.example.ticketsManager.controller.TicketController;
import com.example.ticketsManager.controller.UserController;
import com.example.ticketsManager.dto.CreateUserDTO;
import com.example.ticketsManager.dto.UpdateUserDTO;
import com.example.ticketsManager.entities.User;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Usuario extends JFrame {

    private UserController userController;

    private TicketController ticketController;

    public Usuario(MainWindow mainWindow, UserController userController, TicketController ticketController){
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

        ImageIcon iconeAddUser = new ImageIcon("C:\\Users\\th650\\IdeaProjects\\ticketsManager\\src\\main\\java\\com\\example\\ticketsManager\\view\\resources\\user-add.png");
        ImageIcon iconeEditUser = new ImageIcon("C:\\Users\\th650\\IdeaProjects\\ticketsManager\\src\\main\\java\\com\\example\\ticketsManager\\view\\resources\\user-edit.png");
        ImageIcon iconeListUser = new ImageIcon("C:\\Users\\th650\\IdeaProjects\\ticketsManager\\src\\main\\java\\com\\example\\ticketsManager\\view\\resources\\user-list.png");
        ImageIcon iconeVoltar = new ImageIcon("C:\\Users\\th650\\IdeaProjects\\ticketsManager\\src\\main\\java\\com\\example\\ticketsManager\\view\\resources\\back.png");

        // Características dos botões
        JButton jbCriarUsuarios = new JButton(iconeAddUser);
        JButton jbEditarUsuario = new JButton(iconeEditUser);
        JButton jbListarUsuario= new JButton(iconeListUser);
        JButton jbVoltar = new JButton(iconeVoltar);

        // Adicionando os botões ao painel
        JButton[] botoes = {jbCriarUsuarios,jbEditarUsuario,jbListarUsuario,jbVoltar};

        for (JButton botao: botoes){
            botao.setAlignmentX(Component.LEFT_ALIGNMENT); // Centraliza os botões
            botao.setMaximumSize(new Dimension(200, 100)); // Tamanho máximo
            botao.setMinimumSize(new Dimension(100, 100)); // Tamanho mínimo
            botao.setFont(new Font("Arial", Font.BOLD, 16));

            botao.setFocusPainted(false); // Tira o foco feio quando clica
            botao.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
            painel.add(botao); // Adiciona os botões ao JPanel
            painel.add(Box.createVerticalStrut(25)); // Espaçamento vertical
        }
        jbCriarUsuarios.addActionListener(e ->adicionarUsuário());
        jbListarUsuario.addActionListener(e -> listarUsuario());
        jbEditarUsuario.addActionListener(e -> editarUsuario());

        jbVoltar.addActionListener(e -> {new MainWindow(userController,ticketController);
            mainWindow.setVisible(true);
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
    public void editarUsuario() {
        String idStr = JOptionPane.showInputDialog(null, "Informe o ID do usuário a editar:");
        if (idStr == null || !idStr.matches("\\d+")) {
            JOptionPane.showMessageDialog(null, "ID inválido!");
            return;
        }
        Long id = Long.parseLong(idStr);

        String nome = null;
        String cpf = null;
        String status = null;

        int opcaoNome = JOptionPane.showConfirmDialog(null, "Deseja informar o nome do usuário?", "Nome", JOptionPane.YES_NO_OPTION);
        if (opcaoNome == JOptionPane.YES_OPTION) {
            nome = JOptionPane.showInputDialog("Informe o nome completo:");
            while (nome == null || nome.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nome é obrigatório.");
                nome = JOptionPane.showInputDialog("Informe o nome completo:");
            }
        }


        int opcaoCpf = JOptionPane.showConfirmDialog(null, "Deseja informar o CPF do usuário?", "CPF", JOptionPane.YES_NO_OPTION);
        if (opcaoCpf == JOptionPane.YES_OPTION) {
            cpf = JOptionPane.showInputDialog("Informe o CPF:");
            while (cpf == null || cpf.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "CPF é obrigatório.");
                cpf = JOptionPane.showInputDialog("Informe o CPF:");
            }
        }

        int opcaoStatus = JOptionPane.showConfirmDialog(null, "Deseja informar a situação do usuário (A/I)?", "Situação", JOptionPane.YES_NO_OPTION);
        if (opcaoStatus == JOptionPane.YES_OPTION) {
            String[] opcoesStatus = {"A", "I"};
            status = (String) JOptionPane.showInputDialog(null, "Selecione a situação do usuário:", "Situação",
                    JOptionPane.QUESTION_MESSAGE, null, opcoesStatus, opcoesStatus[0]);
        }

        UpdateUserDTO dto = new UpdateUserDTO();
        dto.setNome(nome);
        dto.setCpf(cpf);
        dto.setSituacaoUsuario(status);

        userController.updateUser(id,dto);
    }
    public void listarUsuario() {
        List<User> usuarios = userController.listUser();

        if (usuarios == null || usuarios.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum usuário encontrado.");
            return;
        }

        StringBuilder lista = new StringBuilder();
        for (User user : usuarios) {
            lista.append("ID: ").append(user.getIdUser()).append("\n");
            lista.append("Nome: ").append(user.getNome()).append("\n");
            lista.append("CPF: ").append(user.getCpf()).append("\n");
            lista.append("Situação: ").append(user.getSituacaoUsuario()).append("\n");
            lista.append("-------------------------\n");
        }

        JTextArea textArea = new JTextArea(lista.toString());
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(400, 300));

        JOptionPane.showMessageDialog(null, scrollPane, "Lista de Usuários", JOptionPane.INFORMATION_MESSAGE);
    }
}
