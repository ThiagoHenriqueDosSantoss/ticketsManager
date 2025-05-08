export interface User {
  id: number;
  name: string;
  cpf: string;
  status: 'active' | 'inactive';
}