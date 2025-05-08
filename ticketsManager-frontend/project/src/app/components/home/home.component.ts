import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="content">
      <div class="home-container">
        <h1>Bem-vindo ao Tickets</h1>
        <p>Utilize o menu lateral para navegar entre as funcionalidades:</p>
        <ul>
          <li>Criar e gerenciar usuários</li>
          <li>Criar tickets para usuários</li>
          <li>Visualizar relatório de tickets</li>
        </ul>
      </div>
    </div>
  `
})
export class HomeComponent {}