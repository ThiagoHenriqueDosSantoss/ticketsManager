import { Component } from '@angular/core';
import { UserComponent } from '../user/user.component';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [UserComponent],  // Importando UserComponent diretamente
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Ticket Manager';

  // Controla a visibilidade do modal
  showModal = false;

  openModal() {
    this.showModal = true;  // Exibe o modal
  }

  fecharModalUsuario() {
    this.showModal = false;  // Fecha o modal
  }
}