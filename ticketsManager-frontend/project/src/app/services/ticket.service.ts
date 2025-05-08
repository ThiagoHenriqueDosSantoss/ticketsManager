import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { Ticket } from '../models/ticket.model';

@Injectable({
  providedIn: 'root'
})
export class TicketService {
  private tickets: Ticket[] = [];
  private currentId = 1;
  private ticketsSubject = new BehaviorSubject<Ticket[]>([]);

  getTickets() {
    return this.ticketsSubject.asObservable();
  }

  addTicket(ticket: Omit<Ticket, 'id'>) {
    const newTicket = { ...ticket, id: this.currentId++ };
    this.tickets.push(newTicket);
    this.ticketsSubject.next([...this.tickets]);
  }
}