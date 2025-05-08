import { Component } from '@angular/core';
import { bootstrapApplication } from '@angular/platform-browser';
import { provideRouter, RouterOutlet, RouterLink, Route } from '@angular/router';
import { UserFormComponent } from './app/components/user-form/user-form.component';
import { TicketFormComponent } from './app/components/ticket-form/ticket-form.component';
import { ReportComponent } from './app/components/report/report.component';
import { HomeComponent } from './app/components/home/home.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, RouterLink],
  template: `
    <div class="sidebar">
      <button routerLink="/">Home</button>
      <button routerLink="/users">Criar Usuário</button>
      <button routerLink="/tickets">Criar Ticket</button>
      <button routerLink="/report">Relatório</button>
    </div>
    <router-outlet></router-outlet>
  `
})
export class App {
  name = 'Angular';
}

const routes: Route[] = [
  { path: '', component: HomeComponent },
  { path: 'users', component: UserFormComponent },
  { path: 'tickets', component: TicketFormComponent },
  { path: 'report', component: ReportComponent }
];

bootstrapApplication(App, {
  providers: [
    provideRouter(routes)
  ]
});