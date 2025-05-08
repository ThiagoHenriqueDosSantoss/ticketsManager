import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { TicketService } from '../../services/ticket.service';
import { UserService } from '../../services/user.service';
import { User } from '../../models/user.model';

@Component({
  selector: 'app-ticket-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  template: `
    <div class="content">
  <h2>Criar Ticket</h2>

  <div *ngIf="successMessage" style="color: green; margin-bottom: 10px;">
    {{ successMessage }}
  </div>
  <div *ngIf="errorMessage" style="color: red; margin-bottom: 10px;">
    {{ errorMessage }}
  </div>

  <div class="form-group">
    <label>Usuário:</label>
    <select [(ngModel)]="userId">
      <option value="">Selecione um usuário</option>
      <option *ngFor="let user of users" [value]="user.id">
        {{ user.name }}
      </option>
    </select>
  </div>
  <div class="form-group">
    <label>Quantidade:</label>
    <input [(ngModel)]="quantity" type="number" min="1">
  </div>
  <button (click)="createTicket()">Criar Ticket</button>
</div>
  `
})
export class TicketFormComponent {
  users: User[] = [];
  userId: number | null = null;
  quantity = 1;
  successMessage = '';
  errorMessage = '';

  constructor(
    private ticketService: TicketService,
    private userService: UserService
  ) {
    this.userService.getUsers().subscribe(users => {
      this.users = users;
    });
  }

  createTicket() {
  if (this.userId && this.quantity > 0) {
    this.ticketService.addTicket({
      userId: this.userId,
      quantity: this.quantity
    }).subscribe({
      next: (response) => {
        this.successMessage = 'Ticket criado com sucesso!';
        this.errorMessage = '';
        this.resetForm();
      },
      error: (err) => {
        this.errorMessage = 'Erro ao criar ticket. Tente novamente.';
        this.successMessage = '';
        console.error(err);
      }
    });
  }
}


  resetForm() {
    this.userId = null;
    this.quantity = 1;
  }
}