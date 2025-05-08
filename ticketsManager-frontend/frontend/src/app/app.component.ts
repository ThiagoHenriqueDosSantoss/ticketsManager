import { Component } from '@angular/core';
import { UserComponent } from '../user/user.component';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [UserComponent],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Ticket Manager';


  showModal = false;

  openModal() {
    this.showModal = true;
  }

  fecharModalUsuario() {
    this.showModal = false;
  }
}