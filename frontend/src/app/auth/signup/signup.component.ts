import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth.service';


@Component({
  selector: 'app-signup',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent {
  username = '';
  email = '';
  password = '';
  confirmPassword = '';
  role = ['USER'];

  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit(): void {}

  signup() {
    if (this.password !== this.confirmPassword) {
      alert('Passwords do not match!');
      return;
    }
  
    const payload = {
      username: this.username,
      email: this.email,
      password: this.password,
      role: this.role
    };
  
    this.authService.signup(payload).subscribe({
      next: (res) => {
        alert('Signup successful! Please login.');
        this.router.navigate(['/login']);
      },
      error: (err) => {
        alert(err.error?.message || 'Signup failed!');
      }
    });
  }

}
