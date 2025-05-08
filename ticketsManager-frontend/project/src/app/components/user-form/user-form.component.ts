import { Component } from '@angular/core';
import { UserService } from '../../services/user.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
@Component({
  selector: 'app-user-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  template: `
    <div class="content">
      <h2>Criar Usuário</h2>

      <div *ngIf="successMessage" style="color: green; margin-bottom: 10px;">
        {{ successMessage }}
      </div>
      <div *ngIf="errorMessage" style="color: red; margin-bottom: 10px;">
        {{ errorMessage }}
      </div>

      <div class="form-group">
        <label>Nome:</label>
        <input [(ngModel)]="name" type="text" placeholder="Digite o nome">
      </div>

      <div class="form-group">
        <label>CPF:</label>
        <input [(ngModel)]="cpf" type="text" placeholder="Digite o CPF">
      </div>

      <div class="form-group">
        <label>Status:</label>
        <select [(ngModel)]="status">
          <option value="A">Ativo</option>
          <option value="I">Inativo</option>
        </select>
      </div>

      <button (click)="createUser()">Criar Usuário</button>
    </div>
  `
})
export class UserFormComponent {
  name = '';
  cpf = '';
  status: 'active' | 'inactive' = 'active';
  successMessage = '';
  errorMessage = '';

  constructor(private userService: UserService) {}

  createUser() {
    if (this.name && this.cpf && this.status) {
      const newUser = {
        name: this.name,
        cpf: this.cpf,
        status: this.status
      };

      this.userService.addUser(newUser)
        .subscribe({
          next: (response) => {
            this.successMessage = 'Usuário criado com sucesso!';
            this.errorMessage = '';
            this.resetForm();
          },
          error: (err) => {
            this.errorMessage = 'Erro ao criar usuário. Tente novamente.';
            this.successMessage = '';
            console.error(err);
          }
        });
    } else {
      this.errorMessage = 'Preencha todos os campos.';
    }
  }

  resetForm() {
    this.name = '';
    this.cpf = '';
    this.status = 'active';
  }
}