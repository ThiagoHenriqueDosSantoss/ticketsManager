import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms'; // Certifique-se de que FormsModule foi importado

@Component({
  selector: 'app-user',
  standalone: true,
  imports: [CommonModule, FormsModule], // Certifique-se de que o FormsModule está na lista de imports
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent {
  @Input() showModal = false;
  @Output() fecharModal = new EventEmitter<void>();

  nome = '';
  cpf = '';
  situacao = 'A';

  salvarUsuario() {
    const usuario = {
      nome: this.nome,
      cpf: this.cpf,
      situacao: this.situacao,
    };
    console.log('Usuário salvo:', usuario);
    this.fecharModal.emit();
  }
}