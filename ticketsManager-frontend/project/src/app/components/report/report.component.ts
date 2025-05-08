import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TicketService } from '../../services/ticket.service';
import { UserService } from '../../services/user.service';
import { Ticket } from '../../models/ticket.model';
import { User } from '../../models/user.model';

@Component({
  selector: 'app-report',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="content">
      <h2>Relatório de Tickets</h2>
      <table>
        <thead>
          <tr>
            <th>Usuário</th>
            <th>Quantidade</th>
          </tr>
        </thead>
        <tbody>
          <tr *ngFor="let ticket of tickets">
            <td>{{ getUserName(ticket.userId) }}</td>
            <td>{{ ticket.quantity }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  `
})
export class ReportComponent {
  tickets: Ticket[] = [];
  users: User[] = [];

  constructor(
    private ticketService: TicketService,
    private userService: UserService
  ) {
    this.ticketService.getTickets().subscribe(tickets => {
      this.tickets = tickets;
    });
    this.userService.getUsers().subscribe(users => {
      this.users = users;
    });
  }

  getUserName(userId: number): string {
    const user = this.users.find(u => u.id === userId);
    return user ? user.name : 'Desconhecido';
  }
}