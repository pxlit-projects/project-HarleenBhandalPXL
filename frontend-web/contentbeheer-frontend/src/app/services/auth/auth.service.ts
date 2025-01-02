import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  user: string | null = null;
  role: string | null = 'user';
  roleKey = 'userRole';

  constructor() {
    this.role = localStorage.getItem(this.roleKey) || 'user';
  }

  setUser(user: string): void {
    this.user = user;
  }

  getUser(): string | null {
    return this.user;
  }

  setRole(role: string): void {
    this.role = role;
    localStorage.setItem(this.roleKey, role);
  }

  getRole(): string | null {
    return this.role;
  }

  clearAuth(): void {
    this.user = null;
    this.role = null;
    localStorage.removeItem(this.roleKey);
  }

  toggleRole(): void {
    this.role = this.role === 'editor' ? 'user' : 'editor';
    localStorage.setItem(this.roleKey, this.role);
  }
}
