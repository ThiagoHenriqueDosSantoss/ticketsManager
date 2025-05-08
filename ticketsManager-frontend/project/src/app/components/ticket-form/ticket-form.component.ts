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
      });
      this.resetForm();
    }
  }

  resetForm() {
    this.userId = null;
    this.quantity = 1;
  }
}