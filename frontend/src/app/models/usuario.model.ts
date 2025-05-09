export interface Usuario {
  id?: number;
  nome: string;
  cpf: string;
  status: 'A' | 'I';
  dataCriacao?: string;
  dataAtualizacao?: string;
}