import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { UserService } from '../../services/user.service';
import { User } from '../../models/user.model';

@Component({
  selector: 'app-user-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  template: `
    <div class="content">
      <h2>{{ editingUser ? 'Editar Usuário' : 'Criar Usuário' }}</h2>
      <div class="form-group">
        <label>Nome:</label>
        <input [(ngModel)]="name" type="text">
      </div>
      <div class="form-group">
        <label>CPF:</label>
        <input [(ngModel)]="cpf" type="text">
      </div>
      <div class="form-group">
        <label>Status:</label>
        <select [(ngModel)]="status">
          <option value="active">Ativo</option>
          <option value="inactive">Inativo</option>
        </select>
      </div>
      <button (click)="saveUser()">{{ editingUser ? 'Atualizar' : 'Criar' }}</button>

      <table>
        <thead>
          <tr>
            <th>Nome</th>
            <th>CPF</th>
            <th>Status</th>
            <th>Ação</th>
          </tr>
        </thead>
        <tbody>
          <tr *ngFor="let user of users">
            <td>{{ user.name }}</td>
            <td>{{ user.cpf }}</td>
            <td>{{ user.status === 'active' ? 'Ativo' : 'Inativo' }}</td>
            <td>
              <button (click)="editUser(user)">Editar</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  `
})
export class UserFormComponent {
  users: User[] = [];
  name = '';
  cpf = '';
  status: 'active' | 'inactive' = 'active';
  editingUser: User | null = null;

  constructor(private userService: UserService) {
    this.userService.getUsers().subscribe(users => {
      this.users = users;
    });
  }

  saveUser() {
    if (this.editingUser) {
      this.userService.updateUser({
        ...this.editingUser,
        name: this.name,
        cpf: this.cpf,
        status: this.status
      });
      this.editingUser = null;
    } else {
      this.userService.addUser({
        name: this.name,
        cpf: this.cpf,
        status: this.status
      });
    }
    this.resetForm();
  }

  editUser(user: User) {
    this.editingUser = user;
    this.name = user.name;
    this.cpf = user.cpf;
    this.status = user.status;
  }

  resetForm() {
    this.name = '';
    this.cpf = '';
    this.status = 'active';
  }
}