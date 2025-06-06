package com.example.ticketsManager.view;

import com.example.ticketsManager.controller.TicketController;
import com.example.ticketsManager.controller.UserController;
import com.example.ticketsManager.dto.UserDTO.CreateUserDTO;
import com.example.ticketsManager.dto.UserDTO.UpdateUserDTO;
import com.example.ticketsManager.entities.User;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Usuario extends JFrame {

    private UserController userController;

    private TicketController ticketController;

    public Usuario(MainWindow mainWindow, UserController userController, TicketController ticketController){
        this.userController = userController;
        ImageIcon iconeTicketManager = new ImageIcon(getClass().getResource("/images/ticket-manager.png"));

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
        ImageIcon iconeAddUser = new ImageIcon(getClass().getResource("/images/user-add.png"));
        ImageIcon iconeEditUser = new ImageIcon(getClass().getResource("/images/user-edit.png"));
        ImageIcon iconeListUser = new ImageIcon(getClass().getResource("/images/user-list.png"));
        ImageIcon iconeVoltar = new ImageIcon(getClass().getResource("/images/back.png"));

        // Botões
        JButton jbCriarUsuarios = new JButton("Cadastrar Usuário",iconeAddUser);
        JButton jbEditarUsuario = new JButton("Editar Usuário",iconeEditUser);
        JButton jbListarUsuario = new JButton("Listar Usuários",iconeListUser);
        JButton jbVoltar = new JButton("Voltar",iconeVoltar);

        JButton[] botoes = {jbCriarUsuarios, jbEditarUsuario, jbListarUsuario, jbVoltar};

        for (JButton botao : botoes) {
            botao.setPreferredSize(new Dimension(200, 100));
            botao.setMaximumSize(new Dimension(200, 100));
            botao.setMinimumSize(new Dimension(100, 100));

            botao.setFont(new Font("Arial", Font.BOLD, 12));
            botao.setFocusPainted(false);
            botao.setBackground(new Color(249, 249, 249));
            botao.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
            botao.setOpaque(true);
            botao.setContentAreaFilled(true);

            painelBotoes.add(Box.createHorizontalStrut(15)); // Espaço entre os botões
            painelBotoes.add(botao);
        }

        // Ações dos botões
        jbCriarUsuarios.addActionListener(e -> createUser());
        jbEditarUsuario.addActionListener(e -> updateUser());
        jbListarUsuario.addActionListener(e -> listUser());
        jbVoltar.addActionListener(e -> {
            MainWindow mainWindow1 = new MainWindow(userController, ticketController);
            mainWindow1.setVisible(true);
            this.setVisible(false);
            this.dispose();
        });

        // Organização no painel principal
        painel.add(Box.createVerticalGlue());
        painel.add(painelBotoes);
        painel.add(Box.createVerticalGlue());

        // Adiciona ao JFrame
        add(painel);

    }
    public void createUser() {
        try {
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
            MaskFormatter cpfMask = new MaskFormatter("###.###.###-##");
            cpfMask.setPlaceholderCharacter('_');
            gbc.gridx = 0;
            gbc.gridy++;
            painel.add(new JLabel("CPF:"), gbc);
            gbc.gridx = 2;
            JFormattedTextField textCpf = new JFormattedTextField(cpfMask);
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
            frame.dispose();
        });

        botoesPanel.add(btnSalvar);
        botoesPanel.add(btnVoltar);

        painel.add(botoesPanel, gbc);

        frame.add(painel);
        frame.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void updateUser() {
        try {
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
                MaskFormatter cpfMask = new MaskFormatter("###.###.###-##");
                cpfMask.setPlaceholderCharacter('_');
                JFormattedTextField cpfField = new JFormattedTextField(cpfMask);

                int resultado = JOptionPane.showConfirmDialog(null, cpfField, "Informe o CPF:", JOptionPane.OK_CANCEL_OPTION);

                while (resultado == JOptionPane.OK_OPTION && cpfField.getText().contains("_")) {
                    JOptionPane.showMessageDialog(null, "CPF é obrigatório e deve estar completo.");
                    resultado = JOptionPane.showConfirmDialog(null, cpfField, "Informe o CPF:", JOptionPane.OK_CANCEL_OPTION);
                }

                if (resultado == JOptionPane.OK_OPTION) {
                    cpf = cpfField.getText();
                    System.out.println("CPF informado: " + cpf);
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

            userController.updateUser(id, dto);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void listUser() {
        try {
            List<User> usuarios = userController.listUser();

            if (usuarios == null || usuarios.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nenhum usuário encontrado.");
                return;
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            StringBuilder lista = new StringBuilder();

            for (User user : usuarios) {
                lista.append("ID: ").append(user.getIdUser()).append("\n");
                lista.append("Nome: ").append(user.getNome()).append("\n");
                lista.append("CPF: ").append(user.getCpf()).append("\n");
                lista.append("Situação: ").append(user.getSituacaoUsuario()).append("\n");
                lista.append("Data Criação: ").append(user.getDataCriacao().format(formatter)).append("\n");

                lista.append("Data Atualização: ");
                if (user.getDataAlteracao() != null) { //verifica se a data é null, para evitar nullpointer
                    lista.append(user.getDataAlteracao().format(formatter));
                } else {
                    lista.append("N/A");
                }
                lista.append("\n");
                lista.append("-------------------------\n");
            }

            JTextArea textArea = new JTextArea(lista.toString());
            textArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(400, 300));

            JOptionPane.showMessageDialog(null, scrollPane, "Lista de Usuários", JOptionPane.INFORMATION_MESSAGE);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
