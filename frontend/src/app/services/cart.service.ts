import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, Subject, tap } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class CartService {
  private baseUrl = 'http://localhost:8080/api/carts';

  constructor(private http: HttpClient) { }

  addToCart(productId: number, quantity: number = 1) {
    return this.http.post(`${this.baseUrl}/products/${productId}/quantity/${quantity}`, {});
  }

  getCart() {
    return this.http.get(`${this.baseUrl}/users/cart`);
  }

  updateQuantity(productId: number, action: 'add' | 'delete') {
    return this.http.put(`${this.baseUrl}/products/${productId}/quantity/${action}`, {});
  }

  deleteFromCart(cartId: number, productId: number) {
    return this.http.delete(`${this.baseUrl}/${cartId}/product/${productId}`);
  }

  private cartUpdated = new Subject<void>();

  // Call this after add/remove actions
  notifyCartChange() {
    this.cartUpdated.next();
  }

  // Allow components (like header) to subscribe
  onCartChange(): Observable<void> {
    return this.cartUpdated.asObservable();
  }


}