import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { User } from '../models/user.model';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private users: User[] = [];
  private currentId = 1;
  private usersSubject = new BehaviorSubject<User[]>([]);

  getUsers() {
    return this.usersSubject.asObservable();
  }

  addUser(user: Omit<User, 'id'>) {
    const newUser = { ...user, id: this.currentId++ };
    this.users.push(newUser);
    this.usersSubject.next([...this.users]);
  }

  updateUser(user: User) {
    const index = this.users.findIndex(u => u.id === user.id);
    if (index !== -1) {
      this.users[index] = user;
      this.usersSubject.next([...this.users]);
    }
  }

  getUserById(id: number) {
    return this.users.find(u => u.id === id);
  }
}