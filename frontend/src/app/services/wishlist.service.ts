import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class WishlistService {
  private baseUrl = 'http://localhost:8080/api/wishlist';

  constructor(private http: HttpClient) { }

  getWishlist(userId: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/${userId}`);
  }

  addProductToWishlist(userId: number, productId: number): Observable<any> {
    return this.http.post(`${this.baseUrl}/${userId}/${productId}`, {});
  }

  removeProductFromWishlist(userId: number, productId: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/${userId}/${productId}`);
  }
}
