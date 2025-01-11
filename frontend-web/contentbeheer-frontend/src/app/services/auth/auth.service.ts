import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  user: string | null = null;
  role: string | null = 'user';
  roleKey = 'userRole';
  userKey = 'username';

  constructor() {
    this.role = localStorage.getItem(this.roleKey) || 'user';
    if (this.role === 'user') {
      this.user = localStorage.getItem(this.userKey);
    }
  }

  setUser(user: string): void {
    if (this.role === 'user') {
      this.user = user;
      localStorage.setItem(this.userKey, user);
    }
  }

  getUser(): string | null {
    return this.user;
  }

  setRole(role: string): void {
    this.role = role;
    localStorage.setItem(this.roleKey, role);
    if (role !== 'user') {
      localStorage.removeItem(this.userKey);
      this.user = null;
    }
  }

  getRole(): string | null {
    return this.role;
  }

  clearAuth(): void {
    this.user = null;
    this.role = null;
    localStorage.removeItem(this.roleKey);
    localStorage.removeItem(this.userKey);
  }

  toggleRole(): void {
    this.role = this.role === 'editor' ? 'user' : 'editor';
    localStorage.setItem(this.roleKey, this.role);
    if (this.role === 'editor') {
      localStorage.removeItem(this.userKey);
      this.user = null;
    }
  }
}
