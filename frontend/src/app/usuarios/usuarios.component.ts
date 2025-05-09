// src/app/usuario/usuario.component.ts
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Usuario } from '../models/usuario.model';
import { UsuarioService } from './usuarios.service';

@Component({
  selector: 'app-usuario',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule
  ],
  templateUrl:'./usuarios.component.html',
})
export class UsuariosComponent implements OnInit {
  formUsuario!: FormGroup;
  usuarios: Usuario[] = [];
  
  constructor(
    private fb: FormBuilder,
    private usuarioService: UsuarioService
  ) {}
  
  ngOnInit(): void {
    // Inicialize o formulário com valores padrão
    this.formUsuario = this.fb.group({
      nome: ['', Validators.required],
      cpf: ['', [Validators.required, Validators.minLength(11), Validators.maxLength(14)]],
      status: ['A', Validators.required]  // Usando 'A' como padrão
    });
    
    // Carregar usuários existentes
    this.carregarUsuarios();
  }
  
  carregarUsuarios(): void {
    this.usuarioService.listarUsuarios().subscribe({
      next: (data) => {
        this.usuarios = data;
      },
      error: (erro) => {
        console.error('Erro ao carregar usuários', erro);
      }
    });
  }
  
  cadastrar(): void {
    if (this.formUsuario.valid) {
      const usuario: Usuario = this.formUsuario.value;
      
      this.usuarioService.cadastrarUsuario(usuario).subscribe({
        next: (usuarioCadastrado) => {
          console.log('Usuário cadastrado com sucesso', usuarioCadastrado);
          
          // Adicionar o usuário à lista ou recarregar a lista
          this.usuarios.push(usuarioCadastrado);
          
          // Resetar o formulário
          this.formUsuario.reset({
            status: 'A' 
          });
          
          // Opcional: Mostrar mensagem de sucesso
          alert('Usuário cadastrado com sucesso!');
        },
        error: (erro) => {
          console.error('Erro ao cadastrar usuário', erro);
          alert('Erro ao cadastrar usuário. Verifique o console para mais detalhes.');
        }
      });
    } else {
      // Marcar todos os campos como touched para mostrar validações
      Object.keys(this.formUsuario.controls).forEach(campo => {
        const controle = this.formUsuario.get(campo);
        controle?.markAsTouched();
      });
      
      alert('Por favor, preencha todos os campos obrigatórios corretamente.');
    }
  }
  
  // Métodos de utilidade para exibição
  getStatusText(status: string): string {
    return status === 'A' ? 'A' : 'I';
  }
}