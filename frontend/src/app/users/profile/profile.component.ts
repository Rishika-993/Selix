import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent {
  private http = inject(HttpClient);
  private router = inject(Router);

  user: any = {};
  updatedUser: any = {};
  loading = true;

  successMessage = '';
  errorMessage = '';

  addresses: any[] = [];
  newAddress: any = {
    street: '',
    buildingName: '',
    city: '',
    state: '',
    country: '',
    pincode: ''
  };

  showDeleteConfirmation = false;
  deleteAddressId: any = null;

  ngOnInit(): void {
    this.fetchCurrentUser();
    this.fetchAddresses();
  }

  fetchCurrentUser() {
    this.http.get<any>('http://localhost:8080/api/users').subscribe({
      next: (res) => {
        this.user = res;
        this.updatedUser = {
          userName: res.userName,
          email: res.email
        };
        this.loading = false;
      },
      error: (err) => {
        console.error('Failed to fetch user', err);
        this.errorMessage = 'Failed to load profile';
        this.loading = false;
      }
    });
  }

  updateProfile() {
    this.http.put(`http://localhost:8080/api/users/${this.user.userId}`, this.updatedUser).subscribe({
      next: () => {
        this.successMessage = 'Profile updated successfully ✅';
        this.errorMessage = '';
        // window.location.reload();
        // this.fetchCurrentUser();
      },
      error: (err) => {
        console.error('Failed to update profile', err);
        this.errorMessage = err.error?.message || 'Failed to update profile';
        this.successMessage = '';
      }
    });
  }

  fetchAddresses() {
    this.http.get<any>('http://localhost:8080/api/addresses/users/current').subscribe({
      next: (res: any) => {
        console.log('Addresses:', res);
        this.addresses = res.content || res;
      },
      error: () => {
        console.error('Failed to load addresses');
      }
    });
  }

  addAddress() {
    this.http.post(`http://localhost:8080/api/addresses`, this.newAddress).subscribe({
      next: () => {
        alert('Address added ✅');
        this.fetchAddresses();
      },
      error: (err) => {
        alert('Failed to add address: ' + (err.error?.message || 'Unknown error'));
      }
    });
  }

  deleteAddress(addressId: number) {
    if (confirm('Are you sure you want to delete this address?')) {
      this.http.delete(`http://localhost:8080/api/addresses/${addressId}`).subscribe({
        next: () => {
          this.fetchAddresses();
          this.showDeleteConfirmation = false;
          alert('Address deleted ✅');
        },
        error: () => {
          alert('Failed to delete address');
        }
      });
    }
  }

  confirmDeleteAddress(addressId: string) {
    this.showDeleteConfirmation = true;
    this.deleteAddressId = addressId;
  }

  cancelDelete() {
    this.showDeleteConfirmation = false;
    this.deleteAddressId = null;
  }
}
