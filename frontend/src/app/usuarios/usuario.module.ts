import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { UsuariosComponent } from './usuarios.component';

@NgModule({
  declarations: [UsuariosComponent],
  imports: [
    CommonModule,
  ],
  exports: [UsuariosComponent]
})
export class UsuarioModule {}