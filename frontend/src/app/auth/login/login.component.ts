import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { Router, RouterLink } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent {
  username: string = '';
  email: string = '';
  password: string = '';
  loginErrorMessage: string = '';


  constructor(private auth: AuthService, private router: Router) {}
  
  ngOnInit() {
    if (this.auth.isLoggedIn()) {
      this.router.navigate(['/products']);
    }
  }

  login() {
    console.log('Email:', this.email);
    console.log('Password:', this.password);
    console.log('Username:', this.username);
  
    this.auth.login({ username: this.username, password: this.password }).subscribe({
      next: (res: any) => {
        console.log('Login successful:', res);
        localStorage.setItem('token', res.jwtToken);
        localStorage.setItem('username', res.username);
        localStorage.setItem('roles', JSON.stringify(res.roles));
        localStorage.setItem('userId', JSON.stringify(res.id));
        this.router.navigate(['/products']);
        window.location.reload();
      },
      error: (err) => {
        console.error('Login error:', err);
        if (err.status === 404 || err.error?.message === 'Bad credentials') {
          this.loginErrorMessage = 'Invalid username or password.';
        } else {
          this.loginErrorMessage = 'Something went wrong. Please try again.';
        }
      }      
    });
  }
  

  // login() {
  //   console.log('Email:', this.email);
  //   console.log('PasswordL', this.password);
  //   console.log('Username:', this.username);

  //   this.auth.login({ username: this.username, password: this.password }).subscribe((res: any) => {
  //     console.log('Login successful:', res);
  //     localStorage.setItem('token', res.token);

  //     // Store JWT and username
  //   localStorage.setItem('token', res.jwtToken);
  //   localStorage.setItem('username', res.username);

  //   // Optional: Store roles if needed
  //   localStorage.setItem('roles', JSON.stringify(res.roles));

  //   // Navigate to home or products
  //   this.router.navigate(['/products']);
  //   window.location.reload();
  //   });
  // }
}