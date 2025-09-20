import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private baseUrl = 'http://localhost:8080/api/auth';

  constructor(private http: HttpClient) {}

  signin(data: { username: string; password: string }) {
    return this.http.post(`${this.baseUrl}/signin`, data);
  }

  // signup(data: { username: string; email: string; password: string; role: string[] }) {
  //   return this.http.post(`${this.baseUrl}/signup`, data);
  // }

  storeToken(token: string) {
    localStorage.setItem('token', token);
  }

  getToken() {
    return localStorage.getItem('token');
  }

  // logout() {
  //   localStorage.removeItem('token');
  // }

  isLoggedIn(): boolean {
    return !!this.getToken();
  }

  login(credentials: any) {
    return this.http.post(`${this.baseUrl}/signin`, credentials);
  }

  signup(data: any) {
    return this.http.post(`${this.baseUrl}/signup`, data);
  }

  getCurrentUser() {
    return this.http.get(`${this.baseUrl}/current-user`);
  }

  logout() {
    return this.http.post(`${this.baseUrl}/logout`, {});
  }
}

