import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UsuariosComponent } from './usuarios/usuarios.component';
import { HomeComponent } from '../home/home.component';

export const routes: Routes = [
    { path: '', component: HomeComponent },
    {
    path: 'usuarios', component: UsuariosComponent
    }
  ];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }