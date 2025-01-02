import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  user: string | null = null;
  role: string | null = 'user';

  constructor() { }

  setUser(user: string): void {
    this.user = user;
  }

  getUser(): string | null {
    return this.user;
  }

  setRole(role: string): void {
    this.role = role;
  }

  getRole(): string | null {
    return this.role;
  }

  clearAuth(): void {
    this.user = null;
    this.role = null;
  }

  toggleRole(): void {
    this.role = this.role === 'editor' ? 'user' : 'editor';
  }
}
