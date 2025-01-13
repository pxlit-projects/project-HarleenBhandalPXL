import { TestBed } from '@angular/core/testing';
import { AuthService } from './auth.service';

describe('AuthService', () => {
  let service: AuthService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AuthService);
    localStorage.clear();
  });

  afterEach(() => {
    localStorage.clear();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should set and get user', () => {
    service.setUser('testUser');
    expect(service.getUser()).toBe('testUser');
  });

  it('should set and get role', () => {
    service.setRole('editor');
    expect(service.getRole()).toBe('editor');
  });

  it('should clear auth', () => {
    service.setUser('testUser');
    service.setRole('editor');
    service.clearAuth();
    expect(service.getUser()).toBeNull();
    expect(service.getRole()).toBeNull();
  });

  it('should toggle role', () => {
    service.setRole('user');
    service.toggleRole();
    expect(service.getRole()).toBe('editor');
    service.toggleRole();
    expect(service.getRole()).toBe('user');
  });

  it('should initialize with role from localStorage', () => {
    localStorage.setItem(service.roleKey, 'editor');
    const newService = new AuthService();
    expect(newService.getRole()).toBe('editor');
  });

  it('should initialize with user from localStorage if role is user', () => {
    localStorage.setItem(service.roleKey, 'user');
    localStorage.setItem(service.userKey, 'testUser');
    const newService = new AuthService();
    expect(newService.getUser()).toBe('testUser');
  });
});
